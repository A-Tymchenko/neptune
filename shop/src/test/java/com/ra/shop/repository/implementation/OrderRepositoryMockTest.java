package com.ra.shop.repository.implementation;

import com.ra.shop.exceptions.RepositoryException;
import com.ra.shop.model.Order;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    private PreparedStatement statement;

    @BeforeAll
    static void initGlobal() {
        jdbcTemplate = mock(JdbcTemplate.class);
        repository = new OrderRepositoryImpl(jdbcTemplate);
    }

    @BeforeEach
    void setup() throws SQLException {
        keyHolder = mock(KeyHolder.class);
        statement = mock(PreparedStatement.class);
    }

    @Test
    void whenCreateOrderThenReturnCreatedOrder() throws RepositoryException, SQLException {
        Order order = new Order(10, 100d, false, 0, false);
        when(jdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class))).thenReturn(1);
        when(keyHolder.getKey()).thenReturn(1L);
        Order created = repository.create(order);
        order.setId(1L);
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
        when(jdbcTemplate.update("DELETE FROM ORDERS WHERE ORDER_ID = ?", order.getId())).thenReturn(1);
        boolean isDeleted = repository.delete(order.getId());
        assertTrue(isDeleted);
    }

    @Test
    void whenDeleteOrderThenReturnUnsuccessfulQueryExecutionResultFalse() throws RepositoryException {
        Order order = new Order(10, 100d, false, 0, false);
        order.setId(6L);
        when(jdbcTemplate.update("DELETE FROM ORDERS WHERE ORDER_ID = ?", order.getId())).thenReturn(0);
        boolean isDeleted = repository.delete(order.getId());
        assertFalse(isDeleted);
    }

    @Test
    void whenGetAllThenReturnListOfExistedOrders() throws RepositoryException {
        List<Map<String, Object>> expectedData = new ArrayList<>();
        when(jdbcTemplate.queryForList("SELECT * FROM ORDERS")).thenReturn(expectedData);
        List<Order> orders = repository.getAll();
        assertTrue(orders.isEmpty());
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
    void whenCreateOrderThenThrowRepositoryException() {
        Order order = new Order(10, 100d, false, 0, false);
        when(jdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class)))
                .thenThrow(new DataAccessException(""){});
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            repository.create(order);
        });
        assertNotNull(repositoryException);
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    /*@Test
    void whenGetAllOrdersThenThrowRepositoryException() {
        when(jdbcTemplate.queryForList(anyString())).thenThrow(new DataAccessException(""){});
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            repository.getAll();
        });
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }*/

    /*@Test
    void whenDeleteOrderThenThrowRepositoryException() {
        when(jdbcTemplate.update(anyString(), any(Object.class))).thenThrow(new DataAccessException(""){});
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            repository.delete(new Order().getId());
        });
        assertNotNull(repositoryException);
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }*/
}
