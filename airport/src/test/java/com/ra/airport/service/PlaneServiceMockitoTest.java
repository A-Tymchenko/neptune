package com.ra.airport.service;

import com.ra.airport.entity.Plane;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.repository.impl.PlaneDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class PlaneServiceMockitoTest {

    private static final String SPACE = " ";

    private Plane plane;

    private PlaneService planeService;

    @Mock
    private PlaneDao planeDao;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        createPlane();
        planeService = new PlaneService(planeDao);
    }

    @Test
    public void whenCreateThenDaoMethodShouldBeCalled() throws AirPortDaoException {
        planeService.create(plane);
        verify(planeDao, times(1)).create(this.plane);
    }

    @Test
    public void whenUpdateThenDaoMethodShouldBeCalled() throws AirPortDaoException {
        planeService.update(plane);
        verify(planeDao, times(1)).update(plane);
    }

    @Test
    public void whenDeleteThenDaoMethodShouldBeCalled() throws AirPortDaoException {
        planeService.delete(plane);
        verify(planeDao, times(1)).delete(plane);
    }

    @Test
    public void whenGetByIdThenDaoMethodShouldBeCalled() throws AirPortDaoException {
        planeService.getById(plane.getPlaneId());
        verify(planeDao, times(1)).getById(plane.getPlaneId());
    }

    @Test
    public void whenGetAllThenDaoMethodShouldBeCalled() throws AirPortDaoException {
        planeService.getAll();
        verify(planeDao, times(1)).getAll();
    }

    private void createPlane() {
        plane = new Plane();
        plane.setPlaneId(1);
        plane.setPlateNumber(2);
        plane.setModel(SPACE);
        plane.setType(SPACE);
        plane.setOwner(SPACE);
    }
}
