package com.ra.shop.connection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.h2.jdbcx.JdbcDataSource;

public final class ConnectionFactory {
    private static JdbcDataSource dataSource;
    private static Properties dbProperties;
    private static ConnectionFactory factoryInstance;

    @SuppressWarnings("PMD.ClassWithOnlyPrivateConstructorsShouldBeFinal")
    private ConnectionFactory() throws IOException {
        dbProperties = new Properties();
        dbProperties.load(ClassLoader.getSystemResourceAsStream("db.properties"));
    }

    /**
     * Returns singleton's instance.
     *
     * @return connection instance.
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
     * @return source connection.
     */
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
