package com.ra.shop.repository.implementation;

import com.ra.shop.exceptions.RepositoryException;
import com.ra.shop.model.Order;
import org.junit.jupiter.api.*;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class OrderRepositoryMockTest {

    private OrderRepositoryImpl repository;
    private JdbcTemplate jdbcTemplate;
    private KeyHolder keyHolder;

    private Connection connection;
    private PreparedStatement statement;
    private static Order TEST_ORDER = new Order(10, 100d, false, 0, false);
    private static final Long DEFAULT_ID = 1L;


    @BeforeAll
    static void createSchema() {
        TEST_ORDER.setId(DEFAULT_ID);
    }

    @BeforeEach
    void setup() {
        jdbcTemplate = mock(JdbcTemplate.class);
        repository = new OrderRepositoryImpl(jdbcTemplate);
        keyHolder = mock(KeyHolder.class);
        connection = mock(Connection.class);
        statement = mock(PreparedStatement.class);
    }

    @Test
    void whenCreateOrderThenReturnCreatedOrder() throws RepositoryException, SQLException {
        when(connection.prepareStatement(
                eq("INSERT INTO ORDERS (NUMBER, PRICE, DELIVERY_INCLUDED, DELIVERY_COST, EXECUTED) "
                        + "VALUES (?, ?, ?, ?, ?)"))).thenReturn(statement);
        doAnswer(invocation -> {
            ((PreparedStatementCreator) invocation.getArguments()[0]).createPreparedStatement(connection);
            return null;
        }).when(jdbcTemplate).update(any(PreparedStatementCreator.class), any(KeyHolder.class));
        when(keyHolder.getKey()).thenReturn(DEFAULT_ID);
        Order created = repository.create(TEST_ORDER);
        TEST_ORDER.setId((long) keyHolder.getKey());

        assertEquals(TEST_ORDER, created);
    }

    @Test
    void whenGetOrderThenReturnCorrectEntity() throws RepositoryException {
        when(jdbcTemplate.queryForObject(eq("SELECT * FROM ORDERS WHERE ORDER_ID = ?"), any(RowMapper.class),
                any(Object.class))).thenReturn(TEST_ORDER);

        assertEquals(TEST_ORDER, repository.get(TEST_ORDER.getId()));
    }

    @Test
    void whenDeleteOrderThenReturnSuccessfulQueryExecutionResultTrue() throws RepositoryException {
        TEST_ORDER.setId(5L);
        when(jdbcTemplate.update(eq("DELETE FROM ORDERS WHERE ORDER_ID = ?"), any(Object.class))).thenReturn(1);

        assertTrue(repository.delete(TEST_ORDER.getId()));
    }

    @Test
    void whenDeleteOrderThenReturnUnsuccessfulQueryExecutionResultFalse() throws RepositoryException {
        TEST_ORDER.setId(6L);
        when(jdbcTemplate.update(eq("DELETE FROM ORDERS WHERE ORDER_ID = ?"), any(Object.class))).thenReturn(0);

        assertFalse(repository.delete(TEST_ORDER.getId()));
    }

    @Test
    void whenGetAllThenReturnListOfExistedOrders() throws RepositoryException {
        when(jdbcTemplate.query(eq("SELECT * FROM ORDERS"), any(RowMapper.class)))
                .thenReturn(new ArrayList<>());

        assertTrue(repository.getAll().isEmpty());
    }

    @Test
    void whenUpdateOrderThenReturnUpdatedOrder() throws RepositoryException {
        doAnswer(invocation -> {
            ((PreparedStatementSetter) invocation.getArguments()[1]).setValues(statement);
            return null;
        }).when(jdbcTemplate).update(eq("UPDATE ORDERS SET NUMBER = ?, PRICE = ?, DELIVERY_INCLUDED = ?, "
                + "DELIVERY_COST = ?, EXECUTED = ? WHERE ORDER_ID = ?"), any(PreparedStatementSetter.class));

        assertEquals(TEST_ORDER, repository.update(TEST_ORDER));
    }

    @Test
    void whenGetOrderThenThrowRepositoryException() {
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class),
                any(Object.class))).thenThrow(new DataAccessException("") {});

        assertThrows(RepositoryException.class, () -> repository.get(TEST_ORDER.getId()));
    }

    @Test
    void whenUpdateOrderThenThrowRepositoryException() {
        when(jdbcTemplate.update(anyString(), any(PreparedStatementSetter.class)))
                .thenThrow(new DataAccessException("") {});

        assertThrows(RepositoryException.class, () -> repository.update(new Order()));
    }

    @Test
    void whenCreateOrderThenThrowRepositoryException() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        doThrow(new DataAccessException("") {})
                .when(jdbcTemplate).update(any(PreparedStatementCreator.class), any(KeyHolder.class));

        assertThrows(RepositoryException.class, () -> repository.create(TEST_ORDER));
    }

    @Test
    void whenGetAllOrdersThenThrowRepositoryException(){
        doThrow(new DataAccessException("") {})
                .when(jdbcTemplate).query(anyString(), any(BeanPropertyRowMapper.class));

        assertThrows(RepositoryException.class, () -> repository.getAll());
    }

    @Test
    void whenDeleteOrderThenThrowRepositoryException() {
        when(jdbcTemplate.update(anyString(), any(Object.class)))
                .thenThrow(new DataAccessException("") {});

        assertThrows(RepositoryException.class, () -> repository.delete(TEST_ORDER.getId()));
    }

}
