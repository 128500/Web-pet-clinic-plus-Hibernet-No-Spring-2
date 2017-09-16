<%--
  Created by IntelliJ IDEA.
  User: ALEKSANDR KUDIN
  Date: 11.09.2017
  Time: 19:04
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" session ="true"%>
<html>
<head>
    <title>SEND MESSAGE</title>
    <link href="${pageContext.servletContext.contextPath}/views/css/sendMessageStyle.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/sendMessageScript.js"></script>
</head>

<body>

<div id="header">
    <img src="${pageContext.servletContext.contextPath}/views/images/logo.png" alt="OMG" id="logo_img">
    <div id="header_sign">
        <div id="purple">WEB</div>
        <div id="gray">clinic</div>
    </div>
</div>

<div id="sidebar">
<a href="${pageContext.servletContext.contextPath}/views/user/CreateUser.jsp" id="refAddUser">&raquo; Add user into the database</a>
<a href="${pageContext.servletContext.contextPath}/views/user/FindUser.jsp" id="refFindUser">&raquo; Find user or pet</a>
</div>


<div id="content">
    <h3>You are going to send message to ${user.getFirstName()} ${user.getLastName()}</h3>

    <form action="${pageContext.servletContext.contextPath}/user/send_message" method="post">
        <input type="hidden" name="id" value="${user.getId()}">
        <textarea name="message" placeholder="Enter your message here!!!" rows="10" cols="45"></textarea>
        <input type="submit" value="Send message">
    </form>

</div>

<div id="footer">
    <p>FOOTER</p>
    <div id="hint"></div>
</div>
</form>
</body>
</html>
