package com.ra.airport;

import com.ra.airport.dao.exception.AirPortDaoException;
import com.ra.airport.dao.impl.TicketDao;
import com.ra.airport.entity.Ticket;
import com.ra.airport.factory.ConnectionFactory;
import com.ra.airport.helper.DataCreationHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static com.ra.airport.dao.exception.ExceptionMessage.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Mockito tests for {@link TicketDao} class
 */
public class TicketDaoMockitoTest {
    private static final String INSERT_SQL = "INSERT INTO TICKET "
            + "(TICKET_NUMBER, PASSENGER_NAME, DOCUMENT, SELLING_DATE) "
            + "VALUES (:ticketNumber, :passengerName, :document, :sellingDate)";

    private static final String UPDATE_SQL = "UPDATE TICKET "
            + "SET TICKET_NUMBER = :ticketNumber, "
            + "PASSENGER_NAME = :passengerName, "
            + "DOCUMENT = :document, "
            + "SELLING_DATE = :sellingDate "
            + "WHERE TICKET_ID = :ticketId";
    private static final String DELETE_SQL = "DELETE FROM TICKET WHERE TICKET_ID = :ticketId";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM TICKET WHERE TICKET_ID = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM TICKET";

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Mock
    private PreparedStatement statement;

    @InjectMocks
    private TicketDao ticketDao;
    private Ticket ticket;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        ticket = new Ticket();
        ticket.setTicketId(8);
        ticket.setTicketNumber("A123-456F");
        ticket.setPassengerName("Petro Velykyi");
        ticket.setDocument("AA192939");
        ticket.setSellingDate(Timestamp.valueOf("2018-06-21 21:05:00"));
    }

    @Test
    void whenCreateTicketThenReturnTicketWithIdTrue() throws AirPortDaoException {
        Mockito.when(namedParameterJdbcTemplate.update(Mockito.eq(INSERT_SQL),
                Mockito.any(BeanPropertySqlParameterSource.class),
                Mockito.any(GeneratedKeyHolder.class), new String[] {"ID"})).thenReturn(1);
        Ticket createdAirport = ticketDao.create(ticket);
        assertEquals(createdAirport, ticket);
    }





//    private void createMocksFromGetByIdMethod() throws SQLException {
//        when(mockResultSet.next()).thenReturn(true);
//        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
//        when(mockConnection.prepareStatement(SELECT_BY_ID_SQL)).thenReturn(mockStatement);
//        when(mockConnection.prepareStatement(DELETE_SQL)).thenReturn(mockStatement);
//        when(mockResultSet.getInt("TICKET_ID")).thenReturn(ticket.getTicketId());
//        when(mockResultSet.getString("TICKET_NUMBER")).thenReturn(ticket.getTicketNumber());
//        when(mockResultSet.getString("PASSENGER_NAME")).thenReturn(ticket.getPassengerName());
//        when(mockResultSet.getString("DOCUMENT")).thenReturn(ticket.getDocument());
//        when(mockResultSet.getTimestamp("SELLING_DATE")).thenReturn(ticket.getSellingDate());
//    }
//
//    @Test
//    public void whenCreateThenCorrectSQLShouldBeExecutedAndCorrectEntityShouldBeReturned() throws SQLException, AirPortDaoException {
//        when(mockConnection.prepareStatement(INSERT_SQL)).thenReturn(mockStatement);
//        when(mockConnection.prepareStatement(SELECT_LAST_GENERATED_ID_SQL)).thenReturn(mockStatement);
//        Ticket result = ticketDao.create(ticket);
//
//        assertEquals(ticket, result);
//    }
//
//    @Test
//    public void whenGetTicketByIdThenReturnEmptyOption() throws SQLException, AirPortDaoException {
//        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
//        when(mockResultSet.next()).thenReturn(false);
//        Optional<Ticket> result = ticketDao.getById(1);
//        assertEquals(result, Optional.empty());
//    }
//
//    @Test
//    public void whenGetTicketByIdThenReturnOptionalTicket() throws SQLException, AirPortDaoException {
//        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
//        when(mockResultSet.next()).thenReturn(true);
//        Optional<Ticket> result = ticketDao.getById(1);
//        assertEquals(result, ticketDao.getById(1));
//
//    }
//
//    @Test
//    public void whenUpdateThenCorrectSQLShouldBeExecutedAndCorrectEntityShouldBeReturned() throws SQLException, AirPortDaoException {
//        when(mockConnection.prepareStatement(UPDATE_SQL)).thenReturn(mockStatement);
//        Ticket result = ticketDao.update(ticket);
//
//        assertEquals(ticket, result);
//    }
//
//    @Test
//    public void whenDeleteThenCorrectSQLShouldBeExecutedAndTrueShouldBeReturned() throws AirPortDaoException, SQLException {
//        when(mockStatement.executeUpdate()).thenReturn(1);
//        boolean result = ticketDao.delete(ticket);
//
//        assertEquals(true, result);
//    }
//
//    @Test
//    public void whenDeleteStatementExecuteReturnOThenFalseShouldBeReturned() throws SQLException, AirPortDaoException {
//        when(mockStatement.executeUpdate()).thenReturn(0);
//        boolean result = ticketDao.delete(ticket);
//
//        assertEquals(false, result);
//    }
//
//    @Test
//    public void whenGetAllThenCorrectSQLShouldBeExecutedAndCorrectListReturned() throws AirPortDaoException, SQLException {
//        when(mockConnection.prepareStatement(SELECT_ALL_SQL)).thenReturn(mockStatement);
//        when(mockResultSet.next()).thenReturn(true,false);
//        List<Ticket> tickets = ticketDao.getAll();
//
//        assertFalse(tickets.isEmpty());
//    }
//
//    @Test
//    public void whenGetByIdReturnEmptyResultSetThenEmptyOptionalShouldBeReturned() throws AirPortDaoException, SQLException {
//        when(mockResultSet.next()).thenReturn(false);
//        Optional<Ticket> ticket = ticketDao.getById(Integer.valueOf(1));
//
//        assertEquals(Optional.empty(), ticket);
//    }
//
//    @Test
//    public void whenGetByIdNullPassedThenDAOExceptionShouldBeThrown() {
//        Throwable exception =  assertThrows(AirPortDaoException.class,() -> {
//            ticketDao.getById(null);
//        });
//
//        assertEquals(TICKET_ID_CANNOT_BE_NULL.get(), exception.getMessage());
//    }
//
//    @Test
//    public void whenCreateThrownSQlExceptionThenDAOExceptionShouldBeThrownToo() {
//        Throwable exception = assertThrows(AirPortDaoException.class, () -> {
//            when(mockConnection.prepareStatement(INSERT_SQL)).thenThrow(new SQLException());
//            ticketDao.create(ticket);
//        });
//
//        assertEquals(exception.getMessage(), FAILED_TO_CREATE_NEW_TICKET.get());
//    }
//
//    @Test
//    public void whenUpdateThrownSQlExceptionThenDAOExceptionShouldBeThrownToo() {
//        Throwable exception = assertThrows(AirPortDaoException.class, () -> {
//            when(mockConnection.prepareStatement(UPDATE_SQL)).thenThrow(new SQLException());
//            ticketDao.update(ticket);
//        });
//
//        assertEquals(exception.getMessage(), FAILED_TO_UPDATE_TICKET_WITH_ID.get() + 1);
//    }
//
//    @Test
//    public void whenDeleteThrownSQlExceptionThenDAOExceptionShouldBeThrownToo() {
//        Throwable exception = assertThrows(AirPortDaoException.class, () -> {
//            when(mockConnection.prepareStatement(DELETE_SQL)).thenThrow(new SQLException());
//            ticketDao.delete(ticket);
//        });
//
//        assertEquals(exception.getMessage(), FAILED_TO_DELETE_TICKET_WITH_ID.get()+1);
//    }
//
//    @Test
//    public void whenGetAllThrownSQlExceptionThenDAOExceptionShouldBeThrownToo() {
//        Throwable exception = assertThrows(AirPortDaoException.class, () -> {
//            when(mockConnection.prepareStatement(SELECT_ALL_SQL)).thenThrow(new SQLException());
//            ticketDao.getAll();
//        });
//
//        assertEquals(exception.getMessage(), FAILED_TO_GET_ALL_TICKETS.get());
//    }
//
//    @Test
//    public void whenGetByIdThrownSQlExceptionThenDAOExceptionShouldBeThrownToo() {
//        Throwable exception = assertThrows(AirPortDaoException.class, () -> {
//            when(mockConnection.prepareStatement(SELECT_BY_ID_SQL)).thenThrow(new SQLException());
//            ticketDao.getById(Integer.valueOf(1));
//        });
//
//        assertEquals(exception.getMessage(), FAILED_TO_GET_TICKET_WITH_ID.get() + 1);
//    }

}
