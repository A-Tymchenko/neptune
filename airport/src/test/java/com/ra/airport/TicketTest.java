package com.ra.airport;

import com.ra.airport.entity.Ticket;
import com.ra.airport.helper.DataCreationHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the model class {@link com.ra.airport.entity.Ticket}
 */
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
        String expected = "Ticket{idTicket=1, ticketNumber='AA111-BB111', passengerName='John Dow', document='QQ12345678QQ', sellingDate=2018-07-24 08:00:00.0}";
        assertEquals(expected, secondTicket.toString());
    }

    @Test
    public void whenEqualsObjectsWithTheSameFieldsTrueShouldBeReturned() {
        assertTrue(firstTicket.equals(secondTicket));
    }

    @Test
    public void whenEqualsObjectsWithTheDifferentIdTicketFalseShouldBeReturned() {
        secondTicket.setIdTicket(2);
        assertFalse(firstTicket.equals(secondTicket));
    }

    @Test
    public void whenEqualsObjectsWithTheDifferentTicketNumberFalseShouldBeReturned() {
        secondTicket.setTicketNumber("");
        assertFalse(firstTicket.equals(secondTicket));
    }

    @Test
    public void whenEqualsObjectsWithTheDifferentPassengerNameFalseShouldBeReturned() {
        secondTicket.setPassengerName("");
        assertFalse(firstTicket.equals(secondTicket));
    }

    @Test
    public void whenEqualsObjectsWithTheDifferentDocumentFalseShouldBeReturned() {
        secondTicket.setDocument("");
        assertFalse(firstTicket.equals(secondTicket));
    }

    @Test
    public void whenEqualsObjectsWithTheDifferentSellingDateFalseShouldBeReturned() {
        secondTicket.setSellingDate(null);
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
