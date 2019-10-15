package com.demo.controller;

import com.demo.annotation.NoneAuth;
import com.demo.entity.JsonData;
import com.demo.utils.ExportUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 导出表格方法
 */
@RestController
@EnableAutoConfiguration
@RequestMapping(value = "export")
public class ExportController {

    @Autowired
    private ExportUtil exportUtil;
    @NoneAuth
    @RequestMapping(value = "excel",method = RequestMethod.GET,produces = "application/zip;charset=utf-8")
    public void export(HttpServletResponse response) throws InterruptedException {
        //多线程下载
//        ExcelThreadService threadService = new ExcelThreadService();
//        Thread t1 = new Thread(threadService);
//        Thread thread = new Thread(() -> exportUtil.ExportExcel());
//        thread.start();

        //导出文件流供下载
        exportUtil.exportExcel(response);
    }
}