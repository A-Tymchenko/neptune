package com.ra.courses.airport.integration;

import com.ra.courses.airport.dao.FlightDAO;
import com.ra.courses.airport.dao.impl.FlightDAOImpl;
import com.ra.courses.airport.entity.Flight;
import com.ra.courses.airport.factory.ConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FlightDaoImplTest {

    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:mem:airport";

    private static final String USER = "";
    private static final String PASSWORD = "";

    private static final String CREATE_FLIGHT_TABLE_QUERY =
            "CREATE TABLE IF NOT EXISTS flight (\n" +
                    "  id INT NOT NULL AUTO_INCREMENT UNIQUE,\n" +
                    "  name VARCHAR(255),\n" +
                    "  carrier VARCHAR(255),\n" +
                    "  duration TIME, \n" +
                    "  meal BOOLEAN DEFAULT FALSE,\n" +
                    "  fare DECIMAL,\n" +
                    "  departure_date DATETIME,\n" +
                    "  arrival_date DATETIME,\n" +
                    ")";

    private FlightDAO dao;

    @BeforeEach
    public void beforeTest() throws SQLException,ClassNotFoundException {
        createDataBase();
        dao = new FlightDAOImpl(ConnectionFactory.getInstance());
    }

    private void createDataBase() throws SQLException, ClassNotFoundException {
        //        Connection connection = getConnection();
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        Class.forName(JDBC_DRIVER);
        PreparedStatement preparedStatement = connection.prepareStatement(CREATE_FLIGHT_TABLE_QUERY);
        preparedStatement.executeUpdate();
    }

    @Test
    public void whenCreateThenNewFlightShouldBeReturned() {
        Flight flight = dao.create();
        assertNotNull(flight);

        Integer flightId = flight.getId();

        assertNotNull(flightId);
    }
}
