package com.demo.entity;

/**
 * 权限的id和权限名称
 */
public class Permission {

    private Integer id;
    //角色id
    private String r_name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getR_name() {
        return r_name;
    }

    public void setR_name(String r_name) {
        this.r_name = r_name;
    }
}
