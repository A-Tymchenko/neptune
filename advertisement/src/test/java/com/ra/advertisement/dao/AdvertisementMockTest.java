package com.ra.advertisement.dao;

import com.ra.advertisement.connection.ConnectionFactory;
import com.ra.advertisement.dao.exceptions.DaoException;
import com.ra.advertisement.entity.Advertisement;
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

public class AdvertisementMockTest {
    private static ConnectionFactory connectionFactory;
    private static AdvertisementDao<Advertisement> advertDao;
    private Connection mockConnection;
    private PreparedStatement mockStatement;
    private ResultSet mockResultSet;
    private Advertisement advertisement;
    private Advertisement advertisementUpdate;

    @BeforeAll
    public static void init() {
        connectionFactory = mock(ConnectionFactory.class);
        advertDao = new AdvertisementAdvertisementDaoImpl(connectionFactory);
    }

    @BeforeEach
    public void reInitAdvertisementDao() throws SQLException {
        mockConnection = mock(Connection.class);
        mockStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        when(connectionFactory.getConnection()).thenReturn(mockConnection);
        advertisement = new Advertisement("Welcome advert", "Welcome to Ukraine",
                "url", "Ukrainian", 1L);
        advertisementUpdate = new Advertisement(1L, "Welcome advert",
                "Welcome Update", "Welcome to Ukraine Update",
                "UrlUpdate", 1l);
    }

    /**
     * Testing method addAdvertisement when result true.
     *
     * @throws SQLException exception.
     */
    @Test
    public void testAddAdvertisement() throws SQLException, DaoException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
        when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        Integer result = advertDao.create(advertisement);
        Assertions.assertTrue(result == 1);
    }

    /**
     * Testing method addAdvertisement and get generated key when result true.
     *
     * @throws SQLException exception.
     */
    @Test
    public void testAddAdvertisementGeneratedKeyReturnTrue() throws SQLException, DaoException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
        when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        while (mockResultSet.next()){
            advertisement.setAdId(mockResultSet.getLong(1));
        }
        Integer result = advertDao.create(advertisement);
        Assertions.assertTrue(result == 1);
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
        Assertions.assertTrue(result == 1);
    }

    /**
     * Testing method updateAdvertisement when result true.
     *
     * @throws SQLException
     */
    @Test
    public void testUpdateAdvertisement() throws SQLException, DaoException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
        int result = advertDao.update(advertisementUpdate);
        Assertions.assertTrue(result == 1);
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
        when(mockResultSet.next()).thenReturn(result = true).thenReturn(false);
        List<Advertisement> listAdvertisement = advertDao.getAll();
        Assertions.assertTrue(result == true);
    }

    /**
     * Testing method getAdvertisementById when Optional.empty result true.
     *
     * @throws SQLException
     * @throws DaoException
     */
    @Test
    public void whenGetByIdReturnEmptyResultSetThenEmptyOptionalShouldBeReturned() throws DaoException, SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);
        Optional<Advertisement> advertisement = advertDao.getById(Long.valueOf(1L));
        assertEquals(Optional.empty(), advertisement);
    }

    /**
     * Testing method getAdvertisementById when Optional is not empty result true.
     *
     * @throws SQLException
     * @throws DaoException
     */
    @Test
    public void whenGetByIdReturnNotEmptyResultSetThenAdvertisementOptionalShouldBeReturned() throws DaoException, SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);
        Optional<Advertisement> advertisement = advertDao.getById(1L);
    }

    /**
     * Testing method deleteAdvertisement catch SQLException throw DaoException.
     *
     * @throws SQLException
     */
    @Test
    public void deleteAdvertThrowDaoException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException());
        assertThrows(DaoException.class, () -> {
            advertDao.delete(1L);
        });
    }

    /**
     * Testing method addAdvertisement catch SQLException throw DaoException.
     *
     * @throws SQLException
     */
    @Test
    public void addAdvertisementThrowDaoException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException());
        assertThrows(DaoException.class, () -> {
            advertDao.create(advertisement);
        });
    }

    /**
     * Testing method UpdateAdvertisement catch SQLException throw DaoException.
     *
     * @throws SQLException
     */
    @Test
    public void updateAdvertisementThrowDaoException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException());
        assertThrows(DaoException.class, () -> {
            advertDao.update(advertisementUpdate);
        });
    }

    /**
     * Testing method getAllAdverts catch SQLException throw DaoException.
     *
     * @throws SQLException
     */
    @Test
    public void getAllAdvertsThrowDaoException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException());
        assertThrows(DaoException.class, () -> {
            advertDao.getAll();
        });
    }

    /**
     * Testing method getAdvertisementById catch SQLException throw DaoException.
     *
     * @throws SQLException
     */
    @Test
    public void getByIdAdvertisementThrowDaoException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException());
        assertThrows(DaoException.class, () -> {
            advertDao.getById(2L);
        });
    }
}