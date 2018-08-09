package com.ra.advertisement.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AdvertisementServletMockTest {
    private static AdvertisementSertvlet advertisementSertvlet;
    private static Controller mockController;
    private static RequestDispatcher mockRequestDispatcher;
    private static HttpServletRequest mockRequest;
    private static HttpServletResponse mockResponse;
    private static ApplicationContext context;

    @BeforeAll
    public static void init() {
        advertisementSertvlet = new AdvertisementSertvlet();
        mockController = mock(Controller.class);
        mockRequest = mock(HttpServletRequest.class);
        mockResponse = mock(HttpServletResponse.class);
        context = mock(ApplicationContext.class);
    }

    @BeforeEach
    public void reInit() {
        mockRequestDispatcher = mock(RequestDispatcher.class);
        when(mockRequest.getRequestDispatcher(anyString())).thenReturn(mockRequestDispatcher);
        when(context.getBean(anyString())).thenReturn(mockController);
    }

    @Test
    public void doGetMethodExecutedReturnTrue() throws ServletException, IOException {
        when(mockRequest.getRequestURI()).thenReturn("alladvertisement");
        advertisementSertvlet.doGet(mockRequest, mockResponse);
        verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
        verify(mockRequestDispatcher, times(1)).forward(mockRequest, mockResponse);
    }

    @Test
    public void doGetMethodThrowServletExceptionReturnTrue() throws ServletException, IOException {
        when(mockRequest.getRequestURI()).thenReturn("alladvertisement");
        doThrow(new ServletException()).when(mockRequestDispatcher).forward(mockRequest, mockResponse);
        assertThrows(ServletException.class, () -> {
            advertisementSertvlet.doGet(mockRequest, mockResponse);
        });
    }

    @Test
    public void doGetMethodThrowIOExceptionExceptionReturnTrue() throws ServletException, IOException {
        when(mockRequest.getRequestURI()).thenReturn("alladvertisement");
        doThrow(new IOException()).when(mockRequestDispatcher).forward(mockRequest, mockResponse);
        assertThrows(IOException.class, () -> {
            advertisementSertvlet.doGet(mockRequest, mockResponse);
        });
    }

    @Test
    public void doPostMethodExecutedReturnTrue() throws ServletException, IOException {
        when(mockRequest.getParameter(anyString())).thenReturn("saveAdvert");
        advertisementSertvlet.doPost(mockRequest, mockResponse);
        verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
        verify(mockRequestDispatcher, times(1)).forward(mockRequest, mockResponse);
    }

    @Test
    public void doPostMethodThrowServletExceptionReturnTrue() throws ServletException, IOException {
        when(mockRequest.getParameter(anyString())).thenReturn("saveAdvert");
        doThrow(new ServletException()).when(mockRequestDispatcher).forward(mockRequest, mockResponse);
        assertThrows(ServletException.class, () -> {
            advertisementSertvlet.doPost(mockRequest, mockResponse);
        });
    }

    @Test
    public void doPostMethodThrowIOExceptionExceptionReturnTrue() throws ServletException, IOException {
        when(mockRequest.getParameter(anyString())).thenReturn("saveAdvert");
        doThrow(new IOException()).when(mockRequestDispatcher).forward(mockRequest, mockResponse);
        assertThrows(IOException.class, () -> {
            advertisementSertvlet.doPost(mockRequest, mockResponse);
        });
    }
}
