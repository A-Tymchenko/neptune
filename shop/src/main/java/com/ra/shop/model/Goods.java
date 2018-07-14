package com.ra.shop.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Entity good.
 */

public class Goods implements Serializable {
    private Long id;

    private String name;
    private Long barcode;
    private float price;

    public Goods() {
    }

    public Goods(Long id, String name, Long barcode, float price) {
        this.id = id;
        this.name = name;
        this.barcode = barcode;
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

    public Long getBarcode() {
        return barcode;
    }

    public void setBarcode(Long barcode) {
        this.barcode = barcode;
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
        Goods goods = (Goods) o;
        return Float.compare(goods.price, price) == 0
                && Objects.equals(id, goods.id)
                && Objects.equals(name, goods.name)
                && Objects.equals(barcode, goods.barcode);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, barcode, price);
    }

    @Override
    public String toString() {
        return "Goods{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", barcode=" + barcode
                + ", price=" + price
                + '}';
    }
}
