package com.ars.system;

/**
 * Created by JJ on 4/12/16.
 */
public class APPStatics {
    //database statics
    public static String postgresDriver = "org.postgresql.Driver";
    public static String databaseURL = "jdbc:postgresql://localhost:5432/ars";
    public static String schemaName = "ars";
    public static String userName = "postgres";
    public static String password = "admin";


    public class RequestStatics{
        //Request Statics
        public static final String ERROR_MESSAGE = "errorMessage";
        public static final String SUCCESS_MESSAGE = "successMessage";
        public static final String INFORMATION_MESSAGE = "informationMessage";

        public static final String FAQS = "faqs";

        public static final String EMPLOYEE_SEARCH_RESULT = "employeeSearchResult";
        public static final String BOOKING_SEARCH_RESULT = "bookingSearchResult";

        public static final String UP_TRIP_SCHEDULES =  "upTripSchedules";
        public static final String DOWN_TRIP_SCHEDULES =  "downTripSchedules";
        public static final String TICKET_SEARCH_RESULT =  "ticketSearchResult";
        public static final String TICKET_GETTING_CANCELLED =  "ticketListGettingCancelled";
        public static final String CANCELLING_TICKET_NUMBER =  "cancellingTicketNumber";

        public static final String NUMBER_OF_PAGES =  "numberOfPages";

        //Statics required for identifying first logging and reset password
        public static final String EMAIL_CONFIRMATION =  "emailConf";
        public static final String ONETIME_PASSWORD =  "oneTimePW";
        public static final String USER_NAME =  "username";
        public static final String PASSWORD =  "password";






    }

    public class SessionStatics{
        public static final String ERROR_MESSAGE = "errorMessage";
        public static final String LOGGED_USER = "loggedUser";

        //For booking process
        public static final String NUMBER_OF_ADULT_PASSENGERS = "numberOfAdultPassengers";
        public static final String NUMBER_OF_CHILD_PASSENGERS = "numberOfChildPassengers";
        public static final String NUMBER_OF_INFANT_PASSENGERS = "numberOfInfantPassengers";
        public static final String TRIP_TYPE = "tripType";
        public static final String TRAVEL_CLASS = "travelClass";
        public static final String SELECTED_UP_TRIP_SCHEDULE_ID = "selectedUpTripScheduleID";
        public static final String SELECTED_UP_TRIP_SCHEDULE_JAVA = "selectedUpTripScheduleIDJava";
        public static final String SELECTED_UP_TRIP_AIRLINE = "selectedUpTripAirline";
        public static final String SELECTED_DOWN_TRIP_SCHEDULE_JAVA = "selectedDownTripScheduleIDJava";
        public static final String SELECTED_DOWN_TRIP_SCHEDULE_ID = "selectedDownTripScheduleID";
        public static final String SELECTED_DOWN_TRIP_AIRLINE = "selectedDownTripAirline";
        public static final String PASSENGER_LIST = "passengerList";
        public static final String ADULT_LIST = "adultPassengerList";
        public static final String CHILD_LIST = "childPassengerList";
        public static final String INFANT_LIST = "infantPassengerList";
        public static final String CONTACT_PERSON = "contactPerson";

        public static final String SOURCE_AIRPORT = "sourceAirport";
        public static final String DESTINATION_AIRPORT = "destinationAirport";
        public static final String UP_TRIP_DEPARTURE_DATE = "upTripDepartureDate";
        public static final String UP_TRIP_DEPARTURE_TIME = "upTripDepartureTime";
        public static final String UP_TRIP_ARRIVAL_DATE = "upTripArrivalDate";
        public static final String UP_TRIP_ARRIVAL_TIME = "upTripArrivalTime";
        public static final String DOWN_TRIP_DEPARTURE_DATE = "downTripDepartureDate";
        public static final String DOWN_TRIP_DEPARTURE_TIME = "downTripDepartureTime";
        public static final String DOWN_TRIP_ARRIVAL_DATE = "downTripArrivalDate";
        public static final String DOWN_TRIP_ARRIVAL_TIME = "downTripArrivalTime";
        public static final String MAXIMUM_TRANSIT = "maximumTransit";

        public static final String SERVICE_CHARGE= "serviceCharge";



        public static final String UP_TRIP_SCHEDULES_JAVA_LIST =  "upTripSchedulesJavaList";
        public static final String DOWN_TRIP_SCHEDULES_JAVA_LIST =  "downTripSchedulesJavaList";

        public static final String BOOKING_DETAILS =  "bookingDetails";



        public static final String REDIRECT_PAGE_AFTER_LOGIN =  "redirectAfterLogin";




    }

    public class PortalStatics{
        public static final String ADD_AIRCRAFT = "ADD_AIRCRAFT";
        public static final String VIEW_AIRCRAFT = "VIEW_AIRCRAFT";
        public static final String UPDATE_AIRCRAFT = "UPDATE_AIRCRAFT";
        public static final String DELETE_AIRCRAFT = "DELETE_AIRCRAFT";
        public static final String MANAGE_AIRCRAFT = "MANAGE_AIRCRAFT";

        public static final String ADD_FLIGHT = "ADD_FLIGHT";
        public static final String MANAGE_FLIGHT = "MANAGE_FLIGHT";
        public static final String DELETE_FLIGHT = "DELETE_FLIGHT";
        public static final String UPDATE_FLIGHT = "UPDATE_FLIGHT";
        public static final String VIEW_FLIGHT = "VIEW_FLIGHT";

        public static final String ADD_SCHEDULE = "ADD_SCHEDULE";
        public static final String DELETE_SCHEDULE = "DELETE_SCHEDULE";
        public static final String UPDATE_SCHEDULE = "UPDATE_SCHEDULE";
        public static final String VIEW_SCHEDULE = "VIEW_SCHEDULE";
        public static final String MANAGE_SCHEDULE = "MANAGE_SCHEDULE";

        public static final String DELETE_AIRPORT = "DELETE_AIRPORT";
        public static final String ADD_AIRPORT = "ADD_AIRPORT";
        public static final String MANAGE_AIRPORT = "MANAGE_AIRPORT";
        public static final String UPDATE_AIRPORT = "UPDATE_AIRPORT";
        public static final String VIEW_AIRPORT = "VIEW_AIRPORT";

        public static final String FIRST_LOGIN_UPDATE = "FIRST_LOGIN_UPDATE";
        public static final String LOGIN = "LOGIN";
        public static final String MANAGE_PASSWORD = "MANAGE_PASSWORD";


        public static final String EMPLOYEE_ADD = "EMPLOYEE_ADD";
        public static final String EMPLOYEE_DELETE = "EMPLOYEE_DELETE";
        public static final String EMPLOYEE_UPDATE = "EMPLOYEE_UPDATE";
        public static final String EMPLOYEE_VIEW = "EMPLOYEE_VIEW";

        public static final String FAQ_QUESTION_ADD = "FAQ_QUESTION_ADD";
        public static final String FAQ_QUESTION_DELETE = "FAQ_QUESTION_DELETE";
        public static final String FAQ_QUESTION_UPDATE = "FAQ_QUESTION_UPDATE";
        public static final String FAQ_QUESTION_VIEW = "FAQ_QUESTION_VIEW";

        public static final String FAQ_ANSWER_ADD = "FAQ_ANSWER_ADD";
        public static final String FAQ_ANSWER_DELETE = "FAQ_ANSWER_DELETE";
        public static final String FAQ_ANSWER_UPDATE = "FAQ_ANSWER_UPDATE";
        public static final String FAQ_ANSWER_VIEW = "FAQ_ANSWER_VIEW";

        public static final String APPROVE_TICKET_CANCELLATION = "APPROVE_TICKET_CANCELLATION_REQ";

    }

    public class DBErrorCodes{
        public static final String UNIQUE_KEY_VIOLATION = "23505";
        public static final String INVALID_CURSOR_STATE = "24000";
        public static final String STRING_LENGTH_TOO_LONG = "22001";
    }
    public class AirlineCompany{
        public static final String SRILANKAN_AIRLINE = "Srilankan Airlines";
        public static final String QATAR_AIRWAYS = "Qatar Airways";
        public static final String BRITISH_AIRWAYS = "British Airways";
        public static final String MALASHIYA_AIRLINES = "Malaysia Airlines";
    }

    public class MailServerStatics{
        public static final String SENDER = "airlinereservationcompany@gmail.com";
        public static final String PASSWORD = "1qaz2wsx#";
    }

    public class Finance{
        public static final float SERVICE_CHARGE_RATE = 0.08f;
    }

}