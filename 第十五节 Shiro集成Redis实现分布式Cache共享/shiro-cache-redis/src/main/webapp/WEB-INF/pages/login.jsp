<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>登录</title>
    <link rel="stylesheet" href="<c:url value="/static/bootstrap.min.css"/>">
    <script src="<c:url value="/static/jquery-3.2.1.min.js"/> "></script>
</head>
<body class="container" style="width: 60%">
<br><br><br><br><br>

<form class="form-group" action="<c:url value="/actions/security/login"/>" method="post">
    <label for="user_name">用户名</label>
    <input class="form-control" type="text" name="username" id="user_name" value="${username}"><br>
    <label for="pass_word">密码</label>
    <input class="form-control" type="password" name="password" id="pass_word"><br>
    <div class="checkbox">
        <label> <input type="checkbox" id="isRememberMe" name="isRememberMe">记住我 </label>
    </div>
    <button id="btn_join" type="submit" class="btn btn-success">提交</button>
</form>

</body>
</html>
