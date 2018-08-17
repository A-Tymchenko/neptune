package com.ra.airport.dto;

public class PlaneDto {

    private Integer planeId;

    private String owner;

    private String model;

    private String type;

    private Integer plateNumber;

    public Integer getPlaneId() {
        return planeId;
    }

    public void setPlaneId(final Integer planeId) {
        this.planeId = planeId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(final String owner) {
        this.owner = owner;
    }

    public String getModel() {
        return model;
    }

    public void setModel(final String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public Integer getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(final Integer plateNumber) {
        this.plateNumber = plateNumber;
    }
}
