package com.ra.courses.airport.integration;

import com.ra.courses.airport.factory.ConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests for {@link ConnectionFactory} class
 */
public class ConnectionFactoryTest {

    private ConnectionFactory connectionFactory;

    @BeforeEach
    void beforeTest () {
       connectionFactory = ConnectionFactory.getInstance();
    }

    /**
     * Check that returned {@link Connection} not null and closed
     */
    @Test
    void whenGetConnectionNewConnectionShouldBeReturned() throws SQLException {
        Connection connection = connectionFactory.getConnection();
        assertNotNull(connection);
        assertFalse(connection.isClosed());
    }
}
