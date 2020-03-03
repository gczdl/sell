package com.mooc.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mooc.dto.CartDTO;
import com.mooc.entity.ProductInfo;
import com.mooc.enums.ProductStatusEnum;
import com.mooc.enums.ResultEnum;
import com.mooc.exception.SellException;
import com.mooc.mapper.ProductInfoMapper;
import com.mooc.service.ProductInfoService;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Resource
    ProductInfoMapper productInfoMapper;

    @Override
    public ProductInfo selOne(String id) {
        return productInfoMapper.selOne(id);
    }

    @Override
    @Transactional
    public int updOne(ProductInfo productInfo) {
        return productInfoMapper.updOne(productInfo);
    }

    @Override
    public int addOne(ProductInfo productInfo) {
        return productInfoMapper.addOne(productInfo);
    }

    @Override
    public List<ProductInfo> selUpAll() {
        return productInfoMapper.selByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public IPage<ProductInfo> selAll(Page page) {
        IPage<ProductInfo> productInfos = productInfoMapper.selAll(page);
        return productInfos;
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> carDTOList) {
        for(CartDTO cartDTO:carDTOList){
            ProductInfo productInfo=productInfoMapper.selOne(cartDTO.getProductId());
            if(productInfo==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result=productInfo.getProductStock()-cartDTO.getProductQuantity();
            if(result<0){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            productInfoMapper.updOne(productInfo);
        }

    }

    @Override
    public void increaseStock(List<CartDTO> carDTOList) {
        for(CartDTO cartDTO:carDTOList){
            ProductInfo productInfo=productInfoMapper.selOne(cartDTO.getProductId());
            if(productInfo==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result=productInfo.getProductStock()+cartDTO.getProductQuantity();
            productInfo.setProductStock(result);
            productInfoMapper.updOne(productInfo);
        }
    }

    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = productInfoMapper.selOne(productId);
        if(productInfo == null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if(productInfo.getProductStatusEnum()==ProductStatusEnum.UP){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        productInfoMapper.updOne(productInfo);
        return productInfo;
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = productInfoMapper.selOne(productId);
        if(productInfo == null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if(productInfo.getProductStatusEnum()==ProductStatusEnum.DOWN){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfoMapper.updOne(productInfo);
        return productInfo;
    }
}
