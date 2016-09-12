
<%--
  Created by IntelliJ IDEA.
  User: JJ
  Date: 9/10/16
  Time: 1:38 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.ars.domain.user.Employee" %>
<%@ page import="com.ars.system.APPStatics" %>
<%@ page import="com.ars.domain.ticket.Ticket" %>


<%
    ArrayList<Ticket> tickets = (ArrayList<Ticket>)request.getAttribute(APPStatics.RequestStatics.TICKET_SEARCH_RESULT);
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

            $(".ticketrow").dblclick(function(){
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

                        <form id="ticketSearchResultForm" class="ticketsForm employeeForm" method="post" action="/admin/ticketCancellation?req=getTicketDetails">
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
                                    <c:if test="${ticketsJSTL==null || fn:length(ticketsJSTL)<=0}">
                                        <tr>
                                            <td colspan="2">No cancellation requests to date</td>
                                        </tr>
                                    </c:if>
                                    <c:forEach items="${ticketsJSTL}" var="ticket">
                                        <tr id="${ticket.tickerNumberARS}" class="ticketrow">
                                            <td>${ticket.ticketNumber}</td>
                                            <td>${ticket.passenger.passengerID}</td>
                                        </tr>
                                    </c:forEach>
                                </table>
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
