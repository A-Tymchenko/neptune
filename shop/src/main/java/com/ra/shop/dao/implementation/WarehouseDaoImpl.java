package com.ra.shop.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ra.shop.connection.ConnectionFactory;
import com.ra.shop.dao.WharehouseDao;
import com.ra.shop.dao.exception.ExceptionMessage;
import com.ra.shop.dao.exception.WarehouseDaoException;
import com.ra.shop.wharehouse.Warehouse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WarehouseDaoImpl implements WharehouseDao<Warehouse> {

    private static final int ID_NUMBER = 1;
    private static final int NAME = 2;
    private static final int PRICE = 3;
    private static final int AMOUNT = 4;

    private static final String INSERT_WARHOUSE = "INSERT INTO warehouse "
            + "(name, price, amount) "
            + " VALUES(?,?,?)";
    private static final String UPDATE_WARHOUSE = "UPDATE warehouse "
            + "SET name = ?, price = ?, amount = ?"
            + "WHERE id = ?";
    private static final String GET_BY_ID = "SELECT * FROM warehouse WHERE id = ?";
    private static final String DELETE_WARHOUSE = "DELETE FROM warehouse WHERE id = ?";
    private static final String GET_ALL_WARHOUSE = "SELECT * FROM warehouse";
    private static final String GET_ID = "SELECT SCOPE_IDENTITY()";
    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseDaoImpl.class);

    private static ConnectionFactory connectionFactory;

    @Override
    public Warehouse create(Warehouse warehouse) throws WarehouseDaoException {
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(INSERT_WARHOUSE);
            fillInStatement(warehouse, preparedStatement);
            preparedStatement.executeUpdate();
            final ResultSet getWarhousId = connection.prepareStatement(GET_ID).executeQuery();
            Long warhousId = null;
            if (getWarhousId.next()) {
                warhousId = getWarhousId.getLong(1);
            }
            warehouse = getById(warhousId).get();
        } catch (SQLException e) {
            LOGGER.debug("Warhouse with id was created {}", warehouse.getIdNumber());
        }
        return null;
    }

    @Override
    public Warehouse update(Warehouse warehouse) throws WarehouseDaoException {
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_WARHOUSE);
            fillInStatement(warehouse, preparedStatement);
            preparedStatement.executeUpdate();
            warehouse = getById(warehouse.getIdNumber()).get();
        } catch (SQLException e) {
            final String errorMessage = ExceptionMessage.FAILED_TO_UPDATE_WAREHOUSE.getMessage() + warehouse.getIdNumber();
            LOGGER.error(errorMessage, e);
            throw new WarehouseDaoException(errorMessage, e);
        }
        LOGGER.debug("Flight with id was updated {}", warehouse.getIdNumber());
        return warehouse;
    }

    @Override
    public boolean delete(final Warehouse warehouse) throws WarehouseDaoException {
        boolean result;
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(DELETE_WARHOUSE);
            preparedStatement.setLong(1, warehouse.getIdNumber());
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            final String errorMessage = ExceptionMessage.FAILED_TO_DELETE_WAREHOUSE.getMessage() + warehouse.getIdNumber();
            LOGGER.error(errorMessage, e);
            throw new WarehouseDaoException(errorMessage, e);
        }
        return result;
    }

    @Override
    public Optional<Warehouse> getById(final Long idNumber) throws WarehouseDaoException {
        if (idNumber == null) {
            throw new WarehouseDaoException(ExceptionMessage.THE_WAREHOUSE_CANNOT_BE_NULL.getMessage());
        }
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID);
            preparedStatement.setLong(ID_NUMBER, idNumber);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                final Warehouse warehouse = new Warehouse();
                final ResultSetMetaData data = resultSet.getMetaData();
                fillInWarehouse(warehouse, data);
                return Optional.of(warehouse);
            }
        } catch (SQLException e) {
            LOGGER.error(ExceptionMessage.FAILED_TO_CREATE_NEW_WAREHOUSE.toString(), e);
            throw new WarehouseDaoException(ExceptionMessage.FAILED_TO_CREATE_NEW_WAREHOUSE.getMessage(), e);
        }
        return Optional.empty();
    }

    private void fillInWarehouse(final Warehouse warehouse, final ResultSetMetaData data) throws SQLException {
        warehouse.setIdNumber(Long.valueOf(data.getColumnName(ID_NUMBER)));
        warehouse.setName(data.getColumnName(NAME));
        warehouse.setPrice(Double.valueOf(data.getColumnName(PRICE)));
        warehouse.setAmount(Integer.valueOf(data.getColumnName(AMOUNT)));
    }

    @Override
    public List<Warehouse> getAll() throws WarehouseDaoException {
        final List<Warehouse> warehouses = new ArrayList<>();
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_WARHOUSE);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                createWarehouse(warehouses, resultSet);
            }
        } catch (SQLException e) {
            final String message = ExceptionMessage.FAILED_TO_GET_ALL_WAREHOUSES.getMessage();
            LOGGER.error(message, e);
            throw new WarehouseDaoException(message, e);
        }
        return warehouses;
    }

    private void createWarehouse(final List<Warehouse> warehouses, final ResultSet resultSet) throws SQLException {
        final ResultSetMetaData data = resultSet.getMetaData();
        final Warehouse warehouse = new Warehouse();
        fillInWarehouse(warehouse, data);
        warehouses.add(warehouse);
    }

    private void fillInStatement(final Warehouse warehouse, final PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(NAME, warehouse.getName());
        preparedStatement.setDouble(PRICE, warehouse.getPrice());
        preparedStatement.setInt(AMOUNT, warehouse.getAmount());
    }
}
