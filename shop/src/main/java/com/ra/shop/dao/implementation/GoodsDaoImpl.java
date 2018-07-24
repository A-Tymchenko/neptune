package com.ra.shop.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ra.shop.connection.ConnectionFactory;
import com.ra.shop.dao.IRepository;
import com.ra.shop.dao.exception.DAOException;
import com.ra.shop.dao.exception.ExceptionMessage;
import com.ra.shop.entity.Goods;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * CRUD for Goods.
 */

public class GoodsDaoImpl implements IRepository<Goods> {

    private static final Logger LOGGER = LogManager.getLogger(GoodsDaoImpl.class);
    private static final Integer FIRST_SQL_INDEX = 1;
    private static final Integer SECOND_SQL_INDEX = 2;
    private static final Integer THIRD_SQL_INDEX = 3;
    private static final Integer FOURTH_SQL_INDEX = 4;
    private static final Integer FIRST_SQL_COLUMN = 1;
    private final transient ConnectionFactory connFactory;

    public GoodsDaoImpl(final ConnectionFactory connFactory) {
        this.connFactory = connFactory;
    }

    /**
     * Create goods in DataBase.
     *
     * @param entity that will be created.
     * @return Entity inserted to database, with added 'ID' from DB.
     */

    @Override
    public Goods create(final Goods entity) throws DAOException {
        try (Connection connection = connFactory.getConnection()) {
            final PreparedStatement statement =
                    connection.prepareStatement("INSERT INTO GOODS (NAME, BARCODE, PRICE) VALUES (?,?,?)");
            setStatementGoodsInSQLIndexes(statement, entity);
            statement.executeUpdate();
            final ResultSet generatedKeys = connection
                    .prepareStatement("SELECT LAST_INSERT_ID()").executeQuery();
            if (generatedKeys.next()) {
                entity.setId(generatedKeys.getLong(FIRST_SQL_COLUMN));
            }
        } catch (SQLException ex) {
            LOGGER.error(ExceptionMessage.FAILED_TO_CREATE_NEW_WAREHOUSE.getMessage(), ex);
            throw new DAOException(ExceptionMessage.FAILED_TO_CREATE_NEW_WAREHOUSE.getMessage(), ex);
        }
        return (Goods) get(entity.getId()).get();
    }


    /**
     * Extracted goods in DataBase.
     *
     * @param entityId of entity that will be insert.
     * @return Optional entity.
     */

    @Override
    public Optional get(final Long entityId) throws DAOException {
        if (entityId == null) {
            final String errorNull = "Attempt to get goods with ID = NULL";
            LOGGER.error(errorNull);
            throw new DAOException(errorNull);
        }
        try (Connection connection = connFactory.getConnection()) {
            final PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM GOODS WHERE ID = ?");
            statement.setLong(FIRST_SQL_INDEX, entityId);
            final ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(createGoods(resultSet));
            }
        } catch (SQLException ex) {
            LOGGER.error(ExceptionMessage.FAILED_TO_GET_WAREHOUSE_BY_ID.getMessage(), ex);
            throw new DAOException(ExceptionMessage.FAILED_TO_GET_WAREHOUSE_BY_ID.getMessage() + " " + entityId, ex);
        }
        return Optional.empty();
    }

    /**
     * Update goods in DataBase.
     *
     * @param newEntity updated version of entity.
     * @return update entity.
     */

    @Override
    public Goods update(final Goods newEntity) throws DAOException {
        try (Connection connection = connFactory.getConnection()) {
            final PreparedStatement statement =
                    connection.prepareStatement("UPDATE GOODS SET NAME = ?, BARCODE = ?, PRICE = ? WHERE ID = ?");
            setStatementGoodsInSQLIndexes(statement, newEntity);
            statement.setLong(FOURTH_SQL_INDEX, newEntity.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.error(ExceptionMessage.FAILED_TO_UPDATE_WAREHOUSE.getMessage(), ex);
            throw new DAOException(ExceptionMessage.FAILED_TO_UPDATE_WAREHOUSE.getMessage(), ex);
        }
        return (Goods) get(newEntity.getId()).get();
    }

    /**
     * Deleting goods in DataBase.
     *
     * @param entityId of entity that will be deleted.
     * @return true else false.
     */

    @Override
    public Boolean delete(final Long entityId) throws DAOException {
        try (Connection connection = connFactory.getConnection()) {
            final PreparedStatement statement =
                    connection.prepareStatement("DELETE FROM GOODS WHERE ID = ?");
            statement.setLong(FIRST_SQL_INDEX, entityId);
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.error(ExceptionMessage.FAILED_TO_DELETE_WAREHOUSE.getMessage(), ex);
            throw new DAOException(ExceptionMessage.FAILED_TO_DELETE_WAREHOUSE.getMessage(), ex);
        }
    }

    /**
     * Extract all goods in DataBase.
     *
     * @return List entity.
     */

    @Override
    public List getAll() throws DAOException {
        final List<Goods> goods = new ArrayList<>();
        try (Connection connection = connFactory.getConnection()) {
            final ResultSet resultSet = connection.prepareStatement("SELECT * FROM GOODS").executeQuery();
            while (resultSet.next()) {
                goods.add(createGoods(resultSet));
            }
        } catch (SQLException ex) {
            LOGGER.error(ExceptionMessage.FAILED_TO_GET_ALL_WAREHOUSES.getMessage(), ex);
            throw new DAOException(ExceptionMessage.FAILED_TO_GET_ALL_WAREHOUSES.getMessage(), ex);
        }
        return goods;
    }

    /**
     * Set goods statements.
     *
     * @param statement to DataBase.
     * @param entity    to DataBase.
     */
    private void setStatementGoodsInSQLIndexes(final PreparedStatement statement, final Goods entity) throws SQLException {
        statement.setString(FIRST_SQL_INDEX, entity.getName());
        statement.setLong(SECOND_SQL_INDEX, entity.getBarcode());
        statement.setFloat(THIRD_SQL_INDEX, entity.getPrice());
    }

    /**
     * Create goods.
     *
     * @param resultSet with DataBase.
     * @return Goods.
     */

    private Goods createGoods(final ResultSet resultSet) throws SQLException {
        final Goods goods = new Goods(resultSet.getString("NAME"),
                resultSet.getLong("BARCODE"),
                resultSet.getFloat("PRICE"));
        goods.setId(resultSet.getLong("ID"));
        return goods;
    }
}
