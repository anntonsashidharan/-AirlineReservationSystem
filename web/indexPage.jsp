<%@ page import="com.airline.webservice.TravelClass" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>

    <meta charset="ISO-8859-1">
    <title>ARS | WELCOME</title>
    <link rel="stylesheet" href="/resources/javascript/jquery-ui.min.css">
    <script src="/resources/javascript/jquery.js"></script>
    <script src="/resources/javascript/jquery-ui.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/resources/css/styles.css">
    <script>
        $(document).ready(function () {
            if ($('input[type=radio][name=tripType]').val() == 'oneway') {
                $("#departDateDownTrip").css("display", "none");
            } else if ($('input[type=radio][name=tripType]').val() == 'round') {
                $("#departDateDownTrip").css("display", "block");
            }

            $("#departDateUpTrip").datepicker({

            });
            $("#departDateDownTrip").datepicker({

            });

            $("#sourceAirport").autocomplete({
                source: $.map(<%=session.getAttribute("airportList")%>, function (value, key) {
                    return {
                        label: value,
                        value: key
                    };
                })
            });

            $("#destinationAirport").autocomplete({
                source: $.map(<%=session.getAttribute("airportList")%>, function (value, key) {
                    return {
                        label: value,
                        value: key
                    };
                })
            });

            $('input[type=radio][name=tripType]').on('change', function () {
                switch ($(this).val()) {
                    case 'oneway':
                        $("#departDateDownTrip").css("display", "none");
                        break;
                    case 'round':
                        $("#departDateDownTrip").css("display", "block");
                        break;
                }
            });


        });

    </script>
</head>
<body>
<div>
    <div id="topbar" class="topbar">
        <%@include file="jsp/templates/menueBar.jsp" %>
        <%@include file="jsp/templates/userLoginInfo.jsp" %>
    </div>

    <div class="section1">


        <div class="indexPageMessageSection">
            <c:if test="${requestScope.successMessage!=null}">
                <div class="successMessage">
                    <p>${requestScope.successMessage}</p>
                </div>
            </c:if>
            <c:if test="${requestScope.errorMessage!=null}">
                <div class="errorMessage">
                    <p>${requestScope.errorMessage}</p>
                </div>
            </c:if>
            <c:if test="${requestScope.informationMessage!=null}">
                <div class="informationMessage">
                    <p>${requestScope.informationMessage}</p>
                </div>
            </c:if>
        </div>

        <div class="content">

            <div class="searchPanel">
                <div class="searchpaneltabs">
                    <ul>
                        <li><a href="#">Flights</a></li>
                        <li><a href="#">Hotels</a></li>
                        <li><a href="#">Holiday</a></li>
                    </ul>
                </div>
                <div class="searchform">
                    <div class="searchFormContent">
                        <form method="post" action="/main">
                            <div class="line1">
                                <div><input type="radio" name="tripType" value="oneway" checked id="tripTypeOneWay"/>One
                                    Way
                                </div>
                                <div><input type="radio" name="tripType" value="round" id="tripTypeRound"/>Round Trip
                                </div>
                            </div>
                            <div class="line2">
                                <div class="col1"><input type="text" name="sourceAirport" id="sourceAirport"
                                                         placeholder="From" value="${sessionScope.sourceAirport}"/></div>
                                <div class="col2"><input type="text" name="destinationAirport" id="destinationAirport"
                                                         placeholder="To" value="${sessionScope.destinationAirport}"/></div>
                            </div>
                            <div class="line3">
                                <fmt:formatDate value="${sessionScope.upTripDepartureDate}"
                                                var="formattedUpDepartureDate"
                                                pattern="MM/dd/yyyy"></fmt:formatDate>
                                <div class="col1"><input type="text" name="departDateUpTrip" id="departDateUpTrip"
                                                         placeholder="Departure Date" value="${formattedUpDepartureDate}"/></div>
                                <fmt:formatDate value="${sessionScope.downTripDepartureDate}"
                                                var="formattedDownDepartDate"
                                                pattern="MM/dd/yyyy"></fmt:formatDate>
                                <div class="col2"><input type="text" name="departDateDownTrip" id="departDateDownTrip"
                                                         placeholder="Arrival Date" style="display: none" value="${formattedDownDepartDate}"/></div>
                            </div>
                            <div class="line4">
                                <div class="col1">
                                    <div>
                                        <div>
                                            <div>Adult</div>
                                            <div>
                                                <select name="numberOfAdults">
                                                    <option value="1">1</option>
                                                    <option value="2">2</option>
                                                    <option value="3">3</option>
                                                    <option value="4">4</option>
                                                    <option value="5">5</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div>
                                            <div>Children</div>
                                            <div>
                                                <select name="numberOfChildren">
                                                    <option value="0">0</option>
                                                    <option value="1">1</option>
                                                    <option value="2">2</option>
                                                    <option value="3">3</option>
                                                    <option value="4">4</option>
                                                    <option value="5">5</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div>
                                            <div>Infant</div>
                                            <div>
                                                <select name="numberOfInfants">
                                                    <option value="0">0</option>
                                                    <option value="1">1</option>
                                                    <option value="2">2</option>
                                                    <option value="3">3</option>
                                                    <option value="4">4</option>
                                                    <option value="5">5</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="col2">
                                    <div>
                                        <div class="onlyDirectFlightCheck">
                                            <div>Maximum Transits</div>
                                            <div>
                                                <select name="maximumTransits">
                                                    <option value="-1">Any</option>
                                                    <option value="0">Only Direct</option>
                                                    <option value="1">1</option>
                                                    <option value="2">2</option>
                                                    <option value="3">3</option>
                                                    <option value="4">4</option>
                                                    <option value="5">5</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="classSelect">
                                            <div>Travel Class</div>
                                            <div>
                                                <select name="travelClass">
                                                    <option value="<%=TravelClass.ECONOMY_CLASS%>">Economy</option>
                                                    <option value="<%=TravelClass.BUSINESS_CLASS%>">Business</option>
                                                    <option value="<%=TravelClass.FIRST_CLASS%>">First Class</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="line5">
                                <input type="submit" name="search" value="Search">
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <div class="extra">

            </div>

        </div>
    </div>

    <div class="footer">
        <%@include file="jsp/templates/footer.jsp" %>
    </div>
</div>


</body>


</html>
