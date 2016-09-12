package com.ars.service.cancellation;

import com.ars.dao.cancellation.CancellationDAO;
import com.ars.dao.ticket.TicketDAO;
import com.ars.domain.cancellation.Cancellation;
import com.ars.domain.ticket.Ticket;
import com.ars.domain.user.UserLogin;
import com.ars.util.db.Transaction;
import com.ars.util.enums.TicketStatus;

import java.sql.Connection;
import java.util.List;

/**
 * Created by JJ on 9/10/16.
 */
public class CancellationService {

    public static Cancellation requestCancellation(Ticket ticket) throws Exception {
        Connection connection = null;
        Cancellation cancellation = new Cancellation();
        try {
            connection = Transaction.beginTransaction();



            CancellationDAO cancellationDAO = new CancellationDAO(connection);
            TicketDAO ticketDAO = new TicketDAO(connection);


            cancellation = cancellationDAO.createCancellation(cancellation);
            List<Ticket> tickets = ticketDAO.getConnectedTickets(ticket);
            for (Ticket ticket1 : tickets){
                ticket1.setCancellation(cancellation);
                ticketDAO.updateTicketStatus(ticket1, TicketStatus.CANCELLATION_REQUESTED);
            }

        } finally {
            Transaction.endTransaction(connection);
        }
        return  cancellation;
    }


    public static List<Ticket> approveCancellation(Ticket ticket,float refundAmount, UserLogin approvedUser) throws Exception {
        Connection connection = null;
        Cancellation cancellation = new Cancellation();
        List<Ticket> tickets = null;
        try {
            connection = Transaction.beginTransaction();



            CancellationDAO cancellationDAO = new CancellationDAO(connection);
            TicketDAO ticketDAO = new TicketDAO(connection);

            tickets = ticketDAO.getConnectedTickets(ticket);

            cancellation.setCancellationID(tickets.get(0).getCancellation().getCancellationID());
            cancellation.setApprovedRefund(refundAmount);
            cancellation.setApprovedBy(approvedUser);
            cancellationDAO.approveCancellation(cancellation);


            for (Ticket ticket1 : tickets){
                ticket1.setCancellation(cancellation);
                ticketDAO.updateTicketStatus(ticket1, TicketStatus.CANCELLED);
            }

        } finally {
            Transaction.endTransaction(connection);
        }
        return  tickets;
    }


}
