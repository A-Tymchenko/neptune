package com.ra.courses.airport.dao.impl;


public enum ExceptionMessage {

    FAILED_TO_CREATE_NEW_PLANE("Failed to create new plane"),
    FAILED_TO_UPDATE_PLANE_WITH_ID("Failed to update plane with id "),
    FAILED_TO_DELETE_PLANE_WITH_ID("Failed to delete plane with id "),
    FAILED_TO_GET_PLANE_WITH_ID("Failed to get plane with id "),
    FAILED_TO_GET_ALL_PLANES("Failed to get all planes"),
    PLANE_ID_CANNOT_BE_NULL("Plane id can't be null");

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


