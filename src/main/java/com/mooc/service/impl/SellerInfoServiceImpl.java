package com.mooc.service.impl;

import com.mooc.entity.SellerInfo;
import com.mooc.mapper.SellerInfoMapper;
import com.mooc.service.SellerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SellerInfoServiceImpl implements SellerInfoService {
    @Autowired
    SellerInfoMapper sellerInfoMapper;

    public List<SellerInfo> selectByMap(Map map){
        return sellerInfoMapper.selectByMap(map);
    }

}
