<%--
  Created by IntelliJ IDEA.
  User: JJ
  Date: 5/1/16
  Time: 2:30 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>ARS | CANCELLATION APPROVAL</title>
    <link rel="stylesheet" type="text/css" href="../resources/css/styles.css">
    <link rel="stylesheet" href="../resources/javascript/jquery-ui.min.css">
    <script src="../resources/javascript/jquery.js"></script>
    <script src="../resources/javascript/jquery-ui.min.js"></script>

    <script>
        $(document).ready(function(){
            $("#dateofbirth").datepicker({

            });

            $("#addFixedLineField").click(function(){
                var node = $(this).parent().clone();
                node.find(".label>p").html("&nbsp;");
                node.find(".add").remove();
                node.find("input").prop("name","fixedLineNumber"+(parseInt($("#numberOfFixedLines").val())+1));

                node.insertAfter($(this).parent());
                $("#numberOfFixedLines").prop("value",(parseInt($("#numberOfFixedLines").val()) + 1));

            });

            $("#mobileNumberField").click(function(){
                var node = $(this).parent().clone();
                node.find(".label>p").html("&nbsp;");
                node.find(".add").remove();
                node.find("input").prop("name","mobileNumber"+(parseInt($("#numberOfMobileNumbers").val())+1));

                node.insertAfter($(this).parent());
                $("#numberOfMobileNumbers").prop("value",(parseInt($("#numberOfMobileNumbers").val()) + 1));

            });

        });
    </script>

</head>
<body>

<div>
<div id="topbar" class="topbar">
    <div id="logo" class="logo">
        <img alt="" src="/resources/images/images1.png" height="">
    </div>
    <div id="menu" class="menu">
        <ul id="navbar" class="navbar">
            <li class="navbarItem"><a href="/main">HOME</a></li>
            <li class="navbarItem"><a href="#">MORE</a></li>
            <li class="navbarItem"><a href="/faq">FAQ</a></li>
            <li class="navbarItem"><a href="#">ABOUT US</a></li>
            <c:if test="${sessionScope.loggedUser.roles.contains('admin') || sessionScope.loggedUser.roles.contains('manager') || sessionScope.loggedUser.roles.contains('staff')}">
                <li class="navbarItem"><a href="/admin/employee">ADMIN</a></li>
            </c:if>
        </ul>
    </div>
    <div id="login" class="login">
        <ul id="loginnavbar" class="loginnavbar">
            <c:choose>
                <c:when test="${sessionScope.loggedUser!=null}">
                    <li class="loginnavbarItem"><a href="/userProfile">${sessionScope.loggedUser.userName}</a></li>
                    <li class="loginnavbarItem"><a href="/logout">Signout</a></li>
                </c:when>
                <c:otherwise>
                    <li class="loginnavbarItem"><a href="/login">Login</a></li>
                    <li class="loginnavbarItem"><a href="/signup">Signup</a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</div>


<div class="adminContentPane" id="adminContentPane">


<c:if test="${requestScope.successMessage!=null}">
    <div id="administrationSuccess" class="administrationSuccess">
        <div class="successMessage">
            <p>${requestScope.successMessage}</p>
        </div>
    </div>
</c:if>
<c:if test="${requestScope.errorMessage!=null}">
    <div id="administrationError" class="administrationError">
        <div class="errorMessage">
            <p>${requestScope.errorMessage}</p>
        </div>
    </div>
</c:if>
<div id="administrationContainer" class="administrationContainer">
<div class="administrationTreeMenue" id="administrationTreeMenue">
    <ul>
        <c:if test="${sessionScope.loggedUser.roles.contains('admin') || sessionScope.loggedUser.roles.contains('manager')}">
            <li><a>Employee</a></li>
            <ul>
                <c:if test="${sessionScope.loggedUser.roles.contains('admin')}">
                    <li><a href="#">Manage Employee</a></li>
                    <li><a href="/admin/employee">Create Employee</a></li>
                </c:if>
            </ul>
        </c:if>


        <li><a>Ticket Cancellation</a></li>
        <ul>
            <li><a href="#">Cancellation Requests</a></li>
        </ul>

        <li><a>Reports</a></li>
        <ul>
            <li><a href="#">Employees</a></li>
            <li><a href="#">Reservations</a></li>
        </ul>

    </ul>
</div>
<div class="adminstrationPanel" id="adminstrationPanel">
    <form id="createUserForm" class="createUserForm" method="post" action="/admin/employee?req=createEmployee">
        <div class="header">
            <p>Ticket Cancellation Requests Management</p>
        </div>

        <div class="subheader">
            <p>Approve/Ignore Ticket Cancellation</p>
        </div>


    </form>
</div>
</div>

</div>

<div class="footer">
    <%@include file="../templates/footer.jsp" %>
</div>
</div>

</body>
</html>
