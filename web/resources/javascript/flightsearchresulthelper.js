/**
 * Created by JJ on 8/24/16.
 */

//update labels while sliding

function updateTimeLable(event, ui) {
    var id = $(this).prop("id");
    var min = ui.values[0];
    var max = ui.values[1];
    var lableToUpdate;
    if (id == "upTripOutboundTimeRangeSlider") {
        lableToUpdate = "#upTripOutboundTimeDetail>p";
    } else if (id == "upTripInboundTimeRangeSlider") {
        lableToUpdate = "#upTripInboundTimeDetail>p";
    }
    else if (id == "downTripOutboundTimeRangeSlider") {
        lableToUpdate = "#downTripOutboundTimeDetail>p";
    } else if (id == "downTripInboundTimeRangeSlider") {
        lableToUpdate = "#downTripInboundTimeDetail>p";
    }
    $(lableToUpdate).html(format24hr(min) +
        " TO " + format24hr(max));
}

function updateNumbertOfTransitLable(event, ui) {
    var id = $(this).prop("id");
    var min = ui.values[0];
    var max = ui.values[1];
    var lableToUpdate;
    if (id == "upTripNumOfTransitRangeSlider") {
        lableToUpdate = "#upTripNumOfTransitDetail>p";
    } else if (id == "downTripNumOfTransitRangeSlider") {
        lableToUpdate = "#downTripNumOfTransitDetail>p";
    }
    $(lableToUpdate).html(min +
        " TO " + max);
}

function updateTotalTravelHoursLable(event, ui) {
    var id = $(this).prop("id");
    var min = ui.values[0];
    var max = ui.values[1];
    var lableToUpdate;
    if (id == "upTripTravelHoursRangeSlider") {
        lableToUpdate = "#upTripTravelHoursDetail>p";
    } else if (id == "downTripTravelHoursRangeSlider") {
        lableToUpdate = "#downTripTravelHoursDetail>p";
    }
    $(lableToUpdate).html(min +
        " TO " + max);
}

function updateCostLable(event, ui) {
    var id = $(this).prop("id");
    var min = ui.values[0];
    var max = ui.values[1];
    var lableToUpdate;
    if (id == "upTripCostRangeSlider") {
        lableToUpdate = "#upTripCostDetail>p";
    } else if (id == "downTripCostRangeSlider") {
        lableToUpdate = "#downTripCostDetail>p";
    }
    $(lableToUpdate).html("$ " + min +
        " TO " + "$ " + max);
}


function renderTripPane(listOfTrips) {
    //$("#test").append(JSON.stringify(listOfTrips)+"<br><br><br><br><br>");
    //var listOfTripsJSONArray = $.parseJSON(listOfTrips);
    var emptyDom = $.parseHTML("<div></div>");
    $("#test").empty();

    $.each(listOfTrips, function (key, value) {
        //$("#test").append(key + "<br>" + JSON.stringify(value) + "<br>");
        //$("#test").append( "<br><br><br>" +new Date().getMilliseconds() + " , " + getMonthMMM(new Date(Date.parse(value.transits[0].schedule.arrivalDate)).getMonth()) + " , " + value.transits[0].schedule.arrivalDate + "<br>" + new Date(Date.parse(value.transits[0].schedule.arrivalTime)) + " , " + value.transits[0].schedule.arrivalTime + "<br><br><br>");

        var domElem = $.parseHTML(tripEntryAsText);

        var departureDate = new Date(Date.parse(value.transits[0].schedule.departDate));
        var departureTime = new Date(Date.parse(value.transits[0].schedule.departTime));
        var arrivalDate = new Date(Date.parse(value.transits[value.transits.length - 1].schedule.arrivalDate));
        var arrivalTime = new Date(Date.parse(value.transits[value.transits.length - 1].schedule.arrivalTime));

        $(domElem).find("#day").html(departureDate.getDate());
        $(domElem).find("#month").html(getMonthMMM(departureDate.getMonth()));
        $(domElem).find("#year").html(departureDate.getFullYear());
        var time = ("00" + departureTime.getHours()).slice(-2) + ":" + ("00" + departureTime.getMinutes()).slice(-2);
        $(domElem).find("#departureTime").html(time);
        time = ("00" + arrivalTime.getHours()).slice(-2) + ":" + ("00" + arrivalTime.getMinutes()).slice(-2);
        $(domElem).find("#arrivalTime").html(time);
        $(domElem).find("#numberOfTransites").html(value.transits.length - 1 + " transit(s)");
        var logo = value.transits[0].schedule.flight.flightNumber.substr(0, 2).toUpperCase();
        $(domElem).find("#airlineLogoImage").prop("src", "/resources/images/" + logo + ".gif");
        $(domElem).find("#airlineName").html(value.transits[0].schedule.airlineCompany);
        $(domElem).find("#price").html("$ " + Number(value.totalCost).toFixed(2));
        $(domElem).find("#tripID").val(value.tripID);

        //transite summery
        var dateTimeDifference = getTimeDifference(departureDate, departureTime, arrivalDate, arrivalTime);
        $(domElem).find(".trasiteSummery>#departuredatetime").html(formatDateMMDDYYYY(departureDate) + " - " + ("00" + departureTime.getHours()).slice(-2) + ":" + ("00" + departureTime.getMinutes()).slice(-2));
        $(domElem).find(".trasiteSummery>#arrivaldatetime").html(formatDateMMDDYYYY(value.transits[value.transits.length - 1].schedule.arrivalDate) + " - " + ("00" + arrivalTime.getHours()).slice(-2) + ":" + ("00" + arrivalTime.getMinutes()).slice(-2));
        $(domElem).find(".trasiteSummery>.duration").html(dateTimeDifference.days + " day(s)  " + ("00" + dateTimeDifference.hours).slice(-2) + " hr(s)  " + ("00" + dateTimeDifference.minutes).slice(-2) + " min(s)");

        //manipulate transit detail panel
        $.each(value.transits, function (key, transite) {
            var transites = $(domElem).find(".transites");

            var domTransite = $.parseHTML(transitAsText);
            //from section
            $(domTransite).find(".from>.airportCode").html(transite.schedule.flight.fromAirport.code);
            $(domTransite).find(".from>.airportDetail").html(transite.schedule.flight.fromAirport.name + ", " + transite.schedule.flight.fromAirport.city + ", " + transite.schedule.flight.fromAirport.country);

            //to section
            $(domTransite).find(".to>.airportCode").html(transite.schedule.flight.toAirport.code);
            $(domTransite).find(".to>.airportDetail").html(transite.schedule.flight.toAirport.name + ", " + transite.schedule.flight.toAirport.city + ", " + transite.schedule.flight.toAirport.country);

            //departure section
            var departDate = new Date(transite.schedule.departDate);
            var departTime = new Date(transite.schedule.departTime);
            $(domTransite).find(".departure>.departreDetails").html(formatDateMMDDYYYY(departDate) + " - " + ("00" + departTime.getHours()).slice(-2) + ":" + ("00" + departTime.getMinutes()).slice(-2));

            //arrival section
            var arrDate = new Date(transite.schedule.arrivalDate);
            var arrTime = new Date(transite.schedule.arrivalTime);
            $(domTransite).find(".arrival>.arrivalDetails").html(formatDateMMDDYYYY(arrDate) + " - " + ("00" + arrTime.getHours()).slice(-2) + ":" + ("00" + arrTime.getMinutes()).slice(-2));

            //duration section
            var dateTimeDifferenceInner = getTimeDifference(departDate, departTime, arrDate, arrTime);
            $(domTransite).find(".duration>.durationValue").html(("00" + dateTimeDifferenceInner.hours).slice(-2) + " hr(s)  " + ("00" + dateTimeDifferenceInner.minutes).slice(-2) + " min(s)");

            //airline info section
            var logoPrefix = transite.schedule.flight.flightNumber.substr(0, 2).toUpperCase();
            $(domTransite).find(".airline img").prop("src", "/resources/images/" + logoPrefix + ".gif");
            $(domTransite).find(".airline>.airlineName").html(transite.schedule.airlineCompany);
            $(domTransite).find(".airline>.flightNumber").html(transite.schedule.flight.flightNumber);
            transites.append(domTransite);

            if (key < value.transits.length - 1) {
                var domTransiteWaitingHours = $.parseHTML(transitWaitingTimeAsString);
                var netxDepartDate = new Date(value.transits[key + 1].schedule.departDate);
                var netxDepartTime = new Date(value.transits[key + 1].schedule.departTime);
                var transiteWaitingTime = getTimeDifference(arrDate, arrTime, netxDepartDate, netxDepartTime);
                $(domTransiteWaitingHours).find(".waitingHours").html(transiteWaitingTime.days + " day(s)  " + ("00" + transiteWaitingTime.hours).slice(-2) + " hr(s)  " + ("00" + transiteWaitingTime.minutes).slice(-2) + " min(s)");
                transites.append(domTransiteWaitingHours);
            }
        });

        //register event to show transite details button
        $(domElem).find("#showTransiteDetailsButton").click(function () {

            var details = $(this).parent().find("#transitesPanel");
            if (details.css("display") == "none") {
                $(document).find(".transitesPanel").hide("fast");
                details.show("slow");
            } else {
                details.hide("slow");
            }
        });


        $(emptyDom).append(domElem);
        //    if(key=="scheduleId"){
        //        $(domElem).find("#price").html(value);
        //        $("#upTripResult").append(domElem);
        //    }
    });
    return emptyDom;
}

function renderUpTripPane(listOfTrips) {
    var dom = $(renderTripPane(listOfTrips));
    $("#upTrip>p").text(listOfTrips[0].from.code + " TO " + listOfTrips[0].to.code);
    dom.find(".tripID").attr("name", "upTripID");
    $("#upTripResult").append(dom.children());
}
function renderDownTripPane(listOfTrips) {
    var dom = $(renderTripPane(listOfTrips));
    $("#downTrip>p").text(listOfTrips[0].from.code + " TO " + listOfTrips[0].to.code);
    dom.find(".tripID").attr("name", "downTripID");
    $("#downTripResult").append(dom.children());
}

//get min, max of filter properties

function getMaxCost(listOfTrips) {
    var maxCost = listOfTrips[0].totalCost;
    for (var i = 1; i < listOfTrips.length; i++) {
        var maxCostTemp = listOfTrips[i].totalCost;
        if (maxCostTemp > maxCost) {
            maxCost = maxCostTemp;
        }
    }
    return maxCost;
}
function getMinCost(listOfTrips) {
    var minCost = listOfTrips[0].totalCost;
    for (var i = 1; i < listOfTrips.length; i++) {
        var minCostTemp = listOfTrips[i].totalCost;
        if (minCostTemp < minCost) {
            minCost = minCostTemp;
        }
    }
    return minCost;
}

function getMaxTransitNumbers(listOfTrips) {
    var maxTransitNumbers = listOfTrips[0].transits.length - 1;
    for (var i = 1; i < listOfTrips.length; i++) {
        var maxTransitNumbersTemp = listOfTrips[i].transits.length - 1;
        if (maxTransitNumbersTemp > maxTransitNumbers) {
            maxTransitNumbers = maxTransitNumbersTemp;
        }
    }
    return maxTransitNumbers;
}
function getMinTransitNumbers(listOfTrips) {
    var minTransitNumbers = listOfTrips[0].transits.length - 1;
    for (var i = 1; i < listOfTrips.length; i++) {
        var minTransitNumbersTemp = listOfTrips[i].transits.length - 1;
        if (minTransitNumbersTemp < minTransitNumbers) {
            minTransitNumbers = minTransitNumbersTemp;
        }
    }
    return minTransitNumbers;
}

function getMaxTravelHours(listOfTrips) {
    var departureDate = new Date(listOfTrips[0].transits[0].schedule.departDate);
    var departureTime = new Date(listOfTrips[0].transits[0].schedule.departTime);
    var arrivalDate = new Date(listOfTrips[0].transits[listOfTrips[0].transits.length - 1].schedule.arrivalDate);
    var arrivalTime = new Date(listOfTrips[0].transits[listOfTrips[0].transits.length - 1].schedule.arrivalTime);
    var dateTimeDifference = getTimeDifference(departureDate, departureTime, arrivalDate, arrivalTime);
    var maxTravelHour = parseFloat(dateTimeDifference.days * 24) + parseFloat(dateTimeDifference.hours) + parseFloat(dateTimeDifference.minutes > 0 || dateTimeDifference.seconds > 0 ? 1 : 0);
    for (var i = 1; i < listOfTrips.length; i++) {
        departureDate = new Date(listOfTrips[i].transits[0].schedule.departDate);
        departureTime = new Date(listOfTrips[i].transits[0].schedule.departTime);
        arrivalDate = new Date(listOfTrips[i].transits[listOfTrips[i].transits.length - 1].schedule.arrivalDate);
        arrivalTime = new Date(listOfTrips[i].transits[listOfTrips[i].transits.length - 1].schedule.arrivalTime);
        dateTimeDifference = getTimeDifference(departureDate, departureTime, arrivalDate, arrivalTime);
        var maxTravelHourTemp = parseFloat(dateTimeDifference.days * 24) + parseFloat(dateTimeDifference.hours) + parseFloat(dateTimeDifference.minutes > 0 || dateTimeDifference.seconds > 0 ? 1 : 0);
        if (maxTravelHourTemp > maxTravelHour) {
            maxTravelHour = maxTravelHourTemp;
        }
    }
    return maxTravelHour;
}

function getMinTravelHours(listOfTrips) {
    var departureDate = new Date(listOfTrips[0].transits[0].schedule.departDate);
    var departureTime = new Date(listOfTrips[0].transits[0].schedule.departTime);
    var arrivalDate = new Date(listOfTrips[0].transits[listOfTrips[0].transits.length - 1].schedule.arrivalDate);
    var arrivalTime = new Date(listOfTrips[0].transits[listOfTrips[0].transits.length - 1].schedule.arrivalTime);
    var dateTimeDifference = getTimeDifference(departureDate, departureTime, arrivalDate, arrivalTime);
    var minTravelHour = parseFloat(dateTimeDifference.days * 24) + parseFloat(dateTimeDifference.hours);
    for (var i = 1; i < listOfTrips.length; i++) {
        departureDate = new Date(listOfTrips[i].transits[0].schedule.departDate);
        departureTime = new Date(listOfTrips[i].transits[0].schedule.departTime);
        arrivalDate = new Date(listOfTrips[i].transits[listOfTrips[i].transits.length - 1].schedule.arrivalDate);
        arrivalTime = new Date(listOfTrips[i].transits[listOfTrips[i].transits.length - 1].schedule.arrivalTime);
        dateTimeDifference = getTimeDifference(departureDate, departureTime, arrivalDate, arrivalTime);
        var minTravelHourTemp = parseFloat(dateTimeDifference.days * 24) + parseFloat(dateTimeDifference.hours);
        if (minTravelHourTemp < minTravelHour) {
            minTravelHour = minTravelHourTemp;
        }
    }
    return minTravelHour;
}

function getMaxOutboundTimeInMinutes(listOfTrips) {
    var departureTime = new Date(listOfTrips[0].transits[0].schedule.departTime);
    var maxOutboundTime = departureTime.getHours() * 60 + departureTime.getMinutes() + parseFloat(departureTime.getSeconds() > 0 ? 1 : 0);
    for (var i = 1; i < listOfTrips.length; i++) {
        departureTime = new Date(listOfTrips[i].transits[0].schedule.departTime);
        var maxOutboundTimeTemp = departureTime.getHours() * 60 + departureTime.getMinutes() + parseFloat(departureTime.getSeconds() > 0 ? 1 : 0);
        if (maxOutboundTimeTemp > maxOutboundTime) {
            maxOutboundTime = maxOutboundTimeTemp;
        }
    }
    return maxOutboundTime;
}

function getMinOutboundTimeInMinutes(listOfTrips) {
    var departureTime = new Date(listOfTrips[0].transits[0].schedule.departTime);
    var minOutboundTime = departureTime.getHours() * 60 + departureTime.getMinutes();
    for (var i = 1; i < listOfTrips.length; i++) {
        departureTime = new Date(listOfTrips[i].transits[0].schedule.departTime);
        var minOutboundTimeTemp = departureTime.getHours() * 60 + departureTime.getMinutes();
        if (minOutboundTimeTemp < minOutboundTime) {
            minOutboundTime = minOutboundTimeTemp;
        }
    }
    return minOutboundTime;
}


function getMaxInboundTimeInMinutes(listOfTrips) {
    var departureTime = new Date(listOfTrips[0].transits[listOfTrips[0].transits.length - 1].schedule.arrivalTime);
    var maxInboundTime = departureTime.getHours() * 60 + departureTime.getMinutes() + parseFloat(departureTime.getSeconds() > 0 ? 1 : 0);
    for (var i = 1; i < listOfTrips.length; i++) {
        departureTime = new Date(listOfTrips[i].transits[listOfTrips[i].transits.length - 1].schedule.arrivalTime);
        var maxInboundTimeTemp = departureTime.getHours() * 60 + departureTime.getMinutes() + parseFloat(departureTime.getSeconds() > 0 ? 1 : 0);
        if (maxInboundTimeTemp > maxInboundTime) {
            maxInboundTime = maxInboundTimeTemp;
        }
    }
    return maxInboundTime;
}

function getMinInboundTimeInMinutes(listOfTrips) {
    var departureTime = new Date(listOfTrips[0].transits[listOfTrips[0].transits.length - 1].schedule.arrivalTime);
    var minInboundTime = departureTime.getHours() * 60 + departureTime.getMinutes();
    for (var i = 1; i < listOfTrips.length; i++) {
        departureTime = new Date(listOfTrips[i].transits[listOfTrips[i].transits.length - 1].schedule.arrivalTime);
        var minInboundTimeTemp = departureTime.getHours() * 60 + departureTime.getMinutes();
        if (minInboundTimeTemp < minInboundTime) {
            minInboundTime = minInboundTimeTemp;
        }
    }
    return minInboundTime;
}


//filter trip panes for slider event

/*
 function filterTripsByOutboundTime(event, ui) {
 var id = $(this).prop("id");
 var min = ui.values[0];
 var max = ui.values[1];
 var filteredTripList = [];
 if (id == "upTripOutboundTimeRangeSlider") {
 for (var i = 0; i < upTrips.length; i++) {
 var departureTime = new Date(upTrips[i].transits[0].schedule.departTime);
 var departureTimeInMinutes = departureTime.getHours() * 60 + departureTime.getMinutes() + parseFloat(departureTime.getSeconds() > 0 ? 1 : 0);
 if (departureTimeInMinutes >= parseFloat(min) && departureTimeInMinutes <= parseFloat(max)) {
 filteredTripList.push(upTrips[i]);
 }
 }
 //upTrips = filteredTripList;
 $("#upTripResult").empty();
 renderUpTripPane(filteredTripList);
 } else if (id == "downTripOutboundTimeRangeSlider") {
 }
 }

 function filterTripsByInboundTime(event, ui) {
 var id = $(this).prop("id");
 var min = ui.values[0];
 var max = ui.values[1];
 var filteredTripList = [];
 if (id == "upTripInboundTimeRangeSlider") {
 for (var i = 0; i < upTrips.length; i++) {
 var arrivalTime = new Date(upTrips[i].transits[upTrips[i].transits.length - 1].schedule.arrivalTime);
 var arrivalTimeInMinutes = arrivalTime.getHours() * 60 + arrivalTime.getMinutes() + parseFloat(arrivalTime.getSeconds() > 0 ? 1 : 0);
 if (arrivalTimeInMinutes >= parseFloat(min) && arrivalTimeInMinutes <= parseFloat(max)) {
 filteredTripList.push(upTrips[i]);
 }
 }
 //upTrips = filteredTripList;
 $("#upTripResult").empty();
 renderUpTripPane(filteredTripList);
 } else if (id == "downTripInboundTimeRangeSlider") {
 }
 }

 function filterTripsByNumberOfTransits(event, ui) {
 var id = $(this).prop("id");
 var min = ui.values[0];
 var max = ui.values[1];
 var filteredTripList = [];
 if (id == "upTripNumOfTransitRangeSlider") {
 for (var i = 0; i < upTrips.length; i++) {
 if (parseFloat(upTrips[i].transits.length-1) >= parseFloat(min) && parseFloat(upTrips[i].transits.length-1) <= parseFloat(max)) {
 filteredTripList.push(upTrips[i]);
 }
 }
 //upTrips = filteredTripList;
 $("#upTripResult").empty();
 renderUpTripPane(filteredTripList);
 } else if (id == "downTripNumOfTransitRangeSlider") {
 }
 }

 function filterTripsByTravelHours(event, ui) {
 var id = $(this).prop("id");
 var min = ui.values[0];
 var max = ui.values[1];
 var filteredTripList = [];
 if (id == "upTripTravelHoursRangeSlider") {
 for (var i = 0; i < upTrips.length; i++) {
 var departureDate = new Date(upTrips[i].transits[0].schedule.departDate);
 var departureTime = new Date(upTrips[i].transits[0].schedule.departTime);
 var arrivalDate = new Date(upTrips[i].transits[upTrips[i].transits.length - 1].schedule.arrivalDate);
 var arrivalTime = new Date(upTrips[i].transits[upTrips[i].transits.length - 1].schedule.arrivalTime);
 var dateTimeDifference = getTimeDifference(departureDate, departureTime, arrivalDate, arrivalTime);
 var travelHours = parseFloat(dateTimeDifference.days * 24) + parseFloat(dateTimeDifference.hours) + parseFloat(dateTimeDifference.minutes > 0 || dateTimeDifference.seconds > 0 ? 1 : 0);
 if (travelHours >= parseFloat(min) && travelHours <= parseFloat(max)) {
 filteredTripList.push(upTrips[i]);
 }
 }
 //upTrips = filteredTripList;
 $("#upTripResult").empty();
 renderUpTripPane(filteredTripList);
 } else if (id == "downTripTravelHoursRangeSlider") {
 }
 }

 function filterTripsByCost(event, ui) {
 var id = $(this).prop("id");
 var min = ui.values[0];
 var max = ui.values[1];
 var filteredTripList = [];
 if (id == "upTripCostRangeSlider") {
 for (var i = 0; i < upTrips.length; i++) {
 if (parseFloat(upTrips[i].totalCost) >= parseFloat(min) && parseFloat(upTrips[i].totalCost) <= parseFloat(max)) {
 filteredTripList.push(upTrips[i]);
 }
 }
 //upTrips = filteredTripList;
 $("#upTripResult").empty();
 renderUpTripPane(filteredTripList);
 } else if (id == "downTripCostRangeSlider") {
 }
 }

 */

function filterTripsByOutboundTime(event, ui) {
    var id = $(this).prop("id");
    var min = ui.values[0];
    var max = ui.values[1];
    maxOutboundTimeUToFilter = max;
    minOutboundTimeUToFilter = min;
    filterUpTrips()
}

function filterTripsByInboundTime(event, ui) {
    var id = $(this).prop("id");
    var min = ui.values[0];
    var max = ui.values[1];
    maxInboundTimeUToFilter = max;
    minInboundTimeUToFilter = min;
    filterUpTrips()
}

function filterTripsByNumberOfTransits(event, ui) {
    var id = $(this).prop("id");
    var min = ui.values[0];
    var max = ui.values[1];
    minNumberOfUpTransitsUToFilter = min;
    maxNumberOfUpTransitsUToFilter = max;
    filterUpTrips()
}

function filterTripsByTravelHours(event, ui) {
    var id = $(this).prop("id");
    var min = ui.values[0];
    var max = ui.values[1];
    maxTravelHourUToFilter = max;
    minTravelHourUToFilter = min;
    filterUpTrips()
}

function filterTripsByCost(event, ui) {
    var id = $(this).prop("id");
    var min = ui.values[0];
    var max = ui.values[1];
    maxCostUToFilter = max;
    minCostUToFilter = min;
    filterUpTrips()
}

function filterUpTrips() {
    filteredTripList = [];
    for (var i = 0; i < upTrips.length; i++) {
        var departureTime = new Date(upTrips[i].transits[0].schedule.departTime);
        var arrivalTime = new Date(upTrips[i].transits[upTrips[i].transits.length - 1].schedule.arrivalTime);
        var departureTimeInMinutes = departureTime.getHours() * 60 + departureTime.getMinutes() + parseFloat(departureTime.getSeconds() > 0 ? 1 : 0);
        var arrivalTimeInMinutes = arrivalTime.getHours() * 60 + arrivalTime.getMinutes() + parseFloat(arrivalTime.getSeconds() > 0 ? 1 : 0);

        var departureDate = new Date(upTrips[i].transits[0].schedule.departDate);
        var departureTime = new Date(upTrips[i].transits[0].schedule.departTime);
        var arrivalDate = new Date(upTrips[i].transits[upTrips[i].transits.length - 1].schedule.arrivalDate);
        var arrivalTime = new Date(upTrips[i].transits[upTrips[i].transits.length - 1].schedule.arrivalTime);
        var dateTimeDifference = getTimeDifference(departureDate, departureTime, arrivalDate, arrivalTime);
        var travelHours = parseFloat(dateTimeDifference.days * 24) + parseFloat(dateTimeDifference.hours) + parseFloat(dateTimeDifference.minutes > 0 || dateTimeDifference.seconds > 0 ? 1 : 0);


        if (departureTimeInMinutes >= parseFloat(minOutboundTimeUToFilter) && departureTimeInMinutes <= parseFloat(maxOutboundTimeUToFilter) &&
            arrivalTimeInMinutes >= parseFloat(minInboundTimeUToFilter) && arrivalTimeInMinutes <= parseFloat(maxInboundTimeUToFilter) &&
            parseFloat(upTrips[i].transits.length - 1) >= parseFloat(minNumberOfUpTransitsUToFilter) && parseFloat(upTrips[i].transits.length - 1) <= parseFloat(maxNumberOfUpTransitsUToFilter) &&
            travelHours >= parseFloat(minTravelHourUToFilter) && travelHours <= parseFloat(maxTravelHourUToFilter) &&
            parseFloat(upTrips[i].totalCost) >= parseFloat(minCostUToFilter) && parseFloat(upTrips[i].totalCost) <= parseFloat(maxCostUToFilter)) {

            filteredTripList.push(upTrips[i]);

        }
    }
    //upTrips = filteredTripList;
    $("#upTripResult").empty();
    renderUpTripPane(filteredTripList);
}
