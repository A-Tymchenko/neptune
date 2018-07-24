package com.ra.project.repository;

import com.ra.project.configuration.ConnectionFactory;
import com.ra.project.exceptions.UserException;
import com.ra.project.model.User;
import com.ra.project.repository.implementation.UserRepositoryImpl;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryIntegrationTest {

    private static ConnectionFactory factory;
    private static UserRepositoryImpl repository;
    private Connection connection;

    @BeforeAll
    static void initGlobal() throws IOException {
        factory = ConnectionFactory.getInstance();
        repository = new UserRepositoryImpl(factory);
    }

    @BeforeEach
    void init() throws SQLException, FileNotFoundException {
        connection = factory.getConnection();
        RunScript.execute(connection, new FileReader(".//src//main//resources//createUsersTable.sql"));
    }

    @AfterEach
    void tearDown() throws FileNotFoundException, SQLException {
        RunScript.execute(connection, new FileReader(".//src//main//resources//dropUsersTable.sql"));
    }

    @AfterAll
    static void tearDownGlobal() {
        repository = null;
        factory = null;
    }

    @Test
    void whenCreateUserThenReturnCreatedUser() throws UserException {
        User user = new User(1L, "3809978957860", "Pasha", "Vakula",
                "Poland", "vakula_2123@gmail.com");
        User created = repository.create(user);
        assertNotNull(created);
        assertEquals(user, created);
    }

    @Test
    void whenCreateNullUserThenThrowNullPointerException() {
        Throwable nullPointerException = assertThrows(NullPointerException.class, () -> {
            repository.create(null);
        });
        assertNotNull(nullPointerException);
        assertEquals(NullPointerException.class, nullPointerException.getClass());
    }

    @Test
    void whenUserCreationFailsThenThrowRepositoryException() {
        Throwable repositoryException = assertThrows(UserException.class, () -> {
            dropTable();
            repository.create(new User());
        });
        assertNotNull(repositoryException);
        assertEquals(UserException.class, repositoryException.getClass());
    }

    @Test
    void whenGetUserThenReturnOptionalOfUser() throws UserException {
        User user = new User(2L, "3809934252275", "Pasha", "Volum",
                "Moscow", "pasha_213@gmail.com");
        repository.create(user);
        Optional<User> optional = repository.get(user.getId());
        assertNotNull(optional);
        assertTrue(optional.isPresent());
        assertEquals(user, optional.get());
    }

    @Test
    void whenGetUserWithNonExistingIdThenReturnOptionalEmpty() throws UserException {
        Optional<User> optional = repository.get(getRandomId());
        assertNotNull(optional);
        assertFalse(optional.isPresent());
        assertEquals(Optional.empty(), optional);
    }

    @Test
    void whenGetUserWithNullIdThenThrowNullPointerException() {
        Throwable nullPointerException = assertThrows(NullPointerException.class, () -> {
            repository.get(null);
        });
        assertNotNull(nullPointerException);
        assertEquals(NullPointerException.class, nullPointerException.getClass());
    }

    @Test
    void whenDropUsersTableAndGetUserThenThrowRepositoryException() {
        Throwable repositoryException = assertThrows(UserException.class, () -> {
            dropTable();
            repository.get(getRandomId());
        });
        assertNotNull(repositoryException);
        assertEquals(UserException.class, repositoryException.getClass());
    }

    @Test
    void whenUpdateUserThenReturnUpdatedUser() throws UserException {
        User user = new User(3L, "3806642341542", "Murchik", "Babulin",
                "USA", "murchik_21@gmail.com");
        repository.create(user);
        user.setName("Gala");
        user.setSecondName("Dabush");
        user.setCountry("Ukraine");
        User updated = repository.update(user);
        assertNotNull(updated);
        assertAll(() -> {
            assertEquals("Gala", user.getName());
            assertEquals("Dabush", user.getSecondName());
            assertEquals("Ukraine", user.getCountry());
        });
    }

    @Test
    void whenUpdateNullUserThenThrowNullPointerException() {
        Throwable nullPointer = assertThrows(NullPointerException.class, () -> {
            repository.update(null);
        });
        assertNotNull(nullPointer);
        assertEquals(NullPointerException.class, nullPointer.getClass());
    }

    @Test
    void whenDropUsersTableAndUpdateNotExistingUserThenThrowRepositoryException() {
        Throwable repositoryException = assertThrows(UserException.class, () -> {
            dropTable();
            repository.update(new User());
        });
        assertNotNull(repositoryException);
        assertEquals(UserException.class, repositoryException.getClass());
    }

    @Test
    void whenDeleteUserAndOperationIsSuccessfulThenReturnTrue() throws UserException {
        User user = new User(4L, "3806754352134", "Taras", "Mazur",
                "Ukraine", "mazur_123@gmail.com");
        repository.create(user);
        Boolean isDeleted = repository.delete(user.getId());
        assertTrue(isDeleted);
        assertEquals(Optional.empty(), repository.get(user.getId()));
    }

    @Test
    void whenDeleteNonExistingUserAndOperationIsFailedThenReturnFalse() throws UserException {
        Boolean isDeleted = repository.delete(getRandomId());
        assertFalse(isDeleted);
        assertEquals(Optional.empty(), repository.get(getRandomId()));
    }

    @Test
    void whenDeleteUserWithNullIdThenThrowNullPointerException() {
        Throwable nullPointer = assertThrows(NullPointerException.class, () -> {
            repository.delete(null);
        });
        assertNotNull(nullPointer);
        assertEquals(NullPointerException.class, nullPointer.getClass());
    }

    @Test
    void whenDropTableAndDeleteNonExistingUserThenThrowRepositoryException() {
        Throwable repositoryException = assertThrows(UserException.class, () -> {
            dropTable();
            repository.delete(getRandomId());
        });
        assertNotNull(repositoryException);
        assertEquals(UserException.class, repositoryException.getClass());
    }

    @Test
    void whenGetAllUsersThenReturnListOfExistingUsers() throws UserException {
        User[] users = getUsers();
        List<User> expected = new ArrayList<>();
        Collections.addAll(expected, users);
        addUsersToDB(users);
        List<User> actual = repository.getAll();
        assertNotNull(actual);
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    void whenNoUsersWereCreatedThenReturnCollectionsEmptyList() throws UserException {
        List<User> users = repository.getAll();
        assertTrue(users.isEmpty());
        assertEquals(Collections.emptyList(), users);
    }

    @Test
    void whenDropUsersTableAndCallGetAllMethodThenThrowRepositoryException() {
        Throwable repositoryException = assertThrows(UserException.class, () -> {
            dropTable();
            repository.getAll();
        });
        assertNotNull(repositoryException);
        assertEquals(UserException.class, repositoryException.getClass());
    }

    private void addUsersToDB(User[] users) throws UserException {
        for (int i = 0; i < users.length; i++) {
            repository.create(users[i]);
        }
    }

    private User[] getUsers() {
        return new User[] {
                new User(5L, "3806734536743", "Adolf", "Hitlerl",
                        "German", "adolfyk_1945@gmail.com"),
                new User(6L, "3809942434543", "Joseph", "Stalin",
                        "Soviet Union", "joseph_1941@gmail.com"),
                new User(7L, "3809923153421", "Taras", "Bulba",
                        "Ukraine", "bulba_100500@gmail.com"),
                new User(8L, "3809765435266", "Taras ", "Shevchenko",
                        "Ukraine", "taras_13@gmail.com"),
                new User(9L, "3806675474848", "Vladimir", "Lenin",
                        "Soviet Union", "vladimir_1939@gmail.com")
        };
    }

    private Long getRandomId() {
        return 123L;
    }

    private void dropTable() throws SQLException, FileNotFoundException {
        RunScript.execute(connection, new FileReader(".//src//main//resources//dropUsersTable.sql"));
    }

}
