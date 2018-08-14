package com.ra.airport;

import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.AirportServiceImpl;
import com.ra.airport.servlet.handler.DeleteAirportHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class DeleteAirportMockitoTest {

    private DeleteAirportHandler airportHandler;
    private MockHttpServletRequest mockRequest;
    private MockHttpServletResponse mockResponse;

    @Mock
    private AirportServiceImpl airportService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockRequest = new MockHttpServletRequest();
        mockResponse = new MockHttpServletResponse();
        airportHandler = new DeleteAirportHandler(airportService);
        mockRequest.setParameter("apId", "1");
    }

    @Test
    public void whenDeleteAirportThenAirportShoudBeDeleted() throws AirPortDaoException {
        airportHandler.delete(mockRequest, mockResponse);
    }

    @Test void testPutPostGetMethods() throws AirPortDaoException {
        airportHandler.put(mockRequest, mockResponse);
        airportHandler.get(mockRequest, mockResponse);
        airportHandler.post(mockRequest, mockResponse);
    }

    @Test
    void whenIdIsBlanckThenNothingShoudBeDleted() throws AirPortDaoException {
        mockRequest.setParameter("apId", "");
        airportHandler.delete(mockRequest, mockResponse);
    }
}
