package com.ra.advertisement.dao.exceptions;

public class DaoException extends Exception {
    private final String message;
    private final Throwable exception;

    public DaoException(final String messageGet, final Throwable causeGet) {
        super(messageGet, causeGet);
        this.message = messageGet;
        this.exception = causeGet;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Throwable getException() {
        return exception;
    }
}
