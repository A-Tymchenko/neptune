package com.ra.shop.repository.implementation;

import com.ra.shop.exceptions.RepositoryException;
import com.ra.shop.model.Order;
import com.ra.shop.repository.IRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class OrderRepositoryImpl implements IRepository<Order> {

    private static final Logger LOGGER = Logger.getLogger(OrderRepositoryImpl.class);
    private final transient KeyHolder keyHolder = new GeneratedKeyHolder();

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Order create(Order entity) throws RepositoryException {
        Objects.requireNonNull(entity);
        Long orderId;
        try {
            jdbcTemplate.update(con -> {
                final PreparedStatement statement = con.prepareStatement("INSERT INTO ORDERS (NUMBER, PRICE, DELIVERY_INCLUDED, DELIVERY_COST, EXECUTED) "
                                + "VALUES (?, ?, ?, ?, ?)");
                fillEntityWithParameters(statement, entity);
                return statement;
            }, keyHolder);
            orderId = (Long) keyHolder.getKey();
        } catch (DataAccessException e) {
            final String message = String.format("Order{id:%d} NOT CREATED!", entity.getId());
            LOGGER.error(message, e);
            throw new RepositoryException(message, e);
        }
        entity.setId(orderId);
        return entity;
    }

    @Override
    public Order get(final Long entityId) throws RepositoryException {
        Order found;
        try {
            BeanPropertyRowMapper<Order> mapper = BeanPropertyRowMapper.newInstance(Order.class);
            found = jdbcTemplate.queryForObject(
                    "SELECT * FROM ORDERS WHERE ORDER_ID = ?",
                    mapper,
                    entityId);
        } catch (DataAccessException e) {
            final String message = String.format("Order{id:%d} NOT FOUND!", entityId);
            LOGGER.error(message, e);
            throw new RepositoryException(message, e);
        }
        return found;
    }

    @Override
    public Order update(final Order newEntity) throws RepositoryException {
        Objects.requireNonNull(newEntity);
        try {
            jdbcTemplate.update(
                    "UPDATE ORDERS SET NUMBER = ?, PRICE = ?, DELIVERY_INCLUDED = ?, "
                            + "DELIVERY_COST = ?, EXECUTED = ? WHERE ORDER_ID = ?", statement -> {
                        fillEntityWithParameters(statement, newEntity);
                        final int orderId = 6;
                        statement.setLong(orderId, newEntity.getId());
                    });
            return newEntity;
        } catch (DataAccessException e) {
            final String message = String.format("Order{id:%d} NOT UPDATED!", newEntity.getId());
            LOGGER.error(message, e);
            throw new RepositoryException(message, e);
        }
    }

    @Override
    public boolean delete(final Long entityId) throws RepositoryException {
        try {
            final int deletedRowsNumber = jdbcTemplate.update(
                    "DELETE FROM ORDERS WHERE ORDER_ID = ?",
                    entityId);
            return deletedRowsNumber > 0;
        } catch (DataAccessException e) {
            final String message = String.format("Order{id:%d} NOT DELETED!", entityId);
            LOGGER.error(message, e);
            throw new RepositoryException(message, e);
        }
    }

    @Override
    public List<Order> getAll() throws RepositoryException {
        List<Map<String, Object>> map;
        try {
            map = jdbcTemplate.queryForList("SELECT * FROM ORDERS");
            getListOfOrders(map);
        } catch (DataAccessException e) {
            final String message = "No orders found in database!";
            LOGGER.error(message, e);
            throw new RepositoryException(message, e);
        }
        return getListOfOrders(map);
    }

    private List<Order> getListOfOrders(List<Map<String, Object>> map) {
        List<Order> orders = new ArrayList<>();
        for (Map<String, Object> row : map) {
            Order order = new Order();
            order.setId((Long) row.get("ORDER_ID"));
            order.setNumber((Integer) row.get("NUMBER"));
            order.setPrice((Double) row.get("PRICE"));
            order.setDeliveryIncluded((Boolean) row.get("DELIVERY_INCLUDED"));
            order.setDeliveryCost((Integer) row.get("DELIVERY_COST"));
            order.setExecuted((Boolean) row.get("EXECUTED"));
            orders.add(order);
        }
        return orders;
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




