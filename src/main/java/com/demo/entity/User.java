package com.demo.entity;

public class User {
    private Integer id;
    private String u_name;
    private int sex;
    private int age;
    private String username;
    private String u_password;

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUsername(){
        return username;
    }

    public void setusername(String username){
        this.username = username; }

    public Integer getId() {
        return id; }

    public void setId(Integer id) {
        this.id = id; }

    public String getU_password() {
        return u_password;
    }

    public void setU_password(String password) {
        this.u_password = password;
    }

    @Override
    public String toString(){
        return "User:{"+
                "id:"+id+"username:"+username;
    }
}
