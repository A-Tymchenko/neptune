package com.ra.airport.factory;

import org.h2.jdbcx.JdbcDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Singelton factory for connection creation.
 */
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
    public static ConnectionFactory getInstance() {
        synchronized (ConnectionFactory.class) {
            try {
                if (factoryInstance == null) {
                    factoryInstance = new ConnectionFactory();
                    dataSource = new JdbcDataSource();
                    dataSource.setURL(dbProperties.getProperty("jdbc.url"));
                    dataSource.setUser(dbProperties.getProperty("jdbc.user"));
                    dataSource.setPassword(dbProperties.getProperty("jdbc.password"));
                }
            } catch (IOException e) {
                e.printStackTrace();
                //todo add logging here
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

