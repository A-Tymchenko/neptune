package com.ra.shop.warehouse;

import com.ra.shop.connection.ConnectionFactory;
import com.ra.shop.dao.exception.WarehouseDaoException;
import com.ra.shop.dao.implementation.WarehouseDaoImpl;
import com.ra.shop.wharehouse.Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.ra.shop.dao.exception.ExceptionMessage.*;
import static com.ra.shop.warehouse.Tools.creteWarehouse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DaoMockitoTest {

    public static final String UPDATE_WAREHOUSE = "UPDATE warehouse SET name = ?, price = ?, amount = ? WHERE id = ?";
    public static final String SELECT_WAREHOUSE_BY_ID = "SELECT * FROM warehouse WHERE id = ?";
    public static final String DELETE_WAREHOUSE_BY_ID = "DELETE FROM warehouse WHERE id = ?";
    public static final String GET_ID = "SELECT SCOPE_IDENTITY()";
    public static final String SELECT_ALL_WAREHOUSES = "SELECT * FROM warehouse";
    private static final String INSERT_WAREHOUSE = "INSERT INTO warehouse "
            + "(name, price, amount) "
            + " VALUES(?,?,?)";
    public static ConnectionFactory mockConnectionFactory;
    private Connection mockConnection;
    private PreparedStatement mockStatement;
    private ResultSet mockResultSet;
    private WarehouseDaoImpl warehouseWarehouseDaoImpl;
    private Warehouse warehouse;

    @BeforeEach
    public void init() throws SQLException {
        mockConnection = mock(Connection.class);
        mockStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        mockConnectionFactory = mock(ConnectionFactory.class);
        warehouseWarehouseDaoImpl = new WarehouseDaoImpl(mockConnectionFactory);
        when(mockConnectionFactory.getConnection()).thenReturn(mockConnection);
        warehouse = creteWarehouse();
        createMockByIdMethod();
    }

    private void createMockByIdMethod() throws SQLException {
        when(mockConnection.prepareStatement(SELECT_WAREHOUSE_BY_ID)).thenReturn(mockStatement);
        when(mockResultSet.next()).thenReturn(true);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.getInt(1)).thenReturn(warehouse.getIdNumber());
        when(mockResultSet.getInt("id")).thenReturn(warehouse.getIdNumber());
        when(mockResultSet.getString("name")).thenReturn(warehouse.getName());
        when(mockResultSet.getDouble("price")).thenReturn(warehouse.getPrice());
        when(mockResultSet.getInt("amount")).thenReturn(warehouse.getAmount());
    }

    @Test
    public void whenCreateMethodCalledThenCorrectEntityReturns() throws SQLException, WarehouseDaoException {
        when(mockConnection.prepareStatement(INSERT_WAREHOUSE)).thenReturn(mockStatement);
        when(mockConnection.prepareStatement(GET_ID)).thenReturn(mockStatement);
        Warehouse result = warehouseWarehouseDaoImpl.create(warehouse);

        assertEquals(warehouse, result);
    }

    @Test
    public void whenUpdateMethodCalledThenCorrectEntityReturnes() throws SQLException, WarehouseDaoException {
        when(mockConnection.prepareStatement(UPDATE_WAREHOUSE)).thenReturn(mockStatement);
        Warehouse result = warehouseWarehouseDaoImpl.update(warehouse);

        assertEquals(result, warehouse);
    }

    @Test
    public void whenDeleteMethodCalledAndEntityExistsThenTrueReturns() throws SQLException, WarehouseDaoException {
        when(mockConnection.prepareStatement(DELETE_WAREHOUSE_BY_ID)).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
        boolean result = warehouseWarehouseDaoImpl.delete(warehouse);

        assertEquals(true, result);
    }

    @Test
    public void whenGetAllMethodCalledThenCorrectListReturns() throws SQLException, WarehouseDaoException {
        when(mockConnection.prepareStatement(SELECT_ALL_WAREHOUSES)).thenReturn(mockStatement);
        when(mockResultSet.next()).thenReturn(true, false);
        List<Warehouse> warehouses = warehouseWarehouseDaoImpl.getAll();

        assertFalse(warehouses.isEmpty());
    }

    @Test
    public void whenGetByIdEmptyOptionalPassedThenDaoExceptionThrown() {
        Throwable exception = assertThrows(WarehouseDaoException.class, () -> {
            warehouseWarehouseDaoImpl.getById(Optional.empty());
        });

        assertEquals(exception.getMessage(), THE_WAREHOUSE_CANNOT_BE_NULL.getMessage());
    }

    @Test
    public void whenCreateMethodCalledThrowsSQLExceptionThenDaoExceptionMustBeThrown() {
        Throwable exception = assertThrows(WarehouseDaoException.class, () -> {
            when(mockConnection.prepareStatement(INSERT_WAREHOUSE)).thenThrow(new SQLException());
            warehouseWarehouseDaoImpl.create(warehouse);
        });

        assertEquals(exception.getMessage(), FAILED_TO_CREATE_NEW_WAREHOUSE.getMessage());
    }

    @Test
    public void whenUpdateMethodCalledThrowsSQLExceptionThenDaoExceptionMustBeThrown() {
        Throwable exception = assertThrows(WarehouseDaoException.class, () -> {
            when(mockConnection.prepareStatement(UPDATE_WAREHOUSE)).thenThrow(new SQLException());
            warehouseWarehouseDaoImpl.update(warehouse);
        });

        assertEquals(exception.getMessage(), FAILED_TO_UPDATE_WAREHOUSE.getMessage());
    }

    @Test
    public void whenDeleteMethodCalledThrowsSQLExceptionThenDaoExceptionMustBeThrown() {
        Throwable exception = assertThrows(WarehouseDaoException.class, () -> {
            when(mockConnection.prepareStatement(DELETE_WAREHOUSE_BY_ID)).thenThrow(new SQLException());
            warehouseWarehouseDaoImpl.delete(warehouse);
        });

        assertEquals(exception.getMessage(), FAILED_TO_DELETE_WAREHOUSE.getMessage());
    }

    @Test
    public void whenGetAllMethodCalledThrowsSQLExceptionThenDaoExceptionMustBeThrown() {
        Throwable exception = assertThrows(WarehouseDaoException.class, () -> {
            when(mockConnection.prepareStatement(SELECT_ALL_WAREHOUSES)).thenThrow(new SQLException());
            warehouseWarehouseDaoImpl.getAll();
        });

        assertEquals(exception.getMessage(), FAILED_TO_GET_ALL_WAREHOUSES.getMessage());
    }

    @Test
    public void whenGetByIdThrowsSQLExceptionThenDaoExceptionMustBeThrown() {
        Throwable exception = assertThrows(WarehouseDaoException.class, () -> {
            when(mockConnection.prepareStatement(SELECT_WAREHOUSE_BY_ID)).thenThrow(new SQLException());
            warehouseWarehouseDaoImpl.getById(Optional.of(1));
        });

        assertEquals(exception.getMessage(), FAILED_TO_GET_WAREHOUSE_BY_ID.getMessage() + " 1");
    }
}
