package com.demo.controller.pagecontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    private static final Logger Llogger = LoggerFactory.getLogger(LoggerFactory.class);
    @GetMapping(value = "/login")
    public String login(){
        Llogger.info("登陆");
        System.out.println("success");
        return "login";
    }
}
