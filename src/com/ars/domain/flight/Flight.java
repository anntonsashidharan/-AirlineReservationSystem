package com.ars.domain.flight;


import com.ars.domain.airport.Airport;

/**
 * Created by JJ on 4/13/16.
 */
public class Flight {
    private String flightNumber;
    private Airport fromAirport;
    private Airport toAirport;

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Airport getFromAirport() {
        return fromAirport;
    }

    public void setFromAirport(Airport fromAirport) {
        this.fromAirport = fromAirport;
    }

    public Airport getToAirport() {
        return toAirport;
    }

    public void setToAirport(Airport toAirport) {
        this.toAirport = toAirport;
    }
}
