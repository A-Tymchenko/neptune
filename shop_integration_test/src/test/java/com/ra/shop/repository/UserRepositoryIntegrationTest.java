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
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

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

    @Test
    void whenCreateUserThenReturnCreatedUser() throws RepositoryException {
        User user = new User("3809978957860", "Pasha", "Vakula",
                "Poland", "vakula_2123@gmail.com");
        User created = repository.create(user);
        assertNotNull(created);
        assertEquals(user, created);
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:drop_table.sql")
    void whenCreateUserThenThrowRepositoryException() {
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            repository.create(new User("3809934252275", "Pasha", "Volum",
                    "Moscow", "pasha_213@gmail.com"));
        });
        assertNotNull(repositoryException);
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    @Test
    void whenGetUserThenReturnOptionalOfUser() throws RepositoryException {
        User user = new User("3809934252275", "Pashink", "Lum",
                "Moscow", "Lum_2@gmail.com");
        User created = repository.create(user);
        User found = repository.get(created.getId());
        assertNotNull(found);
        assertEquals(user, created);
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:drop_table.sql")
    void whenGetUserThenThrowRepositoryException() {
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            repository.get(1L);
        });
        assertNotNull(repositoryException);
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    @Test
    void whenUpdateUserThenReturnUpdatedUser() throws RepositoryException {
        User user = new User("3809978957860", "Pas", "Lank",
                "Poland", "Lank_3@gmail.com");
        User created = repository.create(user);
        user.setName("Gugulya");
        user.setSecondName("Zahrema");
        user.setCountry("Turkey");
        User updated = repository.update(user);
        assertNotNull(updated);
        assertAll(() -> {
            assertEquals(created.getName(), updated.getName());
            assertEquals(created.getSecondName(), updated.getSecondName());
            assertEquals(created.getCountry(), updated.getCountry());
        });
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:drop_table.sql")
    void whenUpdateUserThenThrowRepositoryException() {
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            repository.update(new User("3806754352134", "Tas", "Zur",
                    "Ukraine", "Zur_123@gmail.com"));
        });
        assertNotNull(repositoryException);
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    @Test
    void whenDeleteUserThenReturnTrueOnSuccessfulExecution() throws RepositoryException {
        User user = new User("3806754352134", "Taras", "Mazur",
                "Ukraine", "mazur_123@gmail.com");
        User created = repository.create(user);
        boolean isDeleted = repository.delete(created.getId());
        assertTrue(isDeleted);
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:drop_table.sql")
    void whenDeleteUserThenThrowRepositoryException() {
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            repository.delete(1L);
        });
        assertNotNull(repositoryException);
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    @Test
    void whenGetAllUsersThenReturnListOfUsers() throws RepositoryException {
        User[] users = getUsers();
        addAllUsersToDB(users);
        List<User> actual = repository.getAll();
        assertFalse(actual.isEmpty());
        assertEquals(3, actual.size());
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:drop_table.sql")
    void whenGetAllUsersThenThrowRepositoryException() {
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            repository.getAll();
        });
        assertNotNull(repositoryException);
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
