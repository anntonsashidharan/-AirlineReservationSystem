package com.ars.dao.schedule;

import com.ars.domain.airport.Airport;
import com.ars.domain.flight.Flight;
import com.ars.domain.schedule.Schedule;
import com.ars.system.APPStatics;
import com.ars.util.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by JJ on 4/24/16.
 */
public class ScheduleDAO {
    private Connection connection;

    public ScheduleDAO(Connection connection) {
        this.connection = connection;
    }

    public Schedule createSchedule(Schedule schedule) throws SQLException {

        String sqlNextScheduleNumber = "SELECT nextval('" + APPStatics.schemaName + ".\"seqScheduleNumberGenerrator\"')";
        String sqlSelect = "SELECT * FROM " + APPStatics.schemaName + ".\"SCHEDULE\" " +
                "WHERE schedule_id_airline=? AND airline=?";
        String sqlInsertIntoSchedule = "INSERT INTO " + APPStatics.schemaName + ".\"SCHEDULE\" " +
                "(departure_date,departure_time,arrival_date,arrival_time,aircraft_number,flight_number," +
                "schedule_id_airline,from_airport,to_airport,airline,schedule_number_ars) VALUES(?,?,?,?,?,?,?,?,?,?,?)";


        //test for existence of record for same schedule
        PreparedStatement statement = connection.prepareStatement(sqlSelect);
        statement.setString(1, schedule.getScheduleId());
        statement.setString(2, schedule.getAirlineCompany());
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            schedule.setScheduleNumberARS(resultSet.getInt("schedule_number_ars"));
        } else {

            PreparedStatement statement1 = connection.prepareStatement(sqlNextScheduleNumber);
            ResultSet resultSet1 = statement1.executeQuery();
            resultSet1.next();
            int nextScheduleNumber = resultSet1.getInt("nextval");
            schedule.setScheduleNumberARS(nextScheduleNumber);

            PreparedStatement statement2 = connection.prepareStatement(sqlInsertIntoSchedule);
            statement2.setDate(1, new java.sql.Date(schedule.getDepartDate().getTime()));
            statement2.setTime(2, new Time(schedule.getDepartTime().getTime()));
            statement2.setDate(3, new java.sql.Date(schedule.getArrivalDate().getTime()));
            statement2.setTime(4, new Time(schedule.getArrivalTime().getTime()));
            statement2.setString(5, schedule.getAircraftNumber());
            statement2.setString(6, schedule.getFlight().getFlightNumber());
            statement2.setString(7, schedule.getScheduleId());  //Schedule ID assigned by airline company
            statement2.setString(8, schedule.getFlight().getFromAirport().getCode());
            statement2.setString(9, schedule.getFlight().getToAirport().getCode());
            statement2.setString(10, schedule.getAirlineCompany());
            statement2.setInt(11, schedule.getScheduleNumberARS());
            statement2.execute();

        }

        return schedule;
    }


    public Schedule getScheduleByID(int scheduleIDARS) throws Exception {


        String sqlSelectSchedule = "SELECT * FROM " + APPStatics.schemaName + ".\"SCHEDULE\" " +
                "WHERE schedule_number_ars = ?";


        PreparedStatement statement2 = connection.prepareStatement(sqlSelectSchedule);
        statement2.setInt(1, scheduleIDARS);
        ResultSet resultSet = statement2.executeQuery();

        Schedule schedule = new Schedule();

        if (resultSet.next()) {
            schedule.setDepartDate(resultSet.getDate("departure_date"));
            schedule.setDepartTime(resultSet.getTime("departure_time"));
            schedule.setArrivalDate(resultSet.getDate("arrival_date"));
            schedule.setArrivalTime(resultSet.getTime("arrival_time"));
            schedule.setAircraftNumber(resultSet.getString("aircraft_number"));

            Flight flight = new Flight();
            flight.setFlightNumber(resultSet.getString("flight_number"));
            schedule.setFlight(flight);

            schedule.setScheduleId(resultSet.getString("schedule_id_airline"));

            Airport from = new Airport();
            from.setCode(resultSet.getString("from_airport"));
            schedule.setFrom(from);

            Airport to = new Airport();
            to.setCode(resultSet.getString("to_airport"));
            schedule.setTo(to);

            schedule.setAirlineCompany(resultSet.getString("airline"));
            schedule.setScheduleNumberARS(resultSet.getInt("schedule_number_ars"));


        } else {
            throw new Exception("No schedule found with given ID");
        }
        return schedule;
    }


}

