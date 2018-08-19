package com.ra.airport.config;

import java.util.HashMap;
import java.util.Map;

import com.ra.airport.servlet.handler.CreatePlaneHandler;
import com.ra.airport.servlet.handler.DeletePlaneHandler;
import com.ra.airport.servlet.handler.GetPlanesHandler;
import com.ra.airport.servlet.handler.ServletHandler;
import com.ra.airport.servlet.handler.UpdatePlaneHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlaneMappingConfig {

    @Autowired
    private transient GetPlanesHandler getPlanesHandler;

    @Autowired
    private transient CreatePlaneHandler createPlaneHand;

    @Autowired
    private transient UpdatePlaneHandler updatePlaneHand;

    @Autowired
    private transient DeletePlaneHandler deletePlaneHand;

    /**
     * Register Plane handlers map.
     *
     * @return Map.
     */
    public Map<String, ServletHandler> getMapping() {
        final Map<String, ServletHandler> handlers = new HashMap<>();
        handlers.put("/plane/create", createPlaneHand);
        handlers.put("/plane/update", updatePlaneHand);
        handlers.put("/plane/delete", deletePlaneHand);
        handlers.put("/planes", getPlanesHandler);
        return handlers;
    }
}
