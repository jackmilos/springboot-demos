<!DOCTYPE html>
<%@ page language="java" import="java.util.*"
         pageEncoding="UTF-8" isThreadSafe="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script src="/js/jquery-3.4.1.min.js"></script>

<html lang="ch">
<head>
    <meta charset="UTF-8">
    <title>permission Batch Manage Page</title>
</head>
</br>
<div align="center">
    回到首页：
    <button style="background-color: darkgray" onclick=javascript:toIndex()>首页</button>
</div>

<body>
<div align="center">
    <form action="${PageContext.request.getContextPath}/testboot/batchaddper" method="POST">
        <table border="1" style="background-color: dodgerblue">
            <tr>
                <td><input id="c" type="checkbox" name="selectall" onclick="selectAll()">全选</td>
            </tr>
            <tr><c:forEach items="${user_list}" var="uList" varStatus="ind"></tr>
            <tr><td><input type="checkbox" name="u_list" onclick="toCheck()" value="${uList.id}">
                    <c:if test="$ind.index==1">checked="checked"</c:if></td>
                <td style="background-color: deepskyblue">${uList.id}</td>
                <td style="background-color: darkgrey">${uList.u_name}</td>
                <td style="background-color: gray">${uList.username}</td>
            </c:forEach>
            </tr>
        </table>
        <select>
            <c:forEach items="${per_list}" var="plist">
                <option id="perid${plist.id}" value="${plist.id}">${plist.r_name}</option>
            </c:forEach>
        </select>
        <input type="button" id="submit"  value="添加权限">
    </form>
</div>
</body>
</html>

<script language="JavaScript" type="text/javascript">

    $("#submit").click(function(){
        var url = $('form').attr("action");
        var userList = new Array();
        $("input:checkbox:checked").each(function(i){
            if($(this).val()=="on"){
            }else{
                userList[i] = $(this).val();
            }
        });
        var pid = document.getElementsByTagName('select')[0].value;
         var users = {
            "pid":pid,
             "userList": userList
         };
        $.ajax({
            url: url,
            type: 'post',
            datatype: "json",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(users),
            success: function (s) {
                alert("s",s);
            },
            error: function (e) {
                alert(e.responseText);
            }
        });
    })

    function toIndex() {
        location="/index";
    }

    /**
     * 全选方法
     */
    function selectAll() {
        var selectall = document.getElementById("c");
        var flag = selectall.checked;
        var checkboxs = document.getElementsByName("u_list");
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
        var checkboxs = document.getElementsByName("u_list");
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