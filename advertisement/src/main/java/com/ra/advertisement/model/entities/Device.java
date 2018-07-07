package com.ra.advertisement.model.entities;

public class Device {

    private Long devId;
    private String name;
    private String model;
    private String deviceType;

    public Device() {
    }

    public Device(final Long devId, final String name, final String model, final String deviceType) {
        this.devId = devId;
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
