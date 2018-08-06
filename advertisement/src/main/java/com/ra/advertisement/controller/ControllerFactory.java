package com.ra.advertisement.controller;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public interface ControllerFactory {
    /**
     * Method provide Class which implements interface Controller.
     *
     * @param request HttpServlet request
     * @return Controller
     */
    Controller getController(HttpServletRequest request);
}
