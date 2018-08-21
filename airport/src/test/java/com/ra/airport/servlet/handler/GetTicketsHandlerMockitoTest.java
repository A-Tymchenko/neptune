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

import java.util.List;
import java.util.function.Consumer;

class GetTicketsHandlerMockitoTest {

    @Mock
    private List<Ticket> tickets;

    @InjectMocks
    public GetTicketsHandler ticketHandler;
    private MockHttpServletRequest mockRequest;
    private MockHttpServletResponse mockResponse;

    @Mock
    Ticket ticket;

    @Mock
    private TicketService ticketService;

    @BeforeEach
    public void init() throws AirPortDaoException {
        MockitoAnnotations.initMocks(this);
        mockRequest = new MockHttpServletRequest();
        mockResponse = new MockHttpServletResponse();
        ticketHandler = new GetTicketsHandler(ticketService);
        Mockito.when(ticketService.getAll()).thenReturn(tickets);
    }

    @Test
    public void whenGetTicketsThenShouldBeJSPSet() throws AirPortDaoException {
        Mockito.doAnswer(invocation -> {
            ((Consumer)invocation.getArguments()[0]).accept(ticket);
            return null;
        }).when(tickets).forEach(Mockito.any());
        ticketHandler.get(mockRequest, mockResponse);
        Mockito.verify(ticketService, Mockito.times(1)).getAll();
    }

    @Test
    public void whenPostTicketsThenShouldBeJSPSet() throws AirPortDaoException {
        Mockito.doAnswer(invocation -> {
            ((Consumer)invocation.getArguments()[0]).accept(ticket);
            return null;
        }).when(tickets).forEach(Mockito.any());
        ticketHandler.post(mockRequest, mockResponse);
        Mockito.verify(ticketService, Mockito.times(1)).getAll();
    }
}