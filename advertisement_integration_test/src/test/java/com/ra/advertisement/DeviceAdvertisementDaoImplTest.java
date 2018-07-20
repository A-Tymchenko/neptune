package com.ra.advertisement;

import com.ra.advertisement.connection.ConnectionFactory;
import com.ra.advertisement.dao.AdvertisementDao;
import com.ra.advertisement.dao.DeviceAdvertisementDaoImpl;
import com.ra.advertisement.dao.exceptions.DaoException;
import com.ra.advertisement.entity.Device;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.net.URL;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class DeviceAdvertisementDaoImplTest {
    private static final Device DEVICE = new Device("Nokia", "25-10", "Mobile Phone");
    private static final Device DEVICE_UPDATE = new Device(1L, "Nokia Update", "25-10 Update",
            "Mobile Phone Update");
    private static ConnectionFactory connectionFactory;
    private static URL urlToDeviceDB;
    private static URL urlToDropDeviceDB;
    private AdvertisementDao<Device> deviceDao;

    @BeforeEach
    void setUp() throws Exception {
        connectionFactory = ConnectionFactory.getInstance();
        deviceDao = new DeviceAdvertisementDaoImpl(connectionFactory);
        Connection connection = connectionFactory.getConnection();
        urlToDeviceDB = ClassLoader.getSystemResource("./device_db.sql");
        RunScript.execute(connection, new FileReader(urlToDeviceDB.getPath()));
    }

    /**
     * testing successful result of create method which save info regarding Device into DB
     *
     * @throws Exception
     */
    @Test
    void insertValidDataIntoDbAndGetItsFromThereWithGeneratedIddReturnTrue() throws Exception {
        Device deviceCreated = deviceDao.create(DEVICE);
        Device actual = deviceDao.getById(deviceCreated.getDevId()).orElse(null);
        assertAll("actual",
                () -> assertEquals(deviceCreated.getDevId(), actual.getDevId()),
                () -> assertEquals(deviceCreated.getModel(), actual.getModel()),
                () -> assertEquals(deviceCreated.getDeviceType(), actual.getDeviceType()),
                () -> assertEquals(deviceCreated.getName(), actual.getName())
        );
    }

    /**
     * testing if throws DaoException of create method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void insertValidDataAfterTheTableOfDevicesWasDroppedThrowDaoException() throws Exception {
        dropTableDevicesMethod();
        assertThrows(DaoException.class, () -> {
            deviceDao.create(DEVICE);
        });
    }

    /**
     * testing if throws Exception of create method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void insertValidDataAfterTheTableOfDevicesWasDroppedThrowException() throws Exception {
        dropTableDevicesMethod();
        assertThrows(Exception.class, () -> {
            deviceDao.create(DEVICE);
        });
    }

    /**
     * testing successful result of getById method which gets info regarding Device from DB
     *
     * @throws Exception
     */
    @Test
    void getObjectByIdExecutedReturnTrue() throws Exception {
        Device deviceCreated = deviceDao.create(DEVICE);
        Device actual = deviceDao.getById(deviceCreated.getDevId()).orElse(null);
        assertAll("actual",
                () -> assertEquals(Long.valueOf(1L), actual.getDevId()),
                () -> assertEquals(DEVICE.getModel(), actual.getModel()),
                () -> assertEquals(DEVICE.getDeviceType(), actual.getDeviceType()),
                () -> assertEquals(DEVICE.getName(), actual.getName())
        );
    }

    /**
     * testing if throws DaoException of getById method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void getObjectByIdAfterTheTableOfDevicesWasDroppedThrowDaoException() throws Exception {
        Device newDevice = deviceDao.create(DEVICE);
        dropTableDevicesMethod();
        assertThrows(DaoException.class, () -> {
            deviceDao.getById(newDevice.getDevId());
        });
    }

    /**
     * testing if throws Exception of getById method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void getObjectByIdAfterTheTableOfDevicesWasDroppedThrowException() throws Exception {
        Device newDevice = deviceDao.create(DEVICE);
        dropTableDevicesMethod();
        assertThrows(Exception.class, () -> {
            deviceDao.getById(newDevice.getDevId());
        });
    }

    /**
     * testing successful result of delete method which delete info regarding Device from DB
     *
     * @throws Exception
     */
    @Test
    void deleteValidDataExecutedReturnTrue() throws Exception {
        Device newDevice = deviceDao.create(DEVICE);
        Integer actual = deviceDao.delete(newDevice);
        assertEquals(Integer.valueOf(1), actual);
    }

    /**
     * testing if throws DaoException of delete method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void deleteObjectByIdAfterTheTableOfDevicesWasDroppedThrowDaoException() throws Exception {
        Device newDevice = deviceDao.create(DEVICE);
        dropTableDevicesMethod();
        assertThrows(DaoException.class, () -> {
            deviceDao.delete(newDevice);
        });
    }

    /**
     * testing if throws Exception of delete method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void deleteObjectByIdAfterTheTableOfDevicesWasDroppedThrowException() throws Exception {
        Device newDevice = deviceDao.create(DEVICE);
        dropTableDevicesMethod();
        assertThrows(Exception.class, () -> {
            deviceDao.delete(newDevice);
        });
    }

    /**
     * testing successful result of getAll method which gets info regarding all Devices from DB
     *
     * @throws Exception
     */
    @Test
    void getAllObjectExecutedAndTheListIsNotEmptyReturnTrue() throws Exception {
        deviceDao.create(DEVICE);
        boolean actual = deviceDao.getAll().isEmpty();
        assertEquals(Boolean.valueOf(false), actual);
    }

    /**
     * testing successful result of getAll method which gets info regarding all Devices from DB
     *
     * @throws Exception
     */
    @Test
    void getAllObjectExecutedTwiceAndTheListsSizeIsTwoReturnTrue() throws Exception {
        deviceDao.create(DEVICE);
        deviceDao.create(DEVICE);
        Integer actual = deviceDao.getAll().size();
        assertEquals(Integer.valueOf(2), actual);
    }

    /**
     * testing if throws DaoException of getAll method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void getAllObjectAfterTheTableOfDevicesWasDroppedThrowDaoException() throws Exception {
        deviceDao.create(DEVICE);
        dropTableDevicesMethod();
        assertThrows(DaoException.class, () -> {
            deviceDao.getAll();
        });
    }

    /**
     * testing if throws Exception of getAll method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void getAllObjectAfterTheTableOfDevicesWasDroppedThrowException() throws Exception {
        deviceDao.create(DEVICE);
        dropTableDevicesMethod();
        assertThrows(Exception.class, () -> {
            deviceDao.getAll();
        });
    }

    /**
     * testing successful result of update method which updates info regarding Devices in DB
     *
     * @throws Exception
     */
    @Test
    void updateDataExecutedAndAllFieldsOfTheDeviceRecievedAndCheckedReturnTrue() throws Exception {
        Device newDevice = deviceDao.create(DEVICE);
        deviceDao.update(DEVICE_UPDATE);
        Device actual = deviceDao.getById(DEVICE_UPDATE.getDevId()).orElse(null);
        assertAll("actual",
                () -> assertEquals(DEVICE_UPDATE.getDevId(), actual.getDevId()),
                () -> assertEquals(DEVICE_UPDATE.getModel(), actual.getModel()),
                () -> assertEquals(DEVICE_UPDATE.getDeviceType(), actual.getDeviceType()),
                () -> assertEquals(DEVICE_UPDATE.getName(), actual.getName())
        );
    }

    /**
     * testing if throws DaoException of update method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void updateObjectAfterTheTableOfDevicesWasDroppedThrowDaoException() throws Exception {
        deviceDao.create(DEVICE);
        dropTableDevicesMethod();
        assertThrows(DaoException.class, () -> {
            deviceDao.update(DEVICE_UPDATE);
        });
    }

    /**
     * testing if throws Exception of update method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void updateObjectAfterTheTableOfDevicesWasDroppedThrowException() throws Exception {
        deviceDao.create(DEVICE);
        dropTableDevicesMethod();
        assertThrows(Exception.class, () -> {
            deviceDao.update(DEVICE_UPDATE);
        });
    }

    /**
     * method drop table Devices
     *
     * @throws Exception
     */
    static void dropTableDevicesMethod() throws Exception {
        Connection connection = connectionFactory.getConnection();
        urlToDropDeviceDB = ClassLoader.getSystemResource("./drop_table_devices.sql");
        RunScript.execute(connection, new FileReader(urlToDropDeviceDB.getPath()));
    }
}
