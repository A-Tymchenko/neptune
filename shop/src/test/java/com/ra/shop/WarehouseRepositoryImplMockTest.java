package com.ra.shop;

import com.ra.shop.config.ConnectionFactory;
import com.ra.shop.exceptions.RepositoryException;
import com.ra.shop.repository.implementation.WarehouseRepositoryImpl;
import com.ra.shop.model.Warehouse;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.*;
import java.util.List;

import static com.ra.shop.enums.ExceptionMessage.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WarehouseRepositoryImplMockTest {

    private static final String UPDATE_WAREHOUSE = "UPDATE warehouse SET name = ?, price = ?, amount = ? WHERE id = ?";
    private static final String SELECT_WAREHOUSE_BY_ID = "SELECT * FROM warehouse WHERE id = ?";
    private static final String DELETE_WAREHOUSE_BY_ID = "DELETE FROM warehouse WHERE id = ?";
    private static final String GET_ID = "SELECT SCOPE_IDENTITY()";
    private static final String SELECT_ALL_WAREHOUSES = "SELECT * FROM warehouse";
    private static final String INSERT_WAREHOUSE = "INSERT INTO warehouse "
        + "(name, price, amount) "
        + " VALUES(?,?,?)";
    private static ConnectionFactory mockConnectionFactory;
    private Connection mockConnection;
    private PreparedStatement mockStatement;
    private ResultSet mockResultSet;
    private WarehouseRepositoryImpl warehouseRepositoryImpl;
    private Warehouse warehouse;

    @BeforeEach
    void init() throws SQLException, IOException {
        mockConnection = mock(Connection.class);
        mockStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        mockConnectionFactory = Mockito.spy(ConnectionFactory.getInstance());
        warehouseRepositoryImpl = Mockito.spy(new WarehouseRepositoryImpl(mockConnectionFactory));
        Mockito.doReturn(mockConnection).when(mockConnectionFactory).getConnection();
        warehouse = new Warehouse("Lola", Double.MIN_VALUE, 2);
        warehouse.setIdNumber(1L);
        createMockByIdMethod();
    }

    void createMockByIdMethod() throws SQLException {
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
     * @throws SQLException exception, RepositoryException exception
     */
    @Test
    void whenCreateMethodCalledThenCorrectEntityReturns() throws SQLException, RepositoryException {
        when(mockConnection.prepareStatement(INSERT_WAREHOUSE)).thenReturn(mockStatement);
        when(mockConnection.prepareStatement(GET_ID)).thenReturn(mockStatement);
        Warehouse result = warehouseRepositoryImpl.create(warehouse);

        assertEquals(warehouse, result);
    }

    /**
     * testing update method to return the warehouse.
     *
     * @throws SQLException exception
     */
    @Test
    void whenUpdateMethodCalledThenCorrectEntityReturns() throws SQLException, RepositoryException {
        when(mockConnection.prepareStatement(UPDATE_WAREHOUSE)).thenReturn(mockStatement);
        Warehouse result = warehouseRepositoryImpl.update(warehouse);

        assertEquals(result, warehouse);
    }

    /**
     * testing delete method to return true.
     *
     * @throws SQLException exception
     */
    @Test
    void whenDeleteMethodCalledAndEntityExistsThenReturnTrue() throws SQLException, RepositoryException {
        when(mockConnection.prepareStatement(DELETE_WAREHOUSE_BY_ID)).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
        boolean result = warehouseRepositoryImpl.delete(warehouse.getIdNumber());

        assertTrue(result);
    }

    /**
     * testing delete method to return false.
     *
     * @throws SQLException exception
     */
    @Test
    void whenDeleteMethodCalledAndEntityNotFoundThenReturnFalse() throws SQLException, RepositoryException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(-1);
        boolean result = warehouseRepositoryImpl.delete(warehouse.getIdNumber());

        assertFalse(result);
    }

    /**
     * testing getAll method to return empty List.
     *
     * @throws SQLException exception
     */
    @Test
    void whenGetAllMethodCalledThenCorrectListReturns() throws SQLException, RepositoryException {
        when(mockConnection.prepareStatement(SELECT_ALL_WAREHOUSES)).thenReturn(mockStatement);
        when(mockResultSet.next()).thenReturn(true, false);
        List<Warehouse> warehouses = warehouseRepositoryImpl.getAll();

        assertFalse(warehouses.isEmpty());
    }

    /**
     * testing create method to throw FAILED_TO_CREATE_NEW_SHOP Exception.
     */
    @Test
    void whenCreateMethodCalledThrowsSQLExceptionThenDaoExceptionMustBeThrown() {
        Throwable exception = assertThrows(RepositoryException.class, () -> {
            when(mockConnection.prepareStatement(INSERT_WAREHOUSE)).thenThrow(new SQLException());
            warehouseRepositoryImpl.create(warehouse);
        });

        assertEquals(exception.getMessage(), FAILED_TO_CREATE_NEW_WAREHOUSE.getMessage());
    }

    /**
     * testing create method to throw THE_SHOP_CANNOT_BE_NULL Exception.
     */
    @Test
    void whenCreateMethodCalledWithIdNullThenDaoExceptionMustBeThrown() {
        Throwable exception = assertThrows(RepositoryException.class, () -> {
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
            when(mockResultSet.next()).thenReturn(false);
            warehouseRepositoryImpl.create(warehouse);
        });

        assertEquals(exception.getMessage(), THE_WAREHOUSE_CANNOT_BE_NULL.getMessage());
    }

    /**
     * testing update method to throw FAILED_TO_UPDATE_SHOP Exception.
     */
    @Test
    void whenUpdateMethodCalledThrowsSQLExceptionThenDaoExceptionMustBeThrown() {
        Throwable exception = assertThrows(RepositoryException.class, () -> {
            when(mockConnection.prepareStatement(UPDATE_WAREHOUSE)).thenThrow(new SQLException());
            warehouseRepositoryImpl.update(warehouse);
        });

        assertEquals(exception.getMessage(), FAILED_TO_UPDATE_WAREHOUSE.getMessage());
    }

    /**
     * testing delete method to throw FAILED_TO_DELETE_SHOP Exception.
     */
    @Test
    void whenDeleteMethodCalledThrowsSQLExceptionThenDaoExceptionMustBeThrown() {
        Throwable exception = assertThrows(RepositoryException.class, () -> {
            when(mockConnection.prepareStatement(DELETE_WAREHOUSE_BY_ID)).thenThrow(new SQLException());
            warehouseRepositoryImpl.delete(warehouse.getIdNumber());
        });

        assertEquals(exception.getMessage(), FAILED_TO_DELETE_WAREHOUSE.getMessage());
    }

    /**
     * testing getAll method to throw FAILED_TO_GET_ALL_SHOP Exception.
     */
    @Test
    void whenGetAllMethodCalledThrowsSQLExceptionThenDaoExceptionMustBeThrown() {
        Throwable exception = assertThrows(RepositoryException.class, () -> {
            when(mockConnection.prepareStatement(SELECT_ALL_WAREHOUSES)).thenThrow(new SQLException());
            warehouseRepositoryImpl.getAll();
        });

        assertEquals(exception.getMessage(), FAILED_TO_GET_ALL_WAREHOUSE.getMessage());
    }

    /**
     * testing get method to throw FAILED_TO_GET_SHOP_BY_ID Exception.
     */
    @Test
    void whenGetByIdThrowsSQLExceptionThenDaoExceptionMustBeThrown() {
        Throwable exception = assertThrows(RepositoryException.class, () -> {
            when(mockConnection.prepareStatement(SELECT_WAREHOUSE_BY_ID)).thenThrow(new SQLException());
            warehouseRepositoryImpl.get(1L);
        });

        assertEquals(exception.getMessage(), FAILED_TO_GET_WAREHOUSE_BY_ID.getMessage() + " 1");
    }

    /**
     * testing get method to return null.
     */
    @Test
    void whenGetByIdCalledWithIdNullThenNullIsReturned() throws SQLException, RepositoryException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockResultSet.next()).thenReturn(false);

        assertFalse(warehouseRepositoryImpl.get(1L).isPresent());
    }

    /**
     * testing get method to throw RepositoryException.
     */
    @Test
    void whenGetWarehouseByIdThenReturnSqlException() throws SQLException {
        when(mockConnectionFactory.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenThrow(SQLException.class);
        Throwable thrown = assertThrows(RepositoryException.class, () -> warehouseRepositoryImpl.get(1L));
        assertNotNull(thrown.getMessage());
    }

    /**
     * testing getAll method to throw RepositoryException.
     */
    @Test
    void whenGetAllWarehousesThenReturnSqlException() throws SQLException {
        when(mockConnectionFactory.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(any(String.class))).thenThrow(SQLException.class);
        Throwable thrown = assertThrows(RepositoryException.class, () -> warehouseRepositoryImpl.getAll());
        assertNotNull(thrown.getMessage());
    }

    /**
     * testing create method to throw RepositoryException.
     */
    @Test
    void whenAddWarehouseThenReturnSqlException() throws SQLException {
        when(mockConnectionFactory.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(any(String.class))).thenThrow(SQLException.class);
        Throwable thrown = assertThrows(RepositoryException.class, () -> warehouseRepositoryImpl.create(warehouse));
        assertNotNull(thrown.getMessage());
    }
}
