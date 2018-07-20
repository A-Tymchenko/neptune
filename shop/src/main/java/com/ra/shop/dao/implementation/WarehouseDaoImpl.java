package com.ra.shop.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ra.shop.connection.ConnectionFactory;
import com.ra.shop.dao.WarehouseDao;
import com.ra.shop.dao.exception.ExceptionMessage;
import com.ra.shop.dao.exception.WarehouseDaoException;
import com.ra.shop.wharehouse.Warehouse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WarehouseDaoImpl implements WarehouseDao<Warehouse> {
    private static final int NAME = 1;
    private static final int PRICE = 2;
    private static final int AMOUNT = 3;
    private static final int ID_NUMBER = 4;

    private static final String INSERT_WAREHOUSE = "INSERT INTO warehouse "
            + "(name, price, amount) "
            + " VALUES(?,?,?)";
    private static final String UPDATE_WAREHOUSE = "UPDATE warehouse "
            + "SET name = ?, price = ?, amount = ? "
            + "WHERE id = ?";

    private static final Logger LOGGER = LogManager.getLogger(WarehouseDaoImpl.class);

    private static ConnectionFactory connectionFactory;

    public WarehouseDaoImpl(final ConnectionFactory connectionFactory) {
        WarehouseDaoImpl.connectionFactory = connectionFactory;
    }

    @Override
    public Warehouse create(Warehouse warehouse) throws WarehouseDaoException {
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement insertStatement = connection.prepareStatement(INSERT_WAREHOUSE);
            final PreparedStatement getLastId = connection.prepareStatement("SELECT LAST_INSERT_ID()");
            fillInStatement(warehouse, insertStatement);
            insertStatement.executeUpdate();
            final ResultSet lastIdResultSet = getLastId.executeQuery();
            if (!lastIdResultSet.next()) {
                throw new WarehouseDaoException(ExceptionMessage.THE_WAREHOUSE_CANNOT_BE_NULL.getMessage());
            }
            warehouse = getById(lastIdResultSet.getInt(1));
        } catch (SQLException e) {
            LOGGER.error(ExceptionMessage.FAILED_TO_CREATE_NEW_WAREHOUSE.getMessage(), e);
            throw new WarehouseDaoException(ExceptionMessage.FAILED_TO_CREATE_NEW_WAREHOUSE.getMessage());
        }
        return warehouse;
    }

    @Override
    public Warehouse update(Warehouse warehouse) throws WarehouseDaoException {
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_WAREHOUSE);
            fillInStatement(warehouse, preparedStatement);
            preparedStatement.setInt(ID_NUMBER, warehouse.getIdNumber());
            preparedStatement.executeUpdate();
            warehouse = getById(warehouse.getIdNumber());
        } catch (SQLException e) {
            LOGGER.error(ExceptionMessage.FAILED_TO_UPDATE_WAREHOUSE.getMessage(), e);
            throw new WarehouseDaoException(ExceptionMessage.FAILED_TO_UPDATE_WAREHOUSE.getMessage(), e);
        }
        return warehouse;
    }

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

    @Override
    public Warehouse getById(final Integer idNumber) throws WarehouseDaoException {
        Warehouse warehouse;
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM warehouse WHERE id = ?");
            preparedStatement.setInt(1, idNumber);
            final ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }
            warehouse = new Warehouse(resultSet);
        } catch (SQLException e) {
            LOGGER.error(ExceptionMessage.FAILED_TO_GET_WAREHOUSE_BY_ID.getMessage(), e);
            throw new WarehouseDaoException(ExceptionMessage.FAILED_TO_GET_WAREHOUSE_BY_ID.getMessage() + " " + idNumber);
        }
        return warehouse;
    }

    @Override
    public List<Warehouse> getAll() throws WarehouseDaoException {
        final List<Warehouse> warehouses = new ArrayList<>();
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM warehouse");
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                warehouses.add(newWarehouse(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error(ExceptionMessage.FAILED_TO_GET_ALL_WAREHOUSES.getMessage(), e);
            throw new WarehouseDaoException(ExceptionMessage.FAILED_TO_GET_ALL_WAREHOUSES.getMessage());
        }
        return warehouses;
    }

    private void fillInStatement(final Warehouse warehouse, final PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(NAME, warehouse.getName());
        preparedStatement.setDouble(PRICE, warehouse.getPrice());
        preparedStatement.setInt(AMOUNT, warehouse.getAmount());
    }

    private Warehouse newWarehouse(final ResultSet resultSet) throws WarehouseDaoException {
        return new Warehouse(resultSet);
    }
}
