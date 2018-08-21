package com.ra.airport.servlet.handler;

import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.airport.dto.FlightDto;
import com.ra.airport.entity.Flight;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.FlightService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("/flight/create")
public class CreateFlightHandler implements ServletHandler {

    private final transient FlightService flightService;

    @Autowired
    public CreateFlightHandler(final FlightService flightService) {
        this.flightService = flightService;
    }

    @Override
    public void post(final HttpServletRequest request, final HttpServletResponse response) throws AirPortDaoException {
        final FlightDto flightDto = createFlightDto(request);
        final Flight flight = new Flight();
        BeanUtils.copyProperties(flightDto, flight);
        flightService.create(flight);

        request.setAttribute("jspPath", "/flights");
    }

    @Override
    public void get(final HttpServletRequest request, final HttpServletResponse response) {
        request.setAttribute("jspPath", "/flight/create_flight.jsp");
    }

    private FlightDto createFlightDto(final HttpServletRequest request) {
        final FlightDto flightDto = new FlightDto();
        flightDto.setName(request.getParameter("name"));
        flightDto.setCarrier(request.getParameter("carrier"));
        flightDto.setFare(Double.parseDouble(request.getParameter("fare")));
        flightDto.setMealOn(Boolean.parseBoolean(request.getParameter("mealOn")));
        flightDto.setDepartureDate(LocalDateTime.parse(request.getParameter("departureDate")));
        flightDto.setArrivalDate(LocalDateTime.parse(request.getParameter("arrivalDate")));

        return flightDto;
    }
}
