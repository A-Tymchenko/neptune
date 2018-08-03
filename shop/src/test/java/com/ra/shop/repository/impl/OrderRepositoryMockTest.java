package com.ra.shop.repository.impl;

import com.ra.shop.exceptions.RepositoryException;
import com.ra.shop.model.Order;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class OrderRepositoryMockTest {

    private static OrderRepositoryImpl repository;
    private static JdbcTemplate jdbcTemplate;
    private KeyHolder keyHolder;

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;

    @BeforeAll
    static void initGlobal() {
        jdbcTemplate = mock(JdbcTemplate.class);
        repository = new OrderRepositoryImpl(jdbcTemplate);
    }

    @BeforeEach
    void setup() throws SQLException {
        keyHolder = mock(KeyHolder.class);
        connection = mock(Connection.class);
        statement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
    }

    @Test
    void createOrder() throws RepositoryException, SQLException {
        Order order = new Order(10, 100d, false, 0, false);
        doAnswer(invocation -> {
            PreparedStatement statement = ((PreparedStatementCreator) invocation.getArguments()[0]).createPreparedStatement(connection);
            return statement;
        }).when(jdbcTemplate).update(any(PreparedStatementCreator.class), any(KeyHolder.class));
        Order created = repository.create(order);
        assertNotNull(created);
    }

    @Test
    void getOrder() throws RepositoryException {
        Order order = new Order(10, 100d, false, 0, false);
        order.setId(1L);
        when(jdbcTemplate
                .queryForObject(
                        eq("SELECT * FROM ORDERS WHERE ORDER_ID = ?"),
                        any(RowMapper.class),
                        any(Object[].class))).thenReturn(order);
        Optional<Order> optional = repository.get(order.getId());
        assertTrue(optional.isPresent());
        assertEquals(order, optional.get());
    }

    @Test
    void updateOrder() throws RepositoryException {
        Order order = new Order(10, 100d, false, 0, false);
        order.setId(1L);
        when(jdbcTemplate.update("UPDATE ORDERS SET NUMBER = ?, PRICE = ?, DELIVERY_INCLUDED = ?, "
                        + "DELIVERY_COST = ?, EXECUTED = ? WHERE ORDER_ID = ?",
                order.getNumber(), order.getPrice(), order.getDeliveryIncluded(),
                order.getDeliveryCost(), order.getExecuted(), order.getId())).thenReturn(1);
        Order updated = repository.update(order);
        assertNotNull(updated);
        assertEquals(order, updated);
    }

    @Test
    void deleteOrder() throws RepositoryException {
        Order order = new Order(10, 100d, false, 0, false);
        order.setId(5);
        when(jdbcTemplate.update("DELETE FROM ORDERS WHERE ORDER_ID = ?", order.getId())).thenReturn(1);
        boolean isDeleted = repository.delete(order.getId());
        assertTrue(isDeleted);
    }

    @Test
    void getAll() throws RepositoryException {
        List<Order> expectedData = new ArrayList<>();
        expectedData.add(new Order(10, 100d, false, 0, false));
        expectedData.add(new Order(110, 300d, false, 0, true));
        when(jdbcTemplate.query(eq("SELECT * FROM ORDERS"), any(BeanPropertyRowMapper.class))).thenReturn(expectedData);
        List<Order> orders = repository.getAll();
        assertFalse(orders.isEmpty());
        assertEquals(2, expectedData.size());
    }
}
