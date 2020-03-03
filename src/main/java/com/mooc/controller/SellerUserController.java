package com.mooc.controller;

import com.mooc.config.ProjectUrlConfig;
import com.mooc.constant.CookisConstant;
import com.mooc.constant.RedisConstant;
import com.mooc.entity.SellerInfo;
import com.mooc.enums.ResultEnum;
import com.mooc.service.SellerInfoService;
import com.mooc.utils.CookisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RequestMapping("/seller")
@Controller
@Slf4j
public class SellerUserController {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    SellerInfoService sellerInfoService;

    @Autowired
    ProjectUrlConfig projectUrl;

    @GetMapping("/login")
    public ModelAndView login(){
        return new ModelAndView("user/login");
    }

    @PostMapping("/login")
    public ModelAndView login(String name, String password,
                              HttpServletResponse response,
                              Map<String,String> map){
        if(StringUtils.isEmpty(name)||StringUtils.isEmpty(password)){
            map.put("msg","信息填写错误");
            map.put("url","/sell/seller/login");
            return new ModelAndView("common/error", map);
        }
        map.put("username",name);
        map.put("password",password);
        //查询用户
        List<SellerInfo> sellerInfos = sellerInfoService.selectByMap(map);
        //检查用户是否存在
        if(CollectionUtils.isEmpty(sellerInfos)){
            log.info("【用户登录】 用户不存在");
            map.put("msg","信息填写错误");
            map.put("url","/sell/seller/login");
            return new ModelAndView("common/error", map);
        }

        //设置token至redis
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;

        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),sellerInfos.get(0).getOpenid(),expire, TimeUnit.SECONDS);

        //设置token至cookie
        CookisUtil.set(response, CookisConstant.TOKEN,token,expire);

        return new ModelAndView("redirect:" + projectUrl.getSellUrl() + "/sell/seller/order/list");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                               HttpServletResponse response,
                               Map<String,Object> map){
        Cookie cookie = CookisUtil.get(request,CookisConstant.TOKEN);
        if(cookie!=null){
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));

            CookisUtil.set(response,CookisConstant.TOKEN,null,0);
        }
        map.put("msg", ResultEnum.LOGOUT_SUCCESS.getMessage());
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("common/success", map);
    }
}
