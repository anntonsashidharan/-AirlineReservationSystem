package com.ars.service.employee;

import com.ars.dao.employee.EmployeeDAO;
import com.ars.domain.user.Employee;
import com.ars.domain.user.UserLogin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by JJ on 5/17/16.
 */
public class EmployeeService {
    public static Employee createEmployee(String firstName, String lastName, String otherName, String dateOfBirth, String email, String nicNumber,
                                                         String addressLine1, String addressLine2, String addressLine3, List<String> fixedLineNumbers,
                                                         List<String> mobileNumbers, String userRoleAdmin, String userRoleManager, String userRoleStaff,
                                                         UserLogin creatingUser) throws Exception {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = null;
        try{
            date = simpleDateFormat.parse(dateOfBirth);
        }catch (Exception e){
            throw new Exception("Invalid Date Of Birth (Required format - mm/dd/yyyy)");
        }
        List<String> userRoles = new ArrayList<String>();
        if(userRoleAdmin!=null){
            userRoles.add(userRoleAdmin);
        }
        if(userRoleManager!=null){
            userRoles.add(userRoleManager);
        }
        if(userRoleStaff!=null){
            userRoles.add(userRoleStaff);
        }
        EmployeeDAO employeeDAO = new EmployeeDAO();
        Employee employee = employeeDAO.createEmployee(firstName,lastName,otherName,date,email,nicNumber,addressLine1,addressLine2,addressLine3,fixedLineNumbers,mobileNumbers,userRoles,creatingUser);

        return employee;
    }

    public static List<Employee> getEmployees(int numberOfRecordPerPage,int pageNumber,String employeeNumber,String userName,String firstName, String lastName, String otherName, String email, String nicNumber,
                                              String addressLine1, String addressLine2, String addressLine3, String fixedLineNumbers,
                                              String mobileNumbers, String userRoleAdmin, String userRoleManager, String userRoleStaff,
                                              UserLogin creatingUser){
        EmployeeDAO employeeDAO = new EmployeeDAO();
        List<Employee> employees = employeeDAO.getEmployeesBasicInfo(numberOfRecordPerPage,(pageNumber-1)*numberOfRecordPerPage,employeeNumber,userName,firstName
                                                            ,lastName,otherName,email,nicNumber,addressLine1,addressLine2,addressLine3
                                                            ,fixedLineNumbers,mobileNumbers,userRoleAdmin,userRoleManager,userRoleStaff,creatingUser);
        return employees;
    }

    public static Employee getEmployeeByEID(String employeeID){
        EmployeeDAO employeeDAO = new EmployeeDAO();
        Employee employee = employeeDAO.getEmployeeByEID(employeeID);
        return employee;
    }

    public static int getEmployeesCount(String employeeNumber, String userName, String firstName, String lastName, String otherName, String email, String nicNumber,
                                        String addressLine1, String addressLine2, String addressLine3, String fixedLineNumber, String mobileNumber, String userRoleAdmin,
                                        String userRoleManager, String userRoleStaff, UserLogin userLogin) {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        int count = employeeDAO.getEmployeesCount(employeeNumber,userName,firstName
                ,lastName,otherName,email,nicNumber,addressLine1,addressLine2,addressLine3
                ,fixedLineNumber,mobileNumber,userRoleAdmin,userRoleManager,userRoleStaff,userLogin);
        return count;
    }

    public static Employee updateEmployee(String employeeNumber, String firstName, String lastName, String otherName, String dateOfBirth,
                                          String email, String nicNumber, String addressLine1, String addressLine2, String addressLine3,
                                          List<String> fixedLineNumbers, List<String> mobileNumbers, String userRoleAdmin, String userRoleManager,
                                          String userRoleStaff, UserLogin updatingUser) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = simpleDateFormat.parse(dateOfBirth);
        List<String> userRoles = new ArrayList<String>();
        if(userRoleAdmin!=null){
            userRoles.add(userRoleAdmin);
        }
        if(userRoleManager!=null){
            userRoles.add(userRoleManager);
        }
        if(userRoleStaff!=null){
            userRoles.add(userRoleStaff);
        }
        EmployeeDAO employeeDAO = new EmployeeDAO();
        Employee employee = employeeDAO.updateEmployee(employeeNumber, firstName, lastName, otherName, date, email, nicNumber, addressLine1, addressLine2, addressLine3,
                                                        fixedLineNumbers, mobileNumbers, userRoles, updatingUser);

        return employee;
    }

    public static void deleteEmployee(String employeeNumber, UserLogin userLogin) throws Exception {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        employeeDAO.deleteEmployee(employeeNumber,userLogin);
    }
}
