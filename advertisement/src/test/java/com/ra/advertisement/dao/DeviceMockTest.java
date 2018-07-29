package com.ra.advertisement.dao;

import com.ra.advertisement.config.AdvertisementConfiguration;
import com.ra.advertisement.entity.Device;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.ContextConfiguration;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContextConfiguration(classes = {AdvertisementConfiguration.class})
public class DeviceMockTest {
    private static JdbcTemplate mockjdbcTemplate;
    private static DeviceAdvertisementDaoImpl deviceDao;
    private static final String GET_DEVICE_BY_ID = "SELECT * FROM DEVICES WHERE DEV_ID=?";
    private Device device;
    private Device deviceNoId;
    private Device deviceUpdated;
    private KeyHolder mockkeyHolder = new GeneratedKeyHolder();
    private PreparedStatement mockStatement;

    @BeforeAll
    public static void init() {
        mockjdbcTemplate = Mockito.mock(JdbcTemplate.class);
        deviceDao = new DeviceAdvertisementDaoImpl(mockjdbcTemplate);
    }

    @BeforeEach
    public void reInitAdvertisementDao() {
        mockkeyHolder = Mockito.mock(KeyHolder.class);
        mockStatement = Mockito.mock(PreparedStatement.class);
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
        Mockito.when(mockjdbcTemplate.update(Mockito.any(PreparedStatementCreator.class), Mockito.any(KeyHolder.class))).thenReturn(1);
        Mockito.when(mockkeyHolder.getKey()).thenReturn(1L);
        Device deviceCreated = deviceDao.create(deviceNoId);
        deviceNoId.setDevId((Long) mockkeyHolder.getKey());
        Assertions.assertAll("deviceCreated",
                () -> Assertions.assertEquals(deviceCreated.getDevId(), device.getDevId()),
                () -> Assertions.assertEquals(deviceCreated.getName(), device.getName()),
                () -> Assertions.assertEquals(deviceCreated.getModel(), device.getModel()),
                () -> Assertions.assertEquals(deviceCreated.getDeviceType(), device.getDeviceType()));
    }

    /**
     * Testing method addADevice when we don't get id of created entity.
     */
    @Test
    public void addDeviceAndDontGetGeneratedIdReturnTrue() {
        Mockito.when(mockjdbcTemplate.update(Mockito.any(PreparedStatementCreator.class), Mockito.any(KeyHolder.class))).thenReturn(1);
        Mockito.when(mockkeyHolder.getKey()).thenReturn(null);
        deviceDao.create(deviceNoId);
        Assertions.assertTrue(mockkeyHolder.getKey()==null);
    }

    /**
     * Testing method deleteDevice when result true.
     */
    @Test
    public void deleteDeviceSuccessfulReturnTrue() {
        final String DELETE_DEVICE = "DELETE FROM DEVICES WHERE DEV_ID=?";
        Mockito.when(mockjdbcTemplate.update(DELETE_DEVICE, device.getDevId())).thenReturn(1);
        Integer result = deviceDao.delete(device);
        Assertions.assertTrue(result == 1);
    }

    /**
     * Testing method updateDevice when result true.
     */
    @Test
    public void updateDeviceSuccessfulReturnTrue() {
        int resultFromDB = 0;
        final String UPDATE_DEVICE = "update DEVICES set NAME = ?, MODEL= ?, DEVICE_TYPE = ? where DEV_ID = ?";
        Mockito.when(mockjdbcTemplate.update(Mockito.eq(UPDATE_DEVICE), Mockito.any(PreparedStatement.class))).thenReturn(resultFromDB=1);
        Mockito.doAnswer(invocation -> {
            ((PreparedStatementSetter) invocation.getArguments()[1]).setValues(mockStatement);
            return null;
        }).when(mockjdbcTemplate).update(Mockito.eq(UPDATE_DEVICE), Mockito.any(PreparedStatementSetter.class));
        Device updated = deviceDao.update(deviceUpdated);
        Assertions.assertEquals(1, resultFromDB);
        Assertions.assertAll("updeted",
                () -> Assertions.assertEquals(updated.getDevId(), deviceUpdated.getDevId()),
                () -> Assertions.assertEquals(updated.getName(), deviceUpdated.getName()),
                () -> Assertions.assertEquals(updated.getModel(), deviceUpdated.getModel()),
                () -> Assertions.assertEquals(updated.getDeviceType(), deviceUpdated.getDeviceType()));
    }

    /**
     * Testing method getAllDevices when result true.
     */
    @Test
    public void getAllDevicesExecutedReturnTrue() {
        final String GET_ALL_DEVICES = "SELECT * FROM DEVICES";
        List listFromQueryForList = createListOfMap();
        Mockito.when(mockjdbcTemplate.queryForList(Mockito.eq(GET_ALL_DEVICES))).thenReturn(listFromQueryForList);
        List<Device> result = deviceDao.getAll();
        Assertions.assertTrue(!result.isEmpty());
    }

    /**
     * Testing method getDeviceById when Device was provided result true.
     */
    @Test
    public void deviceGetByIdReturnDeviceReturnTrue() {
        Mockito.when(mockjdbcTemplate.queryForObject(Mockito.eq(GET_DEVICE_BY_ID), Mockito.any(RowMapper.class), Mockito.any(Long.class)))
                .thenReturn(device);
        Device result = deviceDao.getById(device.getDevId());
        Assertions.assertAll("result",
                () -> Assertions.assertEquals(result.getDevId(), device.getDevId()),
                () -> Assertions.assertEquals(result.getName(), device.getName()),
                () -> Assertions.assertEquals(result.getModel(), device.getModel()),
                () -> Assertions.assertEquals(result.getDeviceType(), device.getDeviceType()));
    }

    /**
     * Testing method mapListFromQueryForList.
     */
    @Test
    public void mapListFromQueryForListReturnTrue() {
        List<Map<String, Object>> listToMapFrom = createListOfMap();
        List<Device> result = deviceDao.mapListFromQueryForList(listToMapFrom);
        Device deviceResult = result.get(0);
        Assertions.assertTrue(!result.isEmpty());
        Assertions.assertAll("deviceResult",
                () -> Assertions.assertEquals(deviceResult.getDevId(), device.getDevId()),
                () -> Assertions.assertEquals(deviceResult.getName(), device.getName()),
                () -> Assertions.assertEquals(deviceResult.getModel(), device.getModel()),
                () -> Assertions.assertEquals(deviceResult.getDeviceType(), device.getDeviceType()));
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