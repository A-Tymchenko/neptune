package com.ra.advertisement.controller;

import com.ra.advertisement.config.InitDataBase;
import com.ra.advertisement.controller.get.GetAllAdvertController;
import com.ra.advertisement.controller.get.IndexController;
import com.ra.advertisement.controller.post.AdvertSaveController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AdvertisementServletMockTest {
    private static AdvertisementSertvlet advertisementSertvlet;
    private static RequestDispatcher mockRequestDispatcher;
    private static HttpServletRequest mockRequest;
    private static HttpServletResponse mockResponse;
    private static ApplicationContext mockContext;
    private static IndexController mockController;
    private static AdvertSaveController mockSaveController;

    @BeforeAll
    public static void init() {
        mockRequest = mock(HttpServletRequest.class);
        mockResponse = mock(HttpServletResponse.class);
        mockContext = mock(ApplicationContext.class);
        advertisementSertvlet = new AdvertisementSertvlet();
    }

    @BeforeEach
    public void reInit() throws NoSuchFieldException, IllegalAccessException {
        mockRequestDispatcher = mock(RequestDispatcher.class);
        when(mockRequest.getRequestDispatcher(anyString())).thenReturn(mockRequestDispatcher);
        Field servletField = advertisementSertvlet.getClass().getDeclaredField("context");
        servletField.setAccessible(true);
        servletField.set(advertisementSertvlet, mockContext);
        mockController = mock(IndexController.class);
        mockSaveController = mock(AdvertSaveController.class);
        when(mockRequest.getRequestURI()).thenReturn("sometext");
    }

    @Test
    public void doGetMethodThrowServletExceptionReturnTrue() throws ServletException, IOException {
        when(mockContext.getBean(anyString())).thenReturn(mockController);
        doThrow(new ServletException()).when(mockRequestDispatcher).forward(mockRequest, mockResponse);
        assertThrows(ServletException.class, () -> {
            advertisementSertvlet.doGet(mockRequest, mockResponse);
        });
    }

    @Test
    public void doGetMethodThrowIOExceptionExceptionReturnTrue() throws ServletException, IOException {
        when(mockContext.getBean(anyString())).thenReturn(mockController);
        doThrow(new IOException()).when(mockRequestDispatcher).forward(mockRequest, mockResponse);
        assertThrows(IOException.class, () -> {
            advertisementSertvlet.doGet(mockRequest, mockResponse);
        });
    }

    @Test
    public void doGetExecutedReturnTrue() throws ServletException, IOException {
        GetAllAdvertController advertController = mock(GetAllAdvertController.class);
        when(mockContext.getBean(anyString())).thenReturn(advertController);
        advertisementSertvlet.doGet(mockRequest, mockResponse);
        verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
        verify(mockRequestDispatcher, times(1)).forward(mockRequest, mockResponse);

    }

    @Test
    public void doPostMethodExecutedReturnTrue() throws ServletException, IOException {
        when(mockContext.getBean(anyString())).thenReturn(mockSaveController);
        advertisementSertvlet.doPost(mockRequest, mockResponse);
        verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
        verify(mockRequestDispatcher, times(1)).forward(mockRequest, mockResponse);
    }

    @Test
    public void doPostMethodThrowServletExceptionReturnTrue() throws ServletException, IOException {
        when(mockContext.getBean(anyString())).thenReturn(mockSaveController);
        doThrow(new ServletException()).when(mockRequestDispatcher).forward(mockRequest, mockResponse);
        assertThrows(ServletException.class, () -> {
            advertisementSertvlet.doPost(mockRequest, mockResponse);
        });
    }

    @Test
    public void doPostMethodThrowIOExceptionExceptionReturnTrue() throws ServletException, IOException {
        when(mockContext.getBean(anyString())).thenReturn(mockSaveController);
        doThrow(new IOException()).when(mockRequestDispatcher).forward(mockRequest, mockResponse);
        assertThrows(IOException.class, () -> {
            advertisementSertvlet.doPost(mockRequest, mockResponse);
        });
    }

    @Test
    public void initDbMethodReturnTrue() {

        InitDataBase initDataBase = mock(InitDataBase.class);
        when(mockContext.getBean(anyString())).thenReturn(initDataBase);
        //doNothing().when(initDataBase).initData();
        advertisementSertvlet.init();
        verify(mockContext, times(2)).getBean(anyString());
    }
}
