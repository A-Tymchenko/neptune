package com.ra.project.service;

import com.ra.project.exceptions.InvalidOrderIdException;

import java.util.List;
import java.util.Optional;

public interface IService<T> {
    /**
     * Method creates new entity.
     *
     * @param entity that will be created.
     * @return Integer
     */
    Integer create(T entity);

    /**
     * Method returns entity from database.
     *
     * @param entityId - id of searched entity.
     * @return Optional<T>
     */
    Optional<T> get(Long entityId) throws InvalidOrderIdException;

    /**
     * Method returns an updated rows number.
     *
     * @param newEntity updated version of entity.
     * @return Integer. Returns an updated rows number.
     */
    Integer update(T newEntity) throws InvalidOrderIdException;

    /**
     * Method will delete entity from the database.
     *
     * @param entityId of entity that will be deleted.
     * @return Integer.
     */
    Integer delete(Long entityId) throws InvalidOrderIdException;

    /**
     * Method returns all entities.
     *
     * @return List<T>.
     */
    List<T> getAll();

}
