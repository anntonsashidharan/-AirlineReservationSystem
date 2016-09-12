<%--
  Created by IntelliJ IDEA.
  User: JJ
  Date: 9/8/16
  Time: 7:02 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>ARS | MY PROFILE</title>
    <link rel="stylesheet" href="/resources/javascript/jquery-ui.min.css">
    <script src="/resources/javascript/jquery.js"></script>
    <script src="/resources/javascript/jquery-ui.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/resources/css/styles.css">

    <script>
        var modal;
        var ticketToCancel;
        var bookingIDToSearch;

        function searchBookings() {
            $.ajax({
                type: 'POST',
                url: '/reviewBookings?req=searchByBookingNumber',
                data: $("#bookingsForm").serialize(),
                success: function (data) {
                    $("#modal-body").html(data);
                    modal.style.display = "block";
                    $(".search-row").dblclick(function () {
                        bookingIDToSearch = $(this).attr('id');
                        $.ajax({
                            type: 'POST',
                            url: '/reviewBookings?req=getBooking',
                            data: {bookingNumber: bookingIDToSearch},
                            success: function (data) {
                                modal.style.display = "none";
                                $("#booking").replaceWith(data);
                                $(".cancel-button").click(function(){
                                    ticketToCancel = $(this).attr('id');
                                    $.ajax({
                                        type : 'POST',
                                        url : '/reviewBookings',
                                        data : {req:'cancel',ticketNumber:$(this).attr('id')},
                                        success : function(data){
                                            $(".inputs").replaceWith(data);
                                            $("#confirmCancellation").click(function(){
                                                $.ajax({
                                                    type : 'POST',
                                                    url : '/reviewBookings',
                                                    data : {req:'confirmCancellation',ticketNumber:ticketToCancel},
                                                    success : function(data){
                                                        $(".inputs").replaceWith(data);
                                                    }
                                                });
                                            });
                                            $("#ignoreCancellation").click(function(){
                                                $.ajax({
                                                    type : 'POST',
                                                    url : '/reviewBookings',
                                                    data : {req:'getBooking',bookingNumber: bookingIDToSearch},
                                                    success : function(data){
                                                        $(".inputs").replaceWith(data);
                                                    }
                                                });
                                            });
                                        }
                                    });
                                });
                            }
                        });
                    });
                }
            });
        }

        $(document).ready(function () {
            $("#dateofbirth").datepicker();

            // Get the modal
            modal = document.getElementById('myModal');

            // Get the <span> element that closes the modal
            var span = document.getElementsByClassName("close")[0];

            // When the user clicks on <span> (x), close the modal
            span.onclick = function () {
                modal.style.display = "none";
                pageNumber = 1;
                $("#pageNumber>input").val(pageNumber);
            }

            // When the user clicks anywhere outside of the modal, close it
            window.onclick = function (event) {
                if (event.target == modal) {
                    modal.style.display = "none";
                    pageNumber = 1;
                    $("#pageNumber>input").val(pageNumber);
                }
            }

        });

    </script>

</head>
<body>

<div>
    <div id="topbar" class="topbar">
        <%@include file="../../templates/menueBar.jsp" %>
        <%@include file="../../templates/userLoginInfo.jsp" %>
    </div>


    <div class="profileManagementContentPane contentPane" id="contentPane">
        <c:if test="${requestScope.successMessage!=null}">
            <div id="profileManagementSuccess" class="profileManagementSuccess">
                <div class="successMessage">
                    <p>${requestScope.successMessage}</p>
                </div>
            </div>
        </c:if>
        <c:if test="${requestScope.errorMessage!=null}">
            <div id="profileManagementError" class="profileManagementError">
                <div class="errorMessage">
                    <p>${requestScope.errorMessage}</p>
                </div>
            </div>
        </c:if>


        <div id="profileFormContainer" class="profileFormContainer container">

            <div class="panel-left">
                <%@include file="/jsp/templates/treemenue/usertreemenue.jsp" %>
            </div>

            <div class="searchBookingPanel panel" id="manageUserPanel">
                <form id="bookingsForm" class="bookingsForm form" method="post" action="/reviewBookings">
                    <div class="header-panel header">
                        <div>Booking History</div>
                    </div>


                    <div class="inputs">
                        <div class="form-standard" id="booking">
                            <div id="line">
                                <div class="label">
                                    <p>Booking Number</p>
                                </div>
                                <div class="separator">
                                    <p>:</p>
                                </div>
                                <div class="input">
                                    <p><input name="bookingNumber" type="text"
                                              value="${sessionScope.searchBookingNumber}"/>
                                    </p>
                                </div>
                                <div class="input">
                                    <div class="searchbutton">
                                        <p><input name="searchbyfname" id="searchbyfname" type="button" value="Search"
                                                  class="button-standard-size button-default"
                                                  onclick="searchBookings();"/></p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>

    </div>

    <div class="footer">
        <%@include file="../../templates/footer.jsp" %>
    </div>
</div>


<!-- The Modal -->
<div id="myModal" class="modal">

    <!-- Modal content -->
    <div class="modal-content">
        <div class="modal-header">
            <span class="close">×</span>

            <h2>Booking Search Result</h2>
        </div>
        <div class="modal-body" id="modal-body">

        </div>
        <div class="modal-footer">

            <!--
                        <ul class="pagination-navigation">
                            <li><a class="firstPage" id="firstPage"></a></li>
                            <li><a class="previousPage" id="previousPage"></a></li>
                            <li><a class="pageNumber" id="pageNumber"><input type="text" value="1"> of <label></label></a></li>
                            <li><a class="nextPage" id="nextPage"></a></li>
                            <li><a class="lastPage" id="lastPage"></a></li>
                            <li><a class="recordsPerPage" id="recordsPerPage">
                                <select name="recordsPerPage" id="recordsPerPageDropDown">
                                    <option value="2">2</option>
                                    <option value="5">5</option>
                                    <option value="8">8</option>
                                    <option value="15">15</option>
                                </select>
                            </a></li>
                        </ul>       -->
        </div>
    </div>

</div>


</body>
</html>
