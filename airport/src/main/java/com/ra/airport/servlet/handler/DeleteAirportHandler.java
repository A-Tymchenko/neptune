package com.ra.airport.servlet.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.airport.entity.Airport;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.AirPortService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("/airport/delete")
public class DeleteAirportHandler implements ServletHandler {

    private final transient AirPortService<Airport> airportService;

    @Autowired
    public DeleteAirportHandler(final AirPortService<Airport> airportService) {
        this.airportService = airportService;
    }

    @Override
    public void post(final HttpServletRequest request, final HttpServletResponse response) throws AirPortDaoException {
        delete(request, response);

    }

    @Override
    public void delete(final HttpServletRequest request, final HttpServletResponse response) throws AirPortDaoException {
        final String airportId = request.getParameter("apId");
        if (Strings.isNotBlank(airportId)) {
            final var airport = new Airport();
            airport.setApId(Integer.parseInt(airportId));
            airportService.delete(airport);
            request.setAttribute("airport", airport);
            request.setAttribute("jspPath", "delete_airport.jsp");
        }
    }
}
