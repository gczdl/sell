package com.mooc.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mooc.dto.OrderDTO;
import com.mooc.entity.OrderDetail;
import com.mooc.enums.ResultEnum;
import com.mooc.exception.SellException;
import com.mooc.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderForm2OrderDTOConverter {
    public static OrderDTO convert(OrderForm orderForm){
        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        Gson gson=new Gson();
        List<OrderDetail> orderDetailList=new ArrayList<>();
        try{
            orderDetailList=gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>(){
                    }.getType());
        }catch (Exception e){
            log.error("【对象转换】错误, string={}", orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
