package com.ra.advertisement.controller;

import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public interface Controller {
    /**
     * Method gets request and provide response.
     *
     * @param request  HttpServlet request
     * @param response HttpServlet response
     */
    void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
