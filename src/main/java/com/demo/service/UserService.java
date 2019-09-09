package com.demo.service;

import com.demo.entity.Permission;
import com.demo.entity.User;
import com.demo.dao.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * @author jack
 * 业务层
 */
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    /**
     * 通过id查找用户
     * @param id
     * @return
     */
    public User foundById(int id){
        return userMapper.foundById(id);
    }

    /**
     * 通过姓名查找用户
     * @param name
     * @return
     */
    public User foundByName(String name){return userMapper.foundByName(name);}

    /**
     * 插入一条user数据，输入为username和u_password
     * */
    public ModelAndView insert(User user){
        ModelAndView Imv = new ModelAndView();
        userMapper.insert(user);
        return Imv;
    }

    /**
     * 删除方法，以id为引
     * @param id
     * @return
     */
    public int delete(int id){
        return userMapper.delete(id);
    }

    /**
     * 更改字段
     * @param user
     * @return
     */
    public int update(User user){
        return userMapper.update(user);
    }

    /**
     * 获取所有用户，以List返回
     * @return
     */
    public List<User> listUser(){
        return userMapper.listUser();
    }

    /**
     * 权限名列表
     * @return
     */
    public List<Permission> perList(){
        return userMapper.perList();
    }

    /**
     * 根据用户id查询该用户的权限列表
     * @return
     */
    public List<Permission> existPerList(int id){return userMapper.existPerList(id);}

    /**
     * 添加权限（通过用户角色表添加）
     * @param u_id
     * @param p_id
     * @return
     */
    public int addPer(int u_id,int p_id){
        return userMapper.addPer(u_id,p_id);
    }

    /**
     * 批量
     * @param u_rids
     * @return
     */
    public String addPerBatch(List<Map> u_rids){return userMapper.addPerBatch(u_rids);}
    /**
     * 选择要添加的权限
     * @param id
     * @return
     */
    public List<Permission> choosePerList(int id){return userMapper.choosePerList(id);}
}
