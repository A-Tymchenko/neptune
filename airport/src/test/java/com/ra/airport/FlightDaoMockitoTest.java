package com.ra.airport;

import com.ra.airport.dao.exception.AirPortDaoException;
import com.ra.airport.dao.impl.FlightDao;
import com.ra.airport.entity.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ra.airport.dao.exception.ExceptionMessage.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.eq;

/**
 * Mockito tests for {@link FlightDao} class
 */

public class FlightDaoMockitoTest {

    private static final String INSERT_FLIGHT_SQL = "INSERT INTO flight " +
            "(name, carrier, duration, meal_on, fare, departure_date, arrival_date) " +
            " VALUES(?,?,?,?,?,?,?)";
    private static final String UPDATE_FLIGHT_SQL = "UPDATE flight SET name = ?, carrier = ?, duration = ?, meal_on = ?, fare = ?, departure_date = ?, arrival_date = ? WHERE flId = ?";
    private static final String SELECT_FLIGHT_BY_ID_SQL = "SELECT * FROM flight WHERE flId = ?";
    private static final String DELETE_FLIGHT_BY_ID_SQL = "DELETE FROM flight WHERE flId = ?";
    private static final String SELECT_LAST_GENERATED_ID_SQL = "SELECT SCOPE_IDENTITY()";
    private static final String SELECT_ALL_FLIGHTS_SQL ="SELECT * FROM flight";

    private FlightDao flightDao;
    private Flight flight;

    private JdbcTemplate mockJdbcTemplate;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;

    @BeforeEach
    public void init() throws SQLException {
        createFlight();
        flight.setDepartureDate(LocalDateTime.now());
        flight.setArrivalDate(LocalDateTime.now().plusHours(1));
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockJdbcTemplate = mock(JdbcTemplate.class);
        flightDao = new FlightDao(mockJdbcTemplate);

        when(mockConnection.prepareStatement(INSERT_FLIGHT_SQL)).thenReturn(mockPreparedStatement);
        when(mockConnection.prepareStatement(UPDATE_FLIGHT_SQL)).thenReturn(mockPreparedStatement);
        when(mockJdbcTemplate.queryForObject(SELECT_LAST_GENERATED_ID_SQL, Integer.class)).thenReturn(1);
        when(mockJdbcTemplate.queryForObject(eq(SELECT_FLIGHT_BY_ID_SQL), any(Object[].class), any(RowMapper.class))).thenReturn(flight);
    }

    @Test
    public void whenCreateThenCorrectSQLShouldBeExecutedAndCorrectEntityShouldBeReturned() throws AirPortDaoException {
        doAnswer(invocation -> {
            ((PreparedStatementCreator) invocation.getArguments()[0]).createPreparedStatement(mockConnection);
            return null;
        }).when(mockJdbcTemplate).update(any(PreparedStatementCreator.class), any(KeyHolder.class));
        Flight flightWithoutId = flight;
        flightWithoutId.setFlId(null);
        Flight result = flightDao.create(flightWithoutId);

        assertEquals(flight, result);
    }

    @Test
    public void whenUpdateThenCorrectSQLShouldBeExecutedAndCorrectEntityShouldBeReturned() throws AirPortDaoException {
        doAnswer(invocation -> {
            ((PreparedStatementCreator) invocation.getArguments()[1]).createPreparedStatement(mockConnection);
            return null;
        }).when(mockJdbcTemplate).update(any(PreparedStatementCreator.class), any(KeyHolder.class));
        Flight result = flightDao.update(flight);

        assertEquals(flight, result);
    }

    @Test
    public void whenDeleteThenCorrectSQLShouldBeExecutedAndTrueShouldBeReturned() throws AirPortDaoException {
        when(mockJdbcTemplate.update(DELETE_FLIGHT_BY_ID_SQL,1)).thenReturn(1);
        boolean result = flightDao.delete(flight);

        assertEquals(true, result);
    }

    @Test
    public void whenDeleteStatementExecuteReturnOThenFalseShouldBeReturned() throws AirPortDaoException {
        when(mockJdbcTemplate.update(DELETE_FLIGHT_BY_ID_SQL,1)).thenReturn(0);
        boolean result = flightDao.delete(flight);

        assertEquals(false, result);
    }

    @Test
    public void whenGetAllThenCorrectSQLShouldBeExecutedAndCorrectListReturned() throws AirPortDaoException {
        when(mockJdbcTemplate.query(eq(SELECT_ALL_FLIGHTS_SQL), any(RowMapper.class))).thenReturn(new ArrayList<>());
        List<Flight> flights = flightDao.getAll();

        assertTrue(flights.isEmpty());
    }

    @Test
    public void whenGetByIdReturnEmptyResultSetThenEmptyOptionalShouldBeReturned() throws AirPortDaoException {
        when(mockJdbcTemplate.queryForObject(eq(SELECT_FLIGHT_BY_ID_SQL), any(Object[].class), any(RowMapper.class))).thenReturn(null);
        Optional<Flight> flight = flightDao.getById(Integer.valueOf(1));

        assertEquals(Optional.empty(), flight);
    }

    @Test
    public void whenGetByIdNullPassedThenDAOExceptionShouldBeThrown() {
        Throwable exception =  assertThrows(AirPortDaoException.class,() -> {
            flightDao.getById(null);
        });

        assertEquals(FLIGHT_ID_CANNOT_BE_NULL.get(), exception.getMessage());
    }

    @Test
    public void whenCreateThrownEmptyResultDataAccessExceptionThenDAOExceptionShouldBeThrownToo() {
        Throwable exception = assertThrows(AirPortDaoException.class, () -> {
            when(mockJdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class))).thenThrow(EmptyResultDataAccessException.class);
            flightDao.create(flight);
        });

        assertEquals(exception.getMessage(), FAILED_TO_CREATE_NEW_FLIGHT.get());
    }

    @Test
    public void whenUpdateThrownEmptyResultDataAccessExceptionThenDAOExceptionShouldBeThrownToo() {
        Throwable exception = assertThrows(AirPortDaoException.class, () -> {
            when(mockJdbcTemplate.update(any(PreparedStatementCreator.class))).thenThrow(EmptyResultDataAccessException.class);
            flightDao.update(flight);
        });

        assertEquals(exception.getMessage(), FAILED_TO_UPDATE_FLIGHT_WITH_ID.get()+1);
    }

    @Test
    public void whenDeleteThrownEmptyResultDataAccessExceptionThenDAOExceptionShouldBeThrownToo() {
        Throwable exception = assertThrows(AirPortDaoException.class, () -> {
            when(mockJdbcTemplate.update(DELETE_FLIGHT_BY_ID_SQL,1))
                    .thenThrow(EmptyResultDataAccessException.class);
            flightDao.delete(flight);
        });

        assertEquals(exception.getMessage(), FAILED_TO_DELETE_FLIGHT_WITH_ID.get()+1);
    }

    @Test
    public void whenGetAllThrownEmptyResultDataAccessExceptionThenDAOExceptionShouldBeThrownToo() {
        Throwable exception = assertThrows(AirPortDaoException.class, () -> {
            when(mockJdbcTemplate.query(eq(SELECT_ALL_FLIGHTS_SQL), any(RowMapper.class)))
                    .thenThrow(EmptyResultDataAccessException.class);
            flightDao.getAll();
        });

        assertEquals(exception.getMessage(), FAILED_TO_GET_ALL_FLIGHTS.get());
    }

    @Test
    public void whenGetByIdThrownEmptyResultDataAccessExceptionThenDAOExceptionShouldBeThrownToo() {
        Throwable exception = assertThrows(AirPortDaoException.class, () -> {
            when(mockJdbcTemplate.queryForObject(eq(SELECT_FLIGHT_BY_ID_SQL), any(RowMapper.class), any(Integer.class)))
                    .thenThrow(EmptyResultDataAccessException.class);
            flightDao.getById(Integer.valueOf(1));
        });

        assertEquals(exception.getMessage(), FAILED_TO_GET_FLIGHT_WITH_ID.get()+1);
    }

    public void createFlight() {
        flight = new Flight();
        flight.setFlId(1);
        flight.setName(" ");
        flight.setCarrier(" ");
        flight.setDuration(LocalTime.NOON);
        flight.setFare(Double.MIN_VALUE);
        flight.setMealOn(true);
        flight.setDepartureDate(LocalDateTime.MIN);
        flight.setArrivalDate(LocalDateTime.MAX);
    }
}
