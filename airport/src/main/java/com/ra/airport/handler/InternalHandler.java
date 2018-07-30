package com.ra.airport.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface InternalHandler {

    /**
     * Called where request method is post.
     */
    void post(HttpServletRequest request, HttpServletResponse response);

    /**
     * Called where request method is get.
     */
    void get(HttpServletRequest request, HttpServletResponse response);

}
