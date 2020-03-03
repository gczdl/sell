package com.mooc.mapper;

import com.mooc.entity.OrderMaster;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class OrderMasterMapperTest {
    @Resource
    OrderMasterMapper orderMasterMapper;
    @Test
    void testaddOne(){
        OrderMaster orderMaster =new OrderMaster();
        orderMaster.setOrderId("1234");
        orderMaster.setBuyerAddress("广东财经大学");
        orderMaster.setOrderAmount(new BigDecimal(3.6));
        orderMaster.setBuyerName("张三");
        orderMaster.setBuyerPhone("13214107128");
        orderMaster.setBuyerOpenid("110120");
        orderMasterMapper.addOne(orderMaster);
    }
    @Test
    void selByOpenIdTest(){
//        System.out.println(orderMasterMapper.selByOpenId());
    }
}