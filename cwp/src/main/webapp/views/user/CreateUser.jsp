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
    <title>CREATING ACCOUNT</title>
    <link href="${pageContext.servletContext.contextPath}/views/css/createUserStyle.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/createUserScript.js"></script>
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
<h4>TO CREATE AN ACCOUNT PLEASE FILL THE FORM BELOW</h4>

<form name="forma" action="${pageContext.servletContext.contextPath}/user/create/" method="POST">

    <div class="form_input">
        <label>First name: </label>
        <input type="text" name="client first name">
    </div>

    <div class="form_input">
    <label>Last name:</label>
    <input  type ="text" name = "client last name">
    </div>

    <div class="form_input">
        <label>Address:</label>
        <input  type="text" name="address">
    </div>

    <div class="form_input">
        <label>Phone number:</label>
        <input  type="number" name="phone number">
    </div>

    <div class="form_input">
        <label>Choose pet's kind: </label>
        <select  size='1' name = 'pet kind'>
            <option disabled>Choose pet</option>
            <option value='pet'>pet</option>
            <option value='bunny'>bunny</option>
            <option value='canary'>canary</option>
            <option value='cat'>cat</option>
            <option value='cavy'>cavy</option><%--морская свинка--%>
            <option value='dog'>dog</option>
            <option value='ferret'>ferret</option>
            <option value='fish'>fish</option>
            <option value='frog'>frog</option>
            <option value='hamster'>hamster</option>
            <option value='iguana'>iguana</option>
            <option value='parrot'>parrot</option>
            <option value='rat'>rat</option>
            <option value='turtle'>turtle</option>
        </select>
    </div>

    <div class="form_input">
        <label>Pet's name:</label>
        <input  type = 'text' name = 'pet name'>
    </div>

    <div class="form_input">
        <label>Pet's age: </label>
        <input type="number" name="pet age" >
    </div>

    <div class="form_input">
        <input type = 'reset' value = 'Reset all fields' class="rounded" id="reset">
    </div>

    <div class="form_input">
        <input type = 'submit' name = 'add' value = 'Finish and submit' class="rounded" id="submit">
    </div>

</form>
</div>

<div id="footer">
    FOOTER
</div>

</body>
</html>
