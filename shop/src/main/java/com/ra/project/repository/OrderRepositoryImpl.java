package com.ra.project.repository;

import com.ra.project.config.ConnectionFactory;
import com.ra.project.model.Order;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of IRepository interface.
 */
public class OrderRepositoryImpl implements IRepository<Order> {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(OrderRepositoryImpl.class);

    /**
     * Constant represents order identifier.
     */
    private static final Integer ID = 1;

    /**
     * Constant represents order number.
     */
    private static final Integer NUMBER = 2;

    /**
     * Constant represents order total price.
     */
    private static final Integer PRICE = 3;

    /**
     * Constant represents a delivery option will be included or not.
     */
    private static final Integer DELIVERY_INCLUDED = 4;

    /**
     * Constant represents delivery cost if option is positive.
     */
    private static final Integer DELIVERY_COST = 5;

    /**
     * Constant represents order condition, whether it`s executed or not.
     */
    private static final Integer EXECUTED = 6;

    /**
     * Field connectionFactory.
     */
    private ConnectionFactory connectionFactory;

    /**
     * Constructs new OrderRepositoryImp instance, as an argument accepts ConnectionFactory instance.
     *
     * @param connectionFactory instance.
     */
    public OrderRepositoryImpl(final ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> getAll() {
        List<Order> all = new ArrayList<>();
        try (Connection connection = connectionFactory.getConnection();
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM ORDERS")) {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Order order = getValuesFromResultSet(resultSet);
                all.add(order);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return all;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Integer create(Order entity) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO ORDERS VALUES(?, ?, ?, ?, ?, ?)")) {
            setStatementValuesForCreation(statement, entity);
            return statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return 0;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Optional<Order> get(Long entityId) {
        Order found;
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM ORDERS WHERE ID = ?")) {
            Integer param = 1;
            statement.setLong(param, entityId);
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                found = getValuesFromResultSet(res);
                return Optional.of(found);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Integer update(Order newEntity) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE ORDERS SET NUMBER = ?, PRICE = ?, DELIVERY_INCLUDED = ?, "
                             + "DELIVERY_COST = ?, EXECUTED = ? WHERE ID = ?")) {
            setStatementValuesForUpdate(statement, newEntity);
            return statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return 0;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Integer delete(Long entityId) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM ORDERS WHERE ID = ?")) {
            statement.setLong(1, entityId);
            return statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return 0;
    }

    /**
     * Method configuring PrepareStatement for update method.
     *
     * @param statement PreparedStatement.
     * @param newEntity - entity that will be updated.
     * @throws SQLException if any error occurs.
     */
    private void setStatementValuesForUpdate(PreparedStatement statement, Order newEntity) throws SQLException {
        statement.setInt(1, newEntity.getNumber());
        statement.setDouble(2, newEntity.getPrice());
        statement.setBoolean(3, newEntity.getDeliveryIncluded());
        statement.setInt(4, newEntity.getDeliveryCost());
        statement.setBoolean(5, newEntity.getExecuted());
        statement.setLong(6, newEntity.getId());
    }

    /**
     * Method configuring PrepareStatement for create method.
     * @param preparedStatement PreparedStatement.
     * @param order new order.
     * @throws SQLException if any error occurs.
     */
    private void setStatementValuesForCreation(PreparedStatement preparedStatement, Order order) throws SQLException {
        preparedStatement.setLong(ID, order.getId());
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
    private Order getValuesFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("ID");
        Integer number = resultSet.getInt("NUMBER");
        Double price = resultSet.getDouble("PRICE");
        Boolean deliveryIncluded = resultSet.getBoolean("DELIVERY_INCLUDED");
        Integer deliveryCost = resultSet.getInt("DELIVERY_COST");
        Boolean executed = resultSet.getBoolean("EXECUTED");
        return new Order(id, number, price, deliveryIncluded, deliveryCost, executed);
    }

}
