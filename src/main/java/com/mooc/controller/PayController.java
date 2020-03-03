package com.mooc.controller;

import com.mooc.VO.NotifyVO;
import com.mooc.dto.OrderDTO;
import com.mooc.enums.PayStatusEnum;
import com.mooc.enums.ResultEnum;
import com.mooc.exception.SellException;
import com.mooc.service.OrderService;
import com.mooc.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderService orderService;

    @Autowired
    PayService payService;
    @GetMapping("/create")
    public String create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl")String returnUtl,
                               Model model){
        log.info("【发起支付】orderId={},return={}",orderId,returnUtl);
        //1.检查订单是否存在
        OrderDTO orderDTO = orderService.findOne(orderId);
        if(orderDTO==null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        if(orderDTO.getPayStatus()== PayStatusEnum.SUCCESS.getCode()){
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //2.发起支付
        Map map = payService.create(orderDTO);
        map.put("returnUrl",returnUtl);
        System.out.println(map);
        model.addAttribute("map",map);
        return "pay/create";
    }
    @PostMapping("/notify")
    @ResponseBody
    public String notify(NotifyVO notifyData) {
        payService.notify(notifyData);
        log.info("【应答支付回调】");
        //返回给微信处理结果
        return "OK";
    }

}
