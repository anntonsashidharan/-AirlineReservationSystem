package com.ars.domain.fair;
import com.ars.domain.schedule.Schedule;

/**
 * Created by JJ on 4/13/16.
 */
public class Fair {
    private Schedule schedule;
    private String travelClass;
    private float adultSeatPrice;
    private float childSeatPrice;
    private float infantCost;

    public float getInfantCost() {
        return infantCost;
    }

    public void setInfantCost(float infantCost) {
        this.infantCost = infantCost;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public String getTravelClass() {
        return travelClass;
    }

    public void setTravelClass(String travelClass) {
        this.travelClass = travelClass;
    }

    public float getAdultSeatPrice() {
        return adultSeatPrice;
    }

    public void setAdultSeatPrice(float adultSeatPrice) {
        this.adultSeatPrice = adultSeatPrice;
    }

    public float getChildSeatPrice() {
        return childSeatPrice;
    }

    public void setChildSeatPrice(float childSeatPrice) {
        this.childSeatPrice = childSeatPrice;
    }
}
