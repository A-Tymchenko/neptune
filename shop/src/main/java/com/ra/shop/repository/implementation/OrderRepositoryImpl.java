package com.ra.shop.repository.implementation;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.ra.shop.enums.ExceptionMessage;
import com.ra.shop.exceptions.RepositoryException;
import com.ra.shop.model.Order;
import com.ra.shop.repository.IRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

/**
 * IRepository implementation using Spring jdbc.
 */
@Component
public class OrderRepositoryImpl implements IRepository<Order> {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(OrderRepositoryImpl.class);

    /**
     * KeyHolder instance stores primary keys.
     */
    private final transient KeyHolder keyHolder = new GeneratedKeyHolder();

    /**
     * JdbcTemplate instance.
     */
    private final transient JdbcTemplate jdbcTemplate;

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
        final Long orderId;
        try {
            jdbcTemplate.update(con -> {
                final PreparedStatement statement = con.prepareStatement(
                        "INSERT INTO ORDERS (NUMBER, PRICE, DELIVERY_INCLUDED, DELIVERY_COST, EXECUTED) "
                                + "VALUES (?, ?, ?, ?, ?)");
                fillEntityWithParameters(statement, entity);
                return statement;
            }, keyHolder);
            orderId = (Long) keyHolder.getKey();
        } catch (DataAccessException e) {
            LOGGER.error(ExceptionMessage.FAILED_TO_CREATE_NEW_ORDER.getMessage(), e);
            throw new RepositoryException(ExceptionMessage.FAILED_TO_CREATE_NEW_ORDER.getMessage(), e);
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
    public Order get(final long entityId) throws RepositoryException {
        final Order found;
        try {
            final BeanPropertyRowMapper<Order> mapper = BeanPropertyRowMapper.newInstance(Order.class);
            found = jdbcTemplate.queryForObject(
                    "SELECT * FROM ORDERS WHERE ORDER_ID = ?",
                    mapper,
                    entityId);
        } catch (DataAccessException e) {
            LOGGER.error(ExceptionMessage.FAILED_TO_GET_ORDER_BY_ID.getMessage(), e);
            throw new RepositoryException(ExceptionMessage.FAILED_TO_GET_ORDER_BY_ID.getMessage(), e);
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
            LOGGER.error(ExceptionMessage.FAILED_TO_UPDATE_ORDER.getMessage(), e);
            throw new RepositoryException(ExceptionMessage.FAILED_TO_UPDATE_ORDER.getMessage(), e);
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
    public boolean delete(final long entityId) throws RepositoryException {
        try {
            final int deletedRowsNumber = jdbcTemplate.update(
                    "DELETE FROM ORDERS WHERE ORDER_ID = ?",
                    entityId);
            return deletedRowsNumber > 0;
        } catch (DataAccessException e) {
            LOGGER.error(ExceptionMessage.FAILED_TO_DELETE_ORDER.getMessage(), e);
            throw new RepositoryException(ExceptionMessage.FAILED_TO_DELETE_ORDER.getMessage(), e);
        }
    }

    /**
     * Method returns list of entities that stored in database.
     *
     * @return List list of existed orders.
     * @throws RepositoryException can be thrown if any error occurs.
     */
    @Override
    public List<Order> getAll() throws RepositoryException {
        List<Order> orders;
        try {
            orders = jdbcTemplate.query("SELECT * FROM ORDERS", BeanPropertyRowMapper.newInstance(Order.class));
        } catch (DataAccessException e) {
            LOGGER.error(ExceptionMessage.FAILED_TO_GET_ALL_ORDER.getMessage(), e);
            throw new RepositoryException(ExceptionMessage.FAILED_TO_GET_ALL_ORDER.getMessage(), e);
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




