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
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * IRepository implementation using Spring jdbc.
 */
@Component
public class OrderRepositoryImpl implements IRepository<Order> {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(OrderRepositoryImpl.class);

    /**
     * KeyHolder instance stores primary keys.
     */
    private final transient KeyHolder keyHolder = new GeneratedKeyHolder();

    /**
     * JdbcTemplate instance.
     */
    private JdbcTemplate jdbcTemplate;

    /**
     * Constructor accepts jdbcTemplate as a parameter.
     *
     * @param jdbcTemplate jdbcTemplate
     */
    @Autowired
    public OrderRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Method adds an entity to database.
     *
     * @param entity that will be created.
     * @return Order persisted entity.
     * @throws RepositoryException can be thrown if any error occurs.
     */
    @Override
    public Order create(final Order entity) throws RepositoryException {
        Objects.requireNonNull(entity);
        final Long orderId;
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

    /**
     * Method performs search in database and returns an entity with pointed id.
     *
     * @param entityId - id of searched entity.
     * @return Order searched entity.
     * @throws RepositoryException can be thrown if any error occurs.
     */
    @Override
    public Order get(final Long entityId) throws RepositoryException {
        final Order found;
        try {
            final BeanPropertyRowMapper<Order> mapper = BeanPropertyRowMapper.newInstance(Order.class);
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

    /**
     * Method performs update operation.
     *
     * @param newEntity updated version of entity.
     * @return Order updated version of entity.
     * @throws RepositoryException can be thrown if any error occurs.
     */
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

    /**
     * Method performs delete operation.
     *
     * @param entityId of entity that will be deleted.
     * @return boolean true if entity removed successfully or false if not.
     * @throws RepositoryException can be thrown if any error occurs.
     */
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

    /**
     * Method returns list of entities that stored in database.
     *
     * @return List<Order> list of existed orders.
     * @throws RepositoryException can be thrown if any error occurs.
     */
    @Override
    public List<Order> getAll() throws RepositoryException {
        final List<Map<String, Object>> map;
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

    /**
     * Method iterate over the list of orders, set params into order instances and adds them to ArrayList and returns.
     *
     * @param map stores params for each entity.
     * @return List<Order> list of orders.
     */
    private List<Order> getListOfOrders(final List<Map<String, Object>> map) {
        final List<Order> orders = new ArrayList<>();
        for (Map<String, Object> row : map) {
            final Order order = new Order();
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




