package com.ra.advertisement.dao;

import com.ra.advertisement.connection.ConnectionFactory;
import com.ra.advertisement.dao.exceptions.DaoException;
import com.ra.advertisement.entity.Device;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DeviceDaoMockTest {
    private static ConnectionFactory connectionFactory;
    private Connection mockConnection;
    private PreparedStatement mockStatement;
    private ResultSet mockResultSet;
    private static DeviceAdvertisementDaoImpl deviceDao;
    private Device device;
    private Device deviceUpdate;

    @BeforeAll
    public static void init() {
        connectionFactory = mock(ConnectionFactory.class);
        deviceDao = new DeviceAdvertisementDaoImpl(connectionFactory);
    }

    @BeforeEach
    public void reInitDeviceDao() throws SQLException {
        mockConnection = mock(Connection.class);
        mockStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        when(connectionFactory.getConnection()).thenReturn(mockConnection);
        device = new Device("Device Name", "Device Model", "Device Type");
        deviceUpdate = new Device(1l, "Device Name Update", "Device Model Update", "Device Type Update");
    }

    /**
     * Testing method addDevice when result true.
     *
     * @throws SQLException exception.
     */
    @Test
    public void testAddDevice() throws SQLException, DaoException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
        when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);
        Integer result = deviceDao.create(device);
        Assertions.assertTrue(result == 1);
    }

    /**
     * Testing method addDevice and get generated key when result true.
     *
     * @throws SQLException exception.
     */
    @Test
    public void testAddDeviceGeneratedKeyReturnTrue() throws SQLException, DaoException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
        when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        while (mockResultSet.next()){
            device.setDevId(mockResultSet.getLong(1));
        }
        Integer result = deviceDao.create(device);
        Assertions.assertTrue(result == 1);
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
        Integer result = deviceDao.delete(2L);
        Assertions.assertTrue(result == 1);
    }

    /**
     * Testing method updateDevice when result true.
     *
     * @throws SQLException
     */
    @Test
    public void testUpdateDevice() throws SQLException, DaoException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
        int result = deviceDao.update(deviceUpdate);
        Assertions.assertTrue(result == 1);
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
        when(mockResultSet.next()).thenReturn(result = true).thenReturn(false);
        List<Device> listDevices = deviceDao.getAll();
        Assertions.assertTrue(result == true);
    }

    /**
     * Testing method getByIdDevice when Optional.empty result true.
     *
     * @throws SQLException
     * @throws DaoException
     */
    @Test
    public void whenGetByIdReturnEmptyResultSetThenEmptyOptionalShouldBeReturned() throws DaoException, SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);
        Optional<Device> device = deviceDao.getById(Long.valueOf(1L));
        assertEquals(Optional.empty(), device);
    }

    /**
     * Testing method getByIdDevice when Optional is not empty result true.
     *
     * @throws SQLException
     * @throws DaoException
     */
    @Test
    public void whenGetByIdReturnNotEmptyResultSetThenDeviceOptionalShouldBeReturned() throws DaoException, SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);
        Optional<Device> device = deviceDao.getById(1L);
    }

    /**
     * Testing method deleteDevice catch SQLException throw DaoException.
     *
     * @throws SQLException
     */
    @Test
    public void deleteDeviceThrowDaoException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException());
        assertThrows(DaoException.class, () -> {
            deviceDao.delete(1L);
        });
    }

    /**
     * Testing method addDevice catch SQLException throw DaoException.
     *
     * @throws SQLException
     */
    @Test
    public void addDeviceThrowDaoException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException());
        assertThrows(DaoException.class, () -> {
            deviceDao.create(device);
        });
    }

    /**
     * Testing method UpdateDevice catch SQLException throw DaoException.
     *
     * @throws SQLException
     */
    @Test
    public void updateDeviceThrowDaoException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException());
        assertThrows(DaoException.class, () -> {
            deviceDao.update(deviceUpdate);
        });
    }

    /**
     * Testing method getAllDevices catch SQLException throw DaoException.
     *
     * @throws SQLException
     */
    @Test
    public void getAllDeviceThrowDaoException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException());
        assertThrows(DaoException.class, () -> {
            deviceDao.getAll();
        });
    }

    /**
     * Testing method getById catch SQLException throw DaoException.
     *
     * @throws SQLException
     */
    @Test
    public void getByIdDeviceThrowDaoException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException());
        assertThrows(DaoException.class, () -> {
            deviceDao.getById(2L);
        });
    }
}
