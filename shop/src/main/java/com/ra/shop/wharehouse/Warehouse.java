package com.ra.shop.wharehouse;

import com.ra.shop.dao.exception.ExceptionMessage;
import com.ra.shop.dao.exception.WarehouseDaoException;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Warehouse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idNumber;
    private String name;
    private Double price;
    private Integer amount;

    public Warehouse() {
    }

    public Warehouse(String name, Double price, Integer amount) {
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public Warehouse(ResultSet resultSet) throws WarehouseDaoException {
        try {
            this.idNumber = resultSet.getInt("id");
            this.name = resultSet.getString("name");
            this.price = resultSet.getDouble("price");
            this.amount = resultSet.getInt("amount");
        } catch (SQLException e) {
            throw new WarehouseDaoException(ExceptionMessage.FAILED_TO_RETRIEVE_DATA_FROM_RESULTSET_IN_WAREHOUSE_CONSTRUCTER.getMessage());
        }
    }

    public Integer getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(final Integer idNumber) {
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
