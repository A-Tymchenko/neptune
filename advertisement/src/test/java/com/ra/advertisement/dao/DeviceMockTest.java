package com.ra.advertisement.dao;

import com.ra.advertisement.entity.Device;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DeviceMockTest {
    private static JdbcTemplate mockjdbcTemplate;
    private static DeviceProjectDaoImpl deviceDao;
    private static final String GET_DEVICE_BY_ID = "SELECT * FROM DEVICES WHERE DEV_ID=?";
    private Device device;
    private Device deviceNoId;
    private Device deviceUpdated;
    private KeyHolder mockkeyHolder = new GeneratedKeyHolder();
    private PreparedStatement mockStatement;

    @BeforeAll
    public static void init() {
        mockjdbcTemplate = mock(JdbcTemplate.class);
        deviceDao = new DeviceProjectDaoImpl(mockjdbcTemplate);
    }

    @BeforeEach
    public void reInitAdvertisementDao() {
        mockkeyHolder = mock(KeyHolder.class);
        mockStatement = mock(PreparedStatement.class);
        device = new Device(1L, "Nokia", "25-10", "Mobile Phone");
        deviceNoId = new Device("Nokia", "25-10", "Mobile Phone");
        deviceUpdated = new Device(1L, "Nokia Update", "25-10 Update",
                "Mobile Phone Update");
    }

    /**
     * Testing method addDevice when result true.
     */
    @Test
    public void addDevicetExecuteSuccessfuldReturnTrue() {
        when(mockjdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class))).thenReturn(1);
        when(mockkeyHolder.getKey()).thenReturn(1L);
        Device deviceCreated = deviceDao.create(deviceNoId);
        deviceNoId.setDevId((Long) mockkeyHolder.getKey());
        assertAll("deviceCreated",
                () -> assertEquals(deviceCreated.getDevId(), device.getDevId()),
                () -> assertEquals(deviceCreated.getName(), device.getName()),
                () -> assertEquals(deviceCreated.getModel(), device.getModel()),
                () -> assertEquals(deviceCreated.getDeviceType(), device.getDeviceType()));
    }

    /**
     * Testing method addADevice when we don't get id of created entity.
     */
    @Test
    public void addDeviceAndDontGetGeneratedIdReturnTrue() {
        when(mockjdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class))).thenReturn(1);
        when(mockkeyHolder.getKey()).thenReturn(null);
        deviceDao.create(deviceNoId);
        assertTrue(mockkeyHolder.getKey()==null);
    }

    /**
     * Testing method deleteDevice when result true.
     */
    @Test
    public void deleteDeviceSuccessfulReturnTrue() {
        final String DELETE_DEVICE = "DELETE FROM DEVICES WHERE DEV_ID=?";
        when(mockjdbcTemplate.update(DELETE_DEVICE, device.getDevId())).thenReturn(1);
        Integer result = deviceDao.delete(device);
        assertTrue(result == 1);
    }

    /**
     * Testing method updateDevice when result true.
     */
    @Test
    public void updateDeviceSuccessfulReturnTrue() {
        int resultFromDB = 0;
        final String UPDATE_DEVICE = "update DEVICES set NAME = ?, MODEL= ?, DEVICE_TYPE = ? where DEV_ID = ?";
        when(mockjdbcTemplate.update(eq(UPDATE_DEVICE), any(PreparedStatement.class))).thenReturn(resultFromDB=1);
        doAnswer(invocation -> {
            ((PreparedStatementSetter) invocation.getArguments()[1]).setValues(mockStatement);
            return null;
        }).when(mockjdbcTemplate).update(eq(UPDATE_DEVICE), any(PreparedStatementSetter.class));
        Device updated = deviceDao.update(deviceUpdated);
        assertEquals(1, resultFromDB);
        assertAll("updeted",
                () -> assertEquals(updated.getDevId(), deviceUpdated.getDevId()),
                () -> assertEquals(updated.getName(), deviceUpdated.getName()),
                () -> assertEquals(updated.getModel(), deviceUpdated.getModel()),
                () -> assertEquals(updated.getDeviceType(), deviceUpdated.getDeviceType()));
    }

    /**
     * Testing method getAllDevices when result true.
     */
    @Test
    public void getAllDevicesExecutedReturnTrue() {
        final String GET_ALL_DEVICES = "SELECT * FROM DEVICES";
        List listFromQueryForList = createListOfMap();
        when(mockjdbcTemplate.queryForList(eq(GET_ALL_DEVICES))).thenReturn(listFromQueryForList);
        List<Device> result = deviceDao.getAll();
        assertTrue(!result.isEmpty());
    }

    /**
     * Testing method getDeviceById when Device was provided result true.
     */
    @Test
    public void deviceGetByIdReturnDeviceReturnTrue() {
        when(mockjdbcTemplate.queryForObject(eq(GET_DEVICE_BY_ID), any(RowMapper.class), any(Long.class)))
                .thenReturn(device);
        Device result = deviceDao.getById(device.getDevId());
        assertAll("result",
                () -> assertEquals(result.getDevId(), device.getDevId()),
                () -> assertEquals(result.getName(), device.getName()),
                () -> assertEquals(result.getModel(), device.getModel()),
                () -> assertEquals(result.getDeviceType(), device.getDeviceType()));
    }

    /**
     * Testing method mapListFromQueryForList.
     */
    @Test
    public void mapListFromQueryForListReturnTrue() {
        List<Map<String, Object>> listToMapFrom = createListOfMap();
        List<Device> result = deviceDao.mapListFromQueryForList(listToMapFrom);
        Device deviceResult = result.get(0);
        assertTrue(!result.isEmpty());
        assertAll("deviceResult",
                () -> assertEquals(deviceResult.getDevId(), device.getDevId()),
                () -> assertEquals(deviceResult.getName(), device.getName()),
                () -> assertEquals(deviceResult.getModel(), device.getModel()),
                () -> assertEquals(deviceResult.getDeviceType(), device.getDeviceType()));
    }

    /**
     * this method create List of MapStringObject
     *
     * @return list
     */
    private List createListOfMap() {
        List<Map<String, Object>> listToMapFrom = new ArrayList<>();
        Map<String, Object> devId = new HashMap<>();
        devId.put("DEV_ID", device.getDevId());
        devId.put("NAME", device.getName());
        devId.put("MODEL", device.getModel());
        devId.put("DEVICE_TYPE", device.getDeviceType());
        listToMapFrom.add(devId);
        return listToMapFrom;
    }
}