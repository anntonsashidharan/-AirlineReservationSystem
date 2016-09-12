package com.ars.controller.admin.employee;

import com.ars.domain.user.Employee;
import com.ars.domain.user.UserLogin;
import com.ars.service.employee.EmployeeService;
import com.ars.system.APPStatics;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by JJ on 6/10/16.
 */
@WebServlet("/admin/manageEmployee")
public class MangeEmployeeController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            UserLogin userLogin = (UserLogin) request.getSession().getAttribute(APPStatics.SessionStatics.LOGGED_USER);

            String employeeNumber = request.getParameter("employeenumber");
            String userName = request.getParameter("username");
            String firstName = request.getParameter("firstname");
            String lastName = request.getParameter("lastname");
            String otherName = request.getParameter("othername");
            String dateOfBirth = request.getParameter("dateofbirth");
            String email = request.getParameter("email");
            String nicNumber = request.getParameter("nicnumber");
            String addressLine1 = request.getParameter("addressline1");
            String addressLine2 = request.getParameter("addressline2");
            String addressLine3 = request.getParameter("addressline3");
            String fixedLineNumber = request.getParameter("fixedLineNumber1");
            String mobileNumber = request.getParameter("mobileNumber1");
            String userRoleAdmin = request.getParameter("userRoleAdmin");
            String userRoleManager = request.getParameter("userRoleManager");
            String userRoleStaff = request.getParameter("userRoleStaff");


            if ("searchEmployee".equals(request.getParameter("req"))) {
                int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
                int numberOfRecordsPerPage = Integer.parseInt(request.getParameter("numberOfRecordsPerPage"));
                ArrayList<Employee> employees = (ArrayList<Employee>) EmployeeService.getEmployees(numberOfRecordsPerPage, pageNumber, employeeNumber, userName, firstName, lastName, otherName, email, nicNumber, addressLine1, addressLine2, addressLine3,
                        fixedLineNumber, mobileNumber, userRoleAdmin, userRoleManager, userRoleStaff, userLogin);
                request.setAttribute(APPStatics.RequestStatics.EMPLOYEE_SEARCH_RESULT, employees);
                int count = EmployeeService.getEmployeesCount(employeeNumber, userName, firstName, lastName, otherName, email, nicNumber, addressLine1, addressLine2, addressLine3,
                        fixedLineNumber, mobileNumber, userRoleAdmin, userRoleManager, userRoleStaff, userLogin);
                request.setAttribute(APPStatics.RequestStatics.NUMBER_OF_PAGES, Math.ceil((double) count / numberOfRecordsPerPage));
                request.getRequestDispatcher("/jsp/admin/employee/employeeSearchResult.jsp").forward(request, response);
            } else if ("getEmployee".equals(request.getParameter("req"))) {
                Employee employee = EmployeeService.getEmployeeByEID(request.getParameter("employeeID"));
                PrintWriter out = response.getWriter();
                String json = new Gson().toJson(employee);
                out.println(json);
            } else if ("updateEmployee".equals(request.getParameter("req"))) {
                int numberOfFixedLines = Integer.parseInt(request.getParameter("numberOfFixedLines"));
                List<String> fixedLineNumbers = new ArrayList<String>();
                for (int x = 1; x <= numberOfFixedLines; x++) {
                    String number = request.getParameter("fixedLineNumber" + x);
                    if (number != null && !"".equals(number) && !fixedLineNumbers.contains(number)) {
                        fixedLineNumbers.add(number);
                    }
                }

                int numberOfMobileNumbers = Integer.parseInt(request.getParameter("numberOfMobileNumbers"));
                List<String> mobileNumbers = new ArrayList<String>();
                for (int x = 1; x <= numberOfMobileNumbers; x++) {
                    String number = request.getParameter("mobileNumber" + x);
                    if (number != null && !"".equals(number) && !mobileNumbers.contains(number)) {
                        mobileNumbers.add(number);
                    }

                }

                Employee employee = EmployeeService.updateEmployee(employeeNumber, firstName, lastName, otherName, dateOfBirth, email, nicNumber, addressLine1, addressLine2, addressLine3, fixedLineNumbers, mobileNumbers, userRoleAdmin, userRoleManager, userRoleStaff, userLogin);
                request.setAttribute(APPStatics.RequestStatics.SUCCESS_MESSAGE, "Employee (" + employee.getId() + ") updated successfully");
                request.getRequestDispatcher("/jsp/admin/employee/manageEmployee.jsp").forward(request, response);

            } else if ("deleteEmployee".equals(request.getParameter("req"))) {
                EmployeeService.deleteEmployee(employeeNumber,userLogin);
                request.setAttribute(APPStatics.RequestStatics.SUCCESS_MESSAGE, "Employee (" + employeeNumber + ") deleted successfully");
                request.getRequestDispatcher("/jsp/admin/employee/manageEmployee.jsp").forward(request, response);

            }
        } catch (Exception e) {
            request.setAttribute("errorMessage",e.getMessage());
            request.getRequestDispatcher("/jsp/admin/employee/manageEmployee.jsp").forward(request, response);
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute(APPStatics.SessionStatics.REDIRECT_PAGE_AFTER_LOGIN,"/admin/manageEmployee");
        UserLogin userLogin = (UserLogin) request.getSession().getAttribute(APPStatics.SessionStatics.LOGGED_USER);
        if (userLogin == null) {
            request.setAttribute(APPStatics.RequestStatics.ERROR_MESSAGE, "User not logged");
            response.sendRedirect("/login");
        } else if (userLogin.getRoles().contains("admin")) {   //for valid user(admin)
            request.getRequestDispatcher("/jsp/admin/employee/manageEmployee.jsp").forward(request, response);
        } else if (userLogin.getRoles().contains("manager")) {
            request.getRequestDispatcher("/jsp/cancellation/cancellation.jsp");
        } else if (userLogin.getRoles().contains("staff")) {
            request.getRequestDispatcher("/jsp/cancellation/cancellation.jsp").forward(request, response);
        } else {
            request.setAttribute(APPStatics.RequestStatics.ERROR_MESSAGE, "User doesn\'t have privileges to access this page ");
            response.sendRedirect("/main");
        }
    }
}
