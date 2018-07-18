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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AirportDAOImplMockitoTest {

    ResultSet resultSet;
    ConnectionFactory conn;
    PreparedStatement stat;
    Connection connection;
    AirportDAOImpl airportDAO;
    Airport airport;
    final String query = "INSERT INTO Airport(apname, apnum, aptype, addresses, terminalcount) "
            + "VALUES(?, ?, ?, ?, ?)";


    @BeforeEach
    public void initH2() throws SQLException, IOException {
        airport = new Airport(8,"Kenedy", 4949034, "International", "USA New Yourk", 10);
        resultSet = Mockito.mock(ResultSet.class);
        conn = Mockito.mock(ConnectionFactory.class);
        stat = Mockito.mock(PreparedStatement.class);
        connection = Mockito.mock(Connection.class);
        airportDAO = new AirportDAOImpl(conn);
    }

    @Test
    public void whenGetAirportsExecutedReturnListSizeThree() throws Exception {
        final String query = "Select apname, apnum, aptype, addresses, terminalcount, apid From Airport";
        Mockito.when(conn.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(query)).thenReturn(stat);
        Mockito.when(stat.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        List<Airport> result = airportDAO.getAll();
        Assertions.assertEquals(3, result.size());
    }

    @Test
    public void whenGetAirportByIdThenReturnSqlException() throws SQLException {
        final String query = "Select apname, apnum, aptype, addresses, terminalcount, apid From Airport Where apid = ?";
        Mockito.when(conn.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(query)).thenThrow(SQLException.class);
        Throwable thrown = assertThrows(AirPortDaoException.class, () -> {
            airportDAO.getById(1);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    public void whenGetAirportsThenReturnSqlException() throws SQLException {
        final String query = "Select apname, apnum, aptype, addresses, terminalcount, apid From Airport";
        Mockito.when(conn.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(query)).thenThrow(SQLException.class);
        Throwable thrown = assertThrows(AirPortDaoException.class, () -> {
            airportDAO.getAll();
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    public void whenAddAirportThenReturnSqlException() throws SQLException {
        Mockito.when(conn.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(query)).thenThrow(SQLException.class);
        Throwable thrown = assertThrows(AirPortDaoException.class, () -> {
            airportDAO.create(airport);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    public void whenAddAirportThenReturnAirport() throws SQLException, AirPortDaoException {
        Mockito.when(conn.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(query)).thenReturn(stat);
        Mockito.when(connection.prepareStatement("Select LAST_INSERT_ID() from Airport")).thenReturn(stat);
        Mockito.when(stat.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(false);
        airportDAO.create(airport);
    }

    @Test
    public void whenUpdateAirportThenReturnSqlException() throws SQLException {
        final String query = "UPDATE Airport SET apname = ?, apnum = ?, aptype = ?, addresses = ?, terminalcount = ?"
                + " WHERE apid = ?";
        Mockito.when(conn.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(query)).thenThrow(SQLException.class);
        Throwable thrown = assertThrows(AirPortDaoException.class, () -> {
            airportDAO.update(airport);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    public void whenDeleteAirportThenReturnSqlException() throws SQLException {
        final String query = "DELETE FROM Airport "
                + "WHERE apid = ?";
        Mockito.when(conn.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(query)).thenThrow(SQLException.class);
        Throwable thrown = assertThrows(AirPortDaoException.class, () -> {
            airportDAO.delete(airport);
        });
        assertNotNull(thrown.getMessage());

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
