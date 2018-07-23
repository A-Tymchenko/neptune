package com.ra.shop;

import com.ra.shop.connection.ConnectionFactory;
import com.ra.shop.dao.exception.WarehouseDaoException;
import com.ra.shop.dao.implementation.WarehouseDaoImpl;
import com.ra.shop.entity.Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.ra.shop.dao.exception.ExceptionMessage.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WarehouseDaoImplMockTest {

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
    private WarehouseDaoImpl warehouseDaoImpl;
    private Warehouse warehouse;

    @BeforeEach
    public void init() throws SQLException {
        mockConnection = mock(Connection.class);
        mockStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        mockConnectionFactory = mock(ConnectionFactory.class);
        warehouseDaoImpl = new WarehouseDaoImpl(mockConnectionFactory);
        when(mockConnectionFactory.getConnection()).thenReturn(mockConnection);
        warehouse = new Warehouse("Lola", Double.MIN_VALUE, 2);
        warehouse.setIdNumber(1L);
        createMockByIdMethod();
    }

    private void createMockByIdMethod() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockResultSet.next()).thenReturn(true);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.getLong(1)).thenReturn(warehouse.getIdNumber());
        when(mockResultSet.getLong("id")).thenReturn(warehouse.getIdNumber());
        when(mockResultSet.getString("name")).thenReturn(warehouse.getName());
        when(mockResultSet.getDouble("price")).thenReturn(warehouse.getPrice());
        when(mockResultSet.getInt("amount")).thenReturn(warehouse.getAmount());
    }

    @Test
    public void whenCreateMethodCalledThenCorrectEntityReturns() throws SQLException, WarehouseDaoException {
        when(mockConnection.prepareStatement(INSERT_WAREHOUSE)).thenReturn(mockStatement);
        when(mockConnection.prepareStatement(GET_ID)).thenReturn(mockStatement);
        Warehouse result = warehouseDaoImpl.create(warehouse);

        assertEquals(warehouse, result);
    }

    @Test
    public void whenUpdateMethodCalledThenCorrectEntityReturns() throws SQLException, WarehouseDaoException {
        when(mockConnection.prepareStatement(UPDATE_WAREHOUSE)).thenReturn(mockStatement);
        Warehouse result = warehouseDaoImpl.update(warehouse);

        assertEquals(result, warehouse);
    }

    @Test
    public void whenDeleteMethodCalledAndEntityExistsThenTrueReturns() throws SQLException, WarehouseDaoException {
        when(mockConnection.prepareStatement(DELETE_WAREHOUSE_BY_ID)).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
        boolean result = warehouseDaoImpl.delete(warehouse);

        assertTrue(result);
    }

    @Test
    public void whenGetAllMethodCalledThenCorrectListReturns() throws SQLException, WarehouseDaoException {
        when(mockConnection.prepareStatement(SELECT_ALL_WAREHOUSES)).thenReturn(mockStatement);
        when(mockResultSet.next()).thenReturn(true, false);
        List<Warehouse> warehouses = warehouseDaoImpl.getAll();

        assertFalse(warehouses.isEmpty());
    }

    @Test
    public void whenCreateMethodCalledThrowsSQLExceptionThenDaoExceptionMustBeThrown() {
        Throwable exception = assertThrows(WarehouseDaoException.class, () -> {
            when(mockConnection.prepareStatement(INSERT_WAREHOUSE)).thenThrow(new SQLException());
            warehouseDaoImpl.create(warehouse);
        });

        assertEquals(exception.getMessage(), FAILED_TO_CREATE_NEW_WAREHOUSE.getMessage());
    }

    @Test
    public void whenCreateMethodCalledWithIdNullThenDaoExceptionMustBeThrown() {
        Throwable exception = assertThrows(WarehouseDaoException.class, () -> {
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
            when(mockResultSet.next()).thenReturn(false);
            warehouseDaoImpl.create(warehouse);
        });

        assertEquals(exception.getMessage(), THE_WAREHOUSE_CANNOT_BE_NULL.getMessage());
    }

    @Test
    public void whenUpdateMethodCalledThrowsSQLExceptionThenDaoExceptionMustBeThrown() {
        Throwable exception = assertThrows(WarehouseDaoException.class, () -> {
            when(mockConnection.prepareStatement(UPDATE_WAREHOUSE)).thenThrow(new SQLException());
            warehouseDaoImpl.update(warehouse);
        });

        assertEquals(exception.getMessage(), FAILED_TO_UPDATE_WAREHOUSE.getMessage());
    }

    @Test
    public void whenDeleteMethodCalledThrowsSQLExceptionThenDaoExceptionMustBeThrown() {
        Throwable exception = assertThrows(WarehouseDaoException.class, () -> {
            when(mockConnection.prepareStatement(DELETE_WAREHOUSE_BY_ID)).thenThrow(new SQLException());
            warehouseDaoImpl.delete(warehouse);
        });

        assertEquals(exception.getMessage(), FAILED_TO_DELETE_WAREHOUSE.getMessage());
    }

    @Test
    public void whenGetAllMethodCalledThrowsSQLExceptionThenDaoExceptionMustBeThrown() {
        Throwable exception = assertThrows(WarehouseDaoException.class, () -> {
            when(mockConnection.prepareStatement(SELECT_ALL_WAREHOUSES)).thenThrow(new SQLException());
            warehouseDaoImpl.getAll();
        });

        assertEquals(exception.getMessage(), FAILED_TO_GET_ALL_WAREHOUSES.getMessage());
    }

    @Test
    public void whenGetByIdThrowsSQLExceptionThenDaoExceptionMustBeThrown() {
        Throwable exception = assertThrows(WarehouseDaoException.class, () -> {
            when(mockConnection.prepareStatement(SELECT_WAREHOUSE_BY_ID)).thenThrow(new SQLException());
            warehouseDaoImpl.getById(1L);
        });

        assertEquals(exception.getMessage(), FAILED_TO_GET_WAREHOUSE_BY_ID.getMessage() + " 1");
    }

    @Test
    public void whenGetByIdCalledWithIdNullThenNullIsReturned() throws SQLException, WarehouseDaoException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockResultSet.next()).thenReturn(false);
        Warehouse result = warehouseDaoImpl.getById(1L);

        assertNull(result);
    }

    @Test
    public void whenGetWarehouseByIdThenReturnSqlException() throws SQLException {
        when(mockConnectionFactory.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(any(String.class))).thenThrow(SQLException.class);
        Throwable thrown = assertThrows(WarehouseDaoException.class, () -> warehouseDaoImpl.getById(1L));
        assertNotNull(thrown.getMessage());
    }

    @Test
    public void whenGetAllWarehousesThenReturnSqlException() throws SQLException {
        when(mockConnectionFactory.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(any(String.class))).thenThrow(SQLException.class);
        Throwable thrown = assertThrows(WarehouseDaoException.class, () -> warehouseDaoImpl.getAll());
        assertNotNull(thrown.getMessage());
    }

    @Test
    public void whenAddWarehouseThenReturnSqlException() throws SQLException {
        when(mockConnectionFactory.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(any(String.class))).thenThrow(SQLException.class);
        Throwable thrown = assertThrows(WarehouseDaoException.class, () -> warehouseDaoImpl.create(warehouse));
        assertNotNull(thrown.getMessage());
    }
}
