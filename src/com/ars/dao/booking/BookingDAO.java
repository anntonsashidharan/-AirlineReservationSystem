package com.ars.dao.booking;

import com.airline.webservice.TravelClass;
import com.ars.domain.airport.Airport;
import com.ars.domain.booking.Booking;
import com.ars.domain.contactPerson.ContactPerson;
import com.ars.domain.payment.Payment;
import com.ars.domain.user.UserLogin;
import com.ars.system.APPStatics;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Created by JJ on 9/6/16.
 */
public class BookingDAO {
    private Connection connection;

    public BookingDAO(Connection connection) {
        this.connection = connection;
    }

    public Booking createBooking(Booking booking) throws Exception {
        String sqlNextBookingNumber = "SELECT nextval('" + APPStatics.schemaName + ".\"seqBookingNumberGenerator\"')";
        String sqlInsertIntoBooking = "INSERT INTO " + APPStatics.schemaName + ".\"BOOKING\" " +
                "(booking_number,date,time,user_name,payment_id,contact_person_id,travel_class,from_airport,to_airport) VALUES(?,?,?,?,?,?,?,?,?)";

        PreparedStatement statement1 = connection.prepareStatement(sqlNextBookingNumber);
        ResultSet resultSet1 = statement1.executeQuery();
        resultSet1.next();
        int nextBookingNumber = resultSet1.getInt("nextval");


        PreparedStatement statement2 = connection.prepareStatement(sqlInsertIntoBooking);
        String bookingID = "BKN" + String.format("%06d", nextBookingNumber);
        booking.setBookingNumber(bookingID);

        statement2.setString(1, booking.getBookingNumber());
        java.util.Date date = new java.util.Date();
        booking.setBookingDate(date);
        booking.setBookingTime(date);
        statement2.setDate(2, new Date(date.getTime()));
        statement2.setTime(3, new Time(date.getTime()));
        statement2.setString(4, booking.getUserLogin().getUserName());
        statement2.setString(5, booking.getPayment().getPaymentID());
        statement2.setString(6, booking.getContactPerson().getContactPersonID());
        statement2.setString(7, booking.getTravelClass().toString());
        statement2.setString(8, booking.getFromAirport().getCode());
        statement2.setString(9, booking.getToAirport().getCode());
        statement2.execute();


        return booking;
    }


    public Booking getBookingByID(String bookingID) throws Exception {
        String sqlGetBooking = "SELECT * FROM " + APPStatics.schemaName + ".\"BOOKING\" " +
                "WHERE booking_number = ?";

        PreparedStatement statement2 = connection.prepareStatement(sqlGetBooking);
        statement2.setString(1, bookingID);
        ResultSet resultSet = statement2.executeQuery();
        Booking booking = new Booking();
        if (resultSet.next()) {
            booking.setBookingNumber(resultSet.getString("booking_number"));
            booking.setBookingDate(resultSet.getDate("date"));
            booking.setBookingTime(resultSet.getTime("time"));

            UserLogin userLogin = new UserLogin();
            userLogin.setUserName(resultSet.getString("user_name"));
            booking.setUserLogin(userLogin);

            Payment payment = new Payment();
            payment.setPaymentID(resultSet.getString("payment_id"));
            booking.setPayment(payment);

            ContactPerson contactPerson = new ContactPerson();
            contactPerson.setContactPersonID(resultSet.getString("contact_person_id"));
            booking.setContactPerson(contactPerson);

            booking.setTravelClass(TravelClass.valueOf(resultSet.getString("travel_class")));

            Airport from = new Airport();
            from.setCode(resultSet.getString("from_airport"));
            booking.setFromAirport(from);

            Airport to = new Airport();
            to.setCode(resultSet.getString("to_airport"));
            booking.setToAirport(to);
        } else {
            throw new Exception("No Booking Found with  given ID");
        }

        return booking;
    }


    public List<Booking> getAllBookingByUserName(String userName) throws Exception{
        List<Booking> bookings = new ArrayList<Booking>();

        String sqlGetBookings = "SELECT * FROM " + APPStatics.schemaName + ".\"BOOKING\" " +
                "WHERE user_name = ?";

        PreparedStatement statement2 = connection.prepareStatement(sqlGetBookings);
        statement2.setString(1, userName);
        ResultSet resultSet = statement2.executeQuery();
        Booking booking = null;
        while (resultSet.next()) {
            booking = new Booking();
            booking.setBookingNumber(resultSet.getString("booking_number"));
            booking.setBookingDate(resultSet.getDate("date"));
            booking.setBookingTime(resultSet.getTime("time"));

            UserLogin userLogin = new UserLogin();
            userLogin.setUserName(resultSet.getString("user_name"));
            booking.setUserLogin(userLogin);

            Payment payment = new Payment();
            payment.setPaymentID(resultSet.getString("payment_id"));
            booking.setPayment(payment);

            ContactPerson contactPerson = new ContactPerson();
            contactPerson.setContactPersonID(resultSet.getString("contact_person_id"));
            booking.setContactPerson(contactPerson);

            booking.setTravelClass(TravelClass.valueOf(resultSet.getString("travel_class")));

            Airport from = new Airport();
            from.setCode(resultSet.getString("from_airport"));
            booking.setFromAirport(from);

            Airport to = new Airport();
            to.setCode(resultSet.getString("to_airport"));
            booking.setToAirport(to);
            bookings.add(booking);
        }

        if(bookings.size()==0){
            throw new Exception("No Booking Made By User : " + userName);
        }


        return bookings;
    }



    public Booking getBookingByIDAndUserName(String bookingID, String userName) throws Exception {
        String sqlGetBooking = "SELECT * FROM " + APPStatics.schemaName + ".\"BOOKING\" " +
                "WHERE booking_number = ? " +
                "AND user_name = ?";

        PreparedStatement statement2 = connection.prepareStatement(sqlGetBooking);
        statement2.setString(1, bookingID);
        statement2.setString(2, userName);
        ResultSet resultSet = statement2.executeQuery();
        Booking booking = new Booking();
        if (resultSet.next()) {
            booking.setBookingNumber(resultSet.getString("booking_number"));
            booking.setBookingDate(resultSet.getDate("date"));
            booking.setBookingTime(resultSet.getTime("time"));

            UserLogin userLogin = new UserLogin();
            userLogin.setUserName(resultSet.getString("user_name"));
            booking.setUserLogin(userLogin);

            Payment payment = new Payment();
            payment.setPaymentID(resultSet.getString("payment_id"));
            booking.setPayment(payment);

            ContactPerson contactPerson = new ContactPerson();
            contactPerson.setContactPersonID(resultSet.getString("contact_person_id"));
            booking.setContactPerson(contactPerson);

            booking.setTravelClass(TravelClass.valueOf(resultSet.getString("travel_class")));

            Airport from = new Airport();
            from.setCode(resultSet.getString("from_airport"));
            booking.setFromAirport(from);

            Airport to = new Airport();
            to.setCode(resultSet.getString("to_airport"));
            booking.setToAirport(to);
        } else {
            throw new Exception("This Booking doesn't belongs to : " + userName);
        }

        return booking;
    }


    public List<Booking> getBookingsByIDAndUserName(String bookingID, String userName) throws Exception {
        String sqlGetBookings = "SELECT * FROM " + APPStatics.schemaName + ".\"BOOKING\" " +
                "WHERE booking_number LIKE '%"+ bookingID +"%'" +
                "AND user_name = ?";

        PreparedStatement statement2 = connection.prepareStatement(sqlGetBookings);
        statement2.setString(1, userName);
        ResultSet resultSet = statement2.executeQuery();
        Booking booking = new Booking();
        List<Booking> bookings = new ArrayList<Booking>();
        while (resultSet.next()) {
            booking = new Booking();
            booking.setBookingNumber(resultSet.getString("booking_number"));
            booking.setBookingDate(resultSet.getDate("date"));
            booking.setBookingTime(resultSet.getTime("time"));

            UserLogin userLogin = new UserLogin();
            userLogin.setUserName(resultSet.getString("user_name"));
            booking.setUserLogin(userLogin);

            Payment payment = new Payment();
            payment.setPaymentID(resultSet.getString("payment_id"));
            booking.setPayment(payment);

            ContactPerson contactPerson = new ContactPerson();
            contactPerson.setContactPersonID(resultSet.getString("contact_person_id"));
            booking.setContactPerson(contactPerson);

            booking.setTravelClass(TravelClass.valueOf(resultSet.getString("travel_class")));

            Airport from = new Airport();
            from.setCode(resultSet.getString("from_airport"));
            booking.setFromAirport(from);

            Airport to = new Airport();
            to.setCode(resultSet.getString("to_airport"));
            booking.setToAirport(to);
            bookings.add(booking);
        }

        if (bookings.size()==0){
            throw new Exception("No booking available matching ur search : " + userName);
        }

        return bookings;
    }



}
