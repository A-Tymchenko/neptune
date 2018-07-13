package com.ra.shop.connection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.h2.jdbcx.JdbcDataSource;

@SuppressWarnings("PMD.ClassWithOnlyPrivateConstructorsShouldBeFinal")
public class ConnectionFactory {
    private static ConnectionFactory connectionFactory;
    private static Properties properties;
    private static volatile JdbcDataSource dataSource;

    @SuppressWarnings("PMD.ClassWithOnlyPrivateConstructorsShouldBeFinal")
    private ConnectionFactory() throws IOException {
        properties = new Properties();
        properties.load(ClassLoader.getSystemResourceAsStream("db.properties"));
    }

    public static ConnectionFactory buildConnectionFactory() throws IOException {
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
     * Returns singleton's instance.
     *
     * @return connection instance.
     */
    public static ConnectionFactory getInstance() throws IOException {
        return buildConnectionFactory();
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
