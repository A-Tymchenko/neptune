package com.ra.shop.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ra.shop.model.Good;
import com.ra.shop.utils.ConnectionFactory;
import org.apache.log4j.Logger;

/**
 * CRUD for Good.
 */

public class GoodsDao implements IRepository<Good> {

    private static final Logger LOGGER = Logger.getLogger(GoodsDao.class);
    private static final Integer FIRST_INDEX = 1;
    private static final Integer SECOND_INDEX = 2;
    private static final Integer THIRD_INDEX = 3;
    private final transient ConnectionFactory connFactory;

    public GoodsDao(final ConnectionFactory connFactory) {
        this.connFactory = connFactory;
    }

    /**
     * Create good in DataBase.
     *
     * @param entity that will be created.
     * @return 1 if true else 0.
     */

    @Override
    public Integer create(final Good entity) throws GoodException {
        if (get(entity.getId()).isPresent()) {
            return 0;
        }

        try (Connection connection = connFactory.getConnection()) {
            final PreparedStatement statement =
                connection.prepareStatement("INSERT INTO GOODS VALUES (?,?,?)");
            statement.setLong(FIRST_INDEX, entity.getId());
            statement.setString(SECOND_INDEX, entity.getName());
            statement.setFloat(THIRD_INDEX, entity.getPrice());

            return statement.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            throw new GoodException(ex.getMessage(), ex);
        }
    }

    /**
     * Extracted good in DataBase.
     *
     * @param entityId of entity that will be insert.
     * @return Optional entity.
     */

    @Override
    public Optional get(final Long entityId) throws GoodException {
        ResultSet resultSet = null;

        try (Connection connection = connFactory.getConnection()) {
            final PreparedStatement statement =
                connection.prepareStatement("SELECT * FROM GOODS WHERE ID = ?");

            statement.setLong(FIRST_INDEX, entityId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(createGood(resultSet));
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            throw new GoodException(ex.getMessage(), ex);
        }
        return Optional.empty();
    }

    /**
     * Update good in DataBase.
     *
     * @param newEntity updated version of entity.
     * @return 1 if true else 0.
     */

    @Override
    public Integer update(final Good newEntity) throws GoodException {

        try (Connection connection = connFactory.getConnection()) {
            final PreparedStatement statement =
                connection.prepareStatement("UPDATE GOODS SET NAME = ?, PRICE = ? WHERE ID = ?");
            statement.setString(FIRST_INDEX, newEntity.getName());
            statement.setFloat(SECOND_INDEX, newEntity.getPrice());
            statement.setLong(THIRD_INDEX, newEntity.getId());
            return statement.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            throw new GoodException(ex.getMessage(), ex);
        }
    }

    /**
     * Deleting good in DataBase.
     *
     * @param entityId of entity that will be deleted.
     * @return 1 if true else 0.
     */

    @Override
    public Integer delete(final Long entityId) throws GoodException {
        try (Connection connection = connFactory.getConnection()) {
            final PreparedStatement statement =
                connection.prepareStatement("DELETE FROM GOODS WHERE ID = ?");
            statement.setLong(FIRST_INDEX, entityId);
            return statement.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            throw new GoodException(ex.getMessage(), ex);
        }
    }

    /**
     * Extract all goods in DataBase.
     *
     * @return List entity.
     */

    @Override
    public List getAll() throws GoodException {
        final List<Good> goods = new ArrayList<>();
        ResultSet resultSet = null;
        try (Connection connection = connFactory.getConnection()) {
            final PreparedStatement statement =
                connection.prepareStatement("SELECT * FROM GOODS");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                goods.add(createGood(resultSet));
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            throw new GoodException(ex.getMessage(), ex);
        }
        // resultSet.close();
        return goods;
    }

    /**
     * Create good.
     *
     * @param resultSet with DataBase.
     * @return Good.
     */

    private Good createGood(final ResultSet resultSet) throws SQLException {
        return new Good(resultSet.getLong("ID"),
            resultSet.getString("NAME"),
            resultSet.getFloat("PRICE"));
    }
}
