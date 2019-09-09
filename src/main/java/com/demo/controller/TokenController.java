package com.demo.controller;

import com.demo.annotation.NoneAuth;
import com.demo.constant.MessageConstant;
import com.demo.constant.NormalConstant;
import com.demo.dao.mapper.UserMapper;
import com.demo.entity.JsonData;
import com.demo.entity.User;
import com.demo.enums.HttpStatusEnum;
import com.demo.utils.token.TokenHelper;
import com.demo.utils.token.TokenModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jack
 * token逻辑判断
 */
@RestController
@RequestMapping("/token")
public class TokenController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TokenHelper tokenHelper;

    /**
     * 登陆拦截并判断是否已登陆
     * @param username
     * @param u_password
     * @return
     */
    @NoneAuth
    @GetMapping
    public Object login(String username,String u_password){
        User user = userMapper.foundByName(username);
        if(user == null || !user.getU_password().equals(u_password)){
            return JsonData.buildError(HttpStatusEnum.NOT_FOUND.getCode(), MessageConstant.USERNAME_OR_PASSWORD_ERROR);
        }
        TokenModel model = tokenHelper.create(user.getId());
        return JsonData.buildSuccess(model);
    }

    /**
     * 登出操作处理方法，返回执行成功码和信息
     * @param request
     * @return
     */
    @DeleteMapping
    public Object logout(HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute(NormalConstant.CURRENT_USER_ID);
        if(userId != null){
            tokenHelper.delete(userId);
        }
        return JsonData.buildSuccess();
    }
}
