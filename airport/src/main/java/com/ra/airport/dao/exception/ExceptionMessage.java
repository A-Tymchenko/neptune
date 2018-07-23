package com.ra.airport.dao.exception;

/**
 * Contains exception messages for {@link AirPortDaoException} class.
 */
public enum ExceptionMessage {

    FAILED_TO_CREATE_NEW_FLIGHT("Failed to create new flight"),
    FAILED_TO_UPDATE_FLIGHT_WITH_ID("Failed to update flight with id "),
    FAILED_TO_DELETE_FLIGHT_WITH_ID("Failed to delete flight with id "),
    FAILED_TO_GET_FLIGHT_WITH_ID("Failed to get flight with id "),
    FAILED_TO_GET_ALL_FLIGHTS("Failed to get all flights"),
    FLIGHT_ID_CANNOT_BE_NULL("Flight id can't be null"),
    FAILED_TO_CREATE_NEW_AIRPORT("Failed to create new airport"),
    FAILED_TO_UPDATE_AIRPORT_WITH_ID("Failed to update airport with id "),
    FAILED_TO_DELETE_AIRPORT_WITH_ID("Failed to delete airport with id "),
    FAILED_TO_GET_AIRPORT_WITH_ID("Failed to get airport with id "),
    FAILED_TO_GET_ALL_AIRPORTS("Failed to get all airports");

    private final String message;

    ExceptionMessage(final String message) {
        this.message = message;
    }

    /**
     * Return message of Exception.
     *
     * @return text of message
     */
    public String get() {
        return message;
    }
}
