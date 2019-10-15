<!DOCTYPE html>
<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" isThreadSafe="false"%>
<script src="/js/jquery-3.4.1.min.js"></script>
<script src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.js"></script>

<html lang="ch">
<head>
    <meta charset="UTF-8">
    <title>welcome! Please Login</title>
</head>
<div align="center">
    ${msg}
</div>
</br>
<body>
<div align="center">
    <button onclick=javascript:toregist()>regist now</button>
</div>
<form>
    用户名：<input type="text" name="username" id="username">
    </br>
    密码：<input type="password" name="u_password" id="password">
    </br>
    <button type="button" value="login" onclick="login()">登陆</button>
</form>
</body>
</html>

<script language="JavaScript" type="text/javascript">
    function toregist() {
        location.href='/regist';
    }
    function login() {
    $.ajax({
        url: "/testboot/login",
        dataType: "json",
        data: {'username': $("#username").val(), 'u_password': $("#password").val()},
        type: "POST",
        success: function (res) {
            console.log(res);

            var authStr = res.data.userId + "_" + res.data.token;
            //把生成的token放在cookie中
            $.cookie("authStr", authStr);
            console.log(authStr);
            window.location.href = "/index";
            alert(res.msg);
        }
    })
}
</script>