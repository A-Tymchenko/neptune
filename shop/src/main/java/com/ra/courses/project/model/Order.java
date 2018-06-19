package com.ra.courses.project.model;

import java.io.Serializable;
import java.util.Objects;

public class Order implements Serializable {

    private Long id;
    private Integer number;
    private Double totalPrice;
    private Boolean deliveryIncluded;
    private Integer deliveryCost;
    private Boolean executed;

    public Order() {
        super();
    }

    public Order(Long id, Integer number, Double totalPrice, Boolean deliveryIncluded, Integer deliveryCost, Boolean executed) {
        super();
        this.id = id;
        this.number = number;
        this.totalPrice = totalPrice;
        this.deliveryIncluded = deliveryIncluded;
        this.deliveryCost = deliveryCost;
        this.executed = executed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
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
        return id == order.id;
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
                + ", totalPrice="
                + totalPrice
                + ", deliveryIncluded="
                + deliveryIncluded
                + ", deliveryCost="
                + deliveryCost
                + ", executed="
                + executed
                + ", user="
                + '}';
    }
}
