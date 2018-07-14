package com.ra.airport.mock;

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

    ConnectionFactory conn;
    Airport ap;
    final String query = "INSERT INTO Airport(apname, apnum, aptype, addresses, terminalcount) "
            + "VALUES(?, ?, ?, ?, ?)";

    @BeforeEach
    public void initH2() throws SQLException, IOException {
        conn = ConnectionFactory.getInstance();
        conn.getConnection().createStatement().executeUpdate("RUNSCRIPT FROM 'src/main/resources/dbschema.sql'");
        conn.getConnection().createStatement().executeUpdate("RUNSCRIPT FROM 'src/main/resources/test-data.sql'");
        ap = new Airport(8,"Kenedy", 4949034, "International", "USA New Yourk", 10);
    }

    @Test
    public void whenGetAirportsExecutedReturnListSizeThree() throws Exception {
        final String query = "Select apname, apnum, aptype, addresses, terminalcount, apid From Airport";
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        ConnectionFactory conn = Mockito.mock(ConnectionFactory.class);
        PreparedStatement stat = Mockito.mock(PreparedStatement.class);
        Connection connection = Mockito.mock(Connection.class);
        AirportDAOImpl ap = new AirportDAOImpl(conn);
        Mockito.when(conn.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(query)).thenReturn(stat);
        Mockito.when(stat.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        List<Airport> result = ap.getAll();
        Assertions.assertEquals(3, result.size());
    }

    @Test
    public void whenGetAirportByIdThenReturnSqlException() throws SQLException {
        final String query = "Select apname, apnum, aptype, addresses, terminalcount, apid From Airport Where apid = ?";
        ConnectionFactory conn = Mockito.mock(ConnectionFactory.class);
        Connection connection = Mockito.mock(Connection.class);
        AirportDAOImpl ap = new AirportDAOImpl(conn);
        Mockito.when(conn.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(query)).thenThrow(SQLException.class);
        Throwable thrown = assertThrows(AirPortDaoException.class, () -> {
            ap.getById(1);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    public void whenGetAirportsThenReturnSqlException() throws SQLException {
        final String query = "Select apname, apnum, aptype, addresses, terminalcount, apid From Airport";
        ConnectionFactory conn = Mockito.mock(ConnectionFactory.class);
        Connection connection = Mockito.mock(Connection.class);
        AirportDAOImpl ap = new AirportDAOImpl(conn);
        Mockito.when(conn.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(query)).thenThrow(SQLException.class);
        Throwable thrown = assertThrows(AirPortDaoException.class, () -> {
            ap.getAll();
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    public void whenAddAirportThenReturnSqlException() throws SQLException {
        ConnectionFactory conn = Mockito.mock(ConnectionFactory.class);
        Connection connection = Mockito.mock(Connection.class);
        AirportDAOImpl apim = new AirportDAOImpl(conn);
        Mockito.when(conn.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(query)).thenThrow(SQLException.class);
        Throwable thrown = assertThrows(AirPortDaoException.class, () -> {
            apim.create(ap);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    public void whenAddAirportThenReturnAirport() throws SQLException, AirPortDaoException {
        ConnectionFactory conn = Mockito.mock(ConnectionFactory.class);
        Connection connection = Mockito.mock(Connection.class);
        PreparedStatement stat = Mockito.mock(PreparedStatement.class);
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        AirportDAOImpl apim = new AirportDAOImpl(conn);
        Mockito.when(conn.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(query)).thenReturn(stat);
        Mockito.when(connection.prepareStatement("Select LAST_INSERT_ID() from Airport")).thenReturn(stat);
        Mockito.when(stat.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(false);
        apim.create(ap);
    }

    @Test
    public void whenUpdateAirportThenReturnSqlException() throws SQLException {
        final String query = "UPDATE Airport SET "
                + " apname = ?,"
                + " apnum = ?,"
                + " aptype = ?,"
                + " addresses = ?,"
                + " terminalcount = ?"
                + " WHERE apid = ?";
        ConnectionFactory conn = Mockito.mock(ConnectionFactory.class);
        Connection connection = Mockito.mock(Connection.class);
        AirportDAOImpl apim = new AirportDAOImpl(conn);
        Mockito.when(conn.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(query)).thenThrow(SQLException.class);
        Throwable thrown = assertThrows(AirPortDaoException.class, () -> {
            apim.update(ap);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    public void whenDeleteAirportThenReturnSqlException() throws SQLException {
        final String query = "DELETE FROM Airport "
                + "WHERE apid = ?";
        ConnectionFactory conn = Mockito.mock(ConnectionFactory.class);
        Connection connection = Mockito.mock(Connection.class);
        AirportDAOImpl apim = new AirportDAOImpl(conn);
        Mockito.when(conn.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(query)).thenThrow(SQLException.class);
        Throwable thrown = assertThrows(AirPortDaoException.class, () -> {
            apim.delete(ap);
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
