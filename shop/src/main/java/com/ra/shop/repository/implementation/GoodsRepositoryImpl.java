package com.ra.shop.repository.implementation;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ra.shop.enums.ExceptionMessage;
import com.ra.shop.exceptions.RepositoryException;
import com.ra.shop.model.Goods;
import com.ra.shop.repository.IRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

/**
 * CRUD for Goods.
 */
@Component("goodsDAO")
public final class GoodsRepositoryImpl implements IRepository<Goods> {

    private static final Logger LOGGER = LogManager.getLogger(GoodsRepositoryImpl.class);
    private final transient KeyHolder generatedKeys = new GeneratedKeyHolder();
    private static final Integer FIRST_SQL_INDEX = 1;
    private static final Integer SECOND_SQL_INDEX = 2;
    private static final Integer THIRD_SQL_INDEX = 3;
    private static final Integer FOURTH_SQL_INDEX = 4;
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
    public Goods create(final Goods entity) throws RepositoryException {
        try {
            jdbcTemplate.update(
                    connection -> {
                        final PreparedStatement preparedStatement =
                                connection.prepareStatement("INSERT INTO GOODS (NAME, BARCODE, PRICE) VALUES (?,?,?)");
                        setStatementGoodsInSQLIndexes(preparedStatement, entity);
                        return preparedStatement;
                    }, generatedKeys);
            entity.setId((long) generatedKeys.getKey());
            return entity;
        } catch (DataAccessException ex) {
            LOGGER.error(ExceptionMessage.FAILED_TO_CREATE_NEW_GOODS.getMessage(), ex);
            throw new RepositoryException(ExceptionMessage.FAILED_TO_CREATE_NEW_GOODS.getMessage(), ex);
        }
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
        } catch (DataAccessException ex) {
            LOGGER.error(ExceptionMessage.FAILED_TO_GET_GOODS_BY_ID.getMessage(), ex);
            throw new RepositoryException(ExceptionMessage.FAILED_TO_GET_GOODS_BY_ID.getMessage() + " " + entityId, ex);
        }
    }

    /**
     * Update goods in DataBase.
     *
     * @param newEntity updated version of entity.
     * @return update entity.
     */
    @Override
    public Goods update(final Goods newEntity) throws RepositoryException {
        try {
            jdbcTemplate.update("UPDATE GOODS SET NAME = ?, BARCODE = ?, PRICE = ? WHERE ID = ?",
                    statement -> {
                        setStatementGoodsInSQLIndexes(statement, newEntity);
                        statement.setLong(FOURTH_SQL_INDEX, newEntity.getId());
                    });
            return newEntity;
        } catch (DataAccessException ex) {
            LOGGER.error(ExceptionMessage.FAILED_TO_UPDATE_GOODS.getMessage(), ex);
            throw new RepositoryException(ExceptionMessage.FAILED_TO_UPDATE_GOODS.getMessage(), ex);
        }
    }

    /**
     * Deleting goods in DataBase.
     *
     * @param entityId of entity that will be deleted.
     * @return true else false.
     */
    @Override
    public boolean delete(final long entityId) throws RepositoryException {
        try {
            return jdbcTemplate.update("DELETE FROM GOODS WHERE ID = ?", entityId) > 0;
        } catch (DataAccessException ex) {
            LOGGER.error(ExceptionMessage.FAILED_TO_DELETE_GOODS.getMessage(), ex);
            throw new RepositoryException(ExceptionMessage.FAILED_TO_DELETE_GOODS.getMessage(), ex);
        }
    }

    /**
     * Extract all goods in DataBase.
     *
     * @return List entity.
     */
    @Override
    public List<Goods> getAll() throws RepositoryException {
        try {
            return jdbcTemplate.queryForList("SELECT * FROM GOODS").stream()
                    .map(goods -> getGoodsFromMap(goods))
                    .collect(Collectors.toList());
        } catch (DataAccessException ex) {
            LOGGER.error(ExceptionMessage.FAILED_TO_GET_ALL_GOODS.getMessage(), ex);
            throw new RepositoryException(ExceptionMessage.FAILED_TO_GET_ALL_GOODS.getMessage(), ex);
        }
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
     * @param mapGoods with DataBase.
     * @return Goods.
     */
    private Goods getGoodsFromMap(final Map<String, Object> mapGoods) {
        final Goods goods = new Goods((String) mapGoods.get("NAME"),
                (long) mapGoods.get("BARCODE"),
                (double) mapGoods.get("PRICE"));
        goods.setId((long) mapGoods.get("ID"));
        return goods;
    }
}
