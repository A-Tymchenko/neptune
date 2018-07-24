package com.ra.shop.entity;

import java.io.Serializable;
import java.util.Objects;

public class Warehouse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idNumber;
    private String name;
    private Double price;
    private Integer amount;

    public Warehouse() {
    }

    public Warehouse(final String name, final Double price, final Integer amount) {
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public Long getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(final Long idNumber) {
        this.idNumber = idNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(final Double price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(final Integer amount) {
        this.amount = amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idNumber, name);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Warehouse warehouse = (Warehouse) obj;
        return Objects.equals(idNumber, warehouse.idNumber)
                && Objects.equals(name, warehouse.name)
                && Objects.equals(price, warehouse.price)
                && Objects.equals(amount, warehouse.amount);
    }

    @Override
    public String toString() {
        return "Warehouse{"
                + "idNumber=" + idNumber
                + ", name='" + name + '\''
                + ", price=" + price
                + ", amount=" + amount
                + '}';
    }
}
