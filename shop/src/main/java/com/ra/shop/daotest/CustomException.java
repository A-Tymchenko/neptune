package com.ra.shop.daotest;

public class CustomException extends Exception {

    /**
     * Custom exception
     */


    private static final long serialVersionUID = 1L;

    public CustomException() {
    }

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
}

