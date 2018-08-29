package com.ra.airport.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ra.airport.dto.FlightDto;
import com.ra.airport.entity.Flight;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.repository.impl.FlightDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class FlightServiceMockitoTest {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DEPARTURE_DATE = "2018-06-17 13:15:00";
    private static final String ARRIVAL_DATE = "2018-06-17 15:16:00";

    private Flight flight;
    private FlightDto flightDto;
    private List<Flight> flights;

    @Mock
    private FlightDao flightDao;

    @InjectMocks
    private FlightService flightService;

    @BeforeEach
    public void init() throws AirPortDaoException {
        MockitoAnnotations.initMocks(this);
        createFlight();
        createFlightDTO();
        flights = new ArrayList<>();
        flights.add(flight);
        Mockito.when(flightDao.create(Mockito.any())).thenReturn(flight);
        Mockito.when(flightDao.update(Mockito.any())).thenReturn(flight);
        Mockito.when(flightDao.delete(Mockito.any())).thenReturn(true);
        Mockito.when(flightDao.getById(8)).thenReturn(Optional.ofNullable(flight));
        Mockito.when(flightDao.getAll()).thenReturn(flights);
    }

    @Test
    public void whenCreateThenDaoMethodShouldBeCalled() throws AirPortDaoException {
        flightService.create(flightDto);
        verify(flightDao, times(1)).create(Mockito.any());
    }

    @Test
    public void whenUpdateThenDaoMethodShouldBeCalled() throws AirPortDaoException {
        flightService.update(flightDto);
        verify(flightDao, times(1)).update(Mockito.any());
    }

    @Test
    public void whenDeleteThenDaoMethodShouldBeCalled() throws AirPortDaoException {
        flightService.delete(flightDto);
        verify(flightDao, times(1)).delete(Mockito.any());
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

    private void createFlightDTO() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        LocalDateTime departureDate = LocalDateTime.parse(DEPARTURE_DATE, formatter);
        LocalDateTime arrivalDate = LocalDateTime.parse(ARRIVAL_DATE, formatter);
        flightDto = new FlightDto();
        flightDto.setFlId(1);
        flightDto.setName("Kyiv-Rome");
        flightDto.setCarrier("Wizz Air");
        flightDto.setMealOn(true);
        flightDto.setFare(100.0);
        flightDto.setDepartureDate(departureDate);
        flightDto.setArrivalDate(arrivalDate);
    }
}
