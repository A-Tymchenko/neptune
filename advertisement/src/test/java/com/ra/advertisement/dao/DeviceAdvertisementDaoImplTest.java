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

    @Test
    void insertValidDataReturnTrue() throws Exception {
        Integer result = deviceDao.create(device);
        assertEquals(Integer.valueOf(1), result);
    }

    @Test
    void insertWhenTableDroppedThrowDaoException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_devices.sql"));
        assertThrows(DaoException.class, () -> {
            deviceDao.create(device);
        });
    }

    @Test
    void insertWhenTableDroppedThrowException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_devices.sql"));
        assertThrows(Exception.class, () -> {
            deviceDao.create(device);
        });
    }

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

    @Test
    void getObjectByIdWhenExecutedNullReturnTrue() throws Exception {
        Device actual = deviceDao.getById(device.getDevId()).orElse(null);
        assertEquals(null, actual);
    }

    @Test
    void getObjectByIdWhenTableDroppedThrowDaoException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_devices.sql"));
        assertThrows(DaoException.class, () -> {
            deviceDao.getById(device.getDevId());
        });
    }

    @Test
    void getObjectByIdWhenTableDroppedThrowException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_devices.sql"));
        assertThrows(Exception.class, () -> {
            deviceDao.getById(device.getDevId());
        });
    }


    @Test
    void deleteValidDataReturnTrue() throws Exception {
        deviceDao.create(device);
        Integer actual = deviceDao.delete(device.getDevId());
        assertEquals(Integer.valueOf(1), actual);
    }

    @Test
    void deleteObjectByIdWhenTableDroppedThrowDaoException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_devices.sql"));
        assertThrows(DaoException.class, () -> {
            deviceDao.getById(device.getDevId());
        });
    }

    @Test
    void deleteObjectByIdWhenTableDroppedThrowException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_devices.sql"));
        assertThrows(Exception.class, () -> {
            deviceDao.delete(device.getDevId());
        });
    }

    @Test
    void getAllObjectWhenExecutedAndListIsNotEmptyReturnTrue() throws Exception {
        deviceDao.create(device);
        boolean actual = deviceDao.getAll().isEmpty();
        assertEquals(Boolean.valueOf(false), actual);
    }

    @Test
    void getAllObjectWhenExecutedAndGetListWithSizeTwoReturnTrue() throws Exception {
        deviceDao.create(device);
        deviceDao.create(device);
        Integer actual = deviceDao.getAll().size();
        assertEquals(Integer.valueOf(2), actual);
    }

    @Test
    void getAllObjectWhenTableDroppedThrowDaoException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_devices.sql"));
        assertThrows(DaoException.class, () -> {
            deviceDao.getAll();
        });
    }

    @Test
    void getAllObjectWhenTableDroppedThrowException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_devices.sql"));
        assertThrows(Exception.class, () -> {
            deviceDao.getAll();
        });
    }

    @Test
    void updateDataWhenExecutedReturnTrue() throws Exception {
        deviceDao.create(device);
        Device deviceUpdate = new Device(1L, "Nokia Update", "25-10U pdate", "Mobile Phone Update");
        Integer actual = deviceDao.update(deviceUpdate);
        assertEquals(Integer.valueOf(1), actual);
    }

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

    @Test
    void updateObjectWhenTableDroppedThrowDaoException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        Device deviceUpdate = new Device(1L, "Nokia Update", "25-10 Update", "Mobile Phone Update");
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_devices.sql"));
        assertThrows(DaoException.class, () -> {
            deviceDao.update(deviceUpdate);
        });
    }

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
