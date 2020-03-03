package com.mooc.controller;

import com.google.gson.Gson;
import com.mooc.config.WechatAccountConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeixinController {
    @Autowired
    WechatAccountConfig wechatAccount;

    @GetMapping("/auth")
    public void auth(@RequestParam("code") String code){
//        log.info("进入auth方法。。。");
        log.info("code={}",code);
        String url= "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxe0998069c517da82&secret=0582c7a6abb36d7794f37a04e873e777&code="+code+"&grant_type=authorization_code";
        RestTemplate restTemplate=new RestTemplate();
        String response=restTemplate.getForObject(url,String.class);
        Gson gson=new Gson();
        Map map = new HashMap<String,String>();
        map=gson.fromJson(response,Map.class);
        log.info("response={}",map);
        log.info("openid={}",map.get("openid"));
        String url2="https://api.weixin.qq.com/sns/userinfo?access_token="+map.get("access_token")+"&openid="+map.get("openid")+"&lang=zh_CN";
        String response2=restTemplate.getForObject(url2,String.class);

        log.info("response2={}",response2);
    }
}
