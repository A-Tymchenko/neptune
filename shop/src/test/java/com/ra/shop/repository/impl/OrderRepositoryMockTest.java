package com.ra.shop.repository.impl;

import com.ra.shop.exceptions.RepositoryException;
import com.ra.shop.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class OrderRepositoryMockTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement statement;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private static OrderRepositoryImpl repository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void whenCreateOrderThenReturnCreatedOrderWithId() throws SQLException, RepositoryException {
        Order order = new Order(10, 90d, false, 0, false);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(jdbcTemplate.update((PreparedStatementCreator) statement, any(KeyHolder.class))).thenReturn(1);
        Order created = repository.create(order);
        assertEquals(order, created);
    }

}
