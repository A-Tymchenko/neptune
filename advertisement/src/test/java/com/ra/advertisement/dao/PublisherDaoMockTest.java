package com.ra.advertisement.dao;

import com.ra.advertisement.connection.ConnectionFactory;
import com.ra.advertisement.dao.exceptions.DaoException;
import com.ra.advertisement.entity.Publisher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PublisherDaoMockTest {
    private static ConnectionFactory connectionFactory;
    private static AdvertisementDao<Publisher> publisherDao;
    private Connection mockConnection;
    private PreparedStatement mockStatement;
    private ResultSet mockResultSet;
    private Publisher publisher;
    private Publisher publisherUpdate;

    @BeforeAll
    public static void init() {
        connectionFactory = mock(ConnectionFactory.class);
        publisherDao = new PublisherAdvertisementDaoImpl(connectionFactory);
    }

    @BeforeEach
    public void reInitPublisherDao() throws SQLException {
        mockConnection = mock(Connection.class);
        mockStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        when(connectionFactory.getConnection()).thenReturn(mockConnection);
        publisher = new Publisher("PublisherLtd", "London", "035-45-18", "GB");
        publisherUpdate = new Publisher(1L, "PublisherLtd Update", "London Update", "035-45-18 Update",
                "GB Update");
    }

    /**
     * Testing method addPublisher when result true.
     *
     * @throws SQLException exception.
     */
    @Test
    public void testAddPublisher() throws SQLException, DaoException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
        when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        Integer result = publisherDao.create(publisher);
        Assertions.assertTrue(result == 1);
    }

    /**
     * Testing method addPublisher and get generated key when result true.
     *
     * @throws SQLException exception.
     */
    @Test
    public void testAddPublisherAndGeneratedKeyReturnTrue() throws SQLException, DaoException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
        when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        while (mockResultSet.next()){
            publisher.setPubId(mockResultSet.getLong(1));
        }
        Integer result = publisherDao.create(publisher);
        Assertions.assertTrue(result == 1);
    }

    /**
     * Testing method deletePublisher when result true.
     *
     * @throws SQLException
     */
    @Test
    public void testDeletePublisher() throws SQLException, DaoException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
        int result = publisherDao.delete(2L);
        Assertions.assertTrue(result == 1);
    }

    /**
     * Testing method updatePublisher when result true.
     *
     * @throws SQLException
     */
    @Test
    public void testUpdatePublisher() throws SQLException, DaoException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
        int result = publisherDao.update(publisherUpdate);
        Assertions.assertTrue(result == 1);
    }

    /**
     * Testing method getAllPublishers when result true.
     *
     * @throws SQLException
     */
    @Test
    public void testGetAllPublisher() throws SQLException, DaoException {
        boolean result = false;
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(result = true).thenReturn(false);
        List<Publisher> listDevices = publisherDao.getAll();
        Assertions.assertTrue(result == true);
    }

    /**
     * Testing method getPublisherById when Optional.empty result true.
     *
     * @throws SQLException
     * @throws DaoException
     */
    @Test
    public void whenGetPublisherByIdAndReturnEmptyResultSetThenEmptyOptionalShouldBeReturned() throws DaoException, SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);
        Optional<Publisher> publisher = publisherDao.getById(Long.valueOf(1L));
        assertEquals(Optional.empty(), publisher);
    }

    /**
     * Testing method getPublisherById when Optional is not empty result true.
     *
     * @throws SQLException
     * @throws DaoException
     */
    @Test
    public void whenGetPublisherByIdAndReturnNotEmptyResultSetThenPublisherOptionalShouldBeReturned() throws DaoException, SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);
        Optional<Publisher> publisherOptional = publisherDao.getById(1L);
    }

    /**
     * Testing method deletePublisher catch SQLException throw DaoException.
     *
     * @throws SQLException
     */
    @Test
    public void deletePublisherThrowDaoException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException());
        assertThrows(DaoException.class, () -> {
            publisherDao.delete(1L);
        });
    }

    /**
     * Testing method addPublisher catch SQLException throw DaoException.
     *
     * @throws SQLException
     */
    @Test
    public void addPublisherThrowDaoException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException());
        assertThrows(DaoException.class, () -> {
            publisherDao.create(publisher);
        });
    }

    /**
     * Testing method UpdatePublisher catch SQLException throw DaoException.
     *
     * @throws SQLException
     */
    @Test
    public void updatePublisherThrowDaoException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException());
        assertThrows(DaoException.class, () -> {
            publisherDao.update(publisherUpdate);
        });
    }

    /**
     * Testing method getAllPublishers catch SQLException throw DaoException.
     *
     * @throws SQLException
     */
    @Test
    public void getAllPublishersThrowDaoException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException());
        assertThrows(DaoException.class, () -> {
            publisherDao.getAll();
        });
    }

    /**
     * Testing method getByIdPublisher catch SQLException throw DaoException.
     *
     * @throws SQLException
     */
    @Test
    public void getByIdPublisherThrowDaoException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException());
        assertThrows(DaoException.class, () -> {
            publisherDao.getById(2L);
        });
    }
}