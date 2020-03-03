package com.mooc.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mooc.entity.ProductCategory;
import com.mooc.entity.ProductInfo;
import com.mooc.enums.ResultEnum;
import com.mooc.exception.SellException;
import com.mooc.form.ProductForm;
import com.mooc.service.CategoryService;
import com.mooc.service.ProductInfoService;
import com.mooc.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private ProductInfoService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map){
        IPage<ProductInfo> productInfoPage=productService.selAll(new Page<ProductInfo>(page,size));
        map.put("productInfoPage",productInfoPage);
        map.put("currentPage", page);
        map.put("size", size);
        long pages = productInfoPage.getPages();
        map.put("totalPages",pages);
        return new ModelAndView("product/list", map);
    }
    @RequestMapping("on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId,
                               Map<String,Object> map){
        try {
            productService.onSale(productId);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error");
        }
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success");
    }

    @RequestMapping("off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId,
                               Map<String,Object> map){
        try {
            productService.offSale(productId);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error");
        }
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success");
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId,
                              Map<String, Object> map){
        if(!StringUtils.isEmpty(productId)){
            ProductInfo productInfo=productService.selOne(productId);
            map.put("productInfo",productInfo);
        }
        List<ProductCategory> categoryList = categoryService.selAll();
        map.put("categoryList",categoryList);

        return new ModelAndView("product/index");
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm form,
                             BindingResult bindingResult,
                             Map<String,Object> map){
        if(bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }

        ProductInfo productInfo=new ProductInfo();
        try {
            if(!StringUtils.isEmpty(form.getProductId())){
                productInfo=productService.selOne(form.getProductId());
                if(productInfo==null){
                    throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
                }
                BeanUtils.copyProperties(form, productInfo);
                productService.updOne(productInfo);
            }else{
                form.setProductId(KeyUtil.getUniqueKey());
                BeanUtils.copyProperties(form, productInfo);
                productService.addOne(productInfo);
            }
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/index");
            return new ModelAndView("common/error");
        }
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success");
    }


}
