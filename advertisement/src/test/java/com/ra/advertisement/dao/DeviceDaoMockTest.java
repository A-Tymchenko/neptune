package com.ra.advertisement.dao;

import com.ra.advertisement.connection.ConnectionFactory;
import com.ra.advertisement.dao.exceptions.DaoException;
import com.ra.advertisement.model.entities.Device;
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

public class DeviceDaoMockTest {
    private static ConnectionFactory connectionFactory;
    private Connection mockConnection;
    private PreparedStatement mockStatement;
    private ResultSet mockResultSet;
    private static AdvertisementDao<Device> deviceDao;

    @BeforeAll
    public static void init(){
        connectionFactory = mock(ConnectionFactory.class);
        deviceDao = new DeviceAdvertisementDaoImpl(connectionFactory);
    }

    @BeforeEach
    public void reInitDeviceDao() throws SQLException {
        mockConnection = mock(Connection.class);
        mockStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        when(connectionFactory.getConnection()).thenReturn(mockConnection);
    }

    /**
     * Testing method addDevice when result true.
     *
     * @throws SQLException exception.
     */
    @Test
    public void testAddDevice() throws SQLException, DaoException {
        Device device = new Device();
        device.setDevId(1L);
        device.setName("Phones");
        device.setDeviceType("Mobile Phone");
        device.setModel("25-17");
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
        int result = deviceDao.create(device);
        Assertions.assertTrue( result == 1);
    }

    /**
     * Testing method deleteDevice when result true.
     *
     * @throws SQLException
     */
    @Test
    public void testDeleteDevice() throws SQLException, DaoException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
        int rezult = deviceDao.delete(2L);
        Assertions.assertTrue( rezult == 1);
    }

    /**
     * Testing method updateDevice when result true.
     *
     * @throws SQLException
     */
    @Test
    public void testUpdateDevice() throws SQLException, DaoException {
        Device deviceUpdate = new Device();
        deviceUpdate.setDevId(1L);
        deviceUpdate.setName("Phones Update");
        deviceUpdate.setDeviceType("Mobile Phone Update");
        deviceUpdate.setModel("25-17 Update");
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
        int result = deviceDao.update(deviceUpdate);
        Assertions.assertTrue( result == 1);
    }

    /**
     * Testing method getAllDevices when result true.
     *
     * @throws SQLException
     */
    @Test
    public void testGetAllDevice() throws SQLException, DaoException {
        boolean result = false;
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(result=true).thenReturn(false);
        List<Device> listDevices = deviceDao.getAll();
        Assertions.assertTrue( result == true);
    }


}
