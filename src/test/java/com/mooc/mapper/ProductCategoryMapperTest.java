package com.mooc.mapper;

import com.mooc.entity.ProductCategory;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class ProductCategoryMapperTest {

    @Resource
    ProductCategoryMapper productCategoryMapper;

    @Test
    void selByIdTest(){
        productCategoryMapper.addOne(new ProductCategory("叉烧饭",2));
        ProductCategory pc=productCategoryMapper.selOne(1);
        System.out.println(pc);
        pc.setCategoryType(3);
        productCategoryMapper.updOne(pc);
    }
}