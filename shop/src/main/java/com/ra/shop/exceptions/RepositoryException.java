package com.ra.shop.exceptions;

/**
 * Exception can be thrown if CRUD operations can`t be performed.
 */
public class RepositoryException extends Exception {

    /**
     * Creates RepositoryException instance, accepts exception message itself.
     *
     * @param message error message.
     */
    public RepositoryException(final String message) {
        super(message);
    }

    /**
     * Creates RepositoryException instance, accepts exception message and the exception itself.
     *
     * @param message error message.
     * @param cause   exception itself.
     */
    public RepositoryException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
