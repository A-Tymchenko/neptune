package com.ra.airport.servlet.handler.factory;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.servlet.handler.ServletHandler;

public class HandlerFactory {

    private final transient Map<String, ServletHandler> handlers;

    public HandlerFactory(final Map<String, ServletHandler> handlers) {
        this.handlers = handlers;
    }

    /**
     * Init get request to EntityHandler.
     *
     * @throw AirPortDaoException
     */
    public void handleGetRequest(final String path, final HttpServletRequest request,
                                 final HttpServletResponse response) throws AirPortDaoException {
        if (handlers.containsKey(path)) {
            final ServletHandler handler = handlers.get(path);
            handler.get(request, response);
        }
    }

    /**
     * Init post request to EntityHandler.
     */
    public void handlePostRequest(final String path, final HttpServletRequest request,
                                  final HttpServletResponse response) throws AirPortDaoException {
        if (handlers.containsKey(path)) {
            final ServletHandler handler = handlers.get(path);
            handler.post(request, response);
        }
    }
}
