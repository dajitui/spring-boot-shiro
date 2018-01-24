<%--
  Created by IntelliJ IDEA.
  User: yanshen
  Date: 2018/1/17
  Time: 0:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1 th:inlines="text">文件上传</h1>
<form action="multifileUpload" method="post" enctype="multipart/form-data" >
    <p>选择文件1: <input type="file" name="filename"/></p>
    <p>选择文件2: <input type="file" name="filename"/></p>
    <p>选择文件3: <input type="file" name="filename"/></p>
    <p><input type="submit" value="提交"/></p>
</form>
</body>
</html>
