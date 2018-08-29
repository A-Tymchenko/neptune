package com.ra.airport.service;

import java.util.ArrayList;
import java.util.List;

import com.ra.airport.dto.PlaneDto;
import com.ra.airport.entity.Plane;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.repository.impl.PlaneDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Provides methods for CRUD operations with {@link com.ra.airport.entity.Plane} entity.
 * Using {@link com.ra.airport.repository.AirPortDao}.
 */
@Service
public class PlaneService implements AirPortService<PlaneDto> {

    private final transient PlaneDao planeDao;

    @Autowired
    public PlaneService(final PlaneDao planeDao) {
        this.planeDao = planeDao;
    }

    /**
     * Create entity in DB and return it.
     *
     * @param planeDto entity to create
     * @return plane entity
     * @throws AirPortDaoException exception for DAO layer
     */
    @Override
    public PlaneDto create(final PlaneDto planeDto) throws AirPortDaoException {
        var plane = new Plane();
        BeanUtils.copyProperties(plane, planeDto);
        plane = planeDao.create(plane);
        planeDto.setPlaneId(plane.getPlaneId());
        return planeDto;
    }

    /**
     * Update entity in DB and return it.
     *
     * @param planeDto entity to update
     * @return T entity
     * @throws AirPortDaoException exception for DAO layer
     */
    @Override
    public PlaneDto update(final PlaneDto planeDto) throws AirPortDaoException {
        var plane = new Plane();
        BeanUtils.copyProperties(planeDto, plane);
        planeDao.create(plane);
        return planeDto;
    }

    /**
     * Delete plane entity in DB.
     * And return true if operation was successful or false if not.
     *
     * @param planeDto entity to delete
     * @return boolean flag
     * @throws AirPortDaoException exception for DAO layer
     */
    @Override
    public boolean delete(final PlaneDto planeDto) throws AirPortDaoException {
        var plane = new Plane();
        plane.setPlaneId(planeDto.getPlaneId());
        return planeDao.delete(plane);
    }

    /**
     * Return all planes from DB by T type.
     * If entities absent in DB return empty {@link List}.
     *
     * @return List entities
     * @throws AirPortDaoException exception for DAO layer
     */
    @Override
    public List<PlaneDto> getAll() throws AirPortDaoException {
        var result = new ArrayList<PlaneDto>();
        for (Plane plane : planeDao.getAll()) {
            result.add(createPlaneDto(plane));
        }
        return result;
    }

    private PlaneDto createPlaneDto(Plane plane) {
        PlaneDto planeDto = new PlaneDto();
        BeanUtils.copyProperties(plane, planeDto);
        return planeDto;
    }
}