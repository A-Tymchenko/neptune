package com.ra.shop.repository.implementation;

import com.ra.shop.exceptions.RepositoryException;
import com.ra.shop.model.Order;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class OrderRepositoryMockTest {

    private static OrderRepositoryImpl repository;
    private static JdbcTemplate jdbcTemplate;
    private KeyHolder keyHolder;

    private Connection connection;
    private PreparedStatement statement;

    @BeforeAll
    static void initGlobal() {
        jdbcTemplate = mock(JdbcTemplate.class);
        repository = new OrderRepositoryImpl(jdbcTemplate);
    }

    @BeforeEach
    void setup() {
        keyHolder = mock(KeyHolder.class);
        connection = mock(Connection.class);
        statement = mock(PreparedStatement.class);
    }

    @Test
    void whenCreateOrderThenReturnCreatedOrder() throws RepositoryException, SQLException {
        Order order = new Order(10, 100d, false, 0, false);
        when(connection.prepareStatement(
                eq("INSERT INTO ORDERS (NUMBER, PRICE, DELIVERY_INCLUDED, DELIVERY_COST, EXECUTED) "
                + "VALUES (?, ?, ?, ?, ?)"))).thenReturn(statement);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((PreparedStatementCreator) invocation.getArguments()[0]).createPreparedStatement(connection);
                return null;
            }
        }).when(jdbcTemplate).update(any(PreparedStatementCreator.class), any(KeyHolder.class));
        when(keyHolder.getKey()).thenReturn(1L);
        Order created = repository.create(order);
        order.setId((long) keyHolder.getKey());
        assertNotNull(created);
    }

    @Test
    void whenGetOrderThenReturnCorrectEntity() throws RepositoryException {
        Order order = new Order(10, 100d, false, 0, false);
        order.setId(1L);
        when(jdbcTemplate
                .queryForObject(
                        eq("SELECT * FROM ORDERS WHERE ORDER_ID = ?"),
                        any(RowMapper.class),
                        any(Object[].class))).thenReturn(order);
        Order found = repository.get(order.getId());
        assertNotNull(found);
        assertEquals(order, found);
    }

    @Test
    void whenDeleteOrderThenReturnSuccessfulQueryExecutionResultTrue() throws RepositoryException {
        Order order = new Order(10, 100d, false, 0, false);
        order.setId(5L);
        when(jdbcTemplate.update(eq("DELETE FROM ORDERS WHERE ORDER_ID = ?"), any(Object.class))).thenReturn(1);
        boolean isDeleted = repository.delete(order.getId());
        assertTrue(isDeleted);
    }

    @Test
    void whenDeleteOrderThenReturnUnsuccessfulQueryExecutionResultFalse() throws RepositoryException {
        Order order = new Order(10, 100d, false, 0, false);
        order.setId(6L);
        when(jdbcTemplate.update(eq("DELETE FROM ORDERS WHERE ORDER_ID = ?"), any(Object.class))).thenReturn(0);
        boolean isDeleted = repository.delete(order.getId());
        assertFalse(isDeleted);
    }

    @Test
    void whenGetAllThenReturnListOfExistedOrders() throws RepositoryException {
        List<Map<String, Object>> orders = getListOfOrders();
        when(jdbcTemplate.queryForList(eq("SELECT * FROM ORDERS"))).thenReturn(orders);
        List<Order> actual = repository.getAll();
        List<Order> expected = repository.getListOfOrders(orders);
        assertEquals(expected, actual);
    }

    @Test
    void whenUpdateOrderThenReturnUpdatedOrder() throws RepositoryException {
        Order order = new Order(10, 100d, false, 0, false);
        order.setId(1L);
        final String query = "UPDATE ORDERS SET NUMBER = ?, PRICE = ?, DELIVERY_INCLUDED = ?, "
                + "DELIVERY_COST = ?, EXECUTED = ? WHERE ORDER_ID = ?";
        doAnswer(invocation -> {
            ((PreparedStatementSetter) invocation.getArguments()[1]).setValues(statement);
            return null;
        }).when(jdbcTemplate).update(eq(query), any(PreparedStatementSetter.class));
        Order updated = repository.update(order);
        assertNotNull(updated);
        assertEquals(order, updated);
    }

    @Test
    void whenGetOrderThenThrowRepositoryException() {
        when(jdbcTemplate
                .queryForObject(
                        eq("SELECT * FROM ORDERS WHERE ORDER_ID = ?"),
                        any(RowMapper.class),
                        any(Object[].class)))
                .thenThrow(new DataAccessException(""){});
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            repository.get(new Order().getId());
        });
        assertNotNull(repositoryException);
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    @Test
    void whenUpdateOrderThenThrowRepositoryException() {
        when(jdbcTemplate.update(anyString(), any(PreparedStatementSetter.class))).thenThrow(new DataAccessException(""){});
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            repository.update(new Order());
        });
        assertNotNull(repositoryException);
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    @Test
    void whenCreateOrderThenThrowRepositoryException() throws SQLException {
        Order order = new Order(10, 100d, false, 0, false);
        when(connection.prepareStatement(
                eq("INSERT INTO ORDERS (NUMBER, PRICE, DELIVERY_INCLUDED, DELIVERY_COST, EXECUTED) "
                + "VALUES (?, ?, ?, ?, ?)"))).thenReturn(statement);
        doThrow(new DataAccessException(""){})
                .when(jdbcTemplate).update(any(PreparedStatementCreator.class), any(KeyHolder.class));
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            repository.create(order);
        });
        assertNotNull(repositoryException);
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    @Test
    void whenGetAllOrdersThenThrowRepositoryException() {
        when(jdbcTemplate.queryForList(eq("SELECT * FROM ORDERS"))).thenThrow(new DataAccessException(""){});
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            repository.getAll();
        });
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    @Test
    void whenDeleteOrderThenThrowRepositoryException() {
        when(jdbcTemplate.update(
                eq("DELETE FROM ORDERS WHERE ORDER_ID = ?"),
                any(Object.class))).thenThrow(new DataAccessException(""){});
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            repository.delete(new Order().getId());
        });
        assertNotNull(repositoryException);
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    private List<Map<String, Object>> getListOfOrders() {
        List<Map<String, Object>> mapList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        Order order = new Order(121, 13d, true, 90, true);
        order.setId(9L);
        map.put("ORDER_ID", order.getId());
        map.put("NUMBER", order.getNumber());
        map.put("PRICE", order.getPrice());
        map.put("DELIVERY_INCLUDED", order.getDeliveryIncluded());
        map.put("DELIVERY_COST", order.getDeliveryCost());
        map.put("EXECUTED", order.getExecuted());
        mapList.add(map);
        return mapList;
    }

}
