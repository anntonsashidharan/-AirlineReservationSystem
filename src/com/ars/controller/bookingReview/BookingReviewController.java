package com.ars.controller.bookingReview;

import com.ars.domain.booking.Booking;
import com.ars.domain.ticket.Ticket;
import com.ars.domain.transit.TransitBooking;
import com.ars.domain.user.UserLogin;
import com.ars.service.booking.BookingService;
import com.ars.service.booking.TransitBookingService;
import com.ars.service.cancellation.CancellationService;
import com.ars.service.ticket.TicketService;
import com.ars.system.APPStatics;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JJ on 9/8/16.
 */
@WebServlet("/reviewBookings")
public class BookingReviewController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestType = null;
        try{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               UserLogin userLogin = (UserLogin)request.getSession().getAttribute(APPStatics.SessionStatics.LOGGED_USER);
            if(userLogin==null){
                request.setAttribute(APPStatics.RequestStatics.ERROR_MESSAGE,"User not logged");
                response.sendRedirect("/login");
                return;
            }else {
                requestType = request.getParameter("req");
                if(requestType!=null && "searchByBookingNumber".equalsIgnoreCase(requestType)){
                    List<Booking> bookings = new ArrayList<Booking>();
                    String bookingNumber = request.getParameter("bookingNumber");
                    if(!"".equals(bookingNumber)){
                        bookings = BookingService.getBookingsByBookingsNumberAndUserName(bookingNumber,userLogin);
                    }else {
                        bookings = BookingService.getBookingsByUserName(userLogin);
                    }

                    if(bookings.size()==0){
                        request.setAttribute(APPStatics.RequestStatics.ERROR_MESSAGE,"No booking found");
                    }else {
                        request.setAttribute(APPStatics.RequestStatics.BOOKING_SEARCH_RESULT,bookings);
                    }
                    request.getRequestDispatcher("/jsp/booking/bookingSearchResult/bookingSearchResult.jsp").forward(request,response);
                    return;
                }else if ("getBooking".equals(request.getParameter("req"))) {
                    TransitBooking transitBooking = TransitBookingService.getTransitBooking((String) request.getParameter("bookingNumber"));
                    request.getSession().setAttribute(APPStatics.SessionStatics.BOOKING_DETAILS,transitBooking);
                    request.getRequestDispatcher("/jsp/templates/bookingSummery/booking.jsp").forward(request, response);
                    return;
                }else if ("cancel".equals(request.getParameter("req"))) {
                    String ticketNumber = request.getParameter("ticketNumber");
                    Ticket  ticket = new Ticket();
                    ticket.setTickerNumberARS(Integer.parseInt(ticketNumber));
                    List<Ticket> tickets = TicketService.getChainedTickets(ticket);
                    request.getSession().setAttribute(APPStatics.SessionStatics.BOOKING_DETAILS,tickets);
                    request.setAttribute("ticketListGettingCancelled",tickets);
                    request.getRequestDispatcher("/jsp/cancellation/cancellationConfirmationPage.jsp").forward(request, response);
                    return;
                }
                else if ("confirmCancellation".equals(request.getParameter("req"))) {
                    String ticketNumber = request.getParameter("ticketNumber");
                    Ticket  ticket = new Ticket();
                    ticket.setTickerNumberARS(Integer.parseInt(ticketNumber));
                    CancellationService.requestCancellation(ticket);
                    request.getRequestDispatcher("/jsp/cancellation/cancellationSubmitedSuccessPage.jsp").forward(request, response);
                    return;
                }
            }

        }catch (Exception e){
            request.setAttribute(APPStatics.RequestStatics.ERROR_MESSAGE,e.getMessage());
            if(requestType!=null && "getBooking".equals(request.getParameter("req"))){

            }else if (requestType!=null && "searchByBookingNumber".equalsIgnoreCase(requestType)){
                request.getRequestDispatcher("/jsp/booking/bookingSearchResult/bookingSearchResult.jsp").forward(request,response);
                return;
            }else {
                request.getRequestDispatcher("/jsp/booking/bookingReview/bookingReview.jsp").forward(request,response);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/booking/bookingReview/bookingReview.jsp").forward(request,response);
    }
}
