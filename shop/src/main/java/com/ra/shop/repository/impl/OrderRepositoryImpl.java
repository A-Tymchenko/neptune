package com.ra.shop.repository.impl;

import com.ra.shop.exceptions.RepositoryException;
import com.ra.shop.model.Order;
import com.ra.shop.repository.IRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class OrderRepositoryImpl implements IRepository<Order> {

    private static final Logger LOGGER = Logger.getLogger(OrderRepositoryImpl.class);

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderRepositoryImpl(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Order create(Order entity) throws RepositoryException {
        Objects.requireNonNull(entity);
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
             jdbcTemplate.update(connection -> {
                final PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO ORDERS (NUMBER, PRICE, DELIVERY_INCLUDED, DELIVERY_COST, EXECUTED) "
                                + "VALUES (?, ?, ?, ?, ?)",
                        new String[] {"ORDER_ID"});
                fillEntityWithParameters(statement, entity);
                return statement;
                }, keyHolder);
            entity.setId(keyHolder.getKey().longValue());
            return entity;
        } catch (DataAccessException e) {
            final String message = String.format("Order{id:%d} NOT CREATED!", entity.getId());
            LOGGER.error(message, e);
            throw new RepositoryException(message, e);
        }
    }

    @Override
    public Optional<Order> get(final long entityId) throws RepositoryException {
        try {
            Order found = jdbcTemplate.queryForObject(
                    "SELECT * FROM ORDERS WHERE ORDER_ID = ?",
                    new Object[] {entityId},
                    new OrderMapper());
            return Optional.of(found);
        } catch (DataAccessException e) {
            final String message = String.format("Order{id:%d} NOT FOUND!", entityId);
            LOGGER.error(message, e);
            throw new RepositoryException(message, e);
        }
    }

    @Override
    public Order update(final Order newEntity) throws RepositoryException {
        Objects.requireNonNull(newEntity);
        try {
            jdbcTemplate.update(
                    "UPDATE ORDERS SET NUMBER = ?, PRICE = ?, DELIVERY_INCLUDED = ?, "
                            + "DELIVERY_COST = ?, EXECUTED = ? WHERE ORDER_ID = ?",
                    newEntity.getNumber(), newEntity.getPrice(), newEntity.getDeliveryIncluded(),
                    newEntity.getDeliveryCost(), newEntity.getExecuted(), newEntity.getId());
            return newEntity;
        } catch (DataAccessException e) {
            final String message = String.format("Order{id:%d} NOT UPDATED!", newEntity.getId());
            LOGGER.error(message, e);
            throw new RepositoryException(message, e);
        }
    }

    @Override
    public boolean delete(final long entityId) throws RepositoryException {
        try {
            final int deletedRowsNumber = jdbcTemplate.update("DELETE FROM ORDERS WHERE ORDER_ID = ?", entityId);
            return deletedRowsNumber > 0;
        } catch (DataAccessException e) {
            final String message = String.format("Order{id:%d} NOT DELETED!", entityId);
            LOGGER.error(message, e);
            throw new RepositoryException(message, e);
        }
    }

    @Override
    public List<Order> getAll() throws RepositoryException {
        try {
            final List<Order> orders = jdbcTemplate.query("SELECT * FROM ORDERS", new OrderMapper());
            return orders;
        } catch (DataAccessException e) {
            final String message = "No orders found in database!";
            LOGGER.error(message, e);
            throw new RepositoryException(message, e);
        }
    }

    /**
     * Helper class that performs row mapping for an order entity using method mapRow().
     */
    private static final class OrderMapper implements RowMapper<Order> {
        @Override
        public Order mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
            final Long orderId = resultSet.getLong("ORDER_ID");
            final Integer number = resultSet.getInt("NUMBER");
            final Double price = resultSet.getDouble("PRICE");
            final Boolean deliveryIncluded = resultSet.getBoolean("DELIVERY_INCLUDED");
            final Integer deliveryCost = resultSet.getInt("DELIVERY_COST");
            final Boolean isExecuted = resultSet.getBoolean("EXECUTED");
            final Order order = new Order(number, price, deliveryIncluded, deliveryCost, isExecuted);
            order.setId(orderId);
            return order;
        }
    }

    /**
     * Method fills order entity with parameters using preparedStatement.
     *
     * @param statement PreparedStatement.
     * @param order     order entity that will be created.
     * @throws SQLException if any error occurs.
     */
    private void fillEntityWithParameters(final PreparedStatement statement, final Order order) throws SQLException {
        final int number = 1;
        final int price = 2;
        final int deliveryIncluded = 3;
        final int deliveryCost = 4;
        final int executed = 5;
        statement.setInt(number, order.getNumber());
        statement.setDouble(price, order.getPrice());
        statement.setBoolean(deliveryIncluded, order.getDeliveryIncluded());
        statement.setInt(deliveryCost, order.getDeliveryCost());
        statement.setBoolean(executed, order.getExecuted());
    }

}




