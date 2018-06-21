package com.ra.courses.airport.dao;

/**
 * Created by anbo06131 on 6/15/2018.
 */
public interface DAO <T> {

    T create(T entity);

    T update(T entity);

    boolean delete(Integer flightId);
}
