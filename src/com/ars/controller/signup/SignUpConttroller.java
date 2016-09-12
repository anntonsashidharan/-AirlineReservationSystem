package com.ars.controller.signup;

import com.ars.domain.user.UserLogin;
import com.ars.service.login.LoginService;
import com.ars.service.signup.SignupService;
import com.ars.system.APPStatics;
import com.ars.util.email.EmailSender;
import com.ars.util.validation.Validation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by JJ on 4/29/16.
 */
@WebServlet(urlPatterns = "/signup")
public class SignUpConttroller extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            String userName = request.getParameter("email").trim();
            String password = request.getParameter("password").trim();

            //if invalid data exception will thrown
            Validation.validateSignUpData(userName,password);

            //throw exception if user already exists
            SignupService.registerUser(userName, password);


            //if all perfect
            UserLogin userLogin = LoginService.doLogin(userName, password);

            //prepare link for email confirmation
            String emailConfirmationLink = "localhost:8080/login?"+
                    APPStatics.RequestStatics.USER_NAME + "=" + userLogin.getUserName() + "&"+
                    APPStatics.RequestStatics.ONETIME_PASSWORD + "=" + userLogin.getOneTimePassword() + "&" +
                    APPStatics.RequestStatics.EMAIL_CONFIRMATION + "=1";
            EmailSender.sendEmail(userLogin.getEmail(),"Welcome to express Booking",emailConfirmationLink);

            request.getSession().setAttribute(APPStatics.SessionStatics.LOGGED_USER,userLogin);
            if(userLogin.isMailConfirmed()==true){
                response.sendRedirect("/userProfile");
            }else{
                response.sendRedirect("/login");
            }

        }catch (Exception e){
            request.setAttribute("errorMessage",e.getMessage());
            request.getRequestDispatcher("/jsp/signup/signup.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserLogin userLogin = (UserLogin)request.getSession().getAttribute(APPStatics.SessionStatics.LOGGED_USER);
        if(userLogin==null){
            request.getRequestDispatcher("/jsp/signup/signup.jsp").forward(request,response);
        }else{
            response.sendRedirect("/main");
        }
    }
}
