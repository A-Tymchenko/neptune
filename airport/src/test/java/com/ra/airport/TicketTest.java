package com.ra.airport;

import com.ra.airport.entity.Ticket;
import com.ra.airport.helper.DataCreationHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TicketTest {
    private Ticket firstTicket;

    private Ticket secondTicket;

    @BeforeEach
    public void initData() {
        firstTicket = DataCreationHelper.createTicket();
        secondTicket = DataCreationHelper.createTicket();
    }

    @Test
    public void whenHashCodeIsTheSameForObjectsEqualsShouldReturnTrueForThem() {
        assertTrue(firstTicket.hashCode() == secondTicket.hashCode());

        assertTrue(this.firstTicket.equals(secondTicket));
    }

    @Test
    public void whenHashCodeIsTheDifferentForObjectsEqualsShouldReturnFalseForThem() {
        secondTicket.setIdTicket(2);
        assertFalse(firstTicket.hashCode() == secondTicket.hashCode());

        assertFalse(this.firstTicket.equals(secondTicket));
    }

    @Test
    public void whenToStringCorrectMessageShouldBeReturned() {
        String expected = "Flight{id=1, name=' ', carrier=' ', duration=12:00, departureDate=-999999999-01-01T00:00, arrivalDate=+999999999-12-31T23:59:59.999999999, fare=4.9E-324, mealOn=true}";
        assertEquals(expected, secondTicket.toString());
    }

    @Test
    public void whenEqualsObjectsWithTheSameFieldsTrueShouldBeReturned() {
        assertTrue(firstTicket.equals(secondTicket));
    }

    @Test
    public void whenEqualsObjectsWithTheDifferentIdsFalseShouldBeReturned() {
        secondTicket.setIdTicket(2);
        assertFalse(firstTicket.equals(secondTicket));
    }

    @Test
    public void whenEqualsObjectsWithTheDifferentNamesFalseShouldBeReturned() {
        secondTicket.setPassengerName("");
        assertFalse(firstTicket.equals(secondTicket));
    }

    @Test
    public void whenEqualsObjectsWithTheDifferentCarrierFalseShouldBeReturned() {
        secondTicket.setCarrier("");

        assertFalse(firstTicket.equals(secondTicket));
    }

    @Test
    public void whenEqualsObjectsWithTheDifferentDurationFalseShouldBeReturned() {
        secondTicket.setDuration(LocalTime.MIDNIGHT);

        assertFalse(firstTicket.equals(secondTicket));
    }

    @Test
    public void whenEqualsObjectsWithTheDifferentMealOnFalseShouldBeReturned() {
        secondTicket.setMealOn(false);

        assertFalse(firstTicket.equals(secondTicket));
    }

    @Test
    public void whenEqualsObjectsWithTheDifferentFareFalseShouldBeReturned() {
        secondTicket.setFare(Double.MAX_VALUE);

        assertFalse(firstTicket.equals(secondTicket));
    }

    @Test
    public void whenEqualsObjectsWithTheDifferentDepartureDateFalseShouldBeReturned() {
        secondTicket.setDepartureDate(firstTicket.getDepartureDate().plusDays(1));

        assertFalse(firstTicket.equals(secondTicket));
    }

    @Test
    public void whenEqualsObjectsWithTheDifferentArrivalDateFalseShouldBeReturned() {
        secondTicket.setArrivalDate(firstTicket.getArrivalDate().minusDays(1));

        assertFalse(firstTicket.equals(secondTicket));
    }

    @Test
    public void whenEqualsObjectWithNullThenShouldBeReturned() {
        assertFalse(firstTicket.equals(null));
    }

    @Test
    public void whenEqualsObjectsWithTheDifferentClassesFalseShouldBeReturned() {
        assertFalse(firstTicket.equals(new Object()));
    }

}
