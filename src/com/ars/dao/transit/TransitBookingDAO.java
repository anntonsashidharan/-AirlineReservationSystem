package com.ars.dao.transit;

import com.ars.domain.booking.Booking;
import com.ars.domain.passenger.Passenger;
import com.ars.domain.schedule.Schedule;
import com.ars.domain.ticket.Ticket;
import com.ars.domain.transit.ScheduleTickets;
import com.ars.domain.transit.TransitBooking;
import com.ars.util.enums.PassengerType;
import com.ars.util.enums.TripDirection;
import com.ars.vo.transit.Transit;
import com.ars.system.APPStatics;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by JJ on 9/6/16.
 */
public class TransitBookingDAO {
    private Connection connection;

    public TransitBookingDAO(Connection connection) {
        this.connection = connection;
    }

    public void createBookingTransit(Booking booking, Schedule schedule, Ticket ticket, int transitNumber, TripDirection tripDirection) throws Exception {

        String sqlInsertIntoTransitDetails = "INSERT INTO " + APPStatics.schemaName + ".\"BOOKING_TRANSIT_DETAILS\" " +
                "(booking_number,transite_number,schedule_id,ticket_number,trip_direction) VALUES(?,?,?,?,?)";


        PreparedStatement statement2 = connection.prepareStatement(sqlInsertIntoTransitDetails);

        statement2.setString(1, booking.getBookingNumber());
        statement2.setInt(2, transitNumber);
        statement2.setInt(3, schedule.getScheduleNumberARS());
        statement2.setInt(4, ticket.getTickerNumberARS());
        statement2.setString(5, tripDirection.toString());
        statement2.execute();


    }

    public TransitBooking getTransitBookingByBookingNumber(String bookingNumber) throws Exception {

        String sqlSelectTransitDetails = "SELECT tt.*,p.type FROM " + APPStatics.schemaName + ".\"BOOKING_TRANSIT_DETAILS\" tt, " + APPStatics.schemaName + ".\"TICKET\" ticket, " + APPStatics.schemaName + ".\"PASSENGER\" p " +
                "WHERE booking_number = ? " +
                "AND tt.ticket_number = ticket.ticket_number_ars " +
                "AND ticket.passenger_id = p.passenger_id " +
                "ORDER BY trip_direction,transite_number ";

        HashSet<String> tripDirectionHashSet = new HashSet<String>();
        HashSet<Integer> transitNumberHashSet = new HashSet<Integer>();
        HashSet<String> scheduleHashSet = new HashSet<String>();


        PreparedStatement statement2 = connection.prepareStatement(sqlSelectTransitDetails);
        statement2.setString(1, bookingNumber);
        ResultSet resultSet = statement2.executeQuery();

        TransitBooking transitBooking = new TransitBooking();

        Booking booking = new Booking();
        booking.setBookingNumber(bookingNumber);
        transitBooking.setBooking(booking);

        List<ScheduleTickets> scheduleTicketsUpTrips = new ArrayList<ScheduleTickets>();
        transitBooking.setScheduleTicketsListUpTrips(scheduleTicketsUpTrips);

        List<ScheduleTickets> scheduleTicketsDownTrips = new ArrayList<ScheduleTickets>();
        transitBooking.setScheduleTicketsListDownTrips(scheduleTicketsDownTrips);

        Ticket ticket = new Ticket();
        ScheduleTickets scheduleTickets = new ScheduleTickets();
        Schedule schedule = new Schedule();
        List<Ticket> adultTickets = new ArrayList<Ticket>();
        List<Ticket> childTickets = new ArrayList<Ticket>();
        List<Ticket> infantTickets = new ArrayList<Ticket>();
        while (resultSet.next()) {
            boolean addToTransitNumber = transitNumberHashSet.add(resultSet.getInt("transite_number"));
            boolean addToTripDirection = tripDirectionHashSet.add(resultSet.getString("trip_direction"));
            boolean addToScheduleID = scheduleHashSet.add(resultSet.getString("schedule_id"));


            if (addToTransitNumber || addToTripDirection || addToScheduleID) {
                scheduleTickets = new ScheduleTickets();
                schedule = new Schedule();
                schedule.setScheduleNumberARS(resultSet.getInt("schedule_id"));
                scheduleTickets.setSchedule(schedule);
                scheduleTickets.setTripDirection(TripDirection.valueOf(resultSet.getString("trip_direction")));
                scheduleTickets.setTransitNumber(resultSet.getInt("transite_number"));
                adultTickets = new ArrayList<Ticket>();
                childTickets = new ArrayList<Ticket>();
                infantTickets = new ArrayList<Ticket>();
                scheduleTickets.setAdultTickets(adultTickets);
                scheduleTickets.setChildTickets(childTickets);
                scheduleTickets.setInfantTickets(infantTickets);
                if(TripDirection.UP.toString().equals(resultSet.getString("trip_direction"))){
                    scheduleTicketsUpTrips.add(scheduleTickets);
                }else if(TripDirection.DOWN.toString().equals(resultSet.getString("trip_direction"))){
                    scheduleTicketsDownTrips.add(scheduleTickets);
                }
            }

            //for each row create new ticket and put into ticket list of scheduleTickets
            ticket = new Ticket();
            ticket.setTickerNumberARS(resultSet.getInt("ticket_number"));
            if(PassengerType.ADULT.toString().equals(resultSet.getString("type"))){
                adultTickets.add(ticket);
            }else if(PassengerType.CHILD.toString().equals(resultSet.getString("type"))){
                childTickets.add(ticket);
            }else if(PassengerType.INFANT.toString().equals(resultSet.getString("type"))){
                infantTickets.add(ticket);
            }
        }

        return transitBooking;
    }




}
