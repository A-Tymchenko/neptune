package com.ra.advertisement.service;

import com.ra.advertisement.dao.DeviceAdvertisementDaoImpl;
import com.ra.advertisement.dto.DeviceDto;
import com.ra.advertisement.entity.Device;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DeviceAdvertisementServiceImplTest {
    private static DeviceAdvertisementServiceImpl deviceService;
    private static DeviceAdvertisementDaoImpl mockDeviceDao;
    private static BeanValidator beanValidator;
    private static Validator validator;
    private DeviceDto deviceDto;
    private DeviceDto deviceDtoCorrect;
    private Device device;
    private List<Device> deviceList;
    private static JdbcTemplate mockJdbcTemplate;

    @BeforeAll
    public static void init() {
        beanValidator = new BeanValidator();
        mockJdbcTemplate = mock(JdbcTemplate.class);
        mockDeviceDao = new DeviceAdvertisementDaoImpl(mockJdbcTemplate);
        deviceService = new DeviceAdvertisementServiceImpl(mockDeviceDao, beanValidator);
        mockDeviceDao = mock(DeviceAdvertisementDaoImpl.class);
        validator = beanValidator.getValidator();
    }

    @BeforeEach
    public void reInitAdvertisementDao() {
        deviceDto = new DeviceDto("Nokia", "25-10", "");
        deviceDtoCorrect = new DeviceDto("Nokia", "25-10", "Mobile Phone");
        device = new Device(1L, "Nokia", "25-10", "Mobile Phone");
        deviceList = new ArrayList<>();
        deviceList.add(device);
    }

    /**
     * Method check validation of the dto Object when  validation was not passed.
     *
     * @return entity
     */
    @Test
    public void saveEntityServiceMethodExecutedWithConstraintViolationReturnTrue() {
        List<String> answers = new ArrayList<>();
        final Set<ConstraintViolation<DeviceDto>> violations = validator.validate(deviceDto);
        answers = deviceService.saveEntityService(deviceDto);
        assertTrue(!violations.isEmpty());
        assertTrue(answers.size() == 2);
    }

    /**
     * Method check validation of the dto Object when passed
     *
     * @return entity
     */
    @Test
    public void saveEntityServiceMethodExecutedWithNoConstraintViolationReturnTrue() {
        List<String> answers = new ArrayList<>();
        final Set<ConstraintViolation<DeviceDto>> violations = validator.validate(deviceDtoCorrect);
        answers = deviceService.saveEntityService(deviceDtoCorrect);
        assertTrue(violations.isEmpty());
        assertTrue(answers.get(0).equals("Object has been saved successfully"));
    }

    /**
     * Testing method which converts dto object into Entity.
     */
    @Test
    public void convertDtoIntoEntityReturnTrue() {
        Device deviceCreated = deviceService.devCreator(deviceDto);
        assertAll("deviceCreated",
                () -> assertEquals(deviceCreated.getName(), deviceDto.getName()),
                () -> assertEquals(deviceCreated.getModel(), deviceDto.getModel()),
                () -> assertEquals(deviceCreated.getDeviceType(), deviceDto.getDeviceType()));
    }

    /**
     * Testing method getAllService which provides List of Entities.
     */
    @Test
    public void getAllServiceExecutedSuccessfulReturnTrue() {
        when(mockDeviceDao.getAll()).thenReturn(deviceList);
        List listFromQueryForList = createListOfMap();
        when(mockJdbcTemplate.queryForList(anyString())).thenReturn(listFromQueryForList);
        List<DeviceDto> listDto = deviceService.getAllEntityService();
        assertTrue(!listDto.isEmpty());
    }

    /**
     * Testing method which convertListOfEntity into List of Dto Objects.
     */
    @Test
    public void mapEntityListIntoListOfDtoReturnTrue() {
        List<Device> deviceList = new ArrayList<>();
        deviceList.add(device);
        List<DeviceDto> deviceDtoList = deviceService.mapListEntityIntoDto(deviceList);
        assertTrue(!deviceDtoList.isEmpty());
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
