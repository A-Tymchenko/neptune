package com.ra.courses.airport.factory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.h2.jdbcx.JdbcDataSource;

/**
 * Singelton factory for connection creation.
 */
@SuppressWarnings("PMD.ClassWithOnlyPrivateConstructorsShouldBeFinal")
public class ConnectionFactory {
    private static JdbcDataSource dataSource;
    private static Properties dbProperties;

    private static ConnectionFactory factoryInstance;

    private ConnectionFactory() throws IOException {
        dbProperties = new Properties();
        dbProperties.load(ClassLoader.getSystemResourceAsStream("config.properties"));
    }

    /**
     * Method to produce singleton configured factory.
     *
     * @return ConnectionFactory instance.
     */
    public static ConnectionFactory getInstance() throws IOException {
        synchronized (ConnectionFactory.class) {
            if (factoryInstance == null) {
                factoryInstance = new ConnectionFactory();
                dataSource = new JdbcDataSource();
                dataSource.setURL(dbProperties.getProperty("jdbc.url"));
                dataSource.setUser(dbProperties.getProperty("jdbc.user"));
                dataSource.setPassword(dbProperties.getProperty("jdbc.password"));
            }
        }
        return factoryInstance;
    }

    /**
     * Returns new connection.
     *
     * @return Connection.
     */
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}

