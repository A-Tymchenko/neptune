package com.ra.shop.dao.exception;

public enum ExceptionMessage {

    FAILED_TO_CREATE_NEW_WAREHOUSE("Failed to create new warehouse"),
    FAILED_TO_UPDATE_WAREHOUSE("Failed to update warehouse"),
    FAILED_TO_DELETE_WAREHOUSE("Failed to delete warehouse"),
    FAILED_TO_GET_WAREHOUSE_BY_ID("Failed to get the warehouse by id"),
    FAILED_TO_GET_ALL_WAREHOUSES("Failed to get all warehouses"),
    THE_WAREHOUSE_CANNOT_BE_NULL("Warehouse cannot be null"),
    FAILED_TO_RETRIEVE_DATA_FROM_RESULTSET_IN_WAREHOUSE_CONSTRUCTER("Warehouse constructor failed to retrieve data from ResultSet");

    private final String message;

    ExceptionMessage(final String message) {
        this.message = message;
    }

    /**
     * simple exception message.
     * @return String of the message
     */
    public String getMessage() {
        return message;
    }
}
