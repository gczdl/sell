package com.mooc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mooc.enums.ProductStatusEnum;
import com.mooc.utils.EnumUtil;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;

@Repository
@Data
public class ProductInfo {
    /** id. */
    private String productId;

    /** 名字. */
    private String productName;

    /** 单价. */
    private BigDecimal productPrice;

    /** 库存. */
    private Integer productStock;

    /** 描述. */
    private String productDescription;

    /** 小图. */
    private String productIcon;

    /** 状态, 0正常1下架. */
    private Integer productStatus=0;

    /** 类目编号. */
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum() {
        return EnumUtil.getByCode(productStatus, ProductStatusEnum.class);
    }
}
