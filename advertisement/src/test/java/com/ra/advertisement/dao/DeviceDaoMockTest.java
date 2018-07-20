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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DeviceDaoMockTest {
    private static final String CREATE_DEVICE = "INSERT INTO DEVICES (NAME, MODEL, "
            + "DEVICE_TYPE) VALUES(?,?,?)";
    private static final String GET_DEVICE_BY_ID = "SELECT * FROM DEVICES WHERE DEV_ID=?";
    private static final String GET_ALL_DEVICES = "SELECT * FROM DEVICES";
    private static final String UPDATE_DEVICE = "update DEVICES set NAME = ?, MODEL= ?, "
            + "DEVICE_TYPE = ? where DEV_ID = ?";
    private static final String DELETE_DEVICE = "DELETE FROM DEVICES WHERE DEV_ID=?";
    private static ConnectionFactory connectionFactory;
    private static DeviceAdvertisementDaoImpl deviceDao;
    private Connection mockConnection;
    private PreparedStatement mockStatement;
    private ResultSet mockResultSet;
    private ResultSet mockResultSetForKey;
    private Device device;
    private Device devicetNoId;

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
        mockResultSetForKey = mock(ResultSet.class);
        device = new Device(1L, "Device Name", "Device Model", "Device Type");
        devicetNoId = new Device("Device N", "Device N", "Device Type ");
        when(connectionFactory.getConnection()).thenReturn(mockConnection);
        createMocksFromGetByIdMethod();
    }

    /**
     * Testing method addDevice when result true.
     *
     * @throws SQLException exception.
     */
    @Test
    public void addDeviceExecuteSuccessfuldReturnTrue() throws SQLException, DaoException {
        when(mockConnection.prepareStatement(CREATE_DEVICE)).thenReturn(mockStatement);
        when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSetForKey);
        Device result = deviceDao.create(device);
        assertAll("result",
                () -> assertEquals(result.getDevId(), device.getDevId()),
                () -> assertEquals(result.getDeviceType(), device.getDeviceType()),
                () -> assertEquals(result.getModel(), device.getModel()),
                () -> assertEquals(result.getName(), device.getName()));
    }

    /**
     * Testing method device when we don't get id of created entity.
     *
     * @throws SQLException exception.
     */
    @Test
    public void addDeviceAndDontGetGeneratedIdReturnTrue() throws SQLException, DaoException {
        when(mockConnection.prepareStatement(CREATE_DEVICE)).thenReturn(mockStatement);
        when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSetForKey);
        when(mockResultSetForKey.next()).thenReturn(false);
        Device result = deviceDao.create(devicetNoId);
        Assertions.assertTrue(result.getDevId() == null);
    }

    /**
     * Testing method addDevices when we get id of created entity.
     *
     * @throws SQLException exception.
     */
    @Test
    public void addDevicesAndGetGeneratedIdReturnTrue() throws SQLException, DaoException {
        when(mockConnection.prepareStatement(CREATE_DEVICE)).thenReturn(mockStatement);
        when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSetForKey);
        when(mockResultSetForKey.next()).thenReturn(true);
        Device result = deviceDao.create(devicetNoId);
        Assertions.assertTrue(result.getDevId() == device.getDevId());
    }

    /**
     * Testing method deleteDevice when result true.
     *
     * @throws SQLException
     */
    @Test
    public void deleteDevicetSuccessfulReturnTrue() throws SQLException, DaoException {
        when(mockConnection.prepareStatement(DELETE_DEVICE)).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
        Integer result = deviceDao.delete(device);
        Assertions.assertTrue(result == 1);
    }

    /**
     * Testing method deleteDevice failed return true.
     *
     * @throws SQLException
     */
    @Test
    public void deleteDeviceFailedReturnTrue() throws SQLException, DaoException {
        when(mockConnection.prepareStatement(DELETE_DEVICE)).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
        Device deviceNull = null;
        Integer result = deviceDao.delete(deviceNull);
        Assertions.assertTrue(result == 0);
    }

    /**
     * Testing method updateDevice when result true.
     *
     * @throws SQLException
     */
    @Test
    public void updateDeviceSuccessfulReturnTrue() throws SQLException, DaoException {
        when(mockConnection.prepareStatement(UPDATE_DEVICE)).thenReturn(mockStatement);
        Device result = deviceDao.update(device);
        assertAll("result",
                () -> assertEquals(result.getDevId(), device.getDevId()),
                () -> assertEquals(result.getDeviceType(), device.getDeviceType()),
                () -> assertEquals(result.getModel(), device.getModel()),
                () -> assertEquals(result.getName(), device.getName()));
    }

    /**
     * Testing method getAllDevices when result true.
     *
     * @throws SQLException
     */
    @Test
    public void getAllDeviceExecutedReturnTrue() throws SQLException, DaoException {
        boolean result = false;
        when(mockConnection.prepareStatement(GET_ALL_DEVICES)).thenReturn(mockStatement);
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
    public void deviceGetByIdReturnEmptyResultSetThenEmptyOptionalShouldBeReturned() throws DaoException, SQLException {
        when(mockConnection.prepareStatement(GET_DEVICE_BY_ID)).thenReturn(mockStatement);
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
    public void deviceGetByIdReturnNotEmptyResultSetThenDeviceOptionalShouldBeReturned() throws
            DaoException, SQLException {
        when(mockConnection.prepareStatement(GET_DEVICE_BY_ID)).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);
        Optional<Device> deviceOptional = deviceDao.getById(device.getDevId());
        Device deviceFromFromOptional = deviceOptional.get();
        assertAll("deviceFromFromOptional",
                () -> assertEquals(deviceFromFromOptional.getDevId(), device.getDevId()),
                () -> assertEquals(deviceFromFromOptional.getDeviceType(), device.getDeviceType()),
                () -> assertEquals(deviceFromFromOptional.getModel(), device.getModel()),
                () -> assertEquals(deviceFromFromOptional.getName(), device.getName()));
    }

    /**
     * Testing method deleteDevice catch SQLException throw DaoException.
     *
     * @throws SQLException
     */
    @Test
    public void deleteDeviceThrowDaoException() throws SQLException {
        when(mockConnection.prepareStatement(DELETE_DEVICE)).thenThrow(new SQLException());
        assertThrows(DaoException.class, () -> {
            deviceDao.delete(device);
        });
    }

    /**
     * Testing method addDevice catch SQLException throw DaoException.
     *
     * @throws SQLException
     */
    @Test
    public void addDeviceThrowDaoException() throws SQLException {
        when(mockConnection.prepareStatement(CREATE_DEVICE)).thenThrow(new SQLException());
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
        when(mockConnection.prepareStatement(UPDATE_DEVICE)).thenThrow(new SQLException());
        assertThrows(DaoException.class, () -> {
            deviceDao.update(device);
        });
    }

    /**
     * Testing method getAllDevices catch SQLException throw DaoException.
     *
     * @throws SQLException
     */
    @Test
    public void getAllDeviceThrowDaoException() throws SQLException {
        when(mockConnection.prepareStatement(GET_ALL_DEVICES)).thenThrow(new SQLException());
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
        when(mockConnection.prepareStatement(GET_DEVICE_BY_ID)).thenThrow(new SQLException());
        assertThrows(DaoException.class, () -> {
            deviceDao.getById(device.getDevId());
        });
    }

    private void createMocksFromGetByIdMethod() throws SQLException {
        when(mockConnection.prepareStatement(GET_DEVICE_BY_ID)).thenReturn(mockStatement);
        when(mockResultSet.next()).thenReturn(true);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.getLong("DEV_ID")).thenReturn(device.getDevId());
        when(mockResultSet.getString("MODEL")).thenReturn(device.getModel());
        when(mockResultSet.getString("NAME")).thenReturn(device.getName());
        when(mockResultSet.getString("DEVICE_TYPE")).thenReturn(device.getDeviceType());
    }
}
