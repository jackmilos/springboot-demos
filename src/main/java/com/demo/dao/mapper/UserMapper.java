package com.demo.dao.mapper;

import com.demo.entity.Permission;
import com.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * dao层，访问数据
 */
@Repository
@Mapper
public interface UserMapper {

    /**
     * 通过id查找user
     * @param id
     * @return
     */
    User foundById(@Param("id")int id);  /*查询方法接口,通过id查询*/

    /**
     * 通过姓名查找user
     * @param name
     * @return
     */
    User foundByName(String name);

    /**
     * 添加方法
     * @param user
     * @return
     */
    int insert(User user);

    /**
     * 删除方法
     * @param id
     * @return
     */
    int delete(int id);

    /**
     * 修改方法
     * @param user
     * @return
     */
    int update(User user);

    /**
     * 用户列表
     * @return
     */
    List<User> listUser();

    /**
     * 选中用户的权限管理
     * @param id
     * @return
     */
    List<Permission> existPerList(int id);

    /**
     * 权限名列表
     * @return
     */
    List<Permission> perList();

    /**
     * 添加权限
     * @param u_id
     * @param p_id
     * @return
     */
    int addPer(int u_id,int p_id);

    /**
     * 查询用户所没有的权限
     * @param id
     * @return
     */
    List<Permission> choosePerList(int id);

    /**
     * 批量添加权限
     * @param u_rids
     * @return
     */
    String addPerBatch(List<Map> u_rids);
}
