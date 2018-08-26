package com.ra.airport.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PlaneDto {

    private Integer planeId;

    @NotNull
    private Integer seatsCount;

    @NotBlank
    private String model;

    @NotBlank
    private String type;

    @NotNull
    private Integer plateNumber;

    public Integer getPlaneId() {
        return planeId;
    }

    public void setPlaneId(final Integer planeId) {
        this.planeId = planeId;
    }

    public Integer getSeatsCount() {
        return seatsCount;
    }

    public void setSeatsCount(final Integer seatsCount) {
        this.seatsCount = seatsCount;
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
