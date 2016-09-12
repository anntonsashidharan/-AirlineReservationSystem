package com.ars.controller;

import com.airline.webservice.*;
import com.ars.dao.airport.AirportDAO;
import com.ars.dao.transit.TransitBookingDAO;
import com.ars.domain.airport.Airport;
import com.ars.domain.airport.Airports;
import com.ars.domain.transit.TransitBooking;
import com.ars.domain.trip.Trip;
import com.ars.service.airport.AirportService;
import com.ars.service.booking.TransitBookingService;
import com.ars.service.flight.FlightSearchService;
import com.ars.system.APPStatics;
import com.ars.util.db.Transaction;
import com.ars.util.email.EmailSender;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;
import java.lang.Exception;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by JJ on 4/24/16.
 */
@WebServlet(urlPatterns = "/main")
public class IndexController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            //Empty session variables
            request.getSession().setAttribute(APPStatics.RequestStatics.UP_TRIP_SCHEDULES, null);
            request.getSession().setAttribute(APPStatics.RequestStatics.DOWN_TRIP_SCHEDULES, null);

            String tripeType = request.getParameter("tripType");
            String sourceAirport = request.getParameter("sourceAirport");
            String destinationAirport = request.getParameter("destinationAirport");
            String departDateUpTrip = request.getParameter("departDateUpTrip");
            String departDateDownTrip = request.getParameter("departDateDownTrip");
            int numberOfAdults = Integer.valueOf(request.getParameter("numberOfAdults"));
            int numberOfChildren = Integer.valueOf(request.getParameter("numberOfChildren"));
            int numberOfInfants = Integer.valueOf(request.getParameter("numberOfInfants"));
            int maximumTransits = Integer.valueOf(request.getParameter("maximumTransits"));
            String travelClass = request.getParameter("travelClass");
            String search = request.getParameter("search");


            List<Trip> upTrips = FlightSearchService.getTrips(sourceAirport, destinationAirport, departDateUpTrip, numberOfAdults, numberOfChildren, numberOfInfants, travelClass, maximumTransits);
            request.getSession().setAttribute(APPStatics.SessionStatics.UP_TRIP_SCHEDULES_JAVA_LIST, upTrips);
            String upTripsJson = new Gson().toJson(upTrips);
            request.getSession().setAttribute(APPStatics.RequestStatics.UP_TRIP_SCHEDULES, upTripsJson);

            if ("round".equalsIgnoreCase(tripeType)) {
                List<Trip> downTrips = FlightSearchService.getTrips(destinationAirport, sourceAirport, departDateDownTrip, numberOfAdults, numberOfChildren, numberOfInfants, travelClass, maximumTransits);
                request.getSession().setAttribute(APPStatics.SessionStatics.DOWN_TRIP_SCHEDULES_JAVA_LIST, downTrips);
                String downTripsJson = new Gson().toJson(downTrips);
                request.getSession().setAttribute(APPStatics.RequestStatics.DOWN_TRIP_SCHEDULES, downTripsJson);
            }

            request.getSession().setAttribute(APPStatics.SessionStatics.NUMBER_OF_ADULT_PASSENGERS, numberOfAdults);
            request.getSession().setAttribute(APPStatics.SessionStatics.NUMBER_OF_CHILD_PASSENGERS, numberOfChildren);
            request.getSession().setAttribute(APPStatics.SessionStatics.NUMBER_OF_INFANT_PASSENGERS, numberOfInfants);
            request.getSession().setAttribute(APPStatics.SessionStatics.TRAVEL_CLASS, travelClass);
            request.getSession().setAttribute(APPStatics.SessionStatics.TRIP_TYPE, tripeType);
            request.getSession().setAttribute(APPStatics.SessionStatics.SOURCE_AIRPORT, sourceAirport);
            request.getSession().setAttribute(APPStatics.SessionStatics.DESTINATION_AIRPORT, destinationAirport);
            request.getSession().setAttribute(APPStatics.SessionStatics.UP_TRIP_DEPARTURE_DATE, departDateUpTrip);
            request.getSession().setAttribute(APPStatics.SessionStatics.DOWN_TRIP_DEPARTURE_DATE, departDateDownTrip);
            request.getSession().setAttribute(APPStatics.SessionStatics.MAXIMUM_TRANSIT, maximumTransits);

            response.sendRedirect("/flightSearchResult");
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/indexPage.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        request.getSession().setAttribute(APPStatics.SessionStatics.REDIRECT_PAGE_AFTER_LOGIN, "/main");
        try {
            HashMap<String, String> airports = Airports.getAirportsInstance().getAirports();
            String json = new Gson().toJson(airports);
            request.getSession().setAttribute("airportList", json);
            request.getRequestDispatcher("/indexPage.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/indexPage.jsp").forward(request, response);
        }

    }
}
