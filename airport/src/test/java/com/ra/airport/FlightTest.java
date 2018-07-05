package com.ra.airport;

import com.ra.airport.entity.Flight;
import com.ra.airport.helper.DataCreationHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the model class {@link com.ra.airport.entity.Flight}
 */
public class FlightTest {

    private Flight firstFlight;

    private Flight secondFlight;

    @BeforeEach
    public void initData() {
        firstFlight = DataCreationHelper.createFlight();
        secondFlight = DataCreationHelper.createFlight();
    }

    @Test
    public void whenHashCodeIsTheSameForObjectsEqualsShouldReturnTrueForThem() {
        assertTrue(firstFlight.hashCode() == secondFlight.hashCode());

        assertTrue(this.firstFlight.equals(secondFlight));
    }

    @Test
    public void whenHashCodeIsTheDifferentForObjectsEqualsShouldReturnFalseForThem() {
        secondFlight.setIdentifier(2);
        assertFalse(firstFlight.hashCode() == secondFlight.hashCode());

        assertFalse(this.firstFlight.equals(secondFlight));
    }

    @Test
    public void whenToStringCorrectMessageShouldBeReturned() {
        String expected = "Flight{id=1, name=' ', carrier=' ', duration=12:00, departureDate=-999999999-01-01T00:00, arrivalDate=+999999999-12-31T23:59:59.999999999, fare=4.9E-324, mealOn=true}";
        assertEquals(expected, secondFlight.toString());
    }

    @Test
    public void whenEqualsObjectsWithTheSameFieldsTrueShouldBeReturned() {
        assertTrue(firstFlight.equals(secondFlight));
    }

    @Test
    public void whenEqualsObjectsWithTheDifferentIdsFalseShouldBeReturned() {
        secondFlight.setIdentifier(2);
        assertFalse(firstFlight.equals(secondFlight));
    }

    @Test
    public void whenEqualsObjectsWithTheDifferentNamesFalseShouldBeReturned() {
        secondFlight.setName("");
        assertFalse(firstFlight.equals(secondFlight));
    }

    @Test
    public void whenEqualsObjectsWithTheDifferentCarrierFalseShouldBeReturned() {
        secondFlight.setCarrier("");

        assertFalse(firstFlight.equals(secondFlight));
    }

    @Test
    public void whenEqualsObjectsWithTheDifferentDurationFalseShouldBeReturned() {
        secondFlight.setDuration(LocalTime.MIDNIGHT);

        assertFalse(firstFlight.equals(secondFlight));
    }

    @Test
    public void whenEqualsObjectsWithTheDifferentMealOnFalseShouldBeReturned() {
        secondFlight.setMealOn(false);

        assertFalse(firstFlight.equals(secondFlight));
    }

    @Test
    public void whenEqualsObjectsWithTheDifferentFareFalseShouldBeReturned() {
        secondFlight.setFare(Double.MAX_VALUE);

        assertFalse(firstFlight.equals(secondFlight));
    }

    @Test
    public void whenEqualsObjectsWithTheDifferentDepartureDateFalseShouldBeReturned() {
        secondFlight.setDepartureDate(firstFlight.getDepartureDate().plusDays(1));

        assertFalse(firstFlight.equals(secondFlight));
    }

    @Test
    public void whenEqualsObjectsWithTheDifferentArrivalDateFalseShouldBeReturned() {
        secondFlight.setArrivalDate(firstFlight.getArrivalDate().minusDays(1));

        assertFalse(firstFlight.equals(secondFlight));
    }

    @Test
    public void whenEqualsObjectWithNullThenShouldBeReturned() {
        assertFalse(firstFlight.equals(null));
    }

    @Test
    public void whenEqualsObjectsWithTheDifferentClassesFalseShouldBeReturned() {
        assertFalse(firstFlight.equals(new Object()));
    }
}
