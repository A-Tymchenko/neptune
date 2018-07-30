package com.ra.shop;

import com.ra.shop.config.ConnectionFactory;
import com.ra.shop.exceptions.RepositoryException;
import com.ra.shop.model.User;
import com.ra.shop.repository.implementation.UserRepositoryImpl;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.*;

import java.io.*;
import java.sql.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryIntegrationTest {

    private static final String CREATE_TABLE_USER = "src/test/resources/create_table.sql";
    private static final String DROP_TABLE_USER = "src/test/resources/drop_table.sql";
    private static ConnectionFactory factory;
    private static UserRepositoryImpl repository;
    private static Connection connection;

    @BeforeAll
    static void initGlobal() throws IOException, SQLException {
        factory = ConnectionFactory.getInstance();
        connection = factory.getConnection();
        repository = new UserRepositoryImpl(factory);
    }

    @BeforeEach
    void init() throws FileNotFoundException, SQLException {
        RunScript.execute(connection, new FileReader(CREATE_TABLE_USER));
    }

    @AfterEach
    void tearDown() throws FileNotFoundException, SQLException {
        dropTable(connection);
    }

    @AfterAll
    static void tearDownGlobal() {
        repository = null;
        connection = null;
        factory = null;
    }

    @Test
    void whenCreateUserThenReturnCreatedUser() throws RepositoryException {
        User user = new User("3809978957860", "Pasha", "Vakula",
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
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            dropTable(connection);
            repository.create(new User());
        });
        assertNotNull(repositoryException);
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    @Test
    void whenGetUserThenReturnOptionalOfUser() throws RepositoryException {
        User user = new User("3809934252275", "Pasha", "Volum",
                "Moscow", "pasha_213@gmail.com");
        User created = repository.create(user);
        System.out.println(created.getId());
        Optional<User> optional = repository.get(created.getId());
        assertNotNull(optional);
        assertTrue(optional.isPresent());
        assertEquals(user, optional.get());
    }

    @Test
    void whenGetUserWithNonExistingIdThenReturnOptionalEmpty() throws RepositoryException {
        Optional<User> optional = repository.get(getRandomId());
        assertNotNull(optional);
        assertFalse(optional.isPresent());
        assertEquals(Optional.empty(), optional);
    }

    @Test
    void whenDropOrdersTableAndGetOrderThenThrowRepositoryException() {
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            dropTable(connection);
            repository.get(getRandomId());
        });
        assertNotNull(repositoryException);
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    @Test
    void whenUpdateUserThenReturnUpdatedUser() throws RepositoryException {
        User user = new User("3806642341542", "Murchik", "Babulin",
                "USA", "murchik_21@gmail.com");
        repository.create(user);
        user.setName("Gugulya");
        user.setSecondName("Zahrema");
        user.setCountry("Turkey");
        User updated = repository.update(user);
        assertNotNull(updated);
        assertAll(() -> {
            assertEquals("Gugulya", user.getName());
            assertEquals("Zahrema", user.getSecondName());
            assertEquals("Turkey", user.getCountry());
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
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            dropTable(connection);
            repository.update(new User());
        });
        assertNotNull(repositoryException);
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    @Test
    void whenDeleteUserAndOperationIsSuccessfulThenReturnTrue() throws RepositoryException {
        User user = new User("3806754352134", "Taras", "Mazur",
                "Ukraine", "mazur_123@gmail.com");
        repository.create(user);
        Boolean isDeleted = repository.delete(user.getId());
        assertTrue(isDeleted);
        assertEquals(Optional.empty(), repository.get(user.getId()));
    }

    @Test
    void whenDeleteNonExistingUserAndOperationIsFailedThenReturnFalse() throws RepositoryException {
        Boolean isDeleted = repository.delete(getRandomId());
        assertFalse(isDeleted);
        assertEquals(Optional.empty(), repository.get(getRandomId()));
    }

    @Test
    void whenDropTableAndDeleteNonExistingUserThenThrowRepositoryException() {
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            dropTable(connection);
            repository.delete(getRandomId());
        });
        assertNotNull(repositoryException);
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    @Test
    void whenGetAllUsersThenReturnListOfExistingUsers() throws RepositoryException {
        User[] users = getUsers();
        List<User> expected = new ArrayList<>();
        Collections.addAll(expected, users);
        addUsersToDB(users);
        List<User> actual = repository.getAll();
        assertNotNull(actual);
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    void whenNoUsersWereCreatedThenReturnCollectionsEmptyList() throws RepositoryException {
        List<User> users = repository.getAll();
        assertTrue(users.isEmpty());
        assertEquals(Collections.emptyList(), users);
    }

    @Test
    void whenDropUsersTableAndCallGetAllMethodThenThrowRepositoryException() {
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            dropTable(connection);
            repository.getAll();
        });
        assertNotNull(repositoryException);
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    private void addUsersToDB(User[] users) throws RepositoryException {
        for (int i = 0; i < users.length; i++) {
            repository.create(users[i]);
        }
    }

    private User[] getUsers() {
        return new User[]{
                new User("3806734536743", "Adolf", "Hitlerl",
                        "German", "adolfyk_1945@gmail.com"),
                new User("3809942434543", "Joseph", "Stalin",
                        "Soviet Union", "joseph_1941@gmail.com"),
                new User("3809923153421", "Taras", "Bulba",
                        "Ukraine", "bulba_100500@gmail.com"),
                new User("3809765435266", "Taras ", "Shevchenko",
                        "Ukraine", "taras_13@gmail.com"),
                new User("3806675474848", "Vladimir", "Lenin",
                        "Soviet Union", "vladimir_1939@gmail.com")
        };
    }

    public void dropTable(final Connection connection) throws FileNotFoundException, SQLException {
        RunScript.execute(connection, new FileReader(DROP_TABLE_USER));
    }

    private Long getRandomId() {
        return 654L;
    }

}
