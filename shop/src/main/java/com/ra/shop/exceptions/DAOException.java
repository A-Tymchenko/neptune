package com.ra.shop.exceptions;

public class DAOException extends Exception {

    public DAOException(final String message) {
        super(message);
    }

    public DAOException(final String message, final Throwable issue) {
        super(message, issue);
    }
}

