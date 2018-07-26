package com.ra.shop;

import com.ra.shop.config.ConnectionFactory;
import com.ra.shop.exceptions.RepositoryException;
import com.ra.shop.model.Order;
import com.ra.shop.repository.implementation.OrderRepositoryImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderRepositoryMockTest {

    private static OrderRepositoryImpl mockOrderRepository;
    private static ConnectionFactory factory;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;

    @BeforeAll
    static void initGlobal() {
        factory = mock(ConnectionFactory.class);
        mockOrderRepository = new OrderRepositoryImpl(factory);
    }

    @BeforeEach
    void init() throws SQLException {
        connection = mock(Connection.class);
        statement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        when(factory.getConnection()).thenReturn(connection);
    }

    @Test
    void whenCreateOrderThenReturnCreatedOrderWithId() throws SQLException, RepositoryException {
        Order order = new Order(10, 100D, false, 0, true);
        when(connection.prepareStatement("INSERT INTO ORDERS (NUMBER, PRICE, DELIVERY_INCLUDED, DELIVERY_COST, EXECUTED) "
                        + "VALUES(?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)).thenReturn(statement);
        when(statement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(Boolean.TRUE).thenReturn(Boolean.FALSE);
        Order created = mockOrderRepository.create(order);
        assertNotNull(created.getId());
        assertEquals(order, created);
    }

    @Test
    void whenCreateOrderThenReturnOrderWithoutSettedId() throws SQLException, RepositoryException {
        Order order = new Order(10, 100D, false, 0, true);
        when(connection.prepareStatement("INSERT INTO ORDERS (NUMBER, PRICE, DELIVERY_INCLUDED, DELIVERY_COST, EXECUTED) "
                        + "VALUES(?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)).thenReturn(statement);
        when(statement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(Boolean.FALSE);
        Order created = mockOrderRepository.create(order);
        assertNull(created.getId());
    }

    @Test
    void whenCreateOrderThenThrowRepositoryException() throws RepositoryException, SQLException {
        Order order = new Order(10, 100D, false, 0, true);
        when(connection.prepareStatement("INSERT INTO ORDERS (NUMBER, PRICE, DELIVERY_INCLUDED, DELIVERY_COST, EXECUTED) "
                        + "VALUES(?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS))
                .thenThrow(new SQLException());
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            mockOrderRepository.create(order);
        });
        assertNotNull(repositoryException);
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    @Test
    void whenGetOrderThenReturnOptionalOfOrder() throws SQLException, RepositoryException {
        Order order = new Order(10, 100D, false, 0, true);
        when(connection.prepareStatement("SELECT * FROM ORDERS WHERE ORDER_ID = ?"))
                .thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(Boolean.TRUE).thenReturn(Boolean.FALSE);
        Optional<Order> optional = mockOrderRepository.get(1L);
        assertTrue(optional.isPresent());
    }

    @Test
    void whenGetOrderThenReturnOptionalEmpty() throws SQLException, RepositoryException {
        Order order = new Order(10, 100D, false, 0, true);
        when(connection.prepareStatement("SELECT * FROM ORDERS WHERE ORDER_ID = ?"))
                .thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(Boolean.FALSE);
        Optional<Order> optional = mockOrderRepository.get(1L);
        assertFalse(optional.isPresent());
    }

    @Test
    void whenGetOrderThenThrowRepositoryException() throws RepositoryException, SQLException {
        Order order = new Order(10, 100D, false, 0, true);
        order.setId(5L);
        when(connection.prepareStatement("SELECT * FROM ORDERS WHERE ORDER_ID = ?"))
                .thenThrow(new SQLException());
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            mockOrderRepository.get(order.getId());
        });
        assertNotNull(repositoryException);
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    @Test
    void whenUpdateOrderThenReturnUpdatedOrder() throws SQLException, RepositoryException {
        Order order = new Order(10, 100D, false, 0, true);
        order.setDeliveryIncluded(Boolean.TRUE);
        order.setDeliveryCost(100);
        order.setId(2L);
        when(connection.prepareStatement("UPDATE ORDERS SET NUMBER = ?, PRICE = ?, DELIVERY_INCLUDED = ?, "
                + "DELIVERY_COST = ?, EXECUTED = ? WHERE ORDER_ID = ?")).thenReturn(statement);
        Order updated = mockOrderRepository.update(order);
        assertAll(() -> {
            assertEquals(order.getDeliveryIncluded(), updated.getDeliveryIncluded());
            assertEquals(order.getDeliveryCost(), updated.getDeliveryCost());
        });
    }

    @Test
    void whenUpdateOrderThenThrowRepositoryException() throws RepositoryException, SQLException {
        Order order = new Order(10, 100D, false, 0, true);
        when(connection.prepareStatement("UPDATE ORDERS SET NUMBER = ?, PRICE = ?, DELIVERY_INCLUDED = ?, "
                + "DELIVERY_COST = ?, EXECUTED = ? WHERE ORDER_ID = ?"))
                .thenThrow(new SQLException());
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            mockOrderRepository.update(order);
        });
        assertNotNull(repositoryException);
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    @Test
    void whenDeleteOrderThenReturnTrue() throws SQLException, RepositoryException {
        Order order = new Order(10, 100D, false, 0, true);
        order.setId(22L);
        when(connection.prepareStatement("DELETE FROM ORDERS WHERE ORDER_ID = ?")).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);
        Boolean isDeleted = mockOrderRepository.delete(order.getId());
        assertTrue(isDeleted);
    }

    @Test
    void whenDeleteOrderThenReturnFalse() throws SQLException, RepositoryException {
        Order order = new Order(10, 100D, false, 0, true);
        order.setId(23L);
        when(connection.prepareStatement("DELETE FROM ORDERS WHERE ORDER_ID = ?")).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(0);
        Boolean isDeleted = mockOrderRepository.delete(order.getId());
        assertFalse(isDeleted);
    }

    @Test
    void whenDeleteOrderThenThrowRepositoryException() throws SQLException {
        Order order = new Order(10, 100D, false, 0, true);
        order.setId(23L);
        when(connection.prepareStatement("DELETE FROM ORDERS WHERE ORDER_ID = ?")).thenThrow(new SQLException());
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            mockOrderRepository.delete(order.getId());
        });
        assertNotNull(repositoryException);
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    @Test
    void getAllOrdersAndReturnListOfORders() throws SQLException, RepositoryException {
        when(connection.prepareStatement("SELECT * FROM ORDERS")).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        List<Order> actual = mockOrderRepository.getAll();
        assertFalse(actual.isEmpty());
        assertEquals(3, actual.size());
    }

    @Test
    void whenGetAllOrdersThenReturnEmptyList() throws SQLException, RepositoryException {
        when(connection.prepareStatement("SELECT * FROM ORDERS")).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(Boolean.FALSE);
        List<Order> actual = mockOrderRepository.getAll();
        assertTrue(actual.isEmpty());
        assertEquals(0, actual.size());
        assertEquals(Collections.emptyList(), actual);
    }

    @Test
    void whenGetAllOrdersThenThrowRepositoryException() throws SQLException {
        when(connection.prepareStatement("SELECT * FROM ORDERS")).thenThrow(new SQLException());
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            mockOrderRepository.getAll();
        });
        assertNotNull(repositoryException);
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

}
