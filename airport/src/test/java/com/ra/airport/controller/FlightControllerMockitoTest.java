package com.ra.airport.controller;

import java.time.*;
import java.time.format.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.ra.airport.dto.FlightDto;
import com.ra.airport.service.FlightService;
import com.ra.airport.repository.exception.AirPortDaoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

public class FlightControllerMockitoTest {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DEPARTURE_DATE = "2018-06-17 13:15:00";
    private static final String ARRIVAL_DATE = "2018-06-17 15:16:00";

    @Mock
    FlightService flightService;

    @Mock
    Model model;

    @InjectMocks
    FlightController flightController;

    private FlightDto flightDto;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        createFlightDto();
    }

    @Test
    public void whenGetFlightsThenReturnPathToJspAndFlightsInModel() throws AirPortDaoException {
        assertEquals(flightController.getFlights(model), "flight/flights");
    }

    @Test
    public void whenCreateFlightThenReturnResponseEntity() throws AirPortDaoException {
        ResponseEntity responseEntity = flightController.createFlight(flightDto);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void whenDeleteFlightThenReturnResponseEntity() throws AirPortDaoException {
        ResponseEntity responseEntity = flightController.deleteFlight(flightDto);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void whenUpdateFlightThenReturnResponseEntity() throws AirPortDaoException {
        ResponseEntity responseEntity = flightController.updateFlight(flightDto);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    private void createFlightDto() {
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
