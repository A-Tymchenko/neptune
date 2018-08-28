package com.ra.airport.service;

import java.util.List;
import java.util.Optional;

import com.ra.airport.repository.exception.AirPortDaoException;

/**
 * Service provides methods for Airport CRUD operations.
 * Using {@link com.ra.airport.repository.AirPortDao}.
 */
public interface AirPortService<T> {

    /**
     * Create entity in DB and return it.
     * @param dto dto to create
     * @return T dto
     * @throws AirPortDaoException exception for DAO layer
     */
    T create(T dto) throws AirPortDaoException;

    /**
     * Update entity in DB and return it.
     *
     * @param dto dto to update
     * @return T dto
     * @throws AirPortDaoException exception for DAO layer
     */
    T update(T dto) throws AirPortDaoException;

    /**
     * Delete entity in DB.
     * And return true if operation was successful or false if not.
     *
     * @param dto dto to delete
     * @return boolean flag
     * @throws AirPortDaoException exception for DAO layer
     */
    boolean delete(T dto) throws AirPortDaoException;

    /**
     * Return all entities from DB by T type.
     * If entities absent in DB return empty {@link List}.
     *
     * @return List entities
     * @throws AirPortDaoException exception for DAO layer
     */
    List<T> getAll() throws AirPortDaoException;
}
