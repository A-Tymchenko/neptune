package com.ra.airport.servlet.handler;

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
public class UpdateAirportHandler implements ServletHandler {

    private final transient AirPortService<Airport> airportService;

    @Autowired
    public UpdateAirportHandler(final AirPortService<Airport> airportService) {
        this.airportService = airportService;
    }

    @Override
    public void post(final HttpServletRequest request, final HttpServletResponse response) throws AirPortDaoException {
        final AirportDTO airportDTO = createAirportDTO(request);
        final var airport = new Airport();
        BeanUtils.copyProperties(airportDTO, airport);
        airportService.update(airport);
    }

    @Override
    public void get(final HttpServletRequest request, final HttpServletResponse response) throws AirPortDaoException {

    }

    @Override
    public void delete(final HttpServletRequest request, final HttpServletResponse response) throws AirPortDaoException {

    }

    @Override
    public void put(final HttpServletRequest request, final HttpServletResponse response) throws AirPortDaoException {

    }

    private AirportDTO createAirportDTO(final HttpServletRequest request) {
        final var airportDTO = new AirportDTO();
        airportDTO.setApId(Integer.parseInt(request.getParameter("apId")));
        airportDTO.setApName(request.getParameter("apName"));
        airportDTO.setApNum(Integer.parseInt(request.getParameter("apNum")));
        airportDTO.setApType(request.getParameter("apType"));
        airportDTO.setAddress(request.getParameter("address"));
        airportDTO.setTerminalCount(Integer.parseInt(request.getParameter("terminalCount")));
        return airportDTO;
    }
}
