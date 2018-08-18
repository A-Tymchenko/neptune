package com.ra.airport.servlet.handler;

import java.util.ArrayList;
import java.util.List;
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
public class GetTicketsHandler implements ServletHandler {

    private final transient AirPortService<Ticket> ticketService;

    @Autowired
    public GetTicketsHandler(final AirPortService<Ticket> ticketService) {
        this.ticketService = ticketService;
    }

    @Override
    public void post(final HttpServletRequest request, final HttpServletResponse response) throws AirPortDaoException {
        this.get(request, response);
    }

    @Override
    public void get(final HttpServletRequest request, final HttpServletResponse response) throws AirPortDaoException {
        final List<Ticket> tickets = ticketService.getAll();
        final List<TicketDTO> result = new ArrayList<>();
        tickets.forEach(ticket -> {
            result.add(createTicketDTO(ticket));
        });
        request.setAttribute("tickets", result);
        request.setAttribute("jspPath", "/tickets.jsp");
    }

    private TicketDTO createTicketDTO(final Ticket ticket) {
        final TicketDTO ticketDTO = new TicketDTO();
        BeanUtils.copyProperties(ticket, ticketDTO);
        return ticketDTO;
    }
}
