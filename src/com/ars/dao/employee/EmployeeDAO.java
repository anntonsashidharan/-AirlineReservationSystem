package com.ars.dao.employee;

import com.ars.domain.user.Employee;
import com.ars.domain.user.UserLogin;
import com.ars.system.APPStatics;
import com.ars.util.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by JJ on 5/17/16.
 */
public class EmployeeDAO {

    public Employee createEmployee(String firstName, String lastName, String otherName, Date date, String email, String nicNumber,
                                   String addressLine1, String addressLine2, String addressLine3, List<String> fixedLineNumbers,
                                   List<String> mobileNumbers, List<String> userRoles, UserLogin creatingUser) throws Exception {

        Employee employee = null;
        StringBuilder userName = new StringBuilder();
        if(firstName!=null && !"".equals(firstName)){
            userName.append(firstName.charAt(0));
        }
        if(otherName!=null && !"".equals(otherName)){
            userName.append(otherName.charAt(0));
        }
        if(lastName!=null && !"".equals(lastName)){
            userName.append(lastName.charAt(0));
        }
        String getCountOfSimilarUserName = "SELECT COUNT(user_name) count FROM " + APPStatics.schemaName + ".\"USER\" WHERE LOWER(user_name) LIKE \'" + userName.toString().toLowerCase() + "%\'";
        String sqlGetNextEmployeeNumber = "SELECT nextval('" + APPStatics.schemaName + ".\"employeeNumberGenerator\"')";
        String sqlInsertIntoUser = "INSERT INTO " + APPStatics.schemaName + ".\"USER\" (user_name,password,email,first_name,last_name,other_name,date_of_birth) VALUES(?,?,?,?,?,?,?)";
        String sqlInsertIntoEmployee = "INSERT INTO " + APPStatics.schemaName + ".\"EMPLOYEE\" (employee_id,user_name,address_line1,address_line2,address_line3,nic_number,created_user_name) " +
                "VALUES(?,?,?,?,?,?,?)";
        String assignRole = "INSERT INTO " + APPStatics.schemaName + ".\"USER_ROLE\" (user_name,role_name) VALUES(?,?)";
        String insertFixedLineNumbers = "INSERT INTO " + APPStatics.schemaName + ".\"EMPLOYEE_FIXEDLINE\" (employee_id,fixedline_number) VALUES(?,?)";
        String insertMobileNumbers = "INSERT INTO " + APPStatics.schemaName + ".\"EMPLOYEE_MOBILE\" (employee_id,mobile_number) VALUES(?,?)";
        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        try {
            connection.setAutoCommit(false);    //to process as transaction
            PreparedStatement statement1 = connection.prepareStatement(getCountOfSimilarUserName);
            ResultSet resultSet1 = statement1.executeQuery();
            resultSet1.next();
            int count = resultSet1.getInt("count");
            userName = userName.append(count+1);

            PreparedStatement statement2 = connection.prepareStatement(sqlGetNextEmployeeNumber);
            ResultSet resultSet2 = statement2.executeQuery();
            resultSet2.next();
            String nextValue = resultSet2.getString("nextval");
            String employeeNumber = "EMP"+String.format("%05d", Integer.parseInt(nextValue));

            PreparedStatement statement3 = connection.prepareStatement(sqlInsertIntoUser);
            statement3.setString(1,userName.toString().toLowerCase());
            statement3.setString(2,"1qaz2wsx@");
            statement3.setString(3,email);
            statement3.setString(4,firstName);
            statement3.setString(5,lastName);
            statement3.setString(6,otherName);
            statement3.setDate(7, new java.sql.Date(date.getTime()));
            statement3.execute();

            PreparedStatement statement4 = connection.prepareStatement(sqlInsertIntoEmployee);
            statement4.setString(1,employeeNumber);
            statement4.setString(2,userName.toString().toLowerCase());
            statement4.setString(3,addressLine1);
            statement4.setString(4,addressLine2);
            statement4.setString(5,addressLine3);
            statement4.setString(6,nicNumber);
            statement4.setString(7,creatingUser.getUserName());
            statement4.execute();

            PreparedStatement statement5 = connection.prepareStatement(assignRole);
            for (String role : userRoles){
                statement5.setString(1,userName.toString().toLowerCase());
                statement5.setString(2,role);
                statement5.execute();
            }

            PreparedStatement statement6 = connection.prepareStatement(insertFixedLineNumbers);
            for (String fixedLine : fixedLineNumbers){
                if(fixedLine!=null && !"".equals(fixedLine) ){
                    statement6.setString(1,employeeNumber);
                    statement6.setString(2,fixedLine);
                    try{
                        statement6.execute();
                    } catch (SQLException e) {
                        connection.rollback();
                        e.printStackTrace();
                        if(e.getSQLState().equalsIgnoreCase(APPStatics.DBErrorCodes.UNIQUE_KEY_VIOLATION)){
                            throw new Exception("Duplicate Fixed Line Number");
                        }else {
                            throw new Exception(e.getSQLState());
                        }
                    }
                }
            }

            PreparedStatement statement7 = connection.prepareStatement(insertMobileNumbers);
            for (String mobile : mobileNumbers){
                if(mobile!=null && !"".equals(mobile) ){
                    statement7.setString(1,employeeNumber);
                    statement7.setString(2,mobile);
                    try{
                        statement7.execute();
                    } catch (SQLException e) {
                        connection.rollback();
                        e.printStackTrace();
                        if(e.getSQLState().equalsIgnoreCase(APPStatics.DBErrorCodes.UNIQUE_KEY_VIOLATION)){
                            throw new Exception("Duplicate Mobile Number");
                        }else {
                            throw new Exception(e.getSQLState());
                        }
                    }

                }
            }


            connection.commit();

            employee = new Employee();
            employee.setUserName(userName.toString().toLowerCase());
            employee.setDateOfBirth(date);
            employee.setAddressLine1(addressLine1);
            employee.setAddressLine2(addressLine2);
            employee.setAddressLine3(addressLine3);
            employee.setEmail(email);
            //employee.setEnteredBy(null);
            employee.setFirstName(firstName);
            employee.setLastName(lastName);
            employee.setOtherName(otherName);
            employee.setId(employeeNumber);
            employee.setNicNumber(nicNumber);


        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            if(e.getSQLState().equalsIgnoreCase(APPStatics.DBErrorCodes.UNIQUE_KEY_VIOLATION)){
                throw new Exception("User Already Exists");
            }else {
                throw new Exception(e.getSQLState());
            }

        }
        finally {
            try {
                connection.commit();
                connection.setAutoCommit(true);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        return employee;

    }

    /**
     * To get all employee parse limit and offset as negative value
     *
     * @param limit
     * @param offset
     * @return
     */
    public List<Employee> getEmployees(int limit, int offset,String employeeNumber,String userName,String firstName, String lastName, String otherName, String email, String nicNumber,
                                       String addressLine1, String addressLine2, String addressLine3, String fixedLineNumbers,
                                       String mobileNumbers, String userRoleAdmin, String userRoleManager, String userRoleStaff,
                                       UserLogin creatingUser){
        List<Employee> employees = new ArrayList<Employee>();
        Employee employee = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM " + APPStatics.schemaName + ".\"EMPLOYEE\", " + APPStatics.schemaName + ".\"EMPLOYEE_FIXEDLINE\", " + APPStatics.schemaName + ".\"EMPLOYEE_MOBILE\", " + APPStatics.schemaName + ".\"USER\", " + APPStatics.schemaName + ".\"USER_ROLE\" " +
                "WHERE \n" +
                "  \"EMPLOYEE\".employee_id = \"EMPLOYEE_FIXEDLINE\".employee_id AND\n" +
                "  \"EMPLOYEE\".employee_id = \"EMPLOYEE_MOBILE\".employee_id AND\n" +
                "  \"EMPLOYEE\".user_name = \"USER\".user_name AND\n" +
                "  \"EMPLOYEE\".user_name = \"USER_ROLE\".user_name");

        if(employeeNumber!=null && !"".equals(employeeNumber)){
            sql.append(" AND \"EMPLOYEE\".employee_id LIKE '%"+ employeeNumber +"%'");
        }
        if(userName!=null && !"".equals(userName)){
            sql.append(" AND \"EMPLOYEE\".user_name LIKE '%"+ userName +"%'");
        }
        if(firstName!=null && !"".equals(firstName)){
            sql.append(" AND \"USER\".first_name LIKE '%"+ firstName +"%'");
        }
        if(lastName!=null && !"".equals(lastName)){
            sql.append(" AND \"USER\".last_name LIKE '%"+ lastName +"%'");
        }
        if(otherName!=null && !"".equals(otherName)){
            sql.append(" AND \"USER\".other_name LIKE '%"+ otherName +"%'");
        }
        if(email!=null && !"".equals(email)){
            sql.append(" AND \"USER\".email LIKE '%"+ email +"%'");
        }
        if(nicNumber!=null && !"".equals(nicNumber)){
            sql.append(" AND \"EMPLOYEE\".nic_number LIKE '%"+ nicNumber +"%'");
        }
        if(addressLine1!=null && !"".equals(addressLine1)){
            sql.append(" AND \"EMPLOYEE\".address_line1 LIKE '%"+ addressLine1 +"%'");
        }
        if(addressLine2!=null && !"".equals(addressLine2)){
            sql.append(" AND \"EMPLOYEE\".address_line2 LIKE '%"+ addressLine2 +"%'");
        }
        if(addressLine3!=null && !"".equals(addressLine3)){
            sql.append(" AND \"EMPLOYEE\".address_line3 LIKE '%"+ addressLine3 +"%'");
        }
        if(fixedLineNumbers!=null && !"".equals(fixedLineNumbers)){
            sql.append(" AND \"EMPLOYEE_FIXEDLINE\".fixedline_number LIKE '%"+ fixedLineNumbers +"%'");
        }
        if(mobileNumbers!=null && !"".equals(mobileNumbers)){
            sql.append(" AND \"EMPLOYEE_MOBILE\".mobile_number LIKE '%"+ mobileNumbers +"%'");
        }/*
        if(userRoleAdmin!=null && !"".equals(userRoleAdmin)){
            sql.append(" AND \"USER_ROLE\".role_name LIKE '%"+ userRoleAdmin +"%'");
        }
        if(userRoleManager!=null && !"".equals(userRoleManager)){
            sql.append(" AND \"EMPLOYEE\".employee_id LIKE '%"+ userRoleManager +"%'");
        }
        if(userRoleStaff!=null && !"".equals(userRoleStaff)){
            sql.append(" AND \"EMPLOYEE\".employee_id LIKE '%"+ userRoleStaff +"%'");
        }*/

        /*if(limit > 0 && offset >= 0){
            sql.append("LIMIT "+limit+" OFFSET " + offset);
        }*/
        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql.toString());
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                employee = new Employee();
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setUserName(resultSet.getString("user_name"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }


    public List<Employee> getEmployeesBasicInfo(int limit, int offset,String employeeNumber,String userName,String firstName, String lastName, String otherName, String email, String nicNumber,
                                       String addressLine1, String addressLine2, String addressLine3, String fixedLineNumbers,
                                       String mobileNumbers, String userRoleAdmin, String userRoleManager, String userRoleStaff,
                                       UserLogin creatingUser){
        List<Employee> employees = new ArrayList<Employee>();
        Employee employee = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM " + APPStatics.schemaName + ".\"EMPLOYEE\", " + APPStatics.schemaName + ".\"USER\" " +
                "WHERE \n" +
                "  \"EMPLOYEE\".user_name = \"USER\".user_name \n");

        if(employeeNumber!=null && !"".equals(employeeNumber)){
            sql.append(" AND UPPER(\"EMPLOYEE\".employee_id) LIKE '%"+ employeeNumber.toUpperCase() +"%'");
        }
        if(userName!=null && !"".equals(userName)){
            sql.append(" AND UPPER(\"EMPLOYEE\".user_name) LIKE '%"+ userName.toUpperCase() +"%'");
        }
        if(firstName!=null && !"".equals(firstName)){
            sql.append(" AND UPPER(\"USER\".first_name) LIKE '%"+ firstName.toUpperCase() +"%'");
        }
        if(lastName!=null && !"".equals(lastName)){
            sql.append(" AND UPPER(\"USER\".last_name) LIKE '%"+ lastName.toUpperCase() +"%'");
        }
        if(otherName!=null && !"".equals(otherName)){
            sql.append(" AND UPPER(\"USER\".other_name) LIKE '%"+ otherName.toUpperCase() +"%'");
        }
        if(email!=null && !"".equals(email)){
            sql.append(" AND UPPER(\"USER\".email) LIKE '%"+ email.toUpperCase() +"%'");
        }
        if(nicNumber!=null && !"".equals(nicNumber)){
            sql.append(" AND UPPER(\"EMPLOYEE\".nic_number) LIKE '%"+ nicNumber.toUpperCase() +"%'");
        }
        if(addressLine1!=null && !"".equals(addressLine1)){
            sql.append(" AND UPPER(\"EMPLOYEE\".address_line1) LIKE '%"+ addressLine1.toUpperCase() +"%'");
        }
        if(addressLine2!=null && !"".equals(addressLine2)){
            sql.append(" AND UPPER(\"EMPLOYEE\".address_line2) LIKE '%"+ addressLine2.toUpperCase() +"%'");
        }
        if(addressLine3!=null && !"".equals(addressLine3)){
            sql.append(" AND UPPER(\"EMPLOYEE\".address_line3) LIKE '%"+ addressLine3.toUpperCase() +"%'");
        }
        if(fixedLineNumbers!=null && !"".equals(fixedLineNumbers)){
            sql.append(" AND \"EMPLOYEE\".employee_id IN (SELECT employee_id FROM " + APPStatics.schemaName + ".\"EMPLOYEE_FIXEDLINE\" WHERE \"EMPLOYEE_FIXEDLINE\".fixedline_number LIKE '%"+ fixedLineNumbers +"%')");
        }
        if(mobileNumbers!=null && !"".equals(mobileNumbers)){
            sql.append(" AND \"EMPLOYEE\".employee_id IN (SELECT employee_id FROM " + APPStatics.schemaName + ".\"EMPLOYEE_MOBILE\" WHERE \"EMPLOYEE_MOBILE\".mobile_number LIKE '%"+ mobileNumbers +"%')");
        }
        if(userRoleAdmin!=null && !"".equals(userRoleAdmin)){
            sql.append(" AND \"EMPLOYEE\".user_name IN (SELECT user_name FROM " + APPStatics.schemaName + ".\"USER_ROLE\" WHERE role_name LIKE '%"+ userRoleAdmin +"%')");
        }
        if(userRoleManager!=null && !"".equals(userRoleManager)){
            sql.append(" AND \"EMPLOYEE\".user_name IN (SELECT user_name FROM " + APPStatics.schemaName + ".\"USER_ROLE\" WHERE role_name LIKE '%"+ userRoleManager +"%')");
        }
        if(userRoleStaff!=null && !"".equals(userRoleStaff)){
            sql.append(" AND \"EMPLOYEE\".user_name IN (SELECT user_name FROM " + APPStatics.schemaName + ".\"USER_ROLE\" WHERE role_name LIKE '%"+ userRoleStaff +"%')");
        }

        sql.append(" ORDER BY \"EMPLOYEE\".employee_id ");

        if(limit > 0 && offset >= 0){
            sql.append("LIMIT "+limit+" OFFSET " + offset);
        }

        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql.toString());
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                employee = new Employee();
                employee.setId(resultSet.getString("employee_id"));
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setUserName(resultSet.getString("user_name"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public Employee getEmployeeByEID(String employeeID) {
        Employee employee = null;
        String sql = "SELECT * FROM " + APPStatics.schemaName + ".\"EMPLOYEE\" e " +
                "LEFT JOIN " + APPStatics.schemaName + ".\"USER\" u ON e.user_name = u.user_name " +
                "LEFT JOIN " + APPStatics.schemaName + ".\"USER_ROLE\" ur ON e.user_name = ur.user_name "+
                "LEFT JOIN " + APPStatics.schemaName + ".\"ROLE_PORTAL\" rp ON ur.role_name = rp.role_name " +
                "LEFT JOIN " + APPStatics.schemaName + ".\"PORTAL\" p ON rp.portal_name = p.portal_name " +
                "LEFT JOIN " + APPStatics.schemaName + ".\"EMPLOYEE_FIXEDLINE\" fl ON fl.employee_id = e.employee_id "+
                "LEFT JOIN " + APPStatics.schemaName + ".\"EMPLOYEE_MOBILE\" em ON em.employee_id = e.employee_id " +
                "WHERE e.employee_id = ? ";
        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,employeeID);
            ResultSet resultSet = statement.executeQuery();
            HashSet<String> portals;
            HashSet<String> roles;
            HashSet<String> fixedLinesNumbers;
            HashSet<String> mobileNumbers;

            if(resultSet.next()){
                employee = new Employee();
                employee.setId(employeeID);
                employee.setUserName(resultSet.getString("user_name"));
                employee.setEmail(resultSet.getString("email"));
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setOtherName(resultSet.getString("other_name"));
                employee.setDateOfBirth(resultSet.getDate("date_of_birth"));
                employee.setNicNumber(resultSet.getString("nic_number"));
                employee.setAddressLine1(resultSet.getString("address_line1"));
                employee.setAddressLine2(resultSet.getString("address_line2"));
                employee.setAddressLine3(resultSet.getString("address_line3"));

                portals = new HashSet<String>();
                portals.add(resultSet.getString("portal_name"));

                roles = new HashSet<String>();
                roles.add(resultSet.getString("role_name"));

                fixedLinesNumbers = new HashSet<String>();
                fixedLinesNumbers.add(resultSet.getString("fixedline_number"));

                mobileNumbers = new HashSet<String>();
                mobileNumbers.add(resultSet.getString("mobile_number"));

                while(resultSet.next()){
                    //hash set ignores duplicate role entries
                    roles.add(resultSet.getString("role_name"));
                    portals.add(resultSet.getString("portal_name"));
                    fixedLinesNumbers.add(resultSet.getString("fixedline_number"));
                    mobileNumbers.add(resultSet.getString("mobile_number"));
                }
                employee.setPortals(new ArrayList<String>(portals));
                employee.setRoles(new ArrayList<String>(roles));
                employee.setFixedLineNumbers(new ArrayList<String>(fixedLinesNumbers));
                employee.setMobileNumbers(new ArrayList<String>(mobileNumbers));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    public int getEmployeesCount(String employeeNumber, String userName, String firstName, String lastName, String otherName, String email,
                                 String nicNumber, String addressLine1, String addressLine2, String addressLine3, String fixedLineNumber,
                                 String mobileNumber, String userRoleAdmin, String userRoleManager, String userRoleStaff, UserLogin userLogin) {
        int count = 0;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(employee_id) AS c FROM " + APPStatics.schemaName + ".\"EMPLOYEE\", " + APPStatics.schemaName + ".\"USER\" " +
                "WHERE \n" +
                "  \"EMPLOYEE\".user_name = \"USER\".user_name \n");

        if(employeeNumber!=null && !"".equals(employeeNumber)){
            sql.append(" AND UPPER(\"EMPLOYEE\".employee_id) LIKE '%"+ employeeNumber.toUpperCase() +"%'");
        }
        if(userName!=null && !"".equals(userName)){
            sql.append(" AND UPPER(\"EMPLOYEE\".user_name) LIKE '%"+ userName.toUpperCase() +"%'");
        }
        if(firstName!=null && !"".equals(firstName)){
            sql.append(" AND UPPER(\"USER\".first_name) LIKE '%"+ firstName.toUpperCase() +"%'");
        }
        if(lastName!=null && !"".equals(lastName)){
            sql.append(" AND UPPER(\"USER\".last_name) LIKE '%"+ lastName.toUpperCase() +"%'");
        }
        if(otherName!=null && !"".equals(otherName)){
            sql.append(" AND UPPER(\"USER\".other_name) LIKE '%"+ otherName.toUpperCase() +"%'");
        }
        if(email!=null && !"".equals(email)){
            sql.append(" AND UPPER(\"USER\".email) LIKE '%"+ email.toUpperCase() +"%'");
        }
        if(nicNumber!=null && !"".equals(nicNumber)){
            sql.append(" AND UPPER(\"EMPLOYEE\".nic_number) LIKE '%"+ nicNumber.toUpperCase() +"%'");
        }
        if(addressLine1!=null && !"".equals(addressLine1)){
            sql.append(" AND UPPER(\"EMPLOYEE\".address_line1) LIKE '%"+ addressLine1.toUpperCase() +"%'");
        }
        if(addressLine2!=null && !"".equals(addressLine2)){
            sql.append(" AND UPPER(\"EMPLOYEE\".address_line2) LIKE '%"+ addressLine2.toUpperCase() +"%'");
        }
        if(addressLine3!=null && !"".equals(addressLine3)){
            sql.append(" AND UPPER(\"EMPLOYEE\".address_line3) LIKE '%"+ addressLine3.toUpperCase() +"%'");
        }
        if(fixedLineNumber!=null && !"".equals(fixedLineNumber)){
            sql.append(" AND \"EMPLOYEE\".employee_id IN (SELECT employee_id FROM " + APPStatics.schemaName + ".\"EMPLOYEE_FIXEDLINE\" WHERE \"EMPLOYEE_FIXEDLINE\".fixedline_number LIKE '%"+ fixedLineNumber +"%')");
        }
        if(mobileNumber!=null && !"".equals(mobileNumber)){
            sql.append(" AND \"EMPLOYEE\".employee_id IN (SELECT employee_id FROM " + APPStatics.schemaName + ".\"EMPLOYEE_MOBILE\" WHERE \"EMPLOYEE_MOBILE\".mobile_number LIKE '%"+ mobileNumber +"%')");
        }
        if(userRoleAdmin!=null && !"".equals(userRoleAdmin)){
            sql.append(" AND \"EMPLOYEE\".user_name IN (SELECT user_name FROM " + APPStatics.schemaName + ".\"USER_ROLE\" WHERE role_name LIKE '%"+ userRoleAdmin +"%')");
        }
        if(userRoleManager!=null && !"".equals(userRoleManager)){
            sql.append(" AND \"EMPLOYEE\".user_name IN (SELECT user_name FROM " + APPStatics.schemaName + ".\"USER_ROLE\" WHERE role_name LIKE '%"+ userRoleManager +"%')");
        }
        if(userRoleStaff!=null && !"".equals(userRoleStaff)){
            sql.append(" AND \"EMPLOYEE\".user_name IN (SELECT user_name FROM " + APPStatics.schemaName + ".\"USER_ROLE\" WHERE role_name LIKE '%"+ userRoleStaff +"%')");
        }
        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql.toString());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            count = resultSet.getInt("c");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public Employee updateEmployee(String employeeNumber, String firstName, String lastName, String otherName, Date date, String email, String nicNumber,
                                   String addressLine1, String addressLine2, String addressLine3, List<String> fixedLineNumbers, List<String> mobileNumbers,
                                   List<String> userRoles, UserLogin updatingUser) throws Exception {
        Employee employee = null;
        String userName = null;

        String getUserName = "SELECT user_name FROM " + APPStatics.schemaName + ".\"EMPLOYEE\" WHERE employee_id = ?";
        String sqlUpdateUser = "UPDATE " + APPStatics.schemaName + ".\"USER\" SET email=?,first_name=?,last_name=?,other_name=?,date_of_birth=? WHERE user_name=?";
        String sqlUpdateEmployee = "UPDATE " + APPStatics.schemaName + ".\"EMPLOYEE\" SET address_line1=?,address_line2=?,address_line3=?,nic_number=?," +
                "last_updated_by=? WHERE employee_id=?";

        String removeRoles = "DELETE FROM " + APPStatics.schemaName + ".\"USER_ROLE\" WHERE user_name=?";
        String assignRole = "INSERT INTO " + APPStatics.schemaName + ".\"USER_ROLE\" (user_name,role_name) VALUES(?,?)";

        String removeFixedLineNumbers = "DELETE FROM " + APPStatics.schemaName + ".\"EMPLOYEE_FIXEDLINE\" WHERE employee_id=?";
        String insertFixedLineNumbers = "INSERT INTO " + APPStatics.schemaName + ".\"EMPLOYEE_FIXEDLINE\" (employee_id,fixedline_number) VALUES (?,?)";

        String removeMobileNumbers = "DELETE FROM " + APPStatics.schemaName + ".\"EMPLOYEE_MOBILE\" WHERE employee_id=?";
        String insertMobileNumbers = "INSERT INTO " + APPStatics.schemaName + ".\"EMPLOYEE_MOBILE\" (employee_id,mobile_number) VALUES (?,?)";

        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        try {
            connection.setAutoCommit(false);    //to process as transaction

            try{
                PreparedStatement getUserNamePS = connection.prepareStatement(getUserName);
                getUserNamePS.setString(1,employeeNumber);
                ResultSet getUserNameRS = getUserNamePS.executeQuery();
                getUserNameRS.next();
                userName = getUserNameRS.getString("user_name").toLowerCase();
            }catch (SQLException e){
                if(e.getSQLState().equalsIgnoreCase(APPStatics.DBErrorCodes.INVALID_CURSOR_STATE)){
                    throw new Exception("Non-existing Employee ("+ employeeNumber +")");
                }else {
                    throw new Exception(e.getSQLState());
                }
            }

            PreparedStatement updateUserPS = connection.prepareStatement(sqlUpdateUser);
            updateUserPS.setString(1,email);
            updateUserPS.setString(2,firstName);
            updateUserPS.setString(3,lastName);
            updateUserPS.setString(4,otherName);
            updateUserPS.setDate(5, new java.sql.Date(date.getTime()));
            updateUserPS.setString(6, userName);
            updateUserPS.execute();

            PreparedStatement updateEmployeePS = connection.prepareStatement(sqlUpdateEmployee);
            updateEmployeePS.setString(1,addressLine1);
            updateEmployeePS.setString(2,addressLine2);
            updateEmployeePS.setString(3,addressLine3);
            updateEmployeePS.setString(4,nicNumber);
            updateEmployeePS.setString(5,updatingUser.getUserName());
            updateEmployeePS.setString(6,employeeNumber);
            updateEmployeePS.execute();

            PreparedStatement removeRolesPS = connection.prepareStatement(removeRoles);
            removeRolesPS.setString(1,userName);
            removeRolesPS.execute();

            PreparedStatement assignRolePS = connection.prepareStatement(assignRole);
            for (String role : userRoles){
                assignRolePS.setString(1,userName.toString().toLowerCase());
                assignRolePS.setString(2,role);
                assignRolePS.execute();
            }

            PreparedStatement removeFixedLineNumbersPS = connection.prepareStatement(removeFixedLineNumbers);
            removeFixedLineNumbersPS.setString(1,employeeNumber);
            removeFixedLineNumbersPS.execute();

            PreparedStatement insertFixedLineNumbersPS = connection.prepareStatement(insertFixedLineNumbers);
            for (String fixedLine : fixedLineNumbers){
                insertFixedLineNumbersPS.setString(1,employeeNumber);
                insertFixedLineNumbersPS.setString(2,fixedLine);
                try{
                    insertFixedLineNumbersPS.execute();
                } catch (SQLException e) {
                    connection.rollback();
                    e.printStackTrace();
                    if(e.getSQLState().equalsIgnoreCase(APPStatics.DBErrorCodes.UNIQUE_KEY_VIOLATION)){
                        throw new Exception("Duplicate Fixed Line Number");
                    }else {
                        throw new Exception(e.getSQLState());
                    }
                }
            }

            PreparedStatement removeMobileNumbersPS = connection.prepareStatement(removeMobileNumbers);
            removeMobileNumbersPS.setString(1,employeeNumber);
            removeMobileNumbersPS.execute();

            PreparedStatement insertMobileNumbersPS = connection.prepareStatement(insertMobileNumbers);
            for (String mobile : mobileNumbers){
                insertMobileNumbersPS.setString(1,employeeNumber);
                insertMobileNumbersPS.setString(2,mobile);
                try{
                    insertMobileNumbersPS.execute();
                } catch (SQLException e) {
                    connection.rollback();
                    e.printStackTrace();
                    if(e.getSQLState().equalsIgnoreCase(APPStatics.DBErrorCodes.UNIQUE_KEY_VIOLATION)){
                        throw new Exception("Duplicate Mobile Number");
                    }else {
                        throw new Exception(e.getSQLState());
                    }
                }
            }


            connection.commit();

            employee = new Employee();
            employee.setUserName(userName.toString().toLowerCase());
            employee.setDateOfBirth(date);
            employee.setAddressLine1(addressLine1);
            employee.setAddressLine2(addressLine2);
            employee.setAddressLine3(addressLine3);
            employee.setEmail(email);
            //employee.setEnteredBy(null);
            employee.setFirstName(firstName);
            employee.setLastName(lastName);
            employee.setOtherName(otherName);
            employee.setId(employeeNumber);
            employee.setNicNumber(nicNumber);


        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            if(e.getSQLState().equalsIgnoreCase(APPStatics.DBErrorCodes.UNIQUE_KEY_VIOLATION)){
                throw new Exception("User Already Exists");
            }else if(e.getSQLState().equalsIgnoreCase(APPStatics.DBErrorCodes.STRING_LENGTH_TOO_LONG)){
                throw new Exception("String too long");
            }else {
                throw new Exception(e.getSQLState());
            }

        }
        finally {
            try {
                connection.commit();
                connection.setAutoCommit(true);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        return employee;

    }

    public void deleteEmployee(String employeeNumber, UserLogin userLogin) throws Exception {
        String sql = "DELETE FROM " + APPStatics.schemaName + ".\"USER\" WHERE user_name = (SELECT user_name FROM " + APPStatics.schemaName + ".\"EMPLOYEE\" WHERE employee_id = ?)";
        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,employeeNumber);
            int result = statement.executeUpdate();
            if(result==0){
                throw new Exception("Employee not deleted");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
