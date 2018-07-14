package com.ra.shop.service;

/**
 * Custom exception.
 *
 * @author Leonid
 * @version 1.0
 *
 */

public class GoodsException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Construct a new exeption with the specified detail message.
     *
     * @param message the detail message.
     */

    public GoodsException(final String message) {
        super(message);
    }

    /**
     * Construct a new exeption with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause.
     */

    public GoodsException(final String message, final Throwable cause) {
        super(message, cause);
    }
}

