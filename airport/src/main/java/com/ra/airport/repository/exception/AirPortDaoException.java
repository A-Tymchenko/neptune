package com.ra.airport.repository.exception;

/**
 * Exception class for DAO layer.
 */
public class AirPortDaoException extends Exception {

    public AirPortDaoException(final String message) {
        super(message);
    }

    public AirPortDaoException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
