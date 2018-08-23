package com.ra.airport.controller;

import com.ra.airport.entity.Airport;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.AirportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AirportControllerMockitoTest {

    @Mock
    AirportServiceImpl airportService;
    @Mock
    Model model;
    @InjectMocks
    AirportController airportController;
    String redirectionPath;

    @BeforeEach
    public void init() throws AirPortDaoException {
        MockitoAnnotations.initMocks(this);
        redirectionPath = "/index";
    }

//    @Test
//    public void whenCallGetAirportsThenReturnPathToJsp() throws AirPortDaoException {
//        assertEquals(airportController.getAirports(model), redirectionPath);
//    }
}
