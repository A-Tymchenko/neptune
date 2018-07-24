package com.ra.advertisement.dao;

import com.ra.advertisement.dao.exceptions.DaoException;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

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
}

