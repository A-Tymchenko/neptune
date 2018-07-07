package com.ra.advertisement.dao;

import com.ra.advertisement.connection.ConnectionFactory;
import com.ra.advertisement.dao.exceptions.DaoException;
import com.ra.advertisement.model.entities.Provider;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProviderAdvertisementDaoImplTest {

    private static final Provider provider = new Provider(1L, "Coca Cola", "Lviv", "22-45-18", "Ukraine");
    private ConnectionFactory connectionFactory;
    AdvertisementDao<Provider> providerDao;

    @BeforeEach
    void setUp() throws Exception {
        connectionFactory = ConnectionFactory.getInstance();
        providerDao = new ProviderAdvertisementDaoImpl(connectionFactory);
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\provider_db.sql"));
    }

    @Test
    void insertValidDataReturnTrue() throws Exception {
        Integer result = providerDao.create(provider);
        assertEquals(Integer.valueOf(1), result);
    }

    @Test
    void insertWhenTableDroppedThrowDaoException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_provider.sql"));
        assertThrows(DaoException.class, () -> {
            providerDao.create(provider);
        });
    }

    @Test
    void insertWhenTableDroppedThrowException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_provider.sql"));
        assertThrows(Exception.class, () -> {
            providerDao.create(provider);
        });
    }

    @Test
    void getObjectByIdWhenExecutedReturnTrue() throws Exception {
        providerDao.create(provider);
        Provider actual = providerDao.getById(provider.getProvId()).orElse(null);
        assertAll("actual",
                () -> assertEquals(provider.getProvId(), actual.getProvId()),
                () -> assertEquals(provider.getAddress(), actual.getAddress()),
                () -> assertEquals(provider.getCountry(), actual.getCountry()),
                () -> assertEquals(provider.getName(), actual.getName()),
                () -> assertEquals(provider.getTelephone(), actual.getTelephone())
        );
    }

    @Test
    void getObjectByIdWhenExecutedNullReturnTrue() throws Exception {
        Provider actual = providerDao.getById(provider.getProvId()).orElse(null);
        assertEquals(null, actual);
    }

    @Test
    void getObjectByIdWhenTableDroppedThrowDaoException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_provider.sql"));
        assertThrows(DaoException.class, () -> {
            providerDao.getById(provider.getProvId());
        });
    }

    @Test
    void getObjectByIdWhenTableDroppedThrowException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_provider.sql"));
        assertThrows(Exception.class, () -> {
            providerDao.getById(provider.getProvId());
        });
    }

    @Test
    void deleteValidDataReturnTrue() throws Exception {
        providerDao.create(provider);
        Integer actual = providerDao.delete(provider.getProvId());
        assertEquals(Integer.valueOf(1), actual);
    }

    @Test
    void deleteInValidDataReturnTrue() throws Exception {
        providerDao.create(provider);
        Integer actual = providerDao.delete(2L);
        assertEquals(Integer.valueOf(0), actual);
    }

    @Test
    void deleteObjectByIdWhenTableDroppedThrowDaoException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_provider.sql"));
        assertThrows(DaoException.class, () -> {
            providerDao.delete(provider.getProvId());
        });
    }

    @Test
    void deleteObjectByIdWhenTableDroppedThrowException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_provider.sql"));
        assertThrows(Exception.class, () -> {
            providerDao.delete(provider.getProvId());
        });
    }

    @Test
    void getAllObjectWhenExecutedAndListIsNotEmptyReturnTrue() throws Exception {
        providerDao.create(provider);
        boolean actual = providerDao.getAll().isEmpty();
        assertEquals(Boolean.valueOf(false), actual);
    }

    @Test
    void getAllObjectWhenExecutedAndGetListWithSizeTwoReturnTrue() throws Exception {
        providerDao.create(provider);
        providerDao.create(provider);
        Integer actual = providerDao.getAll().size();
        assertEquals(Integer.valueOf(2), actual);
    }

    @Test
    void getAllObjectWhenTableDroppedThrowDaoException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_provider.sql"));
        assertThrows(DaoException.class, () -> {
            providerDao.getAll();
        });
    }

    @Test
    void getAllObjectWhenTableDroppedThrowException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_provider.sql"));
        assertThrows(Exception.class, () -> {
            providerDao.getAll();
        });
    }

    @Test
    void updateDataWhenExecutedReturnTrue() throws Exception {
        providerDao.create(provider);
        Provider providerUpdate = new Provider(1L, "Coca Cola Update", "LvivUpdate", "22-45-18Update", "UkraineUpdate");
        Integer actual = providerDao.update(providerUpdate);
        assertEquals(Integer.valueOf(1), actual);
    }

    @Test
    void updateInvalidDataWhenExecutedReturnTrue() throws Exception {
        providerDao.create(provider);
        Provider providerUpdate = new Provider(2L, "Coca Cola Update", "LvivUpdate", "22-45-18Update", "UkraineUpdate");
        Integer actual = providerDao.update(providerUpdate);
        assertEquals(Integer.valueOf(0), actual);
    }

    @Test
    void updateDataWhenExecutedAndGetResultReturnTrue() throws Exception {
        providerDao.create(provider);
        Provider providerUpdate = new Provider(1L, "Coca Cola Update", "LvivUpdate", "22-45-18Update", "UkraineUpdate");
        providerDao.update(providerUpdate);
        Provider actual = providerDao.getById(providerUpdate.getProvId()).orElse(null);
        assertAll("actual",
                () -> assertEquals(providerUpdate.getProvId(), actual.getProvId()),
                () -> assertEquals(providerUpdate.getAddress(), actual.getAddress()),
                () -> assertEquals(providerUpdate.getCountry(), actual.getCountry()),
                () -> assertEquals(providerUpdate.getName(), actual.getName()),
                () -> assertEquals(providerUpdate.getTelephone(), actual.getTelephone())
        );


    }

    @Test
    void updateObjectWhenTableDroppedThrowProviderDaoException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        Provider providerUpdate = new Provider(1L, "Coca Cola Update", "LvivUpdate", "22-45-18Update", "UkraineUpdate");
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_provider.sql"));
        assertThrows(DaoException.class, () -> {
            providerDao.update(providerUpdate);
        });

    }

    @Test
    void updateObjectWhenTableDroppedThrowExeption() throws Exception {
        Connection connection = connectionFactory.getConnection();
        Provider providerUpdate = new Provider(1L, "Coca Cola Update", "LvivUpdate", "22-45-18Update", "UkraineUpdate");
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_provider.sql"));
        assertThrows(Exception.class, () -> {
            providerDao.update(providerUpdate);
        });

    }
}