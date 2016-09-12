<%--
  Created by IntelliJ IDEA.
  User: JJ
  Date: 8/26/16
  Time: 9:57 AM
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
            $('.dateOfBirth').each(function(){
                $(this).datepicker({

                });
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

            <div class="addPassengerPanel panel" id="addPassengerPanel">
                <form id="passengerForm" class="passengerForm form" method="post" action="/passenger/addPassenger">
                    <div class="header">
                        <p>Passenger Details</p>
                    </div>


                    <div class="inputs">
                        <c:set var="passengerNumber" value="0"></c:set>
                        <c:forEach var="i" begin="1" end="${sessionScope.numberOfAdultPassengers}">
                            <c:set var="passengerNumber" value="${passengerNumber+1}"></c:set>
                            <div class="passengerDetail">


                                <div class="passengerNumberLable">
                                    <p>Passenger ${passengerNumber} (Adult)</p>
                                </div>

                                <div class="line">
                                    <div class="label">
                                        <p>First Name</p>
                                    </div>
                                    <div class="separator">
                                        <p>:</p>
                                    </div>
                                    <div class="input">
                                        <p><input name="adultFirstName${i}" type="text" class="firstName"/></p>
                                    </div>
                                </div>
                                <div class="line">
                                    <div class="label">
                                        <p>Last Name</p>
                                    </div>
                                    <div class="separator">
                                        <p>:</p>
                                    </div>
                                    <div class="input">
                                        <p><input name="adultLastName${i}" type="text" class="lastName"/></p>
                                    </div>
                                </div>
                                <div class="line">
                                    <div class="label">
                                        <p>Other Name</p>
                                    </div>
                                    <div class="separator">
                                        <p>:</p>
                                    </div>
                                    <div class="input">
                                        <p><input name="adultOtherName${i}" type="text" class="otherName"/></p>
                                    </div>
                                </div>
                                <div class="line">
                                    <div class="label">
                                        <p>Date Of Birth</p>
                                    </div>
                                    <div class="separator">
                                        <p>:</p>
                                    </div>
                                    <div class="input">
                                        <fmt:formatDate value="${sessionScope.loggedUser.dateOfBirth}"
                                                        var="formattedDate"
                                                        pattern="MM/dd/yyyy"></fmt:formatDate>
                                        <p><input class="dateOfBirth" name="adultDateOfBirth${i}" id="adultDateOfBirth${i}" type="text"/></p>
                                    </div>
                                </div>
                                <div class="line">
                                    <div class="label">
                                        <p>Gender</p>
                                    </div>
                                    <div class="separator">
                                        <p>:</p>
                                    </div>
                                    <div class="input">
                                        <p><select name="adultGender${i}" class="gender">
                                            <option value="male">Male</option>
                                            <option value="female">Female</option>
                                        </select></p>
                                    </div>
                                </div>
                                <div class="line">
                                    <div class="label">
                                        <p>Passport Number</p>
                                    </div>
                                    <div class="separator">
                                        <p>:</p>
                                    </div>
                                    <div class="input">
                                        <p><input name="adultPassportNumber${i}" type="text" class="passportNumber"/></p>
                                    </div>
                                </div>
                            </div>

                        </c:forEach>


                        <c:if test="${sessionScope.numberOfChildPassengers>0}">
                            <c:forEach var="i" begin="1" end="${sessionScope.numberOfChildPassengers}">
                                <c:set var="passengerNumber" value="${passengerNumber+1}"></c:set>
                                <div class="passengerDetail">
                                    <div class="passengerNumberLable">
                                        <p>Passenger ${passengerNumber} (Child)</p>
                                    </div>
                                    <div class="line">
                                        <div class="label">
                                            <p>First Name</p>
                                        </div>
                                        <div class="separator">
                                            <p>:</p>
                                        </div>
                                        <div class="input">
                                            <p><input name="childFirstName${i}" type="text" class="firstName"/></p>
                                        </div>
                                    </div>
                                    <div class="line">
                                        <div class="label">
                                            <p>Last Name</p>
                                        </div>
                                        <div class="separator">
                                            <p>:</p>
                                        </div>
                                        <div class="input">
                                            <p><input name="childLastName${i}" type="text" class="lastName"/></p>
                                        </div>
                                    </div>
                                    <div class="line">
                                        <div class="label">
                                            <p>Other Name</p>
                                        </div>
                                        <div class="separator">
                                            <p>:</p>
                                        </div>
                                        <div class="input">
                                            <p><input name="childOtherName${i}" type="text" class="otherName"/></p>
                                        </div>
                                    </div>
                                    <div class="line">
                                        <div class="label">
                                            <p>Date Of Birth</p>
                                        </div>
                                        <div class="separator">
                                            <p>:</p>
                                        </div>
                                        <div class="input">
                                            <fmt:formatDate value="${sessionScope.loggedUser.dateOfBirth}"
                                                            var="formattedDate"
                                                            pattern="MM/dd/yyyy"></fmt:formatDate>
                                            <p><input class="dateOfBirth" name="childDateOfBirth${i}" id="childDateOfBirth${i}" type="text"/></p>
                                        </div>
                                    </div>
                                    <div class="line">
                                        <div class="label">
                                            <p>Gender</p>
                                        </div>
                                        <div class="separator">
                                            <p>:</p>
                                        </div>
                                        <div class="input">
                                            <p><select name="childGender${i}" class="gender">
                                                <option value="male">Male</option>
                                                <option value="female">Female</option>
                                            </select></p>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:if>

                        <c:if test="${sessionScope.numberOfInfantPassengers>0}">
                            <c:forEach var="i" begin="1" end="${sessionScope.numberOfInfantPassengers}">
                                <c:set var="passengerNumber" value="${passengerNumber+1}"></c:set>
                                <div class="passengerDetail">
                                    <div class="passengerNumberLable">
                                        <p>Passenger ${passengerNumber} (Infant)</p>
                                    </div>
                                    <div class="line">
                                        <div class="label">
                                            <p>First Name</p>
                                        </div>
                                        <div class="separator">
                                            <p>:</p>
                                        </div>
                                        <div class="input">
                                            <p><input name="infantFirstName${i}" type="text" class="firstName"/></p>
                                        </div>
                                    </div>
                                    <div class="line">
                                        <div class="label">
                                            <p>Last Name</p>
                                        </div>
                                        <div class="separator">
                                            <p>:</p>
                                        </div>
                                        <div class="input">
                                            <p><input name="infantLastName${i}" type="text" class="lastName"/></p>
                                        </div>
                                    </div>
                                    <div class="line">
                                        <div class="label">
                                            <p>Other Name</p>
                                        </div>
                                        <div class="separator">
                                            <p>:</p>
                                        </div>
                                        <div class="input">
                                            <p><input name="infantOtherName${i}" type="text" class="otherName"/></p>
                                        </div>
                                    </div>
                                    <div class="line">
                                        <div class="label">
                                            <p>Date Of Birth</p>
                                        </div>
                                        <div class="separator">
                                            <p>:</p>
                                        </div>
                                        <div class="input">
                                            <fmt:formatDate value="${sessionScope.loggedUser.dateOfBirth}"
                                                            var="formattedDate"
                                                            pattern="MM/dd/yyyy"></fmt:formatDate>
                                            <p><input class="dateOfBirth" name="infantDateOfBirth${i}" id="infantDateOfBirth${i}" type="text"/></p>
                                        </div>
                                    </div>
                                    <div class="line">
                                        <div class="label">
                                            <p>Gender</p>
                                        </div>
                                        <div class="separator">
                                            <p>:</p>
                                        </div>
                                        <div class="input">
                                            <p><select name="infantGender${i}" class="gender">
                                                <option value="male">Male</option>
                                                <option value="female">Female</option>
                                            </select></p>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:if>


                        <!-- Contact person inforbookingContact-->
                        <div class="bookingContactDetail">
                            <div class="passengerNumberLable">
                                <p>Contact Information</p>
                            </div>
                            <div class="line">
                                <div class="label">
                                    <p>First Name</p>
                                </div>
                                <div class="separator">
                                    <p>:</p>
                                </div>
                                <div class="input">
                                    <p><input name="contactFirstName" type="text" class="firstName"/></p>
                                </div>
                            </div>
                            <div class="line">
                                <div class="label">
                                    <p>Last Name</p>
                                </div>
                                <div class="separator">
                                    <p>:</p>
                                </div>
                                <div class="input">
                                    <p><input name="contactLastName" type="text" class="lastName"/></p>
                                </div>
                            </div>
                            <div class="line">
                                <div class="label">
                                    <p>Other Name</p>
                                </div>
                                <div class="separator">
                                    <p>:</p>
                                </div>
                                <div class="input">
                                    <p><input name="contactOtherName" type="text" class="otherName"/></p>
                                </div>
                            </div>
                            <div class="line">
                                <div class="label">
                                    <p>E-mail</p>
                                </div>
                                <div class="separator">
                                    <p>:</p>
                                </div>
                                <div class="input">
                                    <p><input name="contactEmail" type="email" class="email"/></p>
                                </div>
                            </div>
                            <div class="line">
                                <div class="label">
                                    <p>Address Line 1</p>
                                </div>
                                <div class="separator">
                                    <p>:</p>
                                </div>
                                <div class="input">
                                    <p><input name="contactAddressLine1" type="text" class="addressLine1"/></p>
                                </div>
                            </div>
                            <div class="line">
                                <div class="label">
                                    <p>Address Line 2</p>
                                </div>
                                <div class="separator">
                                    <p>:</p>
                                </div>
                                <div class="input">
                                    <p><input name="contactAddressLine2" type="text" class="addressLine2"/></p>
                                </div>
                            </div>
                            <div class="line">
                                <div class="label">
                                    <p>Address Line 3</p>
                                </div>
                                <div class="separator">
                                    <p>:</p>
                                </div>
                                <div class="input">
                                    <p><input name="contactAddressLine3" type="text" class="addressLine3"/></p>
                                </div>
                            </div>
                            <div class="line">
                                <div class="label">
                                    <p>Country</p>
                                </div>
                                <div class="separator">
                                    <p>:</p>
                                </div>
                                <div class="input">
                                    <p><input name="contactAddressLineCountry" type="text" class="addressLineCountry"/></p>
                                </div>
                            </div>
                            <div class="line">
                                <div class="label">
                                    <p>Contact Number 1</p>
                                </div>
                                <div class="separator">
                                    <p>:</p>
                                </div>
                                <div class="input">
                                    <p><input name="contactNumber1" type="text" class="contactNumber"/></p>
                                </div>
                            </div>
                            <div class="line">
                                <div class="label">
                                    <p>Contact Number 2</p>
                                </div>
                                <div class="separator">
                                    <p>:</p>
                                </div>
                                <div class="input">
                                    <p><input name="contactNumber2" type="text" class="contactNumber"/></p>
                                </div>
                            </div>
                        </div>

                        <div class="buttonSection line">
                            <div class="float-right submitPassengerInfoButton">
                                <p><input class="button-standard-size button-default" type="submit" name="submit"
                                          value="Proceed"></p>
                            </div>
                        </div>


                    </div>
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

