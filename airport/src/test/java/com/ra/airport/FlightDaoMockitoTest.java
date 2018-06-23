package com.ra.airport;

import com.ra.airport.dao.exception.DAOException;
import com.ra.airport.dao.impl.FlightDAO;
import com.ra.airport.entity.Flight;
import com.ra.airport.factory.ConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Mockito tests for {@link FlightDAO} class
 */
public class FlightDaoMockitoTest {

    private static final String UPDATE_FLIGHT_SQL = "UPDATE flight SET name = ?, carrier = ?, duration = ?, meal = ?, fare = ?, departure_date = ?, arrival_date = ? WHERE id = ?";
    private static final String SELECT_FLIGHT_BY_ID_SQL = "SELECT * FROM flight WHERE id = ?";
    private static final String DELETE_FLIGHT_BY_ID_SQL = "DELETE FROM flight WHERE id = ?";

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
        flight = new Flight();
        flight.setId(1);
        when(connectionFactory.getConnection()).thenReturn(mockConnection);
    }

    @Test
    public void whenCreateThenCorrectSQLShouldBeExecutedAndCorrectEntityShouldBeReturned() {
    }

    @Test
    public void whenUpdateThenCorrectSQLShouldBeExecutedAndCorrectEntityShouldBeReturned() {

    }

    @Test
    public void whenDeleteThenCorrectSQLShouldBeExecutedAndTrueShouldBeReturned() throws DAOException, SQLException {
        when(mockConnection.prepareStatement(DELETE_FLIGHT_BY_ID_SQL)).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);

        boolean result = flightDAO.delete(flight);
        assertEquals(true, result);
    }


    @Test
    public void whenGetByIdNullThenDAOExceptionShouldBeThrown() {
        assertThrows(DAOException.class, () -> {
            flightDAO.getById(Optional.empty());
        });
    }
}
