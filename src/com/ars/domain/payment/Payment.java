package com.ars.domain.payment;

import java.util.Date;

/**
 * Created by JJ on 4/13/16.
 */
public class Payment {
    private String paymentID;
    private String paypalUserName;
    private float amount;
    private Date payedDate;
    private Date payedTime;

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public String getPaypalUserName() {
        return paypalUserName;
    }

    public void setPaypalUserName(String paypalUserName) {
        this.paypalUserName = paypalUserName;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getPayedDate() {
        return payedDate;
    }

    public void setPayedDate(Date payedDate) {
        this.payedDate = payedDate;
    }

    public Date getPayedTime() {
        return payedTime;
    }

    public void setPayedTime(Date payedTime) {
        this.payedTime = payedTime;
    }
}
