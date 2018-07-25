package com.ra.shop;

import com.ra.shop.config.ConnectionFactory;
import com.ra.shop.exceptions.DAOException;
import com.ra.shop.repository.implementation.GoodsDaoImpl;
import com.ra.shop.model.Goods;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.*;

import java.io.FileReader;
import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class GoodsDaoImplTest {

    private static final String CREATE_TABLE_GOODS = "src/test/resources/create_table.sql";
    private static final String DROP_TABLE_GOODS = "src/test/resources/drop_table.sql";
    private GoodsDaoImpl dao;
    private Goods existingGoods = new Goods("Camel", 7622210609779l, 1.2f);
    private Long existingGoodsID;

    private void createDataBaseTable() throws IOException, SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        RunScript.execute(connection, new FileReader(CREATE_TABLE_GOODS));
    }

    private void deleteDataBaseTable() throws IOException, SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        RunScript.execute(connection, new FileReader(DROP_TABLE_GOODS));
    }

    @BeforeEach
    public void createSchema() throws SQLException, IOException {
        createDataBaseTable();
    }

    @AfterEach
    public void deleteSchema() throws SQLException, IOException {
        deleteDataBaseTable();
    }

    @Nested
    public class ConnectionSuccess {

        @BeforeEach
        public void setUp() throws Exception {
            dao = new GoodsDaoImpl(ConnectionFactory.getInstance());
            existingGoods.setId(1l);
            Goods result = dao.create(existingGoods);
            existingGoodsID = result.getId();
            Assertions.assertTrue(result.equals(dao.get(result.getId()).get()));
        }

        /**
         * Represents the scenario when DAO operations are being performed on a non existing goods.
         */
        @Nested
        public class NonExistingGoods {

            @Test
            public void addingShouldResultInSuccess() throws Exception {
                List<Goods> goods = dao.getAll();
                assumeTrue(goods.size() == 1);

                final Goods nonExistingGoods = new Goods("Parlament", 7622210722416l, 2.6F);
                nonExistingGoods.setId(null);
                Goods result = dao.create(nonExistingGoods);
                assertTrue(result.equals(dao.get(result.getId()).get()));

                assertGoodsCountIs(2);
                assertTrue(nonExistingGoods.equals(dao.get(nonExistingGoods.getId()).get()));
            }

            @Test
            public void deletionShouldBeFailureAndNotAffectExistingGoods() throws DAOException {
                final Goods nonExistingGoods = new Goods("Marlboro", 4050300003924l, 3.4f);
                nonExistingGoods.setId(getNonExistingGoodId());
                assertGoodsCountIs(1);
                assertFalse(dao.delete(nonExistingGoods.getId()));
                assertGoodsCountIs(1);
            }

            @Test
            public void updationShouldBeFailureAndNotAffectExistingGoods() throws DAOException {
                final Long nonExistingId = getNonExistingGoodId();
                final String newName = "Dunhill";
                final Long newBarcode = 4820005924653l;
                final Float newPrice = 12.6f;
                final Goods goods = new Goods(newName, newBarcode, newPrice);
                goods.setId(nonExistingId);
                assertThrows(NoSuchElementException.class, () -> dao.update(goods));
            }

            @Test
            public void retrieveShouldReturnNoGoods() throws DAOException {
                assertFalse(dao.get(getNonExistingGoodId()).isPresent());
            }
        }

        /**
         * Represents a scenario where DAO operations are being performed on an already existing
         * good.
         */
        @Nested
        public class ExistingGoods {

            @Test
            public void addingShouldResultInFailureAndNotAffectExistingGoods() throws DAOException {
                Goods existingGoods = new Goods("Camel", 7622210609779l, 1.2f);
                existingGoods.setId(existingGoodsID);
                Goods result = dao.create(existingGoods);
                assertTrue(result.equals(existingGoods));

                assertGoodsCountIs(2);
                assertEquals(existingGoods, dao.get(existingGoods.getId()).get());
            }

            @Test
            public void deletionShouldBeSuccessAndGoodsShouldBeNonAccessible() throws DAOException {
                boolean result = dao.delete(existingGoods.getId());

                assertEquals(true, result);
                assertGoodsCountIs(0);
                assertFalse(dao.get(existingGoods.getId()).isPresent());
            }

            @Test
            public void updationShouldBeSuccessAndAccessingTheSameGoodsShouldReturnUpdatedInformation() throws DAOException {
                final String newName = "L&M";
                final Long newBarcode = 740617152326l;
                final Float newPrice = 32.7f;
                final Goods goods = new Goods(newName, newBarcode, newPrice);
                goods.setId(existingGoods.getId());
                Goods result = dao.update(goods);

                assertTrue(goods.equals(result));

                final Goods goodsGet = (Goods) dao.get(existingGoods.getId()).get();
                assertEquals(newName, goodsGet.getName());
                assertEquals(newBarcode, goodsGet.getBarcode(), 0.0002);
                assertEquals(newPrice, goodsGet.getPrice(), 0.0002);
            }
        }

        @Nested
        public class ExistingGoodsWithNull {

            private Goods existingGoodsNullId = new Goods("Marlboro", 4050300003924l, 3.4f);

            @BeforeEach
            public void setUp() {
                existingGoodsNullId.setId(null);
            }

            @Test
            public void addingResultWithNullId() throws Exception {
                assertGoodsCountIs(1);
                existingGoods.setId(null);
                Goods result = dao.create(existingGoodsNullId);
                assertGoodsCountIs(2);
                assertTrue(result.equals(existingGoodsNullId));
                assertTrue(existingGoodsNullId.equals(dao.get(existingGoodsNullId.getId()).get()));
            }

            @Test
            public void deletingResultWithNullGoods() {
                assertThrows(NullPointerException.class, () -> dao.delete(null));
            }

            @Test
            public void updatingResultWithNullGoods() {
                assertThrows(NullPointerException.class, () -> dao.update(existingGoodsNullId));
            }

            @Test
            public void getingResultWithNullId() {
                assertThrows(DAOException.class, () -> dao.get(null));
            }
        }

        @Nested
        public class ExistingGoodsWithZeroId {

            private Goods existingGoodsZeroId = new Goods("Marlboro", 4050300003924l, 3.4f);

            @BeforeEach
            public void setUp() {
                existingGoodsZeroId.setId(0l);
            }

            @Test
            public void addingGoodsWithZeroId() throws Exception {
                assertGoodsCountIs(1);
                existingGoodsZeroId.setId(0l);
                Goods resultZero = dao.create(existingGoodsZeroId);
                assertGoodsCountIs(2);
                assertTrue(resultZero.equals(existingGoodsZeroId));
                assertTrue(existingGoodsZeroId.equals(dao.get(existingGoodsZeroId.getId()).get()));
            }

            @Test
            public void deletingResultWithZeroId() throws Exception {
                assertGoodsCountIs(1);
                assertFalse(dao.delete(0l));
                assertGoodsCountIs(1);
            }

            @Test
            public void updatingResultWithZeroId() throws Exception {
                assertGoodsCountIs(1);
                assertThrows(NoSuchElementException.class, () -> dao.update(existingGoodsZeroId));
            }

            @Test
            public void getingResultWithZeroID() throws Exception {
                assertGoodsCountIs(1);
                assertFalse(dao.get(0l).isPresent());
            }
        }

        @Test
        public void getingResultWithZeroList() throws Exception {
            assertGoodsCountIs(1);
            dao.delete(existingGoodsID);
            assertGoodsCountIs(0);
            assumeTrue(dao.getAll().size() == 0);
        }
    }

    private void assertGoodsCountIs(int count) throws DAOException {
        List<Goods> allGoods = dao.getAll();
        assertTrue(allGoods.size() == count);
    }

    private Long getNonExistingGoodId() {
        return 999l;
    }
}

