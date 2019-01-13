<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>这里是main.jsp，欢迎来到shiro的世界</h1>
<h1>欢迎用户<shiro:principal/>登录</h1>

<shiro:hasRole name="coder">
    <h1> 能看到我代表你有程序员coder角色</h1>
</shiro:hasRole>

<shiro:hasPermission name="code:insert">
    <button>提交代码</button><br>
</shiro:hasPermission>
<shiro:hasPermission name="code:update">
    <button>更新代码</button>
</shiro:hasPermission>
<shiro:hasPermission name="code:delete">
    <button>删除代码</button>
</shiro:hasPermission>

</body>
</html>
