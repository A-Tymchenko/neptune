package com.ra.airport.servlet.handler;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.airport.entity.Flight;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Process /flights user request.
 */
@Component
public class GetAllFlightsHandler implements ServletHandler {

    private FlightService flightService;

    @Autowired
    public GetAllFlightsHandler(FlightService flightService) {
        this.flightService = flightService;
    }

    @Override
    public void post(HttpServletRequest request, HttpServletResponse response) throws AirPortDaoException {
       get(request, response);
    }

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws AirPortDaoException {
        List<Flight> flights = flightService.getAll();
        // call jsp
    }
}
