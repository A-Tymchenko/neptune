package com.ra.airport.helper;

import com.ra.airport.entity.Flight;
import com.ra.airport.entity.Ticket;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class DataCreationHelper {

    private static final String SPACE = " ";

    public static Flight createFlight() {
        Flight flight = new Flight();
        flight.setIdentifier(1);
        flight.setName(SPACE);
        flight.setCarrier(SPACE);
        flight.setDuration(LocalTime.NOON);
        flight.setFare(Double.MIN_VALUE);
        flight.setMealOn(true);
        flight.setDepartureDate(LocalDateTime.MIN);
        flight.setArrivalDate(LocalDateTime.MAX);
        return flight;
    }

    public static Ticket createTicket() {
        Ticket ticket = new Ticket();
        ticket.setIdTicket(1);
        ticket.setTicketNumber("AA111-BB111");
        ticket.setPassengerName("John Dow");
        ticket.setDocument("QQ12345678QQ");
        ticket.setSellingDate(Timestamp.valueOf("2018-10-24 08:00:00"));
        return ticket;
    }
}
