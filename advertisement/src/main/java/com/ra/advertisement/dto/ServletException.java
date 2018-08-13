package com.ra.advertisement.dto;

public class ServletException extends Exception {
    private final String message;
    private final Throwable exception;

    public ServletException(final String messageGet, final Throwable causeGet) {
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
