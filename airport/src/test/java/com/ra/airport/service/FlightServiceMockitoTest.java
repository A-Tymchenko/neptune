package com.ra.airport.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.ra.airport.entity.Flight;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.repository.impl.FlightDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class FlightServiceMockitoTest {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DEPARTURE_DATE = "2018-06-17 13:15:00";
    private static final String ARRIVAL_DATE = "2018-06-17 15:16:00";

    private Flight flight;

    private FlightService flightService;

    @Mock
    private FlightDao flightDao;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        createFlight();
        flightService = new FlightService(flightDao);
    }

    @Test
    public void whenCreateThenDaoMethodShouldBeCalled() throws AirPortDaoException {
        flightService.create(flight);
        verify(flightDao, times(1)).create(flight);
    }

    @Test
    public void whenUpdateThenDaoMethodShouldBeCalled() throws AirPortDaoException {
        flightService.update(flight);
        verify(flightDao, times(1)).update(flight);
    }

    @Test
    public void whenDeleteThenDaoMethodShouldBeCalled() throws AirPortDaoException {
        flightService.delete(flight);
        verify(flightDao, times(1)).delete(flight);
    }

    @Test
    public void whenGetByIdThenDaoMethodShouldBeCalled() throws AirPortDaoException {
        flightService.getById(flight.getFlId());
        verify(flightDao, times(1)).getById(1);
    }

    @Test
    public void whenGetAllThenDaoMethodShouldBeCalled() throws AirPortDaoException {
        flightService.getAll();
        verify(flightDao, times(1)).getAll();
    }

    private void createFlight() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        LocalDateTime departureDate = LocalDateTime.parse(DEPARTURE_DATE, formatter);
        LocalDateTime arrivalDate = LocalDateTime.parse(ARRIVAL_DATE, formatter);
        flight = new Flight();
        flight.setFlId(1);
        flight.setName("Kyiv-Rome");
        flight.setCarrier("Wizz Air");
        flight.setMealOn(true);
        flight.setFare(100.0);
        flight.setDepartureDate(departureDate);
        flight.setArrivalDate(arrivalDate);
    }
}
