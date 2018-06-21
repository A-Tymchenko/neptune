package com.ra.shop.utils;

import com.ra.shop.daotest.DbCustomerDao;
import org.apache.log4j.Logger;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class SingletonFabrikDB {

    private static final Logger LOGGER = Logger.getLogger(SingletonFabrikDB.class);
    private static final SingletonFabrikDB DATASOURSEDB = new SingletonFabrikDB();
    private static JdbcDataSource dataSource;
    private final String URL_FILE_PROPERTIES = "C:\\Users\\vasiliev\\IdeaProjects\\neptune\\shop\\src\\main\\resources\\db.properties";

    private SingletonFabrikDB() {
        InputAndSetProperties();
        LOGGER.info("SingletonFabrikDB set end");
    }

    public static DataSource getDataSource() {
        return DATASOURSEDB.dataSource;
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
            LOGGER.error("file properties not found",
                    e);
        }
    }
}

