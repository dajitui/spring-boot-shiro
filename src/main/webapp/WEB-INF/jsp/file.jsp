<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Insert title here</title>
</head>
<body>
<h1 th:inlines="text">文件上传</h1>
<form action="fileupload" method="post" enctype="multipart/form-data">
    <p>选择文件: <input type="file" name="filename"/></p>
    <p><input type="submit" value="提交"/></p>
</form>
</body>
</html>