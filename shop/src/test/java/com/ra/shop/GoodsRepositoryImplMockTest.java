package com.ra.shop;

import com.ra.shop.exceptions.RepositoryException;
import com.ra.shop.repository.implementation.GoodsRepositoryImpl;
import com.ra.shop.model.Goods;
import com.ra.shop.config.ConnectionFactory;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GoodsRepositoryImplMockTest {

    private static GoodsRepositoryImpl dao;
    private Goods existingGoods = new Goods("Camel", 7622210609779l, 1.2f);
    private static ConnectionFactory mockedConnectionFactory;
    private Connection mockedConnection;
    private PreparedStatement mockedStatement;
    private PreparedStatement mockedStatementKey;
    private ResultSet mockedResultSet;
    private ResultSet mockedResultSetForKey;

    @BeforeEach
    void createSchema() {
        mockedConnectionFactory = mock(ConnectionFactory.class);
        dao = new GoodsRepositoryImpl(mockedConnectionFactory);
        existingGoods.setId(1l);
    }

    @Nested
    class ConnectionSuccess {

        @BeforeEach
        public void setUp() throws Exception {
            mockedConnection = mock(Connection.class);
            mockedStatement = mock(PreparedStatement.class);
            mockedStatementKey = mock(PreparedStatement.class);
            mockedResultSet = mock(ResultSet.class);
            mockedResultSetForKey = mock(ResultSet.class);
            when(mockedConnectionFactory.getConnection()).thenReturn(mockedConnection);
            createMocksFromGetMethod();
        }

        /**
         * Represents the scenario when DAO operations are being performed on a existing connection.
         */
        @Nested
        class mockedGoodsDaoTests {

            @Test
            void whenReadAllGoodThenResultIsTrue() throws Exception {
                boolean result = false;
                when(mockedConnectionFactory.getConnection().prepareStatement("SELECT * FROM GOODS")).thenReturn(mockedStatement);
                when(mockedStatement.executeQuery()).thenReturn(mockedResultSet);
                when(mockedResultSet.next()).thenReturn(result = true).thenReturn(false);
                List<Goods> goods = dao.getAll();
                assertTrue(result == true);
            }

            @Test
            void whenDeletionGoodsThenReturnTrue() throws RepositoryException, SQLException {
                when(mockedConnection.prepareStatement("DELETE FROM GOODS WHERE ID = ?")).thenReturn(mockedStatement);
                when(mockedStatement.executeUpdate()).thenReturn(1);
                assertTrue(dao.delete(existingGoods.getId()));
            }

            @Test
            void whenDeletionGoodsThenReturnFalse() throws RepositoryException, SQLException {
                when(mockedConnection.prepareStatement("DELETE FROM GOODS WHERE ID = ?")).thenReturn(mockedStatement);
                when(mockedStatement.executeUpdate()).thenReturn(-1);
                assertFalse(dao.delete(existingGoods.getId()));
            }

            @Test
            void whenUpdationGoodsThenReturnEqualsGoods() throws SQLException, RepositoryException {
                when(mockedConnection.prepareStatement("UPDATE GOODS SET NAME = ?, BARCODE = ?, PRICE = ? WHERE ID = ?"))
                    .thenReturn(mockedStatement);
                Goods goods = dao.update(existingGoods);
                assertAll("goods",
                    () -> assertEquals(goods.getId(), existingGoods.getId()),
                    () -> assertEquals(goods.getName(), existingGoods.getName()),
                    () -> assertEquals(goods.getBarcode(), existingGoods.getBarcode()),
                    () -> assertEquals(goods.getPrice(), existingGoods.getPrice()));
            }

            @Test
            void whenCreatedGoodsWithFalseNextIdThenReturnEqualsGoods() throws SQLException, RepositoryException {
                when(mockedConnection.prepareStatement("INSERT INTO GOODS (NAME, BARCODE, PRICE) VALUES (?,?,?)"))
                    .thenReturn(mockedStatement);
                when(mockedStatement.executeUpdate()).thenReturn(1);
                when(mockedConnection.prepareStatement("SELECT LAST_INSERT_ID()"))
                    .thenReturn(mockedStatementKey);
                when(mockedStatementKey.executeQuery()).thenReturn(mockedResultSetForKey);
                when(mockedResultSetForKey.next()).thenReturn(false);
                Goods goods = dao.create(existingGoods);
                assertAll("goods",
                    () -> assertEquals(goods.getId(), existingGoods.getId()),
                    () -> assertEquals(goods.getName(), existingGoods.getName()),
                    () -> assertEquals(goods.getBarcode(), existingGoods.getBarcode()),
                    () -> assertEquals(goods.getPrice(), existingGoods.getPrice()));
            }

            @Test
            void whenCreatedGoodsWithTrueNextIdThenReturnNotEqualsGoods() throws SQLException, RepositoryException {
                when(mockedConnection.prepareStatement("INSERT INTO GOODS (NAME, BARCODE, PRICE) VALUES (?,?,?)"))
                    .thenReturn(mockedStatement);
                when(mockedConnection.prepareStatement("SELECT LAST_INSERT_ID()"))
                    .thenReturn(mockedStatementKey);
                when(mockedStatementKey.executeQuery()).thenReturn(mockedResultSetForKey);
                when(mockedResultSetForKey.next()).thenReturn(true);
                when(mockedResultSetForKey.getLong(1)).thenReturn(2l);
                Goods goods = dao.create(existingGoods);
                assertEquals(existingGoods.getId(), 2l, 0.0002);
                assertAll("goods",
                    () -> assertEquals(goods.getId(), existingGoods.getId()),
                    () -> assertEquals(goods.getName(), existingGoods.getName()),
                    () -> assertEquals(goods.getBarcode(), existingGoods.getBarcode()),
                    () -> assertEquals(goods.getPrice(), existingGoods.getPrice()));
            }

            @Test
            void whenGetGoodsWithFalseNextGoodsThenReturnNotPresentGoods() throws RepositoryException, SQLException {
                when(mockedConnection.prepareStatement("SELECT * FROM GOODS WHERE ID = ?")).thenReturn(mockedStatement);
                when(mockedStatement.executeQuery()).thenReturn(mockedResultSet);
                when(mockedResultSet.next()).thenReturn(false);
                assertFalse(dao.get(Long.valueOf(1L)).isPresent());
            }

            @Test
            void whenGetGoodsWithTrueNextGoodsThenReturnEqualsGoods() throws
                SQLException, RepositoryException {
                when(mockedConnection.prepareStatement("SELECT * FROM GOODS WHERE ID = ?")).thenReturn(mockedStatement);
                when(mockedStatement.executeQuery()).thenReturn(mockedResultSet);
                when(mockedResultSet.next()).thenReturn(true);
                Goods goods = (Goods) dao.get(existingGoods.getId()).get();
                assertAll("goods",
                    () -> assertEquals(goods.getId(), existingGoods.getId()),
                    () -> assertEquals(goods.getName(), existingGoods.getName()),
                    () -> assertEquals(goods.getBarcode(), existingGoods.getBarcode()),
                    () -> assertEquals(goods.getPrice(), existingGoods.getPrice()));
            }
        }
    }

    /**
     * Represents a scenario where DB connectivity is not present due to network issue, or
     * DB service unavailable.
     */
    @Nested
    class ConnectivityIssue {

        private static final String EXCEPTION_CAUSE = "Connection not available";

        /**
         * setup a connection failure scenario.
         *
         * @throws SQLException if any error occurs.
         */
        @BeforeEach
        void setUp() throws SQLException, IOException {
            dao = new GoodsRepositoryImpl(mockedConnectionFactory());
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
                dao.create(new Goods("Kent", 4820092770232l, 36.7f));
            });
        }

        @Test
        void deletingAGoodsFailsWithExceptionAsFeedbackToTheClient() {
            assertThrows(Exception.class, () -> {
                dao.delete(existingGoods.getId());
            });
        }

        @Test
        void updatingAGoodsFailsWithFeedbackToTheClient() {
            Goods goods = new Goods("Lark", 740617153127l, 34.7f);
            goods.setId(existingGoods.getId());
            assertThrows(Exception.class, () -> {
                dao.update(goods);
            });
        }

        @Test
        void retrievingAGoodByIdFailsWithExceptionAsFeedbackToClient() {
            assertThrows(Exception.class, () -> {
                dao.get(existingGoods.getId());
            });
        }

        @Test
        void retrievingAllGoodsFailsWithExceptionAsFeedbackToClient() {
            assertThrows(Exception.class, () -> {
                dao.getAll();
            });
        }
    }

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

