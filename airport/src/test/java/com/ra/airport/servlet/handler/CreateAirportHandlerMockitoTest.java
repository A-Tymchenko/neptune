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

public class CreateAirportHandlerMockitoTest {

    @Mock
    private AirportServiceImpl airportService;

    @InjectMocks
    private CreateAirportHandler airportHandler;
    private MockHttpServletRequest mockRequest;
    private MockHttpServletResponse mockResponse;
    private Airport airport;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        airport = new Airport(null,"Kenedy", 4949034, "International", "USA New Yourk", 10);
        mockRequest = new MockHttpServletRequest();
        mockResponse = new MockHttpServletResponse();
        createRequest(mockRequest);
    }

    @Test
    public void whenCreateThenNewAirportShoudBeCreated() throws AirPortDaoException {
        airportHandler.post(mockRequest, mockResponse);
        Mockito.verify(airportService, Mockito.times(1)).create(airport);
    }

    private void createRequest(MockHttpServletRequest mockRequest) {
        mockRequest.setParameter("apName", "Kenedy");
        mockRequest.setParameter("apNum", "4949034");
        mockRequest.setParameter("apType", "International");
        mockRequest.setParameter("address", "USA New Yourk");
        mockRequest.setParameter("terminalCount", "10");
    }
}
