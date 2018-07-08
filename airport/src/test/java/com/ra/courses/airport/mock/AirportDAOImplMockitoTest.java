package com.ra.courses.airport.mock;

import com.ra.courses.airport.dao.impl.AirportDAOImpl;
import com.ra.courses.airport.dao.impl.ConnectionFactory;
import com.ra.courses.airport.entity.Airport;
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
        ap = new Airport("10293","Kenedy", 4949034, "International", "USA New Yourk", 10);
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
        List<Airport> result = ap.getAirports();
        Assertions.assertEquals(3, result.size());
    }

    @Test
    public void whenAddAirportThenReturnSqlException() throws SQLException {
        ConnectionFactory conn = Mockito.mock(ConnectionFactory.class);
        Connection connection = Mockito.mock(Connection.class);
        AirportDAOImpl ap = new AirportDAOImpl(conn);
        Mockito.when(conn.getConnection()).thenReturn(connection);
        Mockito.when(connection.createStatement()).thenThrow(SQLException.class);
        ap.getAirport("1");
    }

    @Test
    public void whenAddAirportThenReturnIOException() throws SQLException {
        ConnectionFactory conn = Mockito.mock(ConnectionFactory.class);
        Connection connection = Mockito.mock(Connection.class);
        AirportDAOImpl ap = new AirportDAOImpl(conn);
        Mockito.when(conn.getConnection()).thenReturn(connection);
        Mockito.when(connection.createStatement()).thenThrow(IOException.class);
        ap.getAirport("1");
    }

    @Test
    public void whenGetAirportsThenReturnSqlException() throws SQLException {
        ConnectionFactory conn = Mockito.mock(ConnectionFactory.class);
        Connection connection = Mockito.mock(Connection.class);
        AirportDAOImpl ap = new AirportDAOImpl(conn);
        Mockito.when(conn.getConnection()).thenReturn(connection);
        Mockito.when(connection.createStatement()).thenThrow(SQLException.class);
        ap.getAirports();
    }

    @Test
    public void whenGetAirportsThenReturnIOException() throws SQLException {
        ConnectionFactory conn = Mockito.mock(ConnectionFactory.class);
        Connection connection = Mockito.mock(Connection.class);
        AirportDAOImpl ap = new AirportDAOImpl(conn);
        Mockito.when(conn.getConnection()).thenReturn(connection);
        Mockito.when(connection.createStatement()).thenThrow(IOException.class);
        ap.getAirports();
    }

    @Test
    public void whenAddAirportsThenReturnSqlException() throws SQLException {
        ConnectionFactory conn = Mockito.mock(ConnectionFactory.class);
        Connection connection = Mockito.mock(Connection.class);
        AirportDAOImpl apim = new AirportDAOImpl(conn);
        Mockito.when(conn.getConnection()).thenReturn(connection);
        Mockito.when(connection.createStatement()).thenThrow(SQLException.class);
        apim.addAirport(ap);
    }

    @Test
    public void whenUpdateAirportsThenReturnSqlException() throws SQLException {
        ConnectionFactory conn = Mockito.mock(ConnectionFactory.class);
        Connection connection = Mockito.mock(Connection.class);
        AirportDAOImpl apim = new AirportDAOImpl(conn);
        Mockito.when(conn.getConnection()).thenReturn(connection);
        Mockito.when(connection.createStatement()).thenThrow(SQLException.class);
        apim.updateAirport(ap);
    }

    @Test
    public void whenDeleteAirportsThenReturnSqlException() throws SQLException {
        ConnectionFactory conn = Mockito.mock(ConnectionFactory.class);
        Connection connection = Mockito.mock(Connection.class);
        AirportDAOImpl apim = new AirportDAOImpl(conn);
        Mockito.when(conn.getConnection()).thenReturn(connection);
        Mockito.when(connection.createStatement()).thenThrow(SQLException.class);
        apim.deleteAirport(ap);
    }
}
