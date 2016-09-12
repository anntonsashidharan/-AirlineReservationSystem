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
    <title>ARS | LOGIN</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/styles.css">
    <link rel="stylesheet" href="/resources/javascript/jquery-ui.min.css">
    <script src="/resources/javascript/jquery.js"></script>
    <script src="/resources/javascript/jquery-ui.min.js"></script>
</head>
<body>

    <div id="topbar" class="topbar">
        <div id="logo" class="logo">
            <img alt="" src="resources/images/images1.png" height="">
        </div>
        <div id="menu" class="menu">
            <ul id="navbar" class="navbar">
                <li class="navbarItem"><a href="/main">HOME</a></li>
                <li class="navbarItem"><a href="#">MORE</a></li>
                <li class="navbarItem"><a href="/faq">FAQ</a></li>
                <li class="navbarItem"><a href="#">ABOUT US</a></li>
            </ul>
        </div>
    </div>

    <div class="contentPane" id="contentPane">


        <div class="loginPageMessageSection">
            <c:if test="${requestScope.successMessage!=null}">
                <div class="successMessage">
                    <p>${requestScope.successMessage}</p>
                </div>
            </c:if>
            <c:if test="${requestScope.errorMessage!=null}">
                <div class="errorMessage">
                    <p>${requestScope.errorMessage}</p>
                </div>
            </c:if>
            <c:if test="${requestScope.informationMessage!=null}">
                <div class="informationMessage">
                    <p>${requestScope.informationMessage}</p>
                </div>
            </c:if>
        </div>

        <div id="loginFormContainer" class="loginFormContainer">
            <div class="loginMessageSection"></div>
            <form id="loginForm" method="post" action="/login">
                <div class="header">
                    <p>Login</p>
                </div>
                <div class="inputs">
                    <div id="line1">
                        <input name="username" type="text" placeholder="Username" autofocus />
                    </div>
                    <div id="line2">
                        <input name="password" type="password" placeholder="Password" />
                    </div>
                    <div id="line3">
                        <input type="submit" name="submit" class="submit" id="submit" value="Login"/>
                    </div>
                    <div id="line4">
                        <p><a href="/signup">I Don't Have An Account</a></p>
                    </div>
                </div>
            </form>
        </div>

    </div>

    <div class="footer">
        <%@include file="../templates/footer.jsp" %>
    </div>

</body>
</html>
