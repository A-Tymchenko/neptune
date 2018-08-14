package com.ra.airport;

import com.ra.airport.entity.Airport;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.repository.impl.AirportDAOImpl;
import com.ra.airport.service.AirportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

public class AirportServiceImplMockitoTest {

    @Mock
    private AirportDAOImpl airportDAO;
    private AirportServiceImpl airportService;
    private Airport airport;
    private List<Airport> airports;

    @BeforeEach
    public void init() throws AirPortDaoException {
        MockitoAnnotations.initMocks(this);
        airportService = new AirportServiceImpl(airportDAO);
        airport = new Airport(8,"Kenedy", 4949034, "International", "USA New Yourk", 10);
        Mockito.when(airportDAO.create(airport)).thenReturn(airport);
        Mockito.when(airportDAO.update(airport)).thenReturn(airport);
        Mockito.when(airportDAO.delete(airport)).thenReturn(true);
        Mockito.when(airportDAO.getById(8)).thenReturn(Optional.ofNullable(airport));
        Mockito.when(airportDAO.create(airport)).thenReturn(airport);
    }

    @Test
    public void whenCreateThenReturnCreatedAirport() throws AirPortDaoException {
        airportService.create(airport);
    }

    @Test
    public void whenUpdateThenReturnUpdatedAirport() throws AirPortDaoException {
        airportService.update(airport);
    }

    @Test
    public void whenDeleteThenReturnDeletedAirport() throws AirPortDaoException {
        airportService.delete(airport);
    }

    @Test
    public void whenGetByIdThenReturnAirport() throws AirPortDaoException {
        airportService.getById(8);
    }

    @Test
    public void whenGetAllThenReturnCreatedAirports() throws AirPortDaoException {
        airportService.getAll();
    }
}
