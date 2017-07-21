<%--
  Created by IntelliJ IDEA.
  User: homeuser
  Date: 27.04.2017
  Time: 17:26
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<html>
<head>
    <title>FAIL SEARCHING</title>
    <link href="${pageContext.servletContext.contextPath}/views/css/failedResultOfSearchingStyle.css" rel="stylesheet" type="text/css">
</head>
<body>
<h2>SORRY! THERE ARE NO MATCHES WITH THE INPUT VALUE</h2>
<br>
<br>
<div>
    <h2>
        <a href="${pageContext.servletContext.contextPath}/views/user/FindUser.jsp">Back to searching</a>
        <br/>
        <br/>
        <a href="${pageContext.servletContext.contextPath}/user/view/">Back to viewing page</a>
    </h2>
</div>
</body>
</html>
