package com.ra.shop.repository.implementation;

import com.ra.shop.exceptions.RepositoryException;
import com.ra.shop.model.Goods;
import org.junit.jupiter.api.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

class GoodsRepositoryMockTest {

    private static GoodsRepositoryImpl dao;
    private static JdbcTemplate mockJdbcTemplate;
    private static Goods TEST_GOODS = new Goods("Camel", 7622210609779L, 1.2d);
    private Connection mockConnection;
    private PreparedStatement mockStatement;
    private KeyHolder mockGeneratedKeyHolder;
    private static final Long DEFAULT_ID = 1L;
    private static final Long GENERATION_ID = 2L;

    @BeforeAll
    static void createSchema() {
        TEST_GOODS.setId(DEFAULT_ID);
    }

    @BeforeEach
    void setUp() {
        mockJdbcTemplate = mock(JdbcTemplate.class);
        dao = new GoodsRepositoryImpl(mockJdbcTemplate);
        mockGeneratedKeyHolder = mock(KeyHolder.class);
        mockConnection = mock(Connection.class);
        mockStatement = mock(PreparedStatement.class);
        when(mockGeneratedKeyHolder.getKey()).thenReturn(GENERATION_ID);
    }

    @Test
    void whenCreatedGoodsWithFalseNextIdThenReturnEqualsGoods() throws RepositoryException, SQLException {
        when(mockConnection.prepareStatement("INSERT INTO GOODS (NAME, BARCODE, PRICE) VALUES (?,?,?)"))
                .thenReturn(mockStatement);
        doAnswer(invocation -> {
            ((PreparedStatementCreator) invocation.getArguments()[0]).createPreparedStatement(mockConnection);
            return null;
        }).when(mockJdbcTemplate).update(any(PreparedStatementCreator.class), any(KeyHolder.class));

        Goods resultGoods = dao.create(TEST_GOODS);
        TEST_GOODS.setId((Long) mockGeneratedKeyHolder.getKey());

        assertEquals(TEST_GOODS, resultGoods);
    }

    @Test
    void whenReadAllGoodThenReturnsNotEmptyList() throws Exception {
        when(mockJdbcTemplate.queryForList("SELECT * FROM GOODS")).thenReturn(createListOfMap());

        assertFalse(dao.getAll().isEmpty());
    }

    @Test
    void whenDeleteGoodsThenReturnTrue() throws RepositoryException {
        when(mockJdbcTemplate.update("DELETE FROM GOODS WHERE ID = ?", TEST_GOODS.getId())).thenReturn(1);

        assertTrue(dao.delete(TEST_GOODS.getId()));
    }

    @Test
    void whenGoodsCannotBeDeletedThenReturnFalse() throws RepositoryException {
        when(mockJdbcTemplate.update("DELETE FROM GOODS WHERE ID = ?", TEST_GOODS.getId())).thenReturn(-1);

        assertFalse(dao.delete(TEST_GOODS.getId()));
    }

    @Test
    void whenGetAllGoodsThrowsExceptionThenRepositoryExceptionThrown() {
        when(mockJdbcTemplate.queryForList(anyString())).thenThrow(new EmptyResultDataAccessException(0));

        assertThrows(RepositoryException.class, () -> dao.getAll());
    }

    @Test
    void whenGetGoodsWithTrueNextGoodsThenReturnEqualsGoods() throws RepositoryException {
        when(mockJdbcTemplate.queryForObject(eq("SELECT * FROM GOODS WHERE ID = ?"), any(RowMapper.class),
                eq(TEST_GOODS.getId()))).thenReturn(TEST_GOODS);

        assertEquals(dao.get(TEST_GOODS.getId()), TEST_GOODS);
    }

    @Test
    void getAGoodByIdFailsWithExceptionAsFeedbackToClient() {
        when(mockJdbcTemplate.queryForObject(anyString(), any(RowMapper.class), any(Long.class)))
                .thenThrow(new EmptyResultDataAccessException(0));

        assertThrows(RepositoryException.class, () -> dao.get(TEST_GOODS.getId()));
    }

    @Test
    void deletingAGoodsFailsWithExceptionAsFeedbackToTheClient() {
        when(mockJdbcTemplate.update(anyString(), anyInt())).thenThrow(new EmptyResultDataAccessException(0));

        assertThrows(RepositoryException.class, () -> dao.delete(TEST_GOODS.getId()));
    }

    @Test
    void updatingAGoodsFailsWithFeedbackToTheClient() {
        when(mockJdbcTemplate.update(anyString(), any(PreparedStatementSetter.class)))
                .thenThrow(new EmptyResultDataAccessException(0));

        assertThrows(RepositoryException.class, () -> dao.update(TEST_GOODS));
    }

    @Test
    void createAGoodsFailsWithExceptionAsFeedbackToClient() {
        when(mockJdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class)))
                .thenThrow(new EmptyResultDataAccessException(0));

        assertThrows(RepositoryException.class, () -> dao.create(TEST_GOODS));
    }

    @Test
    void whenUpdateGoodsThenReturnEqualsGoods() throws RepositoryException {
        doAnswer(invocation -> {
            ((PreparedStatementSetter) invocation.getArguments()[1]).setValues(mockStatement);
            return null;
        }).when(mockJdbcTemplate)
                .update(eq("UPDATE GOODS SET NAME = ?, BARCODE = ?, PRICE = ? WHERE ID = ?"),
                        any(PreparedStatementSetter.class));
        Goods goods = dao.update(TEST_GOODS);

        assertEquals(goods, TEST_GOODS);
    }

    private List createListOfMap() {
        List<Map<String, Object>> listToMapFrom = new ArrayList<>();
        Map<String, Object> goods = new HashMap<>();
        goods.put("ID", TEST_GOODS.getId());
        goods.put("NAME", TEST_GOODS.getName());
        goods.put("BARCODE", TEST_GOODS.getBarcode());
        goods.put("PRICE", TEST_GOODS.getPrice());
        listToMapFrom.add(goods);
        return listToMapFrom;
    }
}

