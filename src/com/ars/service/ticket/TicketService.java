package com.ars.service.ticket;

import com.ars.dao.ticket.TicketDAO;
import com.ars.domain.cancellation.Cancellation;
import com.ars.domain.ticket.Ticket;
import com.ars.util.db.Transaction;
import com.ars.util.enums.TicketStatus;

import java.sql.Connection;
import java.util.List;

/**
 * Created by JJ on 9/10/16.
 */
public class TicketService {

    public static List<Ticket> getChainedTickets(Ticket ticket) throws Exception {
        Connection connection = null;
        List<Ticket> tickets = null;
        try {
            connection = Transaction.beginTransaction();

            TicketDAO ticketDAO = new TicketDAO(connection);

            tickets = ticketDAO.getConnectedTickets(ticket);
            for(int i = 0;i<tickets.size();i++){
                Ticket ticket1 = ticketDAO.getTicketByID(tickets.get(i).getTickerNumberARS());
                tickets.remove(i);
                tickets.add(i,ticket1);
            }

        } finally {
            Transaction.endTransaction(connection);
        }
        return tickets;
    }


    public static List<Ticket> getTickets(Ticket ticket) throws Exception {
        Connection connection = null;
        List<Ticket> tickets = null;
        try {
            connection = Transaction.beginTransaction();

            TicketDAO ticketDAO = new TicketDAO(connection);

            tickets = ticketDAO.getTickets(ticket);
        } finally {
            Transaction.endTransaction(connection);
        }
        return tickets;
    }

    public static List<Ticket> getTickets(Ticket ticket,TicketStatus ticketStatus) throws Exception {
        Connection connection = null;
        List<Ticket> tickets = null;
        try {
            connection = Transaction.beginTransaction();

            TicketDAO ticketDAO = new TicketDAO(connection);

            tickets = ticketDAO.getTickets(ticket,ticketStatus);
        } finally {
            Transaction.endTransaction(connection);
        }
        return tickets;
    }
}
