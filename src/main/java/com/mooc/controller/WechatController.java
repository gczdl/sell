package com.mooc.controller;

import com.mooc.config.ProjectUrlConfig;
import com.mooc.config.WechatAccountConfig;
import com.mooc.enums.ResultEnum;
import com.mooc.exception.SellException;
import com.mooc.utils.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.net.URLEncoder;
import java.util.SortedMap;
import java.util.TreeMap;

@Controller
@Slf4j
@RequestMapping("/wechat")
public class WechatController {
    @Autowired
    WechatAccountConfig wechatAccount;
    @Autowired
    ProjectUrlConfig projectUrl;

    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl){
//        String payUrl="https://api.paycats.cn/v1/wx/openid?";
        SortedMap<String, Object> map = new TreeMap<>();
        map.put("mch_id",wechatAccount.getMchId());
        map.put("callback_url", URLEncoder.encode(returnUrl));
        map.put("scope","snsapi_userinfo");
        String sign = new SignUtil().createSign(map,wechatAccount.getMchKey());

        String url=projectUrl.getGetOpenIdUrl()+"?mch_id="+map.get("mch_id")+"&callback_url="+map.get("callback_url")+
                "&scope="+map.get("scope")+"&sign="+sign;

        return "redirect:"+url;
    }

}
