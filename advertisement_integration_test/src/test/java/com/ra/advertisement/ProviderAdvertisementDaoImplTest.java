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
import java.net.URL;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProviderAdvertisementDaoImplTest {
    private static final Provider PROVIDER = new Provider("Coca Cola", "Lviv",
            "22-45-18", "Ukraine");
    private static final Provider PROVIDER_UPDATE = new Provider(1L, "Coca Cola Update",
            "LvivUpdate", "22-45-18Update", "UkraineUpdate");
    private static ConnectionFactory connectionFactory;
    private static URL urlToProviderDb;
    private static URL urlToDropProviderDB;
    private AdvertisementDao<Provider> providerDao;

    @BeforeEach
    void setUp() throws Exception {
        connectionFactory = ConnectionFactory.getInstance();
        providerDao = new ProviderAdvertisementDaoImpl(connectionFactory);
        Connection connection = connectionFactory.getConnection();
        urlToProviderDb = ClassLoader.getSystemResource("./provider_db.sql");
        RunScript.execute(connection, new FileReader(urlToProviderDb.getPath()));
    }

    /**
     * testing successful result of create method which save info regarding Provider into DB
     *
     * @throws Exception
     */
    @Test
    void insertValidDataIntoDbAndGetItsFromThereWithGeneratedIddReturnTrue() throws Exception {
        Provider providerCreated = providerDao.create(PROVIDER);
        Provider actual = providerDao.getById(providerCreated.getProvId()).orElse(null);
        assertAll("actual",
                () -> assertEquals(providerCreated.getProvId(), actual.getProvId()),
                () -> assertEquals(providerCreated.getName(), actual.getName()),
                () -> assertEquals(providerCreated.getAddress(), actual.getAddress()),
                () -> assertEquals(providerCreated.getCountry(), actual.getCountry()),
                () -> assertEquals(providerCreated.getTelephone(), actual.getTelephone())
        );
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
        Provider providerCreated = providerDao.create(PROVIDER);
        Provider actual = providerDao.getById(providerCreated.getProvId()).orElse(null);
        assertAll("actual",
                () -> assertEquals(providerCreated.getProvId(), actual.getProvId()),
                () -> assertEquals(providerCreated.getAddress(), actual.getAddress()),
                () -> assertEquals(providerCreated.getCountry(), actual.getCountry()),
                () -> assertEquals(providerCreated.getName(), actual.getName()),
                () -> assertEquals(providerCreated.getTelephone(), actual.getTelephone())
        );
    }

    /**
     * testing if throws DaoException of getById method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void getObjectByIdAfterTheTableOfProviderWasDroppedThrowDaoException() throws Exception {
        Provider providerCreated = providerDao.create(PROVIDER);
        dropTableProviderMethod();
        assertThrows(DaoException.class, () -> {
            providerDao.getById(providerCreated.getProvId());
        });
    }

    /**
     * testing if throws Exception of getById method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void getObjectByIdAfterTheTableOfProviderWasDroppedThrowException() throws Exception {
        Provider providerCreated = providerDao.create(PROVIDER);
        dropTableProviderMethod();
        assertThrows(Exception.class, () -> {
            providerDao.getById(providerCreated.getProvId());
        });
    }

    /**
     * testing successful result of delete method which delete info regarding Provider from DB
     *
     * @throws Exception
     */
    @Test
    void deleteValidDataExecutedReturnTrue() throws Exception {
        Provider providerCreated = providerDao.create(PROVIDER);
        Integer actual = providerDao.delete(providerCreated);
        assertEquals(Integer.valueOf(1), actual);
    }

    /**
     * testing if throws DaoException of delete method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void deleteObjectByIdAfterTheTableOfProviderWasDroppedThrowDaoException() throws Exception {
        Provider providerCreated = providerDao.create(PROVIDER);
        dropTableProviderMethod();
        assertThrows(DaoException.class, () -> {
            providerDao.delete(providerCreated);
        });
    }

    /**
     * testing if throws Exception of delete method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void deleteObjectByIdAfterTheTableOfProviderWasDroppedThrowException() throws Exception {
        Provider providerCreated = providerDao.create(PROVIDER);
        dropTableProviderMethod();
        assertThrows(Exception.class, () -> {
            providerDao.delete(providerCreated);
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
        providerDao.create(PROVIDER);
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
        providerDao.create(PROVIDER);
        dropTableProviderMethod();
        assertThrows(Exception.class, () -> {
            providerDao.getAll();
        });
    }

    /**
     * testing successful result of update method which updates info regarding Provider in DB
     *
     * @throws Exception
     */
    @Test
    void updateDataExecutedAndAllFieldsOfTheProviderRecievedAndCheckedReturnTrue() throws Exception {
        Provider providerCreated = providerDao.create(PROVIDER);
        Provider providerUpdated = providerDao.update(PROVIDER_UPDATE);
        Provider actual = providerDao.getById(providerUpdated.getProvId()).orElse(null);
        assertAll("actual",
                () -> assertEquals(providerUpdated.getProvId(), actual.getProvId()),
                () -> assertEquals(providerUpdated.getAddress(), actual.getAddress()),
                () -> assertEquals(providerUpdated.getCountry(), actual.getCountry()),
                () -> assertEquals(providerUpdated.getName(), actual.getName()),
                () -> assertEquals(providerUpdated.getTelephone(), actual.getTelephone())
        );
    }

    /**
     * testing if throws DaoException of update method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void updateObjectAfterTheTableOfProviderWasDroppedThrowDaoException() throws Exception {
        providerDao.create(PROVIDER);
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
        providerDao.create(PROVIDER);
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
        urlToDropProviderDB = ClassLoader.getSystemResource("./drop_table_provider.sql");
        RunScript.execute(connection, new FileReader(urlToDropProviderDB.getPath()));
    }
}