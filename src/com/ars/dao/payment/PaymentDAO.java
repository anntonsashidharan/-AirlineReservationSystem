package com.ars.dao.payment;

import com.ars.domain.payment.Payment;
import com.ars.system.APPStatics;

import java.sql.*;

/**
 * Created by JJ on 9/6/16.
 */
public class PaymentDAO {
    private Connection connection;

    public PaymentDAO(Connection connection) {
        this.connection = connection;
    }

    public Payment createPayment(Payment payment) throws Exception {
        String sqlNextPaymentNumber = "SELECT nextval('" + APPStatics.schemaName + ".\"seqPaymentNumberGenerator\"')";
        String sqlInsertIntoPayment = "INSERT INTO " + APPStatics.schemaName + ".\"PAYMENT\" " +
                "(payment_id,paypal_account,payed_amount,payed_date,payed_time) VALUES(?,?,?,?,?)";

        PreparedStatement statement1 = connection.prepareStatement(sqlNextPaymentNumber);
        ResultSet resultSet1 = statement1.executeQuery();
        resultSet1.next();
        int nextPaymentNumber = resultSet1.getInt("nextval");


        PreparedStatement statement2 = connection.prepareStatement(sqlInsertIntoPayment);
        String paymentID = "PAY" + String.format("%06d", nextPaymentNumber);
        payment.setPaymentID(paymentID);

        statement2.setString(1, payment.getPaymentID());
        statement2.setString(2, payment.getPaypalUserName());
        statement2.setFloat(3, payment.getAmount());
        statement2.setDate(4, new Date(payment.getPayedDate().getTime()));
        statement2.setTime(5, new Time(payment.getPayedTime().getTime()));
        statement2.execute();


        return payment;
    }

    public Payment getPaymentByID(String paymentID) throws Exception {
        String sqlSelectPayment = "SELECT * FROM " + APPStatics.schemaName + ".\"PAYMENT\" " +
                "WHERE payment_id = ?";

        PreparedStatement statement2 = connection.prepareStatement(sqlSelectPayment);
        statement2.setString(1, paymentID);
        ResultSet resultSet = statement2.executeQuery();

        Payment payment = new Payment();

        if(resultSet.next()){
            payment.setPaymentID(resultSet.getString("payment_id"));
            payment.setPaypalUserName(resultSet.getString("paypal_account"));
            payment.setAmount(resultSet.getFloat("payed_amount"));
            payment.setPayedDate(resultSet.getDate("payed_date"));
            payment.setPayedTime(resultSet.getTime("payed_time"));
        } else {
            throw new Exception("N payment found with given ID");
        }

        return payment;
    }

}
