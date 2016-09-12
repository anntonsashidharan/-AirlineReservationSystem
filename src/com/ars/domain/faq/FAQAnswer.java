package com.ars.domain.faq;

import com.ars.domain.user.UserLogin;

import java.util.Date;
import java.util.List;

/**
 * Created by JJ on 5/3/16.
 */
public class FAQAnswer {
    private String answerId;
    private String answer;
    private UserLogin user;
    private Date date;
    private Date time;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    private FAQQuestion faqQuestion;

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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

    public FAQQuestion getFaqQuestion() {
        return faqQuestion;
    }

    public void setFaqQuestion(FAQQuestion faqQuestion) {
        this.faqQuestion = faqQuestion;
    }
}
