package com.ra.shop.servicetest;

import com.ra.shop.utils.ConnectionFactory;

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

    public static void createSchema(ConnectionFactory connectionFactory) throws SQLException {
        try (Connection connection = connectionFactory.getConnection();
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

    public static void deleteSchema(ConnectionFactory connectionFactory) throws SQLException {
        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(GoodSchemaSql.DELETE_SCHEMA_SQL);
        }
    }
}
