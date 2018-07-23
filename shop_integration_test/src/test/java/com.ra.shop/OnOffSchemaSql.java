package com.ra.shop;

import com.ra.shop.utils.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class OnOffSchemaSql {

    public static void createSchema(ConnectionFactory connectionFactory) throws SQLException {
        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(GoodsSchemaSql.CREATE_SCHEMA_SQL);
        }
    }

    public static void deleteSchema(ConnectionFactory connectionFactory) throws SQLException {
        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(GoodsSchemaSql.DELETE_SCHEMA_SQL);
        }
    }
}
