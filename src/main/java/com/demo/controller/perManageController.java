package com.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class perManageController {
    private static final Logger logger = LoggerFactory.getLogger(LoggerFactory.class);

    @GetMapping(value = "/permanage")
    public String permanage(){
        logger.info("权限管理");
        return "permanage";
    }
}
