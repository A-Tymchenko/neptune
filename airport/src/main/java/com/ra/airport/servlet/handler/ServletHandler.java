package com.ra.airport.servlet.handler;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ServletHandler {

    /**
     * Called where request method is post.
     */
    void post(HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * Called where request method is get.
     */
    void get(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
