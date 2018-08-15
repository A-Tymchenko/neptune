package com.ra.airport.servlet.handler;

import com.ra.airport.entity.Flight;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.repository.impl.FlightDao;
import org.springframework.context.ApplicationContext;

import javax.naming.OperationNotSupportedException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Author: anbo
 * Date: 02.08.2018
 */
public class GetAllFlightsHandler implements ServletHandler {

    @Override
    public void post(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws AirPortDaoException {
        
    }

    @Override
    public void delete(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public void put(HttpServletRequest request, HttpServletResponse response) throws AirPortDaoException, OperationNotSupportedException {

    }

    private ApplicationContext getApplicationContext(HttpServletRequest request) {
        final HttpSession session = request.getSession();
        return (ApplicationContext) session.getServletContext().getAttribute("applicationContext");
    }
}
