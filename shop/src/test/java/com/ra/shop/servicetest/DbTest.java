package com.ra.shop.servicetest;

import com.ra.shop.model.Good;
import com.ra.shop.service.GoodDAO;
import com.ra.shop.service.GoodSchemaSql;
import com.ra.shop.utils.DATABASEIO;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


public class DbTest {

    @Test
    public void createSchema() throws SQLException {

        try (Connection connection = DATABASEIO.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(GoodSchemaSql.CREATE_SCHEMA_SQL);
            System.out.println(DATABASEIO.getConnection());
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

        try (Connection connection1 = DATABASEIO.getConnection();
             PreparedStatement statement1 =
                     connection1.prepareStatement("INSERT INTO GOODS VALUES (?,?,?)")) {
            for (Good good : goods) {

                statement1.setInt(1, good.getId());
                statement1.setString(2, good.getName());
                statement1.setFloat(3, good.getPrice());
                statement1.execute();
            }
            connection1.close();
            statement1.close();

        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }

    @Test
    public void viewAll() throws Exception {
        GoodDAO goodDAO = new GoodDAO();
        goodDAO.getAll().forEach(x -> System.out.println(x));
        goodDAO = null;
    }

    @Test
    public void viewID() throws Exception {
        GoodDAO goodDAO = new GoodDAO();
        for (int i = 0; i <= 4; i++) {

            System.out.println("\n" + i + ": ");
            goodDAO.getById(i).ifPresent(x -> System.out.println(x));
            System.out.println(goodDAO.getById(i).orElseGet(() -> null));
        }
    }

    @Test
    public void addGood() throws Exception {
        GoodDAO goodDAO = new GoodDAO();
        System.out.println(goodDAO.add(new Good(1, "Adam", 1.2f)));
        goodDAO.getAll().forEach(x -> System.out.println(x));
        System.out.println(goodDAO.add(new Good(5, "Felix", 13.9f)));
        goodDAO.getAll().forEach(x -> System.out.println(x));
    }

    @Test
    public void addUpdate() throws Exception {
        GoodDAO goodDAO = new GoodDAO();
        goodDAO.getAll().forEach(x -> System.out.println(x));
        System.out.println(goodDAO.update(new Good(1, "Adam Smit", 9.02f)));
        goodDAO.getAll().forEach(x -> System.out.println(x));
        System.out.println(goodDAO.update(new Good(4, "Tom", 14.7f)));
        goodDAO.getAll().forEach(x -> System.out.println(x));
    }

    @Test
    public void addDelete() throws Exception {

        GoodDAO goodDAO = new GoodDAO();
        goodDAO.getAll().forEach(x -> System.out.println(x));
        System.out.println(goodDAO.delete(new Good(1, "Adam Smit", 9.02f)));
        goodDAO.getAll().forEach(x -> System.out.println(x));
    }

    @Test
    public void multithread() throws Exception {

        try (Connection connection = DATABASEIO.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(GoodSchemaSql.CREATE_SCHEMA_SQL);
            System.out.println(DATABASEIO.getConnection());
            connection.close();
            statement.close();
        }

        GoodDAO goodDAO = new GoodDAO();

        goodDAO.getAll().forEach(x -> System.out.println(x));

        ExecutorService goodsThread = Executors.newFixedThreadPool(3);

        Future goodThread1 = goodsThread.submit(() -> {
            String threadName = Thread.currentThread().getName();
            try {
                goodDAO.add(new Good(1, "Adam", 1.2f));
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("1 - Adam " + threadName);
        });

        Future goodThread2 = goodsThread.submit(() -> {
            String threadName = Thread.currentThread().getName();
            try {
                goodDAO.add(new Good(2, "Bob", 23.05f));
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("2 - Bob " + threadName);
        });

        Future goodThread3 = goodsThread.submit(() -> {
            String threadName = Thread.currentThread().getName();
            try {
                goodDAO.add(new Good(3, "Carl", 123.56f));
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("3 - Carl " + threadName);
        });

        goodsThread.shutdown();

        System.out.println(goodThread1.get() + " " + goodThread2.get() + " " + goodThread3.get());

        goodDAO.getAll().forEach(x -> System.out.println(x));
    }

    @Test
    public void multithreadMAX() throws Exception {

        try (Connection connection = DATABASEIO.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(GoodSchemaSql.CREATE_SCHEMA_SQL);
            System.out.println(DATABASEIO.getConnection());
            connection.close();
            statement.close();
        }

        GoodDAO goodDAO = new GoodDAO();

        goodDAO.getAll().forEach(x -> System.out.println(x));

        ExecutorService goodsThread = Executors.newFixedThreadPool(1000);
        for (int i = 0; i < 1000; i++) {

            goodsThread.submit(() -> {
                String threadName = Thread.currentThread().getName();

                try {
                    goodDAO.add(new Good((int) Thread.currentThread().getId(), threadName, 1.2f));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getId() + " " + threadName);
            });
        }

        goodsThread.shutdown();

        while (!goodsThread.awaitTermination(1l, TimeUnit.SECONDS)) {
            System.out.println("Waiting for termination");
        }

        System.out.println();
        goodDAO.getAll().forEach(x -> System.out.println(x));

    }

    @Test
    public void deleteSchema() throws SQLException {
        try (Connection connection = DATABASEIO.getConnection();
             Statement statement = connection.createStatement()) {
            System.out.println(DATABASEIO.getDataSource());
            statement.execute(GoodSchemaSql.DELETE_SCHEMA_SQL);

        }
    }

}

