package com.ra.airport.repository.impl;

public enum StatementParameter {

    FLIGHT_NAME(1),
    FLIGHT_CARRIER(2),
    FLIGHT_MEAL_ON(3),
    FLIGHT_FARE(4),
    FLIGHT_DEPARTURE_DATE(5),
    FLIGHT_ARRIVAL_DATE(6),
    FLIGHT_ID(7),

    AIRPORT_ID(6),
    AIRPORT_NAME(1),
    AIRPORT_NUM(2),
    AIRPORT_TYPE(3),
    AIRPORT_ADDRESSES(4),
    AIRPORT_TERMINAL(5),

    TICKET_NUMBER(1),
    TICKET_PASSENGER_NAME(2),
    TICKET_DOCUMENT(3),
    TICKET_SELLING_DATE(4),
    TICKET_ID(5),

    PLANE_SEATS_COUNT(1),
    PLANE_MODEL(2),
    PLANE_TYPE(3),
    PLANE_PLATE_NUMBER(4),
    PLANE_ID(5);

    private final int parameterNumber;

    StatementParameter(final int parameterNumber) {
        this.parameterNumber = parameterNumber;
    }

    /**
     * Return number of parameter.
     *
     * @return parameter number
     */
    public int get() {
        return parameterNumber;
    }
}
