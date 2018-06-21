package com.ra.shop.servicetest;

import com.ra.shop.model.Good;
import com.ra.shop.service.GoodSchemaSql;
import com.ra.shop.utils.SingletonFabrikDB;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class DbTest {

    @Test
    public void createSchema() throws SQLException {

        try (Connection connection = SingletonFabrikDB.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(GoodSchemaSql.CREATE_SCHEMA_SQL);
            System.out.println(SingletonFabrikDB.getDataSource().getConnection());
            connection.close();
            statement.close();
        }

        Good good1 = new Good(1, "Adam", 1.2f);
        Good good2 = new Good(2, "Bob", 23.05f);
        Good good3 = new Good(3, "Carl", 123.56f);

        List<Good> goods = new ArrayList<>();
        goods.add(good1);
        goods.add(good2);
        goods.add(good3);

        try (Connection connection1 = SingletonFabrikDB.getDataSource().getConnection();
             PreparedStatement statement1 =
                     connection1.prepareStatement("INSERT INTO GOODS VALUES (?,?,?)")) {
            for (Good good : goods) {

                statement1.setInt(1, good.getId());
                statement1.setString(2, good.getName());
                statement1.setFloat(3, good.getPrice());
                statement1.execute();
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Test
    public void deleteSchema() throws SQLException {
        try (Connection connection = SingletonFabrikDB.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            System.out.println(SingletonFabrikDB.getDataSource());
            statement.execute(GoodSchemaSql.DELETE_SCHEMA_SQL);

        }
    }

}
