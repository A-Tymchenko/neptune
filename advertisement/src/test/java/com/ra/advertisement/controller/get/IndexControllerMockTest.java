package com.ra.advertisement.controller.get;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class IndexControllerMockTest {
    private static IndexController indexController;
    private static HttpServletRequest mockRequest;
    private static HttpServletResponse mockResponse;
    private static RequestDispatcher mockRequestDispatcher;


    @BeforeAll
    public static void init() {
        mockRequest = mock(HttpServletRequest.class);
        mockResponse = mock(HttpServletResponse.class);
        indexController = new IndexController();
    }

    @Test
    void whenControllerRedirectResponseOnceReturnTrue() throws ServletException, IOException {
        indexController.execute(mockRequest, mockResponse);
        verify(mockResponse).sendRedirect("/index.jsp");
        verify(mockResponse, times(1)).sendRedirect("/index.jsp");
    }
}