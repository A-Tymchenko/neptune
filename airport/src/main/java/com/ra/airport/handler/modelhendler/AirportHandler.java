package com.ra.airport.handler.modelhendler;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ra.airport.dao.exception.AirPortDaoException;
import com.ra.airport.dao.impl.AirportDAOImpl;
import com.ra.airport.handler.InternalHandler;
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
        session = request.getSession();
    }

    @Override
    public void get(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        try {
            session = request.getSession();
            session.setAttribute("airports", airportDAO.getAll());
            final RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("AirportTable.jsp");
            dispatcher.forward(request, response);
        } catch (AirPortDaoException | ServletException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
