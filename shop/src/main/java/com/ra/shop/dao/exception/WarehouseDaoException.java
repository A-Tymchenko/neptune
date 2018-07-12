package com.ra.shop.dao.exception;

public class WarehouseDaoException extends Exception {

    public WarehouseDaoException(final String message) {
        super(message);
    }

    public WarehouseDaoException(final String message, final Throwable issue) {
        super(message, issue);
    }
}
