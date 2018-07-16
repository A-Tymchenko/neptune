package com.ra.airport.dao.impl;

public enum StatementParameter {

    AIRPORT_ID(6),
    AIRPORT_NAME(1),
    AIRPORT_NUM(2),
    AIRPORT_TYPE(3),
    AIRPORT_ADDRESSES(4),
    AIRPORT_TERMINAL(5);

    private final int num;

    StatementParameter(final int num) {
        this.num = num;
    }

    /**
     * Return number of parameter.
     *
     * @return number of parameter
     */
    public int get() {
        return num;
    }
}
