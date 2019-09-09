<!DOCTYPE html>
<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" isThreadSafe="false"%>

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
<form action="${PageContext.request.getContextPath}/testboot/login" method="post">
    用户名：<input type="text" name="username">
    </br>
    密码：<input type="password" name="u_password">
    </br>
    <button type="onsubmit">登陆</button>
</form>
</body>
</html>

<script language="JavaScript" type="text/javascript">
    function toregist() {
        location.href='/regist';
    }
</script>