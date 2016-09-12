package com.ars.service.login;

import com.ars.dao.user.UserDAO;
import com.ars.domain.user.UserLogin;

/**
 * Created by JJ on 4/30/16.
 */
public class LoginService {
    public static UserLogin doLogin(String userName, String password) throws Exception{
        UserDAO userDAO = new UserDAO();
        UserLogin userLogin = userDAO.getUserLogin(userName,password);
        if(userLogin==null){
            throw new Exception("Incorrect Username Or Password");
        }else{
            return userLogin;
        }
    }

    public static UserLogin doLoginWithOneTimePW(String userName, String oneTimePassword) throws Exception{
        UserDAO userDAO = new UserDAO();
        UserLogin userLogin = userDAO.getUserLoginWithOneTimePW(userName, oneTimePassword);
        if(userLogin==null){
            throw new Exception("Incorrect Username Or Password");
        }else{
            return userLogin;
        }
    }

    /**
     * used to login by email as first login
     * @param userName
     * @param oneTimePassword
     * @return
     * @throws Exception
     */
    public static UserLogin doFirstLogin(String userName, String oneTimePassword) throws Exception {
        UserDAO userDAO = new UserDAO();
        UserLogin userLogin = userDAO.getUserLoginWithOneTimePW(userName,oneTimePassword);
        if(userLogin==null){
            throw new Exception("Incorrect Username Or Password");
        }else{
            userDAO.setEmailConfirmed(userName,true);
            userLogin.setMailConfirmed(true);
            return userLogin;
        }
    }
}
