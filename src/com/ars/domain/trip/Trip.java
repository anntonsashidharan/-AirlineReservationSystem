package com.ars.domain.trip;

import com.ars.domain.airport.Airport;
import com.ars.vo.transit.Transit;

import java.util.List;

/**
 * Created by JJ on 5/15/16.
 */
public class Trip {
    private Airport from;
    private Airport to;
    private List<Transit> transits;
    private float totalCost;
    //to maintain across session in booking process
    private int tripID;

    public Airport getFrom() {
        return from;
    }

    public void setFrom(Airport from) {
        this.from = from;
    }

    public Airport getTo() {
        return to;
    }

    public void setTo(Airport to) {
        this.to = to;
    }

    public List<Transit> getTransits() {
        return transits;
    }

    public void setTransits(List<Transit> transits) {
        this.transits = transits;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public int getTripID() {
        return tripID;
    }

    public void setTripID(int tripID) {
        this.tripID = tripID;
    }
}
