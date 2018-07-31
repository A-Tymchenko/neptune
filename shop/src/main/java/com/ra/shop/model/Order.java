package com.ra.shop.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Entity represents an order that contains all important information.
 */
public class Order implements Serializable {

    private long id;
    private Integer number;
    private Double price;
    private Boolean deliveryIncluded;
    private Integer deliveryCost;
    private Boolean executed;

    public Order(Integer number, Double price, Boolean deliveryIncluded,
                 Integer deliveryCost, Boolean executed) {
        this.number = number;
        this.price = price;
        this.deliveryIncluded = deliveryIncluded;
        this.deliveryCost = deliveryCost;
        this.executed = executed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getDeliveryIncluded() {
        return deliveryIncluded;
    }

    public void setDeliveryIncluded(Boolean deliveryIncluded) {
        this.deliveryIncluded = deliveryIncluded;
    }

    public Integer getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(Integer deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public Boolean getExecuted() {
        return executed;
    }

    public void setExecuted(Boolean executed) {
        this.executed = executed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Order{"
                + "id="
                + id
                + ", number="
                + number
                + ", price="
                + price
                + ", deliveryIncluded="
                + deliveryIncluded
                + ", deliveryCost="
                + deliveryCost
                + ", executed="
                + executed
                + '}';
    }
}
