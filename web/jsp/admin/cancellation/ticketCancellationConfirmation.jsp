<%--
  Created by IntelliJ IDEA.
  User: JJ
  Date: 9/10/16
  Time: 5:36 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.ars.system.APPStatics" %>
<%@ page import="com.ars.domain.ticket.Ticket" %>

<%
    ArrayList<Ticket> tickets = (ArrayList<Ticket>)request.getAttribute(APPStatics.RequestStatics.TICKET_GETTING_CANCELLED);
    pageContext.setAttribute("ticketsJSTL", tickets);
%>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ARS | TICKET CANCELLATION</title>
    <link rel="stylesheet" href="../resources/javascript/jquery-ui.min.css">
    <script src="../resources/javascript/jquery.js"></script>
    <script src="../resources/javascript/jquery-ui.min.js"></script>
    <link rel="stylesheet" type="text/css" href="../resources/css/styles.css">

    <script>
        $(document).ready(function () {

            $(".confirmCancellation").dblclick(function(){
                var selectedTicketNumber = $(this).attr("id");
                $("#ticketSearchResultForm").attr("action","/admin/ticketCancellation?req=getTicketDetails&ticketNumber="+selectedTicketNumber);
                $("#ticketSearchResultForm").submit()
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

            <div class="adminstrationPanel ticketSearchResultPanel" id="adminstrationPanel">
                <c:choose>
                    <c:when test="${sessionScope.loggedUser.portals.contains('APPROVE_TICKET_CANCELLATION_REQ')}">

                        <form id="ticketSearchResultForm" class="ticketsForm employeeForm" method="post" action="/admin/ticketCancellation?req=approveCancellationRequest">
                            <input type="hidden" name="ticketNumber" value="${requestScope.cancellingTicketNumber}">
                            <div class="header">
                                <p>Ticket Cancellation</p>
                            </div>
                            <!--
                                            <div class="subheader">
                                                <p>Create Employee</p>
                                            </div>
                            -->

                            <div class="employeeSearchResult">
                                <table border="1" style="width: 80%" class="employeeSearchResultTable ticketSearchResultTable">
                                    <tr>
                                        <th>Ticket Number</th>
                                        <th>Passenger ID</th>
                                    </tr>
                                    <c:forEach items="${ticketsJSTL}" var="ticket">
                                        <tr id="${ticket.tickerNumberARS}" class="ticketrow">
                                            <td>${ticket.ticketNumber}</td>
                                            <td>${ticket.passenger.passengerID}</td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>

                            <div class="inputs">
                                <div class="line">
                                    <div class="label">
                                        <p>Approved Refund</p>
                                    </div>
                                    <div class="separator">
                                        <p>:</p>
                                    </div>
                                    <div class="input">
                                        <p><input name="approvedRefundAmount" type="text"/></p>
                                    </div>
                                </div>


                                <div class="line">
                                    <div class="createUserButton">
                                        <p><input class="button-standard-size button-default" type="submit" id="confirmCancellation" name="submit"
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






</body>
</html>
