package com.ra.airport.service;

import com.ra.airport.dto.TicketDTO;
import com.ra.airport.entity.Ticket;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.repository.impl.TicketDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TicketServiceMockitoTest {

    @Mock
    private TicketDao ticketDao;
    private TicketDTO ticketDTO;
    private List<Ticket> tickets;
    private Ticket ticket;

    @InjectMocks
    private TicketService ticketService;

    @BeforeEach
    public void init() throws AirPortDaoException {
        MockitoAnnotations.initMocks(this);
        initTicketDTO();
        ticket = new Ticket(8, "123QWE-EWQ321", "Petro Velykyi",
                "AA192939", Timestamp.valueOf("2018-06-21 21:05:00"));
        tickets = new ArrayList<>();
        tickets.add(ticket);
        Mockito.when(ticketDao.create(Mockito.any())).thenReturn(ticket);
        Mockito.when(ticketDao.update(Mockito.any())).thenReturn(ticket);
        Mockito.when(ticketDao.delete(Mockito.any())).thenReturn(true);
        Mockito.when(ticketDao.getById(8)).thenReturn(Optional.ofNullable(ticket));
        Mockito.when(ticketDao.getAll()).thenReturn(tickets);
    }

    @Test
    public void whenCreateThenReturnCreatedAirport() throws AirPortDaoException {
        assertEquals(ticketService.create(ticketDTO), ticketDTO);
    }

    @Test
    public void whenUpdateThenReturnUpdatedAirport() throws AirPortDaoException {
        assertEquals(ticketService.update(ticketDTO), ticketDTO);
    }

    @Test
    public void whenDeleteThenReturnDeletedAirport() throws AirPortDaoException {
        assertEquals(ticketService.delete(ticketDTO), true);
    }

    @Test
    public void whenGetAllThenReturnCreatedAirports() throws AirPortDaoException {
        assertEquals(ticketService.getAll().size(), tickets.size());
    }

    private TicketDTO initTicketDTO() {
        ticketDTO = new TicketDTO();
        ticketDTO.setTicketId(8);
        ticketDTO.setTicketNumber("A123-456F");
        ticketDTO.setPassengerName("Petro Velykyi");
        ticketDTO.setDocument("AA192939");
        ticketDTO.setSellingDate(Timestamp.valueOf("2018-06-21 21:05:00"));
        return ticketDTO;
    }
}