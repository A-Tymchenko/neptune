package com.ra.shop.servicetest;


import com.ra.shop.service.GoodException;
import org.junit.jupiter.api.Test;

import java.io.EOFException;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class GoodsExeptionTest {

    private static final String EXCEPTION_CAUSE = "Connection not available";
    private GoodException goodException;

    @Test
    public void whenInsertMessageInGoodExeptionExecutedTestWillReturnMessage() {
        goodException = new GoodException(EXCEPTION_CAUSE);
        assertEquals(EXCEPTION_CAUSE, goodException.getMessage()
        );
    }

    @Test
    public void whenInsertMessageAndCauceInGoodExeptionExecutedTestWillReturnMessageAndCause() {
        EOFException e = new EOFException();
        goodException = new GoodException(EXCEPTION_CAUSE, e);
        assertEquals(EXCEPTION_CAUSE, goodException.getMessage());
        assertEquals(e, goodException.getCause());
    }
}
