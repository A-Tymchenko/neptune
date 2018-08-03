package com.ra.airport.servlet.handler;

import com.ra.airport.entity.Flight;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.repository.impl.FlightDao;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

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

@Component
public class GetAllFlightsHandler implements ServletHandler {

    @Override
    public void post(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, AirPortDaoException {
        final ApplicationContext context = getApplicationContext(request);
        FlightDao flightDao = context.getBean(FlightDao.class);
        List<Flight> flights = flightDao.getAll();
        PrintWriter out = response.getWriter();
        out.println(flights);
        // it's only example get service from context and call some method
    }

    private ApplicationContext getApplicationContext(HttpServletRequest request) {
        final HttpSession session = request.getSession();
        return (ApplicationContext) session.getServletContext().getAttribute("applicationContext");
    }
}
