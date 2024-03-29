package com.demo.utils.token;

import jdk.nashorn.internal.parser.Token;

/**
 * @author jack
 * Token的辅助接口
 * 在RedisTokenHelper中实现其方法
 */
public interface TokenHelper {
    /**
     * 根据用户id生成token
     * @param id
     * @return
     */
    TokenModel create(Integer id);

    /**
     * 检查是否存在该token
     * @param model
     * @return
     */
    boolean check(TokenModel model);

    /**
     * 从前端返回的校验字符串中获取token
     * @param authStr
     * @return
     */
    TokenModel get(String authStr);

    /**
     * 根据用户id删除token
     * @param id
     * @return
     */
    boolean delete(Integer id);
}
