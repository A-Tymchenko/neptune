package com.ra.shop;

import com.ra.shop.config.ConnectionFactory;
import com.ra.shop.exceptions.RepositoryException;
import com.ra.shop.repository.implementation.GoodsRepositoryImpl;
import com.ra.shop.model.Goods;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.*;

import java.io.*;
import java.sql.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class GoodsRepositoryImplTest {

//    private GoodsRepositoryImpl dao;
//    private static final String CREATE_TABLE_GOODS = "src/test/resources/create_table.sql";
//    private static final String DROP_TABLE_GOODS = "src/test/resources/drop_table.sql";
//    private Goods existingGoods = new Goods("Camel", 7622210609779l, 1.2f);
//    private Long existingGoodsID;
//
//    private void createDataBaseTable() throws IOException, SQLException {
//        Connection connection = ConnectionFactory.getInstance().getConnection();
//        RunScript.execute(connection, new FileReader(CREATE_TABLE_GOODS));
//    }
//
//    private void deleteDataBaseTable() throws IOException, SQLException {
//        Connection connection = ConnectionFactory.getInstance().getConnection();
//        RunScript.execute(connection, new FileReader(DROP_TABLE_GOODS));
//    }
//
//    @BeforeEach
//    public void createSchema() throws SQLException, IOException, RepositoryException {
//        createDataBaseTable();
//    //    dao = new GoodsRepositoryImpl(ConnectionFactory.getInstance());
//        existingGoods.setId(1l);
//        Goods result = dao.create(existingGoods);
//        existingGoodsID = result.getId();
//        Assertions.assertTrue(result.equals(dao.get(result.getId()).get()));
//    }
//
//    @AfterEach
//    void deleteSchema() throws SQLException, IOException {
//        deleteDataBaseTable();
//    }
//
//    @Test
//    public void addingShouldResultInSuccess() throws Exception {
//        List<Goods> goods = dao.getAll();
//        assumeTrue(goods.size() == 1);
//
//        final Goods nonExistingGoods = new Goods("Parlament", 7622210722416l, 2.6F);
//        nonExistingGoods.setId(0);
//        Goods result = dao.create(nonExistingGoods);
//        assertTrue(result.equals(dao.get(result.getId()).get()));
//
//        assertGoodsCountIs(2);
//        assertTrue(nonExistingGoods.equals(dao.get(nonExistingGoods.getId()).get()));
//    }
//
//    @Test
//    void deletionShouldBeFailureAndNotAffectExistingGoods() throws RepositoryException {
//        final Goods nonExistingGoods = new Goods("Marlboro", 4050300003924l, 3.4f);
//        nonExistingGoods.setId(getNonExistingGoodId());
//        assertGoodsCountIs(1);
//        assertFalse(dao.delete(nonExistingGoods.getId()));
//        assertGoodsCountIs(1);
//    }
//
//    @Test
//    void updationShouldBeFailureAndNotAffectExistingGoods() throws RepositoryException, IOException, SQLException {
//        final Long nonExistingId = getNonExistingGoodId();
//        final String newName = "Dunhill";
//        final Long newBarcode = 4820005924653l;
//        final Float newPrice = 12.6f;
//        final Goods goods = new Goods(newName, newBarcode, newPrice);
//        goods.setId(nonExistingId);
//        assertThrows(NoSuchElementException.class, () -> {
//            dao.get(dao.update(goods).getId()).get();
//        });
//    }
//
//    @Test
//    void getShouldReturnNoGoods() throws RepositoryException {
//        assertFalse(dao.get(getNonExistingGoodId()).isPresent());
//    }
//
//    @Test
//    void addingShouldResultInFailureAndNotAffectExistingGoods() throws RepositoryException {
//        Goods existingGoods = new Goods("Camel", 7622210609779l, 1.2f);
//        existingGoods.setId(existingGoodsID);
//        Goods result = dao.create(existingGoods);
//        assertTrue(result.equals(existingGoods));
//
//        assertGoodsCountIs(2);
//        assertEquals(existingGoods, dao.get(existingGoods.getId()).get());
//    }
//
//    @Test
//    void deletionShouldBeSuccessAndGoodsShouldBeNonAccessible() throws RepositoryException {
//        boolean result = dao.delete(existingGoods.getId());
//
//        assertEquals(true, result);
//        assertGoodsCountIs(0);
//        assertFalse(dao.get(existingGoods.getId()).isPresent());
//    }
//
//    @Test
//    void updationShouldBeSuccessAndAccessingTheSameGoodsShouldReturnUpdatedInformation() throws RepositoryException {
//        final String newName = "L&M";
//        final Long newBarcode = 740617152326l;
//        final Float newPrice = 32.7f;
//        final Goods goods = new Goods(newName, newBarcode, newPrice);
//        goods.setId(existingGoods.getId());
//        Goods result = dao.update(goods);
//
//        assertTrue(goods.equals(result));
//
//        final Goods goodsGet = dao.get(existingGoods.getId()).get();
//        assertEquals(newName, goodsGet.getName());
//        assertEquals(newBarcode, goodsGet.getBarcode(), 0.0002);
//        assertEquals(newPrice, goodsGet.getPrice(), 0.0002);
//    }
//
//    private void assertGoodsCountIs(int count) throws RepositoryException {
//        List<Goods> allGoods = dao.getAll();
//        assertTrue(allGoods.size() == count);
//    }
//
//    private Long getNonExistingGoodId() {
//        return 999l;
//    }
}

