package com.ra.airport.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.ra.airport.dto.PlaneDto;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.PlaneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

public class PlaneControllerMockitoTest {

    @Mock
    PlaneService planeService;

    @Mock
    Model model;

    @InjectMocks
    PlaneController planeController;

    private PlaneDto planeDto;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        createPlane();
    }

    @Test
    public void whenGetPlanesThenReturnPathToJspAndPlanesInModel() throws AirPortDaoException {
        assertEquals(planeController.getPlanes(model), "plane/planes");
    }

    @Test
    public void whenCreatePlaneThenReturnResponseEntity() throws AirPortDaoException {
        ResponseEntity responseEntity = planeController.createPlane(planeDto);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void whenDeletePlaneThenReturnResponseEntity() throws AirPortDaoException {
        ResponseEntity responseEntity = planeController.deletePlane(planeDto);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void whenUpdatePlaneThenReturnResponseEntity() throws AirPortDaoException {
        ResponseEntity responseEntity = planeController.updatePlane(planeDto);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    private PlaneDto createPlane() {
        planeDto = new PlaneDto();
        planeDto.setPlaneId(1);
        planeDto.setModel("Boeing");
        planeDto.setType("LargeCarrier");
        planeDto.setSeatsCount(250);
        planeDto.setPlateNumber(13249);
        return planeDto;
    }
}
