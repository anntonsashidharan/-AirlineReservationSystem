<%--
  Created by IntelliJ IDEA.
  User: JJ
  Date: 4/30/16
  Time: 9:11 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>ARS | EMAIL CONFIRMATION REQUIRED</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/styles.css">
    <link rel="stylesheet" href="/resources/javascript/jquery-ui.min.css">
    <script src="/resources/javascript/jquery.js"></script>
    <script src="/resources/javascript/jquery-ui.min.js"></script>
</head>
<body>

<div id="topbar" class="topbar">
    <%@include file="../templates/menueBar.jsp" %>
    <%@include file="../templates/userLoginInfo.jsp" %>
</div>

<div class="contentPane" id="contentPane">
    <c:if test="${requestScope.errorMessage!=null}">
        <div id="loginError" class="loginError">
            <div class="errorMessage">
                <p>${requestScope.errorMessage}</p>
            </div>
        </div>
    </c:if>
    <div id="loginInformation" class="loginInformation">

        <div class="information">
            <p>Please Follow The Link Sent To</p>
            <p>${sessionScope.loggedUser.email}</p>
        </div>

    </div>

</div>

<div class="footer">
    <%@include file="../templates/footer.jsp" %>
</div>

</body>
</html>
