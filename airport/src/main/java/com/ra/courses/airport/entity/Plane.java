package com.ra.courses.airport.entity;

import java.io.Serializable;
import java.util.Objects;

public class Plane implements Serializable {

    private Integer id=0;
    private String owner;
    private String model;
    private String type;
    private Integer platenumber;

    public Plane() {
    }

    public Plane(int id, String owner, String model, String type, int platenumber) {
        this.id = id;
        this.owner = owner;
        this.model = model;
        this.type = type;
        this.platenumber = platenumber;
    }

    public Integer getId() {
        return id;
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

    public int getPlatenumber() {
        return platenumber;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPlatenumber(int platenumber) {
        this.platenumber = platenumber;
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
                return Objects.equals(id, plane.id)
                                && Objects.equals(owner, plane.owner)
                                && Objects.equals(model, plane.model)
                                && Objects.equals(type, plane.type)
                                && Objects.equals(platenumber, plane.platenumber);

            }

    @Override
    public String toString() {
        return "Plane{" +
                "id=" + id +
                ", owner='" + owner + '\'' +
                ", model='" + model + '\'' +
                ", type='" + type + '\'' +
                ", platenumber=" + platenumber +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,owner,model,type,platenumber);
    }
}

