package com.ars.domain.schedule;


import com.ars.domain.airport.Airport;
import com.ars.domain.fair.Fair;
import com.ars.domain.flight.Flight;

import java.util.Date;
import java.util.List;

/**
 * Created by JJ on 4/13/16.
 */
public class Schedule {
    private String airlineCompany;
    private String scheduleId;
    private Date departDate;
    private Date departTime;
    private Date arrivalTime;
    private Date arrivalDate;
    private Flight flight;
    private Airport from;
    private Airport to;
    private String aircraftNumber;
    private int scheduleNumberARS;
    List<Fair> fairs;



    public List<Fair> getFairs() {
        return fairs;
    }

    public void setFairs(List<Fair> fairs) {
        this.fairs = fairs;
    }

    public int getScheduleNumberARS() {
        return scheduleNumberARS;
    }

    public void setScheduleNumberARS(int scheduleNumberARS) {
        this.scheduleNumberARS = scheduleNumberARS;
    }

    public String getAircraftNumber() {
        return aircraftNumber;
    }

    public void setAircraftNumber(String aircraftNumber) {
        this.aircraftNumber = aircraftNumber;
    }

    public String getAirlineCompany() {
        return airlineCompany;
    }

    public void setAirlineCompany(String airlineCompany) {
        this.airlineCompany = airlineCompany;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Date getDepartDate() {
        return departDate;
    }

    public void setDepartDate(Date departDate) {
        this.departDate = departDate;
    }

    public Date getDepartTime() {
        return departTime;
    }

    public void setDepartTime(Date departTime) {
        this.departTime = departTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

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

}
