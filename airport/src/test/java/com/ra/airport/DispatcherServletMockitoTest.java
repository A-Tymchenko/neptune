package com.ra.airport;

import com.ra.airport.servlet.DispatcherServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.servlet.ServletConfig;

public class DispatcherServletMockitoTest {

    private DispatcherServlet dispatcherServlet;

    @Mock
    private ServletConfig servletConfig;

    @BeforeEach
    public void init() {
        dispatcherServlet = new DispatcherServlet();
    }

    @Test
    public void whereServerStartInitContext() {
        dispatcherServlet.init(servletConfig);
    }
}
