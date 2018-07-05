package com.ra.airport.dao;

import java.util.List;
import java.util.Optional;

import com.ra.airport.dao.exception.DAOException;

/**
 * Interface provide methods for the CRUD operations.
 */
public interface AirPortDAO<T> {
    /**
     * Create entity in DB and return it.
     * @param entity entity to create
     * @return T entity
     * @throws DAOException exception for DAO layer
     */
    T create(T entity) throws DAOException;

    /**
     * Update entity in DB and return it.
     *
     * @param entity entity to update
     * @return T entity
     * @throws DAOException exception for DAO layer
     */
    T update(T entity) throws DAOException;

    /**
     * Delete entity in DB.
     * And return true if operation was successful or false if not.
     *
     * @param entity entity to delete
     * @return boolean flag
     * @throws DAOException exception for DAO layer
     */
    boolean delete(T entity) throws DAOException;

    /**
     * Return entity from DB by id.
     *
     * @param flightId entity id
     * @return T entity
     * @throws DAOException exception for DAO layer
     */
    T getById(Optional<Integer> flightId) throws DAOException;

    /**
     * Return all entities from DB by T type.
     * If entities absent in DB return empty {@link List}.
     *
     * @return List entities
     * @throws DAOException exception for DAO layer
     */
    List<T> getAll() throws DAOException;
}
