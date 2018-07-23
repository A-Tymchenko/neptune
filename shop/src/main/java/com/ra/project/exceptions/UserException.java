package com.ra.project.exceptions;

import com.ra.project.model.User;

/**
 * Exception can be thrown if CRUD operations can`t be performed.
 */
public class UserException extends Exception {

    /**
     * Constructor with message argument.
     * @param message exception message.
     */
    public UserException(final String message) {
        super(message);
    }

    public UserException(final String message, final Throwable cause) {
                super(message, cause);
            }

}