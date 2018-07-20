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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PublisherDaoMockTest {
    private static final String CREATE_PUBLISHER = "INSERT INTO PUBLISHER (NAME, ADDRESS, "
            + "TELEPHONE, COUNTRY) VALUES(?,?,?,?)";
    private static final String GET_PUBLISHER_BY_ID = "SELECT * FROM PUBLISHER WHERE PUB_ID=?";
    private static final String GET_ALL_PUBLISHERS = "SELECT * FROM PUBLISHER";
    private static final String UPDATE_PUBLISHER = "update PUBLISHER set NAME = ?, ADDRESS= ?, "
            + "TELEPHONE = ?, COUNTRY = ? where PUB_ID = ?";
    private static final String DELETE_PUBLISHER = "DELETE FROM PUBLISHER WHERE PUB_ID=?";
    private static ConnectionFactory connectionFactory;
    private static AdvertisementDao<Publisher> publisherDao;
    private Connection mockConnection;
    private PreparedStatement mockStatement;
    private ResultSet mockResultSet;
    private ResultSet mockResultSetForKey;
    private Publisher publisher;
    private Publisher publisherNoId;

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
        mockResultSetForKey = mock(ResultSet.class);
        publisher = new Publisher(1L,"PublisherLtd", "London", "035-45-18", "GB");
        publisherNoId = new Publisher( "PublisherLtd Update", "London Update", "035-45-18 Update",
                "GB Update");
        when(connectionFactory.getConnection()).thenReturn(mockConnection);
        createMocksFromGetByIdMethod();
    }

    /**
     * Testing method addPublisher when result true.
     *
     * @throws SQLException exception.
     */
    @Test
    public void addPublisherExecuteSuccessfuldReturnTrue() throws SQLException, DaoException {
        when(mockConnection.prepareStatement(CREATE_PUBLISHER)).thenReturn(mockStatement);
        when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSetForKey);
        Publisher result = publisherDao.create(publisher);
        assertAll("result",
                () -> assertEquals(result.getPubId(), publisher.getPubId()),
                () -> assertEquals(result.getName(), publisher.getName()),
                () -> assertEquals(result.getAddress(), publisher.getAddress()),
                () -> assertEquals(result.getCountry(), publisher.getCountry()),
                () -> assertEquals(result.getTelephone(), publisher.getTelephone()));
    }

    /**
     * Testing method addPublisher and get generated key when result true.
     *
     * @throws SQLException exception.
     */
    @Test
    public void addPublisherAndDontGetGeneratedIdReturnTrue() throws SQLException, DaoException {
        when(mockConnection.prepareStatement(CREATE_PUBLISHER)).thenReturn(mockStatement);
        when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSetForKey);
        when(mockResultSetForKey.next()).thenReturn(false);
        Publisher result = publisherDao.create(publisherNoId);
        Assertions.assertTrue(result.getPubId() == null);
    }

    /**
     * Testing method addPublisher when we get id of created entity.
     *
     * @throws SQLException exception.
     */
    @Test
    public void addPublisherAndGetGeneratedIdReturnTrue() throws SQLException, DaoException {
        when(mockConnection.prepareStatement(CREATE_PUBLISHER)).thenReturn(mockStatement);
        when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSetForKey);
        when(mockResultSetForKey.next()).thenReturn(true);
        Publisher result = publisherDao.create(publisherNoId);
        Assertions.assertTrue(result.getPubId() == publisher.getPubId());
    }

    /**
     * Testing method deletePublisher when result true.
     *
     * @throws SQLException
     */
    @Test
    public void deletePublisherSuccsessfulReturnTrue() throws SQLException, DaoException {
        when(mockConnection.prepareStatement(DELETE_PUBLISHER)).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
        Integer result = publisherDao.delete(publisher);
        Assertions.assertTrue(result == 1);
    }

    /**
     * Testing method deletePrublisher failed return true.
     *
     * @throws SQLException
     */
    @Test
    public void deletePublisherFailedReturnTrue() throws SQLException, DaoException {
        when(mockConnection.prepareStatement(DELETE_PUBLISHER)).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
        Publisher publisherNull = null;
        Integer result = publisherDao.delete(publisherNull);
        Assertions.assertTrue(result == 0);
    }

    /**
     * Testing method updatePublisher when result true.
     *
     * @throws SQLException
     */
    @Test
    public void updatePublisherSuccessfulReturnTrue() throws SQLException, DaoException {
        when(mockConnection.prepareStatement(UPDATE_PUBLISHER)).thenReturn(mockStatement);
        Publisher result = publisherDao.update(publisher);
        assertAll("result",
                () -> assertEquals(result.getPubId(), publisher.getPubId()),
                () -> assertEquals(result.getName(), publisher.getName()),
                () -> assertEquals(result.getAddress(), publisher.getAddress()),
                () -> assertEquals(result.getCountry(), publisher.getCountry()),
                () -> assertEquals(result.getTelephone(), publisher.getTelephone()));
    }

    /**
     * Testing method getAllPublishers when result true.
     *
     * @throws SQLException
     */
    @Test
    public void getAllPublishersExecutedReturnTrue() throws SQLException, DaoException {
        boolean result = false;
        when(mockConnection.prepareStatement(GET_ALL_PUBLISHERS)).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(result = true).thenReturn(false);
        List<Publisher> listPublishers = publisherDao.getAll();
        Assertions.assertTrue(result == true);
    }

    /**
     * Testing method getPublisherById when Optional.empty result true.
     *
     * @throws SQLException
     * @throws DaoException
     */
    @Test
    public void publisherGetByIdReturnEmptyResultSetThenEmptyOptionalShouldBeReturned() throws DaoException, SQLException {
        when(mockConnection.prepareStatement(GET_PUBLISHER_BY_ID)).thenReturn(mockStatement);
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
    public void publisherGetByIdReturnNotEmptyResultSetThenProviderOptionalShouldBeReturned() throws DaoException, SQLException {
        when(mockConnection.prepareStatement(GET_PUBLISHER_BY_ID)).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);
        Optional<Publisher> publisherOptional = publisherDao.getById(publisher.getPubId());
        Publisher publisherFromOptional = publisherOptional.get();
        assertAll("publisherFromOptional",
                () -> assertEquals(publisherFromOptional.getPubId(), publisher.getPubId()),
                () -> assertEquals(publisherFromOptional.getName(), publisher.getName()),
                () -> assertEquals(publisherFromOptional.getAddress(), publisher.getAddress()),
                () -> assertEquals(publisherFromOptional.getCountry(), publisher.getCountry()),
                () -> assertEquals(publisherFromOptional.getTelephone(), publisher.getTelephone()));
    }

    /**
     * Testing method deletePublisher catch SQLException throw DaoException.
     *
     * @throws SQLException
     */
    @Test
    public void deletePublisherThrowDaoException() throws SQLException {
        when(mockConnection.prepareStatement(DELETE_PUBLISHER)).thenThrow(new SQLException());
        assertThrows(DaoException.class, () -> {
            publisherDao.delete(publisher);
        });
    }

    /**
     * Testing method addPublisher catch SQLException throw DaoException.
     *
     * @throws SQLException
     */
    @Test
    public void addPublisherThrowDaoException() throws SQLException {
        when(mockConnection.prepareStatement(CREATE_PUBLISHER)).thenThrow(new SQLException());
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
        when(mockConnection.prepareStatement(UPDATE_PUBLISHER)).thenThrow(new SQLException());
        assertThrows(DaoException.class, () -> {
            publisherDao.update(publisher);
        });
    }

    /**
     * Testing method getAllPublishers catch SQLException throw DaoException.
     *
     * @throws SQLException
     */
    @Test
    public void getAllPublishersThrowDaoException() throws SQLException {
        when(mockConnection.prepareStatement(GET_ALL_PUBLISHERS)).thenThrow(new SQLException());
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
        when(mockConnection.prepareStatement(GET_PUBLISHER_BY_ID)).thenThrow(new SQLException());
        assertThrows(DaoException.class, () -> {
            publisherDao.getById(2L);
        });
    }

    private void createMocksFromGetByIdMethod() throws SQLException {
        when(mockConnection.prepareStatement(GET_PUBLISHER_BY_ID)).thenReturn(mockStatement);
        when(mockResultSet.next()).thenReturn(true);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.getLong("PUB_ID")).thenReturn(publisher.getPubId());
        when(mockResultSet.getString("NAME")).thenReturn(publisher.getName());
        when(mockResultSet.getString("ADDRESS")).thenReturn(publisher.getAddress());
        when(mockResultSet.getString("TELEPHONE")).thenReturn(publisher.getTelephone());
        when(mockResultSet.getString("COUNTRY")).thenReturn(publisher.getCountry());
    }
}