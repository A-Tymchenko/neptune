package com.ra.airport.servlet.handler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import com.ra.airport.dto.FlightDto;
import com.ra.airport.entity.Flight;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class UpdateFlightHandlerMockitoTest {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DEPARTURE_DATE = "2018-06-17 13:15:00";
    private static final String ARRIVAL_DATE = "2018-06-17 15:16:00";

    private Flight flight;

    private UpdateFlightHandler updateFlightHandler;

    private MockHttpServletRequest mockRequest;

    private MockHttpServletResponse mockResponse;

    @Mock
    private FlightService mockFlightService;

    @BeforeEach
    public void init() throws AirPortDaoException {
        MockitoAnnotations.initMocks(this);
        createFlight();
        updateFlightHandler = new UpdateFlightHandler(mockFlightService);
        mockResponse = new MockHttpServletResponse();
        createRequest();
        when(mockFlightService.getById(1)).thenReturn(Optional.of(flight));
    }

    @Test
    public void whenGetThenDtoEntityAndPathToJspShouldBeSetToRequest() throws AirPortDaoException {
        updateFlightHandler.get(mockRequest, mockResponse);
        String jspPath = (String) mockRequest.getAttribute("jspPath");
        FlightDto flightDto = (FlightDto) mockRequest.getAttribute("flight");

        assertEquals("/flight/update_flight.jsp", jspPath);

        assertNotNull(flightDto);
    }

    @Test
    public void whenPostThenPutMethodShouldBeCalled() throws AirPortDaoException {
        updateFlightHandler = mock(UpdateFlightHandler.class);
        doCallRealMethod().when(updateFlightHandler).post(mockRequest, mockResponse);
        updateFlightHandler.post(mockRequest, mockResponse);
        
        verify(updateFlightHandler, times(1)).put(mockRequest, mockResponse);
    }

    @Test
    public void whenPutThenFlightShouldBeUpdatedAndPathToJspShouldBeSetToRequest() throws AirPortDaoException {
        updateFlightHandler.put(mockRequest, mockResponse);
        String jspPath = (String) mockRequest.getAttribute("jspPath");

        assertEquals("/flights", jspPath);

        verify(mockFlightService, times(1)).update(flight);
    }

    @Test
    public void whenGetAndIdNotPassedThenPathToJspShouldBeSetToRequest() throws AirPortDaoException {
        mockRequest.setParameter("id", "");
        updateFlightHandler.get(mockRequest, mockResponse);
        String jspPath = (String) mockRequest.getAttribute("jspPath");
        FlightDto flight = (FlightDto) mockRequest.getAttribute("flight");

        assertEquals("/flight/update_flight.jsp", jspPath);
        assertNull(flight);
    }

    private void createRequest() {
        mockRequest = new MockHttpServletRequest();
        mockRequest.setParameter("id",flight.getFlId().toString());
        mockRequest.setParameter("name",flight.getName());
        mockRequest.setParameter("carrier",flight.getCarrier());
        mockRequest.setParameter("fare",flight.getFare().toString());
        mockRequest.setParameter("mealOn",flight.getMealOn().toString());
        mockRequest.setParameter("departureDate",flight.getDepartureDate().toString());
        mockRequest.setParameter("arrivalDate",flight.getArrivalDate().toString());
    }

    private void createFlight() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        LocalDateTime departureDate = LocalDateTime.parse(DEPARTURE_DATE, formatter);
        LocalDateTime arrivalDate = LocalDateTime.parse(ARRIVAL_DATE, formatter);
        flight = new Flight();
        flight.setFlId(1);
        flight.setName("Kyiv-Rome");
        flight.setCarrier("Wizz Air");
        flight.setMealOn(true);
        flight.setFare(100.0);
        flight.setDepartureDate(departureDate);
        flight.setArrivalDate(arrivalDate);
    }
}
