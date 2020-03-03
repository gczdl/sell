package com.mooc.converter;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mooc.dto.OrderDTO;
import com.mooc.entity.OrderMaster;

import java.util.List;

public class IPageOrderMaster2IPageOrderDTO {
    public static IPage<OrderDTO> converter(IPage<OrderMaster> iPage , List<OrderDTO> orderDTOList){
        IPage<OrderDTO> orderDTOIPage=new Page<OrderDTO>();
        orderDTOIPage.setRecords(orderDTOList);
        orderDTOIPage.setCurrent(iPage.getCurrent());
        orderDTOIPage.setPages(iPage.getPages());
        orderDTOIPage.setSize(iPage.getSize());
        orderDTOIPage.setTotal(iPage.getTotal());
        return orderDTOIPage;
    }

    public static IPage<OrderDTO> converter(IPage<OrderMaster> iPage){
        IPage<OrderDTO> orderDTOIPage=new Page<OrderDTO>();
        orderDTOIPage.setRecords(OrderMaster2OrderDTOConverter.convert(iPage.getRecords()));
        orderDTOIPage.setCurrent(iPage.getCurrent());
        orderDTOIPage.setPages(iPage.getPages());
        orderDTOIPage.setSize(iPage.getSize());
        orderDTOIPage.setTotal(iPage.getTotal());
        return orderDTOIPage;
    }

}
