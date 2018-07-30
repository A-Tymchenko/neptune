package com.ra.shop.repository.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ra.shop.config.ConnectionFactory;
import com.ra.shop.enums.ExceptionMessage;
import com.ra.shop.exceptions.RepositoryException;
import com.ra.shop.model.Warehouse;
import com.ra.shop.repository.IRepository;
import org.apache.log4j.Logger;

public class WarehouseRepositoryImpl implements IRepository<Warehouse> {
    private static final int NAME = 1;
    private static final int PRICE = 2;
    private static final int AMOUNT = 3;
    private static final int ID_NUMBER = 4;

    private static final Logger LOGGER = Logger.getLogger(WarehouseRepositoryImpl.class);

    private static ConnectionFactory connectionFactory;

    public WarehouseRepositoryImpl(final ConnectionFactory connectionFactory) {
        WarehouseRepositoryImpl.connectionFactory = connectionFactory;
    }

    /**
     * Method adds new Warehouse to the Data Base.
     *
     * @param warehouse to save
     * @return new Warehouse
     */
    @Override
    public Warehouse create(Warehouse warehouse) throws RepositoryException {
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO warehouse "
                + "(name, price, amount) "
                + " VALUES(?,?,?)");
            final PreparedStatement getLastId = connection.prepareStatement("SELECT LAST_INSERT_ID()");
            fillInStatement(warehouse, insertStatement);
            insertStatement.executeUpdate();
            final ResultSet lastIdResultSet = getLastId.executeQuery();
            if (!lastIdResultSet.next()) {
                throw new RepositoryException(ExceptionMessage.THE_WAREHOUSE_CANNOT_BE_NULL.getMessage());
            }
            warehouse = get(lastIdResultSet.getLong(1)).get();
        } catch (SQLException e) {
            LOGGER.error(ExceptionMessage.FAILED_TO_CREATE_NEW_WAREHOUSE.getMessage(), e);
            throw new RepositoryException(ExceptionMessage.FAILED_TO_CREATE_NEW_WAREHOUSE.getMessage());
        }
        return warehouse;
    }

    /**
     * Update Warehouse to Data Base.
     *
     * @param warehouse to update
     * @return new Warehouse
     */
    @Override
    public Warehouse update(final Warehouse warehouse) throws RepositoryException {
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement("UPDATE warehouse "
                + "SET name = ?, price = ?, amount = ? "
                + "WHERE id = ?");
            fillInStatement(warehouse, preparedStatement);
            preparedStatement.setLong(ID_NUMBER, warehouse.getIdNumber());
            preparedStatement.executeUpdate();
            get(warehouse.getIdNumber());
        } catch (SQLException e) {
            LOGGER.error(ExceptionMessage.FAILED_TO_UPDATE_WAREHOUSE.getMessage(), e);
            throw new RepositoryException(ExceptionMessage.FAILED_TO_UPDATE_WAREHOUSE.getMessage(), e);
        }
        return warehouse;
    }

    /**
     * Method deletes the warehouse from a Data Base by id.
     *
     * @param entityId Long we want delete
     * @return count of deleted rows
     */
    @Override
    public boolean delete(final Long entityId) throws RepositoryException {
        boolean result;
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM warehouse WHERE id = ?");
            preparedStatement.setLong(1, entityId);
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(ExceptionMessage.FAILED_TO_DELETE_WAREHOUSE.getMessage(), e);
            throw new RepositoryException(ExceptionMessage.FAILED_TO_DELETE_WAREHOUSE.getMessage());
        }
        return result;
    }

    /**
     * Method returns warehouse from a Data Base by id.
     *
     * @param idNumber Warehouse id
     * @return Optional of warehouse or empty optional
     */
    @Override
    public Optional<Warehouse> get(final Long idNumber) throws RepositoryException {
        Optional<Warehouse> warehouse = Optional.empty();
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM warehouse WHERE id = ?");
            preparedStatement.setLong(1, idNumber);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return warehouse;
            }
            warehouse = Optional.of(getWarehouseFromResultSet(resultSet));
            return warehouse;
        } catch (SQLException e) {
            LOGGER.error(ExceptionMessage.FAILED_TO_GET_WAREHOUSE_BY_ID.getMessage(), e);
            throw new RepositoryException(ExceptionMessage.FAILED_TO_GET_WAREHOUSE_BY_ID.getMessage() + " " + idNumber);
        }
    }

    /**
     * Method returns List of Warehouses from a Data Base by id.
     *
     * @return list of warehouses or empty list
     */
    @Override
    public List<Warehouse> getAll() throws RepositoryException {
        final List<Warehouse> warehouses = new ArrayList<>();
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM warehouse");
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                warehouses.add(getWarehouseFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error(ExceptionMessage.FAILED_TO_GET_ALL_WAREHOUSE.getMessage(), e);
            throw new RepositoryException(ExceptionMessage.FAILED_TO_GET_ALL_WAREHOUSE.getMessage());
        }
        return warehouses;
    }

    /**
     * Method fills in preparedStatement from the warehouse.
     *
     * @param preparedStatement to save
     * @param warehouse         to save
     */
    private void fillInStatement(final Warehouse warehouse, final PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(NAME, warehouse.getName());
        preparedStatement.setDouble(PRICE, warehouse.getPrice());
        preparedStatement.setInt(AMOUNT, warehouse.getAmount());
    }

    /**
     * Method retrieves a warehouse from the preparedStatement.
     *
     * @param resultSet received from a Data Base
     * @return warehouse with filled fields
     */
    private Warehouse getWarehouseFromResultSet(final ResultSet resultSet) throws SQLException {
        final Warehouse warehouse = new Warehouse();
        warehouse.setIdNumber(resultSet.getLong("id"));
        warehouse.setName(resultSet.getString("name"));
        warehouse.setPrice(resultSet.getDouble("price"));
        warehouse.setAmount(resultSet.getInt("amount"));
        return warehouse;
    }
}
