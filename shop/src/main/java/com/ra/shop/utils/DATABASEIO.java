package com.ra.shop.utils;

import org.apache.log4j.Logger;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public final class DATABASEIO {

    private static final Logger LOGGER = Logger.getLogger(DATABASEIO.class);
    private static final DATABASEIO DATASOURSEDB = new DATABASEIO();
    private static JdbcDataSource dataSource;
    private final String URL_FILE_PROPERTIES = "/home/user/IdeaProjects/neptune/shop/src/main/resources/db.properties";

    private DATABASEIO() {
        InputAndSetProperties();
        LOGGER.info("DATABASEIO set end");
    }

    public static DataSource getDataSource() {
      //  LOGGER.info("Use getDataSource");
        return DATASOURSEDB.dataSource;
    }


    public static Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            conn = getDataSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error("Error connection " + e.getMessage());
        }
        LOGGER.info("Use getConnection");
        return conn;
    }

    private void InputAndSetProperties() {
        this.dataSource = new JdbcDataSource();
        Properties property = new Properties();

        try {
            property.load(new FileInputStream(URL_FILE_PROPERTIES));

            dataSource.setURL(property.getProperty("URL"));
            dataSource.setUser(property.getProperty("USERNAME"));
            dataSource.setPassword(property.getProperty("PASSWORD"));

        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("File properties not found " + e.getMessage());
        }
    }
}

