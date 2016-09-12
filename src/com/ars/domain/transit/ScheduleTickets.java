package com.ars.domain.transit;

import com.ars.domain.schedule.Schedule;
import com.ars.domain.ticket.Ticket;
import com.ars.util.enums.TripDirection;

import java.util.List;

/**
 * Created by JJ on 9/7/16.
 */
public class ScheduleTickets {
    private int transitNumber;
    private TripDirection tripDirection;
    private Schedule schedule;
    private List<Ticket> adultTickets;
    private List<Ticket> childTickets;
    private List<Ticket> infantTickets;

    public TripDirection getTripDirection() {
        return tripDirection;
    }

    public void setTripDirection(TripDirection tripDirection) {
        this.tripDirection = tripDirection;
    }

    public int getTransitNumber() {
        return transitNumber;
    }

    public void setTransitNumber(int transitNumber) {
        this.transitNumber = transitNumber;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public List<Ticket> getAdultTickets() {
        return adultTickets;
    }

    public void setAdultTickets(List<Ticket> adultTickets) {
        this.adultTickets = adultTickets;
    }

    public List<Ticket> getChildTickets() {
        return childTickets;
    }

    public void setChildTickets(List<Ticket> childTickets) {
        this.childTickets = childTickets;
    }

    public List<Ticket> getInfantTickets() {
        return infantTickets;
    }

    public void setInfantTickets(List<Ticket> infantTickets) {
        this.infantTickets = infantTickets;
    }
}
