package com.mooc.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Data
public class OrderDetail {
    //id
    private String detailId;

    /** 订单id. */
    private String orderId;

    /** 商品id. */
    private String productId;

    /** 商品名称. */
    private String productName;

    /** 商品单价. */
    private BigDecimal productPrice;

    /** 商品数量. */
    private Integer productQuantity;

    /** 商品小图. */
    private String productIcon;

}
