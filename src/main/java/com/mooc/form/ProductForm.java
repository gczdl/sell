package com.mooc.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


@Data
public class ProductForm {


    private String productId;

    /** 名字. */
    @NotEmpty(message = "名称必填")
    private String productName;

    /** 单价. */
    @NotNull(message = "价格必填")
    private BigDecimal productPrice;

    /** 库存. */
    @NotNull(message = "库存必填")
    private Integer productStock;

    /** 描述. */
    private String productDescription;

    /** 小图. */
    private String productIcon;

    /** 类目编号. */
    @NotNull(message = "类目必填")
    private Integer categoryType;
}
