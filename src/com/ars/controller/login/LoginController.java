package com.ars.controller.login;

import com.ars.domain.user.UserLogin;
import com.ars.service.login.LoginService;
import com.ars.system.APPStatics;
import com.ars.util.validation.Validation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by JJ on 4/30/16.
 */
@WebServlet(urlPatterns = "/login")
public class LoginController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = null;
        String password = null;
        String emailConfirmationFlag = null;
        String otp = null;  //one time password

        userName = request.getParameter(APPStatics.RequestStatics.USER_NAME);
        password = request.getParameter(APPStatics.RequestStatics.PASSWORD);
        emailConfirmationFlag = request.getParameter(APPStatics.RequestStatics.EMAIL_CONFIRMATION);
        otp = request.getParameter(APPStatics.RequestStatics.ONETIME_PASSWORD);
        try {
            UserLogin loggedUser = (UserLogin) request.getSession().getAttribute(APPStatics.SessionStatics.LOGGED_USER);
            if (loggedUser == null) {
                if(emailConfirmationFlag!=null && Integer.parseInt(emailConfirmationFlag)==1 ){
                    Validation.validateLoginData(userName, otp);
                    loggedUser = LoginService.doFirstLogin(userName, otp);
                    request.getSession().setAttribute(APPStatics.SessionStatics.LOGGED_USER, loggedUser);
                    if(loggedUser.isMailConfirmed()){
                        request.setAttribute(APPStatics.RequestStatics.INFORMATION_MESSAGE, "Your email is confirmed");
                        response.sendRedirect("/main");
                    }else{
                        request.getRequestDispatcher("/jsp/login/emailConfirmationRequred.jsp").forward(request, response);
                    }
                }/*else if (emailConfirmationFlag!=null && Integer.parseInt(emailConfirmationFlag)==0){
                   //rest password url
                }*/else if (!request.getParameter("submit").equals(null)){  //for login form submision
                    Validation.validateLoginData(userName, password);
                    loggedUser = LoginService.doLogin(userName, password);
                    request.getSession().setAttribute(APPStatics.SessionStatics.LOGGED_USER, loggedUser);
                    if(loggedUser.isMailConfirmed() || loggedUser.getRoles().contains("admin") || loggedUser.getRoles().contains("manager") || loggedUser.getRoles().contains("staff")){
                        if(request.getSession().getAttribute(APPStatics.SessionStatics.REDIRECT_PAGE_AFTER_LOGIN)!=null && !"".equals(request.getSession().getAttribute(APPStatics.SessionStatics.REDIRECT_PAGE_AFTER_LOGIN))){
                            response.sendRedirect((String)request.getSession().getAttribute(APPStatics.SessionStatics.REDIRECT_PAGE_AFTER_LOGIN));
                        }else {
                            response.sendRedirect("/main");
                        }
                    }else{
                        request.getRequestDispatcher("/jsp/login/emailConfirmationRequred.jsp").forward(request, response);
                    }
                }else{  //Initial visit to the page
                    request.getRequestDispatcher("/jsp/login/login.jsp").forward(request,response);
                }
            }else{
                if(loggedUser.isMailConfirmed() || loggedUser.getRoles().contains("admin") || loggedUser.getRoles().contains("manager") || loggedUser.getRoles().contains("staff")){
                    if(request.getSession().getAttribute(APPStatics.SessionStatics.REDIRECT_PAGE_AFTER_LOGIN)!=null && !"".equals(request.getSession().getAttribute(APPStatics.SessionStatics.REDIRECT_PAGE_AFTER_LOGIN))){
                        response.sendRedirect((String)request.getSession().getAttribute(APPStatics.SessionStatics.REDIRECT_PAGE_AFTER_LOGIN));
                    }else {
                        response.sendRedirect("/main");
                    }
                }else{
                    if(emailConfirmationFlag!=null && Integer.parseInt(emailConfirmationFlag)==1 ){
                        Validation.validateLoginData(userName, otp);
                        loggedUser = LoginService.doFirstLogin(userName, otp);
                        request.getSession().setAttribute(APPStatics.SessionStatics.LOGGED_USER, loggedUser);
                        if(loggedUser.isMailConfirmed()){
                            if(request.getSession().getAttribute(APPStatics.SessionStatics.REDIRECT_PAGE_AFTER_LOGIN)!=null && !"".equals(request.getSession().getAttribute(APPStatics.SessionStatics.REDIRECT_PAGE_AFTER_LOGIN))){
                                response.sendRedirect((String)request.getSession().getAttribute(APPStatics.SessionStatics.REDIRECT_PAGE_AFTER_LOGIN));
                            }else {
                                request.setAttribute(APPStatics.RequestStatics.INFORMATION_MESSAGE, "Your email is confirmed");
                                response.sendRedirect("/main");
                            }
                        }else{
                            request.getRequestDispatcher("/jsp/login/emailConfirmationRequred.jsp").forward(request, response);
                        }
                    }else{
                        request.getRequestDispatcher("/jsp/login/emailConfirmationRequred.jsp").forward(request, response);
                    }
                }
            }

            /*if (loggedUser == null) {
                userName = request.getParameter(APPStatics.RequestStatics.USER_NAME);
                password = request.getParameter(APPStatics.RequestStatics.PASSWORD);

                Validation.validateLoginData(userName, password);

                loggedUser = LoginService.doLogin(userName, password);
                request.getSession().setAttribute(APPStatics.SessionStatics.LOGGED_USER, loggedUser);
            }

            String emailConfirmation = request.getParameter(APPStatics.RequestStatics.EMAIL_CONFIRMATION);
            String oneTimePassword = request.getParameter(APPStatics.RequestStatics.ONETIME_PASSWORD);
            if (emailConfirmation != null && !"".equals(emailConfirmation) && oneTimePassword != null && !"".equals(oneTimePassword)) {
                loggedUser = LoginService.doFirstLogin(userName, oneTimePassword);
                request.getSession().setAttribute(APPStatics.SessionStatics.LOGGED_USER, loggedUser);
                response.sendRedirect("/main");
            } else {
                if (oneTimePassword != null && !"".equals(oneTimePassword)) {
                    loggedUser = LoginService.doLoginWithOneTimePW(userName, oneTimePassword);
                } else {
                    loggedUser = LoginService.doLogin(userName, password);
                }
                request.getSession().setAttribute(APPStatics.SessionStatics.LOGGED_USER, loggedUser);
                if (loggedUser.isMailConfirmed()) {
                    response.sendRedirect("/main");
                } else {
                    request.getRequestDispatcher("/jsp/login/emailConfirmationRequred.jsp").forward(request, response);
                }
            }

*/
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/jsp/login/login.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
