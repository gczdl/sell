package com.mooc.service;


import com.mooc.VO.NotifyVO;
import com.mooc.dto.OrderDTO;

import java.util.Map;

public interface PayService {
    public Map create(OrderDTO orderDTO);

    public void notify(NotifyVO notifyData);

    public void refund(OrderDTO orderDTO);
}
