package com.ra.project.exceptions;

/**
 * InvalidOrderIdException can be thrown when order id passes as method argument less or equals 0.
 */
public class InvalidOrderIdException extends Exception {
    public InvalidOrderIdException(String message) {
        super(message);
    }
}
