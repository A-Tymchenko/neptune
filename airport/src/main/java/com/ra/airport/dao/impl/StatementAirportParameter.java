package com.ra.airport.dao.impl;

public enum StatementAirportParameter {

    AIRPORT_NAME(1),
    AIRPORT_NUM(2),
    AIRPORT_TYPE(3),
    AIRPORT_ADDRESSES(4),
    AIRPORT_TERMINAL(5),
    AIRPORT_ID(6);

    private final int parameterNumber;

    StatementAirportParameter(final int parameterNumber) {
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