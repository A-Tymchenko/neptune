package com.ra.shop.servicetest;


import com.ra.shop.service.GoodsException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class GoodsExeptionTest {

    private static final String EXCEPTION_CAUSE = "Connection not available";
    private GoodsException goodsException;

    @Test
    public void whenInsertMessageInGoodExeptionExecutedTestWillReturnMessage() {
        goodsException = new GoodsException(EXCEPTION_CAUSE);
        assertEquals(EXCEPTION_CAUSE, goodsException.getMessage()
        );
    }

    @Test
    public void whenInsertMessageAndCauceInGoodExeptionExecutedTestWillReturnMessageAndCause() {
        IOException e = new IOException();
        goodsException = new GoodsException(EXCEPTION_CAUSE, e);
        assertEquals(EXCEPTION_CAUSE, goodsException.getMessage());
        assertEquals(e, goodsException.getCause());
    }
}
