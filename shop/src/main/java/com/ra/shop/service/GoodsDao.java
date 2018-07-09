package com.ra.shop.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ra.shop.model.Goods;
import com.ra.shop.utils.ConnectionFactory;
import org.apache.log4j.Logger;

/**
 * CRUD for Goods.
 */

public class GoodsDao implements IRepository<Goods> {

    private static final Logger LOGGER = Logger.getLogger(GoodsDao.class);
    private static final Integer FIRST_INDEX = 1;
    private static final Integer SECOND_INDEX = 2;
    private static final Integer THIRD_INDEX = 3;
    private static final Integer FOURTH_INDEX = 4;
    private static final Integer FIRST_COLUMN = 1;
    private final transient ConnectionFactory connFactory;

    public GoodsDao(final ConnectionFactory connFactory) {
        this.connFactory = connFactory;
    }

    /**
     * Create goods in DataBase.
     *
     * @param entity that will be created.
     * @return Entity inserted to database, with added 'ID' from DB.
     */

    @Override
    public Goods create(final Goods entity) throws GoodsException {
        if (get(entity.getId()).isPresent()) {
            final String warning = "Attempt to recreate an existing goods, ID:" + entity.getId();
            LOGGER.warn(warning);
            return entity;
        }
        try (Connection connection = connFactory.getConnection()) {
            final PreparedStatement statement =
                connection.prepareStatement("INSERT INTO GOODS (NAME, BARCODE, PRICE) VALUES (?,?,?)");
            setStatementGoodsWithoutId(statement, entity);
            statement.executeUpdate();

            final ResultSet generatedKeys = connection
                .prepareStatement("SELECT SCOPE_IDENTITY()").executeQuery();
            if (generatedKeys.next()) {
                entity.setId(generatedKeys.getLong(FIRST_COLUMN));
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            throw new GoodsException(ex.getMessage(), ex);
        }
        return entity;
    }


    /**
     * Extracted goods in DataBase.
     *
     * @param entityId of entity that will be insert.
     * @return Optional entity.
     */

    @Override
    public Optional get(final Long entityId) throws GoodsException {
        if (entityId == null) {
            LOGGER.warn("Attempt to get goods with ID=NULL");
            return Optional.empty();
        }
        ResultSet resultSet = null;

        try (Connection connection = connFactory.getConnection()) {
            final PreparedStatement statement =
                connection.prepareStatement("SELECT * FROM GOODS WHERE ID = ?");

            statement.setLong(FIRST_INDEX, entityId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(createGoods(resultSet));
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            throw new GoodsException(ex.getMessage(), ex);
        }
        return Optional.empty();
    }

    /**
     * Update goods in DataBase.
     *
     * @param newEntity updated version of entity.
     * @return 1 if true else 0.
     */

    @Override
    public Integer update(final Goods newEntity) throws GoodsException {
        if (newEntity.getId() == null) {
            LOGGER.warn("Attempt to update goods with ID=NULL");
            return 0;
        }
        try (Connection connection = connFactory.getConnection()) {
            final PreparedStatement statement =
                connection.prepareStatement("UPDATE GOODS SET NAME = ?, BARCODE = ?, PRICE = ? WHERE ID = ?");
            setStatementGoodsWithoutId(statement, newEntity);
            statement.setLong(FOURTH_INDEX, newEntity.getId());
            return statement.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            throw new GoodsException(ex.getMessage(), ex);
        }
    }

    /**
     * Deleting goods in DataBase.
     *
     * @param entityId of entity that will be deleted.
     * @return 1 if true else 0.
     */

    @Override
    public Integer delete(final Long entityId) throws GoodsException {
        if (entityId == null) {
            LOGGER.warn("Attempt to delete goods with ID=NULL");
            return 0;
        }
        try (Connection connection = connFactory.getConnection()) {
            final PreparedStatement statement =
                connection.prepareStatement("DELETE FROM GOODS WHERE ID = ?");
            statement.setLong(FIRST_INDEX, entityId);
            return statement.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            throw new GoodsException(ex.getMessage(), ex);
        }
    }

    /**
     * Extract all goods in DataBase.
     *
     * @return List entity.
     */

    @Override
    public List getAll() throws GoodsException {
        final List<Goods> goods = new ArrayList<>();
        ResultSet resultSet = null;
        try (Connection connection = connFactory.getConnection()) {
            resultSet = connection.prepareStatement("SELECT * FROM GOODS").executeQuery();

            while (resultSet.next()) {
                goods.add(createGoods(resultSet));
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            throw new GoodsException(ex.getMessage(), ex);
        }
        return goods;
    }

    /**
     * Set goods statements.
     *
     * @param statement to DataBase.
     * @param entity    to DataBase.
     */
    private void setStatementGoodsWithoutId(final PreparedStatement statement, final Goods entity) throws SQLException {
        statement.setString(FIRST_INDEX, entity.getName());
        statement.setLong(SECOND_INDEX, entity.getBarcode());
        statement.setFloat(THIRD_INDEX, entity.getPrice());
    }

    /**
     * Create goods.
     *
     * @param resultSet with DataBase.
     * @return Goods.
     */

    private Goods createGoods(final ResultSet resultSet) throws SQLException {
        return new Goods(resultSet.getLong("ID"),
            resultSet.getString("NAME"),
            resultSet.getLong("BARCODE"),
            resultSet.getFloat("PRICE"));
    }
}
