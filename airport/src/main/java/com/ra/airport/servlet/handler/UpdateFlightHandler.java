package com.ra.airport.servlet.handler;

import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.FlightService;
import javax.servlet.http.*;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

@Component
public class UpdateFlightHandler implements ServletHandler {

    private FlightService flightService;

    public UpdateFlightHandler(FlightService flightService) {
        this.flightService = flightService;
    }

    @Override
    public void post(HttpServletRequest request, HttpServletResponse response) throws AirPortDaoException {

    }

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws AirPortDaoException {
        String flightId = request.getParameter("id");
        if (Strings.isBlank(flightId)) {
            flightService.getById(Integer.parseInt(flightId)).ifPresent(
                    flight -> request.setAttribute("flight",flight));
        }
        request.setAttribute("jspPath", "WEB-INF/update_flight.jsp");
    }

    @Override
    public void delete(HttpServletRequest request, HttpServletResponse response) throws AirPortDaoException {

    }

    @Override
    public void put(HttpServletRequest request, HttpServletResponse response) throws AirPortDaoException {

    }
}
