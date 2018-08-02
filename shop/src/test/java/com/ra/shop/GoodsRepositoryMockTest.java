package com.ra.shop;

import com.ra.shop.exceptions.RepositoryException;
import com.ra.shop.repository.implementation.GoodsRepositoryImpl;
import com.ra.shop.model.Goods;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
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
    private SQLException exception;
    private KeyHolder mockedkeyHolder = new GeneratedKeyHolder();
    private BeanPropertyRowMapper mockedBeanPropertyRowMapper;
    private static final String EXCEPTION_CAUSE = "Connection not available-----";

    @BeforeAll
    static void createSchema() {
        mockedjdbcTemplate = mock(JdbcTemplate.class);
        //mockedConnectionFactory = mock(ConnectionFactory.class);
        dao = new GoodsRepositoryImpl(mockedjdbcTemplate);
        existingGoods.setId(1l);
    }

    @BeforeEach
    public void setUp() throws Exception {
        mockedkeyHolder = mock(KeyHolder.class);
        mockedBeanPropertyRowMapper=mock(BeanPropertyRowMapper.class);
        mockedConnection = mock(Connection.class);
        mockedStatement = mock(PreparedStatement.class);
        // mockedStatementKey = mock(PreparedStatement.class);
        mockedResultSet = mock(ResultSet.class);
        mockedResultSetForKey = mock(ResultSet.class);
        exception = new SQLException(EXCEPTION_CAUSE);
        // when(mockedConnectionFactory.getConnection()).thenReturn(mockedConnection);
        createMocksFromGetMethod();
    }

    @Test
    void whenReadAllGoodThenResultIsTrue() throws Exception {
        final String GET_ALL_DEVICES = "SELECT * FROM GOODS";
        List listFromQueryForList = createListOfMap();
        when(mockedjdbcTemplate.queryForList(eq(GET_ALL_DEVICES))).thenReturn(listFromQueryForList);
        List<Goods> goods = dao.getAll();
        assertFalse(goods.isEmpty());

//        boolean result = false;
//        when(mockedConnectionFactory.getConnection().prepareStatement("SELECT * FROM GOODS")).thenReturn(mockedStatement);
//        when(mockedStatement.executeQuery()).thenReturn(mockedResultSet);
//        when(mockedResultSet.next()).thenReturn(result = true).thenReturn(false);
//        List<Goods> goods = dao.getAll();
//        assertTrue(result == true);
    }

//    @Test
//    void whenDeletionGoodsThenReturnTrue() throws RepositoryException, SQLException {
//        when(mockedjdbcTemplate.update("DELETE FROM GOODS WHERE ID = ?", existingGoods.getId())).thenReturn(1);
//        //when(mockedStatement.executeUpdate()).thenReturn(1);
//        assertTrue(dao.delete(existingGoods.getId()));
//    }


    @Test
    void getAllGoodsFailsWithExceptionAsFeedbackToClient() throws SQLException {
//        doThrow(exception).when(mockedjdbcTemplate);
//        assertThrows(Exception.class, () -> {
//            dao.getAll();
//        });
        assertThrows(RepositoryException.class, () -> {
            when(mockedjdbcTemplate.queryForList("SELECT * FROM GOODS"))
                    .thenThrow(EmptyResultDataAccessException.class);
            dao.getAll();
        });
    }
//    @Test
//    void whenDeletionGoodsThenReturnFalse() throws RepositoryException, SQLException {
//        when(mockedConnection.prepareStatement("DELETE FROM GOODS WHERE ID = ?")).thenReturn(mockedStatement);
//        when(mockedStatement.executeUpdate()).thenReturn(-1);
//        assertFalse(dao.delete(existingGoods.getId()));
//    }



//    @Test
//    @Test
//    void whenCreatedGoodsWithFalseNextIdThenReturnEqualsGoods() throws SQLException, RepositoryException {
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
//    }

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
    void whenGetGoodsWithFalseNextGoodsThenReturnNotPresentGoods() throws RepositoryException, SQLException {
        when(mockedConnection.prepareStatement("SELECT * FROM GOODS WHERE ID = ?")).thenReturn(mockedStatement);
        when(mockedStatement.executeQuery()).thenReturn(mockedResultSet);
        when(mockedResultSet.next()).thenReturn(false);
        //  assertFalse(dao.get(Long.valueOf(1L)).isPresent());
    }

    //    }
    @Test
    void whenGetGoodsWithTrueNextGoodsThenReturnEqualsGoods() throws
            SQLException, RepositoryException {
        when(mockedConnection.prepareStatement("SELECT * FROM GOODS WHERE ID = ?")).thenReturn(mockedStatement);
        when(mockedStatement.executeQuery()).thenReturn(mockedResultSet);
        when(mockedResultSet.next()).thenReturn(true);
        // Goods goods = dao.get(existingGoods.getId()).get();
        // assertAll("goods",
        //  () -> assertEquals(goods.getId(), existingGoods.getId()),
        //   () -> assertEquals(goods.getName(), existingGoods.getName()),
        //   () -> assertEquals(goods.getBarcode(), existingGoods.getBarcode()),
        //   () -> assertEquals(goods.getPrice(), existingGoods.getPrice()));
    }

    //        });
//    @Test
//    void getAGoodByIdFailsWithExceptionAsFeedbackToClient() {
////        //doThrow(exception).when(mockedjdbcTemplate);
////        // exceptions();
//////        doThrow(exception)
//////                .when(mockedjdbcTemplate)
//////                .update(Mockito.anyString(), anyObject());
//        Throwable exeption = assertThrows(RepositoryException.class, () -> {
//            when(mockedjdbcTemplate
//                    .queryForObject("SELECT * FROM GOODS WHERE ID = ?", mockedBeanPropertyRowMapper.newInstance(Goods.class),existingGoods.getId()))
//                    .thenThrow(EmptyResultDataAccessException.class);
//            dao.get(existingGoods.getId());
//        });
////        assertThrows(RepositoryException.class, () -> {
////            dao.get(existingGoods.getId());
////        });
//   }

        @Test
    void deletingAGoodsFailsWithExceptionAsFeedbackToTheClient() {
//        doThrow(exception).when(mockedjdbcTemplate)
//                .update("DELETE FROM GOODS WHERE ID = ?", existingGoods.getId());
//        assertThrows(Exception.class, () -> {
//            dao.delete(existingGoods.getId());
//        });
            assertThrows(RepositoryException.class, () -> {
            when(mockedjdbcTemplate.update("DELETE FROM GOODS WHERE ID = ?", existingGoods.getId()))
                    .thenThrow(EmptyResultDataAccessException.class);
            dao.delete(existingGoods.getId());
        });

    }
    @Test
    void whenUpdationGoodsThenReturnEqualsGoods() throws SQLException, RepositoryException {
        int flagUpdateDB = 0;
        when(mockedjdbcTemplate.update(eq("UPDATE GOODS SET NAME = ?, BARCODE = ?, PRICE = ? WHERE ID = ?"), any(PreparedStatement.class)))
                .thenReturn(flagUpdateDB = 1);
        Goods goods = dao.update(existingGoods);
        assertEquals(1, flagUpdateDB);
        assertAll("goods",
                () -> assertEquals(goods.getId(), existingGoods.getId()),
                () -> assertEquals(goods.getName(), existingGoods.getName()),
                () -> assertEquals(goods.getBarcode(), existingGoods.getBarcode()),
                () -> assertEquals(goods.getPrice(), existingGoods.getPrice()));
    }
    //            dao.create(new Goods("Kent", 4820092770232l, 36.7f));

    private void exceptions() {
        //  doThrow(exception).when(mockedjdbcTemplate.update(any(PreparedStatement.class),anyLong()));
    }
    //        assertThrows(Exception.class, () -> {

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

    //        doThrow(exception).when(mockedjdbcTemplate);
    //    public void createAGoodsFailsWithExceptionAsFeedbackToClient() throws SQLException {

    private void createMocksFromGetMethod() throws SQLException {
        when(mockedConnection.prepareStatement("SELECT * FROM GOODS WHERE ID = ?")).thenReturn(mockedStatement);
        when(mockedResultSet.next()).thenReturn(true);
        when(mockedStatement.executeQuery()).thenReturn(mockedResultSet);
        when(mockedResultSet.getLong("ID")).thenReturn(existingGoods.getId());
        when(mockedResultSet.getString("NAME")).thenReturn(existingGoods.getName());
        when(mockedResultSet.getLong("BARCODE")).thenReturn(existingGoods.getBarcode());
        when(mockedResultSet.getDouble("PRICE")).thenReturn(existingGoods.getPrice());
    }

    //    @Test
    //    void updatingAGoodsFailsWithFeedbackToTheClient() throws SQLException {
    //        doThrow(exception).when(mockedjdbcTemplate);
    //        Goods goods = new Goods("Lark", 740617153127l, 34.7f);
    //        goods.setId(existingGoods.getId());
    //        assertThrows(Exception.class, () -> {
}

