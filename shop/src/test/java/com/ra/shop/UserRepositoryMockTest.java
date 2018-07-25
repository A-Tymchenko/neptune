package com.ra.shop;

import com.ra.shop.exceptions.UserException;
import com.ra.shop.model.User;
import com.ra.shop.repository.implementation.UserRepositoryImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserRepositoryMockTest {

    private static UserRepositoryImpl mockUserRepository;

    @BeforeAll
    static void initGlobal() {
        mockUserRepository = mock(UserRepositoryImpl.class);
    }

    @Test
    void whenCreateUserThenReturnCreatedUser() throws UserException {
        User user = new User(2L, "3809934252275", "Pasha", "Volum",
                "Moscow", "pasha_213@gmail.com");
        when(mockUserRepository.create(user)).thenReturn(user);
        User created = mockUserRepository.create(user);
        assertEquals(user, created);
    }

    @Test
    void whenGetUserThenReturnOptionalOfUser() throws UserException{
        User user = new User(3L, "3806642341542", "Murchik", "Babulin",
                "USA", "murchik_21@gmail.com");
        when(mockUserRepository.get(user.getId())).thenReturn(Optional.of(user));
        Optional<User> optional = mockUserRepository.get(user.getId());
        assertTrue(optional.isPresent());
        assertEquals(user, optional.get());
    }

    @Test
    void whenUpdateUserThenReturnUpdatedUser() throws UserException {
        User user = new User(4L, "3806754352134", "Taras", "Mazur",
                "Ukraine", "mazur_123@gmail.com");
        user.setName("Narhayer");
        when(mockUserRepository.update(user)).thenReturn(user);
        User updated = mockUserRepository.update(user);
        assertEquals(user, updated);
        assertEquals(user.getName(), updated.getName());
    }

    @Test
    void whenDeleteUserIsSuccessfulThenReturnTrue() throws UserException {
        User user = new User(8L, "3809765435266", "Taras ", "Shevchenko",
                "Ukraine", "taras_13@gmail.com");
        when(mockUserRepository.delete(user.getId())).thenReturn(Boolean.TRUE);
        Boolean isRowDeleted = mockUserRepository.delete(user.getId());
        assertTrue(isRowDeleted);
    }

    @Test
    void whenGetAllUsersThenReturnListOfUsers() throws UserException {
        User[] users = getUsers();
        List<User> expected = new ArrayList<>();
        Collections.addAll(expected, users);
        when(mockUserRepository.getAll()).thenReturn(expected);
        List<User> actual = mockUserRepository.getAll();
        assertEquals(expected.size(), actual.size());
    }

    private User[] getUsers() {
        return new User[]{
                new User(5L, "3806734536743", "Adolf", "Hitlerl",
                        "German", "adolfyk_1945@gmail.com"),
                new User(6L, "3809942434543", "Joseph", "Stalin",
                        "Soviet Union", "joseph_1941@gmail.com"),
                new User(9L, "3806675474848", "Vladimir", "Lenin",
                        "Soviet Union", "vladimir_1939@gmail.com")
        };
    }
}
