package com.ra.advertisement.config;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.h2.tools.RunScript;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component("initData")
public class InitDataBase {
    private static final Logger LOGGER = LogManager.getLogger(AdvertisementConfiguration.class);
    private final transient DataSource dataSource;

    @Autowired
    public InitDataBase(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * This method creates tables in Data base.
     */
    public void initData() {
        try {
            final Connection connection = this.dataSource.getConnection();
            final Resource creationTables = new ClassPathResource("./advertisement_db.sql");
            RunScript.execute(connection, new InputStreamReader(creationTables.getInputStream()));
        } catch (SQLException | IOException e) {
            final String message = "Trouble in the initDataBase method";
            LOGGER.error(message, e);
        }
    }
}
