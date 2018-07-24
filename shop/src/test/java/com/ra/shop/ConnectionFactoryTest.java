package com.ra.shop;

import com.ra.shop.connection.ConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class ConnectionFactoryTest {

    private ConnectionFactory connectionFactory;

    @BeforeEach
    public void beforeTest() throws IOException {
        connectionFactory = ConnectionFactory.getInstance();
    }

    /**
     * testing if connection available
     *
     * @throws SQLException
     */
    @Test
    public void whenGetConnectionTheConnectionReturned() throws SQLException {
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
    public void whenConnectionFactoryGetInstanceCallMultipleTimesTheSameConnectionReturned() throws IOException {
        ConnectionFactory firstInstance = ConnectionFactory.getInstance();
        ConnectionFactory secondInstance = ConnectionFactory.getInstance();

        assertSame(firstInstance, secondInstance);
    }

}
