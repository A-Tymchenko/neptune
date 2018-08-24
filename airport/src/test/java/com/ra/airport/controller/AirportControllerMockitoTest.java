package com.ra.airport.controller;

import com.ra.airport.dto.AirportDTO;
import com.ra.airport.entity.Airport;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.AirportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    AirportDTO airportDTO;

    @BeforeEach
    public void init() throws AirPortDaoException {
        MockitoAnnotations.initMocks(this);
        redirectionPath = "/index";
        airportDTO = initAirportDTO();
    }

    @Test
    public void whenCallGetAirportsThenReturnPathToJsp() throws AirPortDaoException {
        assertEquals(airportController.getAirports(model), "airport/show_airports");
    }

    @Test
    public void whenCallPOSTAirportsThenReturnResponseEntity() throws AirPortDaoException {
        ResponseEntity responseEntity = airportController.createAirport(airportDTO);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void whenCallDELETEAirportsThenReturnResponseEntity() throws AirPortDaoException {
        ResponseEntity responseEntity = airportController.deleteAirport(airportDTO);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void whenCallPUTAirportsThenReturnResponseEntity() throws AirPortDaoException {
        ResponseEntity responseEntity = airportController.updateAirport(airportDTO);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    private AirportDTO initAirportDTO() {
        var airportDTO = new AirportDTO();
        airportDTO.setApId(1);
        airportDTO.setApName("Texas");
        airportDTO.setApNum(1340);
        airportDTO.setApType("international");
        airportDTO.setAddress("Dallas");
        airportDTO.setTerminalCount(10);
        return airportDTO;
    }
}
