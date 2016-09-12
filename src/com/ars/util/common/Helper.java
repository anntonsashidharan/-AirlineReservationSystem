package com.ars.util.common;

import com.ars.domain.ticket.Ticket;
import com.ars.domain.trip.Trip;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by JJ on 8/30/16.
 */
public class Helper {

    /**
     * returns the trip with given trip id else returns null
     * @param tripID trip id to search
     * @param trips list of trips
     * @return
     */
    public static Trip getTrip(int tripID, List<Trip> trips){
        for (Trip trip : trips){
            if(tripID == trip.getTripID()){
                return trip;
            }
        }
        return null;
    }


    /**
     * converts date to xmlGregorianCalendar
     *
     * @param date
     * @return
     */
    public static XMLGregorianCalendar dateToXMLGregorianCalendar(Date date) throws Exception {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
    }

    public static Date xMLGregorianCalendarToDate(XMLGregorianCalendar date) throws Exception {
        Date date1 = new Date(date.getMillisecond());
        return date1;
    }

    public static Ticket findTicket(List<Ticket> tickets, String ticketNumber) {
        for(Ticket ticket : tickets){
            if(ticket.getTicketNumber().equals(ticketNumber));
            return ticket;
        }
        return null;
    }
}
