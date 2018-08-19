package com.ra.airport.config;

import java.util.HashMap;
import java.util.Map;

import com.ra.airport.servlet.handler.CreateAirportHandler;
import com.ra.airport.servlet.handler.DeleteAirportHandler;
import com.ra.airport.servlet.handler.GetAirportsHandler;
import com.ra.airport.servlet.handler.ServletHandler;
import com.ra.airport.servlet.handler.UpdateAirportHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AirportMappingConfig {

    @Autowired
    private transient GetAirportsHandler airportsHandler;

    @Autowired
    private transient CreateAirportHandler createAirportHand;

    @Autowired
    private transient UpdateAirportHandler updateAirportHand;

    @Autowired
    private transient DeleteAirportHandler deleteAirportHand;

    /**
     * Register Airport handlers map.
     *
     * @return Map.
     */
    public Map<String, ServletHandler> getMapping() {
        final Map<String, ServletHandler> handlers = new HashMap<>();
        handlers.put("/airports", airportsHandler);
        handlers.put("/airport/create", createAirportHand);
        handlers.put("/airport/update", updateAirportHand);
        handlers.put("/airport/delete", deleteAirportHand);
        return handlers;
    }
}
