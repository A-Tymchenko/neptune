package com.ra.courses.airport.integration;

import com.ra.courses.airport.factory.ConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Author: anbo
 * Date: 18.06.2018
 */
public class ConnectionFactoryTest {

    private static ConnectionFactory connectionFactory;

    @BeforeEach
    void beforeTest () {
       connectionFactory = ConnectionFactory.getInstance();
    }

    @Test
    void whenGetConnectionNewConnectionShouldBeReturned()  {
        System.out.println("connectionFactory "+connectionFactory);
        Connection connection = connectionFactory.getConnection();
        assertNotNull(connection);
    }



}
