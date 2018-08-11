package com.ra.airport.servlet.handler;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.airport.entity.Airport;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.AirPortService;
import com.ra.airport.service.AirportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteAirportHandler implements ServletHandler {

    private final transient AirPortService<Airport> airportService;

    @Autowired
    public DeleteAirportHandler(final AirportServiceImpl airportService) {
        this.airportService = airportService;
    }

    @Override
    public void post(final HttpServletRequest request, final HttpServletResponse response) throws AirPortDaoException {
        final String jsonAirport = (String) request.getAttribute("airport");
        final Gson gson = new Gson();
        final Airport airport = gson.fromJson(jsonAirport, Airport.class);
        airportService.delete(airport);
        request.setAttribute("jspPath", "deleteAirport");
    }

    @Override
    public void get(final HttpServletRequest request, final HttpServletResponse response) throws AirPortDaoException {
        post(request, response);
    }
}
