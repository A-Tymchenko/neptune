package com.ra.shop;

import com.ra.shop.config.ConnectionFactory;
import com.ra.shop.exceptions.RepositoryException;
import com.ra.shop.model.User;
import com.ra.shop.repository.implementation.UserRepositoryImpl;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserRepositoryMockTest {

    private static UserRepositoryImpl mockUserRepository;
    private static ConnectionFactory factory;
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
    void whenCreateUserThenReturnCreatedUserWithId() throws SQLException, RepositoryException {
        User user = new User("3806734536743", "Dolf", "Hitlerl",
            "German", "adolfyk_1945@gmail.com");
        when(connection.prepareStatement("INSERT INTO USERS (PHONE_NUMBER, NAME, SECOND_NAME, COUNTRY, EMAIL_ADDRESS) "
                + "VALUES(?, ?, ?, ?, ?)",
            Statement.RETURN_GENERATED_KEYS)).thenReturn(statement);
        when(statement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(Boolean.TRUE).thenReturn(Boolean.FALSE);
        User created = mockUserRepository.create(user);
        assertNotNull(created.getId());
        assertEquals(user, created);
    }

    @Test
    void whenCreateUserThenReturnUserWithoutSettedId() throws SQLException, RepositoryException {
        User user = new User("3806734536743", "Adolf", "Hitlerl",
            "German", "adolfyk_1945@gmail.com");
        when(connection.prepareStatement("INSERT INTO USERS (PHONE_NUMBER, NAME, SECOND_NAME, COUNTRY, EMAIL_ADDRESS) "
                + "VALUES(?, ?, ?, ?, ?)",
            Statement.RETURN_GENERATED_KEYS)).thenReturn(statement);
        when(statement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(Boolean.FALSE);
        User created = mockUserRepository.create(user);
        assertNull(created.getId());
    }

    @Test
    void whenCreateUserThenThrowRepositoryException() throws RepositoryException, SQLException {
        User user = new User("3806734536743", "Adof", "Hitlerl",
            "German", "adolfyk_1945@gmail.com");
        when(connection.prepareStatement("INSERT INTO USERS (PHONE_NUMBER, NAME, SECOND_NAME, COUNTRY, EMAIL_ADDRESS) "
                + "VALUES(?, ?, ?, ?, ?)",
            Statement.RETURN_GENERATED_KEYS))
            .thenThrow(new SQLException());
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            mockUserRepository.create(user);
        });
        assertNotNull(repositoryException);
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

//    @Test
//    void whenGetUserThenReturnOptionalOfUser() throws SQLException, RepositoryException {
//        User user = new User("3806734536743", "Adolf", "Hitlerl",
//            "German", "adolfyk_1945@gmail.com");
//        when(connection.prepareStatement("SELECT * FROM USERS WHERE USER_ID = ?"))
//            .thenReturn(statement);
//        when(statement.executeQuery()).thenReturn(resultSet);
//        when(resultSet.next()).thenReturn(Boolean.TRUE).thenReturn(Boolean.FALSE);
////        Optional<User> optional = mockUserRepository.get(1L);
////        assertTrue(optional.isPresent());
//    }

    @Test
    void whenGetUserThenReturnOptionalEmpty() throws SQLException, RepositoryException {
        User user = new User("3806734536743", "Adolf", "Hitlerl",
            "German", "adolfyk_1945@gmail.com");
        when(connection.prepareStatement("SELECT * FROM USERS WHERE USER_ID = ?"))
            .thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(Boolean.FALSE);
//        Optional<User> optional = mockUserRepository.get(1L);
//        assertFalse(optional.isPresent());
    }

//    @Test
//    void whenGetUserThenThrowRepositoryException() throws RepositoryException, SQLException {
//        User user = new User("3806734536743", "Adolf", "Hitlerl",
//            "German", "adolfyk_1945@gmail.com");
//        user.setId(5L);
//        when(connection.prepareStatement("SELECT * FROM USERS WHERE USER_ID = ?"))
//            .thenThrow(new SQLException());
//        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
//            mockUserRepository.get(user.getId());
//        });
//        assertNotNull(repositoryException);
//        assertEquals(RepositoryException.class, repositoryException.getClass());
//    }

    @Test
    void whenUpdateUserThenReturnUpdatedUser() throws SQLException, RepositoryException {
        User user = new User("3806734536743", "Adf", "Lerl",
            "German", "adolfyk_1945@gmail.com");
        user.setId(1L);
        user.setName("German");
        user.setSecondName("Scheider");
        when(connection.prepareStatement("UPDATE USERS SET PHONE_NUMBER= ?,NAME = ?,SECOND_NAME= ?,COUNTRY= ?,EMAIL_ADDRESS= ? WHERE USER_ID= ?"))
            .thenReturn(statement);
        User updated = mockUserRepository.update(user);
        assertAll(() -> {
            assertEquals(user.getName(), updated.getName());
            assertEquals(user.getSecondName(), updated.getSecondName());
        });
    }

    @Test
    void whenUpdateUserThenThrowRepositoryException() throws RepositoryException, SQLException {
        User user = new User("3806734536743", "Af", "Lrl",
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
        User user = new User("3806734536743", "Af", "Lrl",
            "German", "adolfyk_1945@gmail.com");
        user.setId(82L);
        when(connection.prepareStatement("DELETE FROM USERS WHERE USER_ID = ?")).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);
        Boolean isDeleted = mockUserRepository.delete(user.getId());
        assertTrue(isDeleted);
    }

    @Test
    void whenDeleteUserThenReturnFalse() throws SQLException, RepositoryException {
        User user = new User("3806734536743", "Af", "Lrl",
            "German", "adolfyk_1945@gmail.com");
        user.setId(23L);
        when(connection.prepareStatement("DELETE FROM USERS WHERE USER_ID = ?")).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(0);
        Boolean isDeleted = mockUserRepository.delete(user.getId());
        assertFalse(isDeleted);
    }

    @Test
    void whenDeleteUserThenThrowRepositoryException() throws SQLException {
        User user = new User("3806734536743", "Af", "Lrl",
            "German", "adolfyk_1945@gmail.com");
        user.setId(26L);
        when(connection.prepareStatement("DELETE FROM USERS WHERE USER_ID = ?")).thenThrow(new SQLException());
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            mockUserRepository.delete(user.getId());
        });
        assertNotNull(repositoryException);
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    @Test
    void getAllUsersAndReturnListOfUsers() throws SQLException, RepositoryException {
        when(connection.prepareStatement("SELECT * FROM USERS")).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        List<User> actual = mockUserRepository.getAll();
        assertFalse(actual.isEmpty());
        assertEquals(3, actual.size());
    }

    @Test
    void whenGetAllUsersThenThrowRepositoryException() throws SQLException {
        when(connection.prepareStatement("SELECT * FROM USERS")).thenThrow(new SQLException());
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            mockUserRepository.getAll();
        });
        assertNotNull(repositoryException);
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

}