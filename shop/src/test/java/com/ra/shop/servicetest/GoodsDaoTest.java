package com.ra.shop.servicetest;

import com.ra.shop.model.Goods;
import com.ra.shop.service.GoodsException;
import com.ra.shop.service.GoodsDao;
import com.ra.shop.utils.ConnectionFactory;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.Connection;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.Mockito.*;

import javax.swing.text.html.Option;

public class GoodsDaoTest {

    private GoodsDao dao;
    private Goods existingGoods = new Goods(1l, "Camel", 7622210609779l, 1.2f);
    private Long existingGoodsID;

    @BeforeEach
    public void createSchema() throws SQLException, IOException {
        OnOffSchemaSql.createSchema(ConnectionFactory.getInstance());
    }

    @AfterEach
    public void deleteSchema() throws SQLException, IOException {
        OnOffSchemaSql.deleteSchema(ConnectionFactory.getInstance());
    }

    @Nested
    public class ConnectionSuccess {

        @BeforeEach
        public void setUp() throws Exception {
            dao = new GoodsDao(ConnectionFactory.getInstance());
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

                final Goods nonExistingGoods = new Goods(null, "Parlament", 7622210722416l, 2.6F);
                Goods result = dao.create(nonExistingGoods);
                assertTrue(result.equals(dao.get(result.getId()).get()));

                assertGoodsCountIs(2);
                assertTrue(nonExistingGoods.equals(dao.get(nonExistingGoods.getId()).get()));
            }

            @Test
            public void deletionShouldBeFailureAndNotAffectExistingGoods() throws GoodsException {
                final Goods nonExistingGoods = new Goods(2l, "Marlboro", 4050300003924l, 3.4f);
                int result = dao.delete(nonExistingGoods.getId());

                assertEquals(0, result);
                assertGoodsCountIs(1);
            }

            @Test
            public void updationShouldBeFailureAndNotAffectExistingGoods() throws GoodsException {
                final Long nonExistingId = getNonExistingGoodId();
                final String newName = "Dunhill";
                final Long newBarcode = 4820005924653l;
                final Float newPrice = 12.6f;
                final Goods goods = new Goods(nonExistingId, newName, newBarcode, newPrice);
                int result = dao.update(goods);

                assertEquals(0, result);
                assertFalse(dao.get(nonExistingId).isPresent());
            }

            @Test
            public void retrieveShouldReturnNoGoods() throws GoodsException {
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
            public void addingShouldResultInFailureAndNotAffectExistingGoods() throws GoodsException {
                Goods existingGoods = new Goods(existingGoodsID, "Camel", 7622210609779l, 1.2f);
                Goods result = dao.create(existingGoods);
                assertTrue(result.equals(existingGoods));

                assertGoodsCountIs(1);
                assertEquals(existingGoods, dao.get(existingGoods.getId()).get());
            }

            @Test
            public void deletionShouldBeSuccessAndGoodsShouldBeNonAccessible() throws GoodsException {
                int result = dao.delete(existingGoods.getId());

                assertEquals(1, result);
                assertGoodsCountIs(0);
                assertFalse(dao.get(existingGoods.getId()).isPresent());
            }

            @Test
            public void updationShouldBeSuccessAndAccessingTheSameGoodsShouldReturnUpdatedInformation() throws GoodsException {
                final String newName = "L&M";
                final Long newBarcode = 740617152326l;
                final Float newPrice = 32.7f;
                final Goods goods = new Goods(existingGoods.getId(), newName, newBarcode, newPrice);
                int result = dao.update(goods);

                assertEquals(1, result);

                final Goods goodsGet = (Goods) dao.get(existingGoods.getId()).get();
                assertEquals(newName, goodsGet.getName());
                assertEquals(newBarcode, goodsGet.getBarcode(), 0.0002);
                assertEquals(newPrice, goodsGet.getPrice(), 0.0002);
            }
        }

        @Nested
        public class ExistingGoodsWithNullAndZeroId {

            @Test
            public void addingResultWithNullAndZeroId() throws Exception {
                Goods existingGoodsNullId = new Goods(null, "Marlboro", 4050300003924l, 3.4f);
                Goods result = dao.create(existingGoodsNullId);
                assertTrue(result.equals(existingGoodsNullId));

                Goods existingGoodsZeroId = new Goods(0l, "Marlboro", 4050300003924l, 3.4f);
                Goods resultZero = dao.create(existingGoodsZeroId);
                assertTrue(resultZero.equals(existingGoodsZeroId));
            }

            @Test
            public void deletingResultWithNullAndZeroId() throws Exception {
                Integer resultNull = dao.delete(null);
                assertTrue(0 == resultNull);

                Integer resultZero = dao.delete(0l);
                assertTrue(0 == resultZero);
            }

            @Test
            public void updatingResultWithNullAndZeroId() throws Exception {
                Goods existingGoodsNullId = new Goods(null, "Marlboro", 4050300003924l, 3.4f);
                Integer resultNull = dao.update(existingGoodsNullId);
                assertTrue(0 == resultNull);

                Goods existingGoodsZeroId = new Goods(0l, "Marlboro", 4050300003924l, 3.4f);
                Integer resultZero = dao.update(existingGoodsZeroId);
                assertTrue(0 == resultZero);
            }

            @Test
            public void getingResultWithNullAndZeroId() throws Exception {
                Optional resultNull = dao.get(null);
                assertFalse(resultNull.isPresent());

                Optional resultZero = dao.get(0l);
                assertFalse(resultZero.isPresent());

            }
        }
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

    private void assertGoodsCountIs(int count) throws GoodsException {
        List<Goods> allGoods = dao.getAll();
        assertTrue(allGoods.size() == count);

    }

    private Long getNonExistingGoodId() {
        return 999l;
    }
}

