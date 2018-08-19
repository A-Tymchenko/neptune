package com.ra.airport.servlet.handler;

import java.sql.Timestamp;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.airport.dto.TicketDTO;
import com.ra.airport.entity.Ticket;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.AirPortService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateTicketHandler implements ServletHandler {

    private final transient AirPortService<Ticket> ticketService;

    @Autowired
    public CreateTicketHandler(final AirPortService<Ticket> ticketService) {
        this.ticketService = ticketService;
    }

    @Override
    public void post(final HttpServletRequest request, final HttpServletResponse response) throws AirPortDaoException {
        final var ticketDTO = createTicketDTO(request);
        final var ticket = new Ticket();
        BeanUtils.copyProperties(ticketDTO, ticket);
        final Ticket result = ticketService.create(ticket);
        request.setAttribute("ticket", result);
        request.setAttribute("jspPath", "create_ticket.jsp");
    }

    private TicketDTO createTicketDTO(final HttpServletRequest request) {
        final var ticketDTO = new TicketDTO();
        ticketDTO.setTicketNumber(request.getParameter("ticketNumber"));
        ticketDTO.setPassengerName(request.getParameter("passengerName"));
        ticketDTO.setDocument(request.getParameter("document"));
        ticketDTO.setSellingDate(Timestamp.valueOf(request.getParameter("sellingDate")));
        return ticketDTO;
    }
}
