package com.mooc.service.impl;

import com.mooc.dto.OrderDTO;
import com.mooc.service.OrderService;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
class PayServiceImplTest {
    @Autowired
    OrderService orderService;
    @Autowired
//    PayService payService;
    @Test
    void create() {
        OrderDTO orderDTO = orderService.findOne("123");
        System.out.println(orderDTO);

    }

    @Test
    void notify1() {
    }

    @Test
    void refund() {
    }
}