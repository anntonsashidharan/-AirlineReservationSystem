package com.ars.service.signup;

import com.ars.dao.user.UserDAO;

/**
 * Created by JJ on 4/30/16.
 */
public class SignupService {

    public static void registerUser(String userName, String password) throws Exception{
        UserDAO userDAO = new UserDAO();
        String status = userDAO.createInternetUser(userName,password);
        if(status!=null){
            throw new Exception(status);
        }
    }
}
