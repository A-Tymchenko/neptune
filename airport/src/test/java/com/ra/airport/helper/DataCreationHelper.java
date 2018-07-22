package com.ra.airport.helper;

import com.ra.airport.entity.Flight;
import com.ra.airport.entity.Plane;

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

    public static Plane createPlane(){
        Plane plane = new Plane();
        plane.setId(1);
        plane.setPlateNumber(2);
        plane.setModel(SPACE);
        plane.setType(SPACE);
        plane.setOwner(SPACE);
        return plane;
    }
}
