package com.ra.shop.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Entity good.
 */

public class Good implements Serializable {

    private Long id;
    private String name;
    private float price;

    public Good() {
    }

    public Good(Long id, String name, float price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Good good = (Good) o;
        return Float.compare(good.price, price) == 0
                && Objects.equals(id, good.id)
                && Objects.equals(name, good.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, price);
    }

    @Override
    public String toString() {
        return "Good{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", price=" + price
                + '}';
    }
}
