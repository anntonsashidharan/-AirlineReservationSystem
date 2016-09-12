package com.ars.controller.flightSearchResult;

import com.ars.domain.trip.Trip;
import com.ars.domain.user.UserLogin;
import com.ars.system.APPStatics;
import com.ars.util.common.Helper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JJ on 5/14/16.
 */
@WebServlet(urlPatterns = "/flightSearchResult")
public class FlightSearchResultController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        float serviceCharge;
        String upTripID = request.getParameter("upTripID");
        String downTripID = request.getParameter("downTripID");
        String tripType = (String) request.getSession().getAttribute(APPStatics.SessionStatics.TRIP_TYPE);
        List<Trip> upTrips = (ArrayList<Trip>)request.getSession().getAttribute(APPStatics.SessionStatics.UP_TRIP_SCHEDULES_JAVA_LIST);
        List<Trip> downTrips = (ArrayList<Trip>)request.getSession().getAttribute(APPStatics.SessionStatics.DOWN_TRIP_SCHEDULES_JAVA_LIST);


        try {
            UserLogin loggedUser = (UserLogin) request.getSession().getAttribute(APPStatics.SessionStatics.LOGGED_USER);
            if(loggedUser==null){
                request.setAttribute("errorMessage","Please login to proceed with booking");
                request.getRequestDispatcher("/jsp/flightSearchResult/flightSearchResult.jsp").forward(request, response);
                return;

            }
            if (upTripID == null || "".equals(upTripID)) {
                throw new Exception("Please select an outbound schedule");
            }else {
                request.getSession().setAttribute(APPStatics.SessionStatics.SELECTED_UP_TRIP_SCHEDULE_ID, upTripID);
                request.getSession().setAttribute(APPStatics.SessionStatics.SELECTED_UP_TRIP_SCHEDULE_JAVA, Helper.getTrip(Integer.parseInt(upTripID),upTrips));
                serviceCharge = Helper.getTrip(Integer.parseInt(upTripID),upTrips).getTotalCost() * APPStatics.Finance.SERVICE_CHARGE_RATE;
            }

            if ("round".equalsIgnoreCase(tripType)) {
                if (downTripID == null || "".equals(downTripID)) {
                    throw new Exception("Please select an inbound schedule");
                }else{

                    request.getSession().setAttribute(APPStatics.SessionStatics.SELECTED_DOWN_TRIP_SCHEDULE_ID, downTripID);
                    request.getSession().setAttribute(APPStatics.SessionStatics.SELECTED_DOWN_TRIP_SCHEDULE_JAVA, Helper.getTrip(Integer.parseInt(downTripID),downTrips));
                    serviceCharge = serviceCharge + Helper.getTrip(Integer.parseInt(downTripID),downTrips).getTotalCost()*APPStatics.Finance.SERVICE_CHARGE_RATE;
                }
            }
            request.getSession().setAttribute(APPStatics.SessionStatics.SERVICE_CHARGE,serviceCharge);

            response.sendRedirect("/passenger/addPassenger");

        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/jsp/flightSearchResult/flightSearchResult.jsp").forward(request, response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute(APPStatics.SessionStatics.REDIRECT_PAGE_AFTER_LOGIN, "/flightSearchResult");
        request.getRequestDispatcher("/jsp/flightSearchResult/flightSearchResult.jsp").forward(request, response);
    }
}
