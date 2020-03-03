//package com.mooc.controller;
//
//import com.mooc.enums.ResultEnum;
//import com.mooc.exception.SellException;
//import lombok.extern.slf4j.Slf4j;
//import me.chanjar.weixin.common.api.WxConsts;
//import me.chanjar.weixin.common.error.WxErrorException;
//import me.chanjar.weixin.mp.api.WxMpService;
//import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.net.URLEncoder;
//
//@Controller
//@Slf4j
//@RequestMapping("/wechat")
//public class WechatController_original {
//    @Autowired
//    WxMpService wxMpService;
//
//    @GetMapping("/authorize")
//    public String authorize(@RequestParam("returnUrl") String returnUrl){
//
////        String url= "http://gczdl.mynatapp.cc"+"/sell/wechat/userInfo";
//        String redirectUrl=wxMpService.oauth2buildAuthorizationUrl("http://gczdl.mynatapp.cc/sell/wechat/userInfo", WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl));
//        System.out.println("进来了");
//        System.out.println(redirectUrl);
//        return "redirect:" + redirectUrl;
//    }
//    String codee;
//    @GetMapping("/userInfo")
//    public String userInfo(@RequestParam("code") String code,
//                           @RequestParam("state") String returnUrl){
//            WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
//            try {
//                wxMpOAuth2AccessToken=wxMpService.oauth2getAccessToken(code);
//            }catch (WxErrorException e){
//                log.error("【微信网页授权】{}", e);
//                throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
//            }
//            String openId=wxMpOAuth2AccessToken.getOpenId();
//            log.info("openId={}",openId);
//            log.info("returnUrl={}",returnUrl);
//            return "redirect:"+returnUrl+"?openid="+openId;
//    }
//
//}
