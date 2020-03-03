package com.mooc.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mooc.entity.ProductInfo;
import com.mooc.service.ProductInfoService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class ProductInfoServiceImplTest {
    @Autowired
    ProductInfoService productInfoService;
    @Test
    void selOne() {
        ProductInfo productInfo = productInfoService.selOne("12345");
        System.out.println(productInfo);
    }

    @Test
    void addOne() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("12345");
        productInfo.setProductName("叉烧饭");
        productInfo.setProductPrice(new BigDecimal(13.5));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("好吃的饭");
        productInfo.setProductIcon("http://xxxxx.jpg");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(2);
        int num=productInfoService.addOne(productInfo);
        System.out.println(num);
    }


    @Test
    void selUpAll() {
        System.out.println(productInfoService.selUpAll());
    }

    @Test
    void selAll() {
//        List<ProductInfo> productInfoPageInfo = productInfoService.selAll(new Page(1,2));
//        System.out.println(productInfoPageInfo.get(1));
    }
}