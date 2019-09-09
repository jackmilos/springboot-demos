package com.demo.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @author jack
 * JSON字符处理类，包括JSON字符对象间的转换方法
 */
public class JsonUtils {
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 对象转字符
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String obj2String(T obj){
        if(obj == null){
            return null;
        }
        try{
            return obj instanceof String ? (String)obj : objectMapper.writeValueAsString(obj);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 字符转对象
     * @param str
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T String2Obj(String str,Class<T> clazz){
        if(StringUtils.isEmpty(str) || clazz == null){
            return null;
        }
        try{
            return clazz.equals(String.class) ? (T) str : objectMapper.readValue(str,clazz);
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
