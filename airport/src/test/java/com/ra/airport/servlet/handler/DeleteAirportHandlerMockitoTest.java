package com.ra.airport.servlet.handler;

import com.ra.airport.entity.Airport;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.AirportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class DeleteAirportHandlerMockitoTest {

    @Mock
    private AirportServiceImpl airportService;

    @InjectMocks
    private DeleteAirportHandler airportHandler;
    private MockHttpServletRequest mockRequest;
    private MockHttpServletResponse mockResponse;
    private Airport airport;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        airport = new Airport(1,null, 0, null, null, 0);
        mockRequest = new MockHttpServletRequest();
        mockResponse = new MockHttpServletResponse();
        mockRequest.setParameter("apId", "1");
    }

    @Test
    public void whenDeleteAirportThenAirportShoudBeDeleted() throws AirPortDaoException {
        airportHandler.delete(mockRequest, mockResponse);
        Mockito.verify(airportService, Mockito.times(1)).delete(airport);
    }

    @Test
    public void whenPostAirportThenAirportShoudBeDeleted() throws AirPortDaoException {
        airportHandler.post(mockRequest, mockResponse);
        Mockito.verify(airportService, Mockito.times(1)).delete(airport);
    }

    @Test
    void whenIdIsBlanckThenNothingShoudBeDleted() throws AirPortDaoException {
        mockRequest.setParameter("apId", "");
        airportHandler.delete(mockRequest, mockResponse);
    }
}
