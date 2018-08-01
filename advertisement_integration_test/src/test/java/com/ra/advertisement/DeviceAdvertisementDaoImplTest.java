package com.ra.advertisement;

import com.ra.advertisement.config.AdvertisementConfiguration;
import com.ra.advertisement.dao.DeviceAdvertisementDaoImpl;
import com.ra.advertisement.entity.Device;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AdvertisementConfiguration.class})
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/advertisement_db.sql")
class DeviceAdvertisementDaoImplTest {

    @Autowired
    private DeviceAdvertisementDaoImpl deviceDao;

    private static final Device DEVICE = new Device("Nokia", "25-10", "Mobile Phone");
    private static final Device DEVICE_UPDATE = new Device(1L, "Nokia Update", "25-10 Update",
            "Mobile Phone Update");

    /**
     * testing successful result of create method which save info regarding Device into DB
     */
    @Test
    void insertValidDataIntoDbAndGetItsFromThereWithGeneratedIddReturnTrue() {
        deviceDao.create(DEVICE);
        assertTrue(DEVICE.getDevId() != null);
    }

    /**
     * testing successful result of getById method which gets info regarding Device from DB
     */
    @Test
    void getObjectByIdExecutedReturnTrue() {
        Device deviceCreated = deviceDao.create(DEVICE);
        Device actual = deviceDao.getById(deviceCreated.getDevId());
        assertAll("actual",
                () -> assertEquals(deviceCreated.getDevId(), actual.getDevId()),
                () -> assertEquals(deviceCreated.getName(), actual.getName()),
                () -> assertEquals(deviceCreated.getModel(), actual.getModel()),
                () -> assertEquals(deviceCreated.getDeviceType(), actual.getDeviceType())
        );
    }

    /**
     * testing successful result of delete method which delete info regarding Device from DB
     */
    @Test
    void deleteValidDataExecutedReturnTrue() {
        Device deviceCreated = deviceDao.create(DEVICE);
        Integer actual = deviceDao.delete(deviceCreated);
        assertEquals(Integer.valueOf(1), actual);
    }

    /**
     * testing successful result of getAll method which gets info regarding all Devices from DB
     */
    @Test
    void getAllObjectExecutedAndListIsNotEmptyReturnTrue() {
        deviceDao.create(DEVICE);
        boolean actual = deviceDao.getAll().isEmpty();
        assertEquals(Boolean.valueOf(false), actual);
    }

    /**
     * testing successful result of update method which updates info regarding Device in DB
     */
    @Test
    void updateDataExecutedAndAllFieldsOfDeviceRecievedAndCheckedReturnTrue() {
        deviceDao.create(DEVICE);
        Device deviceUpdated = deviceDao.update(DEVICE_UPDATE);
        Device actual = deviceDao.getById(deviceUpdated.getDevId());
        assertAll("actual",
                () -> assertEquals(deviceUpdated.getDevId(), actual.getDevId()),
                () -> assertEquals(deviceUpdated.getName(), actual.getName()),
                () -> assertEquals(deviceUpdated.getModel(), actual.getModel()),
                () -> assertEquals(deviceUpdated.getDeviceType(), actual.getDeviceType())
        );
    }
}