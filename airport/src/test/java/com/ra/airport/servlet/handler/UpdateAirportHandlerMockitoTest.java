package com.ra.airport.servlet.handler;

import com.ra.airport.entity.Airport;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.AirportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class UpdateAirportHandlerMockitoTest {

    private UpdateAirportHandler airportHandler;
    private MockHttpServletRequest mockRequest;
    private MockHttpServletResponse mockResponse;
    private Airport airport;

    @Mock
    private AirportServiceImpl airportService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        airport = new Airport(1,"Kenedy", 4949034, "International", "USA New Yourk", 10);
        mockRequest = new MockHttpServletRequest();
        mockResponse = new MockHttpServletResponse();
        airportHandler = new UpdateAirportHandler(airportService);
        createRequest(mockRequest);
    }

    @Test
    public void whenUpdateThenAirportShoudBeUpdated() throws AirPortDaoException {
        airportHandler.post(mockRequest, mockResponse);
        Mockito.verify(airportService, Mockito.times(1)).update(airport);
    }

    private void createRequest(MockHttpServletRequest mockRequest) {
        mockRequest.setParameter("apId", "1");
        mockRequest.setParameter("apName", "Kenedy");
        mockRequest.setParameter("apNum", "4949034");
        mockRequest.setParameter("apType", "International");
        mockRequest.setParameter("address", "USA New Yourk");
        mockRequest.setParameter("terminalCount", "10");
    }
}
