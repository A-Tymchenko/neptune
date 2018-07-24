package com.ra.shop;

import com.ra.shop.exceptions.DAOException;
import com.ra.shop.repository.implementation.GoodsDaoImpl;
import com.ra.shop.model.Goods;
import com.ra.shop.config.ConnectionFactory;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GoodsDaoImplMockTest {

    private static GoodsDaoImpl dao;
    private Goods existingGoods = new Goods("Camel", 7622210609779l, 1.2f);
    private static ConnectionFactory mockedConnectionFactory;
    private Connection mockedConnection;
    private PreparedStatement mockedStatement;
    private PreparedStatement mockedStatementKey;
    private ResultSet mockedResultSet;
    private ResultSet mockedResultSetForKey;

    @BeforeEach
    public void createSchema() {
        mockedConnectionFactory = mock(ConnectionFactory.class);
        dao = new GoodsDaoImpl(mockedConnectionFactory);
        existingGoods.setId(1l);
    }

    @Nested
    public class ConnectionSuccess {

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
        public class mockedGoodsDaoTests {

            @Test
            public void whenReadAllGoodThenResultIsTrue() throws Exception {
                boolean result = false;
                when(mockedConnectionFactory.getConnection().prepareStatement("SELECT * FROM GOODS")).thenReturn(mockedStatement);
                when(mockedStatement.executeQuery()).thenReturn(mockedResultSet);
                when(mockedResultSet.next()).thenReturn(result = true).thenReturn(false);
                List<Goods> goods = dao.getAll();
                assertTrue(result == true);
            }

            @Test
            public void whenDeletionGoodsThenReturnTrue() throws DAOException, SQLException {
                when(mockedConnection.prepareStatement("DELETE FROM GOODS WHERE ID = ?")).thenReturn(mockedStatement);
                when(mockedStatement.executeUpdate()).thenReturn(1);
                assertTrue(dao.delete(existingGoods.getId()));
            }

            @Test
            public void whenDeletionGoodsThenReturnFalse() throws DAOException, SQLException {
                when(mockedConnection.prepareStatement("DELETE FROM GOODS WHERE ID = ?")).thenReturn(mockedStatement);
                when(mockedStatement.executeUpdate()).thenReturn(-1);
                assertFalse(dao.delete(existingGoods.getId()));
            }

            @Test
            public void whenUpdationGoodsThenReturnEqualsGoods() throws SQLException, DAOException {
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
            public void whenCreatedGoodsWithFalseNextIdThenReturnEqualsGoods() throws SQLException, DAOException {
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
            public void whenCreatedGoodsWithTrueNextIdThenReturnNotEqualsGoods() throws SQLException, DAOException {
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
                        () -> assertFalse(goods.getId().equals(existingGoods.getId())),
                        () -> assertEquals(goods.getName(), existingGoods.getName()),
                        () -> assertEquals(goods.getBarcode(), existingGoods.getBarcode()),
                        () -> assertEquals(goods.getPrice(), existingGoods.getPrice()));
            }

            @Test
            public void whenGetGoodsWithFalseNextGoodsThenReturnNotPresentGoods() throws DAOException, SQLException {
                when(mockedConnection.prepareStatement("SELECT * FROM GOODS WHERE ID = ?")).thenReturn(mockedStatement);
                when(mockedStatement.executeQuery()).thenReturn(mockedResultSet);
                when(mockedResultSet.next()).thenReturn(false);
                assertFalse(dao.get(Long.valueOf(1L)).isPresent());
            }

            @Test
            public void whenGetGoodsWithTrueNextGoodsThenReturnEqualsGoods() throws
                    SQLException, DAOException {
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
    public class ConnectivityIssue {

        private static final String EXCEPTION_CAUSE = "Connection not available";

        /**
         * setup a connection failure scenario.
         *
         * @throws SQLException if any error occurs.
         */
        @BeforeEach
        public void setUp() throws SQLException, IOException {
            dao = new GoodsDaoImpl(mockedConnectionFactory());
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
        public void deletingAGoodsFailsWithExceptionAsFeedbackToTheClient() {
            assertThrows(Exception.class, () -> {
                dao.delete(existingGoods.getId());
            });
        }

        @Test
        public void updatingAGoodsFailsWithFeedbackToTheClient() {
            Goods goods = new Goods("Lark", 740617153127l, 34.7f);
            goods.setId(existingGoods.getId());
            assertThrows(Exception.class, () -> {
                dao.update(goods);
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

