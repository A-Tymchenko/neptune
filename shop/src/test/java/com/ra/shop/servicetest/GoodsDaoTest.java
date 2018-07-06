package com.ra.shop.servicetest;

import com.ra.shop.model.Good;
import com.ra.shop.service.GoodException;
import com.ra.shop.service.GoodsDao;
import com.ra.shop.utils.ConnectionFactory;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.Connection;

import java.sql.SQLException;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.Mockito.*;

public class GoodsDaoTest {

    private GoodsDao dao;
    private Good existingGood = new Good(1l, "Freddy", 1.2f);


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
            int result = dao.create(existingGood);
            Assertions.assertEquals(1, result);
        }

        /**
         * Represents the scenario when DAO operations are being performed on a non existing good.
         */
        @Nested
        public class NonExistingGood {

            @Test
            public void addingShouldResultInSuccess() throws Exception {
                List<Good> goods = dao.getAll();
                assumeTrue(goods.size() == 1);

                final Good nonExistingGood = new Good(2l, "Robert", 2.6F);
                int result = dao.create(nonExistingGood);
                assertEquals(1, result);

                assertGoodCountIs(2);
                assertEquals(nonExistingGood, dao.get(nonExistingGood.getId()).get());
            }

            @Test
            public void deletionShouldBeFailureAndNotAffectExistingGoods() throws GoodException {
                final Good nonExistingGood = new Good(2l, "Robert", 3.4f);//????
                int result = dao.delete(nonExistingGood.getId());

                assertEquals(0, result);
                assertGoodCountIs(1);
            }

            @Test
            public void updationShouldBeFailureAndNotAffectExistingGoods() throws GoodException {
                final Long nonExistingId = getNonExistingGoodId();
                final String newName = "Douglas";
                final Float newPrice = 12.6f;
                final Good good = new Good(nonExistingId, newName, newPrice);
                int result = dao.update(good);

                assertEquals(0, result);
                assertFalse(dao.get(nonExistingId).isPresent());
            }

            @Test
            public void retrieveShouldReturnNoCustomer() throws GoodException {
                assertFalse(dao.get(getNonExistingGoodId()).isPresent());
            }
        }

        /**
         * Represents a scenario where DAO operations are being performed on an already existing
         * good.
         */
        @Nested
        public class ExistingGood {

            @Test
            public void addingShouldResultInFailureAndNotAffectExistingGoods() throws GoodException {
                Good existingGood = new Good(1l, "Freddy", 1.2f);

                int result = dao.create(existingGood);
                assertEquals(0, result);
                assertGoodCountIs(1);
                assertEquals(existingGood, dao.get(existingGood.getId()).get());
            }

            @Test
            public void deletionShouldBeSuccessAndGoodShouldBeNonAccessible() throws GoodException {
                int result = dao.delete(existingGood.getId());

                assertEquals(1, result);
                assertGoodCountIs(0);
                assertFalse(dao.get(existingGood.getId()).isPresent());
            }

            @Test
            public void updationShouldBeSuccessAndAccessingTheSameGoodShouldReturnUpdatedInformation() throws GoodException {
                final String newName = "Bernard";
                final Float newPrice = 36.7f;
                final Good good = new Good(existingGood.getId(), newName, newPrice);
                int result = dao.update(good);

                assertEquals(1, result);

                final Good good1 = (Good) dao.get(existingGood.getId()).get();
                assertEquals(newName, good1.getName());
                assertEquals(newPrice, good1.getPrice(), 0.0002);
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
            //   dao = null;
            dao = new GoodsDao(mockedConnectionFactory());
        }

        private ConnectionFactory mockedConnectionFactory() throws SQLException, IOException {
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
        public void addingAGoodFailsWithExceptionAsFeedbackToClient() {
            assertThrows(Exception.class, () -> {
                dao.create(new Good(2l, "Bernard", 36.7f));
            });
        }

        @Test
        public void deletingAGoodFailsWithExceptionAsFeedbackToTheClient() {
            assertThrows(Exception.class, () -> {
                dao.delete(existingGood.getId());
            });
        }

        @Test
        public void updatingAGoodFailsWithFeedbackToTheClient() {
            final String newName = "Bernard";
            final Float newPrice = 34.7f;
            assertThrows(Exception.class, () -> {
                dao.update(new Good(existingGood.getId(), newName, newPrice));
            });
        }

        @Test
        public void retrievingAGoodByIdFailsWithExceptionAsFeedbackToClient() {
            assertThrows(Exception.class, () -> {
                dao.get(existingGood.getId());
            });
        }

        @Test
        public void retrievingAllGoodsFailsWithExceptionAsFeedbackToClient() {
            assertThrows(Exception.class, () -> {
                dao.getAll();
            });
        }
    }

    private void assertGoodCountIs(int count) throws GoodException {
        List<Good> allGoods = dao.getAll();
        assertTrue(allGoods.size() == count);

    }

    private Long getNonExistingGoodId() {
        return 999l;
    }


//    @Test
//    public void createSchema1() throws SQLException, IOException {
//
//        OnOffSchemaSql.createSchema(ConnectionFactory.getInstance());
//
//        Good good1 = new Good(1l, "Adam", 1.2f);
//        Good good2 = new Good(2l, "Bob", 23.05f);
//        Good good3 = new Good(3l, "Carl", 123.56f);
//
//        List<Good> goods = new ArrayList<>();
//        goods.add(good1);
//        goods.add(good2);
//        goods.add(good3);
//
//        try (Connection connection1 = ConnectionFactory.getInstance().getConnection();
//             PreparedStatement statement1 =
//                     connection1.prepareStatement("INSERT INTO GOODS VALUES (?,?,?)")) {
//            for (Good good : goods) {
//
//                statement1.setLong(1, good.getId());
//                statement1.setString(2, good.getName());
//                statement1.setFloat(3, good.getPrice());
//                statement1.execute();
//            }
//            connection1.close();
//            statement1.close();
//
//        } catch (SQLException ex) {
//            System.out.println(ex);
//        }
//
//    }
//
//    @Test
//    public void multithreadMAX() throws Exception {
//
//        OnOffSchemaSql.createSchema(ConnectionFactory.getInstance());
//
//        GoodsDao goodDAO = new GoodsDao(ConnectionFactory.getInstance());
//
//        goodDAO.getAll().forEach(x -> System.out.println(x));
//
//        ExecutorService goodsThread = Executors.newFixedThreadPool(1000);
//        for (int i = 0; i < 1000; i++) {
//
//            goodsThread.submit(() -> {
//                String threadName = Thread.currentThread().getName();
//
//                try {
//                    goodDAO.create(new Good((Long) Thread.currentThread().getId(), threadName, 1.2f));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                System.out.println(Thread.currentThread().getId() + " " + threadName);
//            });
//        }
//
//        goodsThread.shutdown();
//
//        while (!goodsThread.awaitTermination(1l, TimeUnit.SECONDS)) {
//            System.out.println("Waiting for termination");
//        }
//
//        System.out.println();
//        goodDAO.getAll().forEach(x -> System.out.println(x));
//
//    }
//
//    @Test
//    public void deleteSchema1() throws IOException, SQLException {
//
//        OnOffSchemaSql.deleteSchema(ConnectionFactory.getInstance());
//    }

}
