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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AdvertisementMockTest {
      private static final String CREATE_ADVERTISEMENT = "INSERT INTO ADVERTISEMENT (TITLE, CONTEXT, IMAGE_URL, LANGUAGE) "
            + "VALUES(?,?,?,?)";
    private static final String GET_ADVER_BY_ID = "SELECT * FROM ADVERTISEMENT WHERE AD_ID=?";
    private static final String GET_ALL_ADVERTS = "SELECT * FROM ADVERTISEMENT";
    private static final String UPDATE_ADVERT = "update ADVERTISEMENT set TITLE = ?, CONTEXT= ?, IMAGE_URL = ?,"
            + " LANGUAGE = ? where AD_ID = ?";
    private static final String DELETE_ADVERT = "DELETE FROM ADVERTISEMENT WHERE AD_ID=?";
    private static ConnectionFactory connectionFactory;
    private static AdvertisementDao<Advertisement> advertDao;
    private Connection mockConnection;
    private PreparedStatement mockStatement;
    private ResultSet mockResultSet;
    private ResultSet mockResultSetForKey;
    private Advertisement advertisement;
    private Advertisement advertisementNoId;

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
        mockResultSetForKey = mock(ResultSet.class);
        advertisement = new Advertisement(1L, "Welcome advert", "Welcome to Ukraine",
                "url", "Ukrainian");
        advertisementNoId = new Advertisement("Welcome advert", "Welcome to Ukraine",
                "url", "Ukrainian");
        when(connectionFactory.getConnection()).thenReturn(mockConnection);
        createMocksFromGetByIdMethod();
    }

    /**
     * Testing method addAdvertisement when result true.
     *
     * @throws SQLException exception.
     */
    @Test
    public void addAdvertisementExecuteSuccessfuldReturnTrue() throws SQLException, DaoException {
        when(mockConnection.prepareStatement(CREATE_ADVERTISEMENT)).thenReturn(mockStatement);
        when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSetForKey);
        Advertisement result = advertDao.create(advertisement);
        assertAll("result",
                () -> assertEquals(result.getAdId(), advertisement.getAdId()),
                () -> assertEquals(result.getTitle(), advertisement.getTitle()),
                () -> assertEquals(result.getContext(), advertisement.getContext()),
                () -> assertEquals(result.getImageUrl(), advertisement.getImageUrl()),
                () -> assertEquals(result.getLanguage(), advertisement.getLanguage()));
    }

    /**
     * Testing method addAdvertisement when we don't get id of created entity.
     *
     * @throws SQLException exception.
     */
    @Test
    public void addAdvertisementAndDontGetGeneratedIdReturnTrue() throws SQLException, DaoException {
        when(mockConnection.prepareStatement(CREATE_ADVERTISEMENT)).thenReturn(mockStatement);
        when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSetForKey);
        when(mockResultSetForKey.next()).thenReturn(false);
        Advertisement result = advertDao.create(advertisementNoId);
        Assertions.assertTrue(result.getAdId() == null);
    }

    /**
     * Testing method addAdvertisement when we get id of created entity.
     *
     * @throws SQLException exception.
     */
    @Test
    public void addAdvertisementAndGetGeneratedIdReturnTrue() throws SQLException, DaoException {
        when(mockConnection.prepareStatement(CREATE_ADVERTISEMENT)).thenReturn(mockStatement);
        when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSetForKey);
        when(mockResultSetForKey.next()).thenReturn(true);
        Advertisement result = advertDao.create(advertisementNoId);
        Assertions.assertTrue(result.getAdId() == advertisement.getAdId());
    }

    /**
     * Testing method deleteAdvertisement when result true.
     *
     * @throws SQLException
     */
    @Test
    public void deleteAdvertisementSuccessfulReturnTrue() throws SQLException, DaoException {
        when(mockConnection.prepareStatement(DELETE_ADVERT)).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
        Integer result = advertDao.delete(advertisement);
        Assertions.assertTrue(result == 1);
    }

    /**
     * Testing method deleteAdvertisement failed return true.
     *
     * @throws SQLException
     */
    @Test
    public void testDeleteAdvertisementFailedReturnTrue() throws SQLException, DaoException {
        when(mockConnection.prepareStatement(DELETE_ADVERT)).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
        Advertisement advertisementNull = null;
        Integer result = advertDao.delete(advertisementNull);
        Assertions.assertTrue(result == 0);
    }

    /**
     * Testing method updateAdvertisement when result true.
     *
     * @throws SQLException
     */
    @Test
    public void updateAdvertisementSuccessfulReturnTrue() throws SQLException, DaoException {
        when(mockConnection.prepareStatement(UPDATE_ADVERT)).thenReturn(mockStatement);
        Advertisement result = advertDao.update(advertisement);
        assertAll("result",
                () -> assertEquals(result.getAdId(), advertisement.getAdId()),
                () -> assertEquals(result.getTitle(), advertisement.getTitle()),
                () -> assertEquals(result.getContext(), advertisement.getContext()),
                () -> assertEquals(result.getImageUrl(), advertisement.getImageUrl()),
                () -> assertEquals(result.getLanguage(), advertisement.getLanguage()));
    }

    /**
     * Testing method getAllAdvertisement when result true.
     *
     * @throws SQLException
     */
    @Test
    public void getAllAdvertisementExecutedReturnTrue() throws SQLException, DaoException {
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
    public void advertisementGetByIdReturnEmptyResultSetThenEmptyOptionalShouldBeReturned() throws
            DaoException, SQLException {
        when(mockConnection.prepareStatement(GET_ADVER_BY_ID)).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);
        Optional<Advertisement> advertisementOptional = advertDao.getById(advertisement.getAdId());
        assertEquals(Optional.empty(), advertisementOptional);
    }

    /**
     * Testing method getAdvertisementById when Optional is not empty result true.
     *
     * @throws SQLException
     * @throws DaoException
     */
    @Test
    public void advertisementGetByIdReturnNotEmptyResultSetThenAdvertisementOptionalShouldBeReturned() throws
            DaoException, SQLException {
        when(mockConnection.prepareStatement(GET_ADVER_BY_ID)).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);
        Optional<Advertisement> advertisementOptional = advertDao.getById(advertisement.getAdId());
        Advertisement advertisementFromOptional = advertisementOptional.get();
        assertAll("advertisementFromOptional",
                () -> assertEquals(advertisementFromOptional.getAdId(), advertisement.getAdId()),
                () -> assertEquals(advertisementFromOptional.getTitle(), advertisement.getTitle()),
                () -> assertEquals(advertisementFromOptional.getContext(), advertisement.getContext()),
                () -> assertEquals(advertisementFromOptional.getImageUrl(), advertisement.getImageUrl()),
                () -> assertEquals(advertisementFromOptional.getLanguage(), advertisement.getLanguage()));
    }

    /**
     * Testing method deleteAdvertisement catch SQLException throw DaoException.
     *
     * @throws SQLException
     */
    @Test
    public void deleteAdvertThrowDaoException() throws SQLException {
        when(mockConnection.prepareStatement(DELETE_ADVERT)).thenThrow(new SQLException());
        assertThrows(DaoException.class, () -> {
            advertDao.delete(advertisement);
        });
    }

    /**
     * Testing method addAdvertisement catch SQLException throw DaoException.
     *
     * @throws SQLException
     */
    @Test
    public void addAdvertisementThrowDaoException() throws SQLException {
        when(mockConnection.prepareStatement(CREATE_ADVERTISEMENT)).thenThrow(new SQLException());
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
        when(mockConnection.prepareStatement(UPDATE_ADVERT)).thenThrow(new SQLException());
        assertThrows(DaoException.class, () -> {
            advertDao.update(advertisement);
        });
    }

    /**
     * Testing method getAllAdverts catch SQLException throw DaoException.
     *
     * @throws SQLException
     */
    @Test
    public void getAllAdvertsThrowDaoException() throws SQLException {
        when(mockConnection.prepareStatement(GET_ALL_ADVERTS)).thenThrow(new SQLException());
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
        when(mockConnection.prepareStatement(GET_ADVER_BY_ID)).thenThrow(new SQLException());
        assertThrows(DaoException.class, () -> {
            advertDao.getById(advertisement.getAdId());
        });
    }

    private void createMocksFromGetByIdMethod() throws SQLException {
        when(mockConnection.prepareStatement(GET_ADVER_BY_ID)).thenReturn(mockStatement);
        when(mockResultSet.next()).thenReturn(true);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.getLong("AD_ID")).thenReturn(advertisement.getAdId());
        when(mockResultSet.getString("TITLE")).thenReturn(advertisement.getTitle());
        when(mockResultSet.getString("CONTEXT")).thenReturn(advertisement.getContext());
        when(mockResultSet.getString("IMAGE_URL")).thenReturn(advertisement.getImageUrl());
        when(mockResultSet.getString("LANGUAGE")).thenReturn(advertisement.getLanguage());
    }
}