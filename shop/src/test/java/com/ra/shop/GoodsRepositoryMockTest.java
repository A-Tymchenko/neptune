package com.ra.shop;

import com.ra.shop.exceptions.RepositoryException;
import com.ra.shop.repository.implementation.GoodsRepositoryImpl;
import com.ra.shop.model.Goods;
import org.junit.jupiter.api.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class GoodsRepositoryMockTest {

    private static GoodsRepositoryImpl dao;
    private static JdbcTemplate mockedjdbcTemplate;
    private static Goods existingGoods = new Goods("Camel", 7622210609779l, 1.2d);
    private Connection mockedConnection;
    private PreparedStatement mockedStatement;
    private ResultSet mockedResultSet;
    private ResultSet mockedResultSetForKey;
    private KeyHolder mockedgeneratedKeys = new GeneratedKeyHolder();
    private BeanPropertyRowMapper mockedBeanPropertyRowMapper;
    private static final Long DEFAULT_ID = 1l;
    private static final Long GENERATION_ID = 2l;

    @BeforeAll
    static void createSchema() {
        mockedjdbcTemplate = mock(JdbcTemplate.class);
        dao = new GoodsRepositoryImpl(mockedjdbcTemplate);
        existingGoods.setId(DEFAULT_ID);
    }

    @BeforeEach
    public void setUp() {
        mockedgeneratedKeys = mock(KeyHolder.class);
        mockedBeanPropertyRowMapper = mock(BeanPropertyRowMapper.class);
        mockedConnection = mock(Connection.class);
        mockedStatement = mock(PreparedStatement.class);
        mockedResultSet = mock(ResultSet.class);
        mockedResultSetForKey = mock(ResultSet.class);
    }

    @Test
    void whenCreatedGoodsWithFalseNextIdThenReturnEqualsGoods() throws RepositoryException, SQLException {
        when(mockedConnection.prepareStatement(eq("INSERT INTO GOODS (NAME, BARCODE, PRICE) VALUES (?,?,?)")))
                .thenReturn(mockedStatement);
        doAnswer(invocation -> {
            ((PreparedStatementCreator) invocation.getArguments()[0]).createPreparedStatement(mockedConnection);
            return null;
        }).when(mockedjdbcTemplate).update(any(PreparedStatementCreator.class), any(KeyHolder.class));
        when(mockedgeneratedKeys.getKey()).thenReturn(GENERATION_ID);
        Goods goods = dao.create(existingGoods);
        existingGoods.setId((Long) mockedgeneratedKeys.getKey());
        assertTrue(goods.equals(existingGoods));
    }

    @Test
    void whenReadAllGoodThenResultIsTrue() throws Exception {
        when(mockedjdbcTemplate.queryForList(eq("SELECT * FROM GOODS"))).thenReturn(createListOfMap());
        assertFalse(dao.getAll().isEmpty());
    }

    @Test
    void whenDeletionGoodsThenReturnTrue() throws RepositoryException {
        when(mockedjdbcTemplate.update(eq("DELETE FROM GOODS WHERE ID = ?"), eq(existingGoods.getId()))).thenReturn(1);
        assertTrue(dao.delete(existingGoods.getId()));
    }

    @Test
    void whenDeletionGoodsThenReturnFalse() throws RepositoryException {
        when(mockedjdbcTemplate.update(eq("DELETE FROM GOODS WHERE ID = ?"),
                eq(existingGoods.getId()))).thenReturn(-1);
        assertFalse(dao.delete(existingGoods.getId()));
    }

    @Test
    void getAllGoodsFailsWithExceptionAsFeedbackToClient() {
        assertThrows(RepositoryException.class, () -> {
            when(mockedjdbcTemplate.queryForList(eq("SELECT * FROM GOODS")))
                    .thenThrow(EmptyResultDataAccessException.class);
            dao.getAll();
        });
    }

    @Test
    void whenGetGoodsWithTrueNextGoodsThenReturnEqualsGoods() throws RepositoryException {
        when(mockedjdbcTemplate.queryForObject(eq("SELECT * FROM GOODS WHERE ID = ?"),
                any(RowMapper.class),
                eq(existingGoods.getId()))).thenReturn(existingGoods);
        assertTrue(dao.get(existingGoods.getId()).equals(existingGoods));
    }

    @Test
    void getAGoodByIdFailsWithExceptionAsFeedbackToClient() {
        assertThrows(RepositoryException.class, () -> {
            when(mockedjdbcTemplate.queryForObject(eq("SELECT * FROM GOODS WHERE ID = ?"),
                    any(RowMapper.class), any(Long.class)))
                    .thenThrow(EmptyResultDataAccessException.class);
            dao.get(existingGoods.getId());
        });
    }

    @Test
    void deletingAGoodsFailsWithExceptionAsFeedbackToTheClient() {
        assertThrows(RepositoryException.class, () -> {
            when(mockedjdbcTemplate.update(eq("DELETE FROM GOODS WHERE ID = ?"),
                    eq(existingGoods.getId())))
                    .thenThrow(EmptyResultDataAccessException.class);
            dao.delete(existingGoods.getId());
        });
    }

    @Test
    void updatingAGoodsFailsWithFeedbackToTheClient() {
        assertThrows(RepositoryException.class, () -> {
            when(mockedjdbcTemplate.update(eq("UPDATE GOODS SET NAME = ?, BARCODE = ?, PRICE = ? WHERE ID = ?"),
                    any(PreparedStatementSetter.class)))
                    .thenThrow(EmptyResultDataAccessException.class);
            dao.update(existingGoods);
        });
    }

    @Test
    public void createAGoodsFailsWithExceptionAsFeedbackToClient() {

        assertThrows(RepositoryException.class, () -> {
            when(mockedjdbcTemplate.update(any(PreparedStatementCreator.class),
                    any(KeyHolder.class)))
                    .thenThrow(EmptyResultDataAccessException.class);
            dao.create(existingGoods);
        });
    }

    @Test
    void whenUpdationGoodsThenReturnEqualsGoods() throws RepositoryException {
        doAnswer(invocation -> {
            ((PreparedStatementSetter) invocation.getArguments()[1])
                    .setValues(mockedStatement);
            return null;
        }).when(mockedjdbcTemplate)
                .update(eq("UPDATE GOODS SET NAME = ?, BARCODE = ?, PRICE = ? WHERE ID = ?"),
                        any(PreparedStatementSetter.class));
        Goods goods = dao.update(existingGoods);
        assertTrue(goods.equals(existingGoods));
    }

    private List createListOfMap() {
        List<Map<String, Object>> listToMapFrom = new ArrayList<>();
        Map<String, Object> goods = new HashMap<>();
        goods.put("ID", existingGoods.getId());
        goods.put("NAME", existingGoods.getName());
        goods.put("BARCODE", existingGoods.getBarcode());
        goods.put("PRICE", existingGoods.getPrice());
        listToMapFrom.add(goods);
        return listToMapFrom;
    }
}

