package com.ars.domain.ticket;

import com.ars.domain.booking.Booking;
import com.ars.domain.cancellation.Cancellation;
import com.ars.domain.passenger.Passenger;
import com.ars.domain.schedule.Schedule;
import com.ars.util.enums.TicketStatus;

/**
 * Created by JJ on 4/13/16.
 */
public class Ticket {
    private String ticketNumber;
    private TicketStatus status;
    private int seatNumber;
    private Passenger passenger;
    private Booking booking;
    private Schedule schedule;
    private Ticket parentTicket;
    private int tickerNumberARS;
    private Cancellation cancellation;

    public Cancellation getCancellation() {
        return cancellation;
    }

    public void setCancellation(Cancellation cancellation) {
        this.cancellation = cancellation;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Ticket getParentTicket() {
        return parentTicket;
    }

    public void setParentTicket(Ticket parentTicket) {
        this.parentTicket = parentTicket;
    }

    public int getTickerNumberARS() {
        return tickerNumberARS;
    }

    public void setTickerNumberARS(int tickerNumberARS) {
        this.tickerNumberARS = tickerNumberARS;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }


    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }
}
