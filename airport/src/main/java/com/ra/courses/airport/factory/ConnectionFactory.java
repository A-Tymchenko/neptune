package com.ra.courses.airport.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by anbo06131 on 6/15/2018.
 */
public final class ConnectionFactory {

    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:mem:airport";

    private static final String USER = "";
    private static final String PASSWORD = "";

    private static final ConnectionFactory factoryInstance = new ConnectionFactory();

    private ConnectionFactory() {
    }

    public Connection getConnection() {
        return createConnection();
    }

    private Connection createConnection() {
        try {
            Connection result = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            Class.forName(JDBC_DRIVER);
            result.setAutoCommit(true);
            return result;
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
            //todo add logging here
        } catch (SQLException exception) {
            exception.printStackTrace();
            //todo add logging here
        }
        return null;
    }

    public static ConnectionFactory getInstance() {
        if (factoryInstance == null) {
            return new ConnectionFactory();
        }
        return factoryInstance;
    }
}
