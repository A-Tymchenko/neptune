package com.ra.airport.servlet.handler;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.airport.entity.Flight;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.FlightService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Process /flights user request.
 */
@Component
public class GetFlightsHandler implements ServletHandler {

    private static final Logger LOGGER = LogManager.getLogger(GetFlightsHandler.class);

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
        try {
            response.getWriter().println(flights);
        } catch (IOException e) {
           LOGGER.error(e);
        }
        // call jsp
    }
}
