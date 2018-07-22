package com.ra.airport;

import com.ra.airport.dao.exception.AirPortDaoException;
import com.ra.airport.entity.Airport;
import com.ra.airport.dao.impl.AirportDAOImpl;
import com.ra.airport.factory.ConnectionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AirportDAOImplMockitoTest {

    private ResultSet resultSet;
    private ConnectionFactory connectionFactory;
    private PreparedStatement statement;
    private Connection connection;
    private AirportDAOImpl airportDAO;
    private Airport airport;
    private Optional<Airport> optionalAirport;
    private static final String INSERT_QUERY = "INSERT INTO Airport(apname, apnum, aptype, address, terminalcount) "
            + "VALUES(?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ID_QUERY = "Select * From Airport Where apid = ?";
    private static final String SELECT_ALL_QUERY = "Select * From Airport";
    private static final String UPDATE_QUERY = "UPDATE Airport SET apname = ?, apnum = ?, aptype = ?, address = ?, terminalcount = ?"
            + " WHERE apid = ?";
    private static final String DELETE_QUERY = "DELETE FROM Airport "
            + "WHERE apid = ?";
    private static final String LAST_INSERT_ID = "Select LAST_INSERT_ID() from Airport";


    @BeforeEach
    public void init() throws SQLException, IOException {
        airport = new Airport(8,"Kenedy", 4949034, "International", "USA New Yourk", 10);
        optionalAirport.of(airport);
        resultSet = Mockito.mock(ResultSet.class);
        connectionFactory = Mockito.mock(ConnectionFactory.class);
        statement = Mockito.mock(PreparedStatement.class);
        connection = Mockito.mock(Connection.class);
        airportDAO = new AirportDAOImpl(connectionFactory);
        createAirport();
    }

    private void createAirport() throws SQLException {
        Mockito.when(resultSet.getInt("apid")).thenReturn(airport.getApId());
        Mockito.when(resultSet.getString("apname")).thenReturn(airport.getApName());
        Mockito.when(resultSet.getInt("apnum")).thenReturn(airport.getApNum());
        Mockito.when(resultSet.getString("aptype")).thenReturn(airport.getApType());
        Mockito.when(resultSet.getString("address")).thenReturn(airport.getAddress());
        Mockito.when(resultSet.getInt("terminalcount")).thenReturn(airport.getTerminalCount());
    }

    @Test
    public void whenGetAirportsExecutedReturnListSizeThree() throws Exception {
        Mockito.when(connectionFactory.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(SELECT_ALL_QUERY)).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        List<Airport> result = airportDAO.getAll();
        Assertions.assertEquals(3, result.size());
    }

    @Test
    public void whenGetAirportByIdThenReturnSqlException() throws SQLException {
        Mockito.when(connectionFactory.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(SELECT_BY_ID_QUERY)).thenThrow(SQLException.class);
        Throwable thrown = assertThrows(AirPortDaoException.class, () -> {
            airportDAO.getById(1);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    public void whenGetAirportByIdThenReturnEmptyOption() throws SQLException, AirPortDaoException {
        Mockito.when(connectionFactory.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(SELECT_BY_ID_QUERY)).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(false);
        Optional<Airport> result = airportDAO.getById(1);
        assertEquals(result, Optional.empty());
    }

    @Test
    public void whenGetAirportsThenReturnSqlException() throws SQLException {
        Mockito.when(connectionFactory.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(SELECT_ALL_QUERY)).thenThrow(SQLException.class);
        Throwable thrown = assertThrows(AirPortDaoException.class, () -> {
            airportDAO.getAll();
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    public void whenAddAirportThenReturnSqlException() throws SQLException {
        Mockito.when(connectionFactory.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(INSERT_QUERY)).thenThrow(SQLException.class);
        Throwable thrown = assertThrows(AirPortDaoException.class, () -> {
            airportDAO.create(airport);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    public void whenAddAirportThenReturnTrueAirport() throws SQLException, AirPortDaoException {
        Mockito.when(connectionFactory.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(INSERT_QUERY)).thenReturn(statement);
        Mockito.when(connection.prepareStatement(LAST_INSERT_ID)).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(connection.prepareStatement(SELECT_BY_ID_QUERY)).thenReturn(statement);
        Airport result = airportDAO.create(airport);
        assertEquals(result, airport);
    }

    @Test
    public void whenAddAirportThenReturnFalseAirport() throws SQLException, AirPortDaoException {
        Mockito.when(connectionFactory.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(INSERT_QUERY)).thenReturn(statement);
        Mockito.when(connection.prepareStatement(LAST_INSERT_ID)).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(false);
        Airport result = airportDAO.create(airport);
        assertEquals(result, airport);
    }

    @Test
    public void whenUpdateAirportThenReturnSqlException() throws SQLException {
        Mockito.when(connectionFactory.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(UPDATE_QUERY)).thenThrow(SQLException.class);
        Throwable thrown = assertThrows(AirPortDaoException.class, () -> {
            airportDAO.update(airport);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    public void whenUpdateAirportThenReturnCorrectAirport() throws SQLException, AirPortDaoException {
        Mockito.when(connectionFactory.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(UPDATE_QUERY)).thenReturn(statement);
        Mockito.when(connection.prepareStatement(SELECT_BY_ID_QUERY)).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
        Airport result = airportDAO.update(airport);
        assertEquals(result, airport);
    }

    @Test
    public void whenDeleteAirportThenReturnSqlException() throws SQLException {
        Mockito.when(connectionFactory.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(DELETE_QUERY)).thenThrow(SQLException.class);
        Throwable thrown = assertThrows(AirPortDaoException.class, () -> {
            airportDAO.delete(airport);
        });
        assertNotNull(thrown.getMessage());

    }

    @Test
    public void whenDeleteAirportThenReturnTrue() throws SQLException, AirPortDaoException {
        Mockito.when(connectionFactory.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(DELETE_QUERY)).thenReturn(statement);
        Mockito.when(statement.executeUpdate()).thenReturn(1);
        boolean result = airportDAO.delete(airport);
        assertEquals(result, true);
    }

    @Test
    public void whenDeleteAirportThenReturnFalse() throws SQLException, AirPortDaoException {
        Mockito.when(connectionFactory.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(DELETE_QUERY)).thenReturn(statement);
        Mockito.when(statement.executeUpdate()).thenReturn(0);
        boolean result = airportDAO.delete(airport);
        assertEquals(result, false);
    }

    @Test
    public void whenThrowAirPortExceptionThenCreateIt() {
        try {
            throw new AirPortDaoException("Test Exception");
        }
        catch (AirPortDaoException e) {

        }
    }
}
