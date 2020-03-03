package com.mooc.mapper;

import com.mooc.entity.OrderDetail;

import java.util.List;

public interface OrderDetailMapper {
    int addOne(OrderDetail orderDetail);
    List<OrderDetail> selByOrderId(String orderId);
}
