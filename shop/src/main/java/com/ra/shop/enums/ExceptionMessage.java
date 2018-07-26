package com.ra.shop.enums;

public enum ExceptionMessage {

    FAILED_TO_CREATE_NEW_SHOP("Failed to create new shop"),
    FAILED_TO_UPDATE_SHOP("Failed to update shop"),
    FAILED_TO_DELETE_SHOP("Failed to delete shop"),
    FAILED_TO_GET_SHOP_BY_ID("Failed to get the shop by id"),
    FAILED_TO_GET_ALL_SHOP("Failed to get all shop"),
    THE_SHOP_CANNOT_BE_NULL("Shop cannot be null");

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
