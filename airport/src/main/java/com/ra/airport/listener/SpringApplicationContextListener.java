package com.ra.airport.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.ra.airport.config.AirPortConfiguration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebListener
public class SpringApplicationContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(final ServletContextEvent sce) {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AirPortConfiguration.class);
        sce.getServletContext().setAttribute("applicationContext", context);
    }

}
