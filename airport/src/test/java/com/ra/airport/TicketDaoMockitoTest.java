package com.ra.airport;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.ra.airport.dao.exception.AirPortDaoException;
import com.ra.airport.dao.impl.TicketDao;
import com.ra.airport.entity.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import static com.ra.airport.dao.exception.ExceptionMessage.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
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
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM TICKET WHERE TICKET_ID = :ticketId";
    private static final String SELECT_ALL_SQL = "SELECT * FROM TICKET";

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

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
    public void whenCreateThenCorrectSQLShouldBeExecutedAndCorrectEntityShouldBeReturned() throws AirPortDaoException {
        when(namedParameterJdbcTemplate.update(eq(INSERT_SQL), any(BeanPropertySqlParameterSource.class),
                                               any(GeneratedKeyHolder.class), eq(new String[]{"ID"}))).thenReturn(1);

        Ticket createdAirport = ticketDao.create(ticket);

        assertEquals(createdAirport, ticket);
    }

    @Test
    public void whenUpdateThenCorrectSQLShouldBeExecutedAndCorrectEntityShouldBeReturned() throws AirPortDaoException {
        when(namedParameterJdbcTemplate.update(eq(UPDATE_SQL), any(BeanPropertySqlParameterSource.class))).thenReturn(1);
        Ticket result = ticketDao.update(ticket);

        assertEquals(ticket, result);
    }

    @Test
    public void whenDeleteThenCorrectSQLShouldBeExecutedAndTrueShouldBeReturned() throws AirPortDaoException {
        when(namedParameterJdbcTemplate.update(eq(DELETE_SQL), any(MapSqlParameterSource.class))).thenReturn(1);
        boolean result = ticketDao.delete(ticket);

        assertTrue(result);
    }

    @Test
    public void whenDeleteTemplateExecuteReturnZeroThenFalseShouldBeReturned() throws AirPortDaoException {
        when(namedParameterJdbcTemplate.update(eq(DELETE_SQL), any(MapSqlParameterSource.class))).thenReturn(0);
        boolean result = ticketDao.delete(ticket);

        assertFalse(result);
    }

    @Test
    public void whenGetAllThenCorrectSQLShouldBeExecutedAndCorrectListReturned() throws AirPortDaoException {
        when(namedParameterJdbcTemplate.query(eq(SELECT_ALL_SQL), any(RowMapper.class))).thenReturn(new ArrayList<>());
        List<Ticket> tickets = ticketDao.getAll();

        assertTrue(tickets.isEmpty());
    }

    @Test
    public void whenGetByIdReturnEmptyResultSetThenEmptyOptionalShouldBeReturned() throws AirPortDaoException {
        when(namedParameterJdbcTemplate.queryForObject(eq(SELECT_BY_ID_SQL), any(MapSqlParameterSource.class),
                                                       any(RowMapper.class))).thenReturn(null);

        Optional<Ticket> ticket = ticketDao.getById(Integer.valueOf(1));

        assertEquals(Optional.empty(), ticket);
    }

    @Test
    public void whenCreateThrownEmptyResultDataAccessExceptionThenDAOExceptionShouldBeThrownToo() {
        Throwable exception = assertThrows(AirPortDaoException.class, () -> {
            when(namedParameterJdbcTemplate.update(eq(INSERT_SQL), any(BeanPropertySqlParameterSource.class),
                                                   any(GeneratedKeyHolder.class), eq(new String[]{"ID"}))).thenThrow(EmptyResultDataAccessException.class);
            ticketDao.create(ticket);
        });

        assertEquals(exception.getMessage(), FAILED_TO_CREATE_NEW_TICKET.get());
    }

    @Test
    public void whenUpdateThrownEmptyResultDataAccessExceptionThenDAOExceptionShouldBeThrownToo() {
        Throwable exception = assertThrows(AirPortDaoException.class, () -> {
            when(namedParameterJdbcTemplate.update(eq(UPDATE_SQL), any(BeanPropertySqlParameterSource.class))).thenThrow(EmptyResultDataAccessException.class);
            ticketDao.update(ticket);
        });

        assertEquals(exception.getMessage(), FAILED_TO_UPDATE_TICKET_WITH_ID.get() + 8);
    }

    @Test
    public void whenDeleteThrownEmptyResultDataAccessExceptionThenDAOExceptionShouldBeThrownToo() {
        Throwable exception = assertThrows(AirPortDaoException.class, () -> {
            when(namedParameterJdbcTemplate.update(eq(DELETE_SQL), any(MapSqlParameterSource.class)))
                    .thenThrow(EmptyResultDataAccessException.class);

            ticketDao.delete(ticket);
        });

        assertEquals(exception.getMessage(), FAILED_TO_DELETE_TICKET_WITH_ID.get() + 8);
    }

    @Test
    public void whenGetAllThrownAccessExceptionThenDAOExceptionShouldBeThrownToo() {
        Throwable exception = assertThrows(AirPortDaoException.class, () -> {
            when(namedParameterJdbcTemplate.query(eq(SELECT_ALL_SQL), any(RowMapper.class)))
                    .thenThrow(EmptyResultDataAccessException.class);

            ticketDao.getAll();
        });

        assertEquals(exception.getMessage(), FAILED_TO_GET_ALL_TICKETS.get());
    }

    @Test
    public void whenGetByIdThrownAccessExceptionThenDAOExceptionShouldBeThrownToo() {
        Throwable exception = assertThrows(AirPortDaoException.class, () -> {
            when(namedParameterJdbcTemplate.queryForObject(eq(SELECT_BY_ID_SQL), any(MapSqlParameterSource.class),
                                                           any(RowMapper.class))).thenThrow(EmptyResultDataAccessException.class);

            ticketDao.getById(Integer.valueOf(1));
        });

        assertEquals(exception.getMessage(), FAILED_TO_GET_TICKET_WITH_ID.get() + 1);
    }
}
