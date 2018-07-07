package com.ra.advertisement.dao;

import com.ra.advertisement.connection.ConnectionFactory;
import com.ra.advertisement.dao.exceptions.DaoException;
import com.ra.advertisement.model.entities.Provider;
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

public class ProviderDaoMockTest {
    private static ConnectionFactory connectionFactory;
    private Connection mockConnection;
    private PreparedStatement mockStatement;
    private ResultSet mockResultSet;
    private static AdvertisementDao<Provider> providerDao;

    @BeforeAll
    public static void init(){
        connectionFactory = mock(ConnectionFactory.class);
        providerDao = new ProviderAdvertisementDaoImpl(connectionFactory);
    }

    @BeforeEach
    public void reInitProviderDao() throws SQLException {
        mockConnection = mock(Connection.class);
        mockStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        when(connectionFactory.getConnection()).thenReturn(mockConnection);
    }

    /**
     * Testing method addProvider when result true.
     *
     * @throws SQLException exception.
     */
    @Test
    public void testAddProvider() throws SQLException, DaoException {
        Provider provider = new Provider();
        provider.setProvId(1L);
        provider.setName("Coca Cola ltd");
        provider.setAddress("Kyiv");
        provider.setTelephone("22-14-58");
        provider.setCountry("Ukraine");
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
        int result = providerDao.create(provider);
        Assertions.assertTrue( result == 1);
    }

    /**
     * Testing method addProvider when result true.
     *
     * @throws SQLException exception.
     */
    @Test
    public void testAddProviderReturnNotExecuted() throws SQLException, DaoException {
        Provider provider = new Provider();
        provider.setProvId(1L);
        provider.setName("Coca Cola ltd");
        provider.setAddress("Kyiv");
        provider.setTelephone("22-14-58");
        provider.setCountry("Ukraine");
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(0);
        int result = providerDao.create(provider);
        Assertions.assertTrue( result == 0);
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
        Assertions.assertTrue( rezult == 1);
    }

    /**
     * Testing method updateProvider when result true.
     *
     * @throws SQLException
     */
    @Test
    public void testUpdateProvider() throws SQLException, DaoException {
        Provider providerUpdate = new Provider();
        providerUpdate.setProvId(1L);
        providerUpdate.setName("Coca Cola ltd Update");
        providerUpdate.setAddress("Kyiv Update");
        providerUpdate.setTelephone("22-14-58 Update");
        providerUpdate.setCountry("Ukraine Update");
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
        int result = providerDao.update(providerUpdate);
        Assertions.assertTrue( result == 1);
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
        when(mockResultSet.next()).thenReturn(result=true).thenReturn(false);
        List<Provider> listDevices = providerDao.getAll();
        Assertions.assertTrue( result == true);
    }


}
