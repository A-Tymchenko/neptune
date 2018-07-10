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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AirportDAOImplMockitoTest {

    ConnectionFactory conn;
    Airport ap;

    {
        try {
            conn = ConnectionFactory.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void initH2(){
        try {
            conn.getConnection().createStatement().executeUpdate("RUNSCRIPT FROM 'src/main/resources/dbschema.sql'");
            conn.getConnection().createStatement().executeUpdate("RUNSCRIPT FROM 'src/main/resources/test-data.sql'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ap = new Airport(8,"Kenedy", 4949034, "International", "USA New Yourk", 10);
    }

    @Test
    public void whenGetAirportsExecutedReturnListSizeThree() throws Exception {
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        ConnectionFactory conn = Mockito.mock(ConnectionFactory.class);
        Statement stat = Mockito.mock(Statement.class);
        Connection connection = Mockito.mock(Connection.class);
        AirportDAOImpl ap = new AirportDAOImpl(conn);
        Mockito.when(conn.getConnection()).thenReturn(connection);
        Mockito.when(connection.createStatement()).thenReturn(stat);
        Mockito.when(stat.executeQuery("Select * From Airport")).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        List<Airport> result = ap.getAll();
        Assertions.assertEquals(3, result.size());
    }

    @Test
    public void whenAddAirportThenReturnSqlException() throws SQLException {
        ConnectionFactory conn = Mockito.mock(ConnectionFactory.class);
        Connection connection = Mockito.mock(Connection.class);
        AirportDAOImpl ap = new AirportDAOImpl(conn);
        Mockito.when(conn.getConnection()).thenReturn(connection);
        Mockito.when(connection.createStatement()).thenThrow(SQLException.class);
        try {
            ap.getById(1);
        } catch (AirPortDaoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenAddAirportThenReturnIOException() throws SQLException {
        ConnectionFactory conn = Mockito.mock(ConnectionFactory.class);
        Connection connection = Mockito.mock(Connection.class);
        AirportDAOImpl ap = new AirportDAOImpl(conn);
        Mockito.when(conn.getConnection()).thenReturn(connection);
        Mockito.when(connection.createStatement()).thenThrow(IOException.class);
        try {
            ap.getById(1);
        } catch (AirPortDaoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenGetAirportsThenReturnSqlException() throws SQLException {
        ConnectionFactory conn = Mockito.mock(ConnectionFactory.class);
        Connection connection = Mockito.mock(Connection.class);
        AirportDAOImpl ap = new AirportDAOImpl(conn);
        Mockito.when(conn.getConnection()).thenReturn(connection);
        Mockito.when(connection.createStatement()).thenThrow(SQLException.class);
        ap.getAll();
    }

    @Test
    public void whenGetAirportsThenReturnIOException() throws SQLException {
        ConnectionFactory conn = Mockito.mock(ConnectionFactory.class);
        Connection connection = Mockito.mock(Connection.class);
        AirportDAOImpl ap = new AirportDAOImpl(conn);
        Mockito.when(conn.getConnection()).thenReturn(connection);
        Mockito.when(connection.createStatement()).thenThrow(IOException.class);
        ap.getAll();
    }

    @Test
    public void whenAddAirportsThenReturnSqlException() throws SQLException {
        ConnectionFactory conn = Mockito.mock(ConnectionFactory.class);
        Connection connection = Mockito.mock(Connection.class);
        AirportDAOImpl apim = new AirportDAOImpl(conn);
        Mockito.when(conn.getConnection()).thenReturn(connection);
        Mockito.when(connection.createStatement()).thenThrow(SQLException.class);
        try {
            apim.create(ap);
        } catch (AirPortDaoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenAddAirportsThenReturnAirport() throws SQLException {
        ConnectionFactory conn = Mockito.mock(ConnectionFactory.class);
        Connection connection = Mockito.mock(Connection.class);
        Statement stat = Mockito.mock(Statement.class);
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        AirportDAOImpl apim = new AirportDAOImpl(conn);
        Mockito.when(conn.getConnection()).thenReturn(connection);
        Mockito.when(connection.createStatement()).thenReturn(stat);
        Mockito.when(stat.executeQuery("Select LAST_INSERT_ID() from Airport")).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(false);
        try {
            apim.create(ap);
        } catch (AirPortDaoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenUpdateAirportsThenReturnSqlException() throws SQLException {
        ConnectionFactory conn = Mockito.mock(ConnectionFactory.class);
        Connection connection = Mockito.mock(Connection.class);
        AirportDAOImpl apim = new AirportDAOImpl(conn);
        Mockito.when(conn.getConnection()).thenReturn(connection);
        Mockito.when(connection.createStatement()).thenThrow(SQLException.class);
        try {
            apim.update(ap);
        } catch (AirPortDaoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenDeleteAirportsThenReturnSqlException() throws SQLException {
        ConnectionFactory conn = Mockito.mock(ConnectionFactory.class);
        Connection connection = Mockito.mock(Connection.class);
        AirportDAOImpl apim = new AirportDAOImpl(conn);
        Mockito.when(conn.getConnection()).thenReturn(connection);
        Mockito.when(connection.createStatement()).thenThrow(SQLException.class);
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
