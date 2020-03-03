package com.mooc.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mooc.entity.OrderMaster;
import org.apache.ibatis.annotations.Param;


public interface OrderMasterMapper extends BaseMapper<OrderMaster>{
    OrderMaster selOne(String orderId);
    int addOne(OrderMaster orderMaster);
    IPage<OrderMaster> selByOpenId(Page page, @Param("openid") String openid);
    int updOne(OrderMaster orderMaster);
}
