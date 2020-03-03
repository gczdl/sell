package com.mooc;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mooc.entity.OrderMaster;
import com.mooc.mapper.OrderMasterMapper;
import com.mooc.mapper.ProductCategoryMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class SellApplicationTests {
    @Test
    void contextLoads() {
        log.debug("debug...");
        log.info("info..");
        log.error("error...");
    }
    @Resource
    ProductCategoryMapper productCategoryMapper;
    @Test
    void selByIdTest(){
//        System.out.println(productCategoryMapper.delById(3));
//        System.out.println(productCategoryMapper.selById(1));
        System.out.println(productCategoryMapper.selByCategoryTypeList(Arrays.asList(2,3,4)));
    }

    @Resource
    OrderMasterMapper orderMasterMapper;

    String openId="oht0ws7e18ygxqbrm258-rXz-31M";
    String postUrl="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=27_mkRY8l-1zBWZNY_rorHXIikJ189CCb_tF26EikH3osuXBp-YdrfymXGL8WehjbODdprX8Qlpegp1fIeQMUZ58p3XFY2h-Ikz8RwXvQHHGstzfcVJu7R6M6SCRJ8HZUhACAYQL";
    @Test
    void test(){

        SortedMap<String, Object> map = new TreeMap<>();
        map.put("touser","oht0ws7e18ygxqbrm258-rXz-31M");
        map.put("template_id","52T9ClTXjjC_nQBbcK5BGFC5AUEhP0PLKUda_LNNt44");
        map.put("data",null);
        RestTemplate restTemplate=new RestTemplate();
        Map response = restTemplate.postForObject(postUrl, map, Map.class);
        System.out.println(response);
    }
}
