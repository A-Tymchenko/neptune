package com.ra.shop.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.h2.jdbcx.JdbcDataSource;

/**
 * ConnectionFactory providing Connection.
 */

public class ConnectionFactory { //NOPMD

    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class);
    private static volatile ConnectionFactory connFactory;
    private static JdbcDataSource dataSource;
    private static Properties property;

    private ConnectionFactory() throws IOException {
        property = new Properties();
        property.load(ClassLoader.getSystemResourceAsStream("db.properties"));
    }

    /**
     * Build ConnectionFactory.
     *
     * @return ConnectionFactory.
     */

    private static ConnectionFactory buildConnectionFactory() throws IOException {
        synchronized (ConnectionFactory.class) {
            if (connFactory == null) {
                connFactory = new ConnectionFactory();
                dataSource = new JdbcDataSource();
                dataSource.setURL(property.getProperty("URL"));
                dataSource.setUser(property.getProperty("USERNAME"));
                dataSource.setPassword(property.getProperty("PASSWORD"));
                LOGGER.info("ConnectionFactory set end.");
            }
        }
        return connFactory;
    }

    /**
     * Return ConnectionFactory.
     *
     * @return ConnectionFactory.
     */

    public static ConnectionFactory getInstance() throws IOException {
        return buildConnectionFactory();
    }

    /**
     * Return Connection.
     *
     * @return Connection.
     */

    public Connection getConnection() throws SQLException {
        LOGGER.info("Use getConnection.");
        return dataSource.getConnection();
    }
}
