package com.ra.airport;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import com.ra.airport.config.AirPortConfiguration;
import com.ra.airport.dao.exception.AirPortDaoException;
import com.ra.airport.dao.impl.TicketDao;
import com.ra.airport.entity.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link TicketDao} class
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AirPortConfiguration.class})
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/create_table_skripts.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/tables_backup(data).sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/remove_table_skripts.sql")
})
class TicketDaoTest {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    TicketDao ticketDao;
    Ticket ticket;

    @BeforeEach
    public void beforeTest() throws SQLException, IOException {
        ticket = new Ticket();
        ticket.setTicketNumber("A123-456F");
        ticket.setPassengerName("Petro Velykyi");
        ticket.setDocument("AA192939");
        ticket.setSellingDate(Timestamp.valueOf("2018-06-21 21:05:00"));
    }

    @Test
    public void whenCreateThenNewTicketWithIdShouldBeReturned() throws AirPortDaoException {
        Ticket createdTicket = ticketDao.create(ticket);
        assertNotNull(createdTicket);
        Integer idTicket = createdTicket.getTicketId();
        assertNotNull(idTicket);
        ticket.setTicketId(idTicket);
        assertEquals(ticket, createdTicket);
    }

    @Test
    public void whenUpdateThenUpdatedTicketShouldBeReturned() throws AirPortDaoException {
        Ticket createdTicket = ticketDao.create(ticket);
        Ticket expectedTicket = changeTicket(createdTicket);
        Ticket updatedTicket = ticketDao.update(createdTicket);
        assertEquals(expectedTicket, updatedTicket);
    }

    @Test
    public void whenDeleteThenDeleteTicketAndReturnTrue() throws AirPortDaoException {
        Ticket createdTicket = ticketDao.create(ticket);
        boolean result = ticketDao.delete(createdTicket);
        assertTrue(result);
    }

    @Test
    public void whenGetTicketByIdThenReturnTicket() throws AirPortDaoException {
        Optional<Ticket> ticket = ticketDao.getById(1);
        assertEquals(ticket.get().getTicketNumber(), this.ticket.getTicketNumber());
    }

    @Test
    public void whenGetNotExistingTicketByIdThenThrowAirPortDaoException() throws AirPortDaoException {
        Throwable thrown = assertThrows(AirPortDaoException.class, () -> ticketDao.getById(5));
        assertNotNull(thrown.getMessage());
    }

    @Test
    public void whenGetAllThenTicketsFromDBShouldBeReturned() throws AirPortDaoException {
        List<Ticket> tickets = ticketDao.getAll();
        assertEquals(tickets.size(), 3);
    }

    private Ticket changeTicket(Ticket ticket) {
        ticket.setTicketNumber("DD111-CC111");
        ticket.setPassengerName("Jane Dow");
        ticket.setDocument("WW12345678WW");
        ticket.setSellingDate(Timestamp.valueOf("2018-07-25 08:00:00"));
        return ticket;
    }
}