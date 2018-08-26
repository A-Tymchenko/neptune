package com.ra.airport.entity;

import java.util.Objects;

public class Plane {

    private Integer planeId;

    private Integer seatsCount;

    private String model;

    private String type;

    private Integer plateNumber;

    public Plane() {
    }

    public Plane(final Integer planeId, final Integer seatsCount, final String model, final String type, final int plateNumber) {
        this.planeId = planeId;
        this.seatsCount = seatsCount;
        this.model = model;
        this.type = type;
        this.plateNumber = plateNumber;
    }

    public Integer getPlaneId() {
        return planeId;
    }

    public Integer getSeatsCount() {
        return seatsCount;
    }

    public String getModel() {
        return model;
    }

    public String getType() {
        return type;
    }

    public Integer getPlateNumber() {
        return plateNumber;
    }

    public void setPlaneId(final Integer planeId) {
        this.planeId = planeId;
    }

    public void setSeatsCount(final Integer seatsCount) {
        this.seatsCount = seatsCount;
    }

    public void setModel(final String model) {
        this.model = model;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public void setPlateNumber(final Integer plateNumber) {
        this.plateNumber = plateNumber;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final Plane plane = (Plane) object;
        return Objects.equals(planeId, plane.planeId)
                && Objects.equals(seatsCount, plane.seatsCount)
                && Objects.equals(model, plane.model)
                && Objects.equals(type, plane.type)
                && Objects.equals(plateNumber, plane.plateNumber);
    }

    @Override
    public String toString() {
        return "Plane{"
                + "planeId=" + planeId
                + ", seatsCount='" + seatsCount + '\''
                + ", model='" + model + '\''
                + ", type='" + type + '\''
                + ", plateNumber=" + plateNumber
                + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(planeId, seatsCount, model, type, plateNumber);
    }
}

