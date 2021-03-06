package com.ra.airport.controller;

import javax.validation.Valid;

import com.ra.airport.dto.TicketDTO;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.TicketService;
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

@RequestMapping("/tickets")
@Controller
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
    public ResponseEntity<TicketDTO> createTicket(@Valid @RequestBody final TicketDTO ticketDTO) throws AirPortDaoException {
        return new ResponseEntity<>(ticketService.create(ticketDTO), HttpStatus.OK);
    }

    /**
     * ticket/ DELETE method.
     * @return ticket/show_tickets
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteTicket(@Valid @RequestBody final TicketDTO ticketDTO) throws AirPortDaoException {
        return new ResponseEntity<>(ticketService.delete(ticketDTO), HttpStatus.OK);
    }

    /**
     * ticket/ PUT method.
     * @return ticket/show_tickets
     */
    @PutMapping
    public ResponseEntity<TicketDTO> updateTicket(@Valid @RequestBody final TicketDTO ticketDTO) throws AirPortDaoException {
        return new ResponseEntity<>(ticketService.update(ticketDTO), HttpStatus.OK);
    }
}
