package com.ars.util.parser;

import com.airline.webservice.Gender;
import com.airline.webservice.PassengerType;
import com.airline.webservice.Tickets;
import com.ars.domain.airport.Airport;
import com.ars.domain.fair.Fair;
import com.ars.domain.flight.Flight;
import com.ars.domain.passenger.Passenger;
import com.ars.domain.schedule.Schedule;
import com.ars.domain.ticket.Ticket;
import com.ars.util.common.Helper;
import com.ars.util.enums.TicketStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JJ on 5/15/16.
 */
public class Parser {
    /**
     * Convert {@link com.airline.webservice.Schedule} to {@link com.ars.domain.schedule.Schedule}
     * @return {@link com.ars.domain.schedule.Schedule}
     */
    public static Schedule parseSchedule(com.airline.webservice.Schedule schedule, String airlineCompany){
        Schedule schedule1 = new Schedule();
        schedule1.setAircraftNumber(schedule.getAircraftNumber());
        schedule1.setAirlineCompany(airlineCompany);
        schedule1.setArrivalDate(schedule.getArrivalDate().toGregorianCalendar().getTime());
        schedule1.setArrivalTime(schedule.getArrivalTime().toGregorianCalendar().getTime());
        schedule1.setDepartDate(schedule.getDepartDate().toGregorianCalendar().getTime());
        schedule1.setDepartTime(schedule.getDepartTime().toGregorianCalendar().getTime());
        schedule1.setFairs(parseFairs(schedule.getFairs()));
        schedule1.setFlight(parseFlight(schedule.getFlight()));
        //schedule1.setNumOfAvailableBusinessSeats(schedule.getNumOfAvailableBusinessSeats());
        //schedule1.setNumOfAvailableEconomySeats(schedule.getNumOfAvailableEconomySeats());
        //schedule1.setNumOfAvailableFirstClassSeats(schedule.getNumOfAvailableFirstClassSeats());
        schedule1.setScheduleId(schedule.getScheduleId());
        return schedule1;
    }

    public static com.airline.webservice.Schedule parseSchedule(Schedule schedule) throws Exception {
        com.airline.webservice.Schedule schedule1 = new com.airline.webservice.Schedule();
        schedule1.setAircraftNumber(schedule.getAircraftNumber());
        schedule1.setArrivalDate(Helper.dateToXMLGregorianCalendar(schedule.getArrivalDate()));
        schedule1.setArrivalTime(Helper.dateToXMLGregorianCalendar(schedule.getArrivalTime()));
        schedule1.setDepartDate(Helper.dateToXMLGregorianCalendar(schedule.getDepartDate()));
        schedule1.setDepartTime(Helper.dateToXMLGregorianCalendar(schedule.getDepartTime()));
        for(Fair fair : schedule.getFairs()){
            schedule1.getFairs().add(parseFair(fair));
        }
        schedule1.setFlight(parseFlight(schedule.getFlight()));
        /*schedule1.setNumOfAvailableBusinessSeats(schedule.getNumOfAvailableBusinessSeats());
        schedule1.setNumOfAvailableEconomySeats(schedule.getNumOfAvailableEconomySeats());
        schedule1.setNumOfAvailableFirstClassSeats(schedule.getNumOfAvailableFirstClassSeats());*/
        schedule1.setScheduleId(schedule.getScheduleId());
        return schedule1;
    }

    /**
     * Convert list of {@link com.airline.webservice.Schedule} to list of {@link com.ars.domain.schedule.Schedule}
     * @return list of {@link com.ars.domain.schedule.Schedule}
     */
    public static List<Schedule> parseSchedules(List<com.airline.webservice.Schedule> schedules, String airlineCompany){
        List<Schedule> schedules1 = new ArrayList<Schedule>();
        for (com.airline.webservice.Schedule schedule : schedules){
             schedules1.add(parseSchedule(schedule,airlineCompany));
        }
        return schedules1;
    }

    /**
     * Convert {@link com.airline.webservice.Flight} to {@link com.ars.domain.flight.Flight}
     * @return {@link com.ars.domain.flight.Flight}
     */
    public static Flight parseFlight(com.airline.webservice.Flight flight){
        Flight flight1 = new Flight();
        flight1.setFromAirport(parseAirport(flight.getFromAirport()));
        flight1.setToAirport(parseAirport(flight.getToAirport()));
        flight1.setFlightNumber(flight.getFlightNumber());
        return flight1;
    }

    /**
     * Convert {@link com.ars.domain.flight.Flight} to {@link com.airline.webservice.Flight}
     * @param flight
     * @return
     */
    public static com.airline.webservice.Flight parseFlight(Flight flight){
        com.airline.webservice.Flight flight1 = new com.airline.webservice.Flight();
        flight1.setFromAirport(parseAirport(flight.getFromAirport()));
        flight1.setToAirport(parseAirport(flight.getToAirport()));
        flight1.setFlightNumber(flight.getFlightNumber());
        return flight1;
    }

    /**
     * Convert {@link com.airline.webservice.Airline1} to {@link com.ars.domain.airport.Airport}
     * @return {@link com.ars.domain.airport.Airport}
     */
    public static Airport parseAirport(com.airline.webservice.Airport airport){
        Airport airport1 = new Airport();
        airport1.setName(airport.getName());
        airport1.setCode(airport.getCode());
        airport1.setCity(airport.getCity());
        airport1.setCountry(airport.getCountry());
        return airport1;
    }

    public static com.airline.webservice.Airport parseAirport(Airport airport){
        com.airline.webservice.Airport airport1 = new com.airline.webservice.Airport();
        airport1.setName(airport.getName());
        airport1.setCode(airport.getCode());
        airport1.setCity(airport.getCity());
        airport1.setCountry(airport.getCountry());
        return airport1;
    }

    /**
     * Convert {@link com.airline.webservice.Airline1} to {@link com.ars.domain.airport.Airport}
     * @return {@link com.ars.domain.airport.Airport}
     */
    public static Fair parseFair(com.airline.webservice.Fair fair){
        Fair fair1 = new Fair();
        fair1.setTravelClass(fair.getTravelClass());
        fair1.setChildSeatPrice(fair.getChildSeatPrice());
        fair1.setAdultSeatPrice(fair.getAdultSeatPrice());
        fair1.setInfantCost(fair.getInfantCost());
        return fair1;
    }
    /**
     * Convert {@link com.ars.domain.airport.Airport} to {@link com.airline.webservice.Airline1}
     * @return {@link com.ars.domain.airport.Airport}
     */
    public static com.airline.webservice.Fair parseFair(Fair fair){
        com.airline.webservice.Fair fair1 = new com.airline.webservice.Fair();
        fair1.setTravelClass(fair.getTravelClass());
        fair1.setChildSeatPrice(fair.getChildSeatPrice());
        fair1.setAdultSeatPrice(fair.getAdultSeatPrice());
        fair1.setInfantCost(fair.getInfantCost());
        return fair1;
    }


    /**
     * Convert list of {@link com.ars.domain.fair.Fair} to list of {@link com.airline.webservice.Fair}
     * @return list of {@link com.ars.domain.flight.Flight}
     */
    public static List<com.airline.webservice.Fair> parseFairs2(List<Fair> fairs){
        List<com.airline.webservice.Fair> fairs1 = new ArrayList<com.airline.webservice.Fair>();
        for (Fair fair : fairs){
            fairs1.add(parseFair(fair));
        }
        return fairs1;
    }


    /**
     * Convert list of {@link com.airline.webservice.Fair} to list of {@link com.ars.domain.fair.Fair}
     * @return list of {@link com.ars.domain.flight.Flight}
     */
    public static List<Fair> parseFairs(List<com.airline.webservice.Fair> fairs){
        List<Fair> fairs1 = new ArrayList<Fair>();
        for (com.airline.webservice.Fair fair : fairs){
            fairs1.add(parseFair(fair));
        }
        return fairs1;
    }

    public static List<com.airline.webservice.Passenger> parsePassengerList(List<Passenger> passengers) throws Exception {
        List<com.airline.webservice.Passenger> passengers1 = new ArrayList<com.airline.webservice.Passenger>();
        for (Passenger passenger : passengers){
            passengers1.add(parsePassenger(passenger));
        }
        return passengers1;
    }

    public static com.airline.webservice.Passenger parsePassenger(Passenger passenger) throws Exception {
        com.airline.webservice.Passenger passenger1 = new com.airline.webservice.Passenger();
        passenger1.setGender(Gender.valueOf(passenger.getGender().toString()));
        passenger1.setDateOfBirth(Helper.dateToXMLGregorianCalendar(passenger.getDateOfBirth()));
        passenger1.setFirstName(passenger.getFirstName());
        passenger1.setLastName(passenger.getLastName());
        passenger1.setOtherName(passenger.getOtherName());
        passenger1.setPassengerType(PassengerType.valueOf(passenger.getPassengerType().toString()));
        passenger1.setPassportNumber(passenger.getPassportNumber());
        return passenger1;
    }

    public static Passenger parsePassenger(com.airline.webservice.Passenger passenger) throws Exception {
        Passenger passenger1 = new Passenger();
        passenger1.setGender(com.ars.util.enums.Gender.valueOf(passenger.getGender().toString()));
        passenger1.setDateOfBirth(Helper.xMLGregorianCalendarToDate(passenger.getDateOfBirth()));
        passenger1.setFirstName(passenger.getFirstName());
        passenger1.setLastName(passenger.getLastName());
        passenger1.setOtherName(passenger.getOtherName());
        passenger1.setPassengerType(com.ars.util.enums.PassengerType.valueOf(passenger.getPassengerType().toString()));
        passenger1.setPassportNumber(passenger.getPassportNumber());
        return passenger1;
    }

    public static Ticket parseTicket(com.airline.webservice.Ticket ticket) throws Exception {
        Ticket ticket1 = new Ticket();
        ticket1.setTicketNumber(ticket.getTicketNumber());
        ticket1.setSeatNumber(ticket.getSeatNumber());
        ticket1.setStatus(TicketStatus.valueOf(ticket.getStatus().toString()));
        ticket1.setPassenger(parsePassenger(ticket.getPassenger()));
        if(ticket.getParentTicket()!=null){
            ticket1.setParentTicket(parseTicket(ticket.getParentTicket()));
        }
        return ticket1;
    }

    public static List<Ticket> parseTickets(List<com.airline.webservice.Ticket> tickets) throws Exception {
        List<Ticket> tickets1 = new ArrayList<Ticket>();
        for (com.airline.webservice.Ticket ticket : tickets){
            tickets1.add(parseTicket(ticket));
        }
        return tickets1;
    }

}
