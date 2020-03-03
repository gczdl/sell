package com.mooc.aspect;

import com.mooc.constant.CookisConstant;
import com.mooc.constant.RedisConstant;
import com.mooc.exception.SellerAuthorizeException;
import com.mooc.utils.CookisUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {
    @Autowired
    StringRedisTemplate redisTemplate;
    @Pointcut("execution(public * com.mooc.controller.Seller*.*(..))" + "&& !execution(public * com.mooc.controller.SellerUserController.*(..))")
    public void verify() {}

    @Before("verify()")
    public void doVerify(){
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Cookie cookie = CookisUtil.get(request, CookisConstant.TOKEN);
        if(cookie==null){
            log.warn("【登录校验】Cookie中查不到token");
            throw new SellerAuthorizeException();
        }
        //查询redis是否存在这个Cookis
        String tokenvalue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
        if(StringUtils.isEmpty(tokenvalue)){
            log.warn("【登录校验】Redis中查不到token");
            throw new SellerAuthorizeException();
        }
    }
}
