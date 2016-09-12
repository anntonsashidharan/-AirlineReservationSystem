package com.ars.dao.contactPerson;

import com.ars.domain.contactPerson.ContactPerson;
import com.ars.system.APPStatics;
import com.ars.util.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by JJ on 8/29/16.
 */
public class ContactPersonDAO {
    private Connection connection;

    public ContactPersonDAO(Connection connection) {
        this.connection = connection;
    }

    public ContactPerson createContactPerson(ContactPerson contactPerson) throws Exception {
        String fName = contactPerson.getFirstName();
        String lName = contactPerson.getFirstName();
        String oName = contactPerson.getFirstName();
        String email = contactPerson.getEmail();
        String addressLine1 = contactPerson.getAddressLine1();
        String addressLine2 = contactPerson.getAddressLine2();
        String addressLine3 = contactPerson.getAddressLine3();
        String country = contactPerson.getCountry();
        String contactNumber1 = contactPerson.getContactNumber1();
        String contactNumber2 = contactPerson.getContactNumber2();


        String sqlNextContactPersonNumber = "SELECT nextval('" + APPStatics.schemaName + ".\"seqContactPersonNumberGenerator\"')";
        String sqlInsertIntoContactPerson = "INSERT INTO " + APPStatics.schemaName + ".\"CONTACT_PERSON\" " +
                "(contact_person_id,contact_first_name,contact_last_name,contact_other_name,email,address_line1,address_line2,address_line3,country,contact_number1,contact_number2) VALUES(?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement statement1 = connection.prepareStatement(sqlNextContactPersonNumber);
        ResultSet resultSet1 = statement1.executeQuery();
        resultSet1.next();
        int nextContactPersonNumber = resultSet1.getInt("nextval");


        PreparedStatement statement2 = connection.prepareStatement(sqlInsertIntoContactPerson);
        String contactPersonID = "CON" + String.format("%05d", nextContactPersonNumber);
        contactPerson.setContactPersonID(contactPersonID);

        statement2.setString(1, contactPersonID);
        statement2.setString(2, fName);
        statement2.setString(3, lName);
        statement2.setString(4, oName);
        statement2.setString(5, email);
        statement2.setString(6, addressLine1);
        statement2.setString(7, addressLine2);
        statement2.setString(8, addressLine3);
        statement2.setString(9, country);
        statement2.setString(10, contactNumber1);
        statement2.setString(11, contactNumber2);
        statement2.execute();


        return contactPerson;
    }


    public ContactPerson getContactPersonByID(String contactPersonID) throws Exception {

        String sqlSelectContactPerson = "SELECT * FROM " + APPStatics.schemaName + ".\"CONTACT_PERSON\" " +
                "WHERE contact_person_id = ?";

        PreparedStatement statement2 = connection.prepareStatement(sqlSelectContactPerson);

        statement2.setString(1, contactPersonID);
        ResultSet resultSet = statement2.executeQuery();

        ContactPerson contactPerson = new ContactPerson();

        if (resultSet.next()) {
            contactPerson.setContactPersonID(resultSet.getString("contact_person_id"));
            contactPerson.setFirstName(resultSet.getString("contact_first_name"));
            contactPerson.setLastName(resultSet.getString("contact_last_name"));
            contactPerson.setOtherName(resultSet.getString("contact_other_name"));
            contactPerson.setEmail(resultSet.getString("email"));
            contactPerson.setAddressLine1(resultSet.getString("address_line1"));
            contactPerson.setAddressLine2(resultSet.getString("address_line2"));
            contactPerson.setAddressLine3(resultSet.getString("address_line3"));
            contactPerson.setCountry(resultSet.getString("country"));
            contactPerson.setContactNumber1(resultSet.getString("contact_number1"));
            contactPerson.setContactNumber2(resultSet.getString("contact_number2"));
        }


        return contactPerson;
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
