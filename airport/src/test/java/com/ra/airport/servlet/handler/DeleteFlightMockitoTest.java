package com.ra.airport.servlet.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import com.ra.airport.entity.Flight;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class DeleteFlightMockitoTest {

    private Flight flight;

    private DeleteFlightHandler deleteFlightHandler;

    private MockHttpServletRequest mockRequest;

    private MockHttpServletResponse mockResponse;

    @Mock
    private FlightService mockFlightService;

    @BeforeEach
    public void init() throws AirPortDaoException {
        MockitoAnnotations.initMocks(this);
        flight = new Flight();
        flight.setFlId(1);
        mockRequest = new MockHttpServletRequest();
        mockResponse = new MockHttpServletResponse();
        deleteFlightHandler = mock(DeleteFlightHandler.class);
        doCallRealMethod().when(deleteFlightHandler).post(mockRequest, mockResponse);
    }

    @Test
    public void whenDeleteThenCorrectEntityShouldBePassToServiceMethod() throws AirPortDaoException {
        deleteFlightHandler = new DeleteFlightHandler(mockFlightService);
        mockRequest.setParameter("id","1");
        deleteFlightHandler.delete(mockRequest, mockResponse);
        verify(mockFlightService,times(1)).delete(flight);
    }

    @Test
    public void whenPostThenDeleteShouldBeExecutedAndPathToFlightsTableShouldBeSet() throws AirPortDaoException {
          deleteFlightHandler.post(mockRequest, mockResponse);
          verify(deleteFlightHandler, times(1)).delete(mockRequest, mockResponse);
          String result = (String) mockRequest.getAttribute("jspPath");

          assertEquals("/flights", result);
    }
}
