package com.demo.controller.pagecontroller;

import com.demo.annotation.NoneAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UlistController {

    private static final Logger Ulogger = LoggerFactory.getLogger(LoggerFactory.class);
    @GetMapping(value = "/ulist")
    public String ulist(){
        Ulogger.info("用户列表");
        return "ulist";
    }
}