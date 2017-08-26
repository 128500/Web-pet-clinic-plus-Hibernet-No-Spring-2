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
    <title>Edit client's data</title>
    <link href="${pageContext.servletContext.contextPath}/views/css/editUserStyle.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/viewUserScript.js"></script>
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
    <div id="pet_image">
        <img src="${pageContext.servletContext.contextPath}/show_image/${user.getPet().getId()}.jpeg" alt="Sorry, there is no photo yet" id="pet_photo">
    </div>
<form action="${pageContext.servletContext.contextPath}/user/edit" method="POST">
    <div class="form_input">
        <input type="hidden" name="id" value="${user.getId()}">
        <label>First name: </label>
        <input type="text" name="client first name" value="${user.getFirstName()}">
    </div>

    <div class="form_input">
        <label>Last name:</label>
        <input  type ="text" name = "client last name" value="${user.getLastName()}">
    </div>

    <div class="form_input">
        <label>Address:</label>
        <input  type="text" name="address" value="${user.getAddress()}">
    </div>

    <div class="form_input">
        <label>Phone number:</label>
        <input  type="number" name="phone number" value="${user.getPhoneNumber()}">
    </div>

    <div class="form_input">
        <label>Choose pet's kind: </label>
        <select  size='1' name = 'pet kind'>
            <option disabled>Choose pet</option>
            <option value="${user.getPet().getKind()}">${user.getPet().getKind()}</option>
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
        <input  type = 'text' name = 'pet name' value="${user.getPet().getName()}">
    </div>

    <div class="form_input">
        <label>Pet's age: </label>
        <input type="number" name="pet age" value="${user.getPet().getAge()}">
    </div>

    <div class="form_input">
        <input type = 'reset' value = 'Reset all fields' class="rounded" id="reset">
    </div>

    <div class="form_input">
        <input type = 'submit' name = 'add' value = 'Finish and submit' class="rounded" id="submit">
    </div>

</form>
</div>

<div id="footer">FOOTER</div>

</body>
</html>
