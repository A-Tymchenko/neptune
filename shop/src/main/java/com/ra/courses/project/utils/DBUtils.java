package com.ra.courses.project.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class DBUtils {

    private static final Logger LOGGER = Logger.getLogger(DBUtils.class);

    /**
     * Method contains a query for table creation.
     * @return String.
     */
    public String createTable() {
        return "CREATE TABLE ORDERS("
                + "ID INT AUTO_INCREMENT PRIMARY KEY, "
                + "NUMBER INT, "
                + "PRICE DOUBLE, "
                + "DELIVERY_INCLUDED BOOLEAN, "
                + "DELIVERY_COST INT, "
                + "EXECUTED BOOLEAN)";
    }

    /**
     * Method contains a query which executes dropping table.
     * @return String;
     */
    public String dropTable() {
        return "DROP TABLE ORDERS IF EXISTS";
    }

    /**
     * Method creates connection to database using driver class, url, username and password,
     * that stored in db.properties file.
     * @return Connection.
     */
    public Connection getConnection() {
        final Properties properties = new Properties();
        Connection connection = null;
        FileInputStream fis;
        try {
            fis = new FileInputStream(
                    new File("/home/reed/IdeaProjects/neptune/shop/src/main/resources/db.properties"));
            properties.load(fis);
            Class.forName(properties.getProperty("DRIVER_CLASS"));
            final String url = properties.getProperty("URL");
            final String username = properties.getProperty("USERNAME");
            final String password = properties.getProperty("PASSWORD");
            connection = DriverManager.getConnection(url, username, password);
            LOGGER.info("Connection to database successful!");
        } catch (IOException | ClassNotFoundException | SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return connection;
    }
}
