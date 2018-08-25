package com.ra.airport.controller;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for CRUD operations with {@link com.ra.airport.entity.Flight} entity.
 */

@Controller
@RequestMapping(value = "/flights")
public class FlightController {

    private FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public String getFlights(final Model model) throws AirPortDaoException {
        List<Flight> flights = flightService.getAll();
        model.addAttribute("flights",flights);

        return "flight/flights";
    }

    @PostMapping
    public ResponseEntity<Flight> createFlight(final @Valid @RequestBody FlightDto flightDto) throws AirPortDaoException {
        final var flight = new Flight();
        BeanUtils.copyProperties(flightDto, flight);
        return new ResponseEntity<>(flightService.create(flight), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Flight> updateFlight(final @Valid @RequestBody FlightDto flightDto) throws AirPortDaoException {
        final var flight = new Flight();
        BeanUtils.copyProperties(flightDto, flight);
        return new ResponseEntity<>(flightService.update(flight), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteFlight(final @Valid @RequestBody FlightDto flightDto) throws AirPortDaoException {
        final var flight = new Flight();
        BeanUtils.copyProperties(flightDto, flight);
        return new ResponseEntity<>(flightService.delete(flight), HttpStatus.OK);
    }
}