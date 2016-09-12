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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>ARS | MY PROFILE</title>
    <link rel="stylesheet" href="/resources/javascript/jquery-ui.min.css">
    <script src="/resources/javascript/jquery.js"></script>
    <script src="/resources/javascript/jquery-ui.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/resources/css/styles.css">

    <script>
        $(document).ready(function () {
            $("#dateofbirth").datepicker({
                changeYear: true,
                changeMonth: true,
                yearRange: '1920:+0'
            });
        });
    </script>

</head>
<body>

<div>
    <div id="topbar" class="topbar">
        <%@include file="../templates/menueBar.jsp" %>
        <%@include file="../templates/userLoginInfo.jsp" %>
    </div>


    <div class="profileManagementContentPane contentPane" id="contentPane">
        <c:if test="${requestScope.successMessage!=null}">
            <div id="profileManagementSuccess" class="profileManagementSuccess">
                <div class="successMessage">
                    <p>${requestScope.successMessage}</p>
                </div>
            </div>
        </c:if>
        <c:if test="${requestScope.errorMessage!=null}">
            <div id="profileManagementError" class="profileManagementError">
                <div class="errorMessage">
                    <p>${requestScope.errorMessage}</p>
                </div>
            </div>
        </c:if>


        <div id="profileFormContainer" class="profileFormContainer container">

            <div class="panel-left">

                <%@include file="/jsp/templates/treemenue/usertreemenue.jsp" %>
            </div>

            <div class="manageUserPanel panel" id="manageUserPanel">
                <form id="profileForm" class="profileForm" method="post" action="/userProfile">
                    <div class="header-panel header">
                        <div>Manage Profile</div>
                    </div>

                    <div class="form-standard">
                        <c:choose>
                            <c:when test="${sessionScope.loggedUser.firstName!=null}">
                                <div class="header">
                                    <p>${sessionScope.loggedUser.firstName} ${sessionScope.loggedUser.lastName}</p>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="header">
                                    <p>${sessionScope.loggedUser.userName}</p>
                                </div>
                            </c:otherwise>
                        </c:choose>


                        <div class="inputs">
                            <div id="line1">
                                <div class="label">
                                    <p>First Name</p>
                                </div>
                                <div class="separator">
                                    <p>:</p>
                                </div>
                                <div class="input">
                                    <p><input name="firstname" type="text"
                                              value="${sessionScope.loggedUser.firstName}"/>
                                    </p>
                                </div>
                            </div>
                            <div id="line2">
                                <div class="label">
                                    <p>Last Name</p>
                                </div>
                                <div class="separator">
                                    <p>:</p>
                                </div>
                                <div class="input">
                                    <p><input name="lastname" type="text" value="${sessionScope.loggedUser.lastName}"/>
                                    </p>
                                </div>
                            </div>
                            <div id="line3">
                                <div class="label">
                                    <p>Other Name</p>
                                </div>
                                <div class="separator">
                                    <p>:</p>
                                </div>
                                <div class="input">
                                    <p><input name="othername" type="text"
                                              value="${sessionScope.loggedUser.otherName}"/>
                                    </p>
                                </div>
                            </div>
                            <div id="line4">
                                <div class="label">
                                    <p>Contact Email</p>
                                </div>
                                <div class="separator">
                                    <p>:</p>
                                </div>
                                <div class="input">
                                    <p><input name="email" type="text" value="${sessionScope.loggedUser.email}"/></p>
                                </div>
                            </div>
                            <div id="line5">
                                <div class="label">
                                    <p>Date Of Birth</p>
                                </div>
                                <div class="separator">
                                    <p>:</p>
                                </div>
                                <div class="input">
                                    <fmt:formatDate value="${sessionScope.loggedUser.dateOfBirth}" var="formattedDate"
                                                    pattern="MM/dd/yyyy"></fmt:formatDate>
                                    <p><input name="dateofbirth" id="dateofbirth" type="text" value="${formattedDate}"/>
                                    </p>
                                </div>
                            </div>
                            <div id="line6">
                                <div class="label">
                                    <p>Old Password</p>
                                </div>
                                <div class="separator">
                                    <p>:</p>
                                </div>
                                <div class="input">
                                    <p><input name="oldpassword" type="password"/></p>
                                </div>
                            </div>
                            <div id="line7">
                                <div class="label">
                                    <p>New Password</p>
                                </div>
                                <div class="separator">
                                    <p>:</p>
                                </div>
                                <div class="input">
                                    <p><input name="newpassword" type="password"/></p>
                                </div>
                            </div>
                            <div id="line8">
                                <div class="label">
                                    <p>Confirm Password</p>
                                </div>
                                <div class="separator">
                                    <p>:</p>
                                </div>
                                <div class="input">
                                    <p><input name="confirmpassword" type="password"/></p>
                                </div>
                            </div>

                            <div id="line9">
                                <div class="update">
                                    <p><input type="submit" class="ui-button" name="submit" value="Update Profile"></p>
                                </div>
                            </div>


                        </div>
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
