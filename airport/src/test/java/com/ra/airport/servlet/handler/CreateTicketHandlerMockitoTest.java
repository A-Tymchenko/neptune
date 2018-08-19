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

class CreateTicketHandlerMockitoTest {

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private CreateTicketHandler ticketHandler;
    private MockHttpServletRequest mockRequest;
    private MockHttpServletResponse mockResponse;
    private Ticket ticket;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        ticket = new Ticket(null, "123QWE-EWQ321", "Petro Velykyi",
                "AA192939", Timestamp.valueOf("2018-06-21 21:05:00"));
        mockRequest = new MockHttpServletRequest();
        mockResponse = new MockHttpServletResponse();
        createRequest(mockRequest);
    }

    @Test
    public void whenCreateThenNewTicketShouldBeCreated() throws AirPortDaoException {
        ticketHandler.post(mockRequest, mockResponse);
        Mockito.verify(ticketService, Mockito.times(1)).create(ticket);
    }

    private void createRequest(MockHttpServletRequest mockRequest) {
        mockRequest.setParameter("ticketNumber", "123QWE-EWQ321");
        mockRequest.setParameter("passengerName", "Petro Velykyi");
        mockRequest.setParameter("document", "AA192939");
        mockRequest.setParameter("sellingDate", "2018-06-21 21:05:00");
    }

}