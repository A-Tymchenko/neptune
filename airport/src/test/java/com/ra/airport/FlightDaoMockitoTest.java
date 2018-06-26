package com.ra.airport;

import com.ra.airport.dao.exception.DAOException;
import com.ra.airport.dao.impl.FlightDAO;
import com.ra.airport.entity.Flight;
import com.ra.airport.factory.ConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Mockito tests for {@link FlightDAO} class
 */
public class FlightDaoMockitoTest extends AbstractTest {

    private static final String INSERT_FLIGHT_SQL = "INSERT INTO flight " +
            "(name, carrier, duration, meal, fare, departure_date, arrival_date) " +
            " VALUES(?,?,?,?,?,?,?)";

    private static final String UPDATE_FLIGHT_SQL = "UPDATE flight SET name = ?, carrier = ?, duration = ?, meal = ?, fare = ?, departure_date = ?, arrival_date = ? WHERE id = ?";
    private static final String SELECT_FLIGHT_BY_ID_SQL = "SELECT * FROM flight WHERE id = ?";
    private static final String DELETE_FLIGHT_BY_ID_SQL = "DELETE FROM flight WHERE id = ?";
    private static final String SELECT_LAST_GENERATED_ID_SQL = "SELECT SCOPE_IDENTITY()";
    private static final String SELECT_ALL_FLIGHTS_SQL ="SELECT * FROM flight";

    private static ConnectionFactory connectionFactory;
    private Connection mockConnection;
    private PreparedStatement mockStatement;
    private ResultSet mockResultSet;
    private FlightDAO flightDAO;
    private Flight flight;

    @BeforeEach
    public void init() throws SQLException {
        mockConnection = mock(Connection.class);
        mockStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        connectionFactory = mock(ConnectionFactory.class);
        flightDAO = new FlightDAO(connectionFactory);
        flight = createFlight();
        when(connectionFactory.getConnection()).thenReturn(mockConnection);
        createMocksFromGetByIdMethod();
    }

    private void createMocksFromGetByIdMethod() throws SQLException {
        when(mockConnection.prepareStatement(SELECT_FLIGHT_BY_ID_SQL)).thenReturn(mockStatement);
        when(mockResultSet.next()).thenReturn(true);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.getInt(1)).thenReturn(flight.getId());
        when(mockResultSet.getInt("id")).thenReturn(flight.getId());
        when(mockResultSet.getString("name")).thenReturn(flight.getName());
        when(mockResultSet.getString("carrier")).thenReturn(flight.getCarrier());
        when(mockResultSet.getTime("duration")).thenReturn(Time.valueOf(flight.getDuration()));
        when(mockResultSet.getBoolean("meal")).thenReturn(flight.isMealOn());
        when(mockResultSet.getDouble("fare")).thenReturn(flight.getFare());
        when(mockResultSet.getTimestamp("departure_date")).thenReturn(Timestamp.valueOf(flight.getDepartureDate()));
        when(mockResultSet.getTimestamp("arrival_date")).thenReturn(Timestamp.valueOf(flight.getArrivalDate()));
    }

    @Test
    public void whenCreateThenCorrectSQLShouldBeExecutedAndCorrectEntityShouldBeReturned() throws SQLException, DAOException {
        when(mockConnection.prepareStatement(INSERT_FLIGHT_SQL)).thenReturn(mockStatement);
        when(mockConnection.prepareStatement(SELECT_LAST_GENERATED_ID_SQL)).thenReturn(mockStatement);

        Flight result = flightDAO.create(flight);
        assertEquals(flight, result);
    }

    @Test
    public void whenUpdateThenCorrectSQLShouldBeExecutedAndCorrectEntityShouldBeReturned() throws SQLException, DAOException {
        when(mockConnection.prepareStatement(UPDATE_FLIGHT_SQL)).thenReturn(mockStatement);
        Flight result = flightDAO.update(flight);

        assertEquals(flight, result);
    }

    @Test
    public void whenDeleteThenCorrectSQLShouldBeExecutedAndTrueShouldBeReturned() throws DAOException, SQLException {
        when(mockConnection.prepareStatement(DELETE_FLIGHT_BY_ID_SQL)).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);

        boolean result = flightDAO.delete(flight);
        assertEquals(true, result);
    }

    @Test
    public void whenGetByIdEmptyOptionalPassedThenDAOExceptionShouldBeThrown() {
        assertThrows(DAOException.class,() -> {
            flightDAO.getById(Optional.empty());
        });
    }

    @Test
    public void whenCreateThrownSQlExceptionThenDAOExceptionShouldBeThrownToo() {
        assertThrows(DAOException.class,() -> {
            when(mockConnection.prepareStatement(INSERT_FLIGHT_SQL)).thenThrow(new SQLException());
            flightDAO.create(flight);
        });
    }

    @Test
    public void whenUpdateThrownSQlExceptionThenDAOExceptionShouldBeThrownToo() {
        assertThrows(DAOException.class,() -> {
            when(mockConnection.prepareStatement(UPDATE_FLIGHT_SQL)).thenThrow(new SQLException());
            flightDAO.update(flight);
        });
    }

    @Test
    public void whenDeleteThrownSQlExceptionThenDAOExceptionShouldBeThrownToo() {
        assertThrows(DAOException.class,() -> {
            when(mockConnection.prepareStatement(DELETE_FLIGHT_BY_ID_SQL)).thenThrow(new SQLException());
            flightDAO.delete(flight);
        });
    }

    @Test
    public void whenGetAllThrownSQlExceptionThenDAOExceptionShouldBeThrownToo() {
        assertThrows(DAOException.class,() -> {
            when(mockConnection.prepareStatement(SELECT_ALL_FLIGHTS_SQL)).thenThrow(new SQLException());
            flightDAO.getAll();
        });
    }
}
