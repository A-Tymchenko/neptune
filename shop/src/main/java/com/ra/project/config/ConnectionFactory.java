package com.ra.project.config;

import org.h2.jdbcx.JdbcDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Class perform connectionFactory creation.
 * This factory is needed for performing connections to database.
 */
public class ConnectionFactory {

    /**
     * Static field Properties.
     */
    private static Properties properties;

    /**
     * Static field JdbcDataSource.
     */
    private static JdbcDataSource dataSource;

    /**
     * Static field ConnectionFactory.
     */
    private static ConnectionFactory connectionFactory;

    /**
     * Private constructor, that store properties inside and perform configuration loading via ClassLoader.
     *
     * @throws IOException if any error occurs.
     */
    private ConnectionFactory() throws IOException {
        properties = new Properties();
        properties.load(ClassLoader.getSystemResourceAsStream("db.properties"));
    }

    /**
     * Singleton that perform creation of the connectionFactory instance.
     *
     * @return ConnectionFactory
     * @throws IOException if any error occurs.
     */
    private static ConnectionFactory buildConnectionFactory() throws IOException {
        synchronized (ConnectionFactory.class) {
            if (connectionFactory == null) {
                connectionFactory = new ConnectionFactory();
                dataSource = new JdbcDataSource();
                dataSource.setURL(properties.getProperty("URL"));
                dataSource.setUser(properties.getProperty("USERNAME"));
                dataSource.setPassword(properties.getProperty("PASSWORD"));
            }
        }
        return connectionFactory;
    }

    /**
     * Method returns created connectionFactory instance.
     *
     * @return ConnectionFactory
     * @throws IOException if any error occurs.
     */
    public static ConnectionFactory getInstance() throws IOException {
        return buildConnectionFactory();
    }

    /**
     * Method returns connection to the database.
     *
     * @return Connection instance for interaction with database.
     * @throws SQLException if ane error occurs.
     */
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
