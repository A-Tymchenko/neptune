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
            jdbcTemplate.update(connection -> {
                final PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO GOODS (NAME, BARCODE, PRICE) VALUES (?,?,?)");
                setStatementGoodsInSQLIndexes(statement, entity);
                return statement;
            }, generatedKeys);
            entity.setId((Long) generatedKeys.getKey());
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
     * @return entity.
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
                        statement.setLong(4, newEntity.getId());
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
            return jdbcTemplate.query("SELECT * FROM GOODS",
                    BeanPropertyRowMapper.newInstance(Goods.class));
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
    private void setStatementGoodsInSQLIndexes(final PreparedStatement statement,
                                               final Goods entity) throws SQLException {
        final int name = 1;
        final int barcode = 2;
        final int price = 3;
        statement.setString(name, entity.getName());
        statement.setLong(barcode, entity.getBarcode());
        statement.setDouble(price, entity.getPrice());
    }
}
