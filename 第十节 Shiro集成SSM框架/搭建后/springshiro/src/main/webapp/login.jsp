<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="<c:url value="/actions/security/login"/>" method="post">
    用户名<input type="text" name="username"><br>
    密码<input type="password" name="password"><br>
    <input type="submit" value="提交">
</form>
</body>
</html>
