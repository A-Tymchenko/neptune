package com.ra.airport.entity;

import java.io.Serializable;
import java.util.Objects;

public class Plane implements Serializable {

    private Integer identifier;
    private String owner;
    private String model;
    private String type;
    private Integer plateNumber;
    private static final long serialVersionUID = 1L;

    public Plane() {
    }

    public Plane(final int identifier, final String owner, final String model, final String type, final int plateNumber) {
        this.identifier = identifier;
        this.owner = owner;
        this.model = model;
        this.type = type;
        this.plateNumber = plateNumber;
    }

    public Integer getIdentifier() {
        return identifier;
    }

    public String getOwner() {
        return owner;
    }

    public String getModel() {
        return model;
    }

    public String getType() {
        return type;
    }

    public int getPlateNumber() {
        return plateNumber;
    }

    public void setIdentifier(final int identifier) {
        this.identifier = identifier;
    }

    public void setOwner(final String owner) {
        this.owner = owner;
    }

    public void setModel(final String model) {
        this.model = model;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public void setPlateNumber(final int plateNumber) {
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
        return Objects.equals(identifier, plane.identifier)
                && Objects.equals(owner, plane.owner)
                && Objects.equals(model, plane.model)
                && Objects.equals(type, plane.type)
                && Objects.equals(plateNumber, plane.plateNumber);
    }

    @Override
    public String toString() {
        return "Plane{"
                + "identifier=" + identifier
                + ", owner='" + owner + '\''
                + ", model='" + model + '\''
                + ", type='" + type + '\''
                + ", plateNumber=" + plateNumber
                + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, owner, model, type, plateNumber);
    }
}
