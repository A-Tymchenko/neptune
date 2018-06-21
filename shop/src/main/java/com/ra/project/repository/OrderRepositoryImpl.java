package com.ra.courses.project.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ra.courses.project.model.Order;
import com.ra.courses.project.utils.DBUtils;
import org.apache.log4j.Logger;

public class OrderRepositoryImpl implements IRepository<Order> {

    private static final Logger LOGGER = Logger.getLogger(OrderRepositoryImpl.class);

    private static final Integer ORDER_ID = 1;
    private static final Integer ORDER_NUMBER = 2;
    private static final Integer TOTAL_PRICE = 3;
    private static final Integer DELIVERY_INCLUDED = 4;
    private static final Integer DELIVERY_COST = 5;
    private static final Integer EXECUTED = 6;
    private DBUtils dbUtils;

    public OrderRepositoryImpl() {
        dbUtils = new DBUtils();
    }

    public OrderRepositoryImpl(DBUtils dbUtils) {
        this.dbUtils = dbUtils;
    }

    @Override
    public Order create(Order entity) {
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("INSERT INTO ORDERS VALUES(?, ?, ?, ?, ?, ?)")) {
            Long orderId = entity.getId();
            Integer orderNumber = entity.getNumber();
            Double totalPrice = entity.getTotalPrice();
            Boolean deliveryIncluded = entity.getDeliveryIncluded();
            Integer deliveryCost = entity.getDeliveryCost();
            Boolean executed = entity.getExecuted();
            statement.setLong(ORDER_ID, orderId);
            statement.setInt(ORDER_NUMBER, orderNumber);
            statement.setDouble(TOTAL_PRICE, totalPrice);
            statement.setBoolean(DELIVERY_INCLUDED, deliveryIncluded);
            statement.setInt(DELIVERY_COST, deliveryCost);
            statement.setBoolean(EXECUTED, executed);
            statement.executeUpdate();
            String logMessage = "Order{id : %d, number ; %d, total_price : %f, "
                    + "delivery_included : %s, delivery_cost : %d, executed : %s successfully created!";
            LOGGER.info(String.format(logMessage, orderId, orderNumber, totalPrice,
                    deliveryIncluded, deliveryCost, executed));
        } catch (SQLException e) {
            LOGGER.error("Order with id : %d doesn`t created!");
        }
        return entity;
    }

    @Override
    public Order get(Long entityId) {
        Order found = null;
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM ORDERS WHERE ID = ?")) {
            statement.setLong(ORDER_ID, entityId);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                Integer number = res.getInt(1);
                Double price = res.getDouble(2);
                Boolean deliveryIncluded = res.getBoolean(3);
                Integer deliveryCost = res.getInt(4);
                Boolean executed = res.getBoolean(5);
                found = new Order(entityId, number, price, deliveryIncluded, deliveryCost, executed);
            }
            LOGGER.info(String.format("Order with id : %d succesfully found!", entityId));
        } catch (SQLException e) {
            LOGGER.error(String.format("Order with id : %d not found!", entityId));
        }
        return found;
    }

    @Override
    public Order update(Long entityId, Order newEntity) {
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE ORDERS SET NUMBER = ?, PRICE = ?, DELIVERY_INCLUDED = ?, DELIVERY_COST = ?, "
                             + "EXECUTED = ? WHERE ID = ?")) {
            statement.setInt(1, newEntity.getNumber());
            statement.setDouble(2, newEntity.getTotalPrice());
            statement.setBoolean(3, newEntity.getDeliveryIncluded());
            statement.setInt(4, newEntity.getDeliveryCost());
            statement.setBoolean(5, newEntity.getExecuted());
            statement.setLong(6, entityId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newEntity;
    }

    @Override
    public Boolean delete(Long entityId) {
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM ORDERS WHERE ID = ?")) {
            statement.setLong(1, entityId);
            int deleted = statement.executeUpdate();
            if (deleted > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
