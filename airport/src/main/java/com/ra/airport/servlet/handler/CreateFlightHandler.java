package com.ra.airport.servlet.handler;

import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ra.airport.dto.FlightDto;
import com.ra.airport.repository.exception.AirPortDaoException;
import org.springframework.stereotype.Component;

@Component
public class CreateFlightHandler implements ServletHandler {

    @Override
    public void post(HttpServletRequest request, HttpServletResponse response) throws AirPortDaoException {
        String name = request.getParameter("name");
        String carrier = request.getParameter("carrier");
        Map<String, String[]> parameterMap = request.getParameterMap();
        createFlightDto(request);
        String fare = request.getParameter("fare");
        String departureDate = request.getParameter("departureDate");
        String arrivalDate = request.getParameter("arrivalDate");
        String mealOn = request.getParameter("mealOn");
    }

    private FlightDto createFlightDto(HttpServletRequest request) {
        FlightDto flightDto = new FlightDto();
        flightDto.setName(request.getParameter("name"));
        flightDto.setCarrier(request.getParameter("carrier"));
        return flightDto;
    }

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws AirPortDaoException {
        request.setAttribute("jspPath","WEB-INF/create_flight.jsp");
    }
}
