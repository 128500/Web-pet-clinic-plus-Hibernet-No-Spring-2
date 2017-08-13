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
    <title>Add info</title>
    <link href="${pageContext.servletContext.contextPath}/views/css/addInfoStyle.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/addInfoScript.js"></script>
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
    <form action="${pageContext.servletContext.contextPath}/user/addinfo" method="POST" enctype="multipart/form-data" class="feedback-form-1">
        <input type="hidden" name="id" value="${user.getId()}">
        <input type="hidden" name="pet_id" value="${user.getPet().getId()}">

            <fieldset>

                <div class="input-file-row-1">

                    <div class="upload-file-container">
                        <img id="image" src="#" alt="" />
                        <div class="upload-file-container-text">
                            <span>Add<br />photo</span>
                            <input type="file" name="photo" class="photo" id="imgInput">
                        </div>
                    </div>

                </div>

                <div >
                    <input type = 'submit' name = 'add' value = 'Finish and submit' class="rounded" id="submit">
                </div>
            </fieldset>
    </form>
<div>
    <div >
        <p readonly>First name: ${user.getFirstName()}</p>
    </div>

    <div >
        <p readonly>Last name: ${user.getLastName()}</p>
    </div>

    <div >
        <p readonly>Address: ${user.getAddress()}</p>
    </div>

    <div >
        <p readonly>Phone number: ${user.getPhoneNumber()}</p>
    </div>

    <div >
        <p readonly>Pet's kind: ${user.getPet().getKind()}</p>
    </div>

    <div >
        <p readonly>Pet's name: ${user.getPet().getName()}</p>
    </div>

    <div >
        <p readonly>Pet's age: ${user.getPet().getAge()}</p>
    </div>
</div>
</div>

<div id="footer">FOOTER</div>

</body>
</html>
