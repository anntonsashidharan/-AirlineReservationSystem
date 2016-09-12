package com.ars.service.booking;

import com.ars.dao.booking.BookingDAO;
import com.ars.dao.contactPerson.ContactPersonDAO;
import com.ars.dao.passenger.PassengerDAO;
import com.ars.dao.payment.PaymentDAO;
import com.ars.dao.schedule.ScheduleDAO;
import com.ars.dao.ticket.TicketDAO;
import com.ars.dao.transit.TransitBookingDAO;
import com.ars.domain.booking.Booking;
import com.ars.domain.passenger.Passenger;
import com.ars.domain.ticket.Ticket;
import com.ars.domain.transit.ScheduleTickets;
import com.ars.domain.transit.TransitBooking;
import com.ars.util.common.Helper;
import com.ars.util.enums.TripDirection;
import com.ars.vo.transit.Transit;
import com.ars.util.db.Transaction;

import java.sql.Connection;
import java.util.List;

/**
 * Created by JJ on 8/31/16.
 */
public class ReservationService {

    public static void makeReservation(TransitBooking transitBooking) throws Exception {
        Connection connection = null;
        try {
            connection = Transaction.beginTransaction();

            TicketDAO ticketDAO = new TicketDAO(connection);
            PassengerDAO passengerDAO = new PassengerDAO(connection);
            PaymentDAO paymentDAO = new PaymentDAO(connection);
            ContactPersonDAO contactPersonDAO = new ContactPersonDAO(connection);
            ScheduleDAO scheduleDAO = new ScheduleDAO(connection);
            BookingDAO bookingDAO = new BookingDAO(connection);
            TransitBookingDAO transitBookingDAO = new TransitBookingDAO(connection);

            paymentDAO.createPayment(transitBooking.getBooking().getPayment());
            contactPersonDAO.createContactPerson(transitBooking.getBooking().getContactPerson());
            bookingDAO.createBooking(transitBooking.getBooking());

            for (ScheduleTickets scheduleTickets : transitBooking.getScheduleTicketsListUpTrips()) {
                scheduleDAO.createSchedule(scheduleTickets.getSchedule());
                for (Ticket ticket : scheduleTickets.getAdultTickets()) {
                    //if passenger already assigned an passenger ID not need to create new passenger entry
                    if (ticket.getPassenger().getPassengerID() == null) {
                        passengerDAO.createPassenger(ticket.getPassenger());
                    }
                    ticketDAO.createTicket(ticket);
                    transitBookingDAO.createBookingTransit(transitBooking.getBooking(), scheduleTickets.getSchedule(), ticket, scheduleTickets.getTransitNumber(), TripDirection.UP);
                }
                for (Ticket ticket : scheduleTickets.getChildTickets()) {
                    if (ticket.getPassenger().getPassengerID() == null) {
                        passengerDAO.createPassenger(ticket.getPassenger());
                    }
                    //Since parent ticket not a reference, update on original object will not get effected
                    Ticket tkt = Helper.findTicket(scheduleTickets.getAdultTickets(),ticket.getParentTicket().getTicketNumber());
                    ticket.getParentTicket().setTickerNumberARS(tkt.getTickerNumberARS());
                    ticketDAO.createTicket(ticket);
                    transitBookingDAO.createBookingTransit(transitBooking.getBooking(), scheduleTickets.getSchedule(), ticket, scheduleTickets.getTransitNumber(),TripDirection.UP);
                }
                for (Ticket ticket : scheduleTickets.getInfantTickets()) {
                    if (ticket.getPassenger().getPassengerID() == null) {
                        passengerDAO.createPassenger(ticket.getPassenger());
                    }
                    //Since parent ticket not a reference, update on original object will not get effected
                    Ticket tkt = Helper.findTicket(scheduleTickets.getAdultTickets(),ticket.getParentTicket().getTicketNumber());
                    ticket.getParentTicket().setTickerNumberARS(tkt.getTickerNumberARS());
                    ticketDAO.createTicket(ticket);
                    transitBookingDAO.createBookingTransit(transitBooking.getBooking(), scheduleTickets.getSchedule(), ticket, scheduleTickets.getTransitNumber(),TripDirection.UP);
                }

            }

            for (ScheduleTickets scheduleTickets : transitBooking.getScheduleTicketsListDownTrips()) {
                scheduleDAO.createSchedule(scheduleTickets.getSchedule());
                for (Ticket ticket : scheduleTickets.getAdultTickets()) {
                    //if passenger already assigned an passenger ID not need to create new passenger entry
                    if (ticket.getPassenger().getPassengerID() == null) {
                        passengerDAO.createPassenger(ticket.getPassenger());
                    }
                    ticketDAO.createTicket(ticket);
                    transitBookingDAO.createBookingTransit(transitBooking.getBooking(), scheduleTickets.getSchedule(), ticket, scheduleTickets.getTransitNumber(),TripDirection.DOWN);
                }
                for (Ticket ticket : scheduleTickets.getChildTickets()) {
                    if (ticket.getPassenger().getPassengerID() == null) {
                        passengerDAO.createPassenger(ticket.getPassenger());
                    }
                    //Since parent ticket not a reference, update on original object will not get effected
                    Ticket tkt = Helper.findTicket(scheduleTickets.getAdultTickets(),ticket.getParentTicket().getTicketNumber());
                    ticket.getParentTicket().setTickerNumberARS(tkt.getTickerNumberARS());
                    ticketDAO.createTicket(ticket);
                    transitBookingDAO.createBookingTransit(transitBooking.getBooking(), scheduleTickets.getSchedule(), ticket, scheduleTickets.getTransitNumber(),TripDirection.DOWN);
                }
                for (Ticket ticket : scheduleTickets.getInfantTickets()) {
                    if (ticket.getPassenger().getPassengerID() == null) {
                        passengerDAO.createPassenger(ticket.getPassenger());
                    }
                    //Since parent ticket not a reference, update on original object will not get effected
                    Ticket tkt = Helper.findTicket(scheduleTickets.getAdultTickets(),ticket.getParentTicket().getTicketNumber());
                    ticket.getParentTicket().setTickerNumberARS(tkt.getTickerNumberARS());
                    ticketDAO.createTicket(ticket);
                    transitBookingDAO.createBookingTransit(transitBooking.getBooking(), scheduleTickets.getSchedule(), ticket, scheduleTickets.getTransitNumber(),TripDirection.DOWN);
                }

            }
        } finally {
            Transaction.endTransaction(connection);
        }
    }





}
