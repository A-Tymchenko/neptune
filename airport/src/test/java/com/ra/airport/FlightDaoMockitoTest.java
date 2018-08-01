package com.ra.airport;

import com.ra.airport.dao.exception.AirPortDaoException;
import com.ra.airport.dao.impl.FlightDao;
import com.ra.airport.entity.Flight;
import com.ra.airport.factory.ConnectionFactory;
import com.ra.airport.helper.DataCreationHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.ra.airport.dao.exception.ExceptionMessage.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Mockito tests for {@link FlightDao} class
 */
public class FlightDaoMockitoTest {

    private static final String INSERT_FLIGHT_SQL = "INSERT INTO flight " +
            "(name, carrier, duration, meal_on, fare, departure_date, arrival_date) " +
            " VALUES(?,?,?,?,?,?,?)";
    private static final String UPDATE_FLIGHT_SQL = "UPDATE flight SET name = ?, carrier = ?, duration = ?, meal_on = ?, fare = ?, departure_date = ?, arrival_date = ? WHERE id = ?";
    private static final String SELECT_FLIGHT_BY_ID_SQL = "SELECT * FROM flight WHERE id = ?";
    private static final String DELETE_FLIGHT_BY_ID_SQL = "DELETE FROM flight WHERE id = ?";
    private static final String SELECT_LAST_GENERATED_ID_SQL = "SELECT SCOPE_IDENTITY()";
    private static final String SELECT_ALL_FLIGHTS_SQL = "SELECT * FROM flight";

    private static ConnectionFactory connectionFactory;
    private Connection mockConnection;
    private PreparedStatement mockStatement;
    private ResultSet mockResultSet;
    private FlightDao flightDao;
    private Flight flight;

    @BeforeEach
    void init() throws SQLException {
        mockConnection = mock(Connection.class);
        mockStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        connectionFactory = mock(ConnectionFactory.class);
        flightDao = new FlightDao(connectionFactory);
        flight = DataCreationHelper.createFlight();
        flight.setDepartureDate(LocalDateTime.now());
        flight.setArrivalDate(LocalDateTime.now().plusHours(1));
        when(connectionFactory.getConnection()).thenReturn(mockConnection);
        createMocksFromGetByIdMethod();
    }

    private void createMocksFromGetByIdMethod() throws SQLException {
        when(mockConnection.prepareStatement(SELECT_FLIGHT_BY_ID_SQL)).thenReturn(mockStatement);
        when(mockConnection.prepareStatement(INSERT_FLIGHT_SQL)).thenReturn(mockStatement);
        when(mockConnection.prepareStatement(SELECT_LAST_GENERATED_ID_SQL)).thenReturn(mockStatement);
        when(mockConnection.prepareStatement(DELETE_FLIGHT_BY_ID_SQL)).thenReturn(mockStatement);
        when(mockResultSet.next()).thenReturn(true);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.getInt(1)).thenReturn(flight.getIdentifier());
        when(mockResultSet.getInt("id")).thenReturn(flight.getIdentifier());
        when(mockResultSet.getString("name")).thenReturn(flight.getName());
        when(mockResultSet.getString("carrier")).thenReturn(flight.getCarrier());
        when(mockResultSet.getTime("duration")).thenReturn(Time.valueOf(flight.getDuration()));
        when(mockResultSet.getBoolean("meal_on")).thenReturn(flight.getMealOn());
        when(mockResultSet.getDouble("fare")).thenReturn(flight.getFare());
        when(mockResultSet.getTimestamp("departure_date")).thenReturn(Timestamp.valueOf(flight.getDepartureDate()));
        when(mockResultSet.getTimestamp("arrival_date")).thenReturn(Timestamp.valueOf(flight.getArrivalDate()));
    }

    @Test
    void whenCreateThenCorrectSQLShouldBeExecutedAndCorrectEntityShouldBeReturned() throws SQLException, AirPortDaoException {
        Flight result = flightDao.create(flight);

        assertEquals(flight, result);
    }

    @Test
    public void whenUpdateThenCorrectSQLShouldBeExecutedAndCorrectEntityShouldBeReturned() throws SQLException, AirPortDaoException {
        when(mockConnection.prepareStatement(UPDATE_FLIGHT_SQL)).thenReturn(mockStatement);
        Flight result = flightDao.update(flight);

        assertEquals(flight, result);
    }

    @Test
    public void whenDeleteThenCorrectSQLShouldBeExecutedAndTrueShouldBeReturned() throws AirPortDaoException, SQLException {

        when(mockStatement.executeUpdate()).thenReturn(1);
        boolean result = flightDao.delete(flight);

        assertTrue(result);
    }

    @Test
    public void whenDeleteStatementExecuteReturnOThenFalseShouldBeReturned() throws SQLException, AirPortDaoException {
        when(mockStatement.executeUpdate()).thenReturn(0);
        boolean result = flightDao.delete(flight);

        assertFalse(result);
    }

    @Test
    public void whenGetAllThenCorrectSQLShouldBeExecutedAndCorrectListReturned() throws AirPortDaoException, SQLException {
        when(mockConnection.prepareStatement(SELECT_ALL_FLIGHTS_SQL)).thenReturn(mockStatement);
        when(mockResultSet.next()).thenReturn(true, false);
        List<Flight> flights = flightDao.getAll();

        assertFalse(flights.isEmpty());
    }

    @Test
    public void whenGetByIdReturnEmptyResultSetThenEmptyOptionalShouldBeReturned() throws AirPortDaoException, SQLException {
        when(mockResultSet.next()).thenReturn(false);
        Optional<Flight> flight = flightDao.getById(1);

        assertEquals(Optional.empty(), flight);
    }

    @Test
    public void whenGetByIdNullPassedThenDAOExceptionShouldBeThrown() {
        Throwable exception = assertThrows(AirPortDaoException.class, () -> {
            flightDao.getById(null);
        });

        assertEquals(FLIGHT_ID_CANNOT_BE_NULL.get(), exception.getMessage());
    }

    @Test
    public void whenCreateThrownSQlExceptionThenDAOExceptionShouldBeThrownToo() {
        Throwable exception = assertThrows(AirPortDaoException.class, () -> {
            when(mockConnection.prepareStatement(INSERT_FLIGHT_SQL)).thenThrow(new SQLException());
            flightDao.create(flight);
        });

        assertEquals(exception.getMessage(), FAILED_TO_CREATE_NEW_FLIGHT.get());
    }

    @Test
    void whenUpdateThrownSQlExceptionThenDAOExceptionShouldBeThrownToo() {
        Throwable exception = assertThrows(AirPortDaoException.class, () -> {
            when(mockConnection.prepareStatement(UPDATE_FLIGHT_SQL)).thenThrow(new SQLException());
            flightDao.update(flight);
        });

        assertEquals(exception.getMessage(), FAILED_TO_UPDATE_FLIGHT_WITH_ID.get() + 1);
    }

    @Test
    void whenDeleteThrownSQlExceptionThenDAOExceptionShouldBeThrownToo() {
        Throwable exception = assertThrows(AirPortDaoException.class, () -> {
            when(mockConnection.prepareStatement(DELETE_FLIGHT_BY_ID_SQL)).thenThrow(new SQLException());
            flightDao.delete(flight);
        });

        assertEquals(exception.getMessage(), FAILED_TO_DELETE_FLIGHT_WITH_ID.get() + 1);
    }

    @Test
    void whenGetAllThrownSQlExceptionThenDAOExceptionShouldBeThrownToo() {
        Throwable exception = assertThrows(AirPortDaoException.class, () -> {
            when(mockConnection.prepareStatement(SELECT_ALL_FLIGHTS_SQL)).thenThrow(new SQLException());
            flightDao.getAll();
        });

        assertEquals(exception.getMessage(), FAILED_TO_GET_ALL_FLIGHTS.get());
    }

    @Test
    void whenGetByIdThrownSQlExceptionThenDAOExceptionShouldBeThrownToo() {
        Throwable exception = assertThrows(AirPortDaoException.class, () -> {
            when(mockConnection.prepareStatement(SELECT_FLIGHT_BY_ID_SQL)).thenThrow(new SQLException());
            flightDao.getById(1);
        });

        assertEquals(exception.getMessage(), FAILED_TO_GET_FLIGHT_WITH_ID.get() + 1);
    }

    @Test
    void whenGeneratedIdRSReturnNullThenNextIdentifierIsNull() throws Exception {
        when(mockConnection.prepareStatement(SELECT_LAST_GENERATED_ID_SQL)).thenReturn(mockStatement);
        when(mockResultSet.next()).thenReturn(false);
        Flight result = flightDao.create(flight);

        assertNull(result.getIdentifier());
    }
}
