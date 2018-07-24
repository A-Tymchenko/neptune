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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProviderDaoMockTest {
    private static ConnectionFactory connectionFactory;
    private static AdvertisementDao<Provider> providerDao;
    private static final String CREATE_PROVIDER = "INSERT INTO PROVIDER (NAME, ADDRESS, "
            + "TELEPHONE, COUNTRY) VALUES(?,?,?,?)";
    private static final String GET_PROVIDER_BY_ID = "SELECT * FROM PROVIDER WHERE PROV_ID=?";
    private static final String GET_ALL_PROVIDERS = "SELECT * FROM PROVIDER";
    private static final String UPDATE_PROVIDER = "update PROVIDER set NAME = ?, ADDRESS= ?, "
            + "TELEPHONE = ?, COUNTRY = ? where PROV_ID = ?";
    private static final String DELETE_PROVIDER = "DELETE FROM PROVIDER WHERE PROV_ID=?";
    private Connection mockConnection;
    private PreparedStatement mockStatement;
    private ResultSet mockResultSet;
    private ResultSet mockResultSetForKey;
    private Provider provider;
    private Provider providerNoId;

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
        mockResultSetForKey = mock(ResultSet.class);
        provider = new Provider(1L, "Coca Cola ltd", "Kyiv", "22-14-45", "Ukraine");
        providerNoId = new Provider("Coca Cola ltd ", "Kyiv ",
                "22-14-45 ", "Ukraine ");
        when(connectionFactory.getConnection()).thenReturn(mockConnection);
        createMocksFromGetByIdMethod();
    }

    /**
     * Testing method addProvider when result true.
     *
     * @throws SQLException exception.
     */
    @Test
    public void addProviderExecuteSuccessfuldReturnTrue() throws SQLException, DaoException {
        when(mockConnection.prepareStatement(CREATE_PROVIDER)).thenReturn(mockStatement);
        when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSetForKey);
        Provider result = providerDao.create(provider);
        assertAll("result",
                () -> assertEquals(result.getProvId(), provider.getProvId()),
                () -> assertEquals(result.getName(), provider.getName()),
                () -> assertEquals(result.getAddress(), provider.getAddress()),
                () -> assertEquals(result.getCountry(), provider.getCountry()),
                () -> assertEquals(result.getTelephone(), provider.getTelephone()));
    }

    /**
     * Testing method addProvider and get generated key when result true.
     *
     * @throws SQLException exception.
     */
    @Test
    public void addProviderAndDontGetGeneratedIdReturnTrue() throws SQLException, DaoException {
        when(mockConnection.prepareStatement(CREATE_PROVIDER)).thenReturn(mockStatement);
        when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSetForKey);
        when(mockResultSetForKey.next()).thenReturn(false);
        Provider result = providerDao.create(providerNoId);
        Assertions.assertTrue(result.getProvId() == null);
    }

    /**
     * Testing method addProvider when we get id of created entity.
     *
     * @throws SQLException exception.
     */
    @Test
    public void addProviderAndGetGeneratedIdReturnTrue() throws SQLException, DaoException {
        when(mockConnection.prepareStatement(CREATE_PROVIDER)).thenReturn(mockStatement);
        when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSetForKey);
        when(mockResultSetForKey.next()).thenReturn(true);
        Provider result = providerDao.create(providerNoId);
        Assertions.assertTrue(result.getProvId() == provider.getProvId());
    }

    /**
     * Testing method deleteProvider when result true.
     *
     * @throws SQLException
     */
    @Test
    public void deleteProviderSuccsessfulReturnTrue() throws SQLException, DaoException {
        when(mockConnection.prepareStatement(DELETE_PROVIDER)).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
        Integer result = providerDao.delete(provider);
        Assertions.assertTrue(result == 1);
    }

    /**
     * Testing method deleteProvider failed return true.
     *
     * @throws SQLException
     */
    @Test
    public void deleteProviderFailedReturnTrue() throws SQLException, DaoException {
        when(mockConnection.prepareStatement(DELETE_PROVIDER)).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
        Provider providerNull = null;
        Integer result = providerDao.delete(providerNull);
        Assertions.assertTrue(result == 0);
    }

    /**
     * Testing method updateProvider when result true.
     *
     * @throws SQLException
     */
    @Test
    public void updateProviderSuccessfulReturnTrue() throws SQLException, DaoException {
        when(mockConnection.prepareStatement(UPDATE_PROVIDER)).thenReturn(mockStatement);
        Provider result = providerDao.update(provider);
        assertAll("result",
                () -> assertEquals(result.getProvId(), provider.getProvId()),
                () -> assertEquals(result.getName(), provider.getName()),
                () -> assertEquals(result.getAddress(), provider.getAddress()),
                () -> assertEquals(result.getCountry(), provider.getCountry()),
                () -> assertEquals(result.getTelephone(), provider.getTelephone()));
    }

    /**
     * Testing method getAllProviders when result true.
     *
     * @throws SQLException
     */
    @Test
    public void getAllProvidersExecutedReturnTrue() throws SQLException, DaoException {
        boolean result = false;
        when(mockConnection.prepareStatement(GET_ALL_PROVIDERS)).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(result = true).thenReturn(false);
        List<Provider> listProviders = providerDao.getAll();
        Assertions.assertTrue(result == true);
    }

    /**
     * Testing method getProviderById when Optional.empty result true.
     *
     * @throws SQLException
     * @throws DaoException
     */
    @Test
    public void providerGetByIdReturnEmptyResultSetThenEmptyOptionalShouldBeReturned() throws DaoException, SQLException {
        when(mockConnection.prepareStatement(GET_PROVIDER_BY_ID)).thenReturn(mockStatement);
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
    public void providerGetByIdReturnNotEmptyResultSetThenProviderOptionalShouldBeReturned() throws DaoException, SQLException {
        when(mockConnection.prepareStatement(GET_PROVIDER_BY_ID)).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);
        Optional<Provider> providerOptional = providerDao.getById(provider.getProvId());
        Provider providerFromOptional = providerOptional.get();
        assertAll("providerFromOptional",
                () -> assertEquals(providerFromOptional.getProvId(), provider.getProvId()),
                () -> assertEquals(providerFromOptional.getName(), provider.getName()),
                () -> assertEquals(providerFromOptional.getAddress(), provider.getAddress()),
                () -> assertEquals(providerFromOptional.getCountry(), provider.getCountry()),
                () -> assertEquals(providerFromOptional.getTelephone(), provider.getTelephone()));
    }

    /**
     * Testing method deleteProvider catch SQLException throw DaoException.
     *
     * @throws SQLException
     */
    @Test
    public void deleteProviderThrowDaoException() throws SQLException {
        when(mockConnection.prepareStatement(DELETE_PROVIDER)).thenThrow(new SQLException());
        assertThrows(DaoException.class, () -> {
            providerDao.delete(provider);
        });
    }

    /**
     * Testing method addProvider catch SQLException throw DaoException.
     *
     * @throws SQLException
     */
    @Test
    public void addProviderThrowDaoException() throws SQLException {
        when(mockConnection.prepareStatement(CREATE_PROVIDER)).thenThrow(new SQLException());
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
        when(mockConnection.prepareStatement(UPDATE_PROVIDER)).thenThrow(new SQLException());
        assertThrows(DaoException.class, () -> {
            providerDao.update(provider);
        });
    }

    /**
     * Testing method getAllProviders catch SQLException throw DaoException.
     *
     * @throws SQLException
     */
    @Test
    public void getAllProvidersThrowDaoException() throws SQLException {
        when(mockConnection.prepareStatement(GET_ALL_PROVIDERS)).thenThrow(new SQLException());
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
        when(mockConnection.prepareStatement(GET_PROVIDER_BY_ID)).thenThrow(new SQLException());
        assertThrows(DaoException.class, () -> {
            providerDao.getById(2L);
        });
    }

    private void createMocksFromGetByIdMethod() throws SQLException {
        when(mockConnection.prepareStatement(GET_PROVIDER_BY_ID)).thenReturn(mockStatement);
        when(mockResultSet.next()).thenReturn(true);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.getLong("PROV_ID")).thenReturn(provider.getProvId());
        when(mockResultSet.getString("NAME")).thenReturn(provider.getName());
        when(mockResultSet.getString("ADDRESS")).thenReturn(provider.getAddress());
        when(mockResultSet.getString("TELEPHONE")).thenReturn(provider.getTelephone());
        when(mockResultSet.getString("COUNTRY")).thenReturn(provider.getCountry());
    }
}

