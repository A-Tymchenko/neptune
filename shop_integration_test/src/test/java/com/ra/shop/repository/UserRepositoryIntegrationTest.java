package com.ra.shop.repository;

import com.ra.shop.config.ShopConfiguration;
import com.ra.shop.exceptions.RepositoryException;
import com.ra.shop.model.User;
import com.ra.shop.repository.implementation.UserRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ShopConfiguration.class})
@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:create_table.sql")
@Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:drop_table.sql")
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepositoryImpl repository;

    private static final User TEST_USER = new User("3809978957860", "Pasha", "Vakula",
            "Poland", "vakula_2123@gmail.com");
    private static final User TEST_USER_UPDATE = new User("3809978957860", "Gugulya", "Zahrema",
            "Turkey", "vakula_2123@gmail.com");

    @Test
    void whenCreateUserThenReturnCreatedUser() throws RepositoryException {

        assertEquals(TEST_USER, repository.create(TEST_USER));
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:drop_table.sql")
    void whenCreateUserThenThrowRepositoryException() {
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            repository.create(TEST_USER);
        });
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    @Test
    void whenGetUserThenReturnCorrectUser() throws RepositoryException {

        assertEquals(TEST_USER, repository.create(TEST_USER));
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:drop_table.sql")
    void whenGetUserThenThrowRepositoryException() {
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            repository.get(1L);
        });
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    @Test
    void whenUpdateUserThenReturnUpdatedUser() throws RepositoryException {
        User created = repository.create(TEST_USER);
        TEST_USER_UPDATE.setId(created.getId());
        User updated = repository.update(TEST_USER_UPDATE);

        assertAll(() -> {
            assertEquals(TEST_USER_UPDATE.getName(), updated.getName());
            assertEquals(TEST_USER_UPDATE.getSecondName(), updated.getSecondName());
            assertEquals(TEST_USER_UPDATE.getCountry(), updated.getCountry());
        });
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:drop_table.sql")
    void whenUpdateUserThenThrowRepositoryException() {
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            repository.update(TEST_USER);
        });
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    @Test
    void whenDeleteUserThenReturnTrueOnSuccessfulExecution() throws RepositoryException {
        User created = repository.create(TEST_USER);

        assertTrue(repository.delete(created.getId()));
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:drop_table.sql")
    void whenDeleteUserThenThrowRepositoryException() {
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            repository.delete(1L);
        });
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    @Test
    void whenGetAllUsersThenReturnListOfUsers() throws RepositoryException {
        User[] users = getUsers();
        addAllUsersToDB(users);

        assertEquals(3, repository.getAll().size());
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:drop_table.sql")
    void whenGetAllUsersThenThrowRepositoryException() {
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            repository.getAll();
        });
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    private void addAllUsersToDB(User[] users) throws RepositoryException {
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
        };
    }

}
