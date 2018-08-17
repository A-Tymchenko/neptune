package com.ra.airport.servlet.handler;

import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

class UpdateTicketHandlerMockitoTest {
    private UpdateTicketHandler ticketHandler;
    private MockHttpServletRequest mockRequest;
    private MockHttpServletResponse mockResponse;

    @Mock
    private TicketService ticketService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockRequest = new MockHttpServletRequest();
        mockResponse = new MockHttpServletResponse();
        ticketHandler = new UpdateTicketHandler(ticketService);
        createRequest(mockRequest);
    }

    @Test
    public void whenUpdateThenTicketShouldBeUpdated() throws AirPortDaoException {
        ticketHandler.post(mockRequest, mockResponse);
    }

    private void createRequest(MockHttpServletRequest mockRequest) {
        mockRequest.setParameter("ticketId", "1");
        mockRequest.setParameter("ticketNumber", "123QWE-EWQ321");
        mockRequest.setParameter("passengerName", "Petro Velykyi");
        mockRequest.setParameter("document", "AA192939");
        mockRequest.setParameter("sellingDate", "2018-06-21 21:05:00");
    }
}