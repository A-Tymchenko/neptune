package com.ra.airport.servlet.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import java.time.*;
import java.time.format.*;
import com.ra.airport.entity.Flight;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class CreateFlightHandlerMockitoTest {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DEPARTURE_DATE = "2018-06-17 13:15:00";
    private static final String ARRIVAL_DATE = "2018-06-17 15:16:00";

    private Flight flight;

    private CreateFlightHandler createFlightHandler;

    private MockHttpServletRequest mockRequest;

    private MockHttpServletResponse mockResponse;

    @Mock
    private FlightService mockFlightService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        createFlight();
        createFlightHandler = new CreateFlightHandler(mockFlightService);
        mockRequest = new MockHttpServletRequest();
        mockResponse = new MockHttpServletResponse();
        createRequest();
    }

    @Test
    public void whenGetThenPathToJspShouldBeSetToRequest() {
        createFlightHandler.get(mockRequest, mockResponse);
        String result = (String) mockRequest.getAttribute("jspPath");
        assertEquals("/flight/create_ticket.jsp", result);
    }

    @Test
    public void whenPostCorrectFlightShouldBeCreated() throws AirPortDaoException {
        createFlightHandler.post(mockRequest, mockResponse);
        verify(mockFlightService, times(1)).create(flight);
    }

    private void createRequest() {
        mockRequest = new MockHttpServletRequest();
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
        flight.setName("Kyiv-Rome");
        flight.setCarrier("Wizz Air");
        flight.setMealOn(true);
        flight.setFare(100.0);
        flight.setDepartureDate(departureDate);
        flight.setArrivalDate(arrivalDate);
    }
}
