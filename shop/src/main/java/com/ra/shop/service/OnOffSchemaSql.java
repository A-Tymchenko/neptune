package com.ra.shop.service;

import com.ra.shop.daotest.CustomerSchemaSql;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class OnOffSchemaSql {

    public static void createSchema(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(GoodSchemaSql.CREATE_SCHEMA_SQL);
        }
    }
    public static void deleteSchema(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(GoodSchemaSql.DELETE_SCHEMA_SQL);
        }
    }
}
