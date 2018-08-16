package com.ra.airport;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import com.ra.airport.config.AirPortConfiguration;
import com.ra.airport.repository.AirPortDao;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.repository.impl.FlightDao;
import com.ra.airport.entity.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link FlightDao} class
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AirPortConfiguration.class})
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/create_table_skripts.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/tables_backup(data).sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/remove_table_skripts.sql")
})
public class FlightDaoTest {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DEPARTURE_DATE = "2018-06-17 13:15:00";
    private static final String ARRIVAL_DATE = "2018-06-17 15:16:00";

    @Autowired
    private AirPortDao<Flight> airPortDao;

    private Flight flight;

    @BeforeEach
    public void beforeTest() {
        createFlight();
    }

    private void createFlight() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        LocalDateTime departureDate = LocalDateTime.parse(DEPARTURE_DATE, formatter);
        LocalDateTime arrivalDate = LocalDateTime.parse(ARRIVAL_DATE, formatter);
        flight = new Flight();
        flight.setName("Kyiv-Rome");
        flight.setCarrier("Wizz Air");
        flight.setMealOn(true);
        flight.setFare(100.0);
        flight.setDepartureDate(departureDate);
        flight.setArrivalDate(arrivalDate);
    }

    @Test
    public void whenCreateThenNewFlightWithIdShouldBeReturned() throws AirPortDaoException {
        Flight createdFlight = airPortDao.create(flight);
        assertNotNull(createdFlight);
        Integer flightId = createdFlight.getFlId();
        assertNotNull(flightId);
        flight.setFlId(flightId);
        assertEquals(flight, createdFlight);
    }

    @Test
    public void whenUpdateThenUpdatedFlightShouldBeReturned() throws AirPortDaoException {
        Optional<Flight> optionalFlight = airPortDao.getById(1);

        assertNotEquals(Optional.empty(), optionalFlight);
        Flight flight = optionalFlight.get();
        Flight expectedFlight = changeFlight(flight);
        Flight updatedFlight = airPortDao.update(flight);

        assertEquals(expectedFlight, updatedFlight);
    }

    @Test
    public void whenDeleteThenDeleteObjectAndReturnTrue() throws AirPortDaoException {
        Optional<Flight> optionalFlight = airPortDao.getById(1);

        assertNotEquals(Optional.empty(), optionalFlight);
        boolean result = airPortDao.delete(optionalFlight.get());

        assertTrue(result);
    }

    @Test
    public void whenGetAllThenFlightsFromDBShouldBeReturned() throws AirPortDaoException {
        List<Flight> flights = airPortDao.getAll();

        assertTrue(flights.size() == 2);
    }

    private Flight changeFlight(Flight flight) {
        flight.setName("Kyiv-Berlin");
        flight.setCarrier("MAU");
        flight.setMealOn(false);
        flight.setFare(Double.valueOf(200));
        flight.setDepartureDate(flight.getDepartureDate().plusDays(1));
        flight.setArrivalDate(flight.getArrivalDate().plusDays(1));
        return flight;
    }
}
