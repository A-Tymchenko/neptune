package com.ra.airport.servlet;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.airport.servlet.handler.factory.HandlerFactory;

@WebServlet(urlPatterns = "/")
public class DispatcherServlet extends HttpServlet {

    private static HandlerFactory handlerFactory;

    @Override
    public void init() {
        handlerFactory = new HandlerFactory();
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
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
