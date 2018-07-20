package com.ra.shop.connection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.h2.jdbcx.JdbcDataSource;

@SuppressWarnings("PMD.ClassWithOnlyPrivateConstructorsShouldBeFinal")
public class ConnectionFactory {
    private static volatile JdbcDataSource dataSource;
    private static Properties properties;

    private static ConnectionFactory factoryInstance;

    @SuppressWarnings("PMD.ClassWithOnlyPrivateConstructorsShouldBeFinal")
    private ConnectionFactory() throws IOException {
        properties = new Properties();
        properties.load(ClassLoader.getSystemResourceAsStream("db.properties"));
    }

    /**
     * Returns singleton's instance.
     *
     * @return factory instance.
     */
    public static ConnectionFactory getInstance() throws IOException {
        synchronized (ConnectionFactory.class) {
            if (factoryInstance == null) {
                factoryInstance = new ConnectionFactory();
                dataSource = new JdbcDataSource();
                dataSource.setURL(properties.getProperty("URL"));
                dataSource.setUser(properties.getProperty("USERNAME"));
                dataSource.setPassword(properties.getProperty("PASSWORD"));
            }
        }
        return factoryInstance;
    }

    /**
     * Method gets Connection from JdbcDataSource.
     * @return Connection instance
     */
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
