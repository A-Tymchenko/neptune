package com.ra.advertisement.dao;

import com.ra.advertisement.connection.ConnectionFactory;
import com.ra.advertisement.dao.exceptions.DaoException;
import com.ra.advertisement.model.entities.Advertisement;
import com.ra.advertisement.model.entities.Provider;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AdvertisementAdvertisementDaoImplTest {

    private static final Advertisement advertisement = new Advertisement(1L, "AdvertoNE",
            "WELCOME TO UKRAINE", "iMAGE uRL", "English", 1L);
    private static final Provider provider = new Provider(1L, "Coca Cola", "Lviv",
            "22-45-18", "Ukraine");
    private static ConnectionFactory connectionFactory;
    private AdvertisementDao<Advertisement> advertisementDao;
    private AdvertisementDao<Provider> providerDao;

    @BeforeEach
    void setUp() throws Exception {
        connectionFactory = ConnectionFactory.getInstance();
        advertisementDao = new AdvertisementAdvertisementDaoImpl(connectionFactory);
        providerDao = new ProviderAdvertisementDaoImpl(connectionFactory);
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\provider_db.sql"));
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\advertisement_db.sql"));

    }

    /**
     * testing successful result of create method which saves info regarding Advertisement into DB
     *
     * @throws Exception
     */
    @Test
    void insertValidDataReturnTrue() throws Exception {
        providerDao.create(provider);
        Integer result = advertisementDao.create(advertisement);
        assertEquals(Integer.valueOf(1), result);
    }

    /**
     * testing if throws DaoException of create method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void insertWhenTableAdvertisementDroppedThrowDaoException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_advertisement.sql"));
        assertThrows(DaoException.class, () -> {
            advertisementDao.create(advertisement);
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
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_advertisement.sql"));
        assertThrows(Exception.class, () -> {
            advertisementDao.create(advertisement);
        });
    }

    /**
     * testing successful result of getById method which gets info regarding Advertisement from DB
     *
     * @throws Exception
     */
    @Test
    void getObjectByIdWhenExecutedReturnTrue() throws Exception {
        providerDao.create(provider);
        advertisementDao.create(advertisement);
        Advertisement actual = advertisementDao.getById(advertisement.getAdId()).orElse(null);
        assertAll("actual",
                () -> assertEquals(advertisement.getAdId(), actual.getAdId()),
                () -> assertEquals(advertisement.getTitle(), actual.getTitle()),
                () -> assertEquals(advertisement.getContext(), actual.getContext()),
                () -> assertEquals(advertisement.getImageUrl(), actual.getImageUrl()),
                () -> assertEquals(advertisement.getLanguage(), actual.getLanguage()),
                () -> assertEquals(advertisement.getProvId(), actual.getProvId()));
    }

    /**
     * testing result (null) of getById method which gets info regarding Advertisement from DB when there no such id in DB
     *
     * @throws Exception
     */
    @Test
    void getObjectByIdWhenExecutedNullReturnTrue() throws Exception {
        Advertisement actual = advertisementDao.getById(advertisement.getAdId()).orElse(null);
        assertEquals(null, actual);
    }

    /**
     * testing if throws DaoException of getById method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void getObjectByIdWhenTableAdvertisementDroppedThrowDaoException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_advertisement.sql"));
        assertThrows(DaoException.class, () -> {
            advertisementDao.getById(advertisement.getAdId());
        });
    }

    /**
     * testing successful result of delete method which delete info regarding Advertisement from DB
     *
     * @throws Exception
     */
    @Test
    void deleteValidDataReturnTrue() throws Exception {
        providerDao.create(provider);
        advertisementDao.create(advertisement);
        Integer actual = advertisementDao.delete(advertisement.getAdId());
        assertEquals(Integer.valueOf(1), actual);
    }

    /**
     * testing if throws DaoException of delete method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void deleteObjectByIdWhenTableAdvertisementDroppedThrowDaoException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_advertisement.sql"));
        assertThrows(DaoException.class, () -> {
            advertisementDao.getById(advertisement.getAdId());
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
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_advertisement.sql"));
        assertThrows(Exception.class, () -> {
            advertisementDao.delete(advertisement.getAdId());
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
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_advertisement.sql"));
        assertThrows(Exception.class, () -> {
            advertisementDao.getById(advertisement.getAdId());
        });
    }

    /**
     * testing successful result of getAll method which gets info regarding all Advertisement from DB
     *
     * @throws Exception
     */
    @Test
    void getAllObjectWhenExecutedAndListIsNotEmptyReturnTrue() throws Exception {
        providerDao.create(provider);
        advertisementDao.create(advertisement);
        boolean actual = advertisementDao.getAll().isEmpty();
        assertEquals(Boolean.valueOf(false), actual);
    }

    /**
     * testing successful result of getAll method which gets info regarding all Advertisement from DB
     *
     * @throws Exception
     */
    @Test
    void getAllObjectWhenExecutedAndGetListWithSizeTwoReturnTrue() throws Exception {
        providerDao.create(provider);
        advertisementDao.create(advertisement);
        advertisementDao.create(advertisement);
        Integer actual = advertisementDao.getAll().size();
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
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_advertisement.sql"));
        assertThrows(DaoException.class, () -> {
            advertisementDao.getAll();
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
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_advertisement.sql"));
        assertThrows(Exception.class, () -> {
            advertisementDao.getAll();
        });
    }

    /**
     * testing successful result of update method which updates info regarding Advertisement in DB
     *
     * @throws Exception
     */
    @Test
    void updateDataWhenExecutedReturnTrue() throws Exception {
        providerDao.create(provider);
        advertisementDao.create(advertisement);
        Advertisement advertisementUpdate = new Advertisement(1L, "AdvertoNEUpdate",
                "WELCOME TO UKRAINE UPDATE", "iMAGE uRL UPDATE", "English UPDATE", 1L);
        Integer actual = advertisementDao.update(advertisementUpdate);
        assertEquals(Integer.valueOf(1), actual);
    }

    /**
     * testing successful result of update method which updates info regarding Advertisement in DB
     *
     * @throws Exception
     */
    @Test
    void updateDataWhenExecutedAndGetResultReturnTrue() throws Exception {
        providerDao.create(provider);
        advertisementDao.create(advertisement);
        Advertisement advertisementUpdate = new Advertisement(1L, "AdvertoNEUpdate",
                "WELCOME TO UKRAINE UPDATE", "iMAGE uRL UPDATE", "English UPDATE", 1L);
        advertisementDao.update(advertisementUpdate);
        Advertisement actual = advertisementDao.getById(advertisementUpdate.getAdId()).orElse(null);
        assertAll("actual",
                () -> assertEquals(advertisementUpdate.getAdId(), actual.getAdId()),
                () -> assertEquals(advertisementUpdate.getTitle(), actual.getTitle()),
                () -> assertEquals(advertisementUpdate.getContext(), actual.getContext()),
                () -> assertEquals(advertisementUpdate.getImageUrl(), actual.getImageUrl()),
                () -> assertEquals(advertisementUpdate.getLanguage(), actual.getLanguage()),
                () -> assertEquals(advertisementUpdate.getProvId(), actual.getProvId()));
    }

    /**
     * testing if throws DaoException of update method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void updateObjectWhenTableAdvertisementDroppedThrowDaoException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        Advertisement advertisementUpdate = new Advertisement(1L, "AdvertoNEUpdate",
                "WELCOME TO UKRAINE UPDATE", "iMAGE uRL UPDATE", "English UPDATE", 1L);
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_advertisement.sql"));
        assertThrows(DaoException.class, () -> {
            advertisementDao.update(advertisementUpdate);
        });
    }

    /**
     * testing if throws Exception of update method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void updateObjectWhenTableDroppedThrowExeption() throws Exception {
        Connection connection = connectionFactory.getConnection();
        Advertisement advertisementUpdate = new Advertisement(1L, "AdvertoNEUpdate",
                "WELCOME TO UKRAINE UPDATE", "iMAGE uRL UPDATE", "English UPDATE", 1L);
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_advertisement.sql"));
        assertThrows(Exception.class, () -> {
            advertisementDao.update(advertisementUpdate);
        });

    }
}