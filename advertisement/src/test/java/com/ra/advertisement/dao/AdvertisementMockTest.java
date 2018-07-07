package com.ra.advertisement.dao;

import com.ra.advertisement.connection.ConnectionFactory;
import com.ra.advertisement.dao.exceptions.DaoException;
import com.ra.advertisement.model.entities.Advertisement;
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

public class AdvertisementMockTest {
    private static ConnectionFactory connectionFactory;
    private Connection mockConnection;
    private PreparedStatement mockStatement;
    private ResultSet mockResultSet;
    private static AdvertisementDao<Advertisement> advertDao;

    @BeforeAll
    public static void init(){
        connectionFactory = mock(ConnectionFactory.class);
        advertDao = new AdvertisementAdvertisementDaoImpl(connectionFactory);
    }

    @BeforeEach
    public void reInitDeviceDao() throws SQLException {
        mockConnection = mock(Connection.class);
        mockStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        when(connectionFactory.getConnection()).thenReturn(mockConnection);
    }

    /**
     * Testing method addAdvertisement when result true.
     *
     * @throws SQLException exception.
     */
    @Test
    public void testAddAdvertisement() throws SQLException, DaoException {
        Advertisement advertisement = new Advertisement();
        advertisement.setAdId(1L);
        advertisement.setProvId(1L);
        advertisement.setTitle("Welcoming advert");
        advertisement.setLanguage("Ukrainian");
        advertisement.setContext("Welcome to Ukraine");
        advertisement.setImageUrl(".//url");
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
        int result = advertDao.create(advertisement);
        Assertions.assertTrue( result == 1);
    }

    /**
     * Testing method deleteAdvertisement when result true.
     *
     * @throws SQLException
     */
    @Test
    public void testDeleteAdvertisement() throws SQLException, DaoException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
        int result = advertDao.delete(2L);
        Assertions.assertTrue( result == 1);
    }

    /**
     * Testing method updateAdvertisement when result true.
     *
     * @throws SQLException
     */
    @Test
    public void testUpdateAdvertisement() throws SQLException, DaoException {
        Advertisement advertisementUpdate = new Advertisement();
        advertisementUpdate.setAdId(1L);
        advertisementUpdate.setProvId(1L);
        advertisementUpdate.setTitle("Welcoming advert Update");
        advertisementUpdate.setLanguage("Ukrainian update");
        advertisementUpdate.setContext("Welcome to Ukraine update");
        advertisementUpdate.setImageUrl(".//url update");
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
        int result = advertDao.update(advertisementUpdate);
        Assertions.assertTrue( result == 1);
    }

    /**
     * Testing method getAllAdvertisement when result true.
     *
     * @throws SQLException
     */
    @Test
    public void testGetAllAdvertisement() throws SQLException, DaoException {
        boolean result = false;
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(result=true).thenReturn(false);
        List<Advertisement> listAdvertisement = advertDao.getAll();
        Assertions.assertTrue( result == true);
    }

}
