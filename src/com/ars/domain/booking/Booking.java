package com.ars.domain.booking;

import com.airline.webservice.TravelClass;
import com.ars.domain.airport.Airport;
import com.ars.domain.contactPerson.ContactPerson;
import com.ars.domain.passenger.Passenger;
import com.ars.domain.payment.Payment;
import com.ars.domain.ticket.Ticket;
import com.ars.domain.user.UserLogin;

import java.util.Date;
import java.util.List;

/**
 * Created by JJ on 8/31/16.
 */
public class Booking {
    private String bookingNumber;
    private Date bookingDate;
    private Date bookingTime;
    private UserLogin userLogin;
    private Payment payment;
    private ContactPerson contactPerson;
    private TravelClass travelClass;
    private Airport fromAirport;
    private Airport toAirport;


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

    public String getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(String bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Date getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(Date bookingTime) {
        this.bookingTime = bookingTime;
    }

    public UserLogin getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(UserLogin userLogin) {
        this.userLogin = userLogin;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public ContactPerson getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(ContactPerson contactPerson) {
        this.contactPerson = contactPerson;
    }

    public TravelClass getTravelClass() {
        return travelClass;
    }

    public void setTravelClass(TravelClass travelClass) {
        this.travelClass = travelClass;
    }
}
