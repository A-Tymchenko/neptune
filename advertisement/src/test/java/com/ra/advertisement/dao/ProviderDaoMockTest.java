package com.ra.advertisement.dao;

import com.ra.advertisement.connection.ConnectionFactory;
import com.ra.advertisement.dao.exceptions.DaoException;
import com.ra.advertisement.entity.Provider;
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

public class ProviderDaoMockTest {
    private static ConnectionFactory connectionFactory;
    private static AdvertisementDao<Provider> providerDao;
    private Connection mockConnection;
    private PreparedStatement mockStatement;
    private ResultSet mockResultSet;
    private Provider provider;
    private Provider providerUpdate;

    @BeforeAll
    public static void init() {
        connectionFactory = mock(ConnectionFactory.class);
        providerDao = new ProviderAdvertisementDaoImpl(connectionFactory);
    }

    @BeforeEach
    public void reInitProviderDao() throws SQLException {
        mockConnection = mock(Connection.class);
        mockStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        when(connectionFactory.getConnection()).thenReturn(mockConnection);
        provider = new Provider("Coca Cola ltd", "Kyiv", "22-14-45", "Ukraine");
        providerUpdate = new Provider(1L, "Coca Cola ltd Update", "Kyiv Update",
                "22-14-45 Update", "Ukraine Update");
    }

    /**
     * Testing method addProvider when result true.
     *
     * @throws SQLException exception.
     */
    @Test
    public void testAddProvider() throws SQLException, DaoException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
        when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        Integer result = providerDao.create(provider);
        Assertions.assertTrue(result == 1);
    }

    /**
     * Testing method addProvider and get generated key when result true.
     *
     * @throws SQLException exception.
     */
    @Test
    public void testAddProviderAndGeneratedKeyReturnTrue() throws SQLException, DaoException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
        when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        while (mockResultSet.next()){
            provider.setProvId(mockResultSet.getLong(1));
        }
        Integer result = providerDao.create(provider);
        Assertions.assertTrue(result == 1);
    }

    /**
     * Testing method deleteProvider when result true.
     *
     * @throws SQLException
     */
    @Test
    public void testDeleteProvider() throws SQLException, DaoException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
        int rezult = providerDao.delete(2L);
        Assertions.assertTrue(rezult == 1);
    }

    /**
     * Testing method updateProvider when result true.
     *
     * @throws SQLException
     */
    @Test
    public void testUpdateProvider() throws SQLException, DaoException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
        int result = providerDao.update(providerUpdate);
        Assertions.assertTrue(result == 1);
    }

    /**
     * Testing method getAllProviders when result true.
     *
     * @throws SQLException
     */
    @Test
    public void testGetAllProviders() throws SQLException, DaoException {
        boolean result = false;
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(result = true).thenReturn(false);
        List<Provider> listDevices = providerDao.getAll();
        Assertions.assertTrue(result == true);
    }

    /**
     * Testing method getProviderById when Optional.empty result true.
     *
     * @throws SQLException
     * @throws DaoException
     */
    @Test
    public void whenGetByIdReturnEmptyResultSetThenEmptyOptionalShouldBeReturned() throws DaoException, SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);
        Optional<Provider> provider = providerDao.getById(Long.valueOf(1L));
        assertEquals(Optional.empty(), provider);
    }

    /**
     * Testing method getProviderById when Optional is not empty result true.
     *
     * @throws SQLException
     * @throws DaoException
     */
    @Test
    public void whenGetByIdReturnNotEmptyResultSetThenProviderOptionalShouldBeReturned() throws DaoException, SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);
        Optional<Provider> providerOptional = providerDao.getById(1L);
    }

    /**
     * Testing method deleteProvider catch SQLException throw DaoException.
     *
     * @throws SQLException
     */
    @Test
    public void deleteProviderThrowDaoException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException());
        assertThrows(DaoException.class, () -> {
            providerDao.delete(1L);
        });
    }

    /**
     * Testing method addProvider catch SQLException throw DaoException.
     *
     * @throws SQLException
     */
    @Test
    public void addProviderThrowDaoException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException());
        assertThrows(DaoException.class, () -> {
            providerDao.create(provider);
        });
    }

    /**
     * Testing method UpdateProvider catch SQLException throw DaoException.
     *
     * @throws SQLException
     */
    @Test
    public void updateProviderThrowDaoException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException());
        assertThrows(DaoException.class, () -> {
            providerDao.update(providerUpdate);
        });
    }

    /**
     * Testing method getAllProviders catch SQLException throw DaoException.
     *
     * @throws SQLException
     */
    @Test
    public void getAllProvidersThrowDaoException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException());
        assertThrows(DaoException.class, () -> {
            providerDao.getAll();
        });
    }

    /**
     * Testing method getByIdProvider catch SQLException throw DaoException.
     *
     * @throws SQLException
     */
    @Test
    public void getByIdProviderThrowDaoException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException());
        assertThrows(DaoException.class, () -> {
            providerDao.getById(2L);
        });
    }
}

