package com.ra.shop.service;

import com.ra.shop.daotest.CustomException;
import com.ra.shop.model.Good;
import com.ra.shop.utils.DATABASEIO;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class GoodDAO implements ShopDao<Good> {

    private static final Logger LOGGER = Logger.getLogger(GoodDAO.class);

    private Map<Integer, Good> idToGood = new HashMap<>();


    @Override
    public Stream<Good> getAll() throws Exception {
        Connection connection;
        try {
            connection = DATABASEIO.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM GOODS"); // NOSONAR
            ResultSet resultSet = statement.executeQuery(); // NOSONAR
            return StreamSupport.stream(new Spliterators.AbstractSpliterator<Good>(Long.MAX_VALUE,
                    Spliterator.ORDERED) {

                @Override
                public boolean tryAdvance(Consumer<? super Good> action) {
                    try {
                        if (!resultSet.next()) {
                            return false;
                        }
                        action.accept(createGood(resultSet));
                        return true;
                    } catch (SQLException e) {
                        throw new RuntimeException(e); // NOSONAR
                    }
                }
            }, false).onClose(() -> mutedClose(connection, statement, resultSet));
        } catch (SQLException e) {
            throw new GoodException(e.getMessage(), e);
        }


        // return idToGood.values().stream();
    }

    private void mutedClose(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        try {
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            LOGGER.info("Exception thrown " + e.getMessage());
        }
    }

    private Good createGood(ResultSet resultSet) throws SQLException {
        return new Good(resultSet.getInt("ID"),
                resultSet.getString("NAME"),
                resultSet.getFloat("PRICE"));
    }

    @Override
    public Optional<Good> getById(int id) throws Exception {
        ResultSet resultSet = null;

        try (Connection connection = DATABASEIO.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM GOODS WHERE ID = ?")) {

            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(createGood(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new GoodException(ex.getMessage(), ex);
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
        //return Optional.ofNullable(idToGood.get(id));
    }

    @Override
    public boolean add(Good good) throws Exception {

        if (getById(good.getId()).isPresent()) {
            return false;
        }

        try (Connection connection = DATABASEIO.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("INSERT INTO GOODS VALUES (?,?,?)")) {
            statement.setInt(1, good.getId());
            statement.setString(2, good.getName());
            statement.setFloat(3, good.getPrice());
            statement.execute();
            return true;
        } catch (SQLException ex) {
            throw new GoodException(ex.getMessage(), ex);
        }

//        if (getById(good.getId()).isPresent()) {
//            return false;
//        }
//        idToGood.put(good.getId(), good);
//        return true;
    }

    @Override
    public boolean update(Good good) throws Exception {

        try (Connection connection = DATABASEIO.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("UPDATE GOODS SET NAME = ?, PRICE = ? WHERE ID = ?")) {
            statement.setString(1, good.getName());
            statement.setFloat(2, good.getPrice());
            statement.setInt(3, good.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new CustomException(ex.getMessage(), ex);
        }

        // return idToGood.replace(good.getId(), good) != null;
    }

    @Override
    public boolean delete(Good good) throws Exception {

        try (Connection connection = DATABASEIO.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("DELETE FROM GOODS WHERE ID = ?")) {
            statement.setInt(1, good.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new CustomException(ex.getMessage(), ex);
        }
       // return idToGood.remove(good.getId()) != null;
    }
}
