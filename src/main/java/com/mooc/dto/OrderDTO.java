package com.mooc.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mooc.entity.OrderDetail;
import com.mooc.enums.OrderStatusEnum;
import com.mooc.enums.PayStatusEnum;
import com.mooc.utils.EnumUtil;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@Data
public class OrderDTO {
    /** 订单id. */
    private String orderId;

    /** 买家名字. */
    private String buyerName;

    /** 买家手机号. */
    private String buyerPhone;

    /** 买家地址. */
    private String buyerAddress;

    /** 买家微信Openid. */
    private String buyerOpenid;

    /** 订单总金额. */
    private BigDecimal orderAmount;

    /** 订单状态, 默认为0新下单. */
    private Integer orderStatus;

    /** 支付状态, 默认为0未支付. */
    private Integer payStatus;

    /** 支付猫支付成功订单号. */
    private String wechatOrderNo;

    /** 创建时间. */
    private Date createTime;

    /** 更新时间. */
    private Date updateTime;

    /** 商品信息. */
    List<OrderDetail> orderDetailList;

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtil.getByCode(orderStatus,OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){
        return EnumUtil.getByCode(payStatus,PayStatusEnum.class);
    }
}
