package com.ra.airport.integration;

import com.ra.airport.factory.ConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link ConnectionFactory} class
 */
public class ConnectionFactoryTest {

    private ConnectionFactory connectionFactory;

    @BeforeEach
    public void beforeTest () {
       connectionFactory = ConnectionFactory.getInstance();
    }

    /**
     * Check that returned {@link Connection} not null and closed
     */
    @Test
    public void whenGetConnectionNewConnectionShouldBeReturned() throws SQLException {
        Connection connection = connectionFactory.getConnection();
        assertNotNull(connection);
        assertFalse(connection.isClosed());
    }

    @Test
    public void whenConnectionFactoryGetInstanceCallTwiceTheSameInstanceShouldBeReturned() {
        ConnectionFactory firstInstance = ConnectionFactory.getInstance();
        ConnectionFactory secondInstance = ConnectionFactory.getInstance();
        assertTrue(firstInstance == secondInstance);
    }
}
