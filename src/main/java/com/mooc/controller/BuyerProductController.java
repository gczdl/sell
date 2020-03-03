package com.mooc.controller;

import com.mooc.VO.ProductInfoVO;
import com.mooc.VO.ProductVO;
import com.mooc.VO.ResultVO;
import com.mooc.entity.ProductCategory;
import com.mooc.entity.ProductInfo;
import com.mooc.service.CategoryService;
import com.mooc.service.ProductInfoService;
import com.mooc.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ResultVO list(){
        //查询所有在架商品
        List<ProductInfo> productInfos = productInfoService.selUpAll();

        //归纳在架商品的所有类别
        List<Integer> productTypeList=new ArrayList<>();
        for(ProductInfo productInfo:productInfos){
            productTypeList.add(productInfo.getCategoryType());
        }
        //查询在架所有类别
        List<ProductCategory> productCategories = categoryService.selByCategoryTypeList(productTypeList);

        //数据拼装
        List<ProductVO> productVOS=new ArrayList<>();
        for(ProductCategory productCategory:productCategories){
            ProductVO productVO=new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());
            List<ProductInfoVO> productInfoVOList=new ArrayList<>();
            for(ProductInfo productInfo:productInfos){
                if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO=new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOS.add(productVO);
        }
        return ResultVOUtil.success(productVOS);
    }
}
