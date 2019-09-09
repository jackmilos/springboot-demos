<!DOCTYPE html>
<%@ page contentType="text/html;charset=utf-8"%>

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
</div>
<body>

</body>
</html>

<script language="JavaScript" type="text/javascript">
    function tologin() {
        location="/login";
    }
</script>