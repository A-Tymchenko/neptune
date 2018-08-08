package com.ra.advertisement.controller;

import com.ra.advertisement.config.AdvertisementConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdvertisementSertvlet extends HttpServlet {

    private final transient ApplicationContext context = new AnnotationConfigApplicationContext(AdvertisementConfiguration.class);
    private final transient ControllerGetFactory getFactory;
    private final transient ControllerPostFactory postFactory;
    private static final Logger LOGGER = LogManager.getLogger(AdvertisementSertvlet.class);

    public AdvertisementSertvlet() {
        this.getFactory = (ControllerGetFactory) context.getBean("controlerGetFactory");
        this.postFactory = (ControllerPostFactory) context.getBean("controlerPostFactory");
    }

    @Override
    public void doGet(final HttpServletRequest req, final HttpServletResponse resp) {
        final Controller controller = getFactory.getController(req);
        try {
            controller.execute(req, resp);
        } catch (ServletException e) {
            final String message = "Trouble in the Servlet method Get";
            LOGGER.error(message, e);
        } catch (IOException e) {
            final String message = "Trouble in the Servlet method Get";
            LOGGER.error(message, e);
        }
    }

    @Override
    public void doPost(final HttpServletRequest req, final HttpServletResponse resp) {
        final Controller controller = postFactory.getController(req);
        try {
            controller.execute(req, resp);
        } catch (ServletException e) {
            final String message = "Trouble in the Servlet method Post";
            LOGGER.error(message, e);
        } catch (IOException e) {
            final String message = "Trouble in the Servlet method Post";
            LOGGER.error(message, e);
        }

    }
}
