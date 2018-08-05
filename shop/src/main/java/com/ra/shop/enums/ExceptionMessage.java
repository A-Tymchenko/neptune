package com.ra.shop.enums;

public enum ExceptionMessage {

    FAILED_TO_CREATE_NEW_WAREHOUSE("Failed to create new warehouse"),
    FAILED_TO_UPDATE_WAREHOUSE("Failed to update warehouse"),
    FAILED_TO_DELETE_WAREHOUSE("Failed to delete warehouse"),
    FAILED_TO_GET_WAREHOUSE_BY_ID("Failed to get the warehouse by id"),
    FAILED_TO_GET_ALL_WAREHOUSE("Failed to get all warehouse"),

    FAILED_TO_CREATE_NEW_GOODS("Failed to create new goods"),
    FAILED_TO_UPDATE_GOODS("Failed to update goods"),
    FAILED_TO_DELETE_GOODS("Failed to delete goods"),
    FAILED_TO_GET_GOODS_BY_ID("Failed to get the goods by id"),
    FAILED_TO_GET_ALL_GOODS("Failed to get all goods"),

    FAILED_TO_CREATE_NEW_ORDER("Failed to create new order"),
    FAILED_TO_UPDATE_ORDER("Failed to update order"),
    FAILED_TO_DELETE_ORDER("Failed to delete order"),
    FAILED_TO_GET_ORDER_BY_ID("Failed to get the order by id"),
    FAILED_TO_GET_ALL_ORDER("Failed to get all order"),

    FAILED_TO_CREATE_NEW_USER("Failed to create new user"),
    FAILED_TO_UPDATE_USER("Failed to update user"),
    FAILED_TO_DELETE_USER("Failed to delete user"),
    FAILED_TO_GET_USER_BY_ID("Failed to get the user by id"),
    FAILED_TO_GET_ALL_USER("Failed to get all user");

    private final String message;

    ExceptionMessage(final String message) {
        this.message = message;
    }

    /**
     * simple exception message.
     *
     * @return String of the message
     */
    public String getMessage() {
        return message;
    }
    }
