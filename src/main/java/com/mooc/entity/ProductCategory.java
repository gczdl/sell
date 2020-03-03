package com.mooc.entity;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.Date;
@Data
public class ProductCategory {
    private Integer categoryId;
    private String categoryName;
    private int categoryType;
    private Date createTime;
    private Date updateTime;

    public ProductCategory() {
    }

    public ProductCategory(String categoryName, int categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }

}
