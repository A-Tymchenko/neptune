package com.ra.airport;

import com.ra.airport.entity.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for the model class {@link com.ra.airport.entity.Flight}
 */
public class FlightTest extends AbstractTest {

    private Flight flight;

    @BeforeEach
    public void initData() {
        flight = createFlight();
        flight.setDepartureDate(LocalDateTime.MIN);
        flight.setArrivalDate(LocalDateTime.MAX);
    }

    @Test
    public void whenToStringCorrectMessageShouldBeReturned() {
        String expected = "Flight{id=1, name='', carrier='', duration=00:00, departure_date=-999999999-01-01T00:00, arrival_date=+999999999-12-31T23:59:59.999999999, fare=4.9E-324, mealOn=false}";
        assertEquals(expected, flight.toString());
    }
}
