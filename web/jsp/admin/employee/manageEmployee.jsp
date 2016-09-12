<%--
  Created by IntelliJ IDEA.
  User: JJ
  Date: 5/1/16
  Time: 2:30 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>ARS | EMPLOYEE</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/styles.css">
    <link rel="stylesheet" href="/resources/javascript/jquery-ui.min.css">
    <script src="/resources/javascript/jquery.js"></script>
    <script src="/resources/javascript/jquery-ui.min.js"></script>
    <script src="/resources/javascript/javascript.js"></script>
    <script>
        var modal;
        var previousPage = 0;
        var pageNumber = 1;
        var nextPage = 2;
        var recordsPerPage = 3;
        var totalPage = 5;

        function searchEmployee(){
            recordsPerPage = $("#recordsPerPage>select").val();
            $.ajax({
                    type: 'POST',
                    url: '/admin/manageEmployee?req=searchEmployee',
                    data: $("#employeeForm").serialize() + "&pageNumber=" + pageNumber + "&numberOfRecordsPerPage="+recordsPerPage,
                    success:function(data){
                        totalPage = $(data).find("#numberOfPages").val();
                        $("#modal-body").html(data);
                        modal.style.display = "block";
                        $("#pageNumber>input").val(pageNumber);
                        $("#pageNumber>label").html(parseInt(totalPage));
                        $(".employeerow").dblclick(function(){
                            var employeeID = $(this).attr('id');
                            //alert(employeeID);
                            $.ajax({
                                    type: 'POST',
                                    url: '/admin/manageEmployee?req=getEmployee',
                                    data: {employeeID:employeeID},
                                    success:function(data){
                                        modal.style.display = "none";
                                        pageNumber=1;
                                        var employee = $.parseJSON(data);
                                        //alert(data);
                                        $("#employeenumber").val(employee.id);
                                        $("#username").val(employee.userName);
                                        $("#firstname").val(employee.firstName);
                                        $("#lastname").val(employee.lastName);
                                        $("#othername").val(employee.otherName);
                                        $("#email").val(employee.email);
                                        $("#dateofbirth").val(formatDateMMDDYYYY(employee.dateOfBirth));
                                        $("#nicnumber").val(employee.nicNumber);
                                        $("#addressline1").val(employee.addressLine1);
                                        $("#addressline2").val(employee.addressLine2);
                                        $("#addressline3").val(employee.addressLine3);

                                        //populate fixed line number information
                                        $("#numberOfFixedLines").val(employee.fixedLineNumbers.length);
                                        var node = $($(".fixedlinenumberline")[0]);
                                        node.find(".input>p>input").val(employee.fixedLineNumbers[0]);
                                        node.find(".add").css("display","block");
                                        for (var i=0, len=employee.fixedLineNumbers.length-1; i < len; i++ ) {
                                            node = $($(".fixedlinenumberline")[0]).clone();
                                            node.find(".label>p").html("&nbsp;");
                                            node.find(".add").remove();
                                            node.find(".input>p>input").prop("name","fixedLineNumber"+(parseInt(i+2)));
                                            node.find(".input>p>input").val(employee.fixedLineNumbers[i+1]);
                                            node.insertAfter($(".fixedlinenumberline")[$(".fixedlinenumberline").length-1]);
                                        }

                                        //populate mobile number information
                                        $("#numberOfMobileNumbers").val(employee.mobileNumbers.length);
                                        var node = $($(".mobilenumberline")[0]);
                                        node.find(".input>p>input").val(employee.mobileNumbers[0]);
                                        node.find(".add").css("display","block");
                                        for (var i=0, len=employee.mobileNumbers.length-1; i < len; i++ ) {
                                            node = $($(".mobilenumberline")[0]).clone();
                                            node.find(".label>p").html("&nbsp;");
                                            node.find(".add").remove();
                                            node.find(".input>p>input").prop("name","mobileNumber"+(parseInt(i+2)));
                                            node.find(".input>p>input").val(employee.mobileNumbers[i+1]);
                                            node.insertAfter($(".mobilenumberline")[$(".mobilenumberline").length-1]);
                                        }
                                        if(employee.roles.indexOf("admin") > -1){
                                            $("#userRoleAdmin").attr("checked",true);
                                        }
                                        if(employee.roles.indexOf("manager") > -1){
                                            $("#userRoleManager").attr("checked",true);
                                        }
                                        if(employee.roles.indexOf("staff") > -1){
                                            $("#userRoleStaff").attr("checked",true);
                                        }

                                        $("#addFixedLineField").click(function(){
                                            node = $($(".fixedlinenumberline")[0]).clone();
                                            node.find(".label>p").html("&nbsp;");
                                            node.find(".add").remove();
                                            node.find(".input>p>input").prop("name","fixedLineNumber"+(parseInt($(".fixedlinenumberline").length+1)));
                                            node.find(".input>p>input").val("");
                                            node.insertAfter($(".fixedlinenumberline")[$(".fixedlinenumberline").length-1]);
                                            $("#numberOfFixedLines").val(parseInt($("#numberOfFixedLines").val()) + 1);

                                        });

                                        $("#mobileNumberField").click(function(){
                                            node = $($(".mobilenumberline")[0]).clone();
                                            node.find(".label>p").html("&nbsp;");
                                            node.find(".add").remove();
                                            node.find(".input>p>input").prop("name","mobileNumber"+(parseInt($(".mobilenumberline").length+1)));
                                            node.find(".input>p>input").val("");
                                            node.insertAfter($(".mobilenumberline")[$(".mobilenumberline").length-1]);
                                            $("#numberOfMobileNumbers").val(parseInt($("#numberOfMobileNumbers").val()) + 1);
                                        });
                                        $("#updateEmployeeButton").attr("disabled",false);
                                        $("#deleteEmployeeButton").attr("disabled",false);
                                    }
                                }
                            );
                        });
                    }
                }
            );
        }

        function submitForm(operation){
            $("#employeeForm").attr("action","/admin/manageEmployee?req=" + operation);
            $("#employeeForm").submit();
        }

        $(document).ready(function(){
            $("#dateofbirth").datepicker();

            // Get the modal
            modal = document.getElementById('myModal');

            // Get the <span> element that closes the modal
            var span = document.getElementsByClassName("close")[0];

            // When the user clicks on <span> (x), close the modal
            span.onclick = function() {
                modal.style.display = "none";
                pageNumber=1;
                $("#pageNumber>input").val(pageNumber);
            }

            // When the user clicks anywhere outside of the modal, close it
            window.onclick = function(event) {
                if (event.target == modal) {
                    modal.style.display = "none";
                    pageNumber=1;
                    $("#pageNumber>input").val(pageNumber);
                }
            }

            $("#nextPage").click(function(){
                if(pageNumber<totalPage){
                    pageNumber = pageNumber+1;
                    searchEmployee();
                    $("#pageNumber>input").val(pageNumber);
                }
            });
            $("#previousPage").click(function(){
                if(pageNumber>1){
                    pageNumber = pageNumber-1;
                    searchEmployee();
                    $("#pageNumber>input").val(pageNumber);
                }
            });
            $("#lastPage").click(function(){
                pageNumber = parseInt(totalPage);
                searchEmployee();
                $("#pageNumber>input").val(pageNumber);
            });
            $("#firstPage").click(function(){
                pageNumber = 1;
                searchEmployee();
                $("#pageNumber>input").val(1);
            });
            $("#recordsPerPage>select").change(function(){
                pageNumber = 1;
                recordsPerPage = $(this).val();
                searchEmployee();
                $("#pageNumber>input").val(1);
            });
            $("#pageNumber>input").keypress(function(event){
                if(event.which == 13){
                    pageNumber = parseInt($("#pageNumber>input").val()) || pageNumber;
                    if(pageNumber>totalPage){
                        pageNumber=parseInt(totalPage);
                    }else if(pageNumber<1){
                        pageNumber=1;
                    }
                    searchEmployee();
                }
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

            <div class="adminstrationPanel" id="adminstrationPanel">
                <form id="employeeForm" class="employeeForm" method="post" action="/admin/manageEmployee">
                    <div class="header">
                        <p>Employee Management</p>
                    </div>

                    <div class="subheader">
                        <p>Manage Employee</p>
                    </div>

                    <div class="inputs">

                        <div class="line">
                            <div class="label">
                                <p>Employee Number</p>
                            </div>
                            <div class="separator">
                                <p>:</p>
                            </div>
                            <div class="input">
                                <p><input name="employeenumber" id="employeenumber" type="text"/></p>
                            </div>
                        </div>
                        <div class="line">
                            <div class="label">
                                <p>User Name</p>
                            </div>
                            <div class="separator">
                                <p>:</p>
                            </div>
                            <div class="input">
                                <p><input name="username" id="username" type="text"/></p>
                            </div>
                        </div>
                        <div class="line">
                            <div class="label">
                                <p>First Name</p>
                            </div>
                            <div class="separator">
                                <p>:</p>
                            </div>
                            <div class="input">
                                <p><input name="firstname" id="firstname" type="text"/></p>
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
                                <p><input name="lastname" id="lastname" type="text"/></p>
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
                                <p><input name="othername" id="othername" type="text"/></p>
                            </div>
                        </div>
                        <div class="line">
                            <div class="label">
                                <p>Contact Email</p>
                            </div>
                            <div class="separator">
                                <p>:</p>
                            </div>
                            <div class="input">
                                <p><input name="email" id="email" type="text"/></p>
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
                                <fmt:formatDate value="${sessionScope.loggedUser.dateOfBirth}" var="formattedDate" pattern="MM/dd/yyyy"></fmt:formatDate>
                                <p><input name="dateofbirth" id="dateofbirth" type="text"/></p>
                            </div>
                        </div>
                        <div class="line">
                            <div class="label">
                                <p>NIC Number</p>
                            </div>
                            <div class="separator">
                                <p>:</p>
                            </div>
                            <div class="input">
                                <p><input name="nicnumber" id="nicnumber" type="text"/></p>
                            </div>
                        </div>
                        <div class="line">
                            <div class="label">
                                <p style="font-size: 20px; font-weight: bold">Address</p>
                            </div>
                        </div>
                        <div class="line">
                            <div class="label">
                                <p>Address Line1</p>
                            </div>
                            <div class="separator">
                                <p>:</p>
                            </div>
                            <div class="input">
                                <p><input name="addressline1" id="addressline1" type="text"/></p>
                            </div>
                        </div>
                        <div class="line">
                            <div class="label">
                                <p>Address Line2</p>
                            </div>
                            <div class="separator">
                                <p>:</p>
                            </div>
                            <div class="input">
                                <p><input name="addressline2" id="addressline2" type="text"/></p>
                            </div>
                        </div>
                        <div class="line">
                            <div class="label">
                                <p>Address Line3</p>
                            </div>
                            <div class="separator">
                                <p>:</p>
                            </div>
                            <div class="input">
                                <p><input name="addressline3" id="addressline3" type="text"/></p>
                            </div>
                        </div>
                        <div class="line">
                            <div class="label">
                                <p style="font-size: 20px; font-weight: bold">Contact Number Information</p>
                            </div>
                        </div>
                        <input type="text" hidden id="numberOfFixedLines" name="numberOfFixedLines" value="1" />
                        <div class="fixedlinenumberline line" id="fixedlinenumberline">
                            <div class="label">
                                <p>Fixed line</p>
                            </div>
                            <div class="separator">
                                <p>:</p>
                            </div>
                            <div class="input">
                                <p><input name="fixedLineNumber1" type="text"/></p>
                            </div>
                            <div class="add" style="display: none">
                                <input class="addPhoneNumber button-default" type="button" id="addFixedLineField" value="Add"/>
                            </div>
                        </div>
                        <input type="text" hidden name="numberOfMobileNumbers"  id="numberOfMobileNumbers" value="1" />
                        <div class="mobilenumberline line" id="mobilenumberline">
                            <div class="label">
                                <p>Mobile Number</p>
                            </div>
                            <div class="separator">
                                <p>:</p>
                            </div>
                            <div class="input">
                                <p><input name="mobileNumber1" type="text"/></p>
                            </div>
                            <div class="add" style="display: none">
                                <input class="addPhoneNumber button-default" id="mobileNumberField" type="button" value="Add" />
                            </div>
                        </div>

                        <div class="line">
                            <div class="label">
                                <p>User Roles</p>
                            </div>
                            <div class="separator">
                                <p>:</p>
                            </div>
                            <div class="radio input">
                                <p>
                                    <c:if test="${sessionScope.loggedUser.roles.contains('admin')}">
                                        <input name="userRoleAdmin" id="userRoleAdmin" value="admin" type="checkbox"/>Administrator<br>
                                        <input name="userRoleManager" id="userRoleManager" value="manager" type="checkbox"/>Manager<br>
                                    </c:if>
                                    <input name="userRoleStaff" id="userRoleStaff" value="staff" type="checkbox"/>Staff
                                </p>
                            </div>
                        </div>



                        <div class="line">
                            <div class="searchbutton">
                                <p><input name="searchbyfname" id="searchbyfname" type="button" value="Search" class="button-standard-size button-default" onclick="searchEmployee();"/></p>
                            </div>
                            <div class="updateEmployeeButton">
                                <p><input type="submit" name="submit" id="updateEmployeeButton" value="Update Employee" class="button-standard-size button-default" disabled onclick="submitForm('updateEmployee')"></p>
                            </div>
                            <div class="deleteEmployeeButton">
                                <p><input type="submit" name="submit" id="deleteEmployeeButton" value="Delete Employee" class="button-standard-size button-default" onclick="submitForm('deleteEmployee');" disabled></p>
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
            <h2>Employee Search Result</h2>
        </div>
        <div class="modal-body" id="modal-body">

        </div>
        <div class="modal-footer">


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
            </ul>
        </div>
    </div>

</div>


</body>
</html>
