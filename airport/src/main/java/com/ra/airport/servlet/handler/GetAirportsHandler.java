package com.ra.airport.servlet.handler;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.airport.dto.AirportDTO;
import com.ra.airport.entity.Airport;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.AirPortService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetAirportsHandler implements ServletHandler {

    private final transient AirPortService<Airport> airportService;

    @Autowired
    public GetAirportsHandler(final AirPortService<Airport> airportService) {
        this.airportService = airportService;
    }

    @Override
    public void post(final HttpServletRequest request, final HttpServletResponse response) throws AirPortDaoException {
        this.get(request, response);
    }

    @Override
    public void get(final HttpServletRequest request, final HttpServletResponse response) throws AirPortDaoException {
        final List<Airport> airports = airportService.getAll();
        final List<AirportDTO> result = new ArrayList<>();
        airports.forEach(airport -> {
            result.add(createAirportDTO(airport));
        });
        request.setAttribute("airports", result);
        request.setAttribute("jspPath", "showAllAirports.jsp");
    }

    @Override
    public void delete(final HttpServletRequest request, final HttpServletResponse response) throws AirPortDaoException {

    }

    @Override
    public void put(final HttpServletRequest request, final HttpServletResponse response) throws AirPortDaoException {

    }

    private AirportDTO createAirportDTO(Airport airport) {
        final AirportDTO airportDTO = new AirportDTO();
        BeanUtils.copyProperties(airport, airportDTO);
        return airportDTO;
    }
}
