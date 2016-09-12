package com.ars.domain.cancellation;

import com.ars.domain.ticket.Ticket;
import com.ars.domain.user.UserLogin;

import java.util.Date;
import java.util.List;

/**
 * Created by JJ on 9/10/16.
 */
public class Cancellation {
    private String cancellationID;
    private List<Ticket> tickets;
    private Date cancellationRequestDate;
    private Date cancellationRequestTime;
    private Date cancellationApprovedDate;
    private Date cancellationApprovedTime;
    private UserLogin approvedBy;
    private float approvedRefund;

    public String getCancellationID() {
        return cancellationID;
    }

    public void setCancellationID(String cancellationID) {
        this.cancellationID = cancellationID;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Date getCancellationRequestDate() {
        return cancellationRequestDate;
    }

    public void setCancellationRequestDate(Date cancellationRequestDate) {
        this.cancellationRequestDate = cancellationRequestDate;
    }

    public Date getCancellationRequestTime() {
        return cancellationRequestTime;
    }

    public void setCancellationRequestTime(Date cancellationRequestTime) {
        this.cancellationRequestTime = cancellationRequestTime;
    }

    public Date getCancellationApprovedDate() {
        return cancellationApprovedDate;
    }

    public void setCancellationApprovedDate(Date cancellationApprovedDate) {
        this.cancellationApprovedDate = cancellationApprovedDate;
    }

    public Date getCancellationApprovedTime() {
        return cancellationApprovedTime;
    }

    public void setCancellationApprovedTime(Date cancellationApprovedTime) {
        this.cancellationApprovedTime = cancellationApprovedTime;
    }

    public UserLogin getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(UserLogin approvedBy) {
        this.approvedBy = approvedBy;
    }

    public float getApprovedRefund() {
        return approvedRefund;
    }

    public void setApprovedRefund(float approvedRefund) {
        this.approvedRefund = approvedRefund;
    }
}
