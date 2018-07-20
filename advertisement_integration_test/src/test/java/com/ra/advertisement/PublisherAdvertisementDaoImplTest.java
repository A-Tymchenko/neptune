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
    private static final Publisher PUBLISHER = new Publisher("Advert ltd",
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
     * testing successful result of create method which save info regarding Publisher into DB
     *
     * @throws Exception
     */
    @Test
    void insertValidDataIntoDbAndGetItsFromThereWithGeneratedIddReturnTrue() throws Exception {
        Publisher publisherCreated = publisherDao.create(PUBLISHER);
        Publisher actual = publisherDao.getById(publisherCreated.getPubId()).orElse(null);
        assertAll("actual",
                () -> assertEquals(publisherCreated.getPubId(), actual.getPubId()),
                () -> assertEquals(publisherCreated.getName(), actual.getName()),
                () -> assertEquals(publisherCreated.getAddress(), actual.getAddress()),
                () -> assertEquals(publisherCreated.getCountry(), actual.getCountry()),
                () -> assertEquals(publisherCreated.getTelephone(), actual.getTelephone())
        );
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
    void getObjectByIdExecutedReturnTrue() throws Exception {
        Publisher publisherCreated = publisherDao.create(PUBLISHER);
        Publisher actual = publisherDao.getById(publisherCreated.getPubId()).orElse(null);
        assertAll("actual",
                () -> assertEquals(publisherCreated.getPubId(), actual.getPubId()),
                () -> assertEquals(publisherCreated.getAddress(), actual.getAddress()),
                () -> assertEquals(publisherCreated.getCountry(), actual.getCountry()),
                () -> assertEquals(publisherCreated.getName(), actual.getName()),
                () -> assertEquals(publisherCreated.getTelephone(), actual.getTelephone())
        );
    }


    /**
     * testing if throws DaoException of getById method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void getObjectByIdAfterTheTableOfPublisherWasDroppedThrowDaoException() throws Exception {
        Publisher publisherCreated = publisherDao.create(PUBLISHER);
        dropTablePublisherMethod();
        assertThrows(DaoException.class, () -> {
            publisherDao.getById(publisherCreated.getPubId());
        });
    }

    /**
     * testing if throws Exception of getById method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void getObjectByIdAfterTheTableOfPublisherWasDroppedThrowException() throws Exception {
        Publisher publisherCreated = publisherDao.create(PUBLISHER);
        dropTablePublisherMethod();
        assertThrows(Exception.class, () -> {
            publisherDao.getById(publisherCreated.getPubId());
        });
    }

    /**
     * testing successful result of delete method which delete info regarding Publisher from DB
     *
     * @throws Exception
     */
    @Test
    void deleteValidDataExecutedReturnTrue() throws Exception {
        Publisher publisherCreated = publisherDao.create(PUBLISHER);
        Integer actual = publisherDao.delete(publisherCreated);
        assertEquals(Integer.valueOf(1), actual);
    }

    /**
     * testing if throws DaoException of delete method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void deleteObjectByIdAfterTheTableOfPublisherWasDroppedThrowDaoException() throws Exception {
        Publisher publisherCreated = publisherDao.create(PUBLISHER);
        dropTablePublisherMethod();
        assertThrows(DaoException.class, () -> {
            publisherDao.getById(publisherCreated.getPubId());
        });
    }

    /**
     * testing if throws Exception of delete method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void deleteObjectByIdAfterTheTableOfPublisherWasDroppedThrowException() throws Exception {
        Publisher publisherCreated = publisherDao.create(PUBLISHER);
        dropTablePublisherMethod();
        assertThrows(Exception.class, () -> {
            publisherDao.getById(publisherCreated.getPubId());
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
        publisherDao.create(PUBLISHER);
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
        publisherDao.create(PUBLISHER);
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
    void updateDataExecutedAndAllFieldsOfThePublisherRecievedAndCheckedReturnTrue() throws Exception {
        publisherDao.create(PUBLISHER);
        Publisher publisherUpdated = publisherDao.update(PUBLISHER_UPDATE);
        Publisher actual = publisherDao.getById(publisherUpdated.getPubId()).orElse(null);
        assertAll("actual",
                () -> assertEquals(publisherUpdated.getPubId(), actual.getPubId()),
                () -> assertEquals(publisherUpdated.getAddress(), actual.getAddress()),
                () -> assertEquals(publisherUpdated.getCountry(), actual.getCountry()),
                () -> assertEquals(publisherUpdated.getName(), actual.getName()),
                () -> assertEquals(publisherUpdated.getTelephone(), actual.getTelephone())
        );
    }

    /**
     * testing if throws DaoException of update method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void updateObjectAfterTheTableOfPublisherWasDroppedThrowDaoException() throws Exception {
        publisherDao.create(PUBLISHER);
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
        publisherDao.create(PUBLISHER);
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