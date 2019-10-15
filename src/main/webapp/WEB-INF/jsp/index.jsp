<!DOCTYPE html>
<%@ page contentType="text/html;charset=utf-8"%>
<script src="/js/jquery-3.4.1.min.js"></script>
<script src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.js"></script>

<html lang="ch">
<head>
    <meta charset="UTF-8">
    <title>nothing to do~</title>
</head>
<div align="center">
    ${addmsg}
</div>
</br>
<div align="center">
    欢迎!请:
    <button onclick=javascript:tologin()>登陆</button>
    <button onclick=javascript:userlist()>用户列表</button>
    <input type="button" value="logout" onclick="logout()">
</div>
<body>

</body>
</html>

<script language="JavaScript" type="text/javascript">
    function tologin() {
        location="/login";
    };
    function userlist() {
        console.log("ul used");
        location="/testboot/ulist";
    };
    function logout() {
        $.ajax({
            url: "/token",
            dataType: "json",
            type: "DELETE",
            beforeSend: function (request) {
                //将cookie中的token信息放于请求头中
                request.setRequestHeader("authStr", $.cookie('authStr'));
            },
            success: function (res) {
                console.log(res);
            }
        });
    }
</script>