package com.ra.shop.warehouse;

import com.ra.shop.connection.ConnectionFactory;
import com.ra.shop.dao.exception.ExceptionMessage;
import com.ra.shop.dao.exception.WarehouseDaoException;
import com.ra.shop.dao.implementation.WarehouseDaoImpl;
import com.ra.shop.wharehouse.Warehouse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class WarehouseDaoMockitoTest {

    private ResultSet resultSet;
    private ConnectionFactory connectionFactory;
    private PreparedStatement preparedStatement;
    private Connection connection;
    private Warehouse warehouse;
    private WarehouseDaoImpl warehouseDao;

    @BeforeEach
    public void initDB() {
        warehouse = new Warehouse("udemy", 1.1, 2);
        resultSet = mock(ResultSet.class);
        connectionFactory = mock(ConnectionFactory.class);
        preparedStatement = mock(PreparedStatement.class);
        connection = mock(Connection.class);
        warehouseDao = new WarehouseDaoImpl(connectionFactory);
    }

    @Test
    public void whenGetWarehousesExecutedThenListSizeReturned() throws SQLException, WarehouseDaoException {
        when(connectionFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        List<Warehouse> list = warehouseDao.getAll();
        Assertions.assertEquals(2, list.size());
    }

    @Test
    public void whenGetWarehouseByIdThenReturnSqlException() throws SQLException {
        when(connectionFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(any(String.class))).thenThrow(SQLException.class);
        Throwable thrown = assertThrows(WarehouseDaoException.class, () -> warehouseDao.getById(1));
        assertNotNull(thrown.getMessage());
    }

    @Test
    public void whenGetAllWarehousesThenReturnSqlException() throws SQLException {
        when(connectionFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(any(String.class))).thenThrow(SQLException.class);
        Throwable thrown = assertThrows(WarehouseDaoException.class, () -> warehouseDao.getAll());
        assertNotNull(thrown.getMessage());
    }

    @Test
    public void whenAddWarehouseThenReturnSqlException() throws SQLException {
        when(connectionFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        Throwable thrown = assertThrows(WarehouseDaoException.class, () -> warehouseDao.create(warehouse));
        assertNotNull(thrown.getMessage());
    }

    @Test
    public void whenAddWarehouseThenReturnWarehouse() throws SQLException, WarehouseDaoException {
        when(connectionFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(connectionFactory.getConnection()).thenReturn(connection);

        warehouseDao.create(warehouse);
    }
}
