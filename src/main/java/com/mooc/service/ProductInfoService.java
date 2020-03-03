package com.mooc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mooc.dto.CartDTO;
import com.mooc.entity.ProductInfo;

import java.util.List;

public interface ProductInfoService {
    ProductInfo selOne(String id);
    int updOne(ProductInfo productInfo);
    int addOne(ProductInfo productInfo);
    List<ProductInfo> selUpAll();
    IPage<ProductInfo> selAll(Page page);
    void decreaseStock(List<CartDTO> carDTOList);
    void increaseStock(List<CartDTO> carDTOList);
    ProductInfo onSale(String productId);
    ProductInfo offSale(String productId);
}
