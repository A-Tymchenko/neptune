package com.ra.airport.servlet.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.naming.OperationNotSupportedException;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void wherePostThrowOperationNotSupportedException() {
        Throwable thrown = assertThrows(OperationNotSupportedException.class, () -> {
            servletHandler.post(mockRequest, mockResponse);
        });
        assertEquals(thrown.getMessage(), "Method POST not supported on this URL");
    }

    @Test
    public void whereGetThrowOperationNotSupportedException() {
        Throwable thrown = assertThrows(OperationNotSupportedException.class, () -> {
            servletHandler.get(mockRequest, mockResponse);
        });
        assertEquals(thrown.getMessage(), "Method GET not supported on this URL");
    }

    @Test
    public void whereDeleteThrowOperationNotSupportedException() {
        Throwable thrown = assertThrows(OperationNotSupportedException.class, () -> {
            servletHandler.delete(mockRequest, mockResponse);
        });
        assertEquals(thrown.getMessage(), "Method DELETE not supported on this URL");
    }

    @Test
    public void wherePutThrowOperationNotSupportedException() {
        Throwable thrown = assertThrows(OperationNotSupportedException.class, () -> {
            servletHandler.put(mockRequest, mockResponse);
        });
        assertEquals(thrown.getMessage(), "Method PUT not supported on this URL");
    }
}
