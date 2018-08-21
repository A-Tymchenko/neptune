package com.ra.shop.repository.implementation;

import com.ra.shop.exceptions.RepositoryException;
import com.ra.shop.model.User;
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

public class UserRepositoryMockTest {

    private UserRepositoryImpl repository;
    private JdbcTemplate jdbcTemplate;
    private KeyHolder keyHolder;

    private Connection connection;
    private PreparedStatement statement;
    private static User TEST_USER = new User("3806642341542", "Murchik", "Babulin",
            "USA", "murchik_21@gmail.com");
    private static final Long DEFAULT_ID = 1L;


    @BeforeAll
    static void createSchema() {
        TEST_USER.setId(DEFAULT_ID);
    }

    @BeforeEach
    void setup() {
        jdbcTemplate = mock(JdbcTemplate.class);
        repository = new UserRepositoryImpl(jdbcTemplate);
        keyHolder = mock(KeyHolder.class);
        connection = mock(Connection.class);
        statement = mock(PreparedStatement.class);
    }

    @Test
    void whenCreateUserThenReturnCreatedUser() throws RepositoryException, SQLException {
        when(connection.prepareStatement(
                eq("INSERT INTO USERS (PHONE_NUMBER, NAME, SECOND_NAME, COUNTRY, EMAIL_ADDRESS) "
                        + "VALUES(?, ?, ?, ?, ?)"))).thenReturn(statement);
        doAnswer(invocation -> {
            ((PreparedStatementCreator) invocation.getArguments()[0]).createPreparedStatement(connection);
            return null;
        }).when(jdbcTemplate).update(any(PreparedStatementCreator.class), any(KeyHolder.class));
        when(keyHolder.getKey()).thenReturn(DEFAULT_ID);
        User created = repository.create(TEST_USER);
        TEST_USER.setId((long) keyHolder.getKey());

        assertEquals(TEST_USER, created);
    }

    @Test
    void whenGetUserThenReturnCorrectEntity() throws RepositoryException {
        when(jdbcTemplate.queryForObject(eq("SELECT * FROM USERS WHERE USER_ID = ?"), any(RowMapper.class),
                any(Object.class))).thenReturn(TEST_USER);

        assertEquals(TEST_USER, repository.get(TEST_USER.getId()));
    }

    @Test
    void whenDeleteUserThenReturnSuccessfulQueryExecutionResultTrue() throws RepositoryException {
        TEST_USER.setId(5L);
        when(jdbcTemplate.update(eq("DELETE FROM USERS WHERE USER_ID = ?"), any(Object.class))).thenReturn(1);

        assertTrue(repository.delete(TEST_USER.getId()));
    }

    @Test
    void whenDeleteUserThenReturnUnsuccessfulQueryExecutionResultFalse() throws RepositoryException {
        TEST_USER.setId(6L);
        when(jdbcTemplate.update(eq("DELETE FROM USERS WHERE USER_ID = ?"), any(Object.class))).thenReturn(0);

        assertFalse(repository.delete(TEST_USER.getId()));
    }

    @Test
    void whenGetAllThenReturnListOfExistedUsers() throws RepositoryException {
        when(jdbcTemplate.query(eq("SELECT * FROM USERS"), any(BeanPropertyRowMapper.class)))
                .thenReturn(new ArrayList<>());

        assertTrue(repository.getAll().isEmpty());
    }

    @Test
    void whenUpdateUserThenReturnUpdatedUser() throws RepositoryException {
        doAnswer(invocation -> {
            ((PreparedStatementSetter) invocation.getArguments()[1]).setValues(statement);
            return null;
        }).when(jdbcTemplate).update(eq("UPDATE USERS SET PHONE_NUMBER= ?,NAME = ?,SECOND_NAME= ?," +
                "COUNTRY= ?,EMAIL_ADDRESS= ? WHERE USER_ID= ?"), any(PreparedStatementSetter.class));

        assertEquals(TEST_USER, repository.update(TEST_USER));
    }

    @Test
    void whenGetUserThenThrowRepositoryException() {
        when(jdbcTemplate.queryForObject(eq("SELECT * FROM USERS WHERE USER_ID = ?"), any(RowMapper.class),
                any(Object.class))).thenThrow(new DataAccessException("") {});

        assertThrows(RepositoryException.class, () -> repository.get(TEST_USER.getId()));
    }

    @Test
    void whenUpdateUserThenThrowRepositoryException() {
        when(jdbcTemplate.update(anyString(), any(PreparedStatementSetter.class)))
                .thenThrow(new DataAccessException("") {});

        assertThrows(RepositoryException.class, () -> repository.update(new User()));
    }

    @Test
    void whenCreateUserThenThrowRepositoryException() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        doThrow(new DataAccessException("") {})
                .when(jdbcTemplate).update(any(PreparedStatementCreator.class), any(KeyHolder.class));

        assertThrows(RepositoryException.class, () -> repository.create(TEST_USER));
    }

    @Test
    void whenGetAllUsersThenThrowRepositoryException() {
        doThrow(new DataAccessException("") {})
                .when(jdbcTemplate).query(anyString(), any(BeanPropertyRowMapper.class));

        assertThrows(RepositoryException.class, () -> repository.getAll());
    }

    @Test
    void whenDeleteUserThenThrowRepositoryException() {
        when(jdbcTemplate.update(eq("DELETE FROM USERS WHERE USER_ID = ?"), any(Object.class)))
                .thenThrow(new DataAccessException("") {
                });
        assertThrows(RepositoryException.class, () -> repository.delete(TEST_USER.getId()));
    }

}