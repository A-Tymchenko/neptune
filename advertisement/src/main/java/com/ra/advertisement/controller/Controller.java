package com.ra.advertisement.controller;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public interface Controller {
    /**
     * Method gets request and provide response.
     *
     * @param request  HttpServlet request
     * @param response HttpServlet response
     */
    void execute(HttpServletRequest request, HttpServletResponse response);

}
