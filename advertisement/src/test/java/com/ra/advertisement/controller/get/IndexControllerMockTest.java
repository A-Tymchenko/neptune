package com.ra.advertisement.controller.get;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class IndexControllerMockTest {
    private static IndexController indexController;
    private static HttpServletRequest mockRequest;
    private static HttpServletResponse mockResponse;

    @BeforeAll
    public static void init() {
        mockRequest = mock(HttpServletRequest.class);
        mockResponse = mock(HttpServletResponse.class);
        indexController = new IndexController();
    }

    @Test
    void whenControllerRedirectResponseOnceReturnTrue() {
        String pathExpected = "/index.jsp";
        String pathResult = indexController.execute(mockRequest, mockResponse);
        assertEquals(pathExpected, pathResult);
    }
}
