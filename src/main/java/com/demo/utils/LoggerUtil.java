package com.demo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtil {
    /**
     * 输出运行信息
     * @param clazz
     * @param infoData
     */
    public static void printInfo(Class clazz,String infoData){
        //调用第三方
        Logger logger = LoggerFactory.getLogger(clazz);
        //打印到文件、控制台
        logger.info(infoData);
    }
}
