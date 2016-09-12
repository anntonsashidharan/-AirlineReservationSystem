package com.ars.controller.flight;

import com.ars.system.APPStatics;
import com.ars.util.validation.Validation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;
import java.util.Date;

/**
 * Created by JJ on 4/23/16.
 */
@WebServlet(urlPatterns = "/searchFlight")
public class SearchFlight extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String source=null,destination=null;
            XMLGregorianCalendar departDate=null, arrivalDate=null;
            String oneWayOrRound=null;
            int onlyDirectFlightFlag = 0; // 1 if only direct flight else 0
            int numberOfAdult = 0;
            int numberOfChild = 0;
            int numberOfInfant = 0;

            Validation.validateFlightSearch(source, destination, departDate, arrivalDate, oneWayOrRound, numberOfAdult, numberOfChild, numberOfInfant);



        } catch (Exception e){

        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute(APPStatics.SessionStatics.REDIRECT_PAGE_AFTER_LOGIN,"/searchFlight");
    }
}
