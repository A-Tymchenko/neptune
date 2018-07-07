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

    /**
     * testing successful result of create method which saves info regarding Provider into DB
     *
     * @throws Exception
     */
    @Test
    void insertValidDataReturnTrue() throws Exception {
        Integer result = providerDao.create(provider);
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
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_provider.sql"));
        assertThrows(DaoException.class, () -> {
            providerDao.create(provider);
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
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_provider.sql"));
        assertThrows(Exception.class, () -> {
            providerDao.create(provider);
        });
    }

    /**
     * testing successful result of getById method which gets info regarding Provider from DB
     *
     * @throws Exception
     */
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

    /**
     * testing result (null) of getById method which gets info regarding Provider from DB when there no such id in DB
     *
     * @throws Exception
     */
    @Test
    void getObjectByIdWhenExecutedNullReturnTrue() throws Exception {
        Provider actual = providerDao.getById(provider.getProvId()).orElse(null);
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
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_provider.sql"));
        assertThrows(DaoException.class, () -> {
            providerDao.getById(provider.getProvId());
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
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_provider.sql"));
        assertThrows(Exception.class, () -> {
            providerDao.getById(provider.getProvId());
        });
    }

    /**
     * testing successful result of delete method which delete info regarding Provider from DB
     *
     * @throws Exception
     */
    @Test
    void deleteValidDataReturnTrue() throws Exception {
        providerDao.create(provider);
        Integer actual = providerDao.delete(provider.getProvId());
        assertEquals(Integer.valueOf(1), actual);
    }

    /**
     * testing unsuccessful result of delete method which delete info regarding Provider from DB
     *
     * @throws Exception
     */
    @Test
    void deleteInValidDataReturnTrue() throws Exception {
        providerDao.create(provider);
        Integer actual = providerDao.delete(2L);
        assertEquals(Integer.valueOf(0), actual);
    }

    /**
     * testing if throws DaoException of delete method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void deleteObjectByIdWhenTableDroppedThrowDaoException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_provider.sql"));
        assertThrows(DaoException.class, () -> {
            providerDao.delete(provider.getProvId());
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
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_provider.sql"));
        assertThrows(Exception.class, () -> {
            providerDao.delete(provider.getProvId());
        });
    }

    /**
     * testing successful result of getAll method which gets info regarding all Provider from DB
     *
     * @throws Exception
     */
    @Test
    void getAllObjectWhenExecutedAndListIsNotEmptyReturnTrue() throws Exception {
        providerDao.create(provider);
        boolean actual = providerDao.getAll().isEmpty();
        assertEquals(Boolean.valueOf(false), actual);
    }

    /**
     * testing successful result of getAll method which gets info regarding all Providers from DB
     *
     * @throws Exception
     */
    @Test
    void getAllObjectWhenExecutedAndGetListWithSizeTwoReturnTrue() throws Exception {
        providerDao.create(provider);
        providerDao.create(provider);
        Integer actual = providerDao.getAll().size();
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
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_provider.sql"));
        assertThrows(DaoException.class, () -> {
            providerDao.getAll();
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
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_provider.sql"));
        assertThrows(Exception.class, () -> {
            providerDao.getAll();
        });
    }

    /**
     * testing successful result of update method which updates info regarding Providers in DB
     *
     * @throws Exception
     */
    @Test
    void updateDataWhenExecutedReturnTrue() throws Exception {
        providerDao.create(provider);
        Provider providerUpdate = new Provider(1L, "Coca Cola Update", "LvivUpdate", "22-45-18Update", "UkraineUpdate");
        Integer actual = providerDao.update(providerUpdate);
        assertEquals(Integer.valueOf(1), actual);
    }

    /**
     * testing unsuccessful result of update method which updates info regarding Provider in DB
     *
     * @throws Exception
     */
    @Test
    void updateInvalidDataWhenExecutedReturnTrue() throws Exception {
        providerDao.create(provider);
        Provider providerUpdate = new Provider(2L, "Coca Cola Update", "LvivUpdate", "22-45-18Update", "UkraineUpdate");
        Integer actual = providerDao.update(providerUpdate);
        assertEquals(Integer.valueOf(0), actual);
    }

    /**
     * testing successful result of update method which updates info regarding Provider in DB
     *
     * @throws Exception
     */
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

    /**
     * testing if throws DaoException of update method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void updateObjectWhenTableDroppedThrowProviderDaoException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        Provider providerUpdate = new Provider(1L, "Coca Cola Update", "LvivUpdate", "22-45-18Update", "UkraineUpdate");
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_provider.sql"));
        assertThrows(DaoException.class, () -> {
            providerDao.update(providerUpdate);
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
        Provider providerUpdate = new Provider(1L, "Coca Cola Update", "LvivUpdate", "22-45-18Update", "UkraineUpdate");
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_provider.sql"));
        assertThrows(Exception.class, () -> {
            providerDao.update(providerUpdate);
        });

    }
}