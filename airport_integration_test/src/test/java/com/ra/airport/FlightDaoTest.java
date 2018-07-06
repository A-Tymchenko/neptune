package com.ra.airport;

import com.ra.airport.dao.AirPortDao;
import com.ra.airport.dao.exception.DaoException;
import com.ra.airport.dao.impl.FlightDao;
import com.ra.airport.entity.Flight;
import com.ra.airport.factory.ConnectionFactory;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link FlightDao} class
 */
public class FlightDaoTest {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DEPARTURE_DATE = "2018-06-17 13:15:00";
    private static final String ARRIVAL_DATE = "2018-06-17 15:16:00";
    private static final String KYIV_ROME = "Kyiv-Rome";
    private static final String WIZZ_AIR = "Wizz Air";
    private static final Double FARE_100 = 100.0;

    private AirPortDao<Flight> airPortDao;

    private Flight flight;

    @BeforeEach
    public void beforeTest() throws SQLException, IOException {
        createDataBaseTable();
        createFlight();
    }

    @AfterEach
    public void afterTest() throws SQLException, IOException {
        deleteTable();
    }

    private void createDataBaseTable() throws SQLException, IOException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        RunScript.execute(connection, new FileReader("src/test/resources/sql/flight_table_backup.sql"));
    }

    private void deleteTable() throws SQLException, IOException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        RunScript.execute(connection, new FileReader("src/test/resources/sql/remove_flight_table.sql"));
    }

    private void createFlight() throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        LocalDateTime departureDate = LocalDateTime.parse(DEPARTURE_DATE, formatter);
        LocalDateTime arrivalDate = LocalDateTime.parse(ARRIVAL_DATE, formatter);
        airPortDao = new FlightDao(ConnectionFactory.getInstance());
        flight = new Flight();
        flight.setName(KYIV_ROME);
        flight.setCarrier(WIZZ_AIR);
        flight.setDuration(LocalTime.of(02, 00, 00));
        flight.setMealOn(true);
        flight.setFare(FARE_100);
        flight.setDepartureDate(departureDate);
        flight.setArrivalDate(arrivalDate);
    }

    @Test
    public void whenCreateThenNewFlightWithIdShouldBeReturned() throws DaoException {
        Flight createdFlight = airPortDao.create(flight);
        assertNotNull(createdFlight);
        Integer flightId = createdFlight.getIdentifier();
        assertNotNull(flightId);
        flight.setIdentifier(flightId);
        assertEquals(flight, createdFlight);
    }

    @Test
    public void whenUpdateThenUpdatedFlightShouldBeReturned() throws DaoException {
        Flight createdFlight = airPortDao.create(flight);
        Flight expectedFlight = changeFlight(createdFlight);

        Flight updatedFlight = airPortDao.update(createdFlight);

        assertEquals(expectedFlight, updatedFlight);
    }

    @Test
    public void whenDeleteThenDeleteObjectAndReturnTrue() throws DaoException {
        Flight createdFlight = airPortDao.create(flight);
        boolean result = airPortDao.delete(createdFlight);

        assertTrue(result);
    }

    @Test
    public void whenGetAllThenFlightsFromDBShouldBeReturned() throws DaoException {
        List<Flight> expectedResult = new ArrayList<>();
        expectedResult.add(airPortDao.create(flight));
        expectedResult.add(airPortDao.create(flight));

        List<Flight> flights = airPortDao.getAll();

        assertEquals(expectedResult, flights);
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
