package com.ars.service.profile;

import com.ars.dao.user.UserDAO;
import com.ars.domain.user.UserLogin;
import com.ars.util.validation.Validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by JJ on 5/2/16.
 */
public class UserProfileService {
    public static void updateUser(UserLogin userLogin,String email,String oldPassword,String newPassword,String confirmPassword,String firstName,String lastName,String otherName,String datOfBirth) throws Exception {
        UserDAO userDAO = new UserDAO();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = simpleDateFormat.parse(datOfBirth);
        String error;
        if(newPassword!=null && !newPassword.equals("")){
            UserLogin validatedUser = userDAO.getUserLogin(userLogin.getUserName(),oldPassword);
            if(validatedUser==null){
                throw new Exception("Incorrect password");
            }
            Validation.validatePasswordChange(newPassword, confirmPassword);
            error = userDAO.updateInternetUserByUserNameWithPassword(userLogin.getUserName(), newPassword, email, firstName, lastName, otherName, date);

        }else{
            error = userDAO.updateInternetUserByUserNameWithoutPassword(userLogin.getUserName(), email, firstName, lastName, otherName, date);
        }
        if(error!=null){
            throw new Exception("Something gone wrong");
        }else{
            userLogin.setFirstName(firstName);
            userLogin.setLastName(lastName);
            userLogin.setOtherName(otherName);
            userLogin.setEmail(email);
            userLogin.setDateOfBirth(date);
        }

    }
}
