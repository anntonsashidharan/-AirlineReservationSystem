package com.ars.dao.faq;

import com.ars.domain.airport.Airport;
import com.ars.domain.faq.FAQAnswer;
import com.ars.domain.faq.FAQQuestion;
import com.ars.domain.user.UserLogin;
import com.ars.system.APPStatics;
import com.ars.util.db.DBConnection;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by JJ on 5/3/16.
 */
public class FAQQuestionDAO {
    public FAQQuestion getFAQQuestionAndAnswersByQuestionId(String questionId){
        FAQQuestion faqQuestion=null;
        String sql = "SELECT * FROM " + APPStatics.schemaName + ".\"FAQ_QUESTION\" q," + APPStatics.schemaName + " \"FAQ_ANSWER s \" " +
                "WHERE q.question_id = ? " +
                "AND q.question_id = a.question_id";
        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,questionId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                faqQuestion = new FAQQuestion();
                faqQuestion.setQuestionId(questionId);
                faqQuestion.setQuestion(resultSet.getString("question"));
                faqQuestion.setDate(resultSet.getDate("date"));

                UserLogin userLogin = new UserLogin();
                userLogin.setUserName(resultSet.getString("user_name"));
                faqQuestion.setUser(userLogin);

                List<FAQAnswer> faqAnswers = new ArrayList<FAQAnswer>();
                FAQAnswer faqAnswer = new FAQAnswer();
                faqAnswer.setAnswerId(resultSet.getString("answer_id"));
                faqAnswer.setAnswer(resultSet.getString("answer"));
                faqAnswer.setFaqQuestion(faqQuestion);
                faqAnswers.add(faqAnswer);
                while (resultSet.next()){
                    faqAnswer = new FAQAnswer();
                    faqAnswer.setAnswerId(resultSet.getString("answer_id"));
                    faqAnswer.setAnswer(resultSet.getString("answer"));
                    faqAnswer.setFaqQuestion(faqQuestion);
                    faqAnswers.add(faqAnswer);
                }

                faqQuestion.setFaqAnswers(faqAnswers);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return faqQuestion;
    }


    /**
     *
     * @return
     */
    public List<FAQQuestion> getAllFAQQuestionAndAnswers(){

        List<FAQQuestion> faqQuestions=new ArrayList<FAQQuestion>();
        String sql = "SELECT * FROM " + APPStatics.schemaName + ".\"FAQ_QUESTION\" q LEFT OUTER JOIN " + APPStatics.schemaName + " .\"FAQ_ANSWER\" a " +
                "ON q.question_id = a.question_id ORDER BY q.question_id";
        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            String currentQuestionID=null;

            //prepare list of questions from resultset
            if(resultSet.next()){
                FAQQuestion faqQuestion = new FAQQuestion();
                faqQuestions.add(faqQuestion);

                currentQuestionID = resultSet.getString("question_id");
                faqQuestion.setQuestionId(currentQuestionID);
                faqQuestion.setQuestion(resultSet.getString("question"));
                faqQuestion.setDate(resultSet.getDate("questioned_date"));
                faqQuestion.setTime(resultSet.getTime("questioned_time"));
                UserLogin userLogin = new UserLogin();
                userLogin.setUserName(resultSet.getString("user_name"));
                faqQuestion.setUser(userLogin);
                List<FAQAnswer> faqAnswers = new ArrayList<FAQAnswer>();
                faqQuestion.setFaqAnswers(faqAnswers);

                String answerId = resultSet.getString("answer_id");
                if(answerId!=null && !answerId.equals("")){
                    FAQAnswer faqAnswer = new FAQAnswer();
                    faqAnswer.setAnswerId(answerId);
                    faqAnswer.setAnswer(resultSet.getString("answer"));
                    UserLogin answeredUser = new UserLogin();
                    answeredUser.setUserName(resultSet.getString("user_name"));
                    faqAnswer.setUser(answeredUser);
                    faqAnswer.setDate(resultSet.getDate("answered_date"));
                    faqAnswer.setTime(resultSet.getTime("answered_time"));
                    faqAnswers.add(faqAnswer);
                }
                while (resultSet.next()){
                    String questionID =  resultSet.getString("question_id");
                    if(currentQuestionID.equalsIgnoreCase(questionID)){
                        answerId = resultSet.getString("answer_id");
                        if(answerId!=null && !answerId.equals("")){
                            FAQAnswer faqAnswer = new FAQAnswer();
                            faqAnswer.setAnswerId(answerId);
                            UserLogin answeredUser = new UserLogin();
                            answeredUser.setUserName(resultSet.getString("user_name"));
                            faqAnswer.setUser(answeredUser);
                            faqAnswer.setAnswer(resultSet.getString("answer"));
                            faqAnswer.setDate(resultSet.getDate("answered_date"));
                            faqAnswer.setTime(resultSet.getTime("answered_time"));
                            faqAnswers.add(faqAnswer);
                        }
                    }else{
                        faqQuestion = new FAQQuestion();
                        faqQuestions.add(faqQuestion);

                        currentQuestionID = resultSet.getString("question_id");
                        faqQuestion.setQuestionId(currentQuestionID);
                        faqQuestion.setQuestion(resultSet.getString("question"));
                        faqQuestion.setDate(resultSet.getDate("questioned_date"));
                        faqQuestion.setTime(resultSet.getTime("questioned_time"));
                        userLogin = new UserLogin();
                        userLogin.setUserName(resultSet.getString("user_name"));
                        faqQuestion.setUser(userLogin);
                        faqAnswers = new ArrayList<FAQAnswer>();
                        faqQuestion.setFaqAnswers(faqAnswers);

                        answerId = resultSet.getString("answer_id");
                        if(answerId!=null && !answerId.equals("")){
                            FAQAnswer faqAnswer = new FAQAnswer();
                            faqAnswer.setAnswerId(answerId);
                            UserLogin answeredUser = new UserLogin();
                            answeredUser.setUserName(resultSet.getString("user_name"));
                            faqAnswer.setUser(answeredUser);
                            faqAnswer.setAnswer(resultSet.getString("answer"));
                            faqAnswer.setDate(resultSet.getDate("answered_date"));
                            faqAnswer.setTime(resultSet.getTime("answered_time"));
                            faqAnswers.add(faqAnswer);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return faqQuestions;
    }


    /**
     *
     * @param searchString
     * @param limit
     * @param offset
     * @return
     */
    public List<FAQQuestion> getFAQQuestionAndAnswers(String searchString,int limit,int offset){

        List<FAQQuestion> faqQuestions=new ArrayList<FAQQuestion>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM (SELECT * FROM " + APPStatics.schemaName + ".\"FAQ_QUESTION\" qsub ORDER BY qsub.question_id");
        /*if (limit > 0 && offset >= 0) {
            sql.append(" LIMIT " + limit + " OFFSET " + offset);
        }*/
        sql.append(") AS q LEFT OUTER JOIN " + APPStatics.schemaName + " .\"FAQ_ANSWER\" a " +
                "ON q.question_id = a.question_id");
        if(searchString!=null && !"".equals(searchString)){
            sql.append(" WHERE LOWER(question) LIKE '%" + searchString.toLowerCase() +"%'" +
                            " OR LOWER(answer) LIKE '%" + searchString.toLowerCase() +"%'");
        }
        sql.append(" ORDER BY q.question_id");
        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql.toString());
            ResultSet resultSet = statement.executeQuery();
            String currentQuestionID=null;

            //prepare list of questions from resultset
            if(resultSet.next()){
                FAQQuestion faqQuestion = new FAQQuestion();
                faqQuestions.add(faqQuestion);

                currentQuestionID = resultSet.getString("question_id");
                faqQuestion.setQuestionId(currentQuestionID);
                faqQuestion.setQuestion(resultSet.getString("question"));
                faqQuestion.setDate(resultSet.getDate("questioned_date"));
                faqQuestion.setTime(resultSet.getTime("questioned_time"));
                UserLogin userLogin = new UserLogin();
                userLogin.setUserName(resultSet.getString("user_name"));
                faqQuestion.setUser(userLogin);
                List<FAQAnswer> faqAnswers = new ArrayList<FAQAnswer>();
                faqQuestion.setFaqAnswers(faqAnswers);

                String answerId = resultSet.getString("answer_id");
                if(answerId!=null && !answerId.equals("")){
                    FAQAnswer faqAnswer = new FAQAnswer();
                    faqAnswer.setAnswerId(answerId);
                    faqAnswer.setAnswer(resultSet.getString("answer"));
                    UserLogin answeredUser = new UserLogin();
                    answeredUser.setUserName(resultSet.getString("user_name"));
                    faqAnswer.setUser(answeredUser);
                    faqAnswer.setDate(resultSet.getDate("answered_date"));
                    faqAnswer.setTime(resultSet.getTime("answered_time"));
                    faqAnswers.add(faqAnswer);
                }
                while (resultSet.next()){
                    String questionID =  resultSet.getString("question_id");
                    if(currentQuestionID.equalsIgnoreCase(questionID)){
                        answerId = resultSet.getString("answer_id");
                        if(answerId!=null && !answerId.equals("")){
                            FAQAnswer faqAnswer = new FAQAnswer();
                            faqAnswer.setAnswerId(answerId);
                            UserLogin answeredUser = new UserLogin();
                            answeredUser.setUserName(resultSet.getString("user_name"));
                            faqAnswer.setUser(answeredUser);
                            faqAnswer.setAnswer(resultSet.getString("answer"));
                            faqAnswer.setDate(resultSet.getDate("answered_date"));
                            faqAnswer.setTime(resultSet.getTime("answered_time"));
                            faqAnswers.add(faqAnswer);
                        }
                    }else{
                        faqQuestion = new FAQQuestion();
                        faqQuestions.add(faqQuestion);

                        currentQuestionID = resultSet.getString("question_id");
                        faqQuestion.setQuestionId(currentQuestionID);
                        faqQuestion.setQuestion(resultSet.getString("question"));
                        faqQuestion.setDate(resultSet.getDate("questioned_date"));
                        faqQuestion.setTime(resultSet.getTime("questioned_time"));
                        userLogin = new UserLogin();
                        userLogin.setUserName(resultSet.getString("user_name"));
                        faqQuestion.setUser(userLogin);
                        faqAnswers = new ArrayList<FAQAnswer>();
                        faqQuestion.setFaqAnswers(faqAnswers);

                        answerId = resultSet.getString("answer_id");
                        if(answerId!=null && !answerId.equals("")){
                            FAQAnswer faqAnswer = new FAQAnswer();
                            faqAnswer.setAnswerId(answerId);
                            UserLogin answeredUser = new UserLogin();
                            answeredUser.setUserName(resultSet.getString("user_name"));
                            faqAnswer.setUser(answeredUser);
                            faqAnswer.setAnswer(resultSet.getString("answer"));
                            faqAnswer.setDate(resultSet.getDate("answered_date"));
                            faqAnswer.setTime(resultSet.getTime("answered_time"));
                            faqAnswers.add(faqAnswer);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<FAQQuestion> faqQuestionsTemp=new ArrayList<FAQQuestion>();
        if (limit > 0 && offset >= 0) {
            for (int i=offset;i<offset+limit;i++){
                if(i<faqQuestions.size()){
                    faqQuestionsTemp.add(faqQuestions.get(i));
                }
            }
        }
        return faqQuestionsTemp;
    }


    public String createQuestion(UserLogin userLogin,String question){
        String sqlGetNextNumber = "SELECT nextval('" + APPStatics.schemaName + ".\"questionNumberGeneraator\"')";
        String sqlCreateQuestion = "INSERT INTO ars.\"FAQ_QUESTION\"(question_id,user_name,question,questioned_date,questioned_time) VALUES(?,?,?,?,?)";

        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        try {
            connection.setAutoCommit(false);

            PreparedStatement psGetNextVal =  connection.prepareStatement(sqlGetNextNumber);
            ResultSet resultSet1 = psGetNextVal.executeQuery();
            resultSet1.next();
            String nextValue = resultSet1.getString("nextval");

            PreparedStatement psInsertInto = connection.prepareStatement(sqlCreateQuestion);
            psInsertInto.setString(1,"Q"+String.format("%06d", Integer.parseInt(nextValue)));
            psInsertInto.setString(2,userLogin.getUserName());
            psInsertInto.setString(3,question);
            psInsertInto.setDate(4, new java.sql.Date(new Date().getTime()));
            psInsertInto.setTime(5, new Time(new Date().getTime()));
            psInsertInto.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            if(e.getSQLState().equalsIgnoreCase(APPStatics.DBErrorCodes.UNIQUE_KEY_VIOLATION)){
                return "Please submit the question again";
            }else {
                return e.getSQLState();
            }

        } catch (Exception e){

        }
        finally {
            try {
                connection.commit();
                connection.setAutoCommit(true);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    public String createAnswer(String questionID, String answer, UserLogin loggedUser) {
        String sqlGetNextNumber = "SELECT nextval('" + APPStatics.schemaName + ".\"answerNumberGeneraator\"')";
        String sqlCreateAnswer = "INSERT INTO ars.\"FAQ_ANSWER\"(answer_id,user_name,question_id,answered_date,answered_time,answer) VALUES(?,?,?,?,?,?)";

        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        try {
            connection.setAutoCommit(false);

            PreparedStatement psGetNextVal =  connection.prepareStatement(sqlGetNextNumber);
            ResultSet resultSet1 = psGetNextVal.executeQuery();
            resultSet1.next();
            String nextValue = resultSet1.getString("nextval");

            PreparedStatement psInsertInto = connection.prepareStatement(sqlCreateAnswer);
            psInsertInto.setString(1,"A"+String.format("%06d", Integer.parseInt(nextValue)));
            psInsertInto.setString(2,loggedUser.getUserName());
            psInsertInto.setString(3,questionID);
            psInsertInto.setDate(4, new java.sql.Date(new Date().getTime()));
            psInsertInto.setTime(5, new Time(new Date().getTime()));
            psInsertInto.setString(6, answer);
            psInsertInto.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            if(e.getSQLState().equalsIgnoreCase(APPStatics.DBErrorCodes.UNIQUE_KEY_VIOLATION)){
                return "Please submit the answer again";
            }else {
                return e.getSQLState();
            }

        } catch (Exception e){

        }
        finally {
            try {
                connection.commit();
                connection.setAutoCommit(true);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    public int getFAQCount(String searchText) throws Exception {
        int count = 0;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(question_id) AS c FROM " + APPStatics.schemaName + ".\"FAQ_QUESTION\" q");
        if(searchText != null && !"".equals(searchText)){
            sql.append(" WHERE LOWER(question) LIKE '%" + searchText.toLowerCase() +"%'" +
                        " OR EXISTS (SELECT * FROM " + APPStatics.schemaName + ".\"FAQ_ANSWER\" a WHERE q.question_id = a.question_id AND" +
                                            " LOWER(answer) LIKE '%" + searchText.toLowerCase() +"%') ");
        }
        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql.toString());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            count = resultSet.getInt("c");
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return count;
    }
}
