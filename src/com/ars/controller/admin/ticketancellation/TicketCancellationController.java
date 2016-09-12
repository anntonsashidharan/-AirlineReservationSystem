package com.ars.controller.admin.ticketancellation;

import com.ars.domain.cancellation.Cancellation;
import com.ars.domain.ticket.Ticket;
import com.ars.domain.transit.TransitBooking;
import com.ars.domain.user.UserLogin;
import com.ars.service.booking.TransitBookingService;
import com.ars.service.cancellation.CancellationService;
import com.ars.service.ticket.TicketService;
import com.ars.system.APPStatics;
import com.ars.util.enums.TicketStatus;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by JJ on 9/10/16.
 */
@WebServlet("/admin/ticketCancellation")
public class TicketCancellationController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
        UserLogin userLogin = (UserLogin)request.getSession().getAttribute(APPStatics.SessionStatics.LOGGED_USER);
        if(userLogin==null){
            request.setAttribute(APPStatics.RequestStatics.ERROR_MESSAGE,"User not logged");
            response.sendRedirect("/login");
        }else if(userLogin.getPortals().contains(APPStatics.PortalStatics.APPROVE_TICKET_CANCELLATION)){
            String req = request.getParameter("req");
            String ticketNumber = request.getParameter("ticketNumber");
            Ticket ticket = null;

            if ("search".equals(req)) {

                ticket = new Ticket();
                ticket.setTicketNumber(ticketNumber);

                List<Ticket> tickets = TicketService.getTickets(ticket,TicketStatus.CANCELLATION_REQUESTED);
                request.setAttribute(APPStatics.RequestStatics.TICKET_SEARCH_RESULT,tickets);
                request.getRequestDispatcher("/jsp/admin/cancellation/ticketssearchresult.jsp").forward(request, response);
                return;
            }else if ("getTicketDetails".equals(req)) {
                ticket = new Ticket();
                ticket.setTickerNumberARS(Integer.parseInt(ticketNumber));
                List<Ticket> tickets = TicketService.getChainedTickets(ticket);
                request.setAttribute(APPStatics.RequestStatics.TICKET_GETTING_CANCELLED,tickets);
                request.setAttribute(APPStatics.RequestStatics.CANCELLING_TICKET_NUMBER,ticketNumber);
                request.getRequestDispatcher("/jsp/admin/cancellation/ticketCancellationConfirmation.jsp").forward(request, response);
                return;
            }else if("approveCancellationRequest".equals(req)){
                ticket = new Ticket();
                ticket.setTickerNumberARS(Integer.parseInt(ticketNumber));
                String approvedRefundAmount = request.getParameter("approvedRefundAmount");
                float approvedRefundAmountFloat = Float.parseFloat(approvedRefundAmount);
                List<Ticket> cancelledTickets =  CancellationService.approveCancellation(ticket,approvedRefundAmountFloat,userLogin);
                String listOfTickets = "";
                for (Ticket ticket1: cancelledTickets){
                    listOfTickets = listOfTickets + ticket1.getTicketNumber() + ",";
                }
                request.setAttribute(APPStatics.RequestStatics.SUCCESS_MESSAGE,"Cancellation Request Accepted for tickets {" + listOfTickets + "}");
                request.getRequestDispatcher("/jsp/admin/cancellation/ticketCancellationConfirmation.jsp").forward(request, response);
                return;
            }





        }else {
            request.setAttribute(APPStatics.RequestStatics.ERROR_MESSAGE,"User doesn\'t have privileges to access this page ");
            response.sendRedirect("/main");
        }
        }catch (Exception e){
            request.setAttribute(APPStatics.RequestStatics.ERROR_MESSAGE,e.getMessage());
            request.getRequestDispatcher("/jsp/admin/cancellation/ticketCancellation.jsp");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute(APPStatics.SessionStatics.REDIRECT_PAGE_AFTER_LOGIN,"/admin/ticketCancellation");
        UserLogin userLogin = (UserLogin)request.getSession().getAttribute(APPStatics.SessionStatics.LOGGED_USER);
        if(userLogin==null){
            request.setAttribute(APPStatics.RequestStatics.ERROR_MESSAGE,"User not logged");
            response.sendRedirect("/login");
        }else if(userLogin.getPortals().contains(APPStatics.PortalStatics.APPROVE_TICKET_CANCELLATION)){
            request.getRequestDispatcher("/jsp/admin/cancellation/ticketCancellation.jsp").forward(request,response);
        }else {
            request.setAttribute(APPStatics.RequestStatics.ERROR_MESSAGE,"User doesn\'t have privileges to access this page ");
            response.sendRedirect("/main");
        }
    }
}
