package com.ra.advertisement.controller;

public enum PathsEnum {
    ALL_ADVERTISEMENTS("/WEB-INF/jsp/alladvertisement.jsp"),
    ALL_DEVICES("/WEB-INF/jsp/alldevices.jsp"),
    ALL_PUBLISHERS("/WEB-INF/jsp/allpublishers.jsp"),
    ALL_PROVIDERS("/WEB-INF/jsp/allproviders.jsp"),
    INDEX_PAGE("/index.jsp"),
    ADVERTISEMENT_FORM("/advertisementform.jsp"),
    DEVICE_FORM("/deviceform.jsp"),
    PUBLISHER_FORM("/publisherform.jsp"),
    PROVIDER_FORM("/providerform.jsp");

    private String path;

    PathsEnum(final String info) {
        path = info;
    }

    public String getPath() {
        return path;
    }
}
