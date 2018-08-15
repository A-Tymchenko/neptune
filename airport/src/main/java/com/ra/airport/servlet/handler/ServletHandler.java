package com.ra.airport.servlet.handler;

import javax.naming.OperationNotSupportedException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.airport.repository.exception.AirPortDaoException;

public interface ServletHandler {
    /**
     * Called where request method is post.
     */
    void post(HttpServletRequest request, HttpServletResponse response) throws AirPortDaoException, OperationNotSupportedException;

    /**
     * Called where request method is get.
     */
    void get(HttpServletRequest request, HttpServletResponse response) throws AirPortDaoException, OperationNotSupportedException;

    /**
     * Called where request method is delete.
     */
    void delete(HttpServletRequest request, HttpServletResponse response) throws AirPortDaoException, OperationNotSupportedException;

    /**
     * Called where request method is put.
     */
    void put(HttpServletRequest request, HttpServletResponse response) throws AirPortDaoException, OperationNotSupportedException;
}
