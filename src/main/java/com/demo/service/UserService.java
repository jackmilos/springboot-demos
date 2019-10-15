package com.demo.service;

import com.demo.entity.Permission;
import com.demo.entity.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface UserService {

    User foundById(int id);

    User foundByName(String name);

    ModelAndView insert(User user);

    Object isLogin(User user);

    //用户列表视图
    ModelAndView lUser(HttpServletRequest request);

    //删除用户
    String delete(int id);

    //修改用户
    String update(User user);

    //获取所有用户
    List<User> listUser();

    //权限管理页面逻辑
    ModelAndView perManage(HttpServletRequest request,int u_id);

    //展示权限视图
    ModelAndView perShow(HttpServletRequest request, int id);

    //获取所有权限
    List<Permission> perList();

    //单个权限添加视图
    ModelAndView addPermission(HttpServletRequest request, int u_id, int perid);

    //已存在的用户权限查询
    List<Permission> existPerList(int id);

    //单个权限添加
    int addPer(int u_id,int p_id);

    //添加权限列表
    List<Permission> choosePerList(int id);

    //批量权限添加
    String addPerBatch(List<Map> u_rids);

    //批量选择视图
    ModelAndView batchAdd(HttpServletRequest request);

    //批量权限添加视图
    ModelAndView batchaddper(HttpServletRequest request,
                             @RequestBody String  requestText);
}
