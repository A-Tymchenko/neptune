package com.ra.shop.dao;

import java.util.List;
import java.util.Optional;

import com.ra.shop.dao.exception.DAOException;

/**
 * Interface represents simple CRUD-methods, which can be implemented for any entity, because it`s parameterized.
 *
 * @param <T></> represents an entity.
 */
public interface IRepository<T> {

    /**
     * Method inserts new entity to database.
     *
     * @param entity that will be created.
     * @return Entity inserted to database, with added 'ID' from DB.
     */
    T create(T entity) throws DAOException;

    /**
     * Method updates existed entity due to it`s new params and send updated entity to database.
     *
     * @param newEntity updated version of entity.
     * @return Entity. Returns an updated entity.
     */
    T update(T newEntity) throws DAOException;

    /**
     * Method will delete entity from the database.
     *
     * @param entityId of entity that will be deleted.
     * @return Boolean value displays deleted from database.
     */

    Boolean delete(Long entityId) throws DAOException;

    /**
     * Method returns Optional wrapper with an entity from database.
     *
     * @param entityId of entity that will be insert.
     * @return Optional T wrapper for chosen entity.
     */
    Optional<T> get(Long entityId) throws DAOException;

    /**
     * Method returns all entities.
     *
     * @return List T which contains all existed entities of the type T.
     */
    List<T> getAll() throws DAOException;
}
