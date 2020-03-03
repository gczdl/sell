package com.mooc.service;

import com.mooc.entity.SellerInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface SellerInfoService {
    public List<SellerInfo> selectByMap(Map map);
}
