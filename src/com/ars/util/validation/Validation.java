package com.ars.util.validation;

import com.ars.domain.user.UserLogin;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by JJ on 4/23/16.
 */
public class Validation {
    public static final Pattern VALID_EMAIL_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final int MIN_PASSWORD_LENGTH = 6;

    public static void validateFlightSearch(String source,String destination,
                                            XMLGregorianCalendar departDate,XMLGregorianCalendar arrivalDate,
                                            String oneWayOrRound,
                                            int numberOfAdult,
                                            int numberOfChild,
                                            int numberOfInfant) throws Exception{

    }

    public static void validateSignUpData(String userName, String password) throws Exception{
        if(userName==null || "".equals(userName)){
            throw new Exception("Please enter a username");
        }
        if(password==null || "".equals(password)){
            throw new Exception("Please enter a password");
        }
        Matcher matcher = VALID_EMAIL_REGEX .matcher(userName);
        if(matcher.find()==false){
            throw new Exception("Invalid username");
        }
        if(password.length()<MIN_PASSWORD_LENGTH){
            throw new Exception("Weak password");
        }
    }

    public static void validateLoginData(String userName, String password) throws Exception{
        if(userName==null || "".equals(userName)){
            throw new Exception("Please enter a username");
        }
        if(password==null || "".equals(password)){
            throw new Exception("Please enter a password");
        }
    }

    public static void validatePasswordChange(String newPassword, String confirmPassword) throws Exception {
        if(!newPassword.equals(confirmPassword)){
            throw new Exception("Incorrect confirm password");
        }
        if(newPassword.length()<MIN_PASSWORD_LENGTH){
            throw new Exception("Weak password");
        }
    }

    /**
     * return tru if valid user else false
     * @param userLogin
     * @return
     */
    public static boolean validateUserForFAQQuestionSubmission(UserLogin userLogin){
        if(userLogin==null || (!userLogin.getRoles().contains("admin") && !userLogin.getRoles().contains("staff") && !userLogin.getRoles().contains("manager"))){
            return false;
        }else{
            return true;
        }
    }

    public static void validateCreateEmployeeForm(String firstName, String lastName, String otherName, String dateOfBirth,
                                                  String email, String nicNumber, String addressLine1, String addressLine2,
                                                  String addressLine3, List<String> fixedLineNumbers,
                                                  List<String> mobileNumbers, String userRoleAdmin, String userRoleManager,
                                                  String userRoleStaff) throws Exception {
        if("".equals(firstName)){
            throw new Exception("First name is required");
        }
        if("".equals(dateOfBirth)){
            throw new Exception("Date of birth is required");
        }
        if("".equals(email)){
            throw new Exception("Email is required");
        }
        if("".equals(nicNumber)){
            throw new Exception("NIC number is required");
        }
        if("".equals(addressLine1)){
            throw new Exception("Address line1 is required");
        }
        if(fixedLineNumbers.size()==0 && mobileNumbers.size()==0){
            throw new Exception("Minimum one fixed line number or mobile number is required");
        }
        if(userRoleAdmin==null && userRoleManager==null && userRoleStaff==null){
            throw new Exception("Minimum one user roll is required");
        }


    }
}
