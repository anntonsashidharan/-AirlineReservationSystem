package com.ars.controller.passenger;

import com.ars.domain.contactPerson.ContactPerson;
import com.ars.util.enums.Gender;
import com.ars.util.enums.PassengerType;
import com.ars.domain.passenger.Passenger;
import com.ars.system.APPStatics;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by JJ on 8/26/16.
 */
@WebServlet("/passenger/addPassenger")
public class PassengerController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int numberOfAdults = Integer.parseInt(request.getSession().getAttribute(APPStatics.SessionStatics.NUMBER_OF_ADULT_PASSENGERS).toString());
            int numberOfChildren = Integer.parseInt(request.getSession().getAttribute(APPStatics.SessionStatics.NUMBER_OF_CHILD_PASSENGERS).toString());
            int numberOfInfants = Integer.parseInt(request.getSession().getAttribute(APPStatics.SessionStatics.NUMBER_OF_INFANT_PASSENGERS).toString());
            List<Passenger> passengers = new ArrayList<Passenger>();
            String firstName;
            String lastName;
            String otherName;
            String dateOfBirth;
            String gender;
            String passportNumber;
            String email;
            String addressLine1;
            String addressLine2;
            String addressLine3;
            String country;
            String contactNumber1;
            String contactNumber2;

            DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            Date dob = null;

            Passenger passenger = null;
            for (int i = 1; i <= numberOfAdults; i++) {
                passenger = new Passenger();
                firstName = request.getParameter("adultFirstName" + i);
                lastName = request.getParameter("adultLastName" + i);
                otherName = request.getParameter("adultOtherName" + i);
                dateOfBirth = request.getParameter("adultDateOfBirth" + i);
                gender = request.getParameter("adultGender" + i);
                passportNumber = request.getParameter("adultPassportNumber" + i);
                passenger.setFirstName(firstName);
                passenger.setLastName(lastName);
                passenger.setOtherName(otherName);
                dob = (Date) formatter.parse(dateOfBirth);
                passenger.setDateOfBirth(dob);
                if (gender.equalsIgnoreCase("male")) {
                    passenger.setGender(Gender.MALE);
                } else {
                    passenger.setGender(Gender.FEMALE);
                }
                passenger.setPassportNumber(passportNumber);
                passenger.setPassengerType(PassengerType.ADULT);
                passengers.add(passenger);
            }

            request.getSession().setAttribute(APPStatics.SessionStatics.ADULT_LIST,passengers);
            passengers = new ArrayList<Passenger>();

            for (int i = 1; i <= numberOfChildren; i++) {
                passenger = new Passenger();
                firstName = request.getParameter("childFirstName" + i);
                lastName = request.getParameter("childLastName" + i);
                otherName = request.getParameter("childOtherName" + i);
                dateOfBirth = request.getParameter("childDateOfBirth" + i);
                gender = request.getParameter("childGender" + i);
                passenger.setFirstName(firstName);
                passenger.setLastName(lastName);
                passenger.setOtherName(otherName);
                dob = (Date) formatter.parse(dateOfBirth);
                passenger.setDateOfBirth(dob);
                if (gender.equalsIgnoreCase("male")) {
                    passenger.setGender(Gender.MALE);
                } else {
                    passenger.setGender(Gender.FEMALE);
                }
                passenger.setPassengerType(PassengerType.CHILD);
                passengers.add(passenger);
            }

            request.getSession().setAttribute(APPStatics.SessionStatics.CHILD_LIST,passengers);
            passengers = new ArrayList<Passenger>();

            for (int i = 1; i <= numberOfInfants; i++) {
                passenger = new Passenger();
                firstName = request.getParameter("infantFirstName" + i);
                lastName = request.getParameter("infantLastName" + i);
                otherName = request.getParameter("infantOtherName" + i);
                dateOfBirth = request.getParameter("infantDateOfBirth" + i);
                gender = request.getParameter("infantGender" + i);
                passenger.setFirstName(firstName);
                passenger.setLastName(lastName);
                passenger.setOtherName(otherName);
                dob = formatter.parse(dateOfBirth);
                passenger.setDateOfBirth(dob);
                if (gender.equalsIgnoreCase("male")) {
                    passenger.setGender(Gender.MALE);
                } else {
                    passenger.setGender(Gender.FEMALE);
                }
                passenger.setPassengerType(PassengerType.INFANT);
                passengers.add(passenger);
            }

            request.getSession().setAttribute(APPStatics.SessionStatics.INFANT_LIST,passengers);
            //passengers = new ArrayList<Passenger>();

            ContactPerson contactPerson = new ContactPerson();
            firstName = request.getParameter("contactFirstName");
            lastName = request.getParameter("contactLastName");
            otherName = request.getParameter("contactOtherName");
            email = request.getParameter("contactEmail");
            addressLine1 = request.getParameter("contactAddressLine1");
            addressLine2 = request.getParameter("contactAddressLine2");
            addressLine3 = request.getParameter("contactAddressLine3");
            country = request.getParameter("contactAddressLineCountry");
            contactNumber1 = request.getParameter("contactNumber1");
            contactNumber2 = request.getParameter("contactNumber2");
            contactPerson.setFirstName(firstName);
            contactPerson.setLastName(lastName);
            contactPerson.setOtherName(otherName);
            contactPerson.setEmail(email);
            contactPerson.setAddressLine1(addressLine1);
            contactPerson.setAddressLine2(addressLine2);
            contactPerson.setAddressLine3(addressLine3);
            contactPerson.setCountry(country);
            contactPerson.setContactNumber1(contactNumber1);
            contactPerson.setContactNumber2(contactNumber2);


//            request.getSession().setAttribute(APPStatics.SessionStatics.PASSENGER_LIST,passenger);
            request.getSession().setAttribute(APPStatics.SessionStatics.CONTACT_PERSON,contactPerson);

            response.sendRedirect("/bookingPayment");

        } catch (Exception e){
            request.setAttribute("errorMessage",e.getMessage());
            request.getRequestDispatcher("/jsp/passenger/addPassenger.jsp").forward(request,response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute(APPStatics.SessionStatics.REDIRECT_PAGE_AFTER_LOGIN,"/passenger/addPassenger");
        request.getRequestDispatcher("/jsp/passenger/addPassenger.jsp").forward(request, response);
    }
}
