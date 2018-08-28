package com.ra.airport.service;

import java.util.List;
import java.util.Optional;

import com.ra.airport.entity.Plane;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.repository.impl.PlaneDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Provides methods for CRUD operations with {@link com.ra.airport.entity.Plane} entity.
 * Using {@link com.ra.airport.repository.AirPortDao}.
 */
@Service
public class PlaneService implements AirPortService<Plane> {

    private final transient PlaneDao planeDao;

    @Autowired
    public PlaneService(final PlaneDao planeDao) {
        this.planeDao = planeDao;
    }

    /**
     * Create entity in DB and return it.
     *
     * @param dto entity to create
     * @return plane entity
     * @throws AirPortDaoException exception for DAO layer
     */
    @Override
    public Plane create(final Plane dto) throws AirPortDaoException {
        return planeDao.create(dto);
    }

    /**
     * Update entity in DB and return it.
     *
     * @param dto entity to update
     * @return T entity
     * @throws AirPortDaoException exception for DAO layer
     */
    @Override
    public Plane update(final Plane dto) throws AirPortDaoException {
        return planeDao.update(dto);
    }

    /**
     * Delete plane entity in DB.
     * And return true if operation was successful or false if not.
     *
     * @param dto entity to delete
     * @return boolean flag
     * @throws AirPortDaoException exception for DAO layer
     */
    @Override
    public boolean delete(final Plane dto) throws AirPortDaoException {
        return planeDao.delete(dto);
    }

    /**
     * Return plane entity from DB by id.
     *
     * @param planeId entity id
     * @return T entity
     * @throws AirPortDaoException exception for DAO layer
     */
    @Override
    public Optional<Plane> getById(final int planeId) throws AirPortDaoException {
        return planeDao.getById(planeId);
    }

    /**
     * Return all planes from DB by T type.
     * If entities absent in DB return empty {@link List}.
     *
     * @return List entities
     * @throws AirPortDaoException exception for DAO layer
     */
    @Override
    public List<Plane> getAll() throws AirPortDaoException {
        return planeDao.getAll();
    }
}