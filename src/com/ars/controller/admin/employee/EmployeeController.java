package com.ars.controller.admin.employee;

import com.ars.domain.user.Employee;
import com.ars.domain.user.UserLogin;
import com.ars.service.employee.EmployeeService;
import com.ars.system.APPStatics;
import com.ars.util.validation.Validation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JJ on 5/6/16.
 */
@WebServlet("/admin/employee")
public class EmployeeController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserLogin userLogin = (UserLogin)request.getSession().getAttribute(APPStatics.SessionStatics.LOGGED_USER);
        if(userLogin==null){
            request.setAttribute(APPStatics.RequestStatics.ERROR_MESSAGE,"User not logged");
            response.sendRedirect("/login");
        }else {}
        String requestType = request.getParameter("req");
        if(requestType==null || "".equals(requestType)){

        }else{
            if("createEmployee".equals(requestType)){
                try {
                    String firstName = request.getParameter("firstname");
                    String lastName = request.getParameter("lastname");
                    String otherName = request.getParameter("othername");
                    String dateOfBirth = request.getParameter("dateofbirth");
                    String email = request.getParameter("email");
                    String nicNumber = request.getParameter("nicnumber");
                    String addressLine1 = request.getParameter("addressline1");
                    String addressLine2 = request.getParameter("addressline2");
                    String addressLine3 = request.getParameter("addressline3");
                    int numberOfFixedLines =  Integer.parseInt(request.getParameter("numberOfFixedLines"));
                    List<String> fixedLineNumbers = new ArrayList<String>();
                    for(int x = 1 ;x<=numberOfFixedLines;x++){
                        fixedLineNumbers.add(request.getParameter("fixedLineNumber" + x));
                    }

                    int numberOfMobileNumbers =  Integer.parseInt(request.getParameter("numberOfMobileNumbers"));
                    List<String> mobileNumbers = new ArrayList<String>();
                    for(int x = 1 ;x<=numberOfMobileNumbers;x++){
                        mobileNumbers.add(request.getParameter("mobileNumber" + x));
                    }
                    String userRoleAdmin = request.getParameter("userRoleAdmin");
                    String userRoleManager  = request.getParameter("userRoleManager");
                    String userRoleStaff = request.getParameter("userRoleStaff");

                    Validation.validateCreateEmployeeForm(firstName,lastName,otherName,dateOfBirth,email,nicNumber,
                            addressLine1,addressLine2,addressLine3,fixedLineNumbers,mobileNumbers,userRoleAdmin,userRoleManager,userRoleStaff);

                    Employee employee =  EmployeeService.createEmployee(firstName,lastName,otherName,dateOfBirth,email,nicNumber,addressLine1,addressLine2,addressLine3,fixedLineNumbers,mobileNumbers,userRoleAdmin,userRoleManager,userRoleStaff,userLogin);
                    request.setAttribute(APPStatics.RequestStatics.SUCCESS_MESSAGE,"User {user name : " + employee.getUserName() + ", EPF Number : "+ employee.getId()+"} successfully Created");
                    request.getRequestDispatcher("/jsp/admin/employee/createEmployee.jsp").forward(request,response);

                } catch (Exception e) {
                    request.setAttribute("errorMessage",e.getMessage());
                    request.getRequestDispatcher("/jsp/admin/employee/createEmployee.jsp").forward(request,response);
                }
            }
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute(APPStatics.SessionStatics.REDIRECT_PAGE_AFTER_LOGIN,"/admin/employee");
        UserLogin userLogin = (UserLogin)request.getSession().getAttribute(APPStatics.SessionStatics.LOGGED_USER);
        if(userLogin==null){
            request.setAttribute(APPStatics.RequestStatics.ERROR_MESSAGE,"User not logged");
            response.sendRedirect("/login");
        }else if(userLogin.getRoles().contains("admin")||userLogin.getRoles().contains("manager")){
            request.getRequestDispatcher("/jsp/admin/employee/createEmployee.jsp").forward(request,response);
        }else if(userLogin.getRoles().contains("staff")){
            request.getRequestDispatcher("/jsp/cancellation/cancellation.jsp").forward(request,response);
        }else{
            request.setAttribute(APPStatics.RequestStatics.ERROR_MESSAGE,"User doesn\'t have privileges to access this page ");
            response.sendRedirect("/main");
        }
    }
}
