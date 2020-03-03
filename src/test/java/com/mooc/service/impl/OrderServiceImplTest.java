package com.mooc.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mooc.dto.OrderDTO;
import com.mooc.entity.OrderDetail;
import com.mooc.entity.OrderMaster;
import com.mooc.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class OrderServiceImplTest {
    @Autowired
    OrderService orderService;

    String orderId="1572165609992271565";
    String openId="oUoQBwVHPAuEOaVdjXsnmDbMKNOU";
    String ORDER_ID="1572165609992271565";
    @Test
    void create() {
        OrderDTO orderDTO= new OrderDTO();
        orderDTO.setBuyerAddress("广东财经大学");
        orderDTO.setBuyerName("我我我");
        orderDTO.setBuyerPhone("15218166921");
        orderDTO.setBuyerOpenid("123321");

        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1=new OrderDetail();
        o1.setProductId("12346");
        o1.setProductQuantity(1);
        orderDetailList.add(o1);
        OrderDetail o2=new OrderDetail();
        o2.setProductId("123458");
        o2.setProductQuantity(2);
        orderDetailList.add(o2);

        orderDTO.setOrderDetailList(orderDetailList);
        orderService.create(orderDTO);
    }

    @Test
    void findOne() {
        log.info("TestfindOne:{}",orderService.findOne(orderId));
    }

    @Test
    void findList() {
        Page<OrderMaster> page=new Page(3,5);
        IPage<OrderDTO> list = orderService.findList(page, openId);
        System.out.println(list.getCurrent());
    }

    @Test
    void cancel() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.cancel(orderDTO);
    }

    @Test
    void finish() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.finish(orderDTO);
    }

    @Test
    void paid() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.paid(orderDTO);
    }

    @Test
    void findList1() {
    }

}