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

    @Test
    void insertValidDataReturnTrue() throws Exception {
        providerDao.create(provider);
        Integer result = advertisementDao.create(advertisement);
        assertEquals(Integer.valueOf(1), result);
    }

    @Test
    void insertWhenTableAdvertisementDroppedThrowDaoException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_advertisement.sql"));
        assertThrows(DaoException.class, () -> {
            advertisementDao.create(advertisement);
        });
    }

    @Test
    void insertWhenTableDroppedThrowException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_advertisement.sql"));
        assertThrows(Exception.class, () -> {
            advertisementDao.create(advertisement);
        });
    }

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

    @Test
    void getObjectByIdWhenExecutedNullReturnTrue() throws Exception {
        Advertisement actual = advertisementDao.getById(advertisement.getAdId()).orElse(null);
        assertEquals(null, actual);
    }

    @Test
    void getObjectByIdWhenTableAdvertisementDroppedThrowDaoException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_advertisement.sql"));
        assertThrows(DaoException.class, () -> {
            advertisementDao.getById(advertisement.getAdId());
        });
    }

    @Test
    void deleteValidDataReturnTrue() throws Exception {
        providerDao.create(provider);
        advertisementDao.create(advertisement);
        Integer actual = advertisementDao.delete(advertisement.getAdId());
        assertEquals(Integer.valueOf(1), actual);
    }

    @Test
    void deleteObjectByIdWhenTableAdvertisementDroppedThrowDaoException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_advertisement.sql"));
        assertThrows(DaoException.class, () -> {
            advertisementDao.getById(advertisement.getAdId());
        });
    }

    @Test
    void deleteObjectByIdWhenTableDroppedThrowException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_advertisement.sql"));
        assertThrows(Exception.class, () -> {
            advertisementDao.delete(advertisement.getAdId());
        });
    }

    @Test
    void getObjectByIdWhenTableDroppedThrowException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_advertisement.sql"));
        assertThrows(Exception.class, () -> {
            advertisementDao.getById(advertisement.getAdId());
        });
    }

    @Test
    void getAllObjectWhenExecutedAndListIsNotEmptyReturnTrue() throws Exception {
        providerDao.create(provider);
        advertisementDao.create(advertisement);
        boolean actual = advertisementDao.getAll().isEmpty();
        assertEquals(Boolean.valueOf(false), actual);
    }

    @Test
    void getAllObjectWhenExecutedAndGetListWithSizeTwoReturnTrue() throws Exception {
        providerDao.create(provider);
        advertisementDao.create(advertisement);
        advertisementDao.create(advertisement);
        Integer actual = advertisementDao.getAll().size();
        assertEquals(Integer.valueOf(2), actual);
    }

    @Test
    void getAllObjectWhenTableDroppedThrowDaoException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_advertisement.sql"));
        assertThrows(DaoException.class, () -> {
            advertisementDao.getAll();
        });
    }

    @Test
    void getAllObjectWhenTableDroppedThrowException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_advertisement.sql"));
        assertThrows(Exception.class, () -> {
            advertisementDao.getAll();
        });
    }

    @Test
    void updateDataWhenExecutedReturnTrue() throws Exception {
        providerDao.create(provider);
        advertisementDao.create(advertisement);
        Advertisement advertisementUpdate = new Advertisement(1L, "AdvertoNEUpdate",
                "WELCOME TO UKRAINE UPDATE", "iMAGE uRL UPDATE", "English UPDATE", 1L);
        Integer actual = advertisementDao.update(advertisementUpdate);
        assertEquals(Integer.valueOf(1), actual);
    }

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