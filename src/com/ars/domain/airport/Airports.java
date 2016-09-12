package com.ars.domain.airport;

import com.ars.service.airport.AirportService;

import java.util.HashMap;
import java.util.List;

/**
 * Created by JJ on 9/10/16.
 */
public class Airports {
    private HashMap<String,String> airports;

    public HashMap<String, String> getAirports() {
        return airports;
    }

    public void setAirports(HashMap<String, String> airports) {
        this.airports = airports;
    }



    private static Airports airportsSigleton = null;

    private Airports() throws Exception{
        airports = AirportService.getAirportListAsHasMap("%");
    }

    public static Airports getAirportsInstance() throws Exception {
        if(airportsSigleton==null || airportsSigleton.airports==null || airportsSigleton.airports.size()==0){
            airportsSigleton = new Airports();
        }
        return airportsSigleton;
    }
}
