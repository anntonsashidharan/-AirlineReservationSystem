package com.ars.dao.airport;

import com.ars.domain.airport.Airport;
import com.ars.domain.user.Employee;
import com.ars.system.APPStatics;
import com.ars.util.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JJ on 4/23/16.
 */
public class AirportDAO {
    private Connection connection;

    public AirportDAO(Connection connection) {
        this.connection = connection;
    }

    public Airport getAirportByCode(String airportCode) throws Exception{


        String sql = "SELECT * FROM " + APPStatics.schemaName + ".\"AIRPORT\" WHERE airport_code = ? ";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, airportCode);
        ResultSet resultSet = statement.executeQuery();

        Airport airport = new Airport();
        if (resultSet.next()) {
            airport.setCode(airportCode);
            airport.setCity(resultSet.getString("city"));
            airport.setName(resultSet.getString("airport_name"));
            Employee employee = new Employee();
            employee.setId(resultSet.getString("employee_id"));
            //airport.setEmployee(employee);
        }else {
            throw new Exception("No airline found with given ID");
        }
        return airport;
    }

    public List<Airport> getAirportList(String airportCode) {
        List<Airport> airports = new ArrayList<Airport>();
        Airport airport = null;
        String sql = "SELECT * FROM " + APPStatics.schemaName + ".\"AIRPORT\" WHERE airport_code LIKE ? ";
        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, airportCode);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                airport = new Airport();
                airport.setCode(resultSet.getString("airport_code"));
                airport.setCity(resultSet.getString("city"));
                airport.setName(resultSet.getString("airport_name"));
                airports.add(airport);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return airports;
    }

}
