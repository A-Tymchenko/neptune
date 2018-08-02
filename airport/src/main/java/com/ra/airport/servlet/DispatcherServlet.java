package com.ra.airport.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ra.airport.repository.impl.FlightDao;
import com.ra.airport.servlet.handler.factory.HandlerFactory;
import org.springframework.context.ApplicationContext;

@WebServlet(urlPatterns = "/")
public class DispatcherServlet extends HttpServlet {

    private static HandlerFactory handlerFactory;

    @Override
    public void init() {
        handlerFactory = new HandlerFactory();
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        final HttpSession session = req.getSession();
        final ApplicationContext context = (ApplicationContext) session.getServletContext().getAttribute("applicationContext");
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
