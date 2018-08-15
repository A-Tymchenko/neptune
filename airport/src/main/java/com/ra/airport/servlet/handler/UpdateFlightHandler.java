package com.ra.airport.servlet.handler;

import java.time.*;
import com.ra.airport.dto.FlightDto;
import com.ra.airport.entity.Flight;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.FlightService;
import javax.naming.OperationNotSupportedException;
import javax.servlet.http.*;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateFlightHandler implements ServletHandler {

    private FlightService flightService;

    @Autowired
    public UpdateFlightHandler(FlightService flightService) {
        this.flightService = flightService;
    }

    @Override
    public void post(HttpServletRequest request, HttpServletResponse response) throws AirPortDaoException {
        put(request, response);
    }

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws AirPortDaoException {
        String flightId = request.getParameter("id");
        if (Strings.isNotBlank(flightId)) {
            flightService.getById(Integer.parseInt(flightId)).ifPresent(
                    flight -> {
                        FlightDto flightDto = new FlightDto();
                        BeanUtils.copyProperties(flight, flightDto);
                        request.setAttribute("flight", flightDto);
                    });
        }
        request.setAttribute("jspPath", "WEB-INF/update_flight.jsp");
    }

    @Override
    public void put(HttpServletRequest request, HttpServletResponse response) throws AirPortDaoException {
        FlightDto flightDto = createFlightDto(request);
        Flight flight = new Flight();
        BeanUtils.copyProperties(flightDto, flight);
        flightService.update(flight);

        request.setAttribute("jspPath", "/flights");
    }

    private FlightDto createFlightDto(HttpServletRequest request) {
        FlightDto flightDto = new FlightDto();
        flightDto.setFlId(Integer.parseInt(request.getParameter("id")));
        flightDto.setName(request.getParameter("name"));
        flightDto.setCarrier(request.getParameter("carrier"));
        flightDto.setFare(Double.parseDouble(request.getParameter("fare")));
        flightDto.setMealOn(Boolean.parseBoolean(request.getParameter("mealOn")));
        flightDto.setDepartureDate(LocalDateTime.parse(request.getParameter("departureDate")));
        flightDto.setArrivalDate(LocalDateTime.parse(request.getParameter("arrivalDate")));

        return flightDto;
    }

    @Override
    public void delete(HttpServletRequest request, HttpServletResponse response) throws AirPortDaoException, OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }
}
