package com.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.entity.Permission;
import com.demo.entity.User;
import com.demo.service.UserService;
import com.demo.utils.LoggerUtil;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/testboot", method = {RequestMethod.GET, RequestMethod.POST})
public class UserControl {
    private static final Logger logger = LoggerFactory.getLogger(LoggerFactory.class);

    private static final LoggerUtil loggerUtil = new LoggerUtil();

    @Autowired
    private UserService userService;

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
        ModelAndView mv = new ModelAndView();
        if (user.getUsername().isEmpty()) {
            mv.addObject("addmsg", "用户名不能为空");
            mv.setViewName("/index");
            return mv;
        } else if (user.getU_password().length() < 6) {
            mv.addObject("addmsg", "密码长度不能少于6位字符!");
            mv.setViewName("/index");
            return mv;
        }
        userService.insert(user);
        System.out.println("add successed!");
        mv.addObject("addmsg", "注册成功！");
        mv.setViewName("/index");
        return mv;
    }

    /**
     * 根据id删除user
     * @param id
     * @return
     */
    @RequestMapping(value = "deleteuser", method = RequestMethod.GET)
    public String deleteUser(int id) {
        int result = userService.delete(id);
        if (result >= 1) {
            return "delete successed";
        } else {
            return "delete failed";
        }
    }

    /**
     * 修改姓名
     * @param user
     * @return
     */
    @RequestMapping(value = "updateuser", method = RequestMethod.POST)
    public String update(User user) {
        int result = userService.update(user);
        if (result >= 1) {
            return "update successed";
        } else {
            return "update failed";
        }
    }

    /**
     * 登陆判断
     * @param request
     * @param session
     * @param user
     * @param model
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView isLogin(HttpServletRequest request, HttpSession session, User user, Model model) {
        if (user == null) {
            logger.error("输入用户信息为空");
        }
        ModelAndView mv = new ModelAndView();
        User userInSql = userService.foundByName(user.getUsername());
        if (userInSql.getUsername() == null) {
            mv.addObject("msg", "此用户不存在,请先注册");
            mv.setViewName("/regist");
            return mv;
        }else if(userInSql.getU_password() != user.getU_password()){
            mv.addObject("msg","您输入的密码有误，请重新输入");
            mv.setViewName("/login");
            return mv;
        }
        mv.setViewName("/index");
        return mv;
    }

    /**
     * 用户列表和权限列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/ulist", method = RequestMethod.GET)
    public ModelAndView lUser(HttpServletRequest request) {
        ModelAndView Lmv = new ModelAndView();
        List<User> user_list = userService.listUser();
        Lmv.setViewName("/ulist");
        request.setAttribute("user_list", user_list);
        //输出日志到本地文档
        loggerUtil.printInfo(LoggerUtil.class,"用户列表查询");
        logger.info("用户列表界面");
        return Lmv;
    }

    /**
     * 权限展示
     * @param request
     * @return
     */
    @RequestMapping(value = "/pershow", method = RequestMethod.POST)
    public ModelAndView perShow(HttpServletRequest request,int u_id){
        ModelAndView mv = new ModelAndView();
        List<User> user_list = userService.listUser();
        mv.setViewName("/pershow");
        request.setAttribute("user_list", user_list);
        User user = userService.foundById(u_id);
        request.setAttribute("user",user);
        List<Permission> ePer = userService.existPerList(u_id);
        request.setAttribute("ePer",ePer);
        logger.info("修改权限页面");
        return mv;
    }

    /**
     * 权限管理
     * @param request
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
        System.out.println(requestText.toString());
        JSONObject obj = JSONObject.parseObject(requestText);
        JSONArray userList = obj.getJSONArray("userList");
        String pid = obj.getString("pid");

        ModelAndView mv = new ModelAndView();
        List<User> user_list = userService.listUser();
        List<Permission> per_list = userService.perList();
        request.setAttribute("user_list", user_list);
        request.setAttribute("per_list", per_list);
        mv.setViewName("/ulist");
        String[] arr = userList.toArray(new String[userList.size()]);
        List<Map> u_rIds= new ArrayList<>();
        for(int i=0; i<arr.length ; i++){
            Map<String,Integer> map = new HashMap<String, Integer>();
            map.put("u_id",Integer.parseInt(arr[i]));
            map.put("p_id",Integer.parseInt(pid));
            u_rIds.add(map);
        }
        System.out.println("插入成功");
        return mv;
    }
}
