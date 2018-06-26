package com.ra.airport;

import com.ra.airport.entity.Flight;

import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class AbstractTest {

    private static final String EMPTY = "";

    protected Flight createFlight() {
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
}
