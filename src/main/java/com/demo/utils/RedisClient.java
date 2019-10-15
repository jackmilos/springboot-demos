package com.demo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author jack
 * redis操作类
 */
@Component
public class RedisClient {
    public static final long TOKEN_EXPIRES_SECOND = 50000;

    @Autowired
    private StringRedisTemplate redisTpl;

    /**
     * 向redis中设置
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, String value){
        boolean result = false;
        try{
            redisTpl.opsForValue().set(key, value);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 向redis中设置过期时间
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean set(String key, String value, long time){
        boolean result = true;
        try{
            redisTpl.opsForValue().set(key, value);
            expire(key, time);

        }catch (Exception e){
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    /**
     * 获取redis中的值
     * @param key
     * @return
     */
    public String get(String key){
        String result = null;
        try{
            result = redisTpl.opsForValue().get(key);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 设置key的过期时间
     * @param key
     * @param time
     * @return
     */
    public boolean expire(String key, long time){
        boolean result = false;
        try{
            if(time > 0){
                redisTpl.expire(key, time, TimeUnit.SECONDS);
                result = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据key删除对应value
     * @param key
     * @return
     */
    public boolean remove(String key){
        boolean result = false;
        try{
            redisTpl.delete(key);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
