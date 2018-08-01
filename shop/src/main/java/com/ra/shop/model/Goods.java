package com.ra.shop.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Entity goods.
 */

public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idNumber;
    private String name;
    private Long barcode;
    private float price;

    public Goods(final String name, final Long barcode, final float price) {
        this.name = name;
        this.barcode = barcode;
        this.price = price;
    }

    public Long getId() {
        return idNumber;
    }

    public void setId(final Long idNumber) {
        this.idNumber = idNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Long getBarcode() {
        return barcode;
    }

    public void setBarcode(final Long barcode) {
        this.barcode = barcode;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(final float price) {
        this.price = price;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Goods goods = (Goods) obj;
        return Float.compare(goods.price, price) == 0
            && Objects.equals(idNumber, goods.idNumber)
            && Objects.equals(name, goods.name)
            && Objects.equals(barcode, goods.barcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idNumber, name, barcode, price);
    }

    @Override
    public String toString() {
        return "Goods{"
            + "id=" + idNumber
            + ", name='" + name + '\''
            + ", barcode=" + barcode
            + ", price=" + price
            + '}';
    }
}
