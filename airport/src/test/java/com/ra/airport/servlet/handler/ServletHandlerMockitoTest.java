package com.ra.airport.servlet.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.naming.OperationNotSupportedException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServletHandlerMockitoTest {

    private ServletHandler servletHandler;
    private MockHttpServletRequest mockRequest;
    private MockHttpServletResponse mockResponse;

    @BeforeEach
    public void init() {
        mockRequest = new MockHttpServletRequest();
        mockResponse = new MockHttpServletResponse();
        servletHandler = new ServletHandler() {
        };
    }

    @Test
    public void wherePostThrowNewException() {
        Throwable thrown = assertThrows(OperationNotSupportedException.class, () -> {
            servletHandler.post(mockRequest, mockResponse);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    public void whereGetThrowNewException() {
        Throwable thrown = assertThrows(OperationNotSupportedException.class, () -> {
            servletHandler.get(mockRequest, mockResponse);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    public void whereDeleteThrowNewException() {
        Throwable thrown = assertThrows(OperationNotSupportedException.class, () -> {
            servletHandler.delete(mockRequest, mockResponse);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    public void wherePutThrowNewException() {
        Throwable thrown = assertThrows(OperationNotSupportedException.class, () -> {
            servletHandler.put(mockRequest, mockResponse);
        });
        assertNotNull(thrown.getMessage());
    }
}
