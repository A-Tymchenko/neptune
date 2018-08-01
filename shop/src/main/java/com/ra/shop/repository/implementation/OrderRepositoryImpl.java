package com.ra.shop.repository.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ra.shop.config.ConnectionFactory;
import com.ra.shop.enums.ExceptionMessage;
import com.ra.shop.exceptions.RepositoryException;
import com.ra.shop.model.Order;
import com.ra.shop.repository.IRepository;
import org.apache.log4j.Logger;

/**
 * Implementation of IRepository interface.
 */
public class OrderRepositoryImpl implements IRepository<Order> {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(OrderRepositoryImpl.class);

    /**
     * Constant represents order number.
     */
    private static final Integer NUMBER = 1;

    /**
     * Constant represents order total price.
     */
    private static final Integer PRICE = 2;

    /**
     * Constant represents a delivery option will be included or not.
     */
    private static final Integer DELIVERY_INCLUDED = 3;

    /**
     * Constant represents delivery cost if option is positive.
     */
    private static final Integer DELIVERY_COST = 4;

    /**
     * Constant represents order condition, whether it`s executed or not.
     */
    private static final Integer EXECUTED = 5;

    /**
     * Field connectionFactory.
     */
    private final transient ConnectionFactory connectionFactory;

    /**
     * Constructs new OrderRepositoryImp instance, as an argument accepts ConnectionFactory instance.
     *
     * @param connectionFactory instance.
     */
    public OrderRepositoryImpl(final ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public List<Order> getAll() throws RepositoryException {
        final List<Order> all = new ArrayList<>();
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM ORDERS")) {
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                final Order order = fillEntityWithValues(resultSet);
                all.add(order);
            }
            return all;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RepositoryException(ExceptionMessage.FAILED_TO_GET_ALL_ORDER.getMessage(), e);
        }
    }

    @Override
    public Order create(final Order entity) throws RepositoryException {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                 "INSERT INTO ORDERS (NUMBER, PRICE, DELIVERY_INCLUDED, DELIVERY_COST, EXECUTED) "
                     + "VALUES(?, ?, ?, ?, ?)",
                 Statement.RETURN_GENERATED_KEYS)) {
            setStatementValuesForCreation(statement, entity);
            statement.executeUpdate();
            final ResultSet primaryKeys = statement.getGeneratedKeys();
            if (primaryKeys.next()) {
                entity.setId(primaryKeys.getLong(1));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RepositoryException(ExceptionMessage.FAILED_TO_CREATE_NEW_ORDER.getMessage(), e);
        }
        return entity;
    }

    @Override
    public Optional<Order> get(final long entityId) throws RepositoryException {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM ORDERS WHERE ORDER_ID = ?")) {
            statement.setLong(1, entityId);
            final ResultSet res = statement.executeQuery();
            if (res.next()) {
                final Order found = fillEntityWithValues(res);
                return Optional.of(found);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RepositoryException(ExceptionMessage.FAILED_TO_GET_ORDER_BY_ID.getMessage() + " " + entityId, e);
        }
        return Optional.empty();
    }

    @Override
    public Order update(final Order newEntity) throws RepositoryException {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                 "UPDATE ORDERS SET NUMBER = ?, PRICE = ?, DELIVERY_INCLUDED = ?, "
                     + "DELIVERY_COST = ?, EXECUTED = ? WHERE ORDER_ID = ?")) {
            setStatementValuesForUpdate(statement, newEntity);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RepositoryException(ExceptionMessage.FAILED_TO_UPDATE_ORDER.getMessage(), e);
        }
        return newEntity;
    }

    @Override
    public boolean delete(final long entityId) throws RepositoryException {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM ORDERS WHERE ORDER_ID = ?")) {
            statement.setLong(1, entityId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RepositoryException(ExceptionMessage.FAILED_TO_DELETE_ORDER.getMessage(), e);
        }
    }

    /**
     * Method configuring PrepareStatement for update method.
     *
     * @param statement PreparedStatement.
     * @param newEntity - entity that will be updated.
     * @throws SQLException if any error occurs.
     */
    private void setStatementValuesForUpdate(final PreparedStatement statement, final Order newEntity) throws SQLException {
        final int orderId = 6;
        statement.setInt(NUMBER, newEntity.getNumber());
        statement.setDouble(PRICE, newEntity.getPrice());
        statement.setBoolean(DELIVERY_INCLUDED, newEntity.getDeliveryIncluded());
        statement.setInt(DELIVERY_COST, newEntity.getDeliveryCost());
        statement.setBoolean(EXECUTED, newEntity.getExecuted());
        statement.setLong(orderId, newEntity.getId());
    }

    /**
     * Method configuring PrepareStatement for create method.
     *
     * @param preparedStatement PreparedStatement.
     * @param order             new order.
     * @throws SQLException if any error occurs.
     */
    private void setStatementValuesForCreation(final PreparedStatement preparedStatement, final Order order) throws SQLException {
        preparedStatement.setInt(NUMBER, order.getNumber());
        preparedStatement.setDouble(PRICE, order.getPrice());
        preparedStatement.setBoolean(DELIVERY_INCLUDED, order.getDeliveryIncluded());
        preparedStatement.setInt(DELIVERY_COST, order.getDeliveryCost());
        preparedStatement.setBoolean(EXECUTED, order.getExecuted());
    }

    /**
     * Method returns resultSet with order params inside.
     *
     * @param resultSet resultSet.
     * @return new Order that filled with values from resultSet.
     * @throws SQLException if any error occurs.
     */
    private Order fillEntityWithValues(final ResultSet resultSet) throws SQLException {
        final Long orderId = resultSet.getLong("ORDER_ID");
        final Integer number = resultSet.getInt("NUMBER");
        final Double price = resultSet.getDouble("PRICE");
        final Boolean deliveryIncluded = resultSet.getBoolean("DELIVERY_INCLUDED");
        final Integer deliveryCost = resultSet.getInt("DELIVERY_COST");
        final Boolean executed = resultSet.getBoolean("EXECUTED");
        final Order order = new Order(number, price, deliveryIncluded, deliveryCost, executed);
        order.setId(orderId);
        return order;
    }

}