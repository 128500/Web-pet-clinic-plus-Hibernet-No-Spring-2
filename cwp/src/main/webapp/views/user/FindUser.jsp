<%--
  Created by IntelliJ IDEA.
  User: homeuser
  Date: 25.04.2017
  Time: 22:04
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" session ="true"%>
<html>
<head>
    <title>FIND CLIENT OR PET</title>
    <link href="${pageContext.servletContext.contextPath}/views/css/findUsersStyle.css" rel="stylesheet" type="text/css">
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
    <a href="${pageContext.servletContext.contextPath}/user/view/">&raquo; Back to the start page</a>
    <a href="${pageContext.servletContext.contextPath}/views/user/CreateUser.jsp" id="refAddUser">&raquo; Add user into the database</a>
</div>

<div id="content">
<form action="${pageContext.servletContext.contextPath}/user/find/" method="POST">

   <div class="form_div">
       <div class="form_input">
           <label>Input a name or part of a name:</label>
       </div>

        <div>
            <input required type="text" name="input" class="form_input">
        </div>

       <div>
           <label>Where to look?</label>
           <br/>
           <input type="radio" name="first_name" value="first_name">First name <br/>
           <input type="radio" name="last_name" value="last_name">Last name <br/>
           <input type="radio" name="pet_name" value="pet_name">Pet name <br/>
       </div>

        <div>
            <input type="submit" name="find" value="Find" class="form_input" id="submit_button">
        </div>
   </div>
</form>
</div>

<div id="footer">FOOTER</div>

</body>
</html>
