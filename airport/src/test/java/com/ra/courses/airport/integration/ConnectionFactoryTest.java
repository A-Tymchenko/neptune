package com.ra.courses.airport.integration;

import com.ra.courses.airport.factory.ConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
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
    void whenGetConnectionNewConnectionShouldBeReturned() throws SQLException {
        Connection connection = connectionFactory.getConnection();
        assertNotNull(connection);
        assertFalse(connection.isClosed());
    }



}
