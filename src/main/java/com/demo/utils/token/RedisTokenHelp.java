package com.demo.utils.token;

import com.demo.utils.RedisClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author jack
 * redis和token交互类，实现创建token到redis缓存中
 */
@Component
public class RedisTokenHelp implements TokenHelper {

    @Autowired
    private RedisClient redisClient;

    /**
     * 创建新的缓存并放到本地的redis中
     * @param id
     * @return
     */
    @Override
    public TokenModel create(Integer id) {
        String token = UUID.randomUUID().toString().replace("-","");
        TokenModel mode = new TokenModel(id, token);
        Boolean flag = redisClient.set(id == null ? null : String.valueOf(id), token, RedisClient.TOKEN_EXPIRES_SECOND);
        System.out.println(flag?"saved":"failed");
        return mode;
    }

    /**
     * 验证是否登陆
     * @param model
     * @return
     */
    @Override
    public boolean check(TokenModel model) {
        boolean result = false;
        if(model != null){
            String userId = model.getUserId().toString();
            String token = model.getToken();
            String authenticatedToken = redisClient.get(userId);
            if(authenticatedToken != null && authenticatedToken.equals(token)){
                redisClient.expire(userId, RedisClient.TOKEN_EXPIRES_SECOND);
                result = true;
            }
        }
        return result;
    }

    /**
     * 从token中取出用户信息
     * @param authStr
     * @return
     */
    @Override
    public TokenModel get(String authStr) {
        TokenModel model = null;
        if(StringUtils.isNotEmpty(authStr)){
            String[] modelArr = authStr.split("_");
            if(modelArr.length == 2){
                int userId = Integer.parseInt(modelArr[0]);
                String token = modelArr[1];
                model = new TokenModel(userId, token);
            }
        }
        return model;
    }

    /**
     * 删除redis中的缓存，用于登出功能
     * @param id
     * @return
     */
    @Override
    public boolean delete(Integer id) {

        return redisClient.remove(id == null ? null : String.valueOf(id));
    }
}
