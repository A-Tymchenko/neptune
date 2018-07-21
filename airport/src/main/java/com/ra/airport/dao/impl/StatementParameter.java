package com.ra.airport.dao.impl;

public enum StatementParameter {

    NAME(1),
    CARRIER(2),
    DURATION(3),
    MEAL_ON(4),
    FARE(5),
    DEPARTURE_DATE(6),
    ARRIVAL_DATE(7),
    IDENTIFIER(8),
    AIRPORT_ID(6),
    AIRPORT_NAME(1),
    AIRPORT_NUM(2),
    AIRPORT_TYPE(3),
    AIRPORT_ADDRESSES(4),
    AIRPORT_TERMINAL(5);

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
