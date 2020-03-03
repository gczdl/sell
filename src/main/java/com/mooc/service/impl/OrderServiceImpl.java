package com.mooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mooc.converter.IPageOrderMaster2IPageOrderDTO;
import com.mooc.converter.OrderMaster2OrderDTOConverter;
import com.mooc.dto.CartDTO;
import com.mooc.dto.OrderDTO;
import com.mooc.entity.OrderDetail;
import com.mooc.entity.OrderMaster;
import com.mooc.entity.ProductInfo;
import com.mooc.enums.OrderStatusEnum;
import com.mooc.enums.PayStatusEnum;
import com.mooc.enums.ResultEnum;
import com.mooc.exception.SellException;
import com.mooc.mapper.OrderDetailMapper;
import com.mooc.mapper.OrderMasterMapper;
import com.mooc.service.OrderService;
import com.mooc.service.PayService;
import com.mooc.service.ProductInfoService;
import com.mooc.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.beans.Transient;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.math.BigDecimal.*;
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    ProductInfoService productInfoService;

    @Autowired
    PayService payService;

    @Resource
    OrderDetailMapper orderDetailMapper;

    @Resource
    OrderMasterMapper orderMasterMapper;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.getUniqueKey();
        BigDecimal orderAmount = new BigDecimal(0);
        //减库存清单
        List<CartDTO> cartDTOList=new ArrayList<>();
        //1. 查询商品（数量, 价格）
        for(OrderDetail orderDetail:orderDTO.getOrderDetailList()){
            ProductInfo productInfo = productInfoService.selOne(orderDetail.getProductId());
            if(productInfo==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //2. 计算订单总价
            orderAmount=productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            //订单详情入库
            orderDetail.setDetailId(KeyUtil.getUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetailMapper.addOne(orderDetail);

            //商品写入减库存清单
            CartDTO cartDTO=new CartDTO(orderDetail.getProductId(),orderDetail.getProductQuantity());
            cartDTOList.add(cartDTO);
        }

        //3. 写入订单数据库（orderMaster和orderDetail）
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterMapper.addOne(orderMaster);
        //4. 扣库存
        productInfoService.decreaseStock(cartDTOList);
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterMapper.selOne(orderId);
        if(orderMaster==null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        OrderDTO orderDTO=new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        List<OrderDetail> orderDetailList = orderDetailMapper.selByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetailList)){
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public IPage<OrderDTO> findList(Page<OrderMaster> page, String buyerOpenid) {
          IPage<OrderMaster> orderMasterIPage = orderMasterMapper.selByOpenId(page, buyerOpenid);
          IPage<OrderDTO> orderDTOIPage= IPageOrderMaster2IPageOrderDTO.converter(orderMasterIPage);
        return orderDTOIPage;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster=new OrderMaster();

        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("订单状态错误：订单{}",orderMaster);
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        int count = orderMasterMapper.updOne(orderMaster);
        if(count==0){
            log.error("【取消订单】更新失败, orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //返回库存
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【取消订单】订单中无商品详情, orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList=orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.increaseStock(cartDTOList);
        //如果已支付, 需要退款
        if(orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            payService.refund(orderDTO);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("订单状态错误：订单详情{}",orderDTO);
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //更新订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster=new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        int count=orderMasterMapper.updOne(orderMaster);
        if(count==0){
            log.error("订单更新失败：订单详情{}",orderDTO);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //查询订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("订单状态错误：订单详情{}",orderDTO);
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //查询支付状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("支付状态错误：订单详情{}",orderDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //更新支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster=new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        int count=orderMasterMapper.updOne(orderMaster);
        if(count==0){
            log.error("订单更新失败：订单详情{}",orderDTO);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    public IPage<OrderDTO> findList(Page<OrderMaster> page) {
        QueryWrapper<OrderMaster> queryWrapper = new QueryWrapper<OrderMaster>();
        IPage<OrderMaster> orderMasterIPage = orderMasterMapper.selectPage(page, queryWrapper);
        return IPageOrderMaster2IPageOrderDTO.converter(orderMasterIPage);
    }
}
