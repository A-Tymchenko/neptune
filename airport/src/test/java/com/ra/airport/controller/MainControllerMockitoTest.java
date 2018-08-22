package com.ra.airport.controller;

import com.ra.airport.repository.exception.AirPortDaoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainControllerMockitoTest {

    MainController mainController;
    String pathToJsp;

    @BeforeEach
    public void init() throws AirPortDaoException {
        MockitoAnnotations.initMocks(this);
        mainController = new MainController();
        pathToJsp = "/index";
    }

    @Test
    public void whenCallIndexThenReturnPathToJsp() {
        assertEquals(mainController.index(), pathToJsp);
    }
}
