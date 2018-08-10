package com.ra.airport.servlet.handler;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.airport.dto.FlightDto;
import com.ra.airport.entity.Flight;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.FlightService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Process /flights user request.
 */
@Component
public class GetFlightsHandler implements ServletHandler {

    private final transient FlightService flightService;

    @Autowired
    public GetFlightsHandler(final FlightService flightService) {
        this.flightService = flightService;
    }

    @Override
    public void post(final HttpServletRequest request, final HttpServletResponse response) throws AirPortDaoException {
       get(request, response);
    }

    @Override
    public void get(final HttpServletRequest request, final HttpServletResponse response) throws AirPortDaoException {
       final List<Flight> flights = flightService.getAll();
       final List<FlightDto> result = new ArrayList<>();
       for (final Flight flight : flights) {
            result.add(createFlightDto(flight));
        }
        request.setAttribute("flights", result);
        request.setAttribute("jspPath", "WEB-INF/flights.jsp");
    }

    private FlightDto createFlightDto(final Flight flight) {
        final FlightDto flightDto = new FlightDto();
        BeanUtils.copyProperties(flight, flightDto);
        return flightDto;
    }

    @Override
    public void delete(final HttpServletRequest request, final HttpServletResponse response) {

    }

    @Override
    public void put(final HttpServletRequest request, final HttpServletResponse response) {

    }
}
