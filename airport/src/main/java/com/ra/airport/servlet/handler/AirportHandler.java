package com.ra.airport.servlet.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ra.airport.dao.exception.AirPortDaoException;
import com.ra.airport.dao.impl.AirportDAOImpl;
import com.ra.airport.entity.Airport;
import com.ra.airport.servlet.InternalHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class AirportHandler implements InternalHandler {

    @Autowired
    private transient AirportDAOImpl airportDAO;

    private transient HttpSession session;

    private static final Logger LOGGER = LogManager.getLogger(AirportDAOImpl.class);

    @Override
    public void post(final HttpServletRequest request, final HttpServletResponse response) {
        try {
            session = request.getSession();
            session.setAttribute("airports", airportDAO.getAll());
        } catch (AirPortDaoException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void get(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        try {
            session = request.getSession();
            final RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("/AirportTable.jsp");
            final List<Airport> list = new ArrayList<>();
            list.add(new Airport(1, "Kenedy", 1, "International", "New York", 1));
            list.add(new Airport(1, "O-hara", 1, "International", "Chicago", 1));
            request.setAttribute("airports", list);
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
