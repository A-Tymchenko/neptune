package com.ra.airport.config;

import java.util.HashMap;
import java.util.Map;

import com.ra.airport.servlet.handler.CreateFlightHandler;
import com.ra.airport.servlet.handler.DeleteFlightHandler;
import com.ra.airport.servlet.handler.GetFlightsHandler;
import com.ra.airport.servlet.handler.ServletHandler;
import com.ra.airport.servlet.handler.UpdateFlightHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FlightMappingConfig {
    @Autowired
    private transient GetFlightsHandler getFlightsHand;

    @Autowired
    private transient CreateFlightHandler createFlightHand;

    @Autowired
    private transient DeleteFlightHandler deleteFlightHand;

    @Autowired
    private transient UpdateFlightHandler updateFlightHand;

    /**
     * Register Flight handlers map.
     *
     * @return Map.
     */
    public Map<String, ServletHandler> getMapping() {
        final Map<String, ServletHandler> handlers = new HashMap<>();
        handlers.put("/flights", getFlightsHand);
        handlers.put("/flight/create", createFlightHand);
        handlers.put("/flight/delete", deleteFlightHand);
        handlers.put("/flight/update", updateFlightHand);
        return handlers;
    }
}
