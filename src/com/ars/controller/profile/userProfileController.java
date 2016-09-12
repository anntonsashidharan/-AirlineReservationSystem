package com.ars.controller.profile;

import com.ars.domain.user.UserLogin;
import com.ars.service.profile.UserProfileService;
import com.ars.system.APPStatics;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by JJ on 5/1/16.
 */
@WebServlet(urlPatterns = "/userProfile")
public class userProfileController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName =null;
        String firstName =null;
        String lastName =null;
        String otherName =null;
        String email =null;
        String dateOfBirth =null;
        String oldPassword =null;
        String newPassword =null;
        String confirmedPassword =null;
        try{
            UserLogin userLogin = (UserLogin)request.getSession().getAttribute(APPStatics.SessionStatics.LOGGED_USER);
            if(userLogin==null){
                request.setAttribute(APPStatics.RequestStatics.ERROR_MESSAGE,"User not logged");
                response.sendRedirect("/login");
            }else {
                userName = userLogin.getUserName();
                firstName = request.getParameter("firstname");
                lastName = request.getParameter("lastname");
                otherName = request.getParameter("othername");
                email = request.getParameter("email");
                dateOfBirth = request.getParameter("dateofbirth");
                oldPassword = request.getParameter("oldpassword");
                newPassword = request.getParameter("newpassword");
                confirmedPassword = request.getParameter("confirmpassword");

                UserProfileService.updateUser(userLogin,email,oldPassword,newPassword,confirmedPassword,firstName,lastName,otherName,dateOfBirth);

                request.setAttribute(APPStatics.RequestStatics.SUCCESS_MESSAGE,"User successfully Updated");
                request.getRequestDispatcher("/jsp/profile/userProfile.jsp").forward(request,response);
            }
        }catch (Exception e){
            request.setAttribute(APPStatics.RequestStatics.ERROR_MESSAGE,e.getMessage()!=null?e.getMessage():"Null pointer exception");
            request.getRequestDispatcher("/jsp/profile/userProfile.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute(APPStatics.SessionStatics.REDIRECT_PAGE_AFTER_LOGIN,"/userProfile");
        UserLogin userLogin = (UserLogin)request.getSession().getAttribute(APPStatics.SessionStatics.LOGGED_USER);
        if(userLogin==null){
            response.sendRedirect("/login");
        }else {
            request.getRequestDispatcher("/jsp/profile/userProfile.jsp").forward(request, response);
        } 
    }
}
