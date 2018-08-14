package com.ra.airport;

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
    private List<Airport> airports;

    @InjectMocks
    private AirportServiceImpl airportService;

    @BeforeEach
    public void init() throws AirPortDaoException {
        MockitoAnnotations.initMocks(this);
        airport = new Airport(8,"Kenedy", 4949034, "International", "USA New Yourk", 10);
        airports = new ArrayList<>();
        airports.add(airport);
        Mockito.when(airportDAO.create(airport)).thenReturn(airport);
        Mockito.when(airportDAO.update(airport)).thenReturn(airport);
        Mockito.when(airportDAO.delete(airport)).thenReturn(true);
        Mockito.when(airportDAO.getById(8)).thenReturn(Optional.ofNullable(airport));
        Mockito.when(airportDAO.getAll()).thenReturn(airports);
    }

    @Test
    public void whenCreateThenReturnCreatedAirport() throws AirPortDaoException {
        assertEquals(airportService.create(airport), airport);
    }

    @Test
    public void whenUpdateThenReturnUpdatedAirport() throws AirPortDaoException {
        assertEquals(airportService.update(airport), airport);
    }

    @Test
    public void whenDeleteThenReturnDeletedAirport() throws AirPortDaoException {
        assertEquals(airportService.delete(airport), true);
    }

    @Test
    public void whenGetByIdThenReturnAirport() throws AirPortDaoException {
        assertEquals(airportService.getById(8).get(), airport);
    }

    @Test
    public void whenGetAllThenReturnCreatedAirports() throws AirPortDaoException {
        assertEquals(airportService.getAll(), airports);
    }
}
