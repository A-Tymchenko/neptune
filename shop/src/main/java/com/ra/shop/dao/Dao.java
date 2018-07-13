package com.ra.shop.dao;

import java.util.List;
import java.util.Optional;

import com.ra.shop.dao.exception.WarehouseDaoException;
import com.ra.shop.wharehouse.Warehouse;

/**
 * CRUD methods.
 */
public interface Dao<T> {
    /**
     * create and return entity.
     *
     * @return entity
     */
    T create(T entity) throws WarehouseDaoException;

    /**
     * update and return entity.
     *
     * @return entity
     */
    T update(T entity) throws WarehouseDaoException;

    /**
     * delete and return the result true of false.
     *
     * @return true or false
     */
    boolean delete(T entity) throws WarehouseDaoException;

    /**
     * get entity by id.
     *
     * @return entity
     */
    Warehouse getById(Optional<Integer> idNumber) throws WarehouseDaoException;

    /**
     * List of entities.
     *
     * @return list of entities
     */
    List<T> getAll() throws WarehouseDaoException;
}
