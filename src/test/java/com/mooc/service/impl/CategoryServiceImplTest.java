package com.mooc.service.impl;

import com.mooc.entity.ProductCategory;
import com.mooc.service.CategoryService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class CategoryServiceImplTest {
    @Autowired
    CategoryService categoryService;
    @Test
    void selOne() {
        ProductCategory productCategory = categoryService.selOne(1);
        Assert.assertNotEquals(null,productCategory);
    }

    @Test
    void updOne() {
        ProductCategory productCategory = categoryService.selOne(1);
        productCategory.setCategoryType(1);
        int num=categoryService.updOne(productCategory);
        Assert.assertNotEquals(0,num);
    }

    @Test
    void addOne() {
        ProductCategory productCategory=new ProductCategory("重庆小面",5);
        int num=categoryService.addOne(productCategory);
        Assert.assertNotEquals(0,num);
    }

    @Test
    void delOne() {
        int num=categoryService.delOne(5);
        Assert.assertNotEquals(0,num);
    }

    @Test
    void selByCategoryTypeList() {
        List<ProductCategory> productCategories = categoryService.selByCategoryTypeList(Arrays.asList(1, 2));
        Assert.assertNotEquals(null,productCategories);
    }

    @Test
    void selAll() {
        List<ProductCategory> productCategories = categoryService.selAll();
//        for(ProductCategory item : productCategories){
//            System.out.println(item);
//        }
        Assert.assertNotEquals(null,productCategories);
    }
}