package com.ra.shop.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ra.shop.connection.ConnectionFactory;
import com.ra.shop.dao.ShopDao;
import com.ra.shop.dao.exception.ExceptionMessage;
import com.ra.shop.dao.exception.WarehouseDaoException;
import com.ra.shop.entity.Warehouse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WarehouseDaoImpl implements ShopDao<Warehouse> {
    private static final int NAME = 1;
    private static final int PRICE = 2;
    private static final int AMOUNT = 3;
    private static final int ID_NUMBER = 4;

    private static final Logger LOGGER = LogManager.getLogger(WarehouseDaoImpl.class);

    private static ConnectionFactory connectionFactory;

    public WarehouseDaoImpl(final ConnectionFactory connectionFactory) {
        WarehouseDaoImpl.connectionFactory = connectionFactory;
    }

    /**
     *  Method adds new Warehouse to the Data Base.
     *
     * @param warehouse to save
     * @return new Warehouse
     */
    @Override
    public Warehouse create(Warehouse warehouse) throws WarehouseDaoException {
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO warehouse "
                    + "(name, price, amount) "
                    + " VALUES(?,?,?)");
            final PreparedStatement getLastId = connection.prepareStatement("SELECT LAST_INSERT_ID()");
            fillInStatement(warehouse, insertStatement);
            insertStatement.executeUpdate();
            final ResultSet lastIdResultSet = getLastId.executeQuery();
            if (!lastIdResultSet.next()) {
                throw new WarehouseDaoException(ExceptionMessage.THE_WAREHOUSE_CANNOT_BE_NULL.getMessage());
            }
            warehouse = getById(lastIdResultSet.getLong(1));
        } catch (SQLException e) {
            LOGGER.error(ExceptionMessage.FAILED_TO_CREATE_NEW_WAREHOUSE.getMessage(), e);
            throw new WarehouseDaoException(ExceptionMessage.FAILED_TO_CREATE_NEW_WAREHOUSE.getMessage());
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
    public Warehouse update(final Warehouse warehouse) throws WarehouseDaoException {
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement("UPDATE warehouse "
                    + "SET name = ?, price = ?, amount = ? "
                    + "WHERE id = ?");
            fillInStatement(warehouse, preparedStatement);
            preparedStatement.setLong(ID_NUMBER, warehouse.getIdNumber());
            preparedStatement.executeUpdate();
            getById(warehouse.getIdNumber());
        } catch (SQLException e) {
            LOGGER.error(ExceptionMessage.FAILED_TO_UPDATE_WAREHOUSE.getMessage(), e);
            throw new WarehouseDaoException(ExceptionMessage.FAILED_TO_UPDATE_WAREHOUSE.getMessage(), e);
        }
        return warehouse;
    }

    /**
     * Method deletes the warehouse from a Data Base by id.
     *
     * @param warehouse Warehouse we want delete
     * @return count of deleted rows
     */
    @Override
    public boolean delete(final Warehouse warehouse) throws WarehouseDaoException {
        boolean result;
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM warehouse WHERE id = ?");
            preparedStatement.setLong(1, warehouse.getIdNumber());
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(ExceptionMessage.FAILED_TO_DELETE_WAREHOUSE.getMessage(), e);
            throw new WarehouseDaoException(ExceptionMessage.FAILED_TO_DELETE_WAREHOUSE.getMessage());
        }
        return result;
    }

    /**
     * Method returns warehouse from a Data Base by id.
     *
     * @param idNumber Warehouse id
     * @return warehouse
     */
    @Override
    public Warehouse getById(final Long idNumber) throws WarehouseDaoException {
        Warehouse warehouse;
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM warehouse WHERE id = ?");
            preparedStatement.setLong(1, idNumber);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            warehouse = getWarehouseFromResultSet(resultSet);
            return warehouse;
        } catch (SQLException e) {
            LOGGER.error(ExceptionMessage.FAILED_TO_GET_WAREHOUSE_BY_ID.getMessage(), e);
            throw new WarehouseDaoException(ExceptionMessage.FAILED_TO_GET_WAREHOUSE_BY_ID.getMessage() + " " + idNumber);
        }
    }

    /**
     * Method returns List of Warehouses from a Data Base by id.
     *
     * @return list of warehouses or empty list
     */
    @Override
    public List<Warehouse> getAll() throws WarehouseDaoException {
        final List<Warehouse> warehouses = new ArrayList<>();
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM warehouse");
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                warehouses.add(getWarehouseFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error(ExceptionMessage.FAILED_TO_GET_ALL_WAREHOUSES.getMessage(), e);
            throw new WarehouseDaoException(ExceptionMessage.FAILED_TO_GET_ALL_WAREHOUSES.getMessage());
        }
        return warehouses;
    }

    /**
     * Method fills in preparedStatement from the warehouse.
     *
     * @param preparedStatement to save
     * @param warehouse to save
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
