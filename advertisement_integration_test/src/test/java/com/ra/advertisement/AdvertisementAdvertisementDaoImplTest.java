package com.ra.advertisement;

import com.ra.advertisement.connection.ConnectionFactory;
import com.ra.advertisement.dao.AdvertisementAdvertisementDaoImpl;
import com.ra.advertisement.dao.AdvertisementDao;
import com.ra.advertisement.dao.ProviderAdvertisementDaoImpl;
import com.ra.advertisement.dao.exceptions.DaoException;
import com.ra.advertisement.entity.Advertisement;
import com.ra.advertisement.entity.Provider;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AdvertisementAdvertisementDaoImplTest {

    private static final Advertisement ADVERTISEMENT = new Advertisement(1L, "AdvertoNE",
            "WELCOME TO UKRAINE", "iMAGE uRL", "English", 1L);
    private static final Provider PROVIDER = new Provider(1L, "Coca Cola", "Lviv",
            "22-45-18", "Ukraine");
    private static final Advertisement ADVERTISEMENT_UPDATE = new Advertisement(1L, "AdvertoNEUpdate",
            "WELCOME TO UKRAINE UPDATE", "iMAGE uRL UPDATE", "English UPDATE", 1L);
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
        providerDao.create(PROVIDER);
        Integer result = advertisementDao.create(ADVERTISEMENT);
        assertEquals(Integer.valueOf(1), result);
    }

    /**
     * testing if throws DaoException of create method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void insertValidDataAfterTheTableOfAdvertisementWasDroppedThrowDaoException() throws Exception {
        dropTableAdvertisementMethod();
        assertThrows(DaoException.class, () -> {
            advertisementDao.create(ADVERTISEMENT);
        });
    }

    /**
     * testing if throws Exception of create method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void insertValidDataAfterTheTableOfAdvertisementWasDroppedThrowException() throws Exception {
        dropTableAdvertisementMethod();
        assertThrows(Exception.class, () -> {
            advertisementDao.create(ADVERTISEMENT);
        });
    }

    /**
     * testing successful result of getById method which gets info regarding Advertisement from DB
     *
     * @throws Exception
     */
    @Test
    void getObjectByIdExecutedReturnTrue() throws Exception {
        providerDao.create(PROVIDER);
        advertisementDao.create(ADVERTISEMENT);
        Advertisement actual = advertisementDao.getById(ADVERTISEMENT.getAdId()).orElse(null);
        assertAll("actual",
                () -> assertEquals(ADVERTISEMENT.getAdId(), actual.getAdId()),
                () -> assertEquals(ADVERTISEMENT.getTitle(), actual.getTitle()),
                () -> assertEquals(ADVERTISEMENT.getContext(), actual.getContext()),
                () -> assertEquals(ADVERTISEMENT.getImageUrl(), actual.getImageUrl()),
                () -> assertEquals(ADVERTISEMENT.getLanguage(), actual.getLanguage()),
                () -> assertEquals(ADVERTISEMENT.getProvId(), actual.getProvId()));
    }

    /**
     * testing result (null) of getById method which gets info regarding Advertisement from DB when there no such id in DB
     *
     * @throws Exception
     */
    @Test
    void getObjectByIdExecutedNullReturnTrue() throws Exception {
        Advertisement actual = advertisementDao.getById(ADVERTISEMENT.getAdId()).orElse(null);
        assertEquals(null, actual);
    }

    /**
     * testing if throws DaoException of getById method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void getObjectByIAfterTheTableOfAdvertisementWasDroppedThrowDaoException() throws Exception {
        dropTableAdvertisementMethod();
        assertThrows(DaoException.class, () -> {
            advertisementDao.getById(ADVERTISEMENT.getAdId());
        });
    }

    /**
     * testing successful result of delete method which delete info regarding Advertisement from DB
     *
     * @throws Exception
     */
    @Test
    void deleteValidDataExecutedReturnTrue() throws Exception {
        providerDao.create(PROVIDER);
        advertisementDao.create(ADVERTISEMENT);
        Integer actual = advertisementDao.delete(ADVERTISEMENT.getAdId());
        assertEquals(Integer.valueOf(1), actual);
    }

    /**
     * testing if throws DaoException of delete method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void deleteObjectByIdAfterTheTableOfAdvertisementWasDroppedThrowDaoException() throws Exception {
        dropTableAdvertisementMethod();
        assertThrows(DaoException.class, () -> {
            advertisementDao.getById(ADVERTISEMENT.getAdId());
        });
    }

    /**
     * testing if throws Exception of delete method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void deleteObjectByIdAfterTheTableOfAdvertisementWasDroppedThrowException() throws Exception {
        dropTableAdvertisementMethod();
        assertThrows(Exception.class, () -> {
            advertisementDao.delete(ADVERTISEMENT.getAdId());
        });
    }

    /**
     * testing if throws Exception of getById method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void getObjectByIdAfterTheTableOfAdvertisementWasDroppedThrowException() throws Exception {
        dropTableAdvertisementMethod();
        assertThrows(Exception.class, () -> {
            advertisementDao.getById(ADVERTISEMENT.getAdId());
        });
    }

    /**
     * testing successful result of getAll method which gets info regarding all Advertisement from DB
     *
     * @throws Exception
     */
    @Test
    void getAllObjectExecutedAndListIsNotEmptyReturnTrue() throws Exception {
        providerDao.create(PROVIDER);
        advertisementDao.create(ADVERTISEMENT);
        boolean actual = advertisementDao.getAll().isEmpty();
        assertEquals(Boolean.valueOf(false), actual);
    }

    /**
     * testing successful result of getAll method which gets info regarding all Advertisement from DB
     *
     * @throws Exception
     */
    @Test
    void getAllObjectExecutedTwiceAndTheListsSizeIsTwoReturnTrue() throws Exception {
        providerDao.create(PROVIDER);
        advertisementDao.create(ADVERTISEMENT);
        advertisementDao.create(ADVERTISEMENT);
        Integer actual = advertisementDao.getAll().size();
        assertEquals(Integer.valueOf(2), actual);
    }

    /**
     * testing if throws DaoException of getAll method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void getAllObjectAfterTheTableOfAdvertisementWasDroppedThrowDaoException() throws Exception {
        dropTableAdvertisementMethod();
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
    void getAllObjectAfterTheTableOfAdvertisementWasDroppedThrowException() throws Exception {
        dropTableAdvertisementMethod();
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
    void updateDataExecutedReturnTrue() throws Exception {
        providerDao.create(PROVIDER);
        advertisementDao.create(ADVERTISEMENT);
        Integer actual = advertisementDao.update(ADVERTISEMENT_UPDATE);
        assertEquals(Integer.valueOf(1), actual);
    }

    /**
     * testing successful result of update method which updates info regarding Advertisement in DB
     *
     * @throws Exception
     */
    @Test
    void updateDataExecutedAndAllFieldsOfAdvertisementRecievedAndCheckedReturnTrue() throws Exception {
        providerDao.create(PROVIDER);
        advertisementDao.create(ADVERTISEMENT);
        advertisementDao.update(ADVERTISEMENT_UPDATE);
        Advertisement actual = advertisementDao.getById(ADVERTISEMENT_UPDATE.getAdId()).orElse(null);
        assertAll("actual",
                () -> assertEquals(ADVERTISEMENT_UPDATE.getAdId(), actual.getAdId()),
                () -> assertEquals(ADVERTISEMENT_UPDATE.getTitle(), actual.getTitle()),
                () -> assertEquals(ADVERTISEMENT_UPDATE.getContext(), actual.getContext()),
                () -> assertEquals(ADVERTISEMENT_UPDATE.getImageUrl(), actual.getImageUrl()),
                () -> assertEquals(ADVERTISEMENT_UPDATE.getLanguage(), actual.getLanguage()),
                () -> assertEquals(ADVERTISEMENT_UPDATE.getProvId(), actual.getProvId()));
    }

    /**
     * testing if throws DaoException of update method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void updateObjectAfterTheTableOfAdvertisementWasDroppedThrowDaoException() throws Exception {
        dropTableAdvertisementMethod();
        assertThrows(DaoException.class, () -> {
            advertisementDao.update(ADVERTISEMENT_UPDATE);
        });
    }

    /**
     * testing if throws Exception of update method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void updateObjectAfterTheTableOfAdvertisementWasDroppedThrowException() throws Exception {
        dropTableAdvertisementMethod();
        assertThrows(Exception.class, () -> {
            advertisementDao.update(ADVERTISEMENT_UPDATE);
        });
    }

    /**
     * method drop table Advertisement
     *
     * @throws Exception
     */
    static void dropTableAdvertisementMethod() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_advertisement.sql"));
    }
}