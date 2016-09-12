<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.ars.system.APPStatics" %>
<%@ page import="com.ars.domain.booking.Booking" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    ArrayList<Booking> bookings = (ArrayList<Booking>)request.getAttribute(APPStatics.RequestStatics.BOOKING_SEARCH_RESULT);
    pageContext.setAttribute("bookingJSTL", bookings);
%>
<div class="employeeSearchResult">
    <input type="hidden" name="numberOfPages" id="numberOfPages" value="${requestScope.numberOfPages}"/>
    <table border="1" class="employeeSearchResultTable">
        <tr>
            <th>Booking Number</th>
            <th>Booking Date</th>
            <th>From</th>
            <th>To</th>
        </tr>
        <c:choose>
            <c:when test="${fn:length(bookingJSTL)>0}">
        <c:forEach items="${bookingJSTL}" var="booking">
            <tr id="${booking.bookingNumber}" class="search-row">
                <td>${booking.bookingNumber}</td>
                <td>${booking.bookingDate}</td>
                <td>${booking.fromAirport.code}</td>
                <td>${booking.toAirport.code}</td>
            </tr>
        </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td colspan="4">No Booking Found</td>
                </tr>
            </c:otherwise>
        </c:choose>
    </table>
</div>