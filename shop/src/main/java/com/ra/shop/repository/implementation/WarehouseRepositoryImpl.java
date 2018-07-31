package com.ra.shop.repository.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ra.shop.enums.ExceptionMessage;
import com.ra.shop.exceptions.RepositoryException;
import com.ra.shop.model.Warehouse;
import com.ra.shop.repository.IRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class WarehouseRepositoryImpl implements IRepository<Warehouse> {
    private static final int NAME = 1;
    private static final int PRICE = 2;
    private static final int AMOUNT = 3;
    private static final int ID_NUMBER = 4;

    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseRepositoryImpl.class);

    private final transient JdbcTemplate jdbcTemplate;
    private final transient KeyHolder keyHolder = new GeneratedKeyHolder();

    @Autowired
    public WarehouseRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Method adds new Warehouse to the Data Base.
     *
     * @param warehouse to save
     * @return new Warehouse
     */
    @Override
    public Warehouse create(final Warehouse warehouse) throws RepositoryException {
        final String insertQuery = "INSERT INTO warehouse (name, price, amount) VALUES(?,?,?)";
        jdbcTemplate.update(connection -> {
            final PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            WarehouseRepositoryImpl.this.fillInStatement(warehouse, insertStatement);
            return insertStatement;
        }, keyHolder);
        final Long warehouseId;
        try {
            warehouseId = (Long) keyHolder.getKey();
        } catch (InvalidDataAccessApiUsageException | DataRetrievalFailureException e) {
            LOGGER.error(ExceptionMessage.FAILED_TO_CREATE_NEW_WAREHOUSE.getMessage(), e);
            throw new RepositoryException(ExceptionMessage.FAILED_TO_CREATE_NEW_WAREHOUSE.getMessage());
        }
        LOGGER.info("Created new Warehouse with ID = {}", warehouseId);
        warehouse.setIdNumber(warehouseId);
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
        final String updateQuery = "UPDATE warehouse SET name = ?, price = ?, amount = ? WHERE id = ?";
        try {
            jdbcTemplate.update(updateQuery, ps -> {
                fillInStatement(warehouse, ps);
                ps.setLong(ID_NUMBER, warehouse.getIdNumber());
            });
        } catch (DataAccessException e) {
            LOGGER.error(ExceptionMessage.FAILED_TO_UPDATE_WAREHOUSE.getMessage(), e);
            throw new RepositoryException(ExceptionMessage.FAILED_TO_UPDATE_WAREHOUSE.getMessage());
        }
        LOGGER.info("Updated Warehouse with ID = {}", warehouse.getIdNumber());
        return warehouse;
    }

    /**
     * Method deletes the warehouse from a Data Base by id.
     *
     * @param entityId Long we want delete
     * @return count of deleted rows
     */
    @Override
    public boolean delete(final long entityId) throws RepositoryException {
        boolean result;
        try {
            result = jdbcTemplate.update("DELETE FROM warehouse WHERE id = ?", entityId) > 0;
        } catch (DataAccessException e) {
            LOGGER.error(ExceptionMessage.FAILED_TO_DELETE_WAREHOUSE.getMessage(), e);
            throw new RepositoryException(ExceptionMessage.FAILED_TO_DELETE_WAREHOUSE.getMessage());
        }
        LOGGER.info("Deleted Warehouse with ID = {}", entityId);
        return result;
    }

    /**
     * Method returns warehouse from a Data Base by id.
     *
     * @param idNumber Warehouse id
     * @return Optional of warehouse or empty optional
     */
    @Override
    public Optional<Warehouse> get(final long entityId) throws RepositoryException {
        Optional<Warehouse> warehouse;
        try {
            warehouse = Optional.of(
                    jdbcTemplate.queryForObject("SELECT * FROM warehouse WHERE id = ?",
                    new Object[] { entityId },
                    BeanPropertyRowMapper.newInstance(Warehouse.class)));
        } catch (DataAccessException e) {
            LOGGER.error(ExceptionMessage.FAILED_TO_GET_WAREHOUSE_BY_ID.getMessage(), e);
            throw new RepositoryException(ExceptionMessage.FAILED_TO_GET_WAREHOUSE_BY_ID.getMessage() + " " + entityId);
        }
        LOGGER.info("Got Warehouse with ID = {}", entityId);
        return warehouse;
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
