package com.ars.dao.passenger;

import com.ars.util.enums.Gender;
import com.ars.util.enums.PassengerType;
import com.ars.domain.passenger.Passenger;
import com.ars.system.APPStatics;
import com.ars.util.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by JJ on 8/28/16.
 */
public class PassengerDAO {
    private Connection connection;

    public PassengerDAO(Connection connection) {
        this.connection = connection;
    }

    public Passenger createPassenger(Passenger passenger) throws Exception {



        String sqlNextPassengerNumber = "SELECT nextval('" + APPStatics.schemaName + ".\"seqPassengerNumberGenerator\"')";
        String sqlInsertIntoPassenger = "INSERT INTO " + APPStatics.schemaName + ".\"PASSENGER\" " +
                "(passenger_id,first_name,last_name,other_name,passport,date_of_birth,type,gender) VALUES(?,?,?,?,?,?,?,?)";


        PreparedStatement statement1 = connection.prepareStatement(sqlNextPassengerNumber);
        ResultSet resultSet1 = statement1.executeQuery();
        resultSet1.next();
        int nextPassengerNumber = resultSet1.getInt("nextval");
        String passengerID = "PSG" + String.format("%06d", nextPassengerNumber);
        passenger.setPassengerID(passengerID);


        PreparedStatement statement2 = connection.prepareStatement(sqlInsertIntoPassenger);
        statement2.setString(1, passengerID);
        statement2.setString(2, passenger.getFirstName());
        statement2.setString(3, passenger.getLastName());
        statement2.setString(4, passenger.getOtherName());
        statement2.setString(5, passenger.getPassportNumber());
        statement2.setDate(6, new java.sql.Date(passenger.getDateOfBirth().getTime()));
        statement2.setString(7, passenger.getPassengerType().toString());
        statement2.setString(8, passenger.getGender().toString());
        statement2.execute();

        return passenger;


    }


    public Passenger getPassengerByID(String passengerID) throws Exception {
        String sqlGetPassenger = "SELECT * FROM " + APPStatics.schemaName + ".\"PASSENGER\" " +
                "WHERE passenger_id = ?";


        PreparedStatement statement2 = connection.prepareStatement(sqlGetPassenger);
        statement2.setString(1, passengerID);
        ResultSet resultSet = statement2.executeQuery();
        Passenger passenger = new Passenger();

        if (resultSet.next()){
            passenger.setPassengerID(resultSet.getString("passenger_id"));
            passenger.setFirstName(resultSet.getString("first_name"));
            passenger.setLastName(resultSet.getString("last_name"));
            passenger.setOtherName(resultSet.getString("other_name"));
            passenger.setPassportNumber(resultSet.getString("passport"));
            passenger.setDateOfBirth(resultSet.getDate("date_of_birth"));
            passenger.setDateOfBirth(resultSet.getDate("date_of_birth"));
            passenger.setPassengerType(PassengerType.valueOf(resultSet.getString("type")));
            passenger.setGender(Gender.valueOf(resultSet.getString("gender")));
        }else{
            throw new Exception("No passenger with given passenger ID");
        }

        return passenger;
    }

/*
    public void updatePassenger(String passengerID, Passenger passenger) throws Exception {
        String fName = passenger.getFirstName();
        String lName = passenger.getFirstName();
        String oName = passenger.getFirstName();
        String passport = passenger.getPassportNumber();
        Gender gender = passenger.getGender();
        PassengerType passengerType = passenger.getPassengerType();
        Date dob = passenger.getDateOfBirth();

        String sqlUpdatePassenger = "UPDATE " + APPStatics.schemaName + ".\"PASSENGER\" SET first_name=?,last_name=?,other_name=?,passport=?,date_of_birth=?,type=?,gender=? WHERE passenger_id=?";

        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        try {
            connection.setAutoCommit(false);    //to process as transaction
            PreparedStatement statement1 = connection.prepareStatement(sqlUpdatePassenger);
            ResultSet resultSet1 = statement1.executeQuery();
            statement1.setString(1, fName);
            statement1.setString(2, lName);
            statement1.setString(3, oName);
            statement1.setString(4, passport);
            statement1.setDate(5, new java.sql.Date(dob.getTime()));
            statement1.setString(6, passengerType.toString());
            statement1.setString(7, gender.toString());
            statement1.setString(8, passengerID);
            int result = statement1.executeUpdate();
            if (result == 0) {
                throw new Exception("Passenger not found");
            }
            connection.commit();


        } catch (SQLException e) {
            connection.rollback();
            throw new Exception(e.getSQLState());
        } finally {
            try {
                connection.commit();
                connection.setAutoCommit(true);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void deletePassenger(String passengerID) throws Exception {
        String sqlDeletePassenger = "DELETE FROM " + APPStatics.schemaName + ".\"PASSENGER\" WHERE passenger_id = ?";

        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        try {
            connection.setAutoCommit(false);    //to process as transaction
            PreparedStatement statement1 = connection.prepareStatement(sqlDeletePassenger);
            ResultSet resultSet1 = statement1.executeQuery();
            statement1.setString(1, passengerID);
            int result = statement1.executeUpdate();
            if (result == 0) {
                throw new Exception("Passenger not found");
            }
            connection.commit();


        } catch (SQLException e) {
            connection.rollback();
            throw new Exception(e.getSQLState());
        } finally {
            try {
                connection.commit();
                connection.setAutoCommit(true);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
    */
}

