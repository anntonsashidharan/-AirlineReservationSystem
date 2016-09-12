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
    <title>ARS | MY PROFILE</title>
    <link rel="stylesheet" href="../resources/javascript/jquery-ui.min.css">
    <script src="../resources/javascript/jquery.js"></script>
    <script src="../resources/javascript/jquery-ui.min.js"></script>
    <link rel="stylesheet" type="text/css" href="../resources/css/styles.css">

    <script>
        $(document).ready(function () {
            $("#dateofbirth").datepicker({

            });

            $("#addFixedLineField").click(function () {
//                $('<div class="line"><div class="label"><p>Fixed Line</p></div><div class="separator"><p>:</p>'+
//                '</div><div class="input"><p><input name="fixedLineNumber1" type="text"/></p></div>' +
//                '<div class="add" id="addFixedLineField">Add</div></div>').insertAfter($(this).parent());
                var node = $(this).parent().parent().clone();
                node.find(".label>p").html("&nbsp;");
                node.find(".add").remove();
                node.find("input").prop("name", "fixedLineNumber" + (parseInt($("#numberOfFixedLines").val()) + 1));

                node.insertAfter($(this).parent().parent());
                $("#numberOfFixedLines").prop("value", (parseInt($("#numberOfFixedLines").val()) + 1));

            });

            $("#mobileNumberField").click(function () {
                var node = $(this).parent().parent().clone();
                node.find(".label>p").html("&nbsp;");
                node.find(".add").remove();
                node.find("input").prop("name", "mobileNumber" + (parseInt($("#numberOfMobileNumbers").val()) + 1));

                node.insertAfter($(this).parent().parent());
                $("#numberOfMobileNumbers").prop("value", (parseInt($("#numberOfMobileNumbers").val()) + 1));
            });

        });
    </script>

</head>
<body>

<div>
<div id="topbar" class="topbar">
    <%@include file="../../templates/menueBar.jsp" %>
    <%@include file="../../templates/userLoginInfo.jsp" %>
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

<%@include file="../../templates/treemenue/admintreemenue.jsp" %>

<div class="adminstrationPanel" id="adminstrationPanel">
    <c:choose>
        <c:when test="${sessionScope.loggedUser.portals.contains('EMPLOYEE_ADD')}">

            <form id="employeeForm" class="employeeForm" method="post" action="/admin/employee?req=createEmployee">
                <div class="header">
                    <p>Employee Management</p>
                </div>

                <div class="subheader">
                    <p>Create Employee</p>
                </div>

                <div class="inputs">
                    <div class="line">
                        <div class="label">
                            <p>First Name</p>
                        </div>
                        <div class="separator">
                            <p>:</p>
                        </div>
                        <div class="input">
                            <p><input name="firstname" type="text"/></p>
                        </div>
                    </div>
                    <div class="line">
                        <div class="label">
                            <p>Last Name</p>
                        </div>
                        <div class="separator">
                            <p>:</p>
                        </div>
                        <div class="input">
                            <p><input name="lastname" type="text"/></p>
                        </div>
                    </div>
                    <div class="line">
                        <div class="label">
                            <p>Other Name</p>
                        </div>
                        <div class="separator">
                            <p>:</p>
                        </div>
                        <div class="input">
                            <p><input name="othername" type="text"/></p>
                        </div>
                    </div>
                    <div class="line">
                        <div class="label">
                            <p>Contact Email</p>
                        </div>
                        <div class="separator">
                            <p>:</p>
                        </div>
                        <div class="input">
                            <p><input name="email" type="text"/></p>
                        </div>
                    </div>
                    <div class="line">
                        <div class="label">
                            <p>Date Of Birth</p>
                        </div>
                        <div class="separator">
                            <p>:</p>
                        </div>
                        <div class="input">
                            <fmt:formatDate value="${sessionScope.loggedUser.dateOfBirth}" var="formattedDate"
                                            pattern="MM/dd/yyyy"></fmt:formatDate>
                            <p><input name="dateofbirth" id="dateofbirth" type="text"/></p>
                        </div>
                    </div>
                    <div class="line">
                        <div class="label">
                            <p>NIC Number</p>
                        </div>
                        <div class="separator">
                            <p>:</p>
                        </div>
                        <div class="input">
                            <p><input name="nicnumber" type="text"/></p>
                        </div>
                    </div>
                    <div class="line">
                        <div class="label">
                            <p style="font-size: 20px; font-weight: bold">Address</p>
                        </div>
                    </div>
                    <div class="line">
                        <div class="label">
                            <p>Address Line1</p>
                        </div>
                        <div class="separator">
                            <p>:</p>
                        </div>
                        <div class="input">
                            <p><input name="addressline1" type="text"/></p>
                        </div>
                    </div>
                    <div class="line">
                        <div class="label">
                            <p>Address Line2</p>
                        </div>
                        <div class="separator">
                            <p>:</p>
                        </div>
                        <div class="input">
                            <p><input name="addressline2" type="text"/></p>
                        </div>
                    </div>
                    <div class="line">
                        <div class="label">
                            <p>Address Line3</p>
                        </div>
                        <div class="separator">
                            <p>:</p>
                        </div>
                        <div class="input">
                            <p><input name="addressline3" type="text"/></p>
                        </div>
                    </div>
                    <div class="line">
                        <div class="label">
                            <p style="font-size: 20px; font-weight: bold">Contact Number Information</p>
                        </div>
                    </div>
                    <input type="text" hidden id="numberOfFixedLines" name="numberOfFixedLines" value="1"/>

                    <div class="line">
                        <div class="label">
                            <p>Fixed Line</p>
                        </div>
                        <div class="separator">
                            <p>:</p>
                        </div>
                        <div class="input">
                            <p><input name="fixedLineNumber1" type="text"/></p>
                        </div>
                        <div class="add">
                            <input class="addPhoneNumber button-default" type="button" id="addFixedLineField"
                                   value="Add"/>
                        </div>
                    </div>

                    <input type="text" hidden name="numberOfMobileNumbers" id="numberOfMobileNumbers" value="1"/>

                    <div class="line">
                        <div class="label">
                            <p>Mobile</p>
                        </div>
                        <div class="separator">
                            <p>:</p>
                        </div>
                        <div class="input">
                            <p><input name="mobileNumber1" type="text"/></p>
                        </div>
                        <div class="add">
                            <input class="addPhoneNumber button-default" id="mobileNumberField" type="button"
                                   value="Add"/>
                        </div>
                    </div>

                    <div class="line">
                        <div class="label">
                            <p>User Roles</p>
                        </div>
                        <div class="separator">
                            <p>:</p>
                        </div>
                        <div class="radio input">
                            <p>
                                <c:if test="${sessionScope.loggedUser.roles.contains('admin')}">
                                    <input name="userRoleAdmin" value="admin" type="checkbox"/>Administrator<br>
                                    <input name="userRoleManager" value="manager" type="checkbox"/>Manager<br>
                                </c:if>
                                <input name="userRoleStaff" value="staff" type="checkbox"/>Staff
                            </p>
                        </div>
                    </div>


                    <div class="line">
                        <div class="createUserButton">
                            <p><input class="button-standard-size button-default" type="submit" name="submit"
                                      value="Create Employee"></p>
                        </div>
                    </div>


                </div>
            </form>

        </c:when>
        <c:otherwise>
            <div style="color: red;font-size: 18px">Sorry you do not have privileges to access this page</div>
        </c:otherwise>
    </c:choose>
</div>

</div>

</div>

<div class="footer">
    <%@include file="../../templates/footer.jsp" %>
</div>
</div>

</body>
</html>
