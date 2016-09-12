package com.ars.service.flight;

import com.airline.webservice.*;
import com.ars.domain.airport.Airport;
import com.ars.domain.fair.Fair;
import com.ars.domain.rout.Routs;
import com.ars.domain.schedule.Schedule;
import com.ars.vo.transit.Transit;
import com.ars.domain.trip.Trip;
import com.ars.system.APPStatics;
import com.ars.util.parser.Parser;
import genarated.routs.Path;
import genarated.routs.Rout;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.lang.Exception;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by JJ on 5/15/16.
 */
public class FlightSearchService {

    public static List<Trip> getTrips(String sourceAirport, String destinationAirport, String departDate,
                                      int numberOfAdults, int numberOfChildren,int numberOfInfants,
                                      String travelClass, int maximumTransits) throws Exception {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = simpleDateFormat.parse(departDate);
        XMLGregorianCalendar departureDateXMLGCal = dateToXMLGregorianCalendar(date);

        Date startOfDay = new Date(0 - 330 * 60 * 1000); //minus timezone value +5:30
        XMLGregorianCalendar startOfDayXMLGCal = dateToXMLGregorianCalendar(startOfDay);

        Date endOfDay = new Date(86399999 - 330 * 60 * 1000);      //23:00:00 //minus timezone value +5:30
        XMLGregorianCalendar endOfDayXMLGCal = dateToXMLGregorianCalendar(endOfDay);

        Routs routsInstance = Routs.getRoutsInstance();
        //genarated.routs.Routs routs = routsInstance.getRouts();

        Rout rout = routsInstance.findRout(sourceAirport, destinationAirport);

        //If no special paths continue search for direct flights
        if (rout==null){
            rout = new Rout();
            rout.setFrom(sourceAirport);
            rout.setTo(destinationAirport);
            Path pathTemp = new Path();
            rout.getPath().add(pathTemp);
            pathTemp.getStop().add(sourceAirport);
            pathTemp.getStop().add(destinationAirport);
        }

        List<Path> paths = rout.getPath();


        //Combine schedules to prepare trips
        List<Trip> trips = new ArrayList<Trip>();

        Transit transit = null;
        List<Transit> transits = null;
        Trip trip = null;

        //to be used by all trips
        com.ars.domain.airport.Airport source = new Airport();
        source.setCode(sourceAirport);
        com.ars.domain.airport.Airport destination = new Airport();
        destination.setCode(destinationAirport);

        XMLGregorianCalendar minTimeXMLGCal = null;
        XMLGregorianCalendar maxTimeXMLGCal = null;
        XMLGregorianCalendar flightDepartureDateXMLGCal = null;

        //new code
        List<List<Schedule>> tempListOfListOfSchedule = null;
        List<Schedule> listOfSchedule = null;
        //identifier for trip for booking purpose
        int tripNumber = 1;
        for (Path path : paths) {
            if (path.getStop().size() <= maximumTransits + 2 || maximumTransits == -1) {
                tempListOfListOfSchedule = new ArrayList<List<Schedule>>();
                listOfSchedule = new ArrayList<Schedule>();
                List<String> stops = path.getStop();
                for (int i = 0; i <= stops.size() - 2; i++) {
                    //check if previous iteration list has any transit
                    if (i == 0 || listOfSchedule.size() != 0) {

                        if (i == 0) {
                            minTimeXMLGCal = startOfDayXMLGCal;
                            maxTimeXMLGCal = endOfDayXMLGCal;
                            flightDepartureDateXMLGCal = departureDateXMLGCal;
                        } else {
                            minTimeXMLGCal = dateToXMLGregorianCalendar(getMinimumArrivalTimeSchedule(listOfSchedule).getArrivalTime());
                            flightDepartureDateXMLGCal = dateToXMLGregorianCalendar(getMinimumArrivalTimeSchedule(listOfSchedule).getArrivalDate());
                            //plus fixed hours
                        }

                        //Webservice call to airline1
                        Airline1_Service airline1_service = new Airline1_Service();
                        Airline1 airline1 = airline1_service.getAirline1Port();
                        List<com.airline.webservice.Schedule> schedules1Airline1 = airline1.getScheduleList(stops.get(i), stops.get(i + 1), flightDepartureDateXMLGCal, minTimeXMLGCal, maxTimeXMLGCal, numberOfAdults + numberOfChildren, TravelClass.valueOf(travelClass));
                        List<Schedule> schedulesAirline1 = Parser.parseSchedules(schedules1Airline1, APPStatics.AirlineCompany.QATAR_AIRWAYS);

                        //Webservice call to airline2
                        Airline2_Service airline2_service = new Airline2_Service();
                        Airline2 airline2 = airline2_service.getAirline2Port();
                        List<com.airline.webservice.Schedule> schedules1Airline2 = airline2.getScheduleList(stops.get(i), stops.get(i + 1), flightDepartureDateXMLGCal, minTimeXMLGCal, maxTimeXMLGCal, numberOfAdults + numberOfChildren, TravelClass.valueOf(travelClass));
                        List<Schedule> schedulesAirline2 = Parser.parseSchedules(schedules1Airline2, APPStatics.AirlineCompany.MALASHIYA_AIRLINES);

                        //Webservice call to airline3
                        Airline3_Service airline3_service = new Airline3_Service();
                        Airline3 airline3 = airline3_service.getAirline3Port();
                        List<com.airline.webservice.Schedule> schedules1Airline3 = airline3.getScheduleList(stops.get(i), stops.get(i + 1), flightDepartureDateXMLGCal, minTimeXMLGCal, maxTimeXMLGCal, numberOfAdults + numberOfChildren, TravelClass.valueOf(travelClass));
                        List<Schedule> schedulesAirline3 = Parser.parseSchedules(schedules1Airline3, APPStatics.AirlineCompany.BRITISH_AIRWAYS);

                        //Webservice call to airline4
                        Airline4_Service airline4_service = new Airline4_Service();
                        Airline4 airline4 = airline4_service.getAirline4Port();
                        List<com.airline.webservice.Schedule> schedules1Airline4 = airline4.getScheduleList(stops.get(i), stops.get(i + 1), flightDepartureDateXMLGCal, minTimeXMLGCal, maxTimeXMLGCal, numberOfAdults + numberOfChildren, TravelClass.valueOf(travelClass));
                        List<Schedule> schedulesAirline4 = Parser.parseSchedules(schedules1Airline4, APPStatics.AirlineCompany.SRILANKAN_AIRLINE);

                        listOfSchedule = new ArrayList<Schedule>();
                        listOfSchedule.addAll(schedulesAirline1);
                        listOfSchedule.addAll(schedulesAirline2);
                        listOfSchedule.addAll(schedulesAirline3);
                        listOfSchedule.addAll(schedulesAirline4);

                        tempListOfListOfSchedule.add(listOfSchedule);

                    }
                }

                //prepare trip with transit list
                //generate list of schedule based on permutation and combination idea
                List<List<Schedule>> output = new ArrayList<List<Schedule>>();
                permutations(tempListOfListOfSchedule, output, 0, new ArrayList<Schedule>());


                //prepare trip list
                for (List<Schedule> schedules : output) {
                    float totalCost = 0;
                    trip = new Trip();
                    trip.setFrom(source);
                    trip.setTo(destination);
                    transits = new ArrayList<Transit>();
                    trip.setTransits(transits);
                    int i = 1;
                    for (Schedule schedule : schedules) {
                        transit = new Transit();
                        transit.setTransitNumber(i);
                        transit.setSchedule(schedule);
                        transits.add(transit);
                        List<Fair> fairs = schedule.getFairs();
                        Fair fair = null;
                        for (Fair fairTemp : fairs) {
                            String x = fairTemp.getTravelClass();
                            if (x.equals(TravelClass.valueOf(travelClass).toString())) {
                                fair = fairTemp;
                                totalCost = totalCost + numberOfAdults * fair.getAdultSeatPrice() + numberOfChildren * fair.getChildSeatPrice() + numberOfInfants * fair.getInfantCost();
                                break;
                            }
                        }
                        i++;
                    }
                    trip.setTotalCost(totalCost);
                    trip.setTripID(tripNumber);
                    tripNumber++;
                    trips.add(trip);
                }

            }
        }
        finalizeTrips(trips);
        return trips;

    }

    /**
     * Generate combination of all possible T s
     * @param ori
     * @param res
     * @param d
     * @param current
     * @param <T>
     */
    private static <T> void permutations(List<List<T>> ori, List<List<T>> res, int d, List<T> current) {
        // if depth equals number of original collections, final reached, add and return
        if (d == ori.size()) {
            res.add(current);
            return;
        }

        // iterate from current collection and copy 'current' element N times, one for each element
        List<T> currentCollection = ori.get(d);
        for (T element : currentCollection) {
            List<T> copy = new ArrayList<T>(current);
            copy.add(element);
            permutations(ori, res, d + 1, copy);
        }
    }

    /**
     * Remove trips with unsynced (arrival time greater than departure time) schedules
     * @param trips
     */
    public static void finalizeTrips(List<Trip> trips){
        Iterator<Trip> tripIterator = trips.iterator();
        while(tripIterator.hasNext()){
            Trip trip = tripIterator.next();
            List<Transit> transits = trip.getTransits();
            for(int i=0; i<transits.size()-1;i++){
                Transit transit1 = transits.get(i);
                Transit transit2 = transits.get(i+1);
                Schedule schedule1 = transit1.getSchedule();
                Schedule schedule2 = transit2.getSchedule();
                Calendar calendar1 = new GregorianCalendar();
                Calendar calendar2 = new GregorianCalendar();
                calendar1.set(schedule1.getArrivalDate().getYear(), schedule1.getArrivalDate().getMonth(), schedule1.getArrivalDate().getDate(), schedule1.getArrivalTime().getHours(), schedule1.getArrivalTime().getMinutes());
                calendar2.set(schedule2.getDepartDate().getYear(), schedule2.getDepartDate().getMonth(), schedule2.getDepartDate().getDate(), schedule2.getDepartTime().getHours(), schedule2.getDepartTime().getMinutes());
                if (calendar2.compareTo(calendar1) < 0) {
                    tripIterator.remove();
                    break;
                }
            }
        }
    }

    /**
     * returns the schedule with least arrival date and time
     * @param schedules
     * @return
     */
    public static Schedule getMinimumArrivalTimeSchedule(List<Schedule> schedules) {
        Schedule schedule = schedules.get(0);
        Calendar leastArrivalTime = new GregorianCalendar();
        Calendar leastArrivalTimeTemp = new GregorianCalendar();
        leastArrivalTime.set(schedule.getArrivalDate().getYear(), schedule.getArrivalDate().getMonth(), schedule.getArrivalDate().getDate(), schedule.getArrivalTime().getHours(), schedule.getArrivalTime().getMinutes());

        for (Schedule scheduleTemp : schedules) {
            leastArrivalTimeTemp.set(scheduleTemp.getArrivalDate().getYear(), scheduleTemp.getArrivalDate().getMonth(), scheduleTemp.getArrivalDate().getDate(), scheduleTemp.getArrivalTime().getHours(), scheduleTemp.getArrivalTime().getMinutes());
            if (leastArrivalTimeTemp.compareTo(leastArrivalTime) < 0) {
                schedule = scheduleTemp;
            }
        }

        return schedule;
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
}
