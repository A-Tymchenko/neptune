package com.ra.airport.service;

import com.ra.airport.dto.AirportDTO;
import com.ra.airport.entity.Airport;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.repository.impl.AirportDAOImpl;
import com.ra.airport.service.AirportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AirportServiceImplMockitoTest {

    @Mock
    private AirportDAOImpl airportDAO;
    private Airport airport;
    private AirportDTO airportDTO;
    private List<Airport> airports;

    @InjectMocks
    private AirportServiceImpl airportService;

    @BeforeEach
    public void init() throws AirPortDaoException {
        MockitoAnnotations.initMocks(this);
        initAirportDTO();
        airport = new Airport(8,"Kenedy", 4949034, "International", "USA New Yourk", 10);
        airports = new ArrayList<>();
        airports.add(airport);
        Mockito.when(airportDAO.create(Mockito.any())).thenReturn(airport);
        Mockito.when(airportDAO.update(Mockito.any())).thenReturn(airport);
        Mockito.when(airportDAO.delete(Mockito.any())).thenReturn(true);
        Mockito.when(airportDAO.getById(8)).thenReturn(Optional.ofNullable(airport));
        Mockito.when(airportDAO.getAll()).thenReturn(airports);
    }

    @Test
    public void whenCreateThenReturnCreatedAirport() throws AirPortDaoException {
        assertEquals(airportService.create(airportDTO), airportDTO);
    }

    @Test
    public void whenUpdateThenReturnUpdatedAirport() throws AirPortDaoException {
        assertEquals(airportService.update(airportDTO), airportDTO);
    }

    @Test
    public void whenDeleteThenReturnDeletedAirport() throws AirPortDaoException {
        assertEquals(airportService.delete(airportDTO), true);
    }

    @Test
    public void whenGetAllThenReturnCreatedAirports() throws AirPortDaoException {
        assertEquals(airportService.getAll().size(), airports.size());
    }

    private AirportDTO initAirportDTO() {
        airportDTO = new AirportDTO();
        airportDTO.setApId(1);
        airportDTO.setApName("Texas");
        airportDTO.setApNum(1340);
        airportDTO.setApType("international");
        airportDTO.setAddress("Dallas");
        airportDTO.setTerminalCount(10);
        return airportDTO;
    }
}
