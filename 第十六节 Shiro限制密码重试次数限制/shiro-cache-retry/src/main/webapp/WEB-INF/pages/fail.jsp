<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录失败</title>
</head>
<body>
登录失败原因：${msg}
<a href="<c:url value="/actions/login"/> ">重新登录</a>
</body>
</html>
