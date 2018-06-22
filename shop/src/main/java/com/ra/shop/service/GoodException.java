package com.ra.shop.service;

public class GoodException extends Exception {

    /**
     * Custom exception
     */


    private static final long serialVersionUID = 1L;

    public GoodException() {
    }

    public GoodException(String message) {
        super(message);
    }

    public GoodException(String message, Throwable cause) {
        super(message, cause);
    }
}

