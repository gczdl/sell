package com.mooc.service.impl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mooc.VO.NotifyVO;
import com.mooc.config.ProjectUrlConfig;
import com.mooc.config.WechatAccountConfig;
import com.mooc.dto.OrderDTO;
import com.mooc.enums.ResultEnum;
import com.mooc.exception.SellException;
import com.mooc.service.OrderService;
import com.mooc.service.PayService;
import com.mooc.utils.JsonUtil;
import com.mooc.utils.MathUtil;
import com.mooc.utils.SignUtil;
import com.mooc.utils.UrlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Slf4j
@Service
public class PayServiceImpl implements PayService {
    final static  String ORDER_BODY="微信点餐订单";
    @Autowired
    WechatAccountConfig wechatAccount;
    @Autowired
    OrderService orderService;
    @Autowired
    ProjectUrlConfig projectUrl;

    @Override
    public Map create(OrderDTO orderDTO) {
        SortedMap<String, Object> map = new TreeMap<>();
        map.put("mch_id",wechatAccount.getMchId());
        map.put("total_fee",(orderDTO.getOrderAmount().multiply(new BigDecimal(100))).intValue());
        map.put("out_trade_no",orderDTO.getOrderId());
        map.put("body",ORDER_BODY);
        map.put("openid",orderDTO.getBuyerOpenid());
//        map.put("attach","微信支付学习");
        String sign=new SignUtil().createSign(map,wechatAccount.getMchKey());
        map.put("sign",sign);
        RestTemplate restTemplate=new RestTemplate();
        Map response = restTemplate.postForObject(projectUrl.getPrePayUrl(), map, Map.class);
        Map bridge_config=JsonUtil.Json2Map(response.get("bridge_config"));
        log.info("【微信预支付详情】 response:{}", bridge_config);
        return bridge_config;
    }

    @Override
    public void notify(NotifyVO notifyData) {
        //1. 验证签名
        //2. 支付的状态
        //3. 支付金额
        //4. 支付人(下单人 == 支付人)
        log.info("【异步通知】 ：{}",notifyData);

        if ("order.succeeded".equals(notifyData.getNotify_type())){
            //查询订单
            OrderDTO orderDTO=orderService.findOne(notifyData.getOut_trade_no());
            //判断订单是否存在
            if(orderDTO==null){
                log.error("【微信支付】异步通知, 订单不存在, orderId={}", notifyData.getOrder_no());
                throw new SellException(ResultEnum.ORDER_NOT_EXIST);
            }
            //判断金额是否一致
            if(!MathUtil.equals(notifyData.getTotal_fee().doubleValue(),
                    orderDTO.getOrderAmount().multiply(new BigDecimal(100)).doubleValue())){
                log.error("【微信支付】异步通知, 订单金额不一致, orderId={}, 微信通知金额={}, 系统金额={}",
                        orderDTO.getOrderId(),
                        orderDTO.getOrderAmount(),
                        notifyData.getTotal_fee());
                throw new SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
            }
            //修改订单的支付状态
            orderDTO.setWechatOrderNo(notifyData.getOrder_no());
            orderService.paid(orderDTO);
        }
    }


    @Override
    public void refund(OrderDTO orderDTO) {

        SortedMap<String,Object> map=new TreeMap<>();
        map.put("mch_id",wechatAccount.getMchId());
        map.put("order_no",orderDTO.getWechatOrderNo());
        if(map.get("order_no")==null||map.get("order_no")==""){
            log.info("【支付单号为空，退款失败】");
            throw new SellException(ResultEnum.PAY_ORDERNO_NULL);
        }
        String sign=new SignUtil().createSign(map,wechatAccount.getMchKey());
        map.put("sign",sign);
        RestTemplate restTemplate=new RestTemplate();
        Map response=new HashMap();
        try{
             response = restTemplate.postForObject(projectUrl.getRefundUrl(), map, Map.class);
        }catch (Exception e){
            log.error("【请求退款失败】：{}",e.getMessage());
            throw new SellException(ResultEnum.ORDER_REFOUND_FAIL);
        }
        if(!response.get("return_code").equals(0)){
            log.info("【支付订单退款失败】response:{}",response);
            throw new SellException(ResultEnum.ORDER_REFOUND_FAIL);
        }
    }
}
