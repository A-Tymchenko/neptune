package com.ra.courses.airport.dao.impl;
//create and add exception

import java.util.List;
import java.util.Optional;

public interface PlaneDaoInterface<T> {


    /**
          * Create entity in DB and return it.
          * @param entity entity to create
          * @return T entity
          * @throws PlaneDaoException exception for DAO layer
          */
    T create(T entity) throws PlaneDaoException;

            /**
      * Update entity in DB and return it.
      *
      * @param entity entity to update
      * @return T entity
      * @throws PlaneDaoException exception for DAO layer
      */
            T update(T entity) throws PlaneDaoException;

            /**
      * Delete entity in DB.
      * And return true if operation was successful or false if not.
      *
      * @param entity entity to delete
      * @return boolean flag
      * @throws PlaneDaoException exception for DAO layer
      */
            boolean delete(T entity) throws PlaneDaoException;

            /**
      * Return entity from DB by id.
      *
      * @param entityId entity id
      * @return T entity
      * @throws PlaneDaoException exception for DAO layer
      */
            Optional<T> getById(Integer entityId) throws PlaneDaoException;

            /**
      * Return all entities from DB by T type.
      * If entities absent in DB return empty {@link List}.
      *
      * @return List entities
      * @throws PlaneDaoException exception for DAO layer
      */
            List<T> getAll() throws PlaneDaoException;
}