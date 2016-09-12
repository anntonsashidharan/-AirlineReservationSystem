package com.ars.service.faq;

import com.ars.dao.faq.FAQQuestionDAO;
import com.ars.domain.faq.FAQAnswer;
import com.ars.domain.faq.FAQQuestion;
import com.ars.domain.user.UserLogin;

import java.util.List;

/**
 * Created by JJ on 5/3/16.
 */
public class FAQService {
    public static List<FAQQuestion> getAllFAQQuestionsAndAnswers(){
        FAQQuestionDAO faqQuestionDAO = new FAQQuestionDAO();
        return faqQuestionDAO.getAllFAQQuestionAndAnswers();
    }

    /**
     *
     * @param searchText matches any question ar answer contains this text
     * @param numberOfRecordPerPage number of record to retrieve if no limit (to retrieve all) parse 0 as value
     * @param pageNumber page number to be displayed
     * @return
     */
    public static List<FAQQuestion> getFAQQuestionsAndAnswers(String searchText,int numberOfRecordPerPage,int pageNumber){
        FAQQuestionDAO faqQuestionDAO = new FAQQuestionDAO();
        return faqQuestionDAO.getFAQQuestionAndAnswers(searchText,numberOfRecordPerPage,(pageNumber-1)*numberOfRecordPerPage);
    }

    public static void createQuestion(UserLogin userLogin,String question) throws Exception {
        FAQQuestionDAO faqQuestionDAO = new FAQQuestionDAO();
        String status = faqQuestionDAO.createQuestion(userLogin,question);
        if(status!=null){
            throw new Exception(status);
        }
    }

    public static void createAnswer(String questionID, String answer, UserLogin loggedUser) throws Exception {
        FAQQuestionDAO faqQuestionDAO = new FAQQuestionDAO();
        String status = faqQuestionDAO.createAnswer(questionID,answer,loggedUser);
        if(status!=null){
            throw new Exception(status);
        }
    }

    public static int getFAQCount(String searchText) throws Exception {
        FAQQuestionDAO faqQuestionDAO = new FAQQuestionDAO();
        return faqQuestionDAO.getFAQCount(searchText);
    }
}
