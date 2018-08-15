package com.ra.airport.servlet.handler;

import javax.naming.OperationNotSupportedException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.airport.repository.exception.AirPortDaoException;

public interface ServletHandler {
    /**
     * Called where request method is post.
     */
    default void post(HttpServletRequest request, HttpServletResponse response) throws AirPortDaoException,
            OperationNotSupportedException {
        throw new OperationNotSupportedException("Method POST not supported on this URL");
    }

    /**
     * Called where request method is get.
     */
    default void get(HttpServletRequest request, HttpServletResponse response) throws AirPortDaoException,
            OperationNotSupportedException {
        throw new OperationNotSupportedException("Method GET not supported on this URL");
    }

    /**
     * Called where request method is delete.
     */
    default void delete(HttpServletRequest request, HttpServletResponse response) throws AirPortDaoException,
            OperationNotSupportedException {
        throw new OperationNotSupportedException("Method DELETE not supported on this URL");
    }

    /**
     * Called where request method is put.
     */
    default void put(HttpServletRequest request, HttpServletResponse response) throws AirPortDaoException,
            OperationNotSupportedException {
        throw new OperationNotSupportedException("Method PUT not supported on this URL");
    }
}
