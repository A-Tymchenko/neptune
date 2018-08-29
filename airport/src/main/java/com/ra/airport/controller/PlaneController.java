package com.ra.airport.controller;

import javax.validation.Valid;

import com.ra.airport.dto.PlaneDto;
import com.ra.airport.entity.Plane;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.PlaneService;
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
 * Controller for CRUD operations with {@link Plane} entity.
 */
@RequestMapping("/planes")
@Controller
public class PlaneController {

    private final transient PlaneService planeService;

    @Autowired
    public PlaneController(final PlaneService planeService) {
        this.planeService = planeService;
    }

    /**
     * Return view of planes.
     * @param model object
     * @return path to view
     * @throws AirPortDaoException dao exception.
     */
    @GetMapping
    public String getPlanes(final Model model) throws AirPortDaoException {
        model.addAttribute("planes", planeService.getAll());
        return "plane/planes";
    }

    /**
     * Create new {@link Plane} entity.
     * @param planeDto {@link PlaneDto}
     * @return {@link ResponseEntity} created entity
     * @throws AirPortDaoException dao exception.
     */
    @PostMapping
    public ResponseEntity<PlaneDto> createPlane(final @Valid @RequestBody PlaneDto planeDto) throws AirPortDaoException {
        return new ResponseEntity<>(planeService.create(planeDto), HttpStatus.OK);
    }

    /**
     * Update {@link Plane} entity.
     * @param planeDto {@link PlaneDto}
     * @return {@link ResponseEntity} updated entity
     * @throws AirPortDaoException dao exception.
     */
    @PutMapping
    public ResponseEntity<PlaneDto> updatePlane(final @Valid @RequestBody PlaneDto planeDto) throws AirPortDaoException {
        return new ResponseEntity<>(planeService.update(planeDto), HttpStatus.OK);
    }

    /**
     * Delete {@link Plane} entity.
     * @param planeDto {@link PlaneDto}
     * @return {@link ResponseEntity} boolean flag
     * @throws AirPortDaoException dao exception.
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deletePlane(final @Valid @RequestBody PlaneDto planeDto) throws AirPortDaoException {
        return new ResponseEntity<>(planeService.delete(planeDto), HttpStatus.OK);
    }
}