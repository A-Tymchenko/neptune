package com.ra.airport.servlet.handler;

import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.servlet.DispatcherServlet;
import com.ra.airport.servlet.handler.factory.HandlerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.naming.OperationNotSupportedException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.io.IOException;
import java.lang.reflect.Field;

public class DispatcherServletMockitoTest {

    @Mock
    private ServletConfig servletConfig;
    @Mock
    private AnnotationConfigApplicationContext context;
    @Mock
    private ServletHandler servletHandler;
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
        Field field = DispatcherServlet.class.getDeclaredField("context");
        field.setAccessible(true);
        field.set(dispatcherServlet, context);
        Mockito.when(context.getBean("/airports")).thenReturn(servletHandler);
    }

    @Test
    public void whereGetRequestThenRedirectToJSP() throws IOException, ServletException, AirPortDaoException, OperationNotSupportedException {
        dispatcherServlet.doGet(request, response);
        Mockito.verify(context, Mockito.times(1)).getBean("/airports");
    }

    @Test
    public void wherePostRequestThenRedirectToJSP() throws IOException, ServletException, AirPortDaoException, OperationNotSupportedException {
        request.setAttribute("jspPath", "TEST");
        dispatcherServlet.doPost(request, response);
        Mockito.verify(context, Mockito.times(1)).getBean("/airports");
    }

    @Test
    public void wherePostRequestThenThrowException() throws IOException, ServletException, AirPortDaoException, OperationNotSupportedException {
        Mockito.doThrow(AirPortDaoException.class).when(context).getBean(Mockito.anyString());
        dispatcherServlet.doPost(request, response);
    }

    @Test
    public void whereGetRequestThenThrowException() throws IOException, ServletException, AirPortDaoException, OperationNotSupportedException {
        Mockito.doThrow(AirPortDaoException.class).when(context).getBean(Mockito.anyString());
        dispatcherServlet.doGet(request, response);
    }

    @Test
    public void whereServerStartInitContext() {
        dispatcherServlet.init(servletConfig);
    }


}
