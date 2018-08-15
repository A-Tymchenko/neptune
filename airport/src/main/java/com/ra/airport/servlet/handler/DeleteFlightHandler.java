package com.ra.airport.servlet.handler;

import java.util.*;
import com.ra.airport.entity.Flight;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.FlightService;
import javax.naming.OperationNotSupportedException;
import javax.servlet.http.*;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteFlightHandler implements ServletHandler {

    private final transient FlightService flightService;

    @Autowired
    public DeleteFlightHandler(FlightService flightService) {
        this.flightService = flightService;
    }

    @Override
    public void post(HttpServletRequest request, HttpServletResponse response) throws AirPortDaoException {
        delete(request, response);
        request.setAttribute("jspPath","/flights");
    }

    @Override
    public void delete(HttpServletRequest request, HttpServletResponse response) throws AirPortDaoException {
        String flightId = request.getParameter("id");
        if (Strings.isNotBlank(flightId)) {
            Flight flight = new Flight();
            flight.setFlId(Integer.parseInt(flightId));
            flightService.delete(flight);
        }
    }
}
