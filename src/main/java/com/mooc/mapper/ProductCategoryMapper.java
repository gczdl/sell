package com.mooc.mapper;

import com.mooc.entity.ProductCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface ProductCategoryMapper {
    ProductCategory selOne(int i);
    int updOne(ProductCategory productCategory);
    int addOne(ProductCategory p);
    int delOne(int i);
    List<ProductCategory> selByCategoryTypeList(@Param("categoryTypeList") List<Integer> categoryTypeList);
    List<ProductCategory> selAll();
}
