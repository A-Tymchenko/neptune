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
     * @param entity entity to create
     * @return T entity
     * @throws AirPortDaoException exception for DAO layer
     */
    T create(T entity) throws AirPortDaoException;

    /**
     * Update entity in DB and return it.
     *
     * @param entity entity to update
     * @return T entity
     * @throws AirPortDaoException exception for DAO layer
     */
    T update(T entity) throws AirPortDaoException;

    /**
     * Delete entity in DB.
     * And return true if operation was successful or false if not.
     *
     * @param entity entity to delete
     * @return boolean flag
     * @throws AirPortDaoException exception for DAO layer
     */
    boolean delete(T entity) throws AirPortDaoException;

    /**
     * Return entity from DB by id.
     *
     * @param entityId entity id
     * @return T entity
     * @throws AirPortDaoException exception for DAO layer
     */
    Optional<T> getById(int entityId) throws AirPortDaoException;

    /**
     * Return all entities from DB by T type.
     * If entities absent in DB return empty {@link List}.
     *
     * @return List entities
     * @throws AirPortDaoException exception for DAO layer
     */
    List<T> getAll() throws AirPortDaoException;
}