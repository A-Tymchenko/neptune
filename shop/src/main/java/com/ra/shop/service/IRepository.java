package com.ra.shop.service;

import java.util.List;
import java.util.Optional;

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
     * @return Integer value display how many rows was inserted to database.
     */

    Integer create(T entity) throws GoodException;


    /**
     * Method returns Optional wrapper with an entity from database.
     *
     * @param entityId of entity that will be insert.
     * @return Optional T wrapper for chosen entity.
     */

    Optional<T> get(Long entityId) throws GoodException;


    /**
     * Method updates existed entity due to it`s new params and send updated entity to database.
     *
     * @param newEntity updated version of entity.
     * @return Integer. Returns an updated rows number.
     */

    Integer update(T newEntity) throws GoodException;


    /**
     * Method will delete entity from the database.
     *
     * @param entityId of entity that will be deleted.
     * @return Integer value displays deleted rows number from database.
     */

    Integer delete(Long entityId) throws GoodException;


    /**
     * Method returns all entities.
     *
     * @return List T which contains all existed entities of the type T.
     */

    List<T> getAll() throws GoodException;
}

