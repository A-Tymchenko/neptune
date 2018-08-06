package com.ra.advertisement.controller;

import com.ra.advertisement.config.AdvertisementConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdvertisementSertvlet extends HttpServlet {

    private final transient ApplicationContext context = new AnnotationConfigApplicationContext(AdvertisementConfiguration.class);
    private final transient ControllerGetFactory getFactory;
    private final transient ControllerPostFactory postFactory;

    public AdvertisementSertvlet() {
        this.getFactory = (ControllerGetFactory) context.getBean("controlerGetFactory");
        this.postFactory = (ControllerPostFactory) context.getBean("controlerPostFactory");
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) {
        final Controller controller = getFactory.getController(req);
        controller.execute(req, resp);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) {
        final Controller controller = postFactory.getController(req);
        controller.execute(req, resp);

    }
}
