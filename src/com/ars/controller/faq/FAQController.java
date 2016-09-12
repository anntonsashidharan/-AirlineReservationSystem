package com.ars.controller.faq;

import com.ars.domain.faq.FAQQuestion;
import com.ars.domain.user.UserLogin;
import com.ars.service.faq.FAQService;
import com.ars.system.APPStatics;
import com.ars.util.validation.Validation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by JJ on 5/3/16.
 */
@WebServlet(urlPatterns = "/faq")
public class FAQController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserLogin loggedUser = (UserLogin)request.getSession().getAttribute(APPStatics.SessionStatics.LOGGED_USER);
        //if answer submission
        try{
            if(request.getParameter("submitAnswer")!=null){
                if(Validation.validateUserForFAQQuestionSubmission(loggedUser)){
                    String questionID = request.getParameter("questionID");
                    String answer = request.getParameter("newAnswerTextArea");
                    FAQService.createAnswer(questionID,answer,loggedUser);
                }else{
                    throw new Exception("User not autherized to post answers");
                }
                request.setAttribute(APPStatics.RequestStatics.SUCCESS_MESSAGE,"Answer updated successfully");
                response.sendRedirect("/faq");
                return;
            }

            //if question submission
            if(request.getParameter("submitQuestion")!=null){
                String question = request.getParameter("question").trim();
                FAQService.createQuestion(loggedUser,question);
                request.setAttribute(APPStatics.RequestStatics.SUCCESS_MESSAGE,"Question submitted successfully");
                response.sendRedirect("/faq");
                return;
            }

            //when post request recieved by search button or by page navigation buttons
            //if((request.getParameter("searchText")!=null && !"".equals(request.getParameter("searchText").trim())) || request.getParameter("navigation")!=null){
            if(request.getParameter("searchText")!=null){
                int count;
                if(request.getParameter("newFAQSearch")!=null){
                    count = FAQService.getFAQCount(request.getParameter("searchText").trim());
                    request.setAttribute("numberOfRecords",count);
                    request.setAttribute("searchText",request.getParameter("searchText"));
                    request.setAttribute("recordsPerPage",2);
                    request.setAttribute("pageNumber",1);
                }else{
                    count = Integer.parseInt(request.getParameter("numberOfRecords"));
                }
                if(count<=0){
                    request.setAttribute(APPStatics.RequestStatics.ERROR_MESSAGE,"No FAQ found related to your search criteria");
                    request.getRequestDispatcher("/jsp/faq/faq.jsp").forward(request,response);
                }else{
                    int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
                    int numberOfRecordsPerPage = Integer.parseInt(request.getParameter("recordsPerPage"));
                    List<FAQQuestion> faqQuestions = FAQService.getFAQQuestionsAndAnswers(request.getParameter("searchText").trim(), numberOfRecordsPerPage, pageNumber);
                    request.setAttribute("recordsPerPage",numberOfRecordsPerPage);
                    request.setAttribute("pageNumber",pageNumber);
                    request.setAttribute("numberOfRecords",count);
                    request.setAttribute("searchText",request.getParameter("searchText"));

                    //request.setAttribute("searchText", request.getParameter("searchText").trim());
                    //request.setAttribute(APPStatics.RequestStatics.NUMBER_OF_PAGES, Math.ceil((double) count / numberOfRecordsPerPage));

                    request.setAttribute(APPStatics.RequestStatics.FAQS,faqQuestions);
                    request.getRequestDispatcher("/jsp/faq/faq.jsp").forward(request,response);
                }
            }
            response.sendRedirect("/faq");
        }catch (Exception e){
            request.setAttribute(APPStatics.RequestStatics.ERROR_MESSAGE,e.getMessage()!=null?e.getMessage():"Null pointer exception");
            request.getRequestDispatcher("/jsp/faq/faq.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute(APPStatics.SessionStatics.REDIRECT_PAGE_AFTER_LOGIN,"/faq");
        try{
            int count = FAQService.getFAQCount(null);
            request.setAttribute("numberOfRecords",count);
            request.setAttribute("recordsPerPage",2);
            request.setAttribute("pageNumber",1);
            request.setAttribute("searchText",null);

            List<FAQQuestion> faqQuestions = FAQService.getFAQQuestionsAndAnswers(null, 2, 1);
            request.setAttribute(APPStatics.RequestStatics.FAQS,faqQuestions);

            //if user not confirmed via emeil link dont allow posting question
            UserLogin loggedUser = (UserLogin)request.getSession().getAttribute(APPStatics.SessionStatics.LOGGED_USER);
            if(loggedUser!=null && !loggedUser.isMailConfirmed()){
                request.setAttribute(APPStatics.RequestStatics.INFORMATION_MESSAGE,"Please confirm your account by clicking the url sent to your email to post question");
            }


            request.getRequestDispatcher("/jsp/faq/faq.jsp").forward(request,response);
        }catch (Exception e){
            request.setAttribute(APPStatics.RequestStatics.ERROR_MESSAGE,e.getMessage()!=null?e.getMessage():"Null pointer exception");
            request.getRequestDispatcher("/jsp/faq/faq.jsp").forward(request,response);
        }
    }
}
