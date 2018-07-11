package com.ra.project.exceptions;

/**
 * Exception can be thrown if CRUD operations can`t be performed.
 */
public class RepositoryException extends Exception {
    /**
     * Constructor with message argument.
     * @param message exception message.
     */
    public RepositoryException(final String message) {
        super(message);
    }
}
