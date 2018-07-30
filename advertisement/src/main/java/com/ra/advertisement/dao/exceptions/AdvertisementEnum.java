package com.ra.advertisement.dao.exceptions;

public enum AdvertisementEnum {
    ADVERTISEMENT("Check table Advertisement"), DEVICES("Check table Devices"),
    PROVIDER("Check table Provider"), PUBLISHER("Check table Publisher");

    private String message;

    AdvertisementEnum(final String info) {
        message = info;
    }

    public String getMessage() {
        return message;
    }
}
