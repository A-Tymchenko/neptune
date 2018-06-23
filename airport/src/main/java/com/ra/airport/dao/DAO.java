package com.ra.airport.dao;

import com.ra.airport.dao.exception.DAOException;

import java.util.Optional;

/**
 * DAO interface provide methods for CRUD operations.
 */
public interface DAO <T> {
    /**
     * Create entity in DB and return it.
     * @param entity
     * @return T
     * @throws DAOException
     */
    T create(T entity) throws DAOException;

    /**
     * Update entity in DB and return it
     *
     * @param entity
     * @return T
     * @throws DAOException
     */
    T update(T entity) throws DAOException;

    /**
     * Delete entity in DB.
     * And return true if operation was successful or false if not.
     *
     * @param entity
     * @return T
     */
    boolean delete(T entity) throws DAOException;

    /**
     * Return entity from DB by id
     *
     * @param id
     * @return T
     * @throws DAOException
     */
    T getById(Optional<Integer> id) throws DAOException;
}
