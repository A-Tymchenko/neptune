package com.ra.advertisement.dao;

import com.ra.advertisement.connection.ConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConnectionFactoryTest {
    private ConnectionFactory connectionFactory;
    private Properties dBproperties;

    @BeforeEach
    void setUp() throws IOException {
        connectionFactory = ConnectionFactory.getInstance();
        dBproperties = new Properties();
    }

    /**
     * testing if the object recived from ConnectionFactory - instanceof ConnectionFactory.class
     *
     * @throws IOException
     */
    @Test
    void getInstanceOfConnectionFactoryReturnTrue() throws IOException {
        ConnectionFactory connectionFactoryResult = ConnectionFactory.getInstance();
        assertTrue(connectionFactoryResult instanceof ConnectionFactory);
    }

    /**
     * testing if the ConnectionFactory - is singleton
     *
     * @throws IOException
     */
    @Test
    void getInstanceOfConnectionFactorySingletonReturnTrue() throws IOException {
        connectionFactory = ConnectionFactory.getInstance();
        ConnectionFactory connectionFactoryResult = ConnectionFactory.getInstance();
        assertEquals(connectionFactory.hashCode(), connectionFactoryResult.hashCode());
    }

    /**
     * testing if the method returns Object of class Connection
     */
    @Test
    void getConnectionFromDataSourceReturnTrue() throws SQLException {
        Connection connection = connectionFactory.getConnection();
        assertTrue(connection instanceof Connection);
    }

    /**
     * testing throw NullpointerException if there is'nt config.properties file
     */
    @Test
    void getPropertiesFromConfigPropertiesFileThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            dBproperties.load(ClassLoader.getSystemResourceAsStream("confi.properties"));
        });
    }

}