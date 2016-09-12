package com.ars.service.booking;

import com.ars.dao.booking.BookingDAO;
import com.ars.domain.booking.Booking;
import com.ars.domain.transit.TransitBooking;
import com.ars.domain.user.UserLogin;
import com.ars.util.db.Transaction;

import java.sql.Connection;
import java.util.List;

/**
 * Created by JJ on 9/9/16.
 */
public class BookingService {
    public static List<Booking> getBookingsByUserName(UserLogin userLogin) throws Exception {
        Connection connection = null;
        List<Booking> bookings=null;
        try {
            connection = Transaction.beginTransaction();

            BookingDAO bookingDAO = new BookingDAO(connection);
            bookings = bookingDAO.getAllBookingByUserName(userLogin.getUserName());

        } finally {
            Transaction.endTransaction(connection);
        }
        return bookings;
    }

    public static List<Booking> getBookingsByBookingsNumberAndUserName(String bookingNumber,UserLogin userLogin) throws Exception {
        Connection connection = null;
        List<Booking> bookings=null;
        try {
            connection = Transaction.beginTransaction();

            BookingDAO bookingDAO = new BookingDAO(connection);
            bookings = bookingDAO.getBookingsByIDAndUserName(bookingNumber,userLogin.getUserName());

        } finally {
            Transaction.endTransaction(connection);
        }
        return bookings;
    }
}
