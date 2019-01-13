<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<h6>这里是login.jsp</h6>
<a href="<c:url value="/authen?username=jay&password=123456"/>">发送登录认证请求</a><br>
<a href="<c:url value="/authen?username=jay&password=12"/>">模拟发送错误的帐号密码请求</a><br>
</body>
</html>
