package com.ra.airport.controller;

import com.ra.airport.entity.Airport;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.AirportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final String REQUEST_PATH = "/airport";

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
    public String createAirport(final @RequestBody Airport airport, final Model model) throws AirPortDaoException {
        model.addAttribute("airport", service.create(airport));
        return "airport/create_airport";
    }

    /**
     * airport/ DELETE controller.
     * @return airport/show_airports
     */
    @DeleteMapping(REQUEST_PATH)
    public String deleteAirport(@RequestBody final Airport airport, final Model model) throws AirPortDaoException {
        model.addAttribute("airport", service.delete(airport));
        return "airport/show_airports";
    }

    /**
     * airport/ PUT controller.
     * @return airport/show_airports
     */
    @PutMapping(REQUEST_PATH)
    public String updateAirport(@RequestBody final Airport airport, final Model model) throws AirPortDaoException {
        model.addAttribute("airport", service.update(airport));
        return "airport/show_airports";
    }
}
