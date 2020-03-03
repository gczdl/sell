package com.mooc.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mooc.entity.ProductInfo;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface ProductInfoMapper {
    ProductInfo selOne(String id);
    int addOne(ProductInfo productInfo);
    List<ProductInfo> selByProductStatus(Integer productStatus);
    IPage<ProductInfo> selAll(Page page);
    int updOne(ProductInfo productInfo);
}
