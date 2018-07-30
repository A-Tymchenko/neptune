package com.ra.airport.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HandlerFactory {

    private static Map<String, InternalHandler> handlers = new HashMap<>();

    /**
     * Init path.
     * For Exelple, add your path to static
     * static {
     *         handlers.put("/autosalon", new AutosalonHandler());
     *     }
     */
    /**
     * Init get request to EntityHandler.
     * @Throw IOException
     */
    public void handleGetRequest(final String path, final HttpServletRequest request,
                                 final HttpServletResponse response) throws IOException {
        if (handlers.containsKey(path)) {
            final InternalHandler handler = handlers.get(path);
            handler.get(request, response);
        }
    }

    /**
     * Init post request to EntityHandler.
     */
    public void handlePostRequest(final String path, final HttpServletRequest request,
                                  final HttpServletResponse response) throws IOException {
        if (handlers.containsKey(path)) {
            final InternalHandler handler = handlers.get(path);
            handler.post(request, response);
        }
    }

}
