package com.ra.airport.controller;

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
@RequestMapping("/flights")
@Controller
public class FlightController {

    private final transient FlightService flightService;

    @Autowired
    public FlightController(final FlightService flightService) {
        this.flightService = flightService;
    }

    /**
     * Return view of flights.
     * @param model object
     * @return path to view
     * @throws AirPortDaoException dao exception.
     */
    @GetMapping
    public String getFlights(final Model model) throws AirPortDaoException {
        model.addAttribute("flights", flightService.getAll());
        return "flight/flights";
    }

    /**
     * Create new {@link com.ra.airport.entity.Flight} entity.
     * @param flightDto {@link com.ra.airport.dto.FlightDto}
     * @return {@link ResponseEntity} created entity
     * @throws AirPortDaoException dao exception.
     */
    @PostMapping
    public ResponseEntity<FlightDto> createFlight(final @Valid @RequestBody FlightDto flightDto) throws AirPortDaoException {
        return new ResponseEntity<>(flightService.create(flightDto), HttpStatus.OK);
    }

    /**
     * Update {@link com.ra.airport.entity.Flight} entity.
     * @param flightDto {@link com.ra.airport.dto.FlightDto}
     * @return {@link ResponseEntity} updated entity
     * @throws AirPortDaoException dao exception.
     */
    @PutMapping
    public ResponseEntity<FlightDto> updateFlight(final @Valid @RequestBody FlightDto flightDto) throws AirPortDaoException {
        return new ResponseEntity<>(flightService.update(flightDto), HttpStatus.OK);
    }

    /**
     * Delete {@link com.ra.airport.entity.Flight} entity.
     * @param flightDto {@link com.ra.airport.dto.FlightDto}
     * @return {@link ResponseEntity} boolean flag
     * @throws AirPortDaoException dao exception.
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteFlight(final @Valid @RequestBody FlightDto flightDto) throws AirPortDaoException {
        return new ResponseEntity<>(flightService.delete(flightDto), HttpStatus.OK);
    }
}