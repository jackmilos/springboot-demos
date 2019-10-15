package com.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.annotation.NoneAuth;
import com.demo.constant.NormalConstant;
import com.demo.entity.JsonData;
import com.demo.entity.Permission;
import com.demo.entity.User;
import com.demo.service.impl.UserServiceImpl;
import com.demo.utils.LoggerUtil;
import com.demo.utils.RedisClient;
import com.demo.utils.token.TokenHelper;
import com.demo.utils.token.TokenModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.HttpCookie;
import java.util.*;

/**
 * 用户控制层接口
 */
@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/testboot", method = {RequestMethod.GET, RequestMethod.POST})
public class UserControl {

    //日志输出
    private static final Logger logger = LoggerFactory.getLogger(LoggerFactory.class);

    //将日志输出到本地
    private static final LoggerUtil loggerUtil = new LoggerUtil();

    //业务逻辑对象
    @Autowired
    private UserServiceImpl userService;

    /**
     * 根据id查询user并返回信息
     * @param id
     * @return
     */
    @RequestMapping(value = "getuser/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable int id, HttpServletResponse response) {
        User user = userService.foundById(id);
        return user;
    }

    /**
     * 根据用户名查找user
     * @param username
     * @return
     */
    @RequestMapping(value = "getbyname/{username}", method = RequestMethod.GET)
    public User getUserName(@PathVariable String username) {
        User user = userService.foundByName(username);
        return user;
    }

    /**
     * 增加user记录
     * @param request
     * @param user
     * @return
     */
    @RequestMapping(value = "/adduser", method = RequestMethod.POST)
    public ModelAndView addUser(HttpServletRequest request, User user) {
        return userService.insert(user);
    }

    /**
     * 根据id删除user
     * @param id
     * @return
     */
    @RequestMapping(value = "deleteuser", method = RequestMethod.GET)
    public String deleteUser(int id) {
        return userService.delete(id);
    }

    /**
     * 修改姓名
     * @param user
     * @return
     */
    @RequestMapping(value = "updateuser", method = RequestMethod.POST)
    public String update(User user) {
        return userService.update(user);
    }

    /**
     * 登陆判断
     * @param session
     * @param user
     * @return
     */
    @NoneAuth
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object isLogin(HttpSession session, User user) {
        return userService.isLogin(user);
    }

    /**
     * 用户列表和权限列表
     * @param request
     * @return
     */

    @RequestMapping(value = "/ulist", method = RequestMethod.GET)
    public ModelAndView lUser(HttpServletRequest request) {
        return userService.lUser(request);
    }

    /**
     * 权限展示
     * @param request
     * @return
     */
    @RequestMapping(value = "/pershow", method = RequestMethod.POST)
    public ModelAndView perShow(HttpServletRequest request,int u_id){
        logger.info("修改权限页面");
        return userService.perShow(request, u_id);
    }

    /**
     * 权限管理
     * @param request
     * @param u_id 用户id
     * @return
     */
    @RequestMapping(value = "/permanage", method = RequestMethod.POST)
    public ModelAndView perManage(HttpServletRequest request,int u_id){
        return userService.perManage(request, u_id);
    }

    /**
     * 增加用户权限
     * @param request
     * @param
     * @return
     */
    @RequestMapping(value = "/addpermission", method = RequestMethod.POST)
    public ModelAndView addPermission(HttpServletRequest request, int u_id, int perid){
        return userService.addPermission(request, u_id, perid);
    }

    /**
     * 批量操作权限页面跳转
     * @param request
     * @return
     */
    @RequestMapping(value = "/batchaddpage", method = RequestMethod.POST)
    public ModelAndView batchAdd(HttpServletRequest request){
        return userService.batchAdd(request);
    }

    /**
     * 批量操作权限页面跳转
     * @param request
     * @return
     */
//    @RequestParam(value ="pid",required = false)int pid
    @RequestMapping(value = "/batchaddper", method = RequestMethod.POST)
    public ModelAndView batchAddPer(HttpServletRequest request,
                                    @RequestBody String  requestText){
        return userService.batchaddper(request, requestText);
    }
}
