package com.ra.advertisement.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class DeviceDto {
    private Long devId;

    @NotEmpty(message = "Name can't be empty")
    @Size(min = MIN_LENGTH, max = MAX_LENGTH, message = "Name should be longer than 4 and less than 50")
    private String name;

    @NotEmpty(message = "Model can't be empty")
    @Size(min = MIN_LENGTH, max = MAX_LENGTH, message = "Model should be longer than 4 and less than 50")
    private String model;

    @NotEmpty(message = "DeviceType can't be empty")
    @Size(min = MIN_LENGTH, max = MAX_LENGTH, message = "DeviceType should be longer than 4 and less than 50")
    private String deviceType;

    private static final int MIN_LENGTH = 4;
    private static final int MAX_LENGTH = 50;

    public DeviceDto() {
    }

    public DeviceDto(final Long devId, final String name, final String model, final String deviceType) {
        this.devId = devId;
        this.name = name;
        this.model = model;
        this.deviceType = deviceType;
    }

    public DeviceDto(final String name, final String model, final String deviceType) {
        this.name = name;
        this.model = model;
        this.deviceType = deviceType;
    }

    public Long getDevId() {
        return devId;
    }

    public void setDevId(final Long devId) {
        this.devId = devId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(final String model) {
        this.model = model;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(final String deviceType) {
        this.deviceType = deviceType;
    }

    @Override
    public String toString() {
        return "Device{"
                + "devId=" + devId
                + ", name='" + name + '\''
                + ", model='" + model + '\''
                + ", deviceType='" + deviceType + '\''
                + '}';
    }
}
