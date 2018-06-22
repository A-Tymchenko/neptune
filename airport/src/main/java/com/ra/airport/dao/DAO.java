package com.ra.airport.dao;

/**
 * DAO interface provide methods for CRUD operations.
 */
public interface DAO <T> {
    /**
     * Create entity in DB and return it.
     *
     * @param entity
     * @return T
     */
    T create(T entity);

    /**
     * Update entity in DB and return it
     *
     * @param entity
     * @return T
     */
    T update(T entity);

    /**
     * Delete entity in DB.
     * And return true if operation was successful or false if not.
     *
     * @param entity
     * @return T
     */
    boolean delete(T entity);
}
