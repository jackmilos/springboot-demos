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

    ModelAndView lUser(HttpServletRequest request);

    String delete(int id);

    String update(User user);

    List<User> listUser();

    ModelAndView perShow(HttpServletRequest request, int id);

    List<Permission> perList();

    List<Permission> existPerList(int id);

    int addPer(int u_id,int p_id);

    List<Permission> choosePerList(int id);

    String addPerBatch(List<Map> u_rids);

    ModelAndView batchaddper(HttpServletRequest request,
                             @RequestBody String  requestText);
}
