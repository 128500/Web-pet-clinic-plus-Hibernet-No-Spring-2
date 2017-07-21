<%--
  Created by IntelliJ IDEA.
  User: дом
  Date: 22.03.2017
  Time: 20:09
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" session ="true"%>
<html>
<head>
    <title>RESULTS OF SEARCHING</title>
    <link href="${pageContext.servletContext.contextPath}/views/css/resultOfSearchingUsersStyle.css" rel="stylesheet" type="text/css">
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
    <a href="${pageContext.servletContext.contextPath}/views/user/CreateUser.jsp">&raquo; Add user into the database</a>
    <a href="${pageContext.servletContext.contextPath}/views/user/FindUser.jsp">&raquo; Find user or pet</a>
    <a href="${pageContext.servletContext.contextPath}/user/view/">&raquo; Return to viewing page</a>
</div>

<div id="content">
<table>
    <tr id="usersTableHeader">
        <th>ID number</th>
        <th>First name</th>
        <th>Last name</th>
        <th>Address</th>
        <th>Phone</th>
        <th>Pet's kind</th>
        <th>Pet's name</th>
        <th>Pet's age</th>
        <th>Options</th>
    </tr>
    <c:forEach items="${users}" var="user" varStatus="status">
        <tr vlign="top">
            <td>${user.getId()}</td>
            <td>${user.getFirstName()}</td>
            <td>${user.getLastName()}</td>
            <td>${user.getAddress()}</td>
            <td>${user.getPhoneNumber()}</td>
            <td>${user.getPet().getKind()}</td>
            <td>${user.getPet().getName()}</td>
            <td>${user.getPet().getAge()}</td>
            <td>
                <a href="${pageContext.servletContext.contextPath}/user/addinfo?id=${user.getId()}">Add information</a>
                <a href="${pageContext.servletContext.contextPath}/user/edit?id=${user.getId()}">Edit client's data</a>
                <a href="${pageContext.servletContext.contextPath}/user/delete?id=${user.getId()}">Delete client</a>
            </td>
        </tr>
    </c:forEach>
</table>
</div>

<diV id="footer">FOOTER</diV>
</body>
</html>
