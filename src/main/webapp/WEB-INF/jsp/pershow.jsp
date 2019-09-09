<jsp:useBean id="user" scope="request" type="com.demo.entity.User"/>
<!DOCTYPE html>
<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" isThreadSafe="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ch">
<head>
    <meta charset="UTF-8">
    <title>permissionShowPage</title>
</head>
</br>
<div align="center">
    回到首页：
    <button style="background-color: darkgray" onclick=javascript:toIndex()>首页</button>
</div>

<body>
<div align="center">
    <form action="${PageContext.request.getContextPath}/testboot/permanage" method="POST">
        <table border="1" style="background-color: dodgerblue">
            <tr>
                <td><input id="c" type="checkbox" name="selectall" onclick="selectAll()"></td>
            </tr>
            <tr>
                <td><input type="checkbox" name="u_id" onclick="toCheck()" value="${user.id}"></td>
                <td>${user.id}</td>
                <td>${user.u_name}</td>
                <td>${user.username}</td>
                <c:forEach items="${ePer}" var="pList" varStatus="ind1">
                    <td>${pList.r_name}</td>
                </c:forEach>
            </tr>
        </table>
        <input type="submit" id="permanage" value="添加权限">
    </form>
</div>
</body>
</html>

<script language="JavaScript" type="text/javascript">
    function toIndex() {
        location="/index";
    }

    /**
     * 全选方法
     */
    function selectAll() {
        var selectall = document.getElementById("c");
        var flag = selectall.checked;
        var checkboxs = document.getElementsByName("u_id");
        var str = "";
        if(flag){
            for(var i = 0; i < checkboxs.length; i++){
                checkboxs[i].checked = true;
                str += checkboxs[i].value + "_";
            }
        }else{
            for(var i = 0; i < checkboxs.length; i++){
                checkboxs[i].checked = false;
            }
        }
    }
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
    }
</script>