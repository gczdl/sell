package com.mooc.service;

import com.mooc.entity.ProductCategory;

import java.util.List;

public interface CategoryService {
    public ProductCategory selOne(int id);
    public int updOne(ProductCategory pc);
    public  int addOne(ProductCategory pc);
    public int delOne(int id);
    public List<ProductCategory> selByCategoryTypeList(List<Integer> list);
    public List<ProductCategory> selAll();
}
