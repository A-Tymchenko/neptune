package com.ra.advertisement.dao.exceptions;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DaoExceptionTest {
    private DaoException daoException;

    @Test
    void getDaoExceptionReturnTrue() {
        final String message = "Message";
        final Throwable throwable = new SQLException();
        DaoException daoException = new DaoException(message, throwable);
        assertAll("daoException", () -> assertEquals(message, daoException.getMessage()
        ), () -> assertEquals(throwable, daoException.getException()));

    }

    @Test
    void getProviderDaoExceptionReturnTrue() {
        final String message = "Message";
        final Throwable throwable = new SQLException();
        DaoException daoException = new DaoException(message, throwable);
        assertAll("daoException", () -> assertEquals(message, daoException.getMessage()
        ), () -> assertEquals(throwable, daoException.getException()));

    }
}

