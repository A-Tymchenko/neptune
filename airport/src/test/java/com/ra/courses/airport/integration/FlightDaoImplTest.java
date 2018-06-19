package com.ra.courses.airport.integration;

import com.ra.courses.airport.dao.DAO;
import com.ra.courses.airport.dao.impl.FlightDAO;
import com.ra.courses.airport.entity.Flight;
import com.ra.courses.airport.factory.ConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FlightDaoImplTest {
    
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

    private DAO <Flight> dao;

    @BeforeEach
    public void beforeTest() throws SQLException,ClassNotFoundException {
        createDataBase();
        dao = new FlightDAO(ConnectionFactory.getInstance());
    }

    private void createDataBase() throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
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
