package com.ra.courses.project.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Order implements Serializable {

    private int id;
    private int number;
    private double totalPrice;
    private boolean deliveryIncluded;
    private int deliveryCost;
    private boolean executed;
    private User user;
    private Date creationDate;
    private Set<Good> goods = new HashSet<>();

    public Order() {
        super();
    }

    public Order(int id, int number, double totalPrice, boolean deliveryIncluded, int deliveryCost, boolean executed) {
        this.id = id;
        this.number = number;
        this.totalPrice = totalPrice;
        this.deliveryIncluded = deliveryIncluded;
        this.deliveryCost = deliveryCost;
        this.executed = executed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isDeliveryIncluded() {
        return deliveryIncluded;
    }

    public void setDeliveryIncluded(boolean deliveryIncluded) {
        this.deliveryIncluded = deliveryIncluded;
    }

    public int getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(int deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Set<Good> getGoods() {
        return goods;
    }

    public void setGoods(Set<Good> goods) {
        this.goods = goods;
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
                + user
                + ", creationDate="
                + creationDate
                + ", goods="
                + goods
                + '}';
    }
}
