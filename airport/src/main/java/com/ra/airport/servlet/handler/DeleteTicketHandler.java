package com.ra.airport.servlet.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.airport.entity.Ticket;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.AirPortService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteTicketHandler implements ServletHandler {

    private final transient AirPortService<Ticket> ticketService;

    @Autowired
    public DeleteTicketHandler(final AirPortService<Ticket> ticketService) {
        this.ticketService = ticketService;
    }

    @Override
    public void post(final HttpServletRequest request, final HttpServletResponse response) throws AirPortDaoException {
        delete(request, response);
    }

    @Override
    public void delete(final HttpServletRequest request, final HttpServletResponse response) throws AirPortDaoException {
        final String ticketId = request.getParameter("ticketId");
        if (Strings.isNotBlank(ticketId)) {
            final var ticket = new Ticket();
            ticket.setTicketId(Integer.parseInt(ticketId));
            ticketService.delete(ticket);
            request.setAttribute("ticket", ticket);
            request.setAttribute("jspPath", "delete_ticket.jsp");
        }
    }
}
