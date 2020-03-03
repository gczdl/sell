package com.mooc.controller;

import com.mooc.entity.ProductCategory;
import com.mooc.enums.ResultEnum;
import com.mooc.exception.SellException;
import com.mooc.form.CategoryForm;
import com.mooc.service.CategoryService;
import com.mooc.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/seller/category")
public class SellerCategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView list(Map<String,Object> map ){
        List<ProductCategory> categoryList=categoryService.selAll();
        map.put("categoryList",categoryList);
        return new ModelAndView("category/list");
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId", required = false) Integer categoryId ,
                              Map<String,Object> map){
        if(categoryId!=null){
            ProductCategory productCategory=categoryService.selOne(categoryId);
            map.put("category",productCategory);
        }
        return new ModelAndView("category/index");
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm form,
                             BindingResult bindingResult,
                             Map<String,Object> map){
        if(bindingResult.hasErrors()){
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/sell/seller/category/list");
            return new ModelAndView("common/error", map);
        }
        ProductCategory productCategory = new ProductCategory();
        try{
            if(form.getCategoryId()!=null){
                productCategory=categoryService.selOne(form.getCategoryId());
                if(productCategory==null){
                    log.error("【更新类目】类目不存在 categoryId={}",form.getCategoryId());
                    throw new SellException(ResultEnum.CATEGORY_NOT_EXIST);
                }
                BeanUtils.copyProperties(form,productCategory);
                categoryService.updOne(productCategory);
            }else{
                BeanUtils.copyProperties(form,productCategory);
                categoryService.addOne(productCategory);
            }
        }catch (SellException e){
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/category/list");
            return new ModelAndView("common/error", map);
        }catch (Exception e){
            map.put("msg", "");
            map.put("url", "/sell/seller/category/list");
            return new ModelAndView("common/error", map);
        }
        map.put("url","/sell/seller/category/list");
        return new ModelAndView("common/success");
    }

}
