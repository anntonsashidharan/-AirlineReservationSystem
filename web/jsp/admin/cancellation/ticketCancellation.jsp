<%--
  Created by IntelliJ IDEA.
  User: JJ
  Date: 9/10/16
  Time: 1:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>ARS | TICKET CANCELLATION</title>
    <link rel="stylesheet" href="../resources/javascript/jquery-ui.min.css">
    <script src="../resources/javascript/jquery.js"></script>
    <script src="../resources/javascript/jquery-ui.min.js"></script>
    <link rel="stylesheet" type="text/css" href="../resources/css/styles.css">

    <script>
        var modal;

        $(document).ready(function () {
            $("#dateofbirth").datepicker();

            // Get the modal
            modal = document.getElementById('myModal');

            // Get the <span> element that closes the modal
            var span = document.getElementsByClassName("close")[0];

            // When the user clicks on <span> (x), close the modal
            span.onclick = function () {
                modal.style.display = "none";
                pageNumber = 1;
                $("#pageNumber>input").val(pageNumber);
            }

            // When the user clicks anywhere outside of the modal, close it
            window.onclick = function (event) {
                if (event.target == modal) {
                    modal.style.display = "none";
                    pageNumber = 1;
                    $("#pageNumber>input").val(pageNumber);
                }
            }

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
        <c:when test="${sessionScope.loggedUser.portals.contains('APPROVE_TICKET_CANCELLATION_REQ')}">

            <form id="ticketSerchForm" class="employeeForm" method="post" action="/admin/ticketCancellation?req=search">
                <div class="header">
                    <p>Ticket Cancellation</p>
                </div>
<!--
                <div class="subheader">
                    <p>Create Employee</p>
                </div>
-->
                <div class="inputs">
                    <div class="line">
                        <div class="label">
                            <p>Ticket Number</p>
                        </div>
                        <div class="separator">
                            <p>:</p>
                        </div>
                        <div class="input">
                            <p><input name="ticketNumber" type="text"/></p>
                        </div>
                    </div>


                    <div class="line">
                        <div class="createUserButton">
                            <p><input class="button-standard-size button-default" type="submit" name="submit"
                                      value="Search Tickets"></p>
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






<!-- The Modal -->
<div id="myModal" class="modal">

    <!-- Modal content -->
    <div class="modal-content">
        <div class="modal-header">
            <span class="close">×</span>
            <h2>Cancellation Requested Ticket Search Result</h2>
        </div>
        <div class="modal-body" id="modal-body">

        </div>
        <div class="modal-footer">



        </div>
    </div>

</div>



</body>
</html>
