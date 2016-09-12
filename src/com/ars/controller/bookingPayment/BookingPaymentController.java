package com.ars.controller.bookingPayment;

import com.airline.webservice.*;
import com.airline.webservice.Ticket;
import com.airline.webservice.TicketStatus;
import com.ars.domain.airport.*;
import com.ars.domain.airport.Airport;
import com.ars.domain.booking.Booking;
import com.ars.domain.contactPerson.ContactPerson;
import com.ars.domain.passenger.Passenger;
import com.ars.domain.payment.Payment;
import com.ars.domain.schedule.Schedule;
import com.ars.domain.ticket.*;
import com.ars.domain.transit.ScheduleTickets;
import com.ars.domain.transit.TransitBooking;
import com.ars.domain.trip.Trip;
import com.ars.domain.user.UserLogin;
import com.ars.service.booking.ReservationService;
import com.ars.system.APPStatics;
import com.ars.util.email.EmailSender;
import com.ars.util.enums.*;
import com.ars.util.parser.Parser;
import com.ars.vo.transit.Transit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.Exception;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by JJ on 8/28/16.
 */
@WebServlet("/bookingPayment")
public class BookingPaymentController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            UserLogin loggedUser = (UserLogin) request.getSession().getAttribute(APPStatics.SessionStatics.LOGGED_USER);

            Agency agency = new Agency();
            agency.setId("agency001");

            Trip upTrip = (Trip) request.getSession().getAttribute(APPStatics.SessionStatics.SELECTED_UP_TRIP_SCHEDULE_JAVA);
            Trip downTrip = (Trip) request.getSession().getAttribute(APPStatics.SessionStatics.SELECTED_DOWN_TRIP_SCHEDULE_JAVA);
            List<Passenger> adultPassengers = (List<Passenger>) request.getSession().getAttribute(APPStatics.SessionStatics.ADULT_LIST);
            List<Passenger> childPassengers = (List<Passenger>) request.getSession().getAttribute(APPStatics.SessionStatics.CHILD_LIST);
            List<Passenger> infantPassengers = (List<Passenger>) request.getSession().getAttribute(APPStatics.SessionStatics.INFANT_LIST);
            ContactPerson contactPerson = (ContactPerson)request.getSession().getAttribute(APPStatics.SessionStatics.CONTACT_PERSON);

            Float payAmount;
            if(request.getAttribute("amount")==null){
                payAmount = 500000f;
            }else {
                payAmount = Float.parseFloat((String)request.getAttribute("amount"));
            }
            //Float payAmount = Float.parseFloat((String)request.getAttribute("amount"));
            //validate payment from user if valid
            com.ars.domain.payment.Payment payment = new Payment();
            payment.setAmount(payAmount);
            Date date = new Date();
            payment.setPayedDate(date);
            payment.setPayedTime(date);
            payment.setPaypalUserName("paypaluser");

            TransitBooking transitBooking = new TransitBooking();
            List<ScheduleTickets>  scheduleTicketsList = new ArrayList<ScheduleTickets>();
            ScheduleTickets scheduleTickets = null;

            com.ars.domain.airport.Airport from = new Airport();
            from.setCode((String)request.getSession().getAttribute(APPStatics.SessionStatics.SOURCE_AIRPORT));

            com.ars.domain.airport.Airport to = new Airport();
            to.setCode((String)request.getSession().getAttribute(APPStatics.SessionStatics.DESTINATION_AIRPORT));


            Booking booking = new Booking();
            booking.setFromAirport(from);
            booking.setToAirport(to);
            booking.setUserLogin(loggedUser);
            booking.setPayment(payment);
            booking.setContactPerson(contactPerson);
            booking.setTravelClass(TravelClass.valueOf((String) request.getSession().getAttribute(APPStatics.SessionStatics.TRAVEL_CLASS)));

            transitBooking.setBooking(booking);
            transitBooking.setScheduleTicketsListUpTrips(scheduleTicketsList);

            for (Transit transit : upTrip.getTransits()) {

                Schedule schedule = transit.getSchedule();
                scheduleTickets = new ScheduleTickets();
                scheduleTickets.setTransitNumber(transit.getTransitNumber());
                scheduleTickets.setSchedule(schedule);
                Tickets tickets = null;
                if (schedule.getAirlineCompany().equals(APPStatics.AirlineCompany.SRILANKAN_AIRLINE)) {
                    Airline4_Service airline4_service = new Airline4_Service();
                    Airline4 airline4 = airline4_service.getAirline4Port();
                    tickets = airline4.reserveTickets(Parser.parseSchedule(schedule),
                            Parser.parsePassengerList(adultPassengers),
                            Parser.parsePassengerList(childPassengers),
                            Parser.parsePassengerList(infantPassengers),
                            TravelClass.valueOf((String) request.getSession().getAttribute(APPStatics.SessionStatics.TRAVEL_CLASS)), agency);

                }else if (schedule.getAirlineCompany().equals(APPStatics.AirlineCompany.QATAR_AIRWAYS)) {
                    Airline1_Service airline1_service = new Airline1_Service();
                    Airline1 airline1 = airline1_service.getAirline1Port();
                    tickets = airline1.reserveTickets(Parser.parseSchedule(schedule),
                            Parser.parsePassengerList(adultPassengers),
                            Parser.parsePassengerList(childPassengers),
                            Parser.parsePassengerList(infantPassengers),
                            TravelClass.valueOf((String) request.getSession().getAttribute(APPStatics.SessionStatics.TRAVEL_CLASS)), agency);


                }else if (schedule.getAirlineCompany().equals(APPStatics.AirlineCompany.MALASHIYA_AIRLINES)) {
                    Airline2_Service airline2_service = new Airline2_Service();
                    Airline2 airline2 = airline2_service.getAirline2Port();
                    tickets = airline2.reserveTickets(Parser.parseSchedule(schedule),
                            Parser.parsePassengerList(adultPassengers),
                            Parser.parsePassengerList(childPassengers),
                            Parser.parsePassengerList(infantPassengers),
                            TravelClass.valueOf((String) request.getSession().getAttribute(APPStatics.SessionStatics.TRAVEL_CLASS)), agency);


                }else if (schedule.getAirlineCompany().equals(APPStatics.AirlineCompany.BRITISH_AIRWAYS)) {
                    Airline3_Service airline3_service = new Airline3_Service();
                    Airline3 airline3 = airline3_service.getAirline3Port();
                    tickets = airline3.reserveTickets(Parser.parseSchedule(schedule),
                            Parser.parsePassengerList(adultPassengers),
                            Parser.parsePassengerList(childPassengers),
                            Parser.parsePassengerList(infantPassengers),
                            TravelClass.valueOf((String) request.getSession().getAttribute(APPStatics.SessionStatics.TRAVEL_CLASS)), agency);

                }

                List<com.ars.domain.ticket.Ticket> adultTickets = Parser.parseTickets(tickets.getAdultTickets());
                int i=0;
                for(com.ars.domain.ticket.Ticket ticket : adultTickets){
                    ticket.setPassenger(adultPassengers.get(i));
                    i++;
                }
                List<com.ars.domain.ticket.Ticket> childTickets = Parser.parseTickets(tickets.getChildTickets());
                i=0;
                for(com.ars.domain.ticket.Ticket ticket : childTickets){
                    ticket.setPassenger(childPassengers.get(i));
                    i++;
                }
                List<com.ars.domain.ticket.Ticket> infantTickets = Parser.parseTickets(tickets.getInfantTickets());
                i=0;
                for(com.ars.domain.ticket.Ticket ticket : infantTickets){
                    ticket.setPassenger(infantPassengers.get(i));
                    i++;
                }
                scheduleTickets.setAdultTickets(adultTickets);
                scheduleTickets.setChildTickets(childTickets);
                scheduleTickets.setInfantTickets(infantTickets);
                scheduleTicketsList.add(scheduleTickets);

            }


            String tripType = (String) request.getSession().getAttribute(APPStatics.SessionStatics.TRIP_TYPE);
            scheduleTicketsList = new ArrayList<ScheduleTickets>();
            transitBooking.setScheduleTicketsListDownTrips(scheduleTicketsList);

            if (tripType!=null && "round".equalsIgnoreCase(tripType)) {
                for (Transit transit : downTrip.getTransits()) {

                    Schedule schedule = transit.getSchedule();
                    scheduleTickets = new ScheduleTickets();
                    scheduleTickets.setTransitNumber(transit.getTransitNumber());
                    scheduleTickets.setSchedule(schedule);
                    Tickets tickets = null;
                    if (schedule.getAirlineCompany().equals(APPStatics.AirlineCompany.SRILANKAN_AIRLINE)) {
                        Airline4_Service airline4_service = new Airline4_Service();
                        Airline4 airline4 = airline4_service.getAirline4Port();
                        tickets = airline4.reserveTickets(Parser.parseSchedule(schedule),
                                Parser.parsePassengerList(adultPassengers),
                                Parser.parsePassengerList(childPassengers),
                                Parser.parsePassengerList(infantPassengers),
                                TravelClass.valueOf((String) request.getSession().getAttribute(APPStatics.SessionStatics.TRAVEL_CLASS)), agency);

                    }else if (schedule.getAirlineCompany().equals(APPStatics.AirlineCompany.QATAR_AIRWAYS)) {
                        Airline1_Service airline1_service = new Airline1_Service();
                        Airline1 airline1 = airline1_service.getAirline1Port();
                        tickets = airline1.reserveTickets(Parser.parseSchedule(schedule),
                                Parser.parsePassengerList(adultPassengers),
                                Parser.parsePassengerList(childPassengers),
                                Parser.parsePassengerList(infantPassengers),
                                TravelClass.valueOf((String) request.getSession().getAttribute(APPStatics.SessionStatics.TRAVEL_CLASS)), agency);


                    }else if (schedule.getAirlineCompany().equals(APPStatics.AirlineCompany.MALASHIYA_AIRLINES)) {
                        Airline2_Service airline2_service = new Airline2_Service();
                        Airline2 airline2 = airline2_service.getAirline2Port();
                        tickets = airline2.reserveTickets(Parser.parseSchedule(schedule),
                                Parser.parsePassengerList(adultPassengers),
                                Parser.parsePassengerList(childPassengers),
                                Parser.parsePassengerList(infantPassengers),
                                TravelClass.valueOf((String) request.getSession().getAttribute(APPStatics.SessionStatics.TRAVEL_CLASS)), agency);


                    }else if (schedule.getAirlineCompany().equals(APPStatics.AirlineCompany.BRITISH_AIRWAYS)) {
                        Airline3_Service airline3_service = new Airline3_Service();
                        Airline3 airline3 = airline3_service.getAirline3Port();
                        tickets = airline3.reserveTickets(Parser.parseSchedule(schedule),
                                Parser.parsePassengerList(adultPassengers),
                                Parser.parsePassengerList(childPassengers),
                                Parser.parsePassengerList(infantPassengers),
                                TravelClass.valueOf((String) request.getSession().getAttribute(APPStatics.SessionStatics.TRAVEL_CLASS)), agency);

                    }

                    List<com.ars.domain.ticket.Ticket> adultTickets = Parser.parseTickets(tickets.getAdultTickets());
                    int i=0;
                    for(com.ars.domain.ticket.Ticket ticket : adultTickets){
                        ticket.setPassenger(adultPassengers.get(i));
                        i++;
                    }
                    List<com.ars.domain.ticket.Ticket> childTickets = Parser.parseTickets(tickets.getChildTickets());
                    i=0;
                    for(com.ars.domain.ticket.Ticket ticket : childTickets){
                        ticket.setPassenger(childPassengers.get(i));
                        i++;
                    }
                    List<com.ars.domain.ticket.Ticket> infantTickets = Parser.parseTickets(tickets.getInfantTickets());
                    i=0;
                    for(com.ars.domain.ticket.Ticket ticket : infantTickets){
                        ticket.setPassenger(infantPassengers.get(i));
                        i++;
                    }
                    scheduleTickets.setAdultTickets(adultTickets);
                    scheduleTickets.setChildTickets(childTickets);
                    scheduleTickets.setInfantTickets(infantTickets);
                    scheduleTicketsList.add(scheduleTickets);

                }
            }

            request.getSession().setAttribute(APPStatics.SessionStatics.BOOKING_DETAILS,transitBooking);
            ReservationService.makeReservation(transitBooking);
            EmailSender.sendEmail(loggedUser.getEmail(), "Booking Number : " + transitBooking.getBooking().getBookingNumber(),transitBooking.getBooking().getBookingNumber());
            response.sendRedirect("/bookingSummery");




        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/jsp/payment/payment.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute(APPStatics.SessionStatics.REDIRECT_PAGE_AFTER_LOGIN,"/bookingPayment");
        request.getRequestDispatcher("/jsp/payment/payment.jsp").forward(request, response);
    }
}
