package com.demo.service;


import com.demo.utils.ExportUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class ExcelThreadService implements Runnable{

    @Autowired
    private ExportUtil exportUtil;
        @Override
        public void run() {
            exportUtil.ExportExcel();
        }
    }
