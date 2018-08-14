package com.ra.airport;

import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.servlet.DispatcherServlet;
import com.ra.airport.servlet.handler.factory.HandlerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.io.IOException;
import java.lang.reflect.Field;

public class DispatcherServletMockitoTest {

    @Mock
    private ServletConfig servletConfig;
    @Mock
    private HandlerFactory handlerFactory;
    @InjectMocks
    private DispatcherServlet dispatcherServlet;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    public void init() throws IllegalAccessException, NoSuchFieldException {
        MockitoAnnotations.initMocks(this);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        request.setServletPath("/airports");
        Field field = DispatcherServlet.class.getDeclaredField("handlerFactory");
        field.setAccessible(true);
        field.set(dispatcherServlet, handlerFactory);
    }

    @Test
    public void whereServerStartInitContext() {
        dispatcherServlet.init(servletConfig);
    }

    @Test
    public void whereGetRequestThenRedirectToJSP() throws IOException, ServletException, NoSuchFieldException, IllegalAccessException {
        Field field = DispatcherServlet.class.getDeclaredField("handlerFactory");
        field.setAccessible(true);
        field.set(dispatcherServlet, handlerFactory);
        dispatcherServlet.doGet(request, response);
    }

    @Test
    public void wherePostRequestThenRedirectToJSP() throws IOException, ServletException, NoSuchFieldException, IllegalAccessException {
        Field field = DispatcherServlet.class.getDeclaredField("handlerFactory");
        field.setAccessible(true);
        field.set(dispatcherServlet, handlerFactory);
        request.setAttribute("jspPath", "TEST");
        dispatcherServlet.doPost(request, response);
    }

    @Test
    public void wherePostRequestThenThrowException() throws IOException, ServletException, NoSuchFieldException, IllegalAccessException, AirPortDaoException {
        request.setAttribute("jspPath", "TEST");
        Mockito.doThrow(AirPortDaoException.class).when(handlerFactory).handlePostRequest(Mockito.any(), Mockito.any(), Mockito.any());
        dispatcherServlet.doPost(request, response);
    }

    @Test
    public void whereGetRequestThenThrowException() throws IOException, ServletException, NoSuchFieldException, IllegalAccessException, AirPortDaoException {
        Mockito.doThrow(AirPortDaoException.class).when(handlerFactory).handleGetRequest(Mockito.any(), Mockito.any(), Mockito.any());
        dispatcherServlet.doGet(request, response);
    }


}
