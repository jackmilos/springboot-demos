package com.demo.controller;

import com.demo.annotation.NoneAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistController {
    private static final Logger Rlogger = LoggerFactory.getLogger(LoggerFactory.class);


    @NoneAuth
    //    @RequestMapping(value = "/login",method = RequestMethod.GET)
    @GetMapping(value = "/regist")
//    @ResponseBody
    public String regist(){
        Rlogger.info("注册");
        return "regist";
    }
}