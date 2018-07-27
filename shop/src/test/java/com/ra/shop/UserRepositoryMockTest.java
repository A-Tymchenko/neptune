package com.ra.shop;

import com.ra.shop.config.ConnectionFactory;
import com.ra.shop.exceptions.RepositoryException;
import com.ra.shop.model.User;
import com.ra.shop.repository.implementation.UserRepositoryImpl;
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

public class UserRepositoryMockTest {

    private static ConnectionFactory factory;
    private static UserRepositoryImpl mockUserRepository;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;

    @BeforeAll
    static void initGlobal() {
        factory = mock(ConnectionFactory.class);
        mockUserRepository = new UserRepositoryImpl(factory);
    }

    @BeforeEach
    void init() throws SQLException {
        connection = mock(Connection.class);
        statement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        when(factory.getConnection()).thenReturn(connection);
    }

    @Test
    void whenCreateUserThenReturnCreatedUser() throws SQLException, RepositoryException {
        User user = new User("3806734536743", "Adolf", "Hitlerl",
                "German", "adolfyk_1945@gmail.com");
        when(connection.prepareStatement("INSERT INTO USERS (PHONE_NUMBER, NAME, SECOND_NAME, COUNTRY, EMAIL_ADDRESS) "
                        + "VALUES(?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)).thenReturn(statement);
        when(statement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(Boolean.TRUE).thenReturn(Boolean.FALSE);
        User newUser = mockUserRepository.create(user);
        assertEquals(user, newUser);
    }

    @Test
    void whenCreateUserThenReturnUserWithoutId() throws SQLException, RepositoryException {
        User user = new User("3806734536743", "Adolf", "Hitlerl",
                "German", "adolfyk_1945@gmail.com");
        when(connection.prepareStatement("INSERT INTO USERS (PHONE_NUMBER, NAME, SECOND_NAME, COUNTRY, EMAIL_ADDRESS) "
                        + "VALUES(?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)).thenReturn(statement);
        when(statement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(Boolean.FALSE);
        User newUser = mockUserRepository.create(user);
        assertNull(newUser.getId());
    }

    @Test
    void whenCreateUserThenThrowRepositoryException() throws SQLException {
        User user = new User("3806734536743", "Adolf", "Hitlerl",
                "German", "adolfyk_1945@gmail.com");
        when(connection.prepareStatement("INSERT INTO USERS (PHONE_NUMBER, NAME, SECOND_NAME, COUNTRY, EMAIL_ADDRESS) "
                        + "VALUES(?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)).thenThrow(new SQLException());
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            mockUserRepository.create(user);
        });
        assertNotNull(repositoryException);
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    @Test
    void whenGetUserThenRetrnOptionalOfUser() throws SQLException, RepositoryException {
        User user = new User("3806734536743", "Adolf", "Hitlerl",
                "German", "adolfyk_1945@gmail.com");
        user.setId(2L);
        when(connection.prepareStatement("SELECT * FROM USERS WHERE USER_ID = ?")).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(Boolean.TRUE).thenReturn(Boolean.FALSE);
        Optional<User> optional = mockUserRepository.get(user.getId());
        assertTrue(optional.isPresent());
    }

    @Test
    void whenGetUserThenReturnOptionalEmpty() throws RepositoryException, SQLException {
        User user = new User("3806734536743", "Adolf", "Hitlerl",
                "German", "adolfyk_1945@gmail.com");
        user.setId(2L);
        when(connection.prepareStatement("SELECT * FROM USERS WHERE USER_ID = ?")).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(Boolean.FALSE);
        Optional<User> optional = mockUserRepository.get(user.getId());
        assertFalse(optional.isPresent());
        assertEquals(Optional.empty(), optional);
    }

    @Test
    void whenGetUserThenThrowRepositoryException() throws SQLException {
        User user = new User("3806734536743", "Adolf", "Hitlerl",
                "German", "adolfyk_1945@gmail.com");
        user.setId(1L);
        when(connection.prepareStatement("SELECT * FROM USERS WHERE USER_ID = ?")).thenThrow(new SQLException());
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            mockUserRepository.get(user.getId());
        });
        assertNotNull(repositoryException);
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    @Test
    void whenUpdateUserThenReturnUpdatedUser() throws SQLException, RepositoryException {
        User user = new User("3806734536743", "Adolf", "Hitlerl",
                "German", "adolfyk_1945@gmail.com");
        user.setId(1L);
        user.setName("German");
        user.setSecondName("Scheider");
        when(connection.prepareStatement("UPDATE USERS SET PHONE_NUMBER= ?,NAME = ?,SECOND_NAME= ?,COUNTRY= ?,EMAIL_ADDRESS= ? WHERE USER_ID= ?"))
                .thenReturn(statement);
        User updated = mockUserRepository.update(user);
        assertNotNull(updated);
        assertAll(() -> {
            assertEquals(user.getName(), updated.getName());
            assertEquals(user.getSecondName(), updated.getSecondName());
        });
    }

    @Test
    void whenUpdateUserThenThrowRepositoryException() throws SQLException {
        User user = new User("3806734536743", "Adolf", "Hitlerl",
                "German", "adolfyk_1945@gmail.com");
        when(connection.prepareStatement("UPDATE USERS SET PHONE_NUMBER= ?,NAME = ?,SECOND_NAME= ?,COUNTRY= ?,EMAIL_ADDRESS= ? WHERE USER_ID= ?"))
                .thenThrow(new SQLException());
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            mockUserRepository.update(user);
        });
        assertNotNull(repositoryException);
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    @Test
    void whenDeleteUserThenReturnTrue() throws SQLException, RepositoryException {
        User user = new User("3806734536743", "Adolf", "Hitlerl",
                "German", "adolfyk_1945@gmail.com");
        user.setId(5L);
        when(connection.prepareStatement("DELETE FROM USERS WHERE USER_ID = ?")).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);
        Boolean isDeleted = mockUserRepository.delete(user.getId());
        assertTrue(isDeleted);
    }

    @Test
    void whenDeleteUserThenReturnFalse() throws SQLException, RepositoryException {
        User user = new User("3806734536743", "Adolf", "Hitlerl",
                "German", "adolfyk_1945@gmail.com");
        user.setId(5L);
        when(connection.prepareStatement("DELETE FROM USERS WHERE USER_ID = ?")).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(0);
        Boolean isNotDeleted = mockUserRepository.delete(user.getId());
        assertFalse(isNotDeleted);
    }

    @Test
    void whenDeleteeUserThenThrowRepositoryException() throws SQLException {
        User user = new User("3806734536743", "Adolf", "Hitlerl",
                "German", "adolfyk_1945@gmail.com");
        user.setId(10L);
        when(connection.prepareStatement("DELETE FROM USERS WHERE USER_ID = ?"))
                .thenThrow(new SQLException());
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            mockUserRepository.delete(user.getId());
        });
        assertNotNull(repositoryException);
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    @Test
    void getAllOrdersAndReturnListOfORders() throws SQLException, RepositoryException {
        when(connection.prepareStatement("SELECT * FROM USERS")).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        List<User> actual = mockUserRepository.getAll();
        assertFalse(actual.isEmpty());
        assertEquals(3, actual.size());
    }

    @Test
    void whenGetAllOrdersThenReturnEmptyList() throws SQLException, RepositoryException {
        when(connection.prepareStatement("SELECT * FROM USERS")).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(Boolean.FALSE);
        List<User> actual = mockUserRepository.getAll();
        assertTrue(actual.isEmpty());
        assertEquals(0, actual.size());
        assertEquals(Collections.emptyList(), actual);
    }

    @Test
    void whenGetAllOrdersThenThrowRepositoryException() throws SQLException {
        when(connection.prepareStatement("SELECT * FROM USERS")).thenThrow(new SQLException());
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            mockUserRepository.getAll();
        });
        assertNotNull(repositoryException);
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

}
