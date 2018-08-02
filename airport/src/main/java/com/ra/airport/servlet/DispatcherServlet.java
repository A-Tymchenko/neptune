package com.ra.airport.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.airport.config.AirPortConfiguration;
import com.ra.airport.repository.impl.FlightDao;
import com.ra.airport.servlet.handler.factory.HandlerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebServlet(urlPatterns = "/")
public class DispatcherServlet extends HttpServlet {

    private static HandlerFactory handlerFactory;
    private transient ApplicationContext context;

    @Override
    public void init() {
        handlerFactory = new HandlerFactory();
        context = new AnnotationConfigApplicationContext(AirPortConfiguration.class);
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        final FlightDao flightDao = (FlightDao) context.getBean("flightDao");
        final PrintWriter out = resp.getWriter();
        out.println(context);
        out.println(flightDao);

        handlerFactory.handleGetRequest(this.getPath(req), req, resp);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        handlerFactory.handlePostRequest(this.getPath(req), req, resp);
    }

    private String getPath(final HttpServletRequest req) {
        return req.getServletPath();
    }
}
