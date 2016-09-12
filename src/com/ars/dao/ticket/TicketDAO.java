package com.ars.dao.ticket;

import com.ars.domain.cancellation.Cancellation;
import com.ars.domain.passenger.Passenger;
import com.ars.domain.ticket.Ticket;
import com.ars.system.APPStatics;
import com.ars.util.db.DBConnection;
import com.ars.util.enums.TicketStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JJ on 8/30/16.
 */
public class TicketDAO {
    private Connection connection;

    public TicketDAO(Connection connection) {
        this.connection = connection;
    }

    public Ticket createTicket(Ticket ticket) throws Exception {

        String sqlNextTicketNumber = "SELECT nextval('" + APPStatics.schemaName + ".\"seqTicketNumberGenerator\"')";
        String sqlInsertIntoTicket = "INSERT INTO " + APPStatics.schemaName + ".\"TICKET\" " +
                "(ticket_number,status,seat_number,ticket_number_ars,passenger_id,parent_ticket_number) VALUES(?,?,?,?,?,?)";

        PreparedStatement statement1 = connection.prepareStatement(sqlNextTicketNumber);
        ResultSet resultSet1 = statement1.executeQuery();
        resultSet1.next();
        int nextTicketNumber = resultSet1.getInt("nextval");
        ticket.setTickerNumberARS(nextTicketNumber);


        PreparedStatement statement2 = connection.prepareStatement(sqlInsertIntoTicket);

        statement2.setString(1, ticket.getTicketNumber());
        statement2.setString(2, ticket.getStatus().toString());
        statement2.setInt(3, ticket.getSeatNumber());
        statement2.setInt(4, ticket.getTickerNumberARS());
        statement2.setString(5, ticket.getPassenger().getPassengerID());
        if (ticket.getParentTicket() != null) {
            statement2.setInt(6, ticket.getParentTicket().getTickerNumberARS());
        } else {
            statement2.setInt(6, 0);
        }

        statement2.execute();

        return ticket;
    }


    public Ticket getTicketByID(int ticketID) throws Exception {

//        String sqlGetTicket = "SELECT * FROM " + APPStatics.schemaName + ".\"TICKET\" child, " + APPStatics.schemaName + ".\"TICKET\" parent,  " + APPStatics.schemaName + ".\"PASSENGER\" pas " +
//                "WHERE child.ticket_number=? " +
//                "AND child.parent_ticket_number = parent.ticket_number " +
//                "AND child.passenger_id = ";

        String sqlGetTicket = "SELECT * FROM " + APPStatics.schemaName + ".\"TICKET\" " +
                "WHERE ticket_number_ars=? ";

        PreparedStatement statement2 = connection.prepareStatement(sqlGetTicket);
        statement2.setInt(1, ticketID);
        ResultSet resultSet = statement2.executeQuery();

        Ticket ticket = new Ticket();

        if (resultSet.next()) {
            ticket.setTicketNumber(resultSet.getString("ticket_number"));
            ticket.setStatus(TicketStatus.valueOf(resultSet.getString("status").trim()));
            ticket.setSeatNumber(resultSet.getInt("seat_number"));
            ticket.setTickerNumberARS(resultSet.getInt("ticket_number_ars"));
            Passenger passenger = new Passenger();
            passenger.setPassengerID(resultSet.getString("passenger_id"));
            ticket.setPassenger(passenger);
            Ticket parent = new Ticket();
            parent.setTickerNumberARS(resultSet.getInt("parent_ticket_number"));
            ticket.setParentTicket(parent);

        } else {
            throw new Exception("No Ticket found with given ID");
        }

        return ticket;
    }


    public Ticket updateTicketStatus(Ticket ticket,  TicketStatus ticketStatus) throws Exception {

        String sqlUpdateTicket = "UPDATE " + APPStatics.schemaName + ".\"TICKET\" " +
                "SET status = ?, " +
                "cancellation_id = ?" +
                "WHERE ticket_number_ars = ?";


        PreparedStatement statement2 = connection.prepareStatement(sqlUpdateTicket);

        statement2.setString(1, ticketStatus.toString());
        statement2.setString(2, ticket.getCancellation().getCancellationID());
        statement2.setInt(3, ticket.getTickerNumberARS());
        statement2.execute();

        return ticket;
    }


    /**
     * get list of ticket which are connected with given ticket ie tickets of same passenger in a booking
     * @param ticket
     * @return
     * @throws Exception
     */
    public List<Ticket> getConnectedTickets (Ticket ticket) throws Exception {

        String sqlSelect = "SELECT *,sec.ticket_number_ars as sec_ticket_number_ars FROM " + APPStatics.schemaName + ".\"TICKET\" prim, " + APPStatics.schemaName + ".\"TICKET\" sec " +
                "WHERE prim.ticket_number_ars = ? " +
                "AND prim.passenger_id = sec.passenger_id";


        PreparedStatement statement2 = connection.prepareStatement(sqlSelect);

        statement2.setInt(1, ticket.getTickerNumberARS());
        ResultSet resultSet = statement2.executeQuery();
        Ticket ticket1 = new Ticket();
        List<Ticket> tickets = new ArrayList<Ticket>();
        Cancellation cancellation = null;
        while (resultSet.next()){
            ticket1 = new Ticket();
            tickets.add(ticket1);
            ticket1.setTickerNumberARS(resultSet.getInt("sec_ticket_number_ars"));
            cancellation = new Cancellation();
            cancellation.setCancellationID(resultSet.getString("cancellation_id"));
            ticket1.setCancellation(cancellation);
            ticket1.setTicketNumber(resultSet.getString("ticket_number"));
        }

        return tickets;
    }


    public List<Ticket> getTickets (Ticket ticket) throws Exception {

        String sqlSelect = "SELECT * FROM " + APPStatics.schemaName + ".\"TICKET\" " +
                "WHERE ticket_number LIKE '%"+ ticket.getTicketNumber() + "%' ";


        PreparedStatement statement2 = connection.prepareStatement(sqlSelect);

        ResultSet resultSet = statement2.executeQuery();
        Ticket ticket1 = new Ticket();
        List<Ticket> tickets = new ArrayList<Ticket>();
        while (resultSet.next()){
            ticket1 = new Ticket();
            ticket1.setTickerNumberARS(resultSet.getInt("ticket_number_ars"));
            ticket1.setTicketNumber(resultSet.getString("ticket_number"));
            ticket1.setStatus(TicketStatus.valueOf(resultSet.getString("status").trim()));
            ticket1.setSeatNumber(resultSet.getInt("seat_number"));

            Passenger passenger = new Passenger();
            passenger.setPassengerID(resultSet.getString("passenger_id"));
            ticket1.setPassenger(passenger);

            Ticket parent = new Ticket();
            parent.setTickerNumberARS(resultSet.getInt("parent_ticket_number"));
            ticket1.setParentTicket(parent);

            Cancellation cancellation = new Cancellation();
            cancellation.setCancellationID(resultSet.getString("cancellation_id"));
            ticket1.setCancellation(cancellation);

            tickets.add(ticket1);
        }

        return tickets;
    }



    public List<Ticket> getTickets (Ticket ticket,TicketStatus ticketStatus) throws Exception {

        String sqlSelect = "SELECT * FROM " + APPStatics.schemaName + ".\"TICKET\" " +
                "WHERE ticket_number LIKE '%"+ ticket.getTicketNumber() + "%' " +
                "AND status = ?";


        PreparedStatement statement2 = connection.prepareStatement(sqlSelect);
        statement2.setString(1,ticketStatus.toString());

        ResultSet resultSet = statement2.executeQuery();
        Ticket ticket1 = new Ticket();
        List<Ticket> tickets = new ArrayList<Ticket>();
        while (resultSet.next()){
            ticket1 = new Ticket();
            ticket1.setTickerNumberARS(resultSet.getInt("ticket_number_ars"));
            ticket1.setTicketNumber(resultSet.getString("ticket_number"));
            ticket1.setStatus(TicketStatus.valueOf(resultSet.getString("status").trim()));
            ticket1.setSeatNumber(resultSet.getInt("seat_number"));

            Passenger passenger = new Passenger();
            passenger.setPassengerID(resultSet.getString("passenger_id"));
            ticket1.setPassenger(passenger);

            Ticket parent = new Ticket();
            parent.setTickerNumberARS(resultSet.getInt("parent_ticket_number"));
            ticket1.setParentTicket(parent);

            Cancellation cancellation = new Cancellation();
            cancellation.setCancellationID(resultSet.getString("cancellation_id"));
            ticket1.setCancellation(cancellation);

            tickets.add(ticket1);
        }

        return tickets;
    }

}
