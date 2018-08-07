package com.ra.airport.servlet.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.airport.repository.exception.AirPortDaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface ServletHandler {

    Logger LOGGER = LogManager.getLogger(ServletHandler.class);

    /**
     * Called where request method is post.
     */
    void post(HttpServletRequest request, HttpServletResponse response) throws AirPortDaoException;

    /**
     * Called where request method is get.
     */
    void get(HttpServletRequest request, HttpServletResponse response) throws AirPortDaoException;

    /**
     * Redirect to jsp page.
     *
     * @param pathToJsp path to jsp page
     * @param request object
     * @param response object
     */
    default void redirectToJSP(String pathToJsp, HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher(pathToJsp).forward(request, response);
        } catch (IOException | ServletException e) {
            LOGGER.error("Redirection to {} failed", pathToJsp, e);
        }
    }
}
