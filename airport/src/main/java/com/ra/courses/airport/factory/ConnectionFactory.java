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

    private Connection connectionInstance;

    private ConnectionFactory() {
    }

    public Connection getConnection() {
        if (connectionInstance == null) {
            return createConnection();
        }
        return createConnection();
    }

    private Connection createConnection() {
        try (Connection result = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            Class.forName(JDBC_DRIVER);
            result.setAutoCommit(false);
            return result;
        } catch (ClassNotFoundException exception) {
            //todo add logging here
        } catch (SQLException ex) {
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
