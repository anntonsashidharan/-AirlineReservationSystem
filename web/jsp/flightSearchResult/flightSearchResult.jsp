<%@ page import="com.airline.webservice.TravelClass" %>
<%@ page import="com.ars.system.APPStatics" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ARS | Search Result</title>
<link rel="stylesheet" href="/resources/javascript/jquery-ui.css">
<script src="/resources/javascript/jquery.js"></script>
<script src="/resources/javascript/jquery-ui.js"></script>
<script src="/resources/javascript/javascript.js"></script>
<script src="/resources/javascript/flightsearchresulthelper.js"></script>
<link rel="stylesheet" type="text/css" href="/resources/css/styles.css">

<script>
//global variable
var upTripsOriginal = <%=session.getAttribute(APPStatics.RequestStatics.UP_TRIP_SCHEDULES)%>;
var upTrips = upTripsOriginal;     //filtered trips

var downTripsOriginal = <%=session.getAttribute(APPStatics.RequestStatics.DOWN_TRIP_SCHEDULES)%>;
var downTrips = downTripsOriginal;

var filteredTripList=upTrips;

var tripEntryAsText = '<%@include file="/jsp/templates/flightSearchResult/tripEntry.jsp" %>';
var transitAsText = '<%@include file="/jsp/templates/flightSearchResult/transite.jsp" %>';
var transitWaitingTimeAsString = '<%@include file="/jsp/templates/flightSearchResult/transiteWaitingTime.jsp" %>';



var maxOutboundTimeUToFilter = getMaxOutboundTimeInMinutes(upTrips);
var minOutboundTimeUToFilter = getMinOutboundTimeInMinutes(upTrips);
var maxInboundTimeUToFilter = getMaxInboundTimeInMinutes(upTrips);
var minInboundTimeUToFilter = getMinInboundTimeInMinutes(upTrips);
var minNumberOfUpTransitsUToFilter = getMinTransitNumbers(upTrips);
var maxNumberOfUpTransitsUToFilter = getMaxTransitNumbers(upTrips);
var maxTravelHourUToFilter = getMaxTravelHours(upTrips);
var minTravelHourUToFilter = getMinTravelHours(upTrips);
var maxCostUToFilter = getMaxCost(upTrips);
var minCostUToFilter = getMinCost(upTrips);


$(document).ready(function () {
    if (upTrips != null && upTrips != "" && upTrips.length > 0) {
        var maxOutboundTime = getMaxOutboundTimeInMinutes(upTrips);
        var minOutboundTime = getMinOutboundTimeInMinutes(upTrips);
        $("#upTripOutboundTimeRangeSlider").slider({
            range: true,
            min: minOutboundTime,
            max: maxOutboundTime,
            values: [minOutboundTime, maxOutboundTime],
            slide: updateTimeLable,
            stop: filterTripsByOutboundTime
        });

        var maxInboundTime = getMaxInboundTimeInMinutes(upTrips);
        var minInboundTime = getMinInboundTimeInMinutes(upTrips);
        $("#upTripInboundTimeRangeSlider").slider({
            range: true,
            min: minInboundTime,
            max: maxInboundTime,
            values: [minInboundTime, maxInboundTime],
            slide: updateTimeLable,
            stop: filterTripsByInboundTime
        });

        var minNumberOfUpTransits = getMinTransitNumbers(upTrips);
        var maxNumberOfUpTransits = getMaxTransitNumbers(upTrips);
        $("#upTripNumOfTransitRangeSlider").slider({
            range: true,
            min: minNumberOfUpTransits,
            max: maxNumberOfUpTransits,
            values: [minNumberOfUpTransits, maxNumberOfUpTransits],	//have to calculate from result set
            slide: updateNumbertOfTransitLable,
            stop: filterTripsByNumberOfTransits
        });


        var maxTravelHour = getMaxTravelHours(upTrips);
        var minTravelHour = getMinTravelHours(upTrips);
        $("#upTripTravelHoursRangeSlider").slider({
            range: true,
            min: minTravelHour,
            max: maxTravelHour,
            values: [minTravelHour, maxTravelHour],
            slide: updateTotalTravelHoursLable,
            stop: filterTripsByTravelHours
        });

        var maxCost = getMaxCost(upTrips);
        var minCost = getMinCost(upTrips);
        $("#upTripCostRangeSlider").slider({
            range: true,
            min: minCost,
            max: maxCost,
            values: [minCost, maxCost],
            slide: updateCostLable,
            stop: filterTripsByCost
        });
    }

    //down trip filters
    if (downTrips != null && downTrips != "" && downTrips.length > 0) {
        var maxOutboundTimeD = getMaxOutboundTimeInMinutes(downTrips);
        var minOutboundTimeD = getMinOutboundTimeInMinutes(downTrips);
        $("#downTripOutboundTimeRangeSlider").slider({
            range: true,
            min: minOutboundTimeD,
            max: maxOutboundTimeD,
            values: [minOutboundTimeD, maxOutboundTimeD],
            slide: updateTimeLable,
            stop: filterTripsByOutboundTime
        });

        var maxInboundTimeD = getMaxInboundTimeInMinutes(downTrips);
        var minInboundTimeD = getMinInboundTimeInMinutes(downTrips);
        $("#downTripInboundTimeRangeSlider").slider({
            range: true,
            min: minInboundTimeD,
            max: maxInboundTimeD,
            values: [minInboundTimeD, maxInboundTimeD],
            slide: updateTimeLable,
            stop: filterTripsByInboundTime
        });

        var minNumberOfUpTransitsD = getMinTransitNumbers(downTrips);
        var maxNumberOfUpTransitsD = getMaxTransitNumbers(downTrips);
        $("#downTripNumOfTransitRangeSlider").slider({
            range: true,
            min: minNumberOfUpTransitsD,
            max: maxNumberOfUpTransitsD,
            values: [minNumberOfUpTransitsD, maxNumberOfUpTransitsD],	//have to calculate from result set
            slide: updateNumbertOfTransitLable,
            stop: filterTripsByNumberOfTransits
        });


        var maxTravelHourD = getMaxTravelHours(downTrips);
        var minTravelHourD = getMinTravelHours(downTrips);
        $("#downTripTravelHoursRangeSlider").slider({
            range: true,
            min: minTravelHourD,
            max: maxTravelHourD,
            values: [minTravelHourD, maxTravelHourD],
            slide: updateTotalTravelHoursLable,
            stop: filterTripsByTravelHours
        });

        var maxCostD = getMaxCost(downTrips);
        var minCostD = getMinCost(downTrips);
        $("#downTripCostRangeSlider").slider({
            range: true,
            min: minCostD,
            max: maxCostD,
            values: [minCostD, maxCostD],
            slide: updateCostLable,
            stop: filterTripsByCost
        });
    }

    if (upTrips != null && upTrips != "" && upTrips.length > 0) {
        $("#upTripOutboundTimeDetail>p").html(format24hr($("#upTripOutboundTimeRangeSlider").slider("values", 0)) +
                " TO " + format24hr($("#upTripOutboundTimeRangeSlider").slider("values", 1)));
        $("#upTripInboundTimeDetail>p").html(format24hr($("#upTripInboundTimeRangeSlider").slider("values", 0)) +
                " TO " + format24hr($("#upTripInboundTimeRangeSlider").slider("values", 1)));
        $("#upTripNumOfTransitDetail>p").html($("#upTripNumOfTransitRangeSlider").slider("values", 0) +
                " TO " + $("#upTripNumOfTransitRangeSlider").slider("values", 1));
        $("#upTripTravelHoursDetail>p").html($("#upTripTravelHoursRangeSlider").slider("values", 0) +
                " TO " + $("#upTripTravelHoursRangeSlider").slider("values", 1));
        $("#upTripCostDetail>p").html("$ " + $("#upTripCostRangeSlider").slider("values", 0) +
                " TO " + "$ " + $("#upTripCostRangeSlider").slider("values", 1));
    }

    if (downTrips != null && downTrips != "" && downTrips.length > 0) {
        $("#downTripOutboundTimeDetail>p").html(format24hr($("#downTripOutboundTimeRangeSlider").slider("values", 0)) +
                " TO " + format24hr($("#downTripOutboundTimeRangeSlider").slider("values", 1)));
        $("#downTripInboundTimeDetail>p").html(format24hr($("#downTripInboundTimeRangeSlider").slider("values", 0)) +
                " TO " + format24hr($("#downTripInboundTimeRangeSlider").slider("values", 1)));
        $("#downTripNumOfTransitDetail>p").html($("#downTripNumOfTransitRangeSlider").slider("values", 0) +
                " TO " + $("#downTripNumOfTransitRangeSlider").slider("values", 1));
        $("#downTripTravelHoursDetail>p").html($("#downTripTravelHoursRangeSlider").slider("values", 0) +
                " TO " + $("#downTripTravelHoursRangeSlider").slider("values", 1));
        $("#downTripCostDetail>p").html("$ " + $("#downTripCostRangeSlider").slider("values", 0) +
                " TO " + "$ " + $("#downTripCostRangeSlider").slider("values", 1));
    }

    $(".sort").click(function () {
        if ($(this).attr("clicked") == "false" || $(this).attr("clicked") == null || $(this).attr("clicked") == "") {
            $(this).attr("clicked", "true");
            $(this).siblings(".sort").attr("clicked", "false");

        } else {
            $(this).attr("clicked", "false");
        }

    });

    $("#viewUpTripFilters").click(function () {
        if ($("#upTripFilterPane").css("display") == "none") {
            $("#upTripFilterPane").show("slow");
            $(this).attr("src", "../../resources/images/up-arrow.png");
        } else {
            $("#upTripFilterPane").hide("slow");
            $(this).attr("src", "../../resources/images/down-arrow.png");
        }
    });
    $("#viewDownTripFilters").click(function () {
        if ($("#downTripFilterPane").css("display") == "none") {
            $("#downTripFilterPane").show("slow");
            $(this).attr("src", "../../resources/images/up-arrow.png");
        } else {
            $("#downTripFilterPane").hide("slow");
            $(this).attr("src", "../../resources/images/down-arrow.png");
        }
    });

    //render up trip details
    if (upTrips != null && upTrips != "" && upTrips.length > 0) {
        renderUpTripPane(upTrips);
    }
    if (downTrips != null && downTrips != "" && downTrips.length > 0) {
        renderDownTripPane(downTrips);
    }

    window.onclick = function (event) {
        //$(document).find(".transitesPanel").css("display","none");
    }



});
function sortUpTrip(element){
    if($(element).attr('id')=='upTripOutboundTimeSortDescending'){
        filteredTripList.sort(function(b,a){
            var x = new Date(a.transits[0].schedule.departTime);
            var y = new Date(b.transits[0].schedule.departTime);
            return (x.getHours() * 60 + x.getMinutes() + x.getSeconds())-(y.getHours() * 60 + y.getMinutes() + y.getSeconds());

        });
    }else if($(element).attr('id')=='upTripOutboundTimeSortAscending'){
        filteredTripList.sort(function(a,b){
            var x = new Date(a.transits[0].schedule.departTime);
            var y = new Date(b.transits[0].schedule.departTime);
            return (x.getHours() * 60 + x.getMinutes() + x.getSeconds())-(y.getHours() * 60 + y.getMinutes() + y.getSeconds());
        });
    }else if($(element).attr('id')=='upTripInboundTimeSortDescending'){
        filteredTripList.sort(function(b,a){
            var x = new Date(a.transits[a.transits.length - 1].schedule.arrivalTime);
            var y = new Date(b.transits[b.transits.length - 1].schedule.arrivalTime);
            return (x.getHours() * 60 + x.getMinutes() + x.getSeconds())-(y.getHours() * 60 + y.getMinutes() + y.getSeconds());

        });
    }else if($(element).attr('id')=='upTripInboundTimeSortAscending'){
        filteredTripList.sort(function(a,b){
            var x = new Date(a.transits[a.transits.length - 1].schedule.arrivalTime);
            var y = new Date(b.transits[b.transits.length - 1].schedule.arrivalTime);
            return (x.getHours() * 60 + x.getMinutes() + x.getSeconds())-(y.getHours() * 60 + y.getMinutes() + y.getSeconds());

        });
    }else if($(element).attr('id')=='upTripNumOfTransitDescending'){
        filteredTripList.sort(function(b,a){
            return a.transits.length - b.transits.length;
        });
    }else if($(element).attr('id')=='upTripNumOfTransitAscending'){
        filteredTripList.sort(function(a,b){
            return a.transits.length - b.transits.length;
        });
    }else if($(element).attr('id')=='upTripTravelHourDescending'){
        filteredTripList.sort(function(b,a){
            var departureDate1 = new Date(a.transits[0].schedule.departDate);
            var departureTime1 = new Date(a.transits[0].schedule.departTime);
            var arrivalDate1= new Date(a.transits[a.transits.length - 1].schedule.arrivalDate);
            var arrivalTime1 = new Date(a.transits[a.transits.length - 1].schedule.arrivalTime);
            var dateTimeDifference1 = getTimeDifference(departureDate1, departureTime1, arrivalDate1, arrivalTime1);
            var travelHours1 = parseFloat(dateTimeDifference1.days * 24) + parseFloat(dateTimeDifference1.hours) + parseFloat(dateTimeDifference1.minutes);


            var departureDate2 = new Date(b.transits[0].schedule.departDate);
            var departureTime2 = new Date(b.transits[0].schedule.departTime);
            var arrivalDate2 = new Date(b.transits[b.transits.length - 1].schedule.arrivalDate);
            var arrivalTime2 = new Date(b.transits[b.transits.length - 1].schedule.arrivalTime);
            var dateTimeDifference2 = getTimeDifference(departureDate2, departureTime2, arrivalDate2, arrivalTime2);
            var travelHours2 = parseFloat(dateTimeDifference2.days * 24) + parseFloat(dateTimeDifference2.hours) + parseFloat(dateTimeDifference2.minutes);


            return travelHours1-travelHours2;
        });
    }else if($(element).attr('id')=='upTripTravelHourAscending'){
        filteredTripList.sort(function(a,b){

            var departureDate1 = new Date(a.transits[0].schedule.departDate);
            var departureTime1 = new Date(a.transits[0].schedule.departTime);
            var arrivalDate1= new Date(a.transits[a.transits.length - 1].schedule.arrivalDate);
            var arrivalTime1 = new Date(a.transits[a.transits.length - 1].schedule.arrivalTime);
            var dateTimeDifference1 = getTimeDifference(departureDate1, departureTime1, arrivalDate1, arrivalTime1);
            var travelHours1 = parseFloat(dateTimeDifference1.days * 24) + parseFloat(dateTimeDifference1.hours) + parseFloat(dateTimeDifference1.minutes);


            var departureDate2 = new Date(b.transits[0].schedule.departDate);
            var departureTime2 = new Date(b.transits[0].schedule.departTime);
            var arrivalDate2 = new Date(b.transits[b.transits.length - 1].schedule.arrivalDate);
            var arrivalTime2 = new Date(b.transits[b.transits.length - 1].schedule.arrivalTime);
            var dateTimeDifference2 = getTimeDifference(departureDate2, departureTime2, arrivalDate2, arrivalTime2);
            var travelHours2 = parseFloat(dateTimeDifference2.days * 24) + parseFloat(dateTimeDifference2.hours) + parseFloat(dateTimeDifference2.minutes);


            return travelHours1-travelHours2;
        });
    }else if($(element).attr('id')=='upTripCostSortDescending'){
        filteredTripList.sort(function(b,a){
            return a.totalCost-b.totalCost;
        });
    }else if($(element).attr('id')=='upTripCostSortAscending'){
        filteredTripList.sort(function(a,b){
            return a.totalCost-b.totalCost;
        });
    }




    $("#upTripResult").empty();
    renderUpTripPane(filteredTripList);

}
function sortTestDescending(){
    filteredTripList.sort(sortDescendingByNumberTransit);
    $("#upTripResult").empty();
    renderUpTripPane(filteredTripList);
}

function redirect(url) {
    window.location = url;
}

</script>
</head>
<body>
<div>
<div id="topbar" class="topbar">
    <%@include file="/jsp/templates/menueBar.jsp" %>
    <%@include file="/jsp/templates/userLoginInfo.jsp" %>
</div>

<div id="test"></div>

<div class="contentPane searchResultContentPane" id="contentPane">
<c:if test="${requestScope.successMessage!=null}">
    <div id="flightSearchResultSuccess" class="flightSearchResultSuccess">
        <div class="successMessage">
            <p>${requestScope.successMessage}</p>
        </div>
    </div>
</c:if>
<c:if test="${requestScope.errorMessage!=null}">
    <div id="flightSearchResultError" class="flightSearchResultError">
        <div class="errorMessage">
            <p>${requestScope.errorMessage}</p>
        </div>
    </div>
</c:if>
<div id="flightSearchResultContainer" class="flightSearchResultContainer">
<form method="post" action="/flightSearchResult">


<!-- 2 - number of charecters in empty array "[]" -->
<div class="uptripPane tripPane" id="uptripPane">

    <div class="heading">
        <div class="text float-left" id="upTrip"><p>AAA to BBB</p></div>
        <div class="viewUpTripFilters showHidefilterButton float-right" id=""><img
                id="viewUpTripFilters" src="../../resources/images/down-arrow.png"></div>
    </div>
    <div style="height: 5px;"></div>

    <c:choose>
        <c:when test="${sessionScope.upTripSchedules != null && sessionScope.upTripSchedules != '' && sessionScope.upTripSchedules.length()  > 2}">

            <div class="upTripFilterPane tripfilterPane" id="upTripFilterPane" style="display: none">
                <div class="filter">
                    <div class="label"><p>Outbound Time</p></div>
                    <div class="input">
                        <p>

                        <div class="upTripOutboundTimeRangeSlider timerangeslider"
                             id="upTripOutboundTimeRangeSlider"></div>
                        </p>
                    </div>
                    <div class="upTripOutboundTimeDetail filterdetails" id="upTripOutboundTimeDetail"><p></p>
                    </div>
                    <div class="none upTripOutboundTimeSort sort" id=""><div><img src="../../resources/images/no-sort.png" height="100%" width="100%" /></div></div>
                    <div class="upTripOutboundTimeDescending upTripOutboundTimeSort sort" id="upTripOutboundTimeSortDescending" onclick="sortUpTrip(this);">
                        <div><img src="../../resources/images/sort-desced.png" height="100%" width="100%" />
                        </div>
                    </div>
                    <div class="upTripOutboundTimeAscending upTripOutboundTimeSort sort" id="upTripOutboundTimeSortAscending" onclick="sortUpTrip(this);" clicked="false">
                        <div><img src="../../resources/images/sort-ascend.png" height="100%" width="100%" /></div>
                    </div>

                </div>
                <div class="filter">
                    <div class="label"><p>Inbound Time</p></div>
                    <div class="input">
                        <p>

                        <div class="upTripInboundTimeRangeSlider timerangeslider input"
                             id="upTripInboundTimeRangeSlider"></div>
                        </p>
                    </div>
                    <div class="upTripInboundTimeDetail filterdetails" id="upTripInboundTimeDetail"><p></p>
                    </div>
                    <div class="none upTripOutboundTimeSort sort" id=""><div><img src="../../resources/images/no-sort.png" height="100%" width="100%" /></div></div>
                    <div class="upTripOutboundTimeDescending upTripOutboundTimeSort sort" id="upTripInboundTimeSortDescending" onclick="sortUpTrip(this);">
                        <div><img src="../../resources/images/sort-desced.png" height="100%" width="100%"/>
                        </div>
                    </div>
                    <div class="upTripOutboundTimeAscending upTripOutboundTimeSort  sort" id="upTripInboundTimeSortAscending" onclick="sortUpTrip(this);">
                        <div><img src="../../resources/images/sort-ascend.png" height="100%" width="100%" /></div>
                    </div>
                </div>

                <div class="filter">
                    <div class="label"><p>Number of Transit</p></div>
                    <div class="input">
                        <p>

                        <div class="upTripNumOfTransitRangeSlider numOfTransitslider input"
                             id="upTripNumOfTransitRangeSlider"></div>
                        </p>
                    </div>
                    <div class="upTripNumOfTransitDetail filterdetails" id="upTripNumOfTransitDetail"><p></p>
                    </div>
                    <div class="none upTripOutboundTimeSort sort" id=""><div><img src="../../resources/images/no-sort.png" height="100%" width="100%" /></div></div>
                    <div class="upTripOutboundTimeDescending upTripOutboundTimeSort sort" id="upTripNumOfTransitDescending" onclick="sortUpTrip(this);">
                        <div><img src="../../resources/images/sort-desced.png" height="100%" width="100%" />
                        </div>
                    </div>
                    <div class="upTripOutboundTimeAscending upTripOutboundTimeSort  sort" id="upTripNumOfTransitAscending" onclick="sortUpTrip(this);">
                        <div><img src="../../resources/images/sort-ascend.png" height="100%" width="100%" /></div>
                    </div>
                </div>

                <div class="filter">
                    <div class="label"><p>Total Travel Hours</p></div>
                    <div class="input">
                        <p>

                        <div class="upTripTravelHoursRangeSlider travelHours input"
                             id="upTripTravelHoursRangeSlider"></div>
                        </p>
                    </div>
                    <div class="upTripTravelHoursDetail filterdetails" id="upTripTravelHoursDetail"><p></p>
                    </div>
                    <div class="none upTripOutboundTimeSort sort" id=""><div><img src="../../resources/images/no-sort.png" height="100%" width="100%" /></div></div>
                    <div class="upTripOutboundTimeDescending upTripOutboundTimeSort sort" id="upTripTravelHourDescending"  onclick="sortUpTrip(this);">
                        <div><img src="../../resources/images/sort-desced.png" height="100%" width="100%" />
                        </div>
                    </div>
                    <div class="upTripOutboundTimeAscending upTripOutboundTimeSort  sort" id="upTripTravelHourAscending"  onclick="sortUpTrip(this);">
                        <div><img src="../../resources/images/sort-ascend.png" height="100%" width="100%" /></div>
                    </div>
                </div>

                <div class="filter">
                    <div class="label"><p>Total Cost</p></div>
                    <div class="input">
                        <p>

                        <div class="upTripCostRangeSlider cost input" id="upTripCostRangeSlider"></div>
                        </p>
                    </div>
                    <div class="upTripCostDetail filterdetails" id="upTripCostDetail"><p></p></div>
                    <div class="none upTripCostSort sort" id=""><div><img src="../../resources/images/no-sort.png" height="100%" width="100%" /></div></div>
                    <div class="upTripCostDescending upTripCostSort sort" id="upTripCostSortDescending" onclick="sortUpTrip(this);">
                        <div><img src="../../resources/images/sort-desced.png" height="100%" width="100%" />
                        </div>
                    </div>
                    <div class="upTripCostAscending upTripCostSort  sort" id="upTripCostSortAscending" onclick="sortUpTrip(this);">
                        <div><img src="../../resources/images/sort-ascend.png" height="100%" width="100%" /></div>
                    </div>
                </div>
            </div>
            <div style="height: 5px;"></div>

            <!-- Will be rendered by javascript -->
            <div class="upTripResult tripResult" id="upTripResult">


            </div>

        </c:when>

        <c:otherwise>
            <div class="message">
                <div>Sorry No Outbound Schedule Found Matching Your Requirement</div>
            </div>
        </c:otherwise>
    </c:choose>
</div>


<c:if test="${sessionScope.tripType=='round'}">

    <br><br><br><br><br>

    <div class="downtripPane tripPane" id="downtripPane">

        <div class="heading">
            <div class="text" id="downTrip"><p>BBB to AAA</p></div>
            <div class="viewDownTripFilters showHidefilterButton float-right" id=""><img
                    id="viewDownTripFilters" src="../../resources/images/down-arrow.png"></div>
        </div>
        <div style="height: 5px;"></div>


        <c:choose>
            <c:when test="${sessionScope.downTripSchedules != null && sessionScope.downTripSchedules != '' && sessionScope.downTripSchedules.length()  > 2 }">

                <div class="downTripFilterPane tripfilterPane" id="downTripFilterPane" style="display: none">
                    <div class="filter">
                        <div class="label"><p>Outbound Time</p></div>
                        <div class="input">
                            <p>

                            <div class="downTripOutboundTimeRangeSlider timerangeslider input"
                                 id="downTripOutboundTimeRangeSlider"></div>
                            </p>
                        </div>
                        <div class="downTripOutboundTimeDetail filterdetails" id="downTripOutboundTimeDetail">
                            <p></p></div>
                        <div class="none upTripOutboundTimeSort sort" id=""><div><img src="../../resources/images/no-sort.png" height="100%" width="100%" /></div></div>
                        <div class="upTripOutboundTimeDescending upTripOutboundTimeSort sort" id="">
                            <div><img src="../../resources/images/sort-desced.png" height="100%" width="100%" />
                            </div>
                        </div>
                        <div class="upTripOutboundTimeAscending upTripOutboundTimeSort  sort" id="">
                            <div><img src="../../resources/images/sort-ascend.png" height="100%" width="100%" /></div>
                        </div>

                    </div>
                    <div class="filter">
                        <div class="label"><p>Inbound Time</p></div>
                        <div class="input">
                            <p>

                            <div class="downTripInboundTimeRangeSlider timerangeslider input"
                                 id="downTripInboundTimeRangeSlider"></div>
                            </p>
                        </div>
                        <div class="downTripInboundTimeDetail filterdetails" id="downTripInboundTimeDetail"><p></p>
                        </div>
                        <div class="none upTripOutboundTimeSort sort" id=""><div><img src="../../resources/images/no-sort.png" height="100%" width="100%" /></div></div>
                        <div class="upTripOutboundTimeDescending upTripOutboundTimeSort sort" id="">
                            <div><img src="../../resources/images/sort-desced.png" height="100%" width="100%" />
                            </div>
                        </div>
                        <div class="upTripOutboundTimeAscending upTripOutboundTimeSort  sort" id="">
                            <div><img src="../../resources/images/sort-ascend.png" height="100%" width="100%" /></div>
                        </div>
                    </div>
                    <div class="filter">
                        <div class="label"><p>Number of Transit</p></div>
                        <div class="input">
                            <p>

                            <div class="downTripNumOfTransitRangeSlider numOfTransitslider input"
                                 id="downTripNumOfTransitRangeSlider"></div>
                            </p>
                        </div>
                        <div class="downTripNumOfTransitDetail filterdetails" id="downTripNumOfTransitDetail">
                            <p></p></div>
                        <div class="none upTripOutboundTimeSort sort" id=""><div><img src="../../resources/images/no-sort.png" height="100%" width="100%" /></div></div>
                        <div class="upTripOutboundTimeDescending upTripOutboundTimeSort sort" id="">
                            <div><img src="../../resources/images/sort-desced.png" height="100%" width="100%" />
                            </div>
                        </div>
                        <div class="upTripOutboundTimeAscending upTripOutboundTimeSort  sort" id="">
                            <div><img src="../../resources/images/sort-ascend.png" height="100%" width="100%" /></div>
                        </div>
                    </div>
                    <div class="filter">
                        <div class="label"><p>Total Travel Hours</p></div>
                        <div class="input">
                            <p>

                            <div class="downTripTravelHoursRangeSlider travelHours input"
                                 id="downTripTravelHoursRangeSlider"></div>
                            </p>
                        </div>
                        <div class="downTripTravelHoursDetail filterdetails" id="downTripTravelHoursDetail"><p></p>
                        </div>
                        <div class="none upTripOutboundTimeSort sort" id=""><div><img src="../../resources/images/no-sort.png" height="100%" width="100%" /></div></div>
                        <div class="upTripOutboundTimeDescending upTripOutboundTimeSort sort" id="">
                            <div><img src="../../resources/images/sort-desced.png" height="100%" width="100%" />
                            </div>
                        </div>
                        <div class="upTripOutboundTimeAscending upTripOutboundTimeSort  sort" id="">
                            <div><img src="../../resources/images/sort-ascend.png" height="100%" width="100%" /></div>
                        </div>
                    </div>
                    <div class="filter">
                        <div class="label"><p>Total Cost</p></div>
                        <div class="input">
                            <p>

                            <div class="downTripCostRangeSlider cost input" id="downTripCostRangeSlider"></div>
                            </p>
                        </div>
                        <div class="downTripCostDetail filterdetails" id="downTripCostDetail"><p></p></div>
                        <div class="none downTripCostSort sort" id=""><div><img src="../../resources/images/no-sort.png" height="100%" width="100%" /></div></div>
                        <div class="downTripCostDescending downTripCostSort sort" id="">
                            <div><img src="../../resources/images/sort-desced.png" height="100%" width="100%" />
                            </div>
                        </div>
                        <div class="downTripCostAscending downTripCostSort  sort" id="">
                            <div><img src="../../resources/images/sort-ascend.png" height="100%" width="100%" /></div>
                        </div>
                    </div>
                </div>
                <div style="height: 5px;"></div>


                <div class="downTripResult tripResult" id="downTripResult">


                </div>
            </c:when>

            <c:otherwise>
                <div class="message">
                    <div>Sorry No Inbound Schedule Found Matching Your Requirement</div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>


</c:if>


<div class="proceedButtonDiv">
    <div class="proceedButton">
        <p><input class="button-standard-size button-default" type="submit" name="submit"
                  value="Proceed"></p>
    </div>
    <div class="tryNewSearchButton">
        <p><input class="button-standard-size button-default" type="button" name="tryNewSearch"
                  onclick="redirect('/main')"
                  value="Try New Search"></p>
    </div>
</div>
</form>
</div>

</div>

<div class="footer">
    <%@include file="../templates/footer.jsp" %>
</div>
</div>
<script type="text/javascript">

</script>
</body>


</html>