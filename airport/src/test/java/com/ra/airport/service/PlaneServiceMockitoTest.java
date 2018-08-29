package com.ra.airport.service;

import com.ra.airport.dto.PlaneDto;
import com.ra.airport.entity.Plane;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.repository.impl.PlaneDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class PlaneServiceMockitoTest {

    private static final String SPACE = " ";

    private Plane plane;

    private PlaneDto planeDto;

    private PlaneService planeService;

    private List<Plane> planes;

    @Mock
    private PlaneDao planeDao;

    @BeforeEach
    public void init() throws AirPortDaoException {
        MockitoAnnotations.initMocks(this);
        createPlane();
        createPlaneDTO();
        planeService = new PlaneService(planeDao);
        planes = new ArrayList<>();
        planes.add(plane);
        Mockito.when(planeDao.create(Mockito.any())).thenReturn(plane);
        Mockito.when(planeDao.update(Mockito.any())).thenReturn(plane);
        Mockito.when(planeDao.delete(Mockito.any())).thenReturn(true);
        Mockito.when(planeDao.getById(8)).thenReturn(Optional.ofNullable(plane));
        Mockito.when(planeDao.getAll()).thenReturn(planes);
    }

    @Test
    public void whenCreateThenDaoMethodShouldBeCalled() throws AirPortDaoException {
        planeService.create(planeDto);
        verify(planeDao, times(1)).create(Mockito.any());
    }

    @Test
    public void whenUpdateThenDaoMethodShouldBeCalled() throws AirPortDaoException {
        planeService.update(planeDto);
        verify(planeDao, times(1)).update(Mockito.any());
    }

    @Test
    public void whenDeleteThenDaoMethodShouldBeCalled() throws AirPortDaoException {
        planeService.delete(planeDto);
        verify(planeDao, times(1)).delete(Mockito.any());
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
        plane.setSeatsCount(150);
    }

    private void createPlaneDTO() {
        planeDto = new PlaneDto();
        planeDto.setPlaneId(1);
        planeDto.setModel("Boeing");
        planeDto.setType("LargeCarrier");
        planeDto.setSeatsCount(250);
        planeDto.setPlateNumber(13249);
    }
}
