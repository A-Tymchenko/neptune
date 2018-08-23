package com.ra.airport.controller;

import com.ra.airport.dto.FlightDto;
import com.ra.airport.entity.Flight;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.FlightService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for CRUD operations with {@link com.ra.airport.entity.Flight} entity.
 */
@Controller
public class FlightController {

    private FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @RequestMapping(value = "/flights/{flightId}", method = RequestMethod.PUT)
    public String updateFlight(@PathVariable Integer flightId) {
        return "/index";
    }

    @RequestMapping(value = "/flights", method = RequestMethod.GET)
    public String getFlights() {
        return "/index";
    }

    @RequestMapping(value = "/flights", method = RequestMethod.POST)
    public ResponseEntity<FlightDto> createFlights(final @RequestBody FlightDto flightDto) throws AirPortDaoException {
       final var flight = new Flight();
       BeanUtils.copyProperties(flightDto, flight);
       BeanUtils.copyProperties(flightService.create(flight), flightDto);
       return new ResponseEntity<>(flightDto, HttpStatus.OK);
    }
}