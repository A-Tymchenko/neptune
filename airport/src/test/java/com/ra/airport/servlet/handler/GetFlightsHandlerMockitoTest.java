package com.ra.airport.servlet.handler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import static org.mockito.Mockito.when;

public class GetFlightsHandlerMockitoTest {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DEPARTURE_DATE = "2018-06-17 13:15:00";
    private static final String ARRIVAL_DATE = "2018-06-17 15:16:00";

    private GetFlightsHandler flightsHandler;

    private Flight flight;

    private MockHttpServletRequest mockRequest;

    private MockHttpServletResponse mockResponse;

    @Mock
    private FlightService mockFlightService;

    @BeforeEach
    public void init() throws AirPortDaoException {
        MockitoAnnotations.initMocks(this);
        flight = createFlight();
        List<Flight> flights = new ArrayList<>(Arrays.asList(flight));

        mockRequest = new MockHttpServletRequest();
        mockResponse = new MockHttpServletResponse();

        when(mockFlightService.getAll()).thenReturn(flights);

        flightsHandler = new GetFlightsHandler(mockFlightService);
    }

    @Test
    public void whenGetThenFlightDTOsShouldBeSetToRequest() throws AirPortDaoException {
        flightsHandler.get(mockRequest, mockResponse);
        List<FlightDto> result = (List<FlightDto>) mockRequest.getAttribute("flights");

        assertEquals(result.size(), 1);

        FlightDto flightDto = result.iterator().next();

        assertAll("flightDto",
                  () -> assertEquals(flight.getFlId(), flightDto.getFlId()),
                  () -> assertEquals(flight.getName(), flightDto.getName()),
                  () -> assertEquals(flight.getCarrier(), flightDto.getCarrier()),
                  () -> assertEquals(flight.getFare(), flightDto.getFare()),
                  () -> assertEquals(flight.getMealOn(), flightDto.getMealOn()),
                  () -> assertEquals(flight.getDepartureDate(), flightDto.getDepartureDate()),
                  () -> assertEquals(flight.getArrivalDate(), flightDto.getArrivalDate())
        );
    }


    private Flight createFlight() {
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

        return flight;
    }
}
