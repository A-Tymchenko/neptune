package com.ra.advertisement.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public interface Controller {
    /**
     * Method gets request and provide response.
     *
     * @param request  HttpServlet request
     * @param response HttpServlet response
     */
    String execute(HttpServletRequest request, HttpServletResponse response);

}
