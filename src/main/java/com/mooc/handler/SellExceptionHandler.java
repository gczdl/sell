package com.mooc.handler;

import com.mooc.config.ProjectUrlConfig;
import com.mooc.exception.SellerAuthorizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class SellExceptionHandler {

    @Autowired
    private ProjectUrlConfig projectUrl;

    @ExceptionHandler(value = SellerAuthorizeException.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handlerAuthorizeException(){
        return new ModelAndView("redirect:" + projectUrl.getSellUrl() + "/sell/seller/login");
    }
}
