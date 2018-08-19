package com.ra.airport.config;

import java.util.HashMap;
import java.util.Map;

import com.ra.airport.servlet.handler.CreateTicketHandler;
import com.ra.airport.servlet.handler.DeleteTicketHandler;
import com.ra.airport.servlet.handler.GetTicketsHandler;
import com.ra.airport.servlet.handler.ServletHandler;
import com.ra.airport.servlet.handler.UpdateTicketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TicketMappingConfig {

    @Autowired
    private transient GetTicketsHandler getTicketsHandler;

    @Autowired
    private transient CreateTicketHandler createTicketHand;

    @Autowired
    private transient UpdateTicketHandler updateTicketHand;

    @Autowired
    private transient DeleteTicketHandler deleteTicketHand;

    /**
     * Register Ticket handlers map.
     *
     * @return Map.
     */
    public Map<String, ServletHandler> getMapping() {
        final Map<String, ServletHandler> handlers = new HashMap<>();
        handlers.put("/tickets", getTicketsHandler);
        handlers.put("/ticket/create", createTicketHand);
        handlers.put("/ticket/update", updateTicketHand);
        handlers.put("/ticket/delete", deleteTicketHand);
        return handlers;
    }
}
