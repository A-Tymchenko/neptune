package com.ra.airport.controller;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import com.ra.airport.dto.FlightDto;
import com.ra.airport.entity.Flight;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.FlightService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    private static final String REQUEST_PATH = "/flights";
    private FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }


    @RequestMapping(value = REQUEST_PATH, method = RequestMethod.POST)
    public ResponseEntity<Flight> createFlights(final @Valid @RequestBody FlightDto flightDto) throws AirPortDaoException {
        final var flight = new Flight();
        BeanUtils.copyProperties(flightDto, flight);
        return new ResponseEntity<>(flightService.create(flight), HttpStatus.OK);
    }

    @RequestMapping(value = REQUEST_PATH, method = RequestMethod.PUT)
    public ResponseEntity<Flight> updateFlight(final @Valid @RequestBody FlightDto flightDto) throws AirPortDaoException {
        final var flight = new Flight();
        BeanUtils.copyProperties(flightDto, flight);
        return new ResponseEntity<>(flightService.update(flight), HttpStatus.OK);
    }

    @RequestMapping(value = REQUEST_PATH, method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteFlight(final @Valid @RequestBody FlightDto flightDto) throws AirPortDaoException {
        final var flight = new Flight();
        BeanUtils.copyProperties(flightDto, flight);
        return new ResponseEntity<>(flightService.delete(flight), HttpStatus.OK);
    }
    
    @RequestMapping(value = REQUEST_PATH, method = RequestMethod.GET)
    public String getFlights(final Model model) throws AirPortDaoException {
        List<Flight> flights = flightService.getAll();
        model.addAttribute("flights",flights);

        return "flight/flights";
    }
}