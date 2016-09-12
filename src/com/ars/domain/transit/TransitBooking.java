package com.ars.domain.transit;

import com.ars.domain.booking.Booking;
import com.ars.domain.schedule.Schedule;
import com.ars.domain.ticket.Ticket;

import java.util.List;

/**
 * Created by JJ on 9/7/16.
 */
public class TransitBooking {

    private Booking booking;
    private List<ScheduleTickets> scheduleTicketsListUpTrips;
    private List<ScheduleTickets> scheduleTicketsListDownTrips;

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }


    public List<ScheduleTickets> getScheduleTicketsListUpTrips() {
        return scheduleTicketsListUpTrips;
    }

    public void setScheduleTicketsListUpTrips(List<ScheduleTickets> scheduleTicketsListUpTrips) {
        this.scheduleTicketsListUpTrips = scheduleTicketsListUpTrips;
    }

    public List<ScheduleTickets> getScheduleTicketsListDownTrips() {
        return scheduleTicketsListDownTrips;
    }

    public void setScheduleTicketsListDownTrips(List<ScheduleTickets> scheduleTicketsListDownTrips) {
        this.scheduleTicketsListDownTrips = scheduleTicketsListDownTrips;
    }
}
