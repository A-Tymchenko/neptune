package com.ra.advertisement.connection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.h2.jdbcx.JdbcDataSource;

@SuppressWarnings("PMD.ClassWithOnlyPrivateConstructorsShouldBeFinal")
public class ConnectionFactory {

    private static ConnectionFactory connFactory;
    private static JdbcDataSource dataSource;
    private static Properties dbProperties;

    private ConnectionFactory() throws IOException {
        dbProperties = new Properties();
        loadProperties();
    }

    /**
     * this method create Property and load data from config.properties file.
     */
    private void loadProperties() throws IOException {
        dbProperties.load(ClassLoader.getSystemResourceAsStream("config.properties"));
    }

    /**
     * Method to produce singleton config factory.
     *
     * @return ConnectionFactory instance.
     */
    public static ConnectionFactory getInstance() throws IOException {
        synchronized (ConnectionFactory.class) {
            if (connFactory == null) {
                connFactory = new ConnectionFactory();
                dataSource = new JdbcDataSource();
                dataSource.setURL(dbProperties.getProperty("db.url"));
                dataSource.setUser(dbProperties.getProperty("db.username"));
                dataSource.setPassword(dbProperties.getProperty("db.password"));
            }
        }
        return connFactory;
    }

    /**
     * This method get Connection from JdbcDataSource.
     *
     * @return Connection.
     */
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
