<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="inputs">


<div class="upTripSummery tripSummery form-standard" id="upTripSummery">

    <div class="tripSummeryHeading subHeading">
        <div>Dependent Cancellations</div>
        <div>
        </div>
    </div>


    <div class="tableOfTickets">
        <table class="tickets" border="1">
            <th>Ticket Number</th>
            <th>From</th>
            <th>To</th>
            <c:forEach items="${requestScope.ticketListGettingCancelled}" var="ticket">
                <tr id="${ticket.tickerNumberARS}">
                    <td>${ticket.ticketNumber}</td>
                    <td>${ticket.schedule.from.code}</td>
                    <td>${ticket.schedule.to.code}</td>
                </tr>
            </c:forEach>

        </table>
    </div>

</div>


<div class="buttonSection line">
    <div class="float-right submitPassengerInfoButton">
        <p><input class="button-standard-size button-default" type="button" name="submit" id="confirmCancellation"
                  value="Confirm Cancellation"></p>
    </div>
    <!--
    <div class="float-right submitPassengerInfoButton">
        <p><input class="button-standard-size button-default" type="button" name="submit" id="ignoreCancellation"
                  value="Go Back"></p>
    </div>
    -->
</div>


</div>