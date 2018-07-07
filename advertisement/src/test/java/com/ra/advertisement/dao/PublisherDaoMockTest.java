package com.ra.advertisement.dao;

import com.ra.advertisement.connection.ConnectionFactory;
import com.ra.advertisement.dao.exceptions.DaoException;
import com.ra.advertisement.model.entities.Publisher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PublisherDaoMockTest {
    private static ConnectionFactory connectionFactory;
    private Connection mockConnection;
    private PreparedStatement mockStatement;
    private ResultSet mockResultSet;
    private static AdvertisementDao<Publisher> publisherDao;

    @BeforeAll
    public static void init() {
        connectionFactory = mock(ConnectionFactory.class);
        publisherDao = new PublisherAdvertisementDaoImpl(connectionFactory);
    }

    @BeforeEach
    public void reInitProviderDao() throws SQLException {
        mockConnection = mock(Connection.class);
        mockStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        when(connectionFactory.getConnection()).thenReturn(mockConnection);
    }

    /**
     * Testing method addPublisher when result true.
     *
     * @throws SQLException exception.
     */
    @Test
    public void testAddDevice() throws SQLException, DaoException {
        Publisher publisher = new Publisher();
        publisher.setPubId(1L);
        publisher.setName("Publisher ltd");
        publisher.setAddress("London");
        publisher.setTelephone("035-14-58");
        publisher.setCountry("GB");
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
        int result = publisherDao.create(publisher);
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
    public void testUpdateProvider() throws SQLException, DaoException {
        Publisher publisherUpdate = new Publisher();
        publisherUpdate.setPubId(1L);
        publisherUpdate.setName("Publisher ltd Update");
        publisherUpdate.setAddress("London Update");
        publisherUpdate.setTelephone("035-14-58 Update");
        publisherUpdate.setCountry("GB Update");
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
    public void testGetAllProviders() throws SQLException, DaoException {
        boolean result = false;
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(result = true).thenReturn(false);
        List<Publisher> listDevices = publisherDao.getAll();
        Assertions.assertTrue(result == true);
    }

}
