package com.ra.advertisement.dao;

import com.ra.advertisement.connection.ConnectionFactory;
import com.ra.advertisement.dao.exceptions.DaoException;
import com.ra.advertisement.model.entities.Device;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeviceAdvertisementDaoImplTest {
    private static final Device device = new Device(1L, "Nokia", "25-10", "Mobile Phone");
    private static ConnectionFactory connectionFactory;
    private AdvertisementDao<Device> deviceDao;

    @BeforeEach
    void setUp() throws Exception {
        connectionFactory = ConnectionFactory.getInstance();
        deviceDao = new DeviceAdvertisementDaoImpl(connectionFactory);
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\device_db.sql"));
    }

    /**
     * testing successful result of create method which saves info regarding Device into DB
     *
     * @throws Exception
     */
    @Test
    void insertValidDataReturnTrue() throws Exception {
        Integer result = deviceDao.create(device);
        assertEquals(Integer.valueOf(1), result);
    }

    /**
     * testing if throws DaoException of create method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void insertWhenTableDroppedThrowDaoException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_devices.sql"));
        assertThrows(DaoException.class, () -> {
            deviceDao.create(device);
        });
    }

    /**
     * testing if throws Exception of create method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void insertWhenTableDroppedThrowException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_devices.sql"));
        assertThrows(Exception.class, () -> {
            deviceDao.create(device);
        });
    }

    /**
     * testing successful result of getById method which gets info regarding Device from DB
     *
     * @throws Exception
     */
    @Test
    void getObjectByIdWhenExecutedReturnTrue() throws Exception {
        deviceDao.create(device);
        Device actual = deviceDao.getById(device.getDevId()).orElse(null);
        assertAll("actual",
                () -> assertEquals(device.getDevId(), actual.getDevId()),
                () -> assertEquals(device.getModel(), actual.getModel()),
                () -> assertEquals(device.getDeviceType(), actual.getDeviceType()),
                () -> assertEquals(device.getName(), actual.getName())
        );
    }

    /**
     * testing result (null) of getById method which gets info regarding Device from DB when there no such id in DB
     *
     * @throws Exception
     */
    @Test
    void getObjectByIdWhenExecutedNullReturnTrue() throws Exception {
        Device actual = deviceDao.getById(device.getDevId()).orElse(null);
        assertEquals(null, actual);
    }

    /**
     * testing if throws DaoException of getById method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void getObjectByIdWhenTableDroppedThrowDaoException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_devices.sql"));
        assertThrows(DaoException.class, () -> {
            deviceDao.getById(device.getDevId());
        });
    }

    /**
     * testing if throws Exception of getById method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void getObjectByIdWhenTableDroppedThrowException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_devices.sql"));
        assertThrows(Exception.class, () -> {
            deviceDao.getById(device.getDevId());
        });
    }

    /**
     * testing successful result of delete method which delete info regarding Device from DB
     *
     * @throws Exception
     */
    @Test
    void deleteValidDataReturnTrue() throws Exception {
        deviceDao.create(device);
        Integer actual = deviceDao.delete(device.getDevId());
        assertEquals(Integer.valueOf(1), actual);
    }

    /**
     * testing if throws DaoException of delete method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void deleteObjectByIdWhenTableDroppedThrowDaoException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_devices.sql"));
        assertThrows(DaoException.class, () -> {
            deviceDao.getById(device.getDevId());
        });
    }

    /**
     * testing if throws Exception of delete method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void deleteObjectByIdWhenTableDroppedThrowException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_devices.sql"));
        assertThrows(Exception.class, () -> {
            deviceDao.delete(device.getDevId());
        });
    }

    /**
     * testing successful result of getAll method which gets info regarding all Devices from DB
     *
     * @throws Exception
     */
    @Test
    void getAllObjectWhenExecutedAndListIsNotEmptyReturnTrue() throws Exception {
        deviceDao.create(device);
        boolean actual = deviceDao.getAll().isEmpty();
        assertEquals(Boolean.valueOf(false), actual);
    }

    /**
     * testing successful result of getAll method which gets info regarding all Devices from DB
     *
     * @throws Exception
     */
    @Test
    void getAllObjectWhenExecutedAndGetListWithSizeTwoReturnTrue() throws Exception {
        deviceDao.create(device);
        deviceDao.create(device);
        Integer actual = deviceDao.getAll().size();
        assertEquals(Integer.valueOf(2), actual);
    }

    /**
     * testing if throws DaoException of getAll method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void getAllObjectWhenTableDroppedThrowDaoException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_devices.sql"));
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
    void getAllObjectWhenTableDroppedThrowException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_devices.sql"));
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
    void updateDataWhenExecutedReturnTrue() throws Exception {
        deviceDao.create(device);
        Device deviceUpdate = new Device(1L, "Nokia Update", "25-10U pdate", "Mobile Phone Update");
        Integer actual = deviceDao.update(deviceUpdate);
        assertEquals(Integer.valueOf(1), actual);
    }

    /**
     * testing successful result of update method which updates info regarding Devices in DB
     *
     * @throws Exception
     */
    @Test
    void updateDataWhenExecutedAndGetResultReturnTrue() throws Exception {
        deviceDao.create(device);
        Device deviceUpdate = new Device(1L, "Nokia Update", "25-10 Update", "Mobile Phone Update");
        deviceDao.update(deviceUpdate);
        Device actual = deviceDao.getById(deviceUpdate.getDevId()).orElse(null);
        assertAll("actual",
                () -> assertEquals(deviceUpdate.getDevId(), actual.getDevId()),
                () -> assertEquals(deviceUpdate.getModel(), actual.getModel()),
                () -> assertEquals(deviceUpdate.getDeviceType(), actual.getDeviceType()),
                () -> assertEquals(deviceUpdate.getName(), actual.getName())
        );
    }

    /**
     * testing if throws DaoException of update method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void updateObjectWhenTableDroppedThrowDaoException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        Device deviceUpdate = new Device(1L, "Nokia Update", "25-10 Update", "Mobile Phone Update");
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_devices.sql"));
        assertThrows(DaoException.class, () -> {
            deviceDao.update(deviceUpdate);
        });
    }

    /**
     * testing if throws Exception of update method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void updateObjectWhenTableDroppedThrowException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        Device deviceUpdate = new Device(1L, "Nokia Update", "25-10 Update", "Mobile Phone Update");
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_devices.sql"));
        assertThrows(Exception.class, () -> {
            deviceDao.update(deviceUpdate);
        });
    }

}
