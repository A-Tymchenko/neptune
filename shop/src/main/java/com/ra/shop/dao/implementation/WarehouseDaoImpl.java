package com.ra.shop.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ra.shop.connection.ConnectionFactory;
import com.ra.shop.dao.Dao;
import com.ra.shop.dao.exception.WarehouseDaoException;
import com.ra.shop.wharehouse.Warehouse;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import static com.ra.shop.dao.exception.ExceptionMessage.*;

public class WarehouseDaoImpl implements Dao<Warehouse> {
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
    private static final String GET_Last_ID = "SELECT SCOPE_IDENTITY()";
    private static final Logger LOGGER = LogManager.getLogger(WarehouseDaoImpl.class);

    private static ConnectionFactory connectionFactory;

    public WarehouseDaoImpl(final ConnectionFactory connectionFactory) {
        WarehouseDaoImpl.connectionFactory = connectionFactory;
    }

    @Override
    public Warehouse create(Warehouse warehouse) throws WarehouseDaoException {
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement insertStatement = connection.prepareStatement(INSERT_WAREHOUSE);
            final PreparedStatement getLastId = connection.prepareStatement(GET_Last_ID);
            fillInStatement(warehouse, insertStatement);
            insertStatement.executeUpdate();
            ResultSet lastIdResultSet = getLastId.executeQuery();
            Optional<Integer> id = lastIdResultSet.next() ?
                    Optional.of(lastIdResultSet.getInt(1)) : Optional.empty();
             warehouse = getById(id);
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
            preparedStatement.setInt(ID_NUMBER, warehouse.getIdNumber());
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
            throw new WarehouseDaoException(FAILED_TO_DELETE_WAREHOUSE.getMessage());
        }
        return result;
    }

    @Override
    public Warehouse getById(Optional<Integer> idNumber) throws WarehouseDaoException {
        Warehouse warehouse;
        if (!idNumber.isPresent()) {
            throw new WarehouseDaoException(THE_WAREHOUSE_CANNOT_BE_NULL.getMessage());
        }
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID);
            preparedStatement.setInt(1, idNumber.get());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }
            warehouse = new Warehouse(resultSet);
            return warehouse;
        } catch (SQLException e) {
            throw new WarehouseDaoException(FAILED_TO_GET_WAREHOUSE_BY_ID.getMessage() + " " + idNumber.get());
        }
    }

    @Override
    public List<Warehouse> getAll() throws WarehouseDaoException {
        final List<Warehouse> warehouses = new ArrayList<>();
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_WAREHOUSE);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                final Warehouse warehouse = new Warehouse(resultSet);
                warehouses.add(warehouse);
            }
        } catch (SQLException e) {
            throw new WarehouseDaoException(FAILED_TO_GET_ALL_WAREHOUSES.getMessage());
        }
        return warehouses;
    }


    private void fillInStatement(final Warehouse warehouse, final PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(NAME, warehouse.getName());
        preparedStatement.setDouble(PRICE, warehouse.getPrice());
        preparedStatement.setInt(AMOUNT, warehouse.getAmount());
    }
}
