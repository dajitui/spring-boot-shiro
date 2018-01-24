<%--
  Created by IntelliJ IDEA.
  User: yanshen
  Date: 2018/1/21
  Time: 22:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<head>
    <title>Title</title>
</head>
<body>
登录页面
<form action="/loginuser">
<p>用户名：<input name="name"></p>
<p>密码：<input name="pws" type="password"></p>
    <input type="checkbox" name="rememberMe" />记住我<br>
    验证码：<input type="text" name="gifCode" id="gifCode"/>
    <img id="pic" alt="验证码" src="/getGifCode" onclick="show()"><br>
    <input type="submit" value="提交">
</form>
<button type="button" onclick="out()">退出</button>
</body>
<script>
    function out() {
        $.get({
            url:"/logOut",
            success:function (result){
                alert("已经退出");
            }
        });
    }

    function show(){
        document.getElementById("pic").src="/getGifCode?"+Math.random();
    }
</script>
</html>
