package com.ra.airport;

import com.ra.airport.entity.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the model class {@link com.ra.airport.entity.Flight}
 */
public class FlightTest extends AbstractTest {

    private Flight firstFlight;

    private Flight secondFlight;

    @BeforeEach
    public void initData() {
        firstFlight = createFirstFlight();
        secondFlight = createSecondFlight();
    }

    @Test
    public void whenToStringCorrectMessageShouldBeReturned() {
        String expected = "Flight{id=2, name=' ', carrier=' ', duration=12:00, departure_date=-999999999-01-01T00:00, arrival_date=+999999999-12-31T23:59:59.999999999, fare=4.9E-324, mealOn=true}";
        assertEquals(expected, secondFlight.toString());
    }

    @Test
    public void whenEqualsObjectsWithTheSameFieldsTrueShouldBeReturned() {
        Flight flightForEquals = createSecondFlight();

        assertTrue(this.secondFlight.equals(flightForEquals));
    }

    @Test
    public void whenEqualsObjectsWithTheDifferentFieldsTrueShouldBeReturned() {
        assertFalse(firstFlight.equals(secondFlight));
    }
}
