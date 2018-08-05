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
}
