package com.ra.airport;

import com.ra.airport.entity.Flight;

import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class AbstractTest {

    private static final String EMPTY = "";
    private static final String SPACE = " ";

    protected Flight createFirstFlight() {
        Flight flight = new Flight();
        flight.setId(1);
        flight.setName(EMPTY);
        flight.setCarrier(EMPTY);
        flight.setDuration(LocalTime.MIDNIGHT);
        flight.setFare(Double.MIN_VALUE);
        flight.setMealOn(false);
        flight.setDepartureDate(LocalDateTime.now());
        flight.setArrivalDate(LocalDateTime.now().plusHours(1));

        return flight;
    }

    protected Flight createSecondFlight() {
        Flight flight = new Flight();
        flight.setId(2);
        flight.setName(SPACE);
        flight.setCarrier(SPACE);
        flight.setDuration(LocalTime.NOON);
        flight.setFare(Double.MIN_VALUE);
        flight.setMealOn(true);
        flight.setDepartureDate(LocalDateTime.MIN);
        flight.setArrivalDate(LocalDateTime.MAX);
        return flight;
    }
}
