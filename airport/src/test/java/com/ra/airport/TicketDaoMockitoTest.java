package com.ra.airport;

import com.ra.airport.dao.exception.AirPortDaoException;
import com.ra.airport.dao.impl.FlightDao;
import com.ra.airport.dao.impl.TicketDao;
import com.ra.airport.entity.Ticket;
import com.ra.airport.factory.ConnectionFactory;
import com.ra.airport.helper.DataCreationHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static com.ra.airport.dao.exception.ExceptionMessage.TICKET_ID_CANNOT_BE_NULL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TicketDaoMockitoTest {
    private static final String INSERT_TICKET_SQL = "INSERT INTO TICKET "
            + "(TICKET_NUMBER, PASSENGER_NAME, DOCUMENT, SELLING_DATE) VALUES (?,?,?,?)";

    private static final String UPDATE_TICKET_SQL = "UPDATE TICKET "
            + "SET TICKET_NUMBER=?, PASSENGER_NAME=?, DOCUMENT=?, SELLING_DATE=?"
            + "WHERE ID_TICKET=?";
    private static final String SELECT_TICKET_BY_ID_SQL = "SELECT * FROM ticket WHERE id = ?";
    private static final String DELETE_TICKET_BY_ID_SQL = "DELETE FROM ticket WHERE id = ?";
    private static final String SELECT_LAST_GENERATED_ID_SQL = "SELECT SCOPE_IDENTITY()";
    private static final String SELECT_ALL_TICKETS_SQL ="SELECT * FROM ticket";

    private static ConnectionFactory connectionFactory;
    private Connection mockConnection;
    private PreparedStatement mockStatement;
    private ResultSet mockResultSet;
    private TicketDao ticketDao;
    private Ticket ticket;

    @BeforeEach
    public void init() throws SQLException {
        mockConnection = mock(Connection.class);
        mockStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        connectionFactory = mock(ConnectionFactory.class);
        ticketDao = new TicketDao(connectionFactory);
        ticket = DataCreationHelper.createTicket();
        ticket.setSellingDate(Timestamp.valueOf("2018-07-25 09:00:00"));
        when(connectionFactory.getConnection()).thenReturn(mockConnection);
        createMocksFromGetByIdMethod();
    }

    private void createMocksFromGetByIdMethod() throws SQLException {
        when(mockConnection.prepareStatement(SELECT_TICKET_BY_ID_SQL)).thenReturn(mockStatement);
        when(mockResultSet.next()).thenReturn(true);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.getInt("ID_TICKET")).thenReturn(ticket.getIdTicket());
        when(mockResultSet.getString("TICKET_NUMBER")).thenReturn(ticket.getTicketNumber());
        when(mockResultSet.getString("PASSENGER_NAME")).thenReturn(ticket.getPassengerName());
        when(mockResultSet.getString("DOCUMENT")).thenReturn(ticket.getDocument());
        when(mockResultSet.getTimestamp("SELLING_DATE")).thenReturn(ticket.getSellingDate());
    }

    @Test
    public void whenCreateThenCorrectSQLShouldBeExecutedAndCorrectEntityShouldBeReturned() throws SQLException, AirPortDaoException {
        when(mockConnection.prepareStatement(INSERT_TICKET_SQL)).thenReturn(mockStatement);
        when(mockConnection.prepareStatement(SELECT_LAST_GENERATED_ID_SQL)).thenReturn(mockStatement);
        Ticket result = ticketDao.create(ticket);

        assertEquals(ticket, result);
    }

    @Test
    public void whenCreateReturnNullGeneratedIdThenDAOExceptionShouldBeThrown() throws SQLException, AirPortDaoException {
        when(mockConnection.prepareStatement(INSERT_TICKET_SQL)).thenReturn(mockStatement);
        when(mockConnection.prepareStatement(SELECT_LAST_GENERATED_ID_SQL)).thenReturn(mockStatement);
        when(mockResultSet.next()).thenReturn(false);
        Throwable exception =  assertThrows(AirPortDaoException.class,() -> {
            ticketDao.create(ticket);
        });

        assertEquals(exception.getMessage(), TICKET_ID_CANNOT_BE_NULL.get());
    }

}
