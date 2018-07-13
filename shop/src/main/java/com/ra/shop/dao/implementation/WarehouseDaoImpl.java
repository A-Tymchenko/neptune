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
import com.ra.shop.dao.WarehouseDao;
import com.ra.shop.dao.exception.ExceptionMessage;
import com.ra.shop.dao.exception.WarehouseDaoException;
import com.ra.shop.wharehouse.Warehouse;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import static com.ra.shop.dao.exception.ExceptionMessage.*;

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
    private static final String GET_BY_ID = "SELECT * FROM warehouse WHERE id = ?";
    public static final String DELETE_WAREHOUSE_BY_ID = "DELETE FROM warehouse WHERE id = ?";
    private static final String GET_ALL_WAREHOUSE = "SELECT * FROM warehouse";
    private static final String GET_ID = "SELECT SCOPE_IDENTITY()";
    private static final Logger LOGGER = LogManager.getLogger(WarehouseDaoImpl.class);

    private static ConnectionFactory connectionFactory;

    public WarehouseDaoImpl(final ConnectionFactory connectionFactory) {
        WarehouseDaoImpl.connectionFactory = connectionFactory;
    }

    @Override
    public Warehouse create(Warehouse warehouse) throws WarehouseDaoException {
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(INSERT_WAREHOUSE);
            fillInStatement(warehouse, preparedStatement);
            preparedStatement.executeUpdate();
            final ResultSet getWarehouseId = connection.prepareStatement(GET_ID).executeQuery();
            Integer warehouseId = null;
            if (getWarehouseId.next()) {
                warehouseId = getWarehouseId.getInt(1);
                System.out.println("warehouseId "+warehouseId);
            }
            getById(Optional.of(warehouse.getIdNumber()));
        } catch (SQLException e) {
            throw new WarehouseDaoException(FAILED_TO_CREATE_NEW_WAREHOUSE.getMessage());
        }
        return warehouse;
    }

    @Override
    public Warehouse update(Warehouse warehouse) throws WarehouseDaoException {
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_WAREHOUSE);
            fillInStatement(warehouse, preparedStatement);
            preparedStatement.executeUpdate();
            getById(Optional.of(warehouse.getIdNumber()));
        } catch (SQLException e) {
            throw new WarehouseDaoException(FAILED_TO_UPDATE_WAREHOUSE.getMessage(), e);
        }
        return warehouse;
    }

    @Override
    public boolean delete(final Warehouse warehouse) throws WarehouseDaoException {
        boolean result;
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(DELETE_WAREHOUSE_BY_ID);
            preparedStatement.setLong(1, warehouse.getIdNumber());
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
//            LOGGER.error(FAILED_TO_DELETE_WAREHOUSE.getMessage(), e);
            throw new WarehouseDaoException(FAILED_TO_DELETE_WAREHOUSE.getMessage());
        }
        return result;
    }

    @Override
    public Warehouse getById(Optional<Integer> idNumber) throws WarehouseDaoException {
        Warehouse warehouse = null;
        if (!idNumber.isPresent()) {
            throw new WarehouseDaoException(THE_WAREHOUSE_CANNOT_BE_NULL.getMessage());
        }
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID);
            preparedStatement.setInt(1, idNumber.get());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                warehouse = new Warehouse();
                fillInWarehouse(warehouse, resultSet);
                return warehouse;
            }
        } catch (SQLException e) {
            throw new WarehouseDaoException(FAILED_TO_GET_WAREHOUSE_BY_ID.getMessage() + " " + idNumber.get());
        }
        return warehouse;
    }

    @Override
    public List<Warehouse> getAll() throws WarehouseDaoException {
        final List<Warehouse> warehouses = new ArrayList<>();
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_WAREHOUSE);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                createWarehouse(warehouses, resultSet);
            }
        } catch (SQLException e) {
            final String message = FAILED_TO_GET_ALL_WAREHOUSES.getMessage();
            LOGGER.error(message, e);
            throw new WarehouseDaoException(message, e);
        }
        return warehouses;
    }

    private void createWarehouse(final List<Warehouse> warehouses, final ResultSet resultSet) throws SQLException {
        final Warehouse warehouse = new Warehouse();
        fillInWarehouse(warehouse, resultSet);
        warehouses.add(warehouse);
    }

    private void fillInWarehouse(final Warehouse warehouse, final ResultSet resultSet) throws SQLException {
        warehouse.setIdNumber(resultSet.getInt("id"));
        warehouse.setName(resultSet.getString("name"));
        warehouse.setPrice(resultSet.getDouble("price"));
        warehouse.setAmount(resultSet.getInt("amount"));
    }

    private void fillInStatement(final Warehouse warehouse, final PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(NAME, warehouse.getName());
        preparedStatement.setDouble(PRICE, warehouse.getPrice());
        preparedStatement.setInt(AMOUNT, warehouse.getAmount());
        preparedStatement.setInt(ID_NUMBER, warehouse.getIdNumber());
    }
}
