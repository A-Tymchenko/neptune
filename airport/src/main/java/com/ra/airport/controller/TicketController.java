package com.ra.airport.controller;

import javax.validation.Valid;

import com.ra.airport.dto.TicketDTO;
import com.ra.airport.entity.Ticket;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.TicketService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final transient TicketService ticketService;

    @Autowired
    public TicketController(final TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * ticket/ GET method.
     * @return ticket/show_tickets
     */
    @GetMapping
    public String getTickets(final Model model) throws AirPortDaoException {
        model.addAttribute("tickets", ticketService.getAll());
        return "ticket/show_tickets";
    }

    /**
     * ticket/ POST method.
     * @return ticket/create_ticket
     */
    @PostMapping
    public ResponseEntity<Ticket> createTicket(@Valid @RequestBody final TicketDTO ticketDTO) throws AirPortDaoException {
        final var ticket = new Ticket();
        BeanUtils.copyProperties(ticketDTO, ticket);
        return new ResponseEntity<>(ticketService.create(ticket), HttpStatus.OK);
    }

    /**
     * ticket/ DELETE method.
     * @return ticket/show_tickets
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteTicket(@Valid @RequestBody final TicketDTO ticketDTO) throws AirPortDaoException {
        final var ticket = new Ticket();
        BeanUtils.copyProperties(ticketDTO, ticket);
        return new ResponseEntity<Boolean>(ticketService.delete(ticket), HttpStatus.OK);
    }

    /**
     * ticket/ PUT method.
     * @return ticket/show_tickets
     */
    @PutMapping
    public ResponseEntity<Ticket> updateTicket(@Valid @RequestBody final TicketDTO ticketDTO) throws AirPortDaoException {
        final var ticket = new Ticket();
        BeanUtils.copyProperties(ticketDTO, ticket);
        return new ResponseEntity<Ticket>(ticketService.update(ticket), HttpStatus.OK);
    }
}
