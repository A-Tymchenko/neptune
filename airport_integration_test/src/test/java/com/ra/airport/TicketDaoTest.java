package com.ra.airport;

import com.ra.airport.dao.AirPortDao;
import com.ra.airport.dao.exception.AirPortDaoException;
import com.ra.airport.dao.impl.TicketDao;
import com.ra.airport.entity.Ticket;
import com.ra.airport.factory.ConnectionFactory;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link TicketDao} class
 */
class TicketDaoTest {
    private static URL urlToTicketScript;

    private AirPortDao<Ticket> airPortDao;

    private Ticket ticket;

    @BeforeEach
    public void beforeTest() throws SQLException, IOException {
        createDataBaseTable();
        createTicket();
    }

    @AfterEach
    public void afterTest() throws SQLException, IOException {
        deleteTable();
    }

    private void createDataBaseTable() throws SQLException, IOException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        urlToTicketScript = ClassLoader.getSystemResource("./sql/ticket_create_table.sql");
        RunScript.execute(connection, new FileReader(urlToTicketScript.getPath()));
    }

    private void deleteTable() throws SQLException, IOException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        urlToTicketScript = ClassLoader.getSystemResource("./sql/ticket_drop_table.sql");
        RunScript.execute(connection, new FileReader(urlToTicketScript.getPath()));
    }

    private void createTicket() throws IOException {
        airPortDao = new TicketDao(ConnectionFactory.getInstance());
        ticket = new Ticket();
        ticket.setTicketNumber("AA111-BB111");
        ticket.setPassengerName("John Dow");
        ticket.setDocument("QQ12345678QQ");
        ticket.setSellingDate(Timestamp.valueOf("2018-07-24 08:00:00"));
    }

    @Test
    public void whenCreateThenNewTicketWithIdShouldBeReturned() throws AirPortDaoException {
        Ticket createdTicket = airPortDao.create(ticket);
        assertNotNull(createdTicket);
        Integer idTicket = createdTicket.getIdTicket();
        assertNotNull(idTicket);
        ticket.setIdTicket(idTicket);
        assertEquals(ticket, createdTicket);
    }

    @Test
    public void whenUpdateThenUpdatedTicketShouldBeReturned() throws AirPortDaoException {
        Ticket createdTicket = airPortDao.create(ticket);
        Ticket expectedTicket = changeTicket(createdTicket);
        Ticket updatedTicket = airPortDao.update(createdTicket);
        assertEquals(expectedTicket, updatedTicket);
    }

    @Test
    public void whenDeleteThenDeleteTicketAndReturnTrue() throws AirPortDaoException {
        Ticket createdTicket = airPortDao.create(ticket);
        boolean result = airPortDao.delete(createdTicket);
        assertTrue(result);
    }

    @Test
    public void whenGetAllThenTicketsFromDBShouldBeReturned() throws AirPortDaoException {
        List<Ticket> expectedResult = new ArrayList<>();
        expectedResult.add(airPortDao.create(ticket));
        expectedResult.add(airPortDao.create(ticket));
        expectedResult.add(airPortDao.create(ticket));
        List<Ticket> tickets = airPortDao.getAll();
        assertEquals(expectedResult.size(), tickets.size());
    }

    private Ticket changeTicket(Ticket ticket) {
        ticket.setTicketNumber("DD111-CC111");
        ticket.setPassengerName("Jane Dow");
        ticket.setDocument("WW12345678WW");
        ticket.setSellingDate(Timestamp.valueOf("2018-07-25 08:00:00"));
        return ticket;
    }
}