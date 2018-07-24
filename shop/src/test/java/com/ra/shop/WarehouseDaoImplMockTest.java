package com.ra.shop;

import com.ra.shop.connection.ConnectionFactory;
import com.ra.shop.dao.exception.DAOException;
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

    /**
     * testing create method to return the warehouse.
     *
     * @throws SQLException exception, DAOException exception
     */
    @Test
    public void whenCreateMethodCalledThenCorrectEntityReturns() throws SQLException, DAOException {
        when(mockConnection.prepareStatement(INSERT_WAREHOUSE)).thenReturn(mockStatement);
        when(mockConnection.prepareStatement(GET_ID)).thenReturn(mockStatement);
        Warehouse result = warehouseDaoImpl.create(warehouse);

        assertEquals(warehouse, result);
    }

    /**
     * testing update method to return the warehouse.
     *
     * @throws SQLException exception
     */
    @Test
    public void whenUpdateMethodCalledThenCorrectEntityReturns() throws SQLException, DAOException {
        when(mockConnection.prepareStatement(UPDATE_WAREHOUSE)).thenReturn(mockStatement);
        Warehouse result = warehouseDaoImpl.update(warehouse);

        assertEquals(result, warehouse);
    }

    /**
     * testing delete method to return true.
     *
     * @throws SQLException exception
     */
    @Test
    public void whenDeleteMethodCalledAndEntityExistsThenReturnTrue() throws SQLException, DAOException {
        when(mockConnection.prepareStatement(DELETE_WAREHOUSE_BY_ID)).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
        boolean result = warehouseDaoImpl.delete(warehouse.getIdNumber());

        assertTrue(result);
    }

    /**
     * testing delete method to return false.
     *
     * @throws SQLException exception
     */
    @Test
    public void whenDeleteMethodCalledAndEntityNotFoundThenReturnFalse() throws SQLException, DAOException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(-1);
        boolean result = warehouseDaoImpl.delete(warehouse.getIdNumber());

        assertFalse(result);
    }

    /**
     * testing getAll method to return empty List.
     *
     * @throws SQLException exception
     */
    @Test
    public void whenGetAllMethodCalledThenCorrectListReturns() throws SQLException, DAOException {
        when(mockConnection.prepareStatement(SELECT_ALL_WAREHOUSES)).thenReturn(mockStatement);
        when(mockResultSet.next()).thenReturn(true, false);
        List<Warehouse> warehouses = warehouseDaoImpl.getAll();

        assertFalse(warehouses.isEmpty());
    }

    /**
     * testing create method to throw FAILED_TO_CREATE_NEW_SHOP Exception.
     */
    @Test
    public void whenCreateMethodCalledThrowsSQLExceptionThenDaoExceptionMustBeThrown() {
        Throwable exception = assertThrows(DAOException.class, () -> {
            when(mockConnection.prepareStatement(INSERT_WAREHOUSE)).thenThrow(new SQLException());
            warehouseDaoImpl.create(warehouse);
        });

        assertEquals(exception.getMessage(), FAILED_TO_CREATE_NEW_SHOP.getMessage());
    }

    /**
     * testing create method to throw THE_SHOP_CANNOT_BE_NULL Exception.
     */
    @Test
    public void whenCreateMethodCalledWithIdNullThenDaoExceptionMustBeThrown() {
        Throwable exception = assertThrows(DAOException.class, () -> {
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
            when(mockResultSet.next()).thenReturn(false);
            warehouseDaoImpl.create(warehouse);
        });

        assertEquals(exception.getMessage(), THE_SHOP_CANNOT_BE_NULL.getMessage());
    }

    /**
     * testing update method to throw FAILED_TO_UPDATE_SHOP Exception.
     */
    @Test
    public void whenUpdateMethodCalledThrowsSQLExceptionThenDaoExceptionMustBeThrown() {
        Throwable exception = assertThrows(DAOException.class, () -> {
            when(mockConnection.prepareStatement(UPDATE_WAREHOUSE)).thenThrow(new SQLException());
            warehouseDaoImpl.update(warehouse);
        });

        assertEquals(exception.getMessage(), FAILED_TO_UPDATE_SHOP.getMessage());
    }

    /**
     * testing delete method to throw FAILED_TO_DELETE_SHOP Exception.
     */
    @Test
    public void whenDeleteMethodCalledThrowsSQLExceptionThenDaoExceptionMustBeThrown() {
        Throwable exception = assertThrows(DAOException.class, () -> {
            when(mockConnection.prepareStatement(DELETE_WAREHOUSE_BY_ID)).thenThrow(new SQLException());
            warehouseDaoImpl.delete(warehouse.getIdNumber());
        });

        assertEquals(exception.getMessage(), FAILED_TO_DELETE_SHOP.getMessage());
    }

    /**
     * testing getAll method to throw FAILED_TO_GET_ALL_SHOP Exception.
     */
    @Test
    public void whenGetAllMethodCalledThrowsSQLExceptionThenDaoExceptionMustBeThrown() {
        Throwable exception = assertThrows(DAOException.class, () -> {
            when(mockConnection.prepareStatement(SELECT_ALL_WAREHOUSES)).thenThrow(new SQLException());
            warehouseDaoImpl.getAll();
        });

        assertEquals(exception.getMessage(), FAILED_TO_GET_ALL_SHOP.getMessage());
    }

    /**
     * testing get method to throw FAILED_TO_GET_SHOP_BY_ID Exception.
     */
    @Test
    public void whenGetByIdThrowsSQLExceptionThenDaoExceptionMustBeThrown() {
        Throwable exception = assertThrows(DAOException.class, () -> {
            when(mockConnection.prepareStatement(SELECT_WAREHOUSE_BY_ID)).thenThrow(new SQLException());
            warehouseDaoImpl.get(1L);
        });

        assertEquals(exception.getMessage(), FAILED_TO_GET_SHOP_BY_ID.getMessage() + " 1");
    }

    /**
     * testing get method to return null.
     */
    @Test
    public void whenGetByIdCalledWithIdNullThenNullIsReturned() throws SQLException, DAOException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockResultSet.next()).thenReturn(false);

        assertFalse(warehouseDaoImpl.get(1L).isPresent());
    }

    /**
     * testing get method to throw DAOException.
     */
    @Test
    public void whenGetWarehouseByIdThenReturnSqlException() throws SQLException {
        when(mockConnectionFactory.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenThrow(SQLException.class);
        Throwable thrown = assertThrows(DAOException.class, () -> warehouseDaoImpl.get(1L));
        assertNotNull(thrown.getMessage());
    }

    /**
     * testing getAll method to throw DAOException.
     */
    @Test
    public void whenGetAllWarehousesThenReturnSqlException() throws SQLException {
        when(mockConnectionFactory.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(any(String.class))).thenThrow(SQLException.class);
        Throwable thrown = assertThrows(DAOException.class, () -> warehouseDaoImpl.getAll());
        assertNotNull(thrown.getMessage());
    }

    /**
     * testing create method to throw DAOException.
     */
    @Test
    public void whenAddWarehouseThenReturnSqlException() throws SQLException {
        when(mockConnectionFactory.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(any(String.class))).thenThrow(SQLException.class);
        Throwable thrown = assertThrows(DAOException.class, () -> warehouseDaoImpl.create(warehouse));
        assertNotNull(thrown.getMessage());
    }
}
