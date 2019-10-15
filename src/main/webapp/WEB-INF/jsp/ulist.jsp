<!DOCTYPE html>
<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" isThreadSafe="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html lang="ch">
<script src="/js/jquery-3.4.1.min.js"></script>
<style>
    .pop_con{
        position:absolute;
        /*margin:0 auto;*/
        height: 200px;
        width: 300px;
        text-align: center;
        background-color: darkgray;
    }
    .pop_body{
        position:relative;
        margin: auto;
        height:200px;
        width:300px;
    }
    .abt{
        position:absolute; top:0; right:0; z-index:99;
        background-color: dodgerblue;
    }
</style>
<head>
    <meta charset="UTF-8">
    <title>userlistpage</title>
</head>
</br>
<div align="center">
    欢迎!请:
    <button id="b1" style="background-color: darkgray">返回首页</button>
</div>
<body>
<div align="center">
    <h4>用户列表：</h4>
    <form action="${PageContext.request.getContextPath}/testboot/pershow" method="post">
    <table border="1">
        <tr>
            <th>选择</th>
            <th>账号</th>
            <th>姓名</th>
            <th>年龄</th>
            <th>性别</th>
        </tr>
        <c:forEach items="${user_list}" var="ul" varStatus="ind">
         <tr>
             <td><input type="checkbox" name="u_id" onclick="toCheck()" value="${ul.id}"></td>
<%--            <c:if test="$ind.index==1">checked="checked"</c:if></td>--%>
             <td>${ul.username}</td>
             <td>${ul.u_name}</td>
             <td>${ul.age}</td>
             <td>
                 <c:choose>
                 <c:when test="${ul.sex==1}">男</c:when>
                 <c:when test="${ul.sex==2}">女</c:when>
                     <c:otherwise>未知</c:otherwise>
             </c:choose>
             </td>
         </tr>
        </c:forEach>
        <tr>
            <td>
                <input id="manageper" type="submit" value="查询权限">
            </td>
            <td>
                <input id="batchmanageper" type="button" class="batchadd" value="批量操作">
            </td>
        </tr>
    </table>
    </form>
    <div id="div1" align="center">
    </div>
</div>
<div align="center">
<input type="button" value="修改用户信息" id="btn">
</div>
<div class="pop_body">
    <div class="pop_con">
        <div class="pop_title">
            <h3>系统提示</h3>
            <a href="#" class="abt">×</a>
        </div>
        <div class="pop_detail">
            账号：<input type="text" name="username" id="username">
            </br>
            姓名：<input type="text" name="u_name" id="u_name">
            </br>
            年龄：<input type="text" name="age" id="age">
            </br>
            性别：
            <select id="sex">
                <option value="1">男</option>
                <option value="2">女</option>
                <option value="0">不选择</option>
            </select>
        </div>
        <div class="pop_footer">
            <input type="button" value="确 定" class="confirm">
            <input type="button" value="取 消" class="cancel">
        </div>
    </div>
    <div class="mask"></div>
</div>
<div align="right">
    <input type="button" value="修改用户信息" id="btn2">
</div>
<div class="pop_body2">
    <div class="pop_con">
        <div class="pop_title">
            <h3>系统提示</h3>
            <a href="#" class="abt">×</a>
        </div>
        <div>
            选择要导出的表
            <select id="excel">
                <option value="1">用户详细信息表</option>
            </select>
        </div>
        <div class="pop_footer">
            <input type="button" value="确 定" class="confirm2">
            <input type="button" value="取 消" class="cancel">
        </div>
    </div>
</div>
</body>
</html>

<script language="JavaScript" type="text/javascript">
    function toIndex() {
        location="/index";
    };
    function toCheck() {
        var selectall = document.getElementById("c");
        var checkboxs = document.getElementsByName("u_id");
        var count = 0;
        for(var i = 0; i < checkboxs.length; i++){
            if(checkboxs[i].checked){
                count++;
            }
        }
        if(count == checkboxs.length){
            selectall.checked = true;
        } else{
            selectall.checked = false;
        }
    };
</script>

<script language="JavaScript" type="text/javascript">
    $(document).ready(function() {
        $("#b1").click(function (){
            alert("即将跳转到登录页！")
            location="/index";
        });
    });
</script>

<script>
    $(function(){
        $('.pop_body').hide()

        $('#btn').click(function(){
            //显示弹窗的主界面
            $('.pop_body').show()
            //设置animate动画初始值
            $('.pop_con').css({'top':0,'opacity':0})
            $('.pop_con').animate({'top':'50%','opacity':1})
        })

        //取消按钮和关闭按钮添加事件
        $('.cancel,.pop_title a').click(function(){
            $('.pop_con').animate({'top':0,'opacity':0},function(){
                //隐藏弹窗的主界面
                $('.pop_body').hide()
            })
        })

        $('.confirm').click(function (){
            //js原生写法
            // var username = document.getElementById("username").value;
            //jQuery写法
            var username = $("#username").val();
            var u_name = $("#u_name").val();
            var age = $("#age").val();
            var sex = $("#sex").val();
            $.ajax({
                type: "GET",
                url: "/testboot/updateuser",
                asnyc: true,
                data: {"username": username,"u_name": u_name,"age": age,"sex":sex},
                dataType: "text",
                contentType: "application/json;charset=UTF-8",
                beforeSend: function(request) {
                    //将cookie中的token信息放于请求头中
                    request.setRequestHeader("authStr", $.cookie('authStr'));
                },
                success: function (response) {
                    alert(typeof response+" 姓名："+u_name+"  账号："+username+" 修改成功");
                },
                error: function () {
                    alert(username+"111111");
                }
            })
        })

        $('.pop_body2').hide()

        $('#btn2').click(function(){
            //显示弹窗的主界面
            $('.pop_body2').show()
            //设置animate动画初始值
            $('.pop_con').css({'top':0,'opacity':0})
            $('.pop_con').animate({'top':'50%','opacity':1})
        })

        //取消按钮和关闭按钮添加事件
        $('.cancel,.pop_title a').click(function(){
            $('.pop_con').animate({'top':0,'opacity':0},function(){
                //隐藏弹窗的主界面
                $('.pop_body2').hide()
            })
        })

        $('.confirm2').click(function () {
            $.ajax({
                type: "POST",
                url: "/export/excel",
                beforeSend: function(request) {
                    //将cookie中的token信息放于请求头中
                    request.setRequestHeader("authStr", $.cookie('authStr'));
                },
                success: function (response) {
                    alert(" 表："+$("#excel").val( ) +  "导出成功");
                },
                error: function () {
                    alert("failed to export!");
                }
            })
        })


        $('.batchadd').click(function () {
            location="/testboot/batchaddpage";
        })
    })
</script>