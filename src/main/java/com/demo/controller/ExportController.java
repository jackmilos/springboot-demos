package com.demo.controller;

import com.demo.annotation.NoneAuth;
import com.demo.service.ExcelThreadService;
import com.demo.utils.ExportUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@RequestMapping(value = "export")
public class ExportController {
    @Autowired
    private ExportUtil exportUtil;
    @NoneAuth
    @RequestMapping(value = "excel",method = RequestMethod.POST)
    public void export() throws InterruptedException {
       // ExcelThreadService threadService = new ExcelThreadService();
        //Thread t1 = new Thread(threadService);
        Thread thread = new Thread(() -> exportUtil.ExportExcel());
        thread.start();
    }
}