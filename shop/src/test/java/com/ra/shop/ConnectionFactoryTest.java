package com.ra.shop;

import com.ra.shop.config.ConnectionFactory;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

public class ConnectionFactoryTest {

    private ConnectionFactory connectionFactory;

    @BeforeEach
    void beforeTest() throws IOException {
        connectionFactory = ConnectionFactory.getInstance();
    }

    /**
     * testing if connection available
     *
     * @throws SQLException
     */
    @Test
    void whenGetConnectionTheConnectionReturned() throws SQLException {
        Connection connection = connectionFactory.getConnection();

        assertNotNull(connection);
        assertFalse(connection.isClosed());
    }

    /**
     * testing whether the connection is of the singleton pattern
     *
     * @throws IOException
     */

    @Test
    void whenConnectionFactoryGetInstanceCallMultipleTimesTheSameConnectionReturned() throws IOException {
        ConnectionFactory firstInstance = ConnectionFactory.getInstance();
        ConnectionFactory secondInstance = ConnectionFactory.getInstance();

        assertSame(firstInstance, secondInstance);
    }

}