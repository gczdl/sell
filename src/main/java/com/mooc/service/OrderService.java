package com.mooc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mooc.dto.OrderDTO;
import com.mooc.entity.OrderMaster;

import java.util.List;

public interface OrderService {
    /** 创建订单.*/
    OrderDTO create(OrderDTO orderDTO);

    /** 查询单个订单. */
    OrderDTO findOne(String orderId);

    /** 查询订单列表. */
    IPage<OrderDTO> findList(Page<OrderMaster> page, String buyerOpenid);

    /** 取消订单. */
    OrderDTO cancel(OrderDTO orderDTO);

    /** 完结订单. */
    OrderDTO finish(OrderDTO orderDTO);

    /** 支付订单. */
    OrderDTO paid(OrderDTO orderDTO);

    /** 查询订单列表. */
    IPage<OrderDTO> findList(Page<OrderMaster> page);
}
