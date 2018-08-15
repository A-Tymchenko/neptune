package com.ra.airport.servlet.handler;

import java.time.*;
import com.ra.airport.entity.Flight;
import com.ra.airport.service.FlightService;
import javax.naming.OperationNotSupportedException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ra.airport.dto.FlightDto;
import com.ra.airport.repository.exception.AirPortDaoException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateFlightHandler implements ServletHandler {

    private FlightService flightService;

    @Autowired
    public CreateFlightHandler(FlightService flightService) {
        this.flightService = flightService;
    }

    @Override
    public void post(HttpServletRequest request, HttpServletResponse response) throws AirPortDaoException {
        FlightDto flightDto = createFlightDto(request);
        Flight flight = new Flight();
        BeanUtils.copyProperties(flightDto, flight);
        flightService.create(flight);

        request.setAttribute("jspPath", "/flights");
    }

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("jspPath","WEB-INF/create_flight.jsp");
    }

    private FlightDto createFlightDto(HttpServletRequest request) {
        FlightDto flightDto = new FlightDto();
        flightDto.setName(request.getParameter("name"));
        flightDto.setCarrier(request.getParameter("carrier"));
        flightDto.setFare(Double.parseDouble(request.getParameter("fare")));
        flightDto.setMealOn(Boolean.parseBoolean(request.getParameter("mealOn")));
        flightDto.setDepartureDate(LocalDateTime.parse(request.getParameter("departureDate")));
        flightDto.setArrivalDate(LocalDateTime.parse(request.getParameter("arrivalDate")));

        return flightDto;
    }
}
