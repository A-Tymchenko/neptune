package com.ra.airport.dao;

import java.util.List;
import java.util.Optional;

import com.ra.airport.dao.exception.DaoException;

/**
 * Interface provide methods for the CRUD operations.
 */
public interface AirPortDao<T> {
    /**
     * Create entity in DB and return it.
     * @param entity entity to create
     * @return T entity
     * @throws DaoException exception for DAO layer
     */
    T create(T entity) throws DaoException;

    /**
     * Update entity in DB and return it.
     *
     * @param entity entity to update
     * @return T entity
     * @throws DaoException exception for DAO layer
     */
    T update(T entity) throws DaoException;

    /**
     * Delete entity in DB.
     * And return true if operation was successful or false if not.
     *
     * @param entity entity to delete
     * @return boolean flag
     * @throws DaoException exception for DAO layer
     */
    boolean delete(T entity) throws DaoException;

    /**
     * Return entity from DB by id.
     *
     * @param flightId entity id
     * @return T entity
     * @throws DaoException exception for DAO layer
     */
    Optional<T> getById(Integer flightId) throws DaoException;

    /**
     * Return all entities from DB by T type.
     * If entities absent in DB return empty {@link List}.
     *
     * @return List entities
     * @throws DaoException exception for DAO layer
     */
    List<T> getAll() throws DaoException;
}
