package com.mooc.VO;

import lombok.Data;

@Data
public class NotifyVO {
    //商户号
    private String mch_id;
    //通知类型
    private String notify_type;
    //数据签名
    private String sign;
    //支付猫系统订单号
    private String order_no;
    //金额。单位：分
    private Integer total_fee;
    //商户端自主生成的订单号
    private String out_trade_no;
    //微信用户手机显示订单号
    private String transaction_id;
    //支付成功时间
    private String pay_at;
    //用户OPENID标示
    private String openid;
    //商户自定义数据
    private String attach;
}
