package com.ra.shop;

import com.ra.shop.exceptions.RepositoryException;
import com.ra.shop.repository.implementation.GoodsRepositoryImpl;
import com.ra.shop.model.Goods;
import org.junit.jupiter.api.*;
import org.mockito.stubbing.Answer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class GoodsRepositoryMockTest {

    private static GoodsRepositoryImpl dao;
    private static JdbcTemplate mockedjdbcTemplate;
    private static Goods existingGoods = new Goods("Camel", 7622210609779l, 1.2f);
    //private static ConnectionFactory mockedConnectionFactory;
    private Connection mockedConnection;
    private PreparedStatement mockedStatement;
    private PreparedStatement mockedStatementKey;
    private ResultSet mockedResultSet;
    private ResultSet mockedResultSetForKey;
    //  private SQLException exception;
    private KeyHolder mockedgeneratedKeys = new GeneratedKeyHolder();
   // private KeyHolder mockedkeyHolder = new GeneratedKeyHolder();
    //private KeyHolder mockedkeyHolder;
    private BeanPropertyRowMapper mockedBeanPropertyRowMapper;
    // private static final String EXCEPTION_CAUSE = "Connection not available-----";
    private static final Long DEFAULT_ID =1l;
    private static final Long GENERATION_ID =2l;

    @BeforeAll
    static void createSchema() {
        mockedjdbcTemplate = mock(JdbcTemplate.class);
        //mockedConnectionFactory = mock(ConnectionFactory.class);
        dao = new GoodsRepositoryImpl(mockedjdbcTemplate);
        existingGoods.setId(DEFAULT_ID);
    }

    @BeforeEach
    public void setUp() throws Exception {
        mockedgeneratedKeys = mock(KeyHolder.class);
        mockedBeanPropertyRowMapper = mock(BeanPropertyRowMapper.class);
        mockedConnection = mock(Connection.class);
        mockedStatement = mock(PreparedStatement.class);
        // mockedStatementKey = mock(PreparedStatement.class);
        mockedResultSet = mock(ResultSet.class);
        mockedResultSetForKey = mock(ResultSet.class);
        when(mockedConnection.prepareStatement("INSERT INTO GOODS (NAME, BARCODE, PRICE) VALUES (?,?,?)"))
                .thenReturn(mockedStatement);
//        when(mockConnection.prepareStatement(UPDATE_FLIGHT_SQL)).thenReturn(mockPreparedStatement);
//        when(mockJdbcTemplate.queryForObject(SELECT_LAST_GENERATED_ID_SQL, Integer.class)).thenReturn(1);
//        when(mockJdbcTemplate.queryForObject(eq(SELECT_FLIGHT_BY_ID_SQL), any(Object[].class), any(RowMapper.class))).thenReturn(flight);
//

        //  exception = new SQLException(EXCEPTION_CAUSE);
        // when(mockedConnectionFactory.getConnection()).thenReturn(mockedConnection);
        //createMocksFromGetMethod();
    }

    @Test
    void whenCreatedGoodsWithFalseNextIdThenReturnEqualsGoods() throws RepositoryException {
        //KeyHolder mockedgeneratedKeys = new GeneratedKeyHolder();
//        when(mockedjdbcTemplate.update(eq("INSERT INTO GOODS (NAME, BARCODE, PRICE) VALUES (?,?,?)"),
//                eq(mockedgeneratedKeys)))
//                .thenReturn(1);
        doAnswer(invocation -> {
            ((PreparedStatementCreator) invocation.getArguments()[0]).createPreparedStatement(mockedConnection);
            return null;
        }).when(mockedjdbcTemplate).update(any(PreparedStatementCreator.class), any(KeyHolder.class));
//        when(mockedjdbcTemplate.update(any(PreparedStatementCreator.class),
//                any(KeyHolder.class)))
//                .thenReturn(1);
        when(mockedgeneratedKeys.getKey()).thenReturn(2L);
//        doReturn(GENERATION_ID)
//                .when(mockedgeneratedKeys).getKey();
        //Device deviceCreated = deviceDao.create(deviceNoId);
        //System.out.println(existingGoods);
        Goods goods = dao.create(existingGoods);
        //deviceNoId.setDevId((Long) mockkeyHolder.getKey());
       // System.out.println(mockedgeneratedKeys.getKey());
       // System.out.println(goods);
        existingGoods.setId( (Long) mockedgeneratedKeys.getKey());
        // System.out.println(goods);
                assertTrue(goods.equals(existingGoods));
//        assertAll("goods",
//                () -> assertEquals(goods.getId(), existingGoods.getId()),
//                () -> assertEquals(goods.getName(), existingGoods.getName()),
//                () -> assertEquals(goods.getBarcode(), existingGoods.getBarcode()),
//                () -> assertEquals(goods.getPrice(), existingGoods.getPrice()));



//        when(mockedConnection.prepareStatement("INSERT INTO GOODS (NAME, BARCODE, PRICE) VALUES (?,?,?)"))
//                .thenReturn(mockedStatement);
//        when(mockedStatement.executeUpdate()).thenReturn(1);
//        when(mockedConnection.prepareStatement("SELECT LAST_INSERT_ID()"))
//                .thenReturn(mockedStatementKey);
//        when(mockedStatementKey.executeQuery()).thenReturn(mockedResultSetForKey);
//        when(mockedResultSetForKey.next()).thenReturn(false);
//        Goods goods = dao.create(existingGoods);
//        assertAll("goods",
//                () -> assertEquals(goods.getId(), existingGoods.getId()),
//                () -> assertEquals(goods.getName(), existingGoods.getName()),
//                () -> assertEquals(goods.getBarcode(), existingGoods.getBarcode()),
//                () -> assertEquals(goods.getPrice(), existingGoods.getPrice()));
//        assertThrows(RepositoryException.class, () -> {
//            when(mockedjdbcTemplate.update(any(PreparedStatementCreator.class),
//                    any(KeyHolder.class)))
//                    .thenThrow(EmptyResultDataAccessException.class);
//            dao.create(existingGoods);
//        });
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


    //    }
//    @Test
//    void whenCreatedGoodsWithTrueNextIdThenReturnNotEqualsGoods() throws SQLException, RepositoryException {
//        when(mockedConnection.prepareStatement("INSERT INTO GOODS (NAME, BARCODE, PRICE) VALUES (?,?,?)"))
//                .thenReturn(mockedStatement);
//        when(mockedConnection.prepareStatement("SELECT LAST_INSERT_ID()"))
//                .thenReturn(mockedStatementKey);
//        when(mockedStatementKey.executeQuery()).thenReturn(mockedResultSetForKey);
//        when(mockedResultSetForKey.next()).thenReturn(true);
//        when(mockedResultSetForKey.getLong(1)).thenReturn(2l);
//        Goods goods = dao.create(existingGoods);
//        assertEquals(existingGoods.getId(), 2l, 0.0002);
//        assertAll("goods",
//                () -> assertEquals(goods.getId(), existingGoods.getId()),
//                () -> assertEquals(goods.getName(), existingGoods.getName()),
//                () -> assertEquals(goods.getBarcode(), existingGoods.getBarcode()),
//                () -> assertEquals(goods.getPrice(), existingGoods.getPrice()));
//    }

    //        });

    //            dao.update(goods);

    @Test
    void whenGetGoodsWithTrueNextGoodsThenReturnEqualsGoods() throws RepositoryException {
        when(mockedjdbcTemplate.queryForObject(eq("SELECT * FROM GOODS WHERE ID = ?"),
                any(RowMapper.class),
                eq(existingGoods.getId()))).thenReturn(existingGoods);
        assertTrue(dao.get(existingGoods.getId()).equals(existingGoods));
    }

    //    }
//    @Test
//    void whenGetGoodsWithTrueNextGoodsThenReturnEqualsGoods1() throws
//            SQLException, RepositoryException {
//        when(mockedjdbcTemplate.queryForObject(eq("SELECT * FROM GOODS WHERE ID = ?"),
//                mockedBeanPropertyRowMapper.newInstance(Goods.class),eq(existingGoods.getId()))).thenReturn(existingGoods);
//        when(mockedStatement.executeQuery()).thenReturn(mockedResultSet);
//        when(mockedResultSet.next()).thenReturn(true);
//        // Goods goods = dao.get(existingGoods.getId()).get();
//        // assertAll("goods",
//        //  () -> assertEquals(goods.getId(), existingGoods.getId()),
//        //   () -> assertEquals(goods.getName(), existingGoods.getName()),
//        //   () -> assertEquals(goods.getBarcode(), existingGoods.getBarcode()),
//        //   () -> assertEquals(goods.getPrice(), existingGoods.getPrice()));
//    }

    //        });


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
    public void createAGoodsFailsWithExceptionAsFeedbackToClient() throws SQLException {

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

//    private void createMocksFromGetMethod() throws SQLException {
//        when(mockedConnection.prepareStatement(eq("SELECT * FROM GOODS WHERE ID = ?"))).thenReturn(mockedStatement);
//        when(mockedResultSet.next()).thenReturn(true);
//        when(mockedStatement.executeQuery()).thenReturn(mockedResultSet);
//        when(mockedResultSet.getLong("ID")).thenReturn(existingGoods.getId());
//        when(mockedResultSet.getString("NAME")).thenReturn(existingGoods.getName());
//        when(mockedResultSet.getLong("BARCODE")).thenReturn(existingGoods.getBarcode());
//        when(mockedResultSet.getDouble("PRICE")).thenReturn(existingGoods.getPrice());
//    }

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

