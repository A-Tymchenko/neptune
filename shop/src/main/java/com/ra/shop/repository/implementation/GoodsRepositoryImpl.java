package com.ra.shop.repository.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ra.shop.config.ConnectionFactory;
import com.ra.shop.enums.ExceptionMessage;
import com.ra.shop.exceptions.RepositoryException;
import com.ra.shop.model.Goods;
import com.ra.shop.repository.IRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * CRUD for Goods.
 */
@Component("goodsDAO")
public final class GoodsRepositoryImpl implements IRepository<Goods> {

    private static final Logger LOGGER = Logger.getLogger(GoodsRepositoryImpl.class);
    private final transient KeyHolder generatedKeys = new GeneratedKeyHolder();
    private static final Integer FIRST_SQL_INDEX = 1;
    private static final Integer SECOND_SQL_INDEX = 2;
    private static final Integer THIRD_SQL_INDEX = 3;
    private static final Integer FOURTH_SQL_INDEX = 4;
    private static final Integer FIRST_SQL_COLUMN = 1;
    //private final transient ConnectionFactory connFactory;
    private final transient JdbcTemplate jdbcTemplate;

    @Autowired
    public GoodsRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Create goods in DataBase.
     *
     * @param entity that will be created.
     * @return Entity inserted to database, with added 'ID' from DB.
     */
    @Override
    public Goods create(final Goods entity) {
        //final String createDevice = "INSERT INTO DEVICES (NAME, MODEL, DEVICE_TYPE) VALUES(?,?,?)";
        jdbcTemplate.update(
                connection -> {
                    final PreparedStatement preparedStatement =
                            connection.prepareStatement("INSERT INTO GOODS (NAME, BARCODE, PRICE) VALUES (?,?,?)");
                    setStatementGoodsInSQLIndexes(preparedStatement, entity);
                    return preparedStatement;
                }, generatedKeys);
        entity.setId((long) generatedKeys.getKey());
        return entity;
//        try (Connection connection = connFactory.getConnection()) {
//            final PreparedStatement statement =
//                    connection.prepareStatement("INSERT INTO GOODS (NAME, BARCODE, PRICE) VALUES (?,?,?)");
//            setStatementGoodsInSQLIndexes(statement, entity);
//            statement.executeUpdate();
//            final ResultSet generatedKeys = connection
//                    .prepareStatement("SELECT LAST_INSERT_ID()").executeQuery();
//            if (generatedKeys.next()) {
//                entity.setId(generatedKeys.getLong(FIRST_SQL_COLUMN));
//            }
//        } catch (SQLException ex) {
//            LOGGER.error(ExceptionMessage.FAILED_TO_CREATE_NEW_GOODS.getMessage(), ex);
//            throw new RepositoryException(ExceptionMessage.FAILED_TO_CREATE_NEW_GOODS.getMessage(), ex);
//        }
//        return entity;
    }

    /**
     * Extracted goods in DataBase.
     *
     * @param entityId of entity that will be insert.
     * @return Optional entity.
     */
    @Override
    public Goods get(final long entityId) throws RepositoryException {
        try {


            return jdbcTemplate.queryForObject("SELECT * FROM GOODS WHERE ID = ?",
                    BeanPropertyRowMapper.newInstance(Goods.class), entityId);
//        try (Connection connection = connFactory.getConnection()) {
//            final PreparedStatement statement =
//                    connection.prepareStatement("SELECT * FROM GOODS WHERE ID = ?");
//            statement.setLong(FIRST_SQL_INDEX, entityId);
//            final ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                return Optional.of(getGoodsFromResultSet(resultSet));
//            }
        } catch (DataAccessException ex) {
            LOGGER.error(ExceptionMessage.FAILED_TO_GET_GOODS_BY_ID.getMessage(), ex);
            throw new RepositoryException(ExceptionMessage.FAILED_TO_GET_GOODS_BY_ID.getMessage() + " " + entityId, ex);
        }
//        return Optional.empty();
    }

    /**
     * Update goods in DataBase.
     *
     * @param newEntity updated version of entity.
     * @return update entity.
     */
    @Override
    public Goods update(final Goods newEntity) {
        // final String updateDevice = "update DEVICES set NAME = ?, MODEL= ?, DEVICE_TYPE = ? where DEV_ID = ?";
        jdbcTemplate.update("UPDATE GOODS SET NAME = ?, BARCODE = ?, PRICE = ? WHERE ID = ?",
                statement -> {
                    setStatementGoodsInSQLIndexes(statement, newEntity);
                    statement.setLong(FOURTH_SQL_INDEX, newEntity.getId());
                });
        return newEntity;

//        try (Connection connection = connFactory.getConnection()) {
//            final PreparedStatement statement =
//                connection.prepareStatement("UPDATE GOODS SET NAME = ?, BARCODE = ?, PRICE = ? WHERE ID = ?");
//            setStatementGoodsInSQLIndexes(statement, newEntity);
//            statement.setLong(FOURTH_SQL_INDEX, newEntity.getId());
//            statement.executeUpdate();
//        } catch (SQLException ex) {
//            LOGGER.error(ExceptionMessage.FAILED_TO_UPDATE_GOODS.getMessage(), ex);
//            throw new RepositoryException(ExceptionMessage.FAILED_TO_UPDATE_GOODS.getMessage(), ex);
//        }
//        return newEntity;
    }

    /**
     * Deleting goods in DataBase.
     *
     * @param entityId of entity that will be deleted.
     * @return true else false.
     */
    @Override
    public boolean delete(final long entityId) {
        return jdbcTemplate.update("DELETE FROM GOODS WHERE ID = ?", entityId) > 0;
//        try (Connection connection = connFactory.getConnection()) {
//            final PreparedStatement statement =
//                connection.prepareStatement("DELETE FROM GOODS WHERE ID = ?");
//            statement.setLong(FIRST_SQL_INDEX, entityId);
//            return statement.executeUpdate() > 0;
//        } catch (SQLException ex) {
//            LOGGER.error(ExceptionMessage.FAILED_TO_DELETE_GOODS.getMessage(), ex);
//            throw new RepositoryException(ExceptionMessage.FAILED_TO_DELETE_GOODS.getMessage(), ex);
//        }
    }

    /**
     * Extract all goods in DataBase.
     *
     * @return List entity.
     */
    @Override
    public List<Goods> getAll() {
        //final String getAllDevices = "SELECT * FROM DEVICES";
        // final List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM GOODS");
        return jdbcTemplate.queryForList("SELECT * FROM GOODS").stream()
                .map(goods -> getGoodsFromMap(goods))
                .collect(Collectors.toList());
        // return mapListFromQueryForList(rows);
//        final List<Goods> goods = new ArrayList<>();
//        try (Connection connection = connFactory.getConnection()) {
//            final ResultSet resultSet = connection.prepareStatement("SELECT * FROM GOODS").executeQuery();
//            while (resultSet.next()) {
//                goods.add(getGoodsFromResultSet(resultSet));
//            }
//        } catch (SQLException ex) {
//            LOGGER.error(ExceptionMessage.FAILED_TO_GET_ALL_GOODS.getMessage(), ex);
//            throw new RepositoryException(ExceptionMessage.FAILED_TO_GET_ALL_GOODS.getMessage(), ex);
//        }
//        return goods;
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
        statement.setDouble(THIRD_SQL_INDEX, entity.getPrice());
    }

    /**
     * Create goods.
     *
     * @param resultSet with DataBase.
     * @return Goods.
     */
//    private Goods getGoodsFromResultSet(final ResultSet resultSet) throws SQLException {
//        final Goods goods = new Goods(resultSet.getString("NAME"),
//                resultSet.getLong("BARCODE"),
//                resultSet.getFloat("PRICE"));
//        goods.setId(resultSet.getLong("ID"));
//        return goods;
//    }
    private Goods getGoodsFromMap(final Map<String, Object> resultSet) {
        final Goods goods = new Goods((String) resultSet.get("NAME"),
                (long) resultSet.get("BARCODE"),
                (double) resultSet.get("PRICE"));
        goods.setId((long) resultSet.get("ID"));
        return goods;
    }
}
