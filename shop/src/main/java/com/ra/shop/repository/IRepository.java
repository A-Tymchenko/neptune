package com.ra.shop.repository;

import java.util.List;
import java.util.Optional;

import com.ra.shop.exceptions.RepositoryException;

/**
 * Interface represents simple CRUD-methods, which can be implemented for any entity, because it`s parameterized.
 *
 * @param <T> represents an entity.
 */
public interface IRepository<T> {

    /**
     * Method inserts new entity to database.
     *
     * @param entity that will be created.
     * @return T created entity.
     */
    T create(T entity) throws RepositoryException;

    /**
     * Method returns Optional wrapper with an entity from database.
     *
     * @param entityId - id of searched entity.
     * @return Optional wrapper for chosen entity.
     */
    Optional<T> get(Long entityId) throws RepositoryException;

    /**
     * Method updates existed entity due to it`s new params and send updated entity to database.
     *
     * @param newEntity updated version of entity.
     * @return T. Returns an updated entity.
     */
    T update(T newEntity) throws RepositoryException;

    /**
     * Method will delete entity from the database.
     *
     * @param entityId of entity that will be deleted.
     * @return boolean true if entity deleted, false if not.
     */
    boolean delete(Long entityId) throws RepositoryException;

    /**
     * Method returns all entities.
     *
     * @return List which contains all existed entities of the type T.
     */
    List<T> getAll() throws RepositoryException;

}
