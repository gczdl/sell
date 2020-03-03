package com.mooc.mapper;

import com.mooc.entity.OrderDetail;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class OrderDetailTest {
    @Resource
    OrderDetailMapper orderDetailMapper;
    @Test
    void addOne(){
        OrderDetail orderDetail=new OrderDetail();
        orderDetail.setDetailId("1111");
        orderDetail.setOrderId("1234");
        orderDetail.setProductId("12345");
        orderDetail.setProductName("叉烧饭");
        orderDetail.setProductPrice(new BigDecimal(2.3));
        orderDetail.setProductIcon("http://xxxx.jpg");
        orderDetail.setProductQuantity(1);
        int i = orderDetailMapper.addOne(orderDetail);
        System.out.println(i);
    }
    @Test
    void selByOrderIdTest(){
        System.out.println(orderDetailMapper.selByOrderId("123"));
    }
}