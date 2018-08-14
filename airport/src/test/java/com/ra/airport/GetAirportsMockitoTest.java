package com.ra.airport;

import com.ra.airport.entity.Airport;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.AirportServiceImpl;
import com.ra.airport.servlet.handler.CreateAirportHandler;
import com.ra.airport.servlet.handler.GetAirportsHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GetAirportsMockitoTest {

    @Mock
    private List<Airport> airports;

    @InjectMocks
    public GetAirportsHandler airportHandler;
    private MockHttpServletRequest mockRequest;
    private MockHttpServletResponse mockResponse;

    @Mock
    Airport airport;

    @Mock
    private AirportServiceImpl airportService;

    @BeforeEach
    public void init() throws AirPortDaoException {
        MockitoAnnotations.initMocks(this);
        mockRequest = new MockHttpServletRequest();
        mockResponse = new MockHttpServletResponse();
        airportHandler = new GetAirportsHandler(airportService);
        Mockito.when(airportService.getAll()).thenReturn(airports);
    }

    @Test
    public void whenGetAirportsThenShoudBeJSPSet() throws AirPortDaoException {
        Mockito.doAnswer(invocation -> {
            ((Consumer)invocation.getArguments()[0]).accept(airport);
            return null;
        }).when(airports).forEach(Mockito.any());
        airportHandler.get(mockRequest, mockResponse);
        Mockito.verify(airportService, Mockito.times(1)).getAll();
    }

    @Test void testPutDeletePostMethods() throws AirPortDaoException {
        airportHandler.put(mockRequest, mockResponse);
        airportHandler.post(mockRequest, mockResponse);
        airportHandler.delete(mockRequest, mockResponse);
    }
}
