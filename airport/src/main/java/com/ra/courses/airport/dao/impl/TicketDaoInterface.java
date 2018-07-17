package com.ra.courses.airport.dao.impl;

import com.ra.courses.airport.dao.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface TicketDaoInterface<E> {

    /**
     * Method for creating new object into database.
     *
     * @param entity object for inserting
     * @return number of affected rows int database
     */
    Integer insert(E entity) throws DaoException;

    /**
     * Method for retrieving all object from database.
     *
     * @return list of objects from database or empty list otherwise
     */
    List<E> getAll() throws DaoException;

    /**
     * Method for retrieving object from database by specified id.
     *
     * @param entityId id of the object to be selected from database
     * @return an Optional with a present value if the specified value
     *         is non-null, otherwise an empty Optional
     */
    Optional<E> getById(Long entityId) throws DaoException;

    /**
     * Method for updating object in database.
     *
     * @param entity object to be updated
     * @return number of affected rows in database
     */
    Integer update(E entity) throws DaoException;

    /**
     * Method for deleting object from database.
     *
     * @param entityId id of the object to be removed from database
     * @return number of affected rows in database
     */
    Integer delete(Long entityId) throws DaoException;
}
