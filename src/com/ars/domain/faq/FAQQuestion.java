package com.ars.domain.faq;

import com.ars.domain.user.UserLogin;

import java.util.Date;
import java.util.List;

/**
 * Created by JJ on 5/3/16.
 */
public class FAQQuestion {
    private String questionId;
    private String question;
    private UserLogin user;
    private Date date;
    private Date time;
    private List<FAQAnswer> faqAnswers;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public UserLogin getUser() {
        return user;
    }

    public void setUser(UserLogin user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<FAQAnswer> getFaqAnswers() {
        return faqAnswers;
    }

    public void setFaqAnswers(List<FAQAnswer> faqAnswers) {
        this.faqAnswers = faqAnswers;
    }
}
