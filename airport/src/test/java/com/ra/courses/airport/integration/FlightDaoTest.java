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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FlightDaoTest {
    
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

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DEPARTURE_DATE = "2018-06-17 13:15:00";
    private static final String ARRIVAL_DATE = "2018-06-17 15:16:00";
    private static final String KYIV_ROME = "Kyiv-Rome";
    private static final String WIZZ_AIR = "Wizz Air";
    private static final Double FARE_100 = 100.0;

    private DAO <Flight> dao;

    private Flight flight;

    @BeforeEach
    public void beforeAll() throws SQLException {
        createDataBaseTable();
        createFlight();
    }
    
    private void createFlight() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        LocalDateTime departureDate = LocalDateTime.parse(DEPARTURE_DATE, formatter);
        LocalDateTime arrivalDate = LocalDateTime.parse(ARRIVAL_DATE, formatter);
        dao = new FlightDAO(ConnectionFactory.getInstance());
        flight = new Flight();
        flight.setName(KYIV_ROME);
        flight.setCarrier(WIZZ_AIR);
        flight.setDuration(LocalTime.of(02, 00, 00));
        flight.setMealOn(true);
        flight.setFare(FARE_100);
        flight.setDepartureDate(departureDate);
        flight.setArrivalDate(arrivalDate);
    }

    private void createDataBaseTable() throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(CREATE_FLIGHT_TABLE_QUERY);
        preparedStatement.executeUpdate();
    }

    @Test
    public void whenCreateThenNewFlightWithIdShouldBeReturned() {
        Flight createdFlight = dao.create(flight);
        assertNotNull(createdFlight);
        Integer flightId = createdFlight.getId();
        assertNotNull(flightId);
        flight.setId(flightId);
        assertEquals(flight, createdFlight);
    }

    @Test
    public void whenUpdateThenUpdatedFlightShouldBeReturned() {
        Flight createdFlight = dao.create(flight);
        Flight expectedFlight = changeFlight(createdFlight);

        Flight updatedFlight = dao.update(createdFlight);

        assertEquals(expectedFlight, updatedFlight);
    }

    private Flight changeFlight(Flight flight) {
        flight.setName("Kyiv-Berlin");
        flight.setCarrier("MAU");
        flight.setDuration(flight.getDuration().plus(1, ChronoUnit.HOURS));
        flight.setMealOn(false);
        flight.setFare(Double.valueOf(200));
        flight.setDepartureDate(flight.getDepartureDate().plusDays(1));
        flight.setArrivalDate(flight.getArrivalDate().plusDays(1));
        return flight;
    }
}
