package com.ra.advertisement;

import com.ra.advertisement.connection.ConnectionFactory;
import com.ra.advertisement.dao.AdvertisementDao;
import com.ra.advertisement.dao.ProviderAdvertisementDaoImpl;
import com.ra.advertisement.dao.exceptions.DaoException;
import com.ra.advertisement.entity.Provider;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProviderAdvertisementDaoImplTest {

    private static final Provider PROVIDER = new Provider(1L, "Coca Cola", "Lviv",
            "22-45-18", "Ukraine");
    private static final Provider PROVIDER_UPDATE = new Provider(1L, "Coca Cola Update",
            "LvivUpdate", "22-45-18Update", "UkraineUpdate");
    private static ConnectionFactory connectionFactory;
    private AdvertisementDao<Provider> providerDao;

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
        Integer result = providerDao.create(PROVIDER);
        assertEquals(Integer.valueOf(1), result);
    }

    /**
     * testing if throws DaoException of create method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void insertValidDataAfterTheTableOfProviderWasDroppedThrowDaoException() throws Exception {
        dropTableProviderMethod();
        assertThrows(DaoException.class, () -> {
            providerDao.create(PROVIDER);
        });
    }

    /**
     * testing if throws Exception of create method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void insertValidDataAfterTheTableOfProviderWasDroppedThrowException() throws Exception {
        dropTableProviderMethod();
        assertThrows(Exception.class, () -> {
            providerDao.create(PROVIDER);
        });
    }

    /**
     * testing successful result of getById method which gets info regarding Provider from DB
     *
     * @throws Exception
     */
    @Test
    void getObjectByIdExecutedReturnTrue() throws Exception {
        providerDao.create(PROVIDER);
        Provider actual = providerDao.getById(PROVIDER.getProvId()).orElse(null);
        assertAll("actual",
                () -> assertEquals(PROVIDER.getProvId(), actual.getProvId()),
                () -> assertEquals(PROVIDER.getAddress(), actual.getAddress()),
                () -> assertEquals(PROVIDER.getCountry(), actual.getCountry()),
                () -> assertEquals(PROVIDER.getName(), actual.getName()),
                () -> assertEquals(PROVIDER.getTelephone(), actual.getTelephone())
        );
    }

    /**
     * testing result (null) of getById method which gets info regarding Provider from DB when there no such id in DB
     *
     * @throws Exception
     */
    @Test
    void getObjectByIdExecutedAndReturnNullReturnTrue() throws Exception {
        Provider actual = providerDao.getById(PROVIDER.getProvId()).orElse(null);
        assertEquals(null, actual);
    }

    /**
     * testing if throws DaoException of getById method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void getObjectByIdAfterTheTableOfProviderWasDroppedThrowDaoException() throws Exception {
        dropTableProviderMethod();
        assertThrows(DaoException.class, () -> {
            providerDao.getById(PROVIDER.getProvId());
        });
    }

    /**
     * testing if throws Exception of getById method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void getObjectByIdAfterTheTableOfProviderWasDroppedThrowException() throws Exception {
        dropTableProviderMethod();
        assertThrows(Exception.class, () -> {
            providerDao.getById(PROVIDER.getProvId());
        });
    }

    /**
     * testing successful result of delete method which delete info regarding Provider from DB
     *
     * @throws Exception
     */
    @Test
    void deleteValidDataExecutedReturnTrue() throws Exception {
        providerDao.create(PROVIDER);
        Integer actual = providerDao.delete(PROVIDER.getProvId());
        assertEquals(Integer.valueOf(1), actual);
    }

    /**
     * testing unsuccessful result of delete method which delete info regarding Provider from DB
     *
     * @throws Exception
     */
    @Test
    void deleteInValidDataReturnTrue() throws Exception {
        providerDao.create(PROVIDER);
        Integer actual = providerDao.delete(2L);
        assertEquals(Integer.valueOf(0), actual);
    }

    /**
     * testing if throws DaoException of delete method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void deleteObjectByIdAfterTheTableOfProviderWasDroppedThrowDaoException() throws Exception {
        dropTableProviderMethod();
        assertThrows(DaoException.class, () -> {
            providerDao.delete(PROVIDER.getProvId());
        });
    }

    /**
     * testing if throws Exception of delete method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void deleteObjectByIdAfterTheTableOfProviderWasDroppedThrowException() throws Exception {
        dropTableProviderMethod();
        assertThrows(Exception.class, () -> {
            providerDao.delete(PROVIDER.getProvId());
        });
    }

    /**
     * testing successful result of getAll method which gets info regarding all Provider from DB
     *
     * @throws Exception
     */
    @Test
    void getAllObjectExecutedAndTheListIsNotEmptyReturnTrue() throws Exception {
        providerDao.create(PROVIDER);
        boolean actual = providerDao.getAll().isEmpty();
        assertEquals(Boolean.valueOf(false), actual);
    }

    /**
     * testing successful result of getAll method which gets info regarding all Providers from DB
     *
     * @throws Exception
     */
    @Test
    void getAllObjectExecutedAndTheListsSizeIsTwoReturnTrue() throws Exception {
        providerDao.create(PROVIDER);
        providerDao.create(PROVIDER);
        Integer actual = providerDao.getAll().size();
        assertEquals(Integer.valueOf(2), actual);
    }

    /**
     * testing if throws DaoException of getAll method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void getAllObjectAfterTheTableOfProviderWasDroppedThrowDaoException() throws Exception {
        dropTableProviderMethod();
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
    void getAllObjectAfterTheTableOfProviderWasDroppedThrowException() throws Exception {
        dropTableProviderMethod();
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
    void updateDataExecutedReturnTrue() throws Exception {
        providerDao.create(PROVIDER);
        Integer actual = providerDao.update(PROVIDER_UPDATE);
        assertEquals(Integer.valueOf(1), actual);
    }

    /**
     * testing successful result of update method which updates info regarding Provider in DB
     *
     * @throws Exception
     */
    @Test
    void updateDataExecutedAndAllFieldsOfTheProviderRecievedAndCheckedReturnTrue() throws Exception {
        providerDao.create(PROVIDER);
        providerDao.update(PROVIDER_UPDATE);
        Provider actual = providerDao.getById(PROVIDER_UPDATE.getProvId()).orElse(null);
        assertAll("actual",
                () -> assertEquals(PROVIDER_UPDATE.getProvId(), actual.getProvId()),
                () -> assertEquals(PROVIDER_UPDATE.getAddress(), actual.getAddress()),
                () -> assertEquals(PROVIDER_UPDATE.getCountry(), actual.getCountry()),
                () -> assertEquals(PROVIDER_UPDATE.getName(), actual.getName()),
                () -> assertEquals(PROVIDER_UPDATE.getTelephone(), actual.getTelephone())
        );
    }

    /**
     * testing if throws DaoException of update method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void updateObjectAfterTheTableOfProviderWasDroppedThrowDaoException() throws Exception {
        dropTableProviderMethod();
        assertThrows(DaoException.class, () -> {
            providerDao.update(PROVIDER_UPDATE);
        });
    }

    /**
     * testing if throws Exception of update method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void updateObjectAfterTheTableOfProviderWasDroppedThrowException() throws Exception {
        dropTableProviderMethod();
        assertThrows(Exception.class, () -> {
            providerDao.update(PROVIDER_UPDATE);
        });
    }

    /**
     * method drop table Provider
     *
     * @throws Exception
     */
    static void dropTableProviderMethod() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_provider.sql"));
    }
}