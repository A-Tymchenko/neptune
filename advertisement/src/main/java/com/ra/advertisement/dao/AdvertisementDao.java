package com.ra.advertisement.dao;

import java.util.List;
import java.util.Optional;

import com.ra.advertisement.dao.exceptions.DaoException;

public interface AdvertisementDao<T> {

    /**
     * Method save entity with parameters into Data Base.
     *
     * @param entity object for saving into Data Base
     * @return entity with genereted Id
     */
    T create(T entity) throws DaoException;

    /**
     * Method get the object from a Data Base by id.
     *
     * @param entityId id of the object which will be selected from database
     * @return an Optional with a present value if the specified value is non-null, otherwise an empty Optional
     */
    Optional<T> getById(Long entityId) throws DaoException;

    /**
     * Method delete the object from a Data Base by id.
     *
     * @param entity object which will be deleted from database
     * @return number of affected rows in database
     */
    Integer delete(T entity) throws DaoException;

    /**
     * Method for updating object in database.
     *
     * @param entity object to be updated
     * @return new Entity updated
     */
    T update(T entity) throws DaoException;

    /**
     * Method get all objects from a Data Base.
     *
     * @return list of objects from database or empty list otherwise
     */
    List<T> getAll() throws DaoException;

}
