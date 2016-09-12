<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: JJ
  Date: 4/29/16
  Time: 12:33 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>ARS | SIGNUP</title>
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
        <c:if test="${requestScope.errorMessage!=null}">
            <div id="signupError" class="signupError">
                <div class="errorMessage">
                    <p>${requestScope.errorMessage}</p>
                </div>
            </div>
        </c:if>
        <div id="signupFormContainer" class="signupFormContainer">

            <form id="signupForm" method="post" action="/signup">
                <div class="header">
                    <p>Sign Up</p>
                </div>
                <div class="inputs">
                    <div id="line1">
                        <input name="email" type="email" placeholder="Email" autofocus />
                    </div>
                    <div id="line2">
                        <input name="password" type="password" placeholder="Password" />
                    </div>
                    <div id="line3">
                        <input type="submit" name="submit" class="submit" id="submit" value="Create An Account"/>
                    </div>
                    <div id="line4">
                        <p><a href="/login">Already Have An Account</a></p>
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
