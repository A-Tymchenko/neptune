package com.ra.advertisement;

import com.ra.advertisement.connection.ConnectionFactory;
import com.ra.advertisement.dao.AdvertisementAdvertisementDaoImpl;
import com.ra.advertisement.dao.AdvertisementDao;
import com.ra.advertisement.dao.exceptions.DaoException;
import com.ra.advertisement.entity.Advertisement;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.net.URL;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AdvertisementAdvertisementDaoImplTest {
    private static final Advertisement ADVERTISEMENT = new Advertisement("AdvertoNE",
            "WELCOME TO UKRAINE", "iMAGE uRL", "English");
    private static final Advertisement ADVERTISEMENT_UPDATE = new Advertisement(1L, "AdvertoNEUpdate",
            "WELCOME TO UKRAINE UPDATE", "iMAGE uRL UPDATE", "English UPDATE");
    private static ConnectionFactory connectionFactory;
    private static URL urlToAdvertisementDB;
    private static URL urlToDropTableAdvertisement;
    private AdvertisementDao<Advertisement> advertisementDao;

    @BeforeEach
    void setUp() throws Exception {
        connectionFactory = ConnectionFactory.getInstance();
        advertisementDao = new AdvertisementAdvertisementDaoImpl(connectionFactory);
        Connection connection = connectionFactory.getConnection();
        urlToAdvertisementDB = ClassLoader.getSystemResource("./advertisement_db.sql");
        RunScript.execute(connection, new FileReader(urlToAdvertisementDB.getPath()));
    }

    /**
     * testing successful result of create method which save info regarding Advertisement into DB
     *
     * @throws Exception
     */
    @Test
    void insertValidDataIntoDbAndGetItsFromThereWithGeneratedIddReturnTrue() throws Exception {
        Advertisement advertisementCreated = advertisementDao.create(ADVERTISEMENT);
        Advertisement actual = advertisementDao.getById(advertisementCreated.getAdId()).orElse(null);
        assertAll("actual",
                () -> assertEquals(advertisementCreated.getAdId(), actual.getAdId()),
                () -> assertEquals(advertisementCreated.getTitle(), actual.getTitle()),
                () -> assertEquals(advertisementCreated.getContext(), actual.getContext()),
                () -> assertEquals(advertisementCreated.getImageUrl(), actual.getImageUrl()),
                () -> assertEquals(advertisementCreated.getLanguage(), actual.getLanguage())
        );
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
        Advertisement advertisementCreated = advertisementDao.create(ADVERTISEMENT);
        Advertisement actual = advertisementDao.getById(advertisementCreated.getAdId()).orElse(null);
        assertAll("actual",
                () -> assertEquals(advertisementCreated.getAdId(), actual.getAdId()),
                () -> assertEquals(advertisementCreated.getTitle(), actual.getTitle()),
                () -> assertEquals(advertisementCreated.getContext(), actual.getContext()),
                () -> assertEquals(advertisementCreated.getImageUrl(), actual.getImageUrl()),
                () -> assertEquals(advertisementCreated.getLanguage(), actual.getLanguage()));
    }

    /**
     * testing if throws DaoException of getById method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void getObjectByIAfterTheTableOfAdvertisementWasDroppedThrowDaoException() throws Exception {
        Advertisement advertisementCreated = advertisementDao.create(ADVERTISEMENT);
        dropTableAdvertisementMethod();
        assertThrows(DaoException.class, () -> {
            advertisementDao.getById(advertisementCreated.getAdId());
        });
    }

    /**
     * testing successful result of delete method which delete info regarding Advertisement from DB
     *
     * @throws Exception
     */
    @Test
    void deleteValidDataExecutedReturnTrue() throws Exception {
        Advertisement advertisementCreated = advertisementDao.create(ADVERTISEMENT);
        Integer actual = advertisementDao.delete(advertisementCreated);
        assertEquals(Integer.valueOf(1), actual);
    }

    /**
     * testing if throws DaoException of delete method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void deleteObjectByIdAfterTheTableOfAdvertisementWasDroppedThrowDaoException() throws Exception {
        Advertisement advertisementCreated = advertisementDao.create(ADVERTISEMENT);
        dropTableAdvertisementMethod();
        assertThrows(DaoException.class, () -> {
            advertisementDao.getById(advertisementCreated.getAdId());
        });
    }

    /**
     * testing if throws Exception of delete method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void deleteObjectByIdAfterTheTableOfAdvertisementWasDroppedThrowException() throws Exception {
        Advertisement advertisementCreated = advertisementDao.create(ADVERTISEMENT);
        dropTableAdvertisementMethod();
        assertThrows(Exception.class, () -> {
            advertisementDao.delete(advertisementCreated);
        });
    }

    /**
     * testing if throws Exception of getById method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void getObjectByIdAfterTheTableOfAdvertisementWasDroppedThrowException() throws Exception {
        Advertisement advertisementCreated = advertisementDao.create(ADVERTISEMENT);
        dropTableAdvertisementMethod();
        assertThrows(Exception.class, () -> {
            advertisementDao.getById(advertisementCreated.getAdId());
        });
    }

    /**
     * testing successful result of getAll method which gets info regarding all Advertisement from DB
     *
     * @throws Exception
     */
    @Test
    void getAllObjectExecutedAndListIsNotEmptyReturnTrue() throws Exception {
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
    void updateDataExecutedAndAllFieldsOfAdvertisementRecievedAndCheckedReturnTrue() throws Exception {
        advertisementDao.create(ADVERTISEMENT);
        Advertisement advertisementUpdated = advertisementDao.update(ADVERTISEMENT_UPDATE);
        Advertisement actual = advertisementDao.getById(advertisementUpdated.getAdId()).orElse(null);
        assertAll("actual",
                () -> assertEquals(advertisementUpdated.getAdId(), actual.getAdId()),
                () -> assertEquals(advertisementUpdated.getTitle(), actual.getTitle()),
                () -> assertEquals(advertisementUpdated.getContext(), actual.getContext()),
                () -> assertEquals(advertisementUpdated.getImageUrl(), actual.getImageUrl()),
                () -> assertEquals(advertisementUpdated.getLanguage(), actual.getLanguage()));
    }

    /**
     * testing if throws DaoException of update method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void updateObjectAfterTheTableOfAdvertisementWasDroppedThrowDaoException() throws Exception {
        advertisementDao.create(ADVERTISEMENT);
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
        advertisementDao.create(ADVERTISEMENT);
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
        urlToDropTableAdvertisement = ClassLoader.getSystemResource("./drop_table_advertisement.sql");
        RunScript.execute(connection, new FileReader(urlToDropTableAdvertisement.getPath()));
    }
}