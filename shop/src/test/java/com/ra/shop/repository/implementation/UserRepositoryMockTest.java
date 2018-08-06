package com.ra.shop.repository.implementation;

import com.ra.shop.exceptions.RepositoryException;
import com.ra.shop.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        User user = new User( "3809934252275", "Pasha", "Volum",
                "Moscow", "pasha_213@gmail.com");
        when(connection.prepareStatement(
                eq("INSERT INTO USERS (PHONE_NUMBER, NAME, SECOND_NAME, COUNTRY, EMAIL_ADDRESS) "
                        + "VALUES(?, ?, ?, ?, ?)"))).thenReturn(statement);
        doAnswer(invocation -> {
            ((PreparedStatementCreator) invocation.getArguments()[0]).createPreparedStatement(connection);
            return null;
        }).when(jdbcTemplate).update(any(PreparedStatementCreator.class), any(KeyHolder.class));
        when(keyHolder.getKey()).thenReturn(1L);
        User created = repository.create(user);
        user.setId((long) keyHolder.getKey());
        assertEquals(user, created);
    }

    @Test
    void whenGetUserThenReturnCorrectEntity() throws RepositoryException {
        User user = new User( "3806642341542", "Murchik", "Babulin",
                "USA", "murchik_21@gmail.com");
        user.setId(1L);
        when(jdbcTemplate.queryForObject(eq("SELECT * FROM USERS WHERE USER_ID = ?"), any(RowMapper.class),
                        any(Object.class))).thenReturn(user);
        User found = repository.get(user.getId());
        assertEquals(user, found);
    }

    @Test
    void whenDeleteUserThenReturnSuccessfulQueryExecutionResultTrue() throws RepositoryException {
        User user = new User( "3806642341542", "Murik", "Balin",
                "USA", "urchik_457@gmail.com");
        user.setId(5L);
        when(jdbcTemplate.update(eq("DELETE FROM USERS WHERE USER_ID = ?"), any(Object.class))).thenReturn(1);
        boolean isDeleted = repository.delete(user.getId());
        assertTrue(isDeleted);
    }

    @Test
    void whenDeleteUserThenReturnUnsuccessfulQueryExecutionResultFalse() throws RepositoryException {
        User user = new User( "3809765435266", "Taras ", "Shevchenko",
                "Ukraine", "taras_13@gmail.com");
        user.setId(6L);
        when(jdbcTemplate.update(eq("DELETE FROM USERS WHERE USER_ID = ?"), any(Object.class))).thenReturn(0);
        boolean isDeleted = repository.delete(user.getId());
        assertFalse(isDeleted);
    }

    @Test
    void whenGetAllThenReturnListOfExistedUsers() throws RepositoryException {
        List<User> users = new ArrayList<>();
        when(jdbcTemplate.query(eq("SELECT * FROM USERS"), any(BeanPropertyRowMapper.class))).thenReturn(users);
        List<User> actual = repository.getAll();
        assertEquals(0, actual.size());
    }

    @Test
    void whenUpdateUserThenReturnUpdatedUser() throws RepositoryException {
        User user = new User("3809934252275", "Pasha", "Volum",
                "Moscow", "pasha_213@gmail.com");
        user.setId(1L);
        doAnswer(invocation -> {((PreparedStatementSetter) invocation.getArguments()[1]).setValues(statement);
            return null;
        }).when(jdbcTemplate).update(eq("UPDATE USERS SET PHONE_NUMBER= ?,NAME = ?,SECOND_NAME= ?," +
                "COUNTRY= ?,EMAIL_ADDRESS= ? WHERE USER_ID= ?"), any(PreparedStatementSetter.class));
        User updated = repository.update(user);
        assertEquals(user, updated);
    }

    @Test
    void whenGetUserThenThrowRepositoryException() {
        User user = new User("3809934252275", "Pasha", "Volum",
                "Moscow", "pasha_213@gmail.com");
        user.setId(1L);
        when(jdbcTemplate.queryForObject(eq("SELECT * FROM USERS WHERE USER_ID = ?"), any(RowMapper.class),
                        any(Object.class))).thenThrow(new DataAccessException(""){});
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            repository.get(user.getId());
        });
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    @Test
    void whenUpdateUserThenThrowRepositoryException() {
        when(jdbcTemplate.update(anyString(), any(PreparedStatementSetter.class))).thenThrow(new DataAccessException(""){});
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            repository.update(new User());
        });
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    @Test
    void whenCreateUserThenThrowRepositoryException() throws SQLException {
        User user = new User("3806754352134", "Taras", "Mazur",
                "Ukraine", "mazur_123@gmail.com");
        when(connection.prepareStatement(
                eq("INSERT INTO USERS (PHONE_NUMBER, NAME, SECOND_NAME, COUNTRY, EMAIL_ADDRESS) "
                        + "VALUES(?, ?, ?, ?, ?)"))).thenReturn(statement);
        doThrow(new DataAccessException(""){})
                .when(jdbcTemplate).update(any(PreparedStatementCreator.class), any(KeyHolder.class));
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            repository.create(user);
        });
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    @Test
    void whenGetAllUsersThenThrowRepositoryException() {
        doThrow(new DataAccessException(""){})
                .when(jdbcTemplate).query(eq("SELECT * FROM USERS"), any(BeanPropertyRowMapper.class));
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            repository.getAll();
        });
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    @Test
    void whenDeleteUserThenThrowRepositoryException() {
        User user = new User("3809934252275", "Pasha", "Volum",
                "Moscow", "pasha_213@gmail.com");
        user.setId(1L);
        when(jdbcTemplate.update(eq("DELETE FROM USERS WHERE USER_ID = ?"), any(Object.class)))
                .thenThrow(new DataAccessException(""){});
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            repository.delete(user.getId());
        });
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

}