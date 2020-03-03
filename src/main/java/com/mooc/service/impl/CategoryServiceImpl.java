package com.mooc.service.impl;

import com.mooc.entity.ProductCategory;
import com.mooc.service.CategoryService;
import com.mooc.mapper.ProductCategoryMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    ProductCategoryMapper productCategoryMapper;

    @Override
    public ProductCategory selOne(int id) {
        return productCategoryMapper.selOne(id);
    }

    @Override
    public int updOne(ProductCategory pc)  {
        int i=0;
        try{
            i = productCategoryMapper.updOne(pc);
        }catch (Exception e){
            throw e;
        }
        return i;
    }

    @Override
    public int addOne(ProductCategory pc) {
        return productCategoryMapper.addOne(pc);
    }

    @Override
    public int delOne(int id) {
        return productCategoryMapper.delOne(id);
    }

    @Override
    public List<ProductCategory> selByCategoryTypeList(List<Integer> list) {
        return productCategoryMapper.selByCategoryTypeList(list);
    }

    @Override
    public List<ProductCategory> selAll() {
        return productCategoryMapper.selAll();
    }
}
