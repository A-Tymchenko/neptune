package com.ra.airport.servlet.handler.factory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.servlet.handler.GetAllFlightsHandler;
import com.ra.airport.servlet.handler.ServletHandler;

public class HandlerFactory {

    private static Map<String, ServletHandler> handlers = new HashMap<>();

    static {
        handlers.put("/flights", new GetAllFlightsHandler());
    }

    /**
     * Init get request to EntityHandler.
     * @Throw IOException
     */
    public void handleGetRequest(final String path, final HttpServletRequest request,
                                 final HttpServletResponse response) throws IOException, AirPortDaoException {
        if (handlers.containsKey(path)) {
            final ServletHandler handler = handlers.get(path);
            handler.get(request, response);
        }
    }

    /**
     * Init post request to EntityHandler.
     */
    public void handlePostRequest(final String path, final HttpServletRequest request,
                                  final HttpServletResponse response) throws IOException {
        if (handlers.containsKey(path)) {
            final ServletHandler handler = handlers.get(path);
            handler.post(request, response);
        }
    }

}
