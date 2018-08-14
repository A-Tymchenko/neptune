package com.ra.airport;

import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.AirportServiceImpl;
import com.ra.airport.servlet.handler.UpdateAirportHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class UpdateAirportMockitoTest {

    private UpdateAirportHandler airportHandler;
    private MockHttpServletRequest mockRequest;
    private MockHttpServletResponse mockResponse;

    @Mock
    private AirportServiceImpl airportService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockRequest = new MockHttpServletRequest();
        mockResponse = new MockHttpServletResponse();
        airportHandler = new UpdateAirportHandler(airportService);
        createRequest(mockRequest);
    }

    @Test
    public void whenUpdateThenAirportShoudBeUpdated() throws AirPortDaoException {
        airportHandler.post(mockRequest, mockResponse);
    }

    @Test void testPutDeleteGetMethods() throws AirPortDaoException {
        airportHandler.put(mockRequest, mockResponse);
        airportHandler.get(mockRequest, mockResponse);
        airportHandler.delete(mockRequest, mockResponse);
    }

    private void createRequest(MockHttpServletRequest mockRequest) {
        mockRequest.setParameter("apId", "1");
        mockRequest.setParameter("apName", "name");
        mockRequest.setParameter("apNum", "5");
        mockRequest.setParameter("apType", "type");
        mockRequest.setParameter("address", "address");
        mockRequest.setParameter("terminalCount", "10");
    }
}
