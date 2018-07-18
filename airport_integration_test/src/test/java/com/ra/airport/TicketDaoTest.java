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
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link TicketDao} class
 */
class TicketDaoTest {

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
        RunScript.execute(connection, new FileReader("src/test/resources/sql/ticket_create_table.sql"));
    }

    private void deleteTable() throws SQLException, IOException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        RunScript.execute(connection, new FileReader("src/test/resources/sql/ticket_drop_table.sql"));
    }

    private void createTicket() throws IOException {
        airPortDao = new TicketDao(ConnectionFactory.getInstance());
        ticket = new Ticket();
        ticket.setIdTicket(1);
        ticket.setTicketNumber("AA111-BB111");
        ticket.setPassengerName("John Dow");
        ticket.setDocument("QQ12345678QQ");
        ticket.setSellingDate(Timestamp.valueOf("2018-07-24 08:00:00"));
    }

    @Test
    public void whenCreateThenNewFlightWithIdShouldBeReturned() throws AirPortDaoException {
        Ticket createdTicket = airPortDao.create(ticket);
        assertNotNull(createdTicket);
        Integer idTicket = createdTicket.getIdTicket();
        assertNotNull(idTicket);
        ticket.setIdTicket(idTicket);
        assertEquals(ticket, createdTicket);
    }
}