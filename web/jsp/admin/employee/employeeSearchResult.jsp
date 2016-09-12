<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.ars.domain.user.Employee" %>
<%@ page import="com.ars.system.APPStatics" %>

<%
    ArrayList<Employee> employees = (ArrayList<Employee>)request.getAttribute(APPStatics.RequestStatics.EMPLOYEE_SEARCH_RESULT);
    pageContext.setAttribute("employeesJSTL", employees);
%>
<div class="employeeSearchResult">
    <input type="hidden" name="numberOfPages" id="numberOfPages" value="${requestScope.numberOfPages}"/>
    <table border="1" class="employeeSearchResultTable">
        <tr>
            <th>Employee Number</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>User Name</th>
        </tr>
        <c:forEach items="${employeesJSTL}" var="employee">
            <tr id="${employee.id}" class="employeerow">
                <td>${employee.id}</td>
                <td>${employee.firstName}</td>
                <td>${employee.lastName}</td>
                <td>${employee.userName}</td>
            </tr>
        </c:forEach>
    </table>
</div>