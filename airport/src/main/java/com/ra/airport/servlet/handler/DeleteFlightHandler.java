package com.ra.airport.servlet.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.airport.entity.Flight;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.FlightService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("/flight/delete")
public class DeleteFlightHandler implements ServletHandler {

    private final transient FlightService flightService;

    @Autowired
    public DeleteFlightHandler(final FlightService flightService) {
        this.flightService = flightService;
    }

    @Override
    public void post(final HttpServletRequest request, final HttpServletResponse response) throws AirPortDaoException {
        delete(request, response);
        request.setAttribute("jspPath", "/flights");
    }

    @Override
    public void delete(final HttpServletRequest request, final HttpServletResponse response) throws AirPortDaoException {
        final String flightId = request.getParameter("id");
        if (Strings.isNotBlank(flightId)) {
            final Flight flight = new Flight();
            flight.setFlId(Integer.parseInt(flightId));
            flightService.delete(flight);
        }
    }
}
