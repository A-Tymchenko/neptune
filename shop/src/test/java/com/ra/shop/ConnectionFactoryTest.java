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

    @Test
    public void whenGetConnectionTheConnectionReturned() throws SQLException {
        Connection connection = connectionFactory.getConnection();

        assertNotNull(connection);
        assertFalse(connection.isClosed());
    }

    @Test
    public void whenConnectionFactoryGetInstanceCallMultipleTimesTheSameConnectionReturned() throws IOException {
        ConnectionFactory firstInstance = ConnectionFactory.getInstance();
        ConnectionFactory secondInstance = ConnectionFactory.getInstance();

        assertTrue(firstInstance == secondInstance);
    }

}
