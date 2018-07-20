package com.ra.shop.dao;

import java.util.List;

import com.ra.shop.dao.exception.WarehouseDaoException;

/**
 * CRUD methods.
 */
public interface WarehouseDao<T> {
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
    T getById(Integer idNumber) throws WarehouseDaoException;

    /**
     * List of entities.
     *
     * @return list of entities
     */
    List<T> getAll() throws WarehouseDaoException;
}
