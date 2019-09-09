<!DOCTYPE html>
<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" isThreadSafe="false"%>

<html lang="ch">
<head>
    <meta charset="UTF-8">
    <title>registpage</title>
</head>
<div align="center">
    ${msg}
</div>
</br>
<div>
    <button onclick="relogin()" style="align-content: center">重新输入账号密码</button>
</div>
<form action="${PageContext.request.getContextPath}/testboot/adduser" method="post">
    用户名：<input type="text" name="username">
    </br>
    密码：<input type="password" name="u_password">
    </br>
    <button type="onsubmit">注册</button>
    </div>
</form>
<body>

</body>
</html>

<script language="JavaScript" type="text/javascript">
    function relogin() {
        location.href='/login';
    }
</script>