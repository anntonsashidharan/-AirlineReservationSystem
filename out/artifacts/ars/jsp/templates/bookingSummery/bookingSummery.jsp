<div class="bookingSummery" id="bookingSummery">
    <div class="heading">
        <div>Summery</div>
    </div>

    <c:if test="${sessionScope.tripType != null && sessionScope.tripType != ''}">

        <div class="outboundDetails section">
            <div class="subHeading">
                <div class="icon"><img src="../../resources/images/outboung.png" height="25px" width="25px"></div>
                <div class="text"><span>Outbound</span></div>
            </div>
            <div class="detail">
                <div class="label">From :</div>
                <div class="value">${sessionScope.sourceAirport}</div>
            </div>
            <div class="detail">
                <div class="label">To :</div>
                <div class="value">${sessionScope.destinationAirport}</div>
            </div>
            <div class="detail">
                <div class="label">Departure :</div>
                <fmt:formatDate value="${sessionScope.selectedUpTripScheduleIDJava.transits[0].schedule.departDate}"
                                var="formattedOutboundDate"
                                pattern="MM/dd/yyyy"></fmt:formatDate>
                <div class="value float-left">${formattedOutboundDate}</div>
                <fmt:formatDate value="${sessionScope.selectedUpTripScheduleIDJava.transits[0].schedule.departTime}"
                                var="formattedOutboundTime"
                                pattern="HH:MM"></fmt:formatDate>
                <div class="value">${formattedOutboundTime}</div>
            </div>
            <div class="detail">
                <div class="label">Arrival :</div>
                <fmt:formatDate
                        value="${sessionScope.selectedUpTripScheduleIDJava.transits[fn:length(sessionScope.selectedUpTripScheduleIDJava.transits)-1].schedule.arrivalDate}"
                        var="formattedOutboundArrivalDate"
                        pattern="MM/dd/yyyy"></fmt:formatDate>
                <div class="value float-left">${formattedOutboundArrivalDate}</div>
                <fmt:formatDate
                        value="${sessionScope.selectedUpTripScheduleIDJava.transits[fn:length(sessionScope.selectedUpTripScheduleIDJava.transits)-1].schedule.arrivalTime}"
                        var="formattedOutboundArrivalTime"
                        pattern="HH:MM"></fmt:formatDate>
                <div class="value">${formattedOutboundArrivalTime}</div>
            </div>
            <div class="detail">
                <div><p>${fn:length(sessionScope.selectedUpTripScheduleIDJava.transits)-1} Transit(s)</p></div>
            </div>
        </div>

        <c:if test="${sessionScope.tripType != null && sessionScope.tripType != '' && sessionScope.tripType == 'round'}">

            <div class="inboundDetails section">
                <div class="subHeading">
                    <div class="icon"><img src="../../resources/images/inbound.png" height="25px" width="25px"></div>
                    <div class="text"><span>Inbound</span></div>
                </div>
                <div class="detail">
                    <div class="label">From :</div>
                    <div class="value">${sessionScope.destinationAirport}</div>
                </div>
                <div class="detail">
                    <div class="label">To :</div>
                    <div class="value">${sessionScope.sourceAirport}</div>
                </div>
                <div class="detail">
                    <div class="label">Departure :</div>
                    <fmt:formatDate value="${sessionScope.selectedDownTripScheduleIDJava.transits[0].schedule.departDate}"
                                    var="formattedInboundDepartDate"
                                    pattern="MM/dd/yyyy"></fmt:formatDate>
                    <div class="value float-left">${formattedInboundDepartDate}</div>
                    <fmt:formatDate value="${sessionScope.selectedDownTripScheduleIDJava.transits[0].schedule.departTime}"
                                    var="formattedInboundDepartTime"
                                    pattern="HH:MM"></fmt:formatDate>
                    <div class="value">${formattedInboundDepartTime}</div>
                </div>
                <div class="detail">
                    <div class="label">Arrival :</div>
                    <fmt:formatDate value="${sessionScope.selectedDownTripScheduleIDJava.transits[fn:length(sessionScope.selectedDownTripScheduleIDJava.transits)-1].schedule.departDate}"
                                    var="formattedInboundArrivalDate"
                                    pattern="MM/dd/yyyy"></fmt:formatDate>
                    <div class="value float-left">${formattedInboundArrivalDate}</div>
                    <fmt:formatDate value="${sessionScope.selectedDownTripScheduleIDJava.transits[fn:length(sessionScope.selectedDownTripScheduleIDJava.transits)-1].schedule.departTime}"
                                    var="formattedInboundArrivalTime"
                                    pattern="HH:MM"></fmt:formatDate>
                    <div class="value">${formattedInboundArrivalTime}</div>
                </div>
                <div class="detail">
                    <div><p>${fn:length(sessionScope.selectedDownTripScheduleIDJava.transits)-1} Transit(s)</p></div>
                </div>
            </div>
        </c:if>

    </c:if>

    <c:if test="${sessionScope.numberOfAdultPassengers != null && sessionScope.numberOfAdultPassengers != ''}">

        <div class="passengerDetails section">
            <div class="subHeading">
                <div class="icon"><img src="../../resources/images/passenger_icon.png" height="25px" width="25px"></div>
                <div class="text"><span>Passenger(s)</span></div>
            </div>
            <div class="detail">
                <div class="label">Adult Passengers(s) :</div>
                <div class="value">${sessionScope.numberOfAdultPassengers}</div>
            </div>

            <c:if test="${sessionScope.numberOfChildPassengers != null && sessionScope.numberOfChildPassengers > 0}">
                <div class="detail">
                    <div class="label">Child Passengers(s) :</div>
                    <div class="value">${sessionScope.numberOfChildPassengers}</div>
                </div>
            </c:if>
            <c:if test="${sessionScope.numberOfInfantPassengers != null && sessionScope.numberOfInfantPassengers > 0}">
                <div class="detail">
                    <div class="label">Infant Passengers(s) :</div>
                    <div class="value">${sessionScope.numberOfInfantPassengers}</div>
                </div>
            </c:if>
        </div>


        <div class="costDetails section">
            <div class="subHeading">
                <div class="icon"><img src="../../resources/images/dollar_icon.png" height="25px" width="25px"></div>
                <div class="text"><span>Total Cost</span></div>
            </div>
            <div class="detail">
                <div class="label">Outbound</div>
                <fmt:formatNumber minFractionDigits="2" maxFractionDigits="2" value="${sessionScope.selectedUpTripScheduleIDJava.totalCost}" var="formattedOutBoundCost" />
                <div class="value">$ <div class="float-right">${formattedOutBoundCost}</div></div>
            </div>
            <c:if test="${sessionScope.tripType != null && sessionScope.tripType != '' && sessionScope.tripType == 'round'}">
                <div class="detail">
                    <div class="label">Inbound</div>
                    <fmt:formatNumber minFractionDigits="2" maxFractionDigits="2" value="${sessionScope.selectedDownTripScheduleIDJava.totalCost}" var="formattedInBoundCost" />
                    <div class="value">$ <div class="float-right">${formattedInBoundCost}</div></div>
                </div>
            </c:if>
            <div class="detail">
                <div class="label">Service Charges</div>
                <fmt:formatNumber minFractionDigits="2" maxFractionDigits="2" value="${sessionScope.serviceCharge}" var="formattedServiceCharge" />
                <div class="value">$ <div class="float-right">${formattedServiceCharge}</div></div>
            </div>
            <div class="detail">
                <div class="label">Total Charges</div>
                <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${sessionScope.selectedUpTripScheduleIDJava.totalCost + sessionScope.selectedDownTripScheduleIDJava.totalCost + sessionScope.serviceCharge}" var="formattedTotalCost" />
                <div class="value">$ <div class="float-right">${formattedTotalCost}</div></div>
            </div>
        </div>
    </c:if>


</div>