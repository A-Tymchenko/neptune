package com.ra.airport.servlet.handler.factory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.annotation.PostConstruct;

import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.servlet.handler.GetAllFlightsHandler;
import com.ra.airport.servlet.handler.ServletHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HandlerFactory {

    @Autowired
    private GetAllFlightsHandler getAllFlightsHandler;

    private static Map<String, ServletHandler> handlers = new HashMap<>();

    @PostConstruct
    public void init() {
        handlers.put("/flights", getAllFlightsHandler);
    }

    /**
     * Init get request to EntityHandler.
     *
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
