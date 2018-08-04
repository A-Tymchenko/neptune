package com.ra.shop.repository.impl;

import com.ra.shop.exceptions.RepositoryException;
import com.ra.shop.model.Order;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderRepositoryMockTest {

    private static JdbcTemplate jdbcTemplate;
    private static OrderRepositoryImpl repository;
    private KeyHolder keyHolder;

    @BeforeAll
    static void setupGlobal() {
        jdbcTemplate = mock(JdbcTemplate.class);
        repository = mock(OrderRepositoryImpl.class);
    }

    @BeforeEach
    void setup() {
        keyHolder = mock(KeyHolder.class);
    }

    @Test
    void whenCreateOrderThenReturnCreatedOrder() throws RepositoryException {
        Order order = new Order(10, 90d, false, 0, false);
        when(jdbcTemplate.update(eq("INSERT INTO ORDERS (NUMBER, PRICE, DELIVERY_INCLUDED, DELIVERY_COST, EXECUTED) "
                + "VALUES (?, ?, ?, ?, ?)"), any(PreparedStatementCreator.class), any(KeyHolder.class))).thenReturn(1);
        when(keyHolder.getKey()).thenReturn(1);
        when(repository.create(order)).thenReturn(order);
        Order created = repository.create(order);
        created.setId(keyHolder.getKey().intValue());
        assertEquals(order, created);
    }

    @Test
    void whenGetOrderByIdThenReturnOptionalOfOrder() throws RepositoryException {
        Order order = new Order(10, 90d, false, 0, false);
        order.setId(2L);
        when(jdbcTemplate.queryForObject(
                eq("SELECT * FROM ORDERS WHERE ORDER_ID = ?"),
                any(Object[].class), any(RowMapper.class))).thenReturn(order);
        when(repository.get(order.getId())).thenReturn(Optional.of(order));
        Optional<Order> optional = repository.get(order.getId());
        assertTrue(optional.isPresent());
        assertEquals(order, optional.get());
    }

    @Test
    void whenUpdateOrderThenReturnUpdatedOrder() throws RepositoryException {
        Order order = new Order(10, 90d, false, 0, false);
        when(jdbcTemplate.update("UPDATE ORDERS SET NUMBER = ?, PRICE = ?, DELIVERY_INCLUDED = ?, "
                        + "DELIVERY_COST = ?, EXECUTED = ? WHERE ORDER_ID = ?",
                order.getNumber(), order.getPrice(), order.getDeliveryIncluded(),
                order.getDeliveryCost(), order.getExecuted(), order.getId())).thenReturn(1);
        when(repository.update(order)).thenReturn(order);
        Order updated = repository.update(order);
        assertNotNull(updated);
        assertEquals(order, updated);
    }

    @Test
    void deleteOrderAntReturnTrueIfSuccessful() throws RepositoryException {
        Order order = new Order(10, 90d, false, 0, false);
        order.setId(4L);
        when(jdbcTemplate.update(eq("DELETE FROM ORDERS WHERE ORDER_ID = ?"), any(Long.class))).thenReturn(1);
        when(repository.delete(order.getId())).thenReturn(true);
        boolean isDeleted = repository.delete(order.getId());
        assertTrue(isDeleted);
    }

    @Test
    void whenGetAllOrdersThenREturnListOfOrders() throws RepositoryException {
        when(jdbcTemplate.query(eq("SELECT * FROM ORDERS"), any(BeanPropertyRowMapper.class))).thenReturn(new ArrayList<>());
        List<Order> orderList = repository.getAll();
        assertTrue(orderList.isEmpty());
    }

    /*@Test
    void whenCreateOrderThenReturnCreatedOrder() throws RepositoryException {
        Order order = new Order(10, 90d, false, 0, false);
        when(jdbcTemplate.update(eq("INSERT INTO ORDERS (NUMBER, PRICE, DELIVERY_INCLUDED, DELIVERY_COST, EXECUTED) "
                + "VALUES (?, ?, ?, ?, ?)"), any(GeneratedKeyHolder.class))).thenReturn(1);
        when(keyHolder.getKey()).thenReturn(1L);
        when(repository.create(order)).thenReturn(order);
        Order created = repository.create(order);
        created.setId(keyHolder.getKey().intValue());
        assertNotNull(created);
        assertEquals(1L, created.getId());
        assertEquals(order, created);
    }

    @Test
    void whenGetOrderByIdThenReturnOptionalOfOrder() throws RepositoryException {
        Order order = new Order(10, 90d, false, 0, false);
        order.setId(2);
        when(jdbcTemplate.queryForObject(
                eq("SELECT * FROM ORDERS WHERE ORDER_ID = ?"),
                any(Object[].class), any(RowMapper.class))).thenReturn(order);
        when(repository.get(order.getId())).thenReturn(Optional.of(order));
        Optional<Order> optional = repository.get(order.getId());
        assertTrue(optional.isPresent());
        assertEquals(order, optional.get());
    }

    @Test
    void whenUpdateOrderThenReturnUpdatedOrder() throws RepositoryException {
        Order order = new Order(10, 90d, false, 0, false);
        order.setId(3);
        when(jdbcTemplate.update("UPDATE ORDERS SET NUMBER = ?, PRICE = ?, DELIVERY_INCLUDED = ?, "
                        + "DELIVERY_COST = ?, EXECUTED = ? WHERE ORDER_ID = ?",
                order.getNumber(), order.getPrice(), order.getDeliveryIncluded(),
                order.getDeliveryCost(), order.getExecuted(), order.getId())).thenReturn(1);
        when(repository.update(order)).thenReturn(order);
        Order updated = repository.update(order);
        assertNotNull(updated);
        assertEquals(order, updated);
    }*/
}
