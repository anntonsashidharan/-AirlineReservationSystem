package com.ars.service.booking;

import com.ars.dao.airport.AirportDAO;
import com.ars.dao.booking.BookingDAO;
import com.ars.dao.contactPerson.ContactPersonDAO;
import com.ars.dao.passenger.PassengerDAO;
import com.ars.dao.payment.PaymentDAO;
import com.ars.dao.schedule.ScheduleDAO;
import com.ars.dao.ticket.TicketDAO;
import com.ars.dao.transit.TransitBookingDAO;
import com.ars.domain.airport.Airport;
import com.ars.domain.passenger.Passenger;
import com.ars.domain.ticket.Ticket;
import com.ars.domain.transit.ScheduleTickets;
import com.ars.domain.transit.TransitBooking;
import com.ars.util.db.Transaction;

import java.sql.Connection;
import java.util.Iterator;

/**
 * Created by JJ on 9/9/16.
 */
public class TransitBookingService {
    public static TransitBooking getTransitBooking(String bookingNumber) throws Exception{
        Connection connection = null;
        TransitBooking transitBooking = null;
        try{
            connection = Transaction.beginTransaction();

            TransitBookingDAO transitBookingDAO = new TransitBookingDAO(connection);
            PassengerDAO passengerDAO = new PassengerDAO(connection);
            TicketDAO ticketDAO = new TicketDAO(connection);
            ScheduleDAO scheduleDAO = new ScheduleDAO(connection);
            BookingDAO bookingDAO = new BookingDAO(connection);
            AirportDAO airportDAO = new AirportDAO(connection);
            ContactPersonDAO contactPersonDAO = new ContactPersonDAO(connection);
            PaymentDAO paymentDAO = new PaymentDAO(connection);

            transitBooking = transitBookingDAO.getTransitBookingByBookingNumber(bookingNumber);
            transitBooking.setBooking(bookingDAO.getBookingByID(bookingNumber));
            transitBooking.getBooking().setFromAirport(airportDAO.getAirportByCode(transitBooking.getBooking().getFromAirport().getCode()));
            transitBooking.getBooking().setToAirport(airportDAO.getAirportByCode(transitBooking.getBooking().getToAirport().getCode()));
            transitBooking.getBooking().setContactPerson(contactPersonDAO.getContactPersonByID(transitBooking.getBooking().getContactPerson().getContactPersonID()));
            transitBooking.getBooking().setPayment(paymentDAO.getPaymentByID(transitBooking.getBooking().getPayment().getPaymentID()));

            for(ScheduleTickets scheduleTickets : transitBooking.getScheduleTicketsListUpTrips()){

                for( int i = 0; i < scheduleTickets.getAdultTickets().size();i++){
                    /*scheduleTickets.getAdultTickets().get(i).setTickerNumberARS(ticket.getTickerNumberARS());
                    scheduleTickets.getAdultTickets().get(i).setTicketNumber(ticket.getTicketNumber());
                    scheduleTickets.getAdultTickets().get(i).setSchedule(ticket.getSchedule());
                    scheduleTickets.getAdultTickets().get(i).setBooking(ticket.getBooking());
                    scheduleTickets.getAdultTickets().get(i).setSeatNumber(ticket.getSeatNumber());
                    scheduleTickets.getAdultTickets().get(i).setStatus(ticket.getStatus());
                    scheduleTickets.getAdultTickets().get(i).setPassenger(passengerDAO.getPassengerByID(ticket.getPassenger().getPassengerID()));*/
                    Ticket ticket = ticketDAO.getTicketByID(scheduleTickets.getAdultTickets().get(i).getTickerNumberARS());
                    ticket.setPassenger(passengerDAO.getPassengerByID(ticket.getPassenger().getPassengerID()));
                    scheduleTickets.getAdultTickets().remove(i);
                    scheduleTickets.getAdultTickets().add(i, ticket);
                }
                for( int i = 0; i < scheduleTickets.getChildTickets().size();i++){
                    Ticket ticket = ticketDAO.getTicketByID(scheduleTickets.getChildTickets().get(i).getTickerNumberARS());
                    ticket.setParentTicket(ticketDAO.getTicketByID(ticket.getParentTicket().getTickerNumberARS()));
                    ticket.setPassenger(passengerDAO.getPassengerByID(ticket.getPassenger().getPassengerID()));
                    scheduleTickets.getChildTickets().remove(i);
                    scheduleTickets.getChildTickets().add(i, ticket);
                }
                for(int i = 0; i < scheduleTickets.getInfantTickets().size();i++){
                    Ticket ticket = ticketDAO.getTicketByID(scheduleTickets.getInfantTickets().get(i).getTickerNumberARS());
                    ticket.setParentTicket(ticketDAO.getTicketByID(ticket.getParentTicket().getTickerNumberARS()));
                    ticket.setPassenger(passengerDAO.getPassengerByID(ticket.getPassenger().getPassengerID()));
                    scheduleTickets.getInfantTickets().remove(i);
                    scheduleTickets.getInfantTickets().add(i,ticket);
                }
                scheduleTickets.setSchedule(scheduleDAO.getScheduleByID(scheduleTickets.getSchedule().getScheduleNumberARS()));
            }
            for(ScheduleTickets scheduleTickets : transitBooking.getScheduleTicketsListDownTrips()){

                for( int i = 0; i < scheduleTickets.getAdultTickets().size();i++){
                    Ticket ticket = ticketDAO.getTicketByID(scheduleTickets.getAdultTickets().get(i).getTickerNumberARS());
                    ticket.setPassenger(passengerDAO.getPassengerByID(ticket.getPassenger().getPassengerID()));
                    scheduleTickets.getAdultTickets().remove(i);
                    scheduleTickets.getAdultTickets().add(i, ticket);
                }
                for( int i = 0; i < scheduleTickets.getChildTickets().size();i++){
                    Ticket ticket = ticketDAO.getTicketByID(scheduleTickets.getChildTickets().get(i).getTickerNumberARS());
                    ticket.setParentTicket(ticketDAO.getTicketByID(ticket.getParentTicket().getTickerNumberARS()));
                    ticket.setPassenger(passengerDAO.getPassengerByID(ticket.getPassenger().getPassengerID()));
                    scheduleTickets.getChildTickets().remove(i);
                    scheduleTickets.getChildTickets().add(i, ticket);
                }
                for(int i = 0; i < scheduleTickets.getInfantTickets().size();i++){
                    Ticket ticket = ticketDAO.getTicketByID(scheduleTickets.getInfantTickets().get(i).getTickerNumberARS());
                    ticket.setParentTicket(ticketDAO.getTicketByID(ticket.getParentTicket().getTickerNumberARS()));
                    ticket.setPassenger(passengerDAO.getPassengerByID(ticket.getPassenger().getPassengerID()));
                    scheduleTickets.getInfantTickets().remove(i);
                    scheduleTickets.getInfantTickets().add(i,ticket);
                }
                scheduleTickets.setSchedule(scheduleDAO.getScheduleByID(scheduleTickets.getSchedule().getScheduleNumberARS()));
            }

        }finally {
            Transaction.endTransaction(connection);
        }
        return transitBooking;
    }
}
