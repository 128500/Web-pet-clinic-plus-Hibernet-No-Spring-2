<%--
  Created by IntelliJ IDEA.
  User: homeuser
  Date: 19.04.2017
  Time: 11:49
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" session ="true"%>
<html>
<head>
    <title>TROUBLE WITH PARAMETERS</title>
    <link href="${pageContext.servletContext.contextPath}/views/css/wrongParameterStyle.css" rel="stylesheet" type="text/css">
</head>
<body>

<h2>WE ARE SORRY, BUT THERE WERE PROBLEMS WITH THE INFORMATION YOU HAVE PUT INTO THE FOLLOWING FIELD(S)</h2>
<br>
<c:forEach items="${wrongParams}" var="wp" varStatus="status">
    <h3>${wp}</h3>
</c:forEach>
<br>
<br>
<h3 id="select">Please select one of the below options to continue</h3>
<br>
<br>
<div>
    <h2><a href="${pageContext.servletContext.contextPath}/views/user/CreateUser.jsp">Back to creating new account</a>
<br/>
<br/>
<a href="${pageContext.servletContext.contextPath}/user/view/">Back to the table of the clients</a>
</h2>
</div>
</body>
</html>
