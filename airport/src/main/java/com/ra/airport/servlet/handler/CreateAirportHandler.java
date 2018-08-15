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
public class CreateAirportHandler implements ServletHandler {

    private final transient AirPortService<Airport> airportService;

    @Autowired
    public CreateAirportHandler(final AirPortService<Airport> airportService) {
        this.airportService = airportService;
    }

    @Override
    public void post(final HttpServletRequest request, final HttpServletResponse response) throws AirPortDaoException {
        final var airportDTO = createAirportDTO(request);
        final var airport = new Airport();
        BeanUtils.copyProperties(airportDTO, airport);
        airportService.create(airport);
        request.setAttribute("jspPath", "createAirport.jsp");
    }

    private AirportDTO createAirportDTO(final HttpServletRequest request) {
        final var airportDTO = new AirportDTO();
        airportDTO.setApName(request.getParameter("apName"));
        airportDTO.setApNum(Integer.parseInt(request.getParameter("apNum")));
        airportDTO.setApType(request.getParameter("apType"));
        airportDTO.setAddress(request.getParameter("address"));
        airportDTO.setTerminalCount(Integer.parseInt(request.getParameter("terminalCount")));
        return airportDTO;
    }
}
