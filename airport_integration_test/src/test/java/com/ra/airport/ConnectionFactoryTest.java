package com.ra.airport;

import com.ra.airport.factory.ConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link ConnectionFactory} class
 */
public class ConnectionFactoryTest {

    private ConnectionFactory connectionFactory;

    @BeforeEach
    public void beforeTest() throws IOException {
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
    public void whenConnectionFactoryGetInstanceCallTwiceTheSameInstanceShouldBeReturned() throws IOException {
        ConnectionFactory firstInstance = ConnectionFactory.getInstance();
        ConnectionFactory secondInstance = ConnectionFactory.getInstance();
        assertTrue(firstInstance == secondInstance);
    }
}
