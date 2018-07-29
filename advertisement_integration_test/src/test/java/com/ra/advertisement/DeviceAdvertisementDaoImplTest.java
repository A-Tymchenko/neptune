package com.ra.advertisement;

import com.ra.advertisement.config.AdvertisementConfiguration;
import com.ra.advertisement.dao.AdvertisementDao;
import com.ra.advertisement.entity.Device;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

class DeviceAdvertisementDaoImplTest {
    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AdvertisementConfiguration.class);
    private AdvertisementDao<Device> deviceDao = (AdvertisementDao<Device>) context.getBean("deviceDao");
    private static final Device DEVICE = new Device("Nokia", "25-10", "Mobile Phone");
    private static final Device DEVICE_UPDATE = new Device(1L, "Nokia Update", "25-10 Update",
            "Mobile Phone Update");

    @BeforeEach
    public void setUp() {
        ResourceDatabasePopulator tables = new ResourceDatabasePopulator();
        tables.addScript(new ClassPathResource("/advertisement_db.sql"));
        DatabasePopulatorUtils.execute(tables, (DataSource) context.getBean("simpleDataSource"));
    }

    /**
     * testing successful result of create method which save info regarding Device into DB
     */
    @Test
    void insertValidDataIntoDbAndGetItsFromThereWithGeneratedIddReturnTrue() {
        deviceDao.create(DEVICE);
        Assertions.assertTrue(DEVICE.getDevId() != null);
    }

    /**
     * testing successful result of getById method which gets info regarding Device from DB
     */
    @Test
    void getObjectByIdExecutedReturnTrue() {
        Device deviceCreated = deviceDao.create(DEVICE);
        Device actual = deviceDao.getById(deviceCreated.getDevId());
        Assertions.assertAll("actual",
                () -> Assertions.assertEquals(deviceCreated.getDevId(), actual.getDevId()),
                () -> Assertions.assertEquals(deviceCreated.getName(), actual.getName()),
                () -> Assertions.assertEquals(deviceCreated.getModel(), actual.getModel()),
                () -> Assertions.assertEquals(deviceCreated.getDeviceType(), actual.getDeviceType())
        );
    }

    /**
     * testing successful result of delete method which delete info regarding Device from DB
     */
    @Test
    void deleteValidDataExecutedReturnTrue() {
        Device deviceCreated = deviceDao.create(DEVICE);
        Integer actual = deviceDao.delete(deviceCreated);
        Assertions.assertEquals(Integer.valueOf(1), actual);
    }

    /**
     * testing successful result of getAll method which gets info regarding all Devices from DB
     */
    @Test
    void getAllObjectExecutedAndListIsNotEmptyReturnTrue() {
        deviceDao.create(DEVICE);
        boolean actual = deviceDao.getAll().isEmpty();
        Assertions.assertEquals(Boolean.valueOf(false), actual);
    }

    /**
     * testing successful result of update method which updates info regarding Device in DB
     */
    @Test
    void updateDataExecutedAndAllFieldsOfDeviceRecievedAndCheckedReturnTrue() {
        deviceDao.create(DEVICE);
        Device deviceUpdated = deviceDao.update(DEVICE_UPDATE);
        Device actual = deviceDao.getById(deviceUpdated.getDevId());
        Assertions.assertAll("actual",
                () -> Assertions.assertEquals(deviceUpdated.getDevId(), actual.getDevId()),
                () -> Assertions.assertEquals(deviceUpdated.getName(), actual.getName()),
                () -> Assertions.assertEquals(deviceUpdated.getModel(), actual.getModel()),
                () -> Assertions.assertEquals(deviceUpdated.getDeviceType(), actual.getDeviceType())
        );
    }
}