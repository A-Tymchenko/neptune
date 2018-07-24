package com.ra.shop;

import com.ra.shop.dao.exception.DAOException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DAOExeptionTest {

    private static final String EXCEPTION_CAUSE = "Connection not available";
    private DAOException goodsException;

    @Test
    public void whenInsertMessageInGoodExeptionExecutedTestWillReturnMessage() {
        goodsException = new DAOException(EXCEPTION_CAUSE);
        assertEquals(EXCEPTION_CAUSE, goodsException.getMessage()
        );
    }

    @Test
    public void whenInsertMessageAndCauceInGoodExeptionExecutedTestWillReturnMessageAndCause() {
        IOException e = new IOException();
        goodsException = new DAOException(EXCEPTION_CAUSE, e);
        assertEquals(EXCEPTION_CAUSE, goodsException.getMessage());
        assertEquals(e, goodsException.getCause());
    }
}
