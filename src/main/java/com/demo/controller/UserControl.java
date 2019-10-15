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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.HttpCookie;
import java.util.*;

@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/testboot", method = {RequestMethod.GET, RequestMethod.POST})
public class UserControl {
    private static final Logger logger = LoggerFactory.getLogger(LoggerFactory.class);

    private static final LoggerUtil loggerUtil = new LoggerUtil();

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RedisClient redisClient;

    /**
     * 根据id查询user并返回信息
     * @param id
     * @return
     */
    @RequestMapping(value = "getuser/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable int id) {
        User user = userService.foundById(id);
        return user;
    }

    /**
     * 根据用户名查找user
     * @param username
     * @return
     */
    @RequestMapping(value = "getbyname/{username}", method = RequestMethod.GET)
    public User GetUsername(@PathVariable String username) {
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

    @NoneAuth
    @RequestMapping(value = "/ulist", method = RequestMethod.GET)
    public ModelAndView lUser(HttpServletRequest request) {
        //输出日志到本地文档
        Cookie[] cookie = request.getCookies();
        HttpSession session =request.getSession();
        if(!session.getId().equals("")){
            System.out.println(session.getId());
        }
        for (int i = 0; i < cookie.length; i++) {
            System.out.println("name:" + cookie[i].getName()+", value: " + cookie[i].getValue());
            if(cookie[i].getName().equals(NormalConstant.AUTHORIZATION) && !cookie[i].getValue().isEmpty()){
                logger.info("已登陆");
                return userService.lUser(request);
            }
        }
            ModelAndView mv = new ModelAndView();
            mv.setViewName("/login");
            return mv;
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
        ModelAndView mv = new ModelAndView();
        List<Permission> pList = userService.choosePerList(u_id);
        mv.setViewName("/permanage");
        User user = userService.foundById(u_id);
        request.setAttribute("user",user);
        request.setAttribute("addablePer",pList);
        logger.info("修改权限页面");
        return mv;
    }

    /**
     * 增加用户权限
     * @param request
     * @param
     * @return
     */
    @RequestMapping(value = "/addpermission", method = RequestMethod.POST)
    public ModelAndView addPermission(HttpServletRequest request, int u_id, int perid){
        ModelAndView mv = new ModelAndView();
        List<Permission> pList = userService.choosePerList(perid);
        int addp = userService.addPer(u_id,perid);
        request.setAttribute("allPer",pList);
        mv.setViewName("index");
        return mv;
    }

    /**
     * 批量操作权限页面跳转
     * @param request
     * @return
     */
    @RequestMapping(value = "/batchaddpage", method = RequestMethod.POST)
    public ModelAndView batchadd(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        List<User> user_list = userService.listUser();
        List<Permission> per_list = userService.perList();
        request.setAttribute("user_list", user_list);
        request.setAttribute("per_list", per_list);
        mv.setViewName("/batchaddper");
        return mv;
    }

    /**
     * 批量操作权限页面跳转
     * @param request
     * @return
     */
//    @RequestParam(value ="pid",required = false)int pid
    @RequestMapping(value = "/batchaddper", method = RequestMethod.POST)
    public ModelAndView batchaddper(HttpServletRequest request,
                    @RequestBody String  requestText){
        return userService.batchaddper(request, requestText);
    }
}
