package com.mooc.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mooc.dto.OrderDTO;
import com.mooc.enums.ResultEnum;
import com.mooc.exception.SellException;
import com.mooc.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {
    @Autowired
    private OrderService orderService;
    @RequestMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String,Object> map){
        IPage<OrderDTO> orderDTOIPage = orderService.findList(new Page<>(page, size));
        long pages = orderDTOIPage.getPages();
        map.put("orderDTOIPage",orderDTOIPage);
        map.put("page",page);
        map.put("size",size);
        map.put("totalPages",pages);
        return new ModelAndView("order/list");
    }
    @GetMapping("/cancel")
    public ModelAndView cancel(@Param("orderId") String orderId,
                               Map<String,Object> map,
                               @RequestParam(value = "page", defaultValue = "1") Integer page,
                               @RequestParam(value = "size", defaultValue = "10") Integer size){
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.cancel(orderDTO);
        }
        catch (SellException e){
            log.error("【卖家端取消订单】发生异常{}", e);
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/order/list?size="+size+"&page="+page);
            return new ModelAndView("common/error", map);
        }
        map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
        map.put("url","/sell/seller/order/list?size="+size+"&page="+page);
        return new ModelAndView("common/success");
    }

    @GetMapping("/detail")
    public ModelAndView detail(@Param("orderId") String orderId,
                               Map<String,Object> map,
                               @RequestParam(value = "page", defaultValue = "1") Integer page,
                               @RequestParam(value = "size", defaultValue = "10") Integer size){
        OrderDTO orderDTO=new OrderDTO();
        try {
            orderDTO = orderService.findOne(orderId);
        }
        catch (SellException e){
            log.error("【卖家端查询订单详情】发生异常{}", e);
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/order/list?size="+size+"&page="+page);
            return new ModelAndView("common/error", map);
        }
        map.put("orderDTO",orderDTO);
        map.put("page",page);
        map.put("size",size);
        return new ModelAndView("order/detail");
    }

    @GetMapping("/finish")
    public ModelAndView finish(@Param("orderId") String orderId,
                               Map<String,Object> map,
                               @RequestParam(value = "page", defaultValue = "1") Integer page,
                               @RequestParam(value = "size", defaultValue = "10") Integer size){
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.finish(orderDTO);
        }
        catch (SellException e){
            log.error("【卖家端完结订单】发生异常{}", e);
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/order/list?size="+size+"&page="+page);
            return new ModelAndView("common/error", map);
        }
        map.put("msg", ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
        map.put("url","/sell/seller/order/list?size="+size+"&page="+page);
        return new ModelAndView("common/success");
    }
}
