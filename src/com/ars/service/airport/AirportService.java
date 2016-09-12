package com.ars.service.airport;

import com.ars.dao.airport.AirportDAO;
import com.ars.domain.airport.Airport;
import com.ars.util.db.Transaction;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by JJ on 4/24/16.
 */
public class AirportService {
    public static List<Airport> getAirportList(String airportCode) throws Exception{
        Connection connection = Transaction.beginTransaction();

        AirportDAO airportDAO = new AirportDAO(connection);
        List<Airport> airports = airportDAO.getAirportList(airportCode);

        Transaction.endTransaction(connection);
        return airports;
    }

    public static List<String> getAirportListAsStringList(String airportCode)throws Exception{

        Connection connection = Transaction.beginTransaction();

        AirportDAO airportDAO = new AirportDAO(connection);
        List<Airport> airports = airportDAO.getAirportList(airportCode);
        List<String> strings = new ArrayList<String>();
        for(Airport airport : airports){
            strings.add(airport.getCode());
        }


        Transaction.endTransaction(connection);
        return strings;
    }
    public static HashMap<String,String> getAirportListAsHasMap(String airportCode)throws Exception{
        Connection connection = Transaction.beginTransaction();

        AirportDAO airportDAO = new AirportDAO(connection);
        List<Airport> airports = airportDAO.getAirportList(airportCode);
        HashMap<String,String> stringHashMap = new HashMap<String, String>();
        for(Airport airport : airports){
            stringHashMap.put(airport.getCode(),airport.getCode() + " ("+airport.getName() + " - " + airport.getCity() + ")");
        }
        Transaction.endTransaction(connection);
        return stringHashMap;
    }
}
