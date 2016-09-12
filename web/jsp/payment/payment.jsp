<%--
  Created by IntelliJ IDEA.
  User: JJ
  Date: 8/27/16
  Time: 11:40 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>ARS | ADD PASSENGERS</title>
    <link rel="stylesheet" href="/resources/javascript/jquery-ui.min.css">
    <script src="/resources/javascript/jquery.js"></script>
    <script src="/resources/javascript/jquery-ui.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/resources/css/styles.css">

    <script>
        $(document).ready(function () {
            $("#paypalOption").click(function () {
                $("#paymentOptionForm").submit();
            });
        });
    </script>

</head>
<body>

<div>
    <div id="topbar" class="topbar">
        <%@include file="/jsp/templates/menueBar.jsp" %>
        <%@include file="/jsp/templates/userLoginInfo.jsp" %>
    </div>


    <div class="addPassengerContentPane" id="addPassengerContentPane">


        <c:if test="${requestScope.successMessage!=null}">
            <div id="addPassengerSuccess" class="addPassengerSuccess success">
                <div class="successMessage">
                    <p>${requestScope.successMessage}</p>
                </div>
            </div>
        </c:if>
        <c:if test="${requestScope.errorMessage!=null}">
            <div id="addPassengerError" class="addPassengerError error">
                <div class="errorMessage">
                    <p>${requestScope.errorMessage}</p>
                </div>
            </div>
        </c:if>

        <div id="addPassengerContainer" class="addPassengerContainer container">

            <%@include file="/jsp/templates/bookingSummery/bookingSummery.jsp" %>

            <div class="paymentOptionPanel panel" id="paymentOptionPanel">
                <form id="paymentOptionForm" class="paymentOptionForm form"
                      action="localhost:8080/bookingPayment" method="post" name="frmPayPal1">
                    <div class="header">
                        <p>Payment Option</p>
                    </div>


                    <div class="inputs">
                        <div class="paymentOptions">
                            <div class="panelSubHeading">
                                <p>Choose An Payment Option</p>
                            </div>
                            <div class="line paymentOption" id="paypalOption">
                                <div class="paymentOptionLabel">
                                    <span>Pay With PayPal</span>
                                </div>
                                <div class="image">
                                    <span><img src="../../resources/images/paypal-logo.png" height="50px"
                                               width="120px"/></span>
                                </div>
                            </div>
                            <div class="line paymentOption" id="amexOption">
                                <div class="paymentOptionLabel">
                                    <span>Pay With American Express</span>
                                </div>
                                <div class="image">
                                    <span><img src="../../resources/images/amex_logo.png" height="50px" width="120px"/></span>
                                </div>
                            </div>
                            <div class="line paymentOption" id="visaOption">
                                <div class="paymentOptionLabel">
                                    <span>Pay With Visa Card</span>
                                </div>
                                <div class="image">
                                    <span><img src="../../resources/images/visa-logo.png" height="50px" width="120px"/></span>
                                </div>
                            </div>
                            <div class="line paymentOption" id="mastercardOption">
                                <div class="paymentOptionLabel">
                                    <span>Pay With Master Card</span>
                                </div>
                                <div class="image">
                                    <span><img src="../../resources/images/mastercard_logo.png" height="50px"
                                               width="120px"/></span>
                                </div>
                            </div>

                        </div>

                    </div>

                    <input type="hidden" name="business" value="airlinereservationcompany@gmail.com">
                    <input type="hidden" name="cmd" value="_xclick">
                    <input type="hidden" name="item_name" value="Reservation">
                    <input type="hidden" name="item_number" value="1">
                    <input type="hidden" name="credits" value="510">
                    <input type="hidden" name="userid" value="1">
                    <fmt:formatNumber minFractionDigits="2" maxFractionDigits="2"
                                      value="${sessionScope.selectedUpTripScheduleIDJava.totalCost + sessionScope.selectedDownTripScheduleIDJava.totalCost + sessionScope.serviceCharge}"
                                      var="formattedCost"/>
                    <input type="hidden" name="amount" id="amount"
                           value="${formattedCost}">
                    <input type="hidden" name="cpp_header_image" value="">
                    <input type="hidden" name="no_shipping" value="1">
                    <input type="hidden" name="currency_code" value="USD">
                    <input type="hidden" name="handling" value="0">
                    <input type="hidden" name="cancel_return" value="http://localhost:8080/bookingPayment">
                    <input type="hidden" name="return" value="http://localhost:8080/bookingPayment">
                    <input type="hidden" name="rm" value="2">


                </form>

            </div>

        </div>


    </div>

    <div class="footer">
        <%@include file="/jsp/templates/footer.jsp" %>
    </div>
</div>

</body>
</html>

