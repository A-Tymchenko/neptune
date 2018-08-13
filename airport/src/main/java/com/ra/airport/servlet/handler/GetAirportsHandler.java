package com.ra.airport.servlet.handler;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.airport.entity.Airport;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.AirPortService;
import com.ra.airport.service.AirportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetAirportsHandler implements ServletHandler {

    private final transient AirPortService<Airport> airportService;

    @Autowired
    public GetAirportsHandler(final AirportServiceImpl airportService) {
        this.airportService = airportService;
    }

    @Override
    public void post(final HttpServletRequest request, final HttpServletResponse response) throws AirPortDaoException {

    }

    @Override
    public void get(final HttpServletRequest request, final HttpServletResponse response) throws AirPortDaoException {
        final List<Airport> list = airportService.getAll();
        request.setAttribute("airports", list);
        request.setAttribute("jspPath", "showAllAirports.jsp");
    }

    @Override
    public void delete(final HttpServletRequest request, final HttpServletResponse response) throws AirPortDaoException {

    }

    @Override
    public void put(final HttpServletRequest request, final HttpServletResponse response) throws AirPortDaoException {

    }
}
