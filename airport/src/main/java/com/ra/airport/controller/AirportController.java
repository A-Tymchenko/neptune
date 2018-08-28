package com.ra.airport.controller;

import javax.validation.Valid;

import com.ra.airport.dto.AirportDTO;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.AirportServiceImpl;
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

@Controller
public class AirportController {

    private final transient AirportServiceImpl service;
    private static final String REQUEST_PATH = "/airports";

    @Autowired
    public AirportController(final AirportServiceImpl service) {
        this.service = service;
    }

    /**
     * airport/ GET controller.
     * @return airport/show_airports
     */
    @GetMapping(REQUEST_PATH)
    public String getAirports(final Model model) throws AirPortDaoException {
        model.addAttribute("airports", service.getAll());
        return "airport/show_airports";
    }

    /**
     * airport/ POST controller.
     * @return airport/create_airport
     */
    @PostMapping(REQUEST_PATH)
    public ResponseEntity<AirportDTO> createAirport(@Valid @RequestBody final AirportDTO airportDTO) throws AirPortDaoException {
        return new ResponseEntity<>(service.create(airportDTO), HttpStatus.OK);
    }

    /**
     * airport/ DELETE controller.
     * @return airport/show_airports
     */
    @DeleteMapping(REQUEST_PATH)
    public ResponseEntity<Boolean> deleteAirport(@Valid @RequestBody final AirportDTO airportDTO) throws AirPortDaoException {
        return new ResponseEntity<>(service.delete(airportDTO), HttpStatus.OK);
    }

    /**
     * airport/ PUT controller.
     * @return airport/show_airports
     */
    @PutMapping(REQUEST_PATH)
    public ResponseEntity<AirportDTO> updateAirport(@Valid @RequestBody final AirportDTO airportDTO) throws AirPortDaoException {
        return new ResponseEntity<>(service.update(airportDTO), HttpStatus.OK);
    }
}
