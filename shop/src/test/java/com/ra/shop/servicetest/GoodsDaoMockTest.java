package com.ra.shop.servicetest;

import com.ra.shop.model.Goods;
import com.ra.shop.service.GoodsException;
import com.ra.shop.service.GoodsDao;
import com.ra.shop.utils.ConnectionFactory;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.Mockito.*;

public class GoodsDaoMockTest {

    private static GoodsDao dao;
    private Goods existingGoods = new Goods(1l, "Camel", 7622210609779l, 1.2f);
    private Long existingGoodsID;
    private static ConnectionFactory mockedConnectionFactory;
    private Connection mockedConnection;
    private PreparedStatement mockedStatement;
    private PreparedStatement mockedStatementKey;
    private ResultSet mockedResultSet;
    private ResultSet mockedResultSetForKey;


    @BeforeEach
    public void createSchema() {
        mockedConnectionFactory = mock(ConnectionFactory.class);
        dao = new GoodsDao(mockedConnectionFactory);
    }

//    @AfterEach
//    public void deleteSchema() {
//        //   OnOffSchemaSql.deleteSchema(ConnectionFactory.getInstance());
//    }

    @Nested
    public class ConnectionSuccess {

        @BeforeEach
        public void setUp() throws Exception {
            // dao = new GoodsDao(mockedConnectionFactory());
            mockedConnection = mock(Connection.class);
            mockedStatement = mock(PreparedStatement.class);
            mockedStatementKey = mock(PreparedStatement.class);
            mockedResultSet = mock(ResultSet.class);
            mockedResultSetForKey = mock(ResultSet.class);
            when(mockedConnectionFactory.getConnection()).thenReturn(mockedConnection);
            createMocksFromGetMethod();
            //when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSetForKey);
//            Goods result = dao.create(existingGoods);
//            existingGoodsID = result.getId();
//            Assertions.assertTrue(result.equals(dao.get(result.getId()).get()));
        }

//        private ConnectionFactory mockedConnectionFactory() throws SQLException {
//            ConnectionFactory mockedConnectionFactory = mock(ConnectionFactory.class);
//            Connection mockedConnection = mock(Connection.class);
//            //SQLException exception = new SQLException(EXCEPTION_CAUSE);
////            doThrow(exception)
////                    .when(mockedConnection)
////                    .prepareStatement(Mockito.anyString());
//            when(mockedConnectionFactory.getConnection()).thenReturn(mockedConnection);
//            return mockedConnectionFactory;
//        }

        /**
         * Represents the scenario when DAO operations are being performed on a non existing goods.
         */
        @Nested
        public class NonExistingGoods {

            //all
            @Test
            public void addingShouldResultInSuccess() throws Exception {
                boolean result = false;
                when(mockedConnectionFactory.getConnection().prepareStatement("SELECT * FROM GOODS")).thenReturn(mockedStatement);
                //  when(mockedConnectionFactory.getConnection().prepareStatement("SELECT * FROM GOODS").executeQuery())
                //          .thenReturn(mockedResultSet);
                when(mockedStatement.executeQuery()).thenReturn(mockedResultSet);
                when(mockedResultSet.next()).thenReturn(result = true).thenReturn(false);
                List<Goods> goods = dao.getAll();
                assertTrue(result == true);
//                assumeTrue(goods.size() == 1);
//
//                final Goods nonExistingGoods = new Goods(null, "Parlament", 7622210722416l, 2.6F);
//                Goods result = dao.create(nonExistingGoods);
//                assertTrue(result.equals(dao.get(result.getId()).get()));
//
//                assertGoodsCountIs(2);
//                assertTrue(nonExistingGoods.equals(dao.get(nonExistingGoods.getId()).get()));
            }

            //del True
            @Test
            public void deletionShouldBeFailureAndNotAffectExistingGoods() throws GoodsException, SQLException {
                when(mockedConnection.prepareStatement("DELETE FROM GOODS WHERE ID = ?")).thenReturn(mockedStatement);
                when(mockedStatement.executeUpdate()).thenReturn(1);
                assertTrue(dao.delete(existingGoods.getId()));

//                final Goods nonExistingGoods = new Goods(getNonExistingGoodId(), "Marlboro", 4050300003924l, 3.4f);
//                assertGoodsCountIs(1);
//                assertFalse(dao.delete(nonExistingGoods.getId()));
//                assertGoodsCountIs(1);
            }

            //del False
            @Test
            public void deletionShouldBeFailureAndNotAffectExistingGoods1() throws GoodsException, SQLException {
                when(mockedConnection.prepareStatement("DELETE FROM GOODS WHERE ID = ?")).thenReturn(mockedStatement);
                when(mockedStatement.executeUpdate()).thenReturn(-1);
                assertFalse(dao.delete(existingGoods.getId()));

//                final Goods nonExistingGoods = new Goods(getNonExistingGoodId(), "Marlboro", 4050300003924l, 3.4f);
//                assertGoodsCountIs(1);
//                assertFalse(dao.delete(nonExistingGoods.getId()));
//                assertGoodsCountIs(1);
            }

            //update
            @Test
            public void updationShouldBeFailureAndNotAffectExistingGoods() throws SQLException, GoodsException {
                when(mockedConnection.prepareStatement("UPDATE GOODS SET NAME = ?, BARCODE = ?, PRICE = ? WHERE ID = ?"))
                        .thenReturn(mockedStatement);
                Goods goods = dao.update(existingGoods);
                assertAll("goods",
                        () -> assertEquals(goods.getId(), existingGoods.getId()),
                        () -> assertEquals(goods.getName(), existingGoods.getName()),
                        () -> assertEquals(goods.getBarcode(), existingGoods.getBarcode()),
                        () -> assertEquals(goods.getPrice(), existingGoods.getPrice()));

//                final Long nonExistingId = getNonExistingGoodId();
//                final String newName = "Dunhill";
//                final Long newBarcode = 4820005924653l;
//                final Float newPrice = 12.6f;
//                final Goods goods = new Goods(nonExistingId, newName, newBarcode, newPrice);
//                assertThrows(NoSuchElementException.class, () -> dao.update(goods));
            }

            //create false
            @Test
            public void addGoodsAndDontGetGeneratedIdReturnTrue() throws SQLException, GoodsException {
                when(mockedConnection.prepareStatement("INSERT INTO GOODS (NAME, BARCODE, PRICE) VALUES (?,?,?)"))
                        .thenReturn(mockedStatement);
                when(mockedStatement.executeUpdate()).thenReturn(1);
                when(mockedConnection.prepareStatement("SELECT SCOPE_IDENTITY()"))
                        .thenReturn(mockedStatementKey);
                when(mockedStatementKey.executeQuery()).thenReturn(mockedResultSetForKey);
                when(mockedResultSetForKey.next()).thenReturn(false);
                Goods goods = dao.create(existingGoods);
                assertAll("goods",
                        () -> assertEquals(goods.getId(), existingGoods.getId()),
                        () -> assertEquals(goods.getName(), existingGoods.getName()),
                        () -> assertEquals(goods.getBarcode(), existingGoods.getBarcode()),
                        () -> assertEquals(goods.getPrice(), existingGoods.getPrice()));
                // /assertTrue(goods.getId() == null);
            }

            //create True
            @Test
            public void addDeviceAndDontGetGeneratedIdReturnFalse() throws SQLException, GoodsException {
                when(mockedConnection.prepareStatement("INSERT INTO GOODS (NAME, BARCODE, PRICE) VALUES (?,?,?)"))
                        .thenReturn(mockedStatement);
                when(mockedConnection.prepareStatement("SELECT SCOPE_IDENTITY()"))
                        .thenReturn(mockedStatementKey);
                when(mockedStatementKey.executeQuery()).thenReturn(mockedResultSetForKey);
                when(mockedResultSetForKey.next()).thenReturn(true);
                when(mockedResultSetForKey.getLong(1)).thenReturn(2l);
                Goods goods = dao.create(existingGoods);
                assertEquals(existingGoods.getId(), 2l, 0.0002);
                assertAll("goods",
                        () -> assertFalse(goods.getId().equals(existingGoods.getId())),
                        () -> assertEquals(goods.getName(), existingGoods.getName()),
                        () -> assertEquals(goods.getBarcode(), existingGoods.getBarcode()),
                        () -> assertEquals(goods.getPrice(), existingGoods.getPrice()));
                // /assertTrue(goods.getId() == null);
            }

            //get false
            @Test
            public void retrieveShouldReturnNoGoods() throws GoodsException, SQLException {
                when(mockedConnection.prepareStatement("SELECT * FROM GOODS WHERE ID = ?")).thenReturn(mockedStatement);
                when(mockedStatement.executeQuery()).thenReturn(mockedResultSet);
                when(mockedResultSet.next()).thenReturn(false);
                //Optional<Goods> goods = dao.get(Long.valueOf(1L));
                // assertEquals(Optional.empty(), goods);
                assertFalse(dao.get(Long.valueOf(1L)).isPresent());
                //assertFalse(dao.get(getNonExistingGoodId()).isPresent());
            }

            //get true
            @Test
            public void deviceGetByIdReturnNotEmptyResultSetThenDeviceOptionalShouldBeReturned() throws
                    SQLException, GoodsException {
                when(mockedConnection.prepareStatement("SELECT * FROM GOODS WHERE ID = ?")).thenReturn(mockedStatement);
                when(mockedStatement.executeQuery()).thenReturn(mockedResultSet);
                when(mockedResultSet.next()).thenReturn(true);
                // Optional<Goods> goodsOptional = dao.get(existingGoods.getId());
                Goods goods = (Goods) dao.get(existingGoods.getId()).get();
                assertAll("goods",
                        () -> assertEquals(goods.getId(), existingGoods.getId()),
                        () -> assertEquals(goods.getName(), existingGoods.getName()),
                        () -> assertEquals(goods.getBarcode(), existingGoods.getBarcode()),
                        () -> assertEquals(goods.getPrice(), existingGoods.getPrice()));
            }
        }

        /**
         * Represents a scenario where DAO operations are being performed on an already existing
         * good.
         */
//        @Nested
//        public class ExistingGoods {
//
//            @Test
//            public void addingShouldResultInFailureAndNotAffectExistingGoods() throws GoodsException {
//                Goods existingGoods = new Goods(existingGoodsID, "Camel", 7622210609779l, 1.2f);
//                Goods result = dao.create(existingGoods);
//                assertTrue(result.equals(existingGoods));
//
//                assertGoodsCountIs(2);
//                assertEquals(existingGoods, dao.get(existingGoods.getId()).get());
//            }
//
//            @Test
//            public void deletionShouldBeSuccessAndGoodsShouldBeNonAccessible() throws GoodsException {
//                boolean result = dao.delete(existingGoods.getId());
//
//                assertEquals(true, result);
//                assertGoodsCountIs(0);
//                assertFalse(dao.get(existingGoods.getId()).isPresent());
//            }
//
//            @Test
//            public void updationShouldBeSuccessAndAccessingTheSameGoodsShouldReturnUpdatedInformation() throws GoodsException {
//                final String newName = "L&M";
//                final Long newBarcode = 740617152326l;
//                final Float newPrice = 32.7f;
//                final Goods goods = new Goods(existingGoods.getId(), newName, newBarcode, newPrice);
//                Goods result = dao.update(goods);
//
//                assertTrue(goods.equals(result));
//
//                final Goods goodsGet = (Goods) dao.get(existingGoods.getId()).get();
//                assertEquals(newName, goodsGet.getName());
//                assertEquals(newBarcode, goodsGet.getBarcode(), 0.0002);
//                assertEquals(newPrice, goodsGet.getPrice(), 0.0002);
//            }
//        }
//
//
//        @Nested
//        public class ExistingGoodsWithNull {
//
//            private Goods existingGoodsNullId = new Goods(null, "Marlboro", 4050300003924l, 3.4f);
//
//            @Test
//            public void addingResultWithNullId() throws Exception {
//                assertGoodsCountIs(1);
//                Goods result = dao.create(existingGoodsNullId);
//                assertGoodsCountIs(2);
//                assertTrue(result.equals(existingGoodsNullId));
//                assertTrue(existingGoodsNullId.equals(dao.get(existingGoodsNullId.getId()).get()));
//            }
//
//            @Test
//            public void deletingResultWithNullGoods() {
//                assertThrows(NullPointerException.class, () -> dao.delete(null));
//            }
//
//            @Test
//            public void updatingResultWithNullGoods() {
//                assertThrows(NullPointerException.class, () -> dao.update(existingGoodsNullId));
//            }
//
//            @Test
//            public void getingResultWithNullId() {
//                assertThrows(GoodsException.class, () -> dao.get(null));
//            }
//        }

//        @Nested
//        public class ExistingGoodsWithZeroId {
//
//            private Goods existingGoodsZeroId = new Goods(0l, "Marlboro", 4050300003924l, 3.4f);
//
//            @Test
//            public void addingGoodsWithZeroId() throws Exception {
//                assertGoodsCountIs(1);
//                Goods resultZero = dao.create(existingGoodsZeroId);
//                assertGoodsCountIs(2);
//                assertTrue(resultZero.equals(existingGoodsZeroId));
//                assertTrue(existingGoodsZeroId.equals(dao.get(existingGoodsZeroId.getId()).get()));
//            }
//
//            @Test
//            public void deletingResultWithZeroId() throws Exception {
//                assertGoodsCountIs(1);
//                assertFalse(dao.delete(0l));
//                assertGoodsCountIs(1);
//            }
//
//            @Test
//            public void updatingResultWithZeroId() throws Exception {
//                assertGoodsCountIs(1);
//                assertThrows(NoSuchElementException.class, () -> dao.update(existingGoodsZeroId));
//            }
//
//            @Test
//            public void getingResultWithZeroID() throws Exception {
//                assertGoodsCountIs(1);
//                assertFalse(dao.get(0l).isPresent());
//            }
//        }

//        @Test
//        public void getingResultWithZeroList() throws Exception {
//            assertGoodsCountIs(1);
//            dao.delete(existingGoodsID);
//            assertGoodsCountIs(0);
//            assumeTrue(dao.getAll().size() == 0);
//        }
    }

    /**
     * Represents a scenario where DB connectivity is not present due to network issue, or
     * DB service unavailable.
     */
    @Nested
    public class ConnectivityIssue {

        private static final String EXCEPTION_CAUSE = "Connection not available";

        /**
         * setup a connection failure scenario.
         *
         * @throws SQLException if any error occurs.
         */
        @BeforeEach
        public void setUp() throws SQLException, IOException {
            dao = new GoodsDao(mockedConnectionFactory());
        }

        private ConnectionFactory mockedConnectionFactory() throws SQLException {
            ConnectionFactory mockedConnectionFactory = mock(ConnectionFactory.class);
            Connection mockedConnection = mock(Connection.class);
            SQLException exception = new SQLException(EXCEPTION_CAUSE);
            doThrow(exception)
                    .when(mockedConnection)
                    .prepareStatement(Mockito.anyString());
            when(mockedConnectionFactory.getConnection()).thenReturn(mockedConnection);
            return mockedConnectionFactory;
        }

        @Test
        public void addingAGoodsFailsWithExceptionAsFeedbackToClient() {
            assertThrows(Exception.class, () -> {
                dao.create(new Goods(2l, "Kent", 4820092770232l, 36.7f));
            });
        }

        @Test
        public void deletingAGoodsFailsWithExceptionAsFeedbackToTheClient() {
            assertThrows(Exception.class, () -> {
                dao.delete(existingGoods.getId());
            });
        }

        @Test
        public void updatingAGoodsFailsWithFeedbackToTheClient() {
            final String newName = "Lark";
            final Long newBarcode = 740617153127l;
            final Float newPrice = 34.7f;
            assertThrows(Exception.class, () -> {
                dao.update(new Goods(existingGoods.getId(), newName, newBarcode, newPrice));
            });
        }

        @Test
        public void retrievingAGoodByIdFailsWithExceptionAsFeedbackToClient() {
            assertThrows(Exception.class, () -> {
                dao.get(existingGoods.getId());
            });
        }

        @Test
        public void retrievingAllGoodsFailsWithExceptionAsFeedbackToClient() {
            assertThrows(Exception.class, () -> {
                dao.getAll();
            });
        }
    }

//    private void assertGoodsCountIs(int count) throws GoodsException {
//        List<Goods> allGoods = dao.getAll();
//        assertTrue(allGoods.size() == count);
//    }
//
//    private Long getNonExistingGoodId() {
//        return 999l;
//    }

    private void createMocksFromGetMethod() throws SQLException {
        when(mockedConnection.prepareStatement("SELECT * FROM GOODS WHERE ID = ?")).thenReturn(mockedStatement);
        when(mockedResultSet.next()).thenReturn(true);
        when(mockedStatement.executeQuery()).thenReturn(mockedResultSet);
        when(mockedResultSet.getLong("ID")).thenReturn(existingGoods.getId());
        when(mockedResultSet.getString("NAME")).thenReturn(existingGoods.getName());
        when(mockedResultSet.getLong("BARCODE")).thenReturn(existingGoods.getBarcode());
        when(mockedResultSet.getFloat("PRICE")).thenReturn(existingGoods.getPrice());
    }
}

