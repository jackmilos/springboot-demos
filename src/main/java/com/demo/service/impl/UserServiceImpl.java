package com.demo.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.annotation.NoneAuth;
import com.demo.constant.MessageConstant;
import com.demo.entity.JsonData;
import com.demo.entity.Permission;
import com.demo.entity.User;
import com.demo.dao.mapper.UserMapper;
import com.demo.enums.HttpStatusEnum;
import com.demo.service.UserService;
import com.demo.utils.token.TokenHelper;
import com.demo.utils.token.TokenModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.security.*;

/**
 * @author jack
 * 业务层
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * 数据访问对象
     */
    @Autowired
    UserMapper userMapper;

    /**
     * token辅助对象
     */
    @Autowired
    private TokenHelper tokenHelper;

    /**
     * 通过id查找用户
     * @param id
     * @return
     */
    @Override
    public User foundById(int id){
        return userMapper.foundById(id);
    }

    /**
     * 通过姓名查找用户
     * @param name
     * @return
     */
    @Override
    public User foundByName(String name){return userMapper.foundByName(name);}

    /**
     * 插入一条user数据，输入为username和u_password
     * */
    @Override
    public ModelAndView insert(User user){
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
        userMapper.insert(user);
        System.out.println("add successed!");
        mv.addObject("addmsg", "注册成功！");
        mv.setViewName("/index");
        return mv;
    }

    /**
     * 控制登陆判断
     * @param user
     * @return
     */
    @NoneAuth
    @Override
    public Object isLogin(User user) {
        System.out.println(user.getUsername());
        if (user == null) {
            System.out.println("输入用户信息为空");
        }
        ModelAndView mv = new ModelAndView();
        User userInSql = this.foundByName(user.getUsername());
        if (userInSql.getUsername() == null) {
            mv.addObject("msg", "此用户不存在,请先注册");
            mv.setViewName("/regist");
            System.out.println("username not exist");
            return JsonData.buildError(401,"not exist");
        }else if(!userInSql.getU_password().equals(user.getU_password())){
            mv.addObject("msg","您输入的密码有误，请重新输入");
            mv.setViewName("/login");
            return JsonData.buildError(400,"wrong pwd");
        }
        TokenModel model = tokenHelper.create(userInSql.getId());
        return JsonData.buildSuccess(model);
    }

    /**
     * 用户列表视图
     * @param request
     * @return
     */
    @Override
    public ModelAndView lUser(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        List<User> user_list = this.listUser();
        mv.setViewName("/ulist");
        request.setAttribute("user_list", user_list);
        return mv;
    }

    /**
     * 删除方法，以id为引
     * @param id
     * @return
     */
    @Override
    public String delete(int id){
        return userMapper.delete(id)>=1?"delete succeed!":"delete failed!";
    }

    /**
     * 更改字段
     * @param user
     * @return
     */
    @Override
    public String update(User user){
        return userMapper.update(user)>=1?"update succeed!":"update failed!";
    }

    /**
     * 获取所有用户，以List返回
     * @return
     */
    @Override
    public List<User> listUser(){
        return userMapper.listUser();
    }


    /**
     * 用户可添加权限展示视图
     * @param request
     * @param u_id
     * @return
     */
    @Override
    public ModelAndView perManage(HttpServletRequest request, int u_id) {
        ModelAndView mv = new ModelAndView();
        List<Permission> pList = this.choosePerList(u_id);
        mv.setViewName("/permanage");
        User user = this.foundById(u_id);
        request.setAttribute("user",user);
        request.setAttribute("addablePer",pList);
        return mv;
    }

    /**
     * 权限展示
     * @param request
     * @return
     */
    @Override
    public ModelAndView perShow(HttpServletRequest request, int u_id) {
        ModelAndView mv = new ModelAndView();
        List<User> user_list = this.listUser();
        mv.setViewName("/pershow");
        request.setAttribute("user_list", user_list);
        User user = this.foundById(u_id);
        request.setAttribute("user",user);
        List<Permission> ePer = this.existPerList(u_id);
        request.setAttribute("ePer",ePer);
        return mv;
    }

    /**
     * 权限名列表
     * @return
     */
    @Override
    public List<Permission> perList(){
        return userMapper.perList();
    }

    /**
     * 添加权限视图
     * @param request
     * @param u_id
     * @param perid
     * @return
     */
    @Override
    public ModelAndView addPermission(HttpServletRequest request, int u_id, int perid) {
        ModelAndView mv = new ModelAndView();
        List<Permission> pList = this.choosePerList(perid);
        int addp = this.addPer(u_id,perid);
        request.setAttribute("allPer",pList);
        mv.setViewName("index");
        return mv;
    }

    /**
     * 根据用户id查询该用户的权限列表
     * @return
     */
    @Override
    public List<Permission> existPerList(int id){return userMapper.existPerList(id);}

    /**
     * 添加权限（通过用户角色表添加）
     * @param u_id
     * @param p_id
     * @return
     */
    @Override
    public int addPer(int u_id,int p_id){
        return userMapper.addPer(u_id,p_id);
    }

    /**
     * 批量
     * @param u_rids
     * @return
     */
    @Override
    public String addPerBatch(List<Map> u_rids){return userMapper.addPerBatch(u_rids);}

    /**
     * 批量添加视图
     * @param request
     * @return
     */
    @Override
    public ModelAndView batchAdd(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        List<User> user_list = this.listUser();
        List<Permission> per_list = this.perList();
        request.setAttribute("user_list", user_list);
        request.setAttribute("per_list", per_list);
        mv.setViewName("/batchaddper");
        return mv;
    }

    /**
     * 批量添加权限视图
     * @param request
     * @param requestText
     * @return
     */
    @Override
    public ModelAndView batchaddper(HttpServletRequest request,@RequestBody String requestText) {
        JSONObject obj = JSONObject.parseObject(requestText);
        JSONArray userList = obj.getJSONArray("userList");
        String pid = obj.getString("pid");

        //设置页面所需要的视图和数据
        ModelAndView mv = new ModelAndView();
        List<User> user_list = this.listUser();
        List<Permission> per_list = this.perList();
        request.setAttribute("user_list", user_list);
        request.setAttribute("per_list", per_list);
        mv.setViewName("/ulist");

        //根据前端输入值来解析并向数据库中插入相应的角色
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

    /**
     * 选择要添加的权限
     * @param id
     * @return
     */
    @Override
    public List<Permission> choosePerList(int id){return userMapper.choosePerList(id);}
}
