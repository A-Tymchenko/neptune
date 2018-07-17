package com.ra.advertisement;

import com.ra.advertisement.connection.ConnectionFactory;
import com.ra.advertisement.dao.AdvertisementDao;
import com.ra.advertisement.dao.PublisherAdvertisementDaoImpl;
import com.ra.advertisement.dao.exceptions.DaoException;
import com.ra.advertisement.entity.Publisher;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.net.URL;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PublisherAdvertisementDaoImplTest {

    private static final Publisher PUBLISHER = new Publisher(1L, "Advert ltd",
            "Kyiv", "25-17-84", "Ukraine");
    private static final Publisher PUBLISHER_UPDATE = new Publisher(1L, "Advert ltd Update",
            "Kyiv Update", "25-17-84 Update", "Ukraine Update");
    private static ConnectionFactory connectionFactory;
    private static URL urlToPublisherDb;
    private static URL urlToDropPublisherDb;
    private AdvertisementDao<Publisher> publisherDao;

    @BeforeEach
    void setUp() throws Exception {
        connectionFactory = ConnectionFactory.getInstance();
        publisherDao = new PublisherAdvertisementDaoImpl(connectionFactory);
        Connection connection = connectionFactory.getConnection();
        urlToPublisherDb = ClassLoader.getSystemResource("./publisher_db.sql");
        RunScript.execute(connection, new FileReader(urlToPublisherDb.getPath()));
    }

    /**
     * testing successful result of create method which saves info regarding Publisher into DB
     *
     * @throws Exception
     */
    @Test
    void insertValidDataReturnTrue() throws Exception {
        Integer result = publisherDao.create(PUBLISHER);
        assertEquals(Integer.valueOf(1), result);
    }

    /**
     * testing if throws DaoException of create method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void insertValidDataAfterTheTableOfPublisherWasDroppedThrowDaoException() throws Exception {
        dropTablePublisherMethod();
        assertThrows(DaoException.class, () -> {
            publisherDao.create(PUBLISHER);
        });
    }

    /**
     * testing if throws Exception of create method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void insertValidDataAfterTheTableOfPublisherWasDroppedThrowException() throws Exception {
        dropTablePublisherMethod();
        assertThrows(Exception.class, () -> {
            publisherDao.create(PUBLISHER);
        });
    }

    /**
     * testing successful result of getById method which gets info regarding Publisher from DB
     *
     * @throws Exception
     */
    @Test
    void getObjectByIExecutedReturnTrue() throws Exception {
        publisherDao.create(PUBLISHER);
        Publisher actual = publisherDao.getById(PUBLISHER.getPubId()).orElse(null);
        assertAll("actual",
                () -> assertEquals(PUBLISHER.getPubId(), actual.getPubId()),
                () -> assertEquals(PUBLISHER.getAddress(), actual.getAddress()),
                () -> assertEquals(PUBLISHER.getCountry(), actual.getCountry()),
                () -> assertEquals(PUBLISHER.getName(), actual.getName()),
                () -> assertEquals(PUBLISHER.getTelephone(), actual.getTelephone())
        );
    }

    /**
     * testing result (null) of getById method which gets info regarding Publisher from DB when there no such id in DB
     *
     * @throws Exception
     */
    @Test
    void getObjectByIdExecutedAndReturnNullReturnTrue() throws Exception {
        Publisher actual = publisherDao.getById(PUBLISHER.getPubId()).orElse(null);
        assertEquals(null, actual);
    }

    /**
     * testing if throws DaoException of getById method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void getObjectByIdAfterTheTableOfPublisherWasDroppedThrowDaoException() throws Exception {
        dropTablePublisherMethod();
        assertThrows(DaoException.class, () -> {
            publisherDao.getById(PUBLISHER.getPubId());
        });
    }

    /**
     * testing if throws Exception of getById method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void getObjectByIdAfterTheTableOfPublisherWasDroppedThrowException() throws Exception {
        dropTablePublisherMethod();
        assertThrows(Exception.class, () -> {
            publisherDao.getById(PUBLISHER.getPubId());
        });
    }

    /**
     * testing successful result of delete method which delete info regarding Publisher from DB
     *
     * @throws Exception
     */
    @Test
    void deleteValidDataExecutedReturnTrue() throws Exception {
        publisherDao.create(PUBLISHER);
        Integer actual = publisherDao.delete(PUBLISHER.getPubId());
        assertEquals(Integer.valueOf(1), actual);
    }

    /**
     * testing if throws DaoException of delete method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void deleteObjectByIdAfterTheTableOfPublisherWasDroppedThrowDaoException() throws Exception {
        dropTablePublisherMethod();
        assertThrows(DaoException.class, () -> {
            publisherDao.getById(PUBLISHER.getPubId());
        });
    }

    /**
     * testing if throws Exception of delete method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void deleteObjectByIdAfterTheTableOfPublisherWasDroppedThrowException() throws Exception {
        dropTablePublisherMethod();
        assertThrows(Exception.class, () -> {
            publisherDao.getById(PUBLISHER.getPubId());
        });
    }

    /**
     * testing successful result of getAll method which gets info regarding all Publishers from DB
     *
     * @throws Exception
     */
    @Test
    void getAllObjectExecutedAndTheListIsNotEmptyReturnTrue() throws Exception {
        publisherDao.create(PUBLISHER);
        boolean actual = publisherDao.getAll().isEmpty();
        assertEquals(Boolean.valueOf(false), actual);
    }

    /**
     * testing successful result of getAll method which gets info regarding all Publishers from DB
     *
     * @throws Exception
     */
    @Test
    void getAllObjectExecutedAndTheListsSizeIsTwoReturnTrue() throws Exception {
        publisherDao.create(PUBLISHER);
        publisherDao.create(PUBLISHER);
        Integer actual = publisherDao.getAll().size();
        assertEquals(Integer.valueOf(2), actual);
    }

    /**
     * testing if throws DaoException of getAll method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void getAllObjectAfterTheTableOfPublisherWasDroppedThrowDaoException() throws Exception {
        dropTablePublisherMethod();
        assertThrows(DaoException.class, () -> {
            publisherDao.getAll();
        });
    }

    /**
     * testing if throws Exception of getAll method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void getAllObjectAfterTheTableOfPublisherWasDroppedThrowException() throws Exception {
        dropTablePublisherMethod();
        assertThrows(Exception.class, () -> {
            publisherDao.getAll();
        });
    }

    /**
     * testing successful result of update method which updates info regarding Publisher in DB
     *
     * @throws Exception
     */
    @Test
    void updateDataWhenExecutedReturnTrue() throws Exception {
        publisherDao.create(PUBLISHER);
        Integer actual = publisherDao.update(PUBLISHER_UPDATE);
        assertEquals(Integer.valueOf(1), actual);
    }

    /**
     * testing successful result of update method which updates info regarding Publisher in DB
     *
     * @throws Exception
     */
    @Test
    void updateDataExecutedAndAllFieldsOfThePublisherRecievedAndCheckedReturnTrue() throws Exception {
        publisherDao.create(PUBLISHER);
        publisherDao.update(PUBLISHER_UPDATE);
        Publisher actual = publisherDao.getById(PUBLISHER_UPDATE.getPubId()).orElse(null);
        assertAll("actual",
                () -> assertEquals(PUBLISHER_UPDATE.getPubId(), actual.getPubId()),
                () -> assertEquals(PUBLISHER_UPDATE.getAddress(), actual.getAddress()),
                () -> assertEquals(PUBLISHER_UPDATE.getCountry(), actual.getCountry()),
                () -> assertEquals(PUBLISHER_UPDATE.getName(), actual.getName()),
                () -> assertEquals(PUBLISHER_UPDATE.getTelephone(), actual.getTelephone())
        );
    }

    /**
     * testing if throws DaoException of update method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void updateObjectAfterTheTableOfPublisherWasDroppedThrowDaoException() throws Exception {
        dropTablePublisherMethod();
        assertThrows(DaoException.class, () -> {
            publisherDao.update(PUBLISHER_UPDATE);
        });
    }

    /**
     * testing if throws Exception of update method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void updateObjectAfterTheTableOfPublisherWasDroppedThrowException() throws Exception {
        dropTablePublisherMethod();
        assertThrows(Exception.class, () -> {
            publisherDao.update(PUBLISHER_UPDATE);
        });
    }

    /**
     * method drop table Publisher
     *
     * @throws Exception
     */
    static void dropTablePublisherMethod() throws Exception {
        Connection connection = connectionFactory.getConnection();
        urlToDropPublisherDb = ClassLoader.getSystemResource("./drop_table_publisher.sql");
        RunScript.execute(connection, new FileReader(urlToDropPublisherDb.getPath()));
    }
}