package com.ra.airport.servlet.handler;

import com.ra.airport.entity.Ticket;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.sql.Timestamp;

class DeleteTicketHandlerMockitoTest {

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private DeleteTicketHandler ticketHandler;
    private MockHttpServletRequest mockRequest;
    private MockHttpServletResponse mockResponse;
    private Ticket ticket;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        ticket = new Ticket(1, null, null,
                null, null);
        mockRequest = new MockHttpServletRequest();
        mockResponse = new MockHttpServletResponse();
        mockRequest.setParameter("ticketId", "1");
    }

    @Test
    public void whenDeleteTicketThenTicketShouldBeDeleted() throws AirPortDaoException {
        ticketHandler.delete(mockRequest, mockResponse);
        Mockito.verify(ticketService, Mockito.times(1)).delete(ticket);
    }

    @Test
    public void whenPostTicketThenTicketShouldBeDeleted() throws AirPortDaoException {
        ticketHandler.post(mockRequest, mockResponse);
        Mockito.verify(ticketService, Mockito.times(1)).delete(ticket);
    }

    @Test
    void whenIdIsBlankThenNothingShouldBeDeleted() throws AirPortDaoException {
        mockRequest.setParameter("ticketId", "");
        ticketHandler.delete(mockRequest, mockResponse);
    }
}