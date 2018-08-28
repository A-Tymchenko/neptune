package com.ra.airport.controller;

import com.ra.airport.dto.TicketDTO;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TicketControllerMockitoTest {

    @Mock
    TicketService ticketService;

    @Mock
    Model model;

    @InjectMocks
    TicketController ticketController;
    String redirectionPath;
    TicketDTO ticketDTO;

    @BeforeEach
    public void init() throws AirPortDaoException {
        MockitoAnnotations.initMocks(this);
        redirectionPath = "/index";
        ticketDTO = initTicketDTO();
    }

    @Test
    public void whenCallGetTicketsThenReturnPathToJsp() throws AirPortDaoException {
        assertEquals(ticketController.getTickets(model), "ticket/show_tickets");
    }

    @Test
    public void whenCallPOSTTicketsThenReturnResponseEntity() throws AirPortDaoException {
        ResponseEntity responseEntity = ticketController.createTicket(ticketDTO);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void whenCallDELETETicketsThenReturnResponseEntity() throws AirPortDaoException {
        ResponseEntity responseEntity = ticketController.deleteTicket(ticketDTO);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void whenCallPUTTicketsThenReturnResponseEntity() throws AirPortDaoException {
        ResponseEntity responseEntity = ticketController.updateTicket(ticketDTO);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    private TicketDTO initTicketDTO() {
        var ticketDTO = new TicketDTO();
        ticketDTO.setTicketId(8);
        ticketDTO.setTicketNumber("A123-456F");
        ticketDTO.setPassengerName("Petro Velykyi");
        ticketDTO.setDocument("AA192939");
        ticketDTO.setSellingDate(Timestamp.valueOf("2018-06-21 21:05:00"));
        return ticketDTO;
    }
}