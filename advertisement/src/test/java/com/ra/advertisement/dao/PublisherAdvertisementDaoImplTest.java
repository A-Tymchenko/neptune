package com.ra.advertisement.dao;

import com.ra.advertisement.connection.ConnectionFactory;
import com.ra.advertisement.dao.exceptions.DaoException;
import com.ra.advertisement.model.entities.Publisher;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PublisherAdvertisementDaoImplTest {

    private static final Publisher publisher = new Publisher(1L, "Advert ltd",
            "Kyiv", "25-17-84", "Ukraine");
    private ConnectionFactory connectionFactory;
    AdvertisementDao<Publisher> publisherDao;

    @BeforeEach
    void setUp() throws Exception {
        connectionFactory = ConnectionFactory.getInstance();
        publisherDao = new PublisherAdvertisementDaoImpl(connectionFactory);
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\publisher_db.sql"));
    }

    /**
     * testing successful result of create method which saves info regarding Publisher into DB
     *
     * @throws Exception
     */
    @Test
    void insertValidDataReturnTrue() throws Exception {
        Integer result = publisherDao.create(publisher);
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
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_publisher.sql"));
        assertThrows(DaoException.class, () -> {
            publisherDao.create(publisher);
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
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_publisher.sql"));
        assertThrows(Exception.class, () -> {
            publisherDao.create(publisher);
        });
    }

    /**
     * testing successful result of getById method which gets info regarding Publisher from DB
     *
     * @throws Exception
     */
    @Test
    void getObjectByIdWhenExecutedReturnTrue() throws Exception {
        publisherDao.create(publisher);
        Publisher actual = publisherDao.getById(publisher.getPubId()).orElse(null);
        assertAll("actual",
                () -> assertEquals(publisher.getPubId(), actual.getPubId()),
                () -> assertEquals(publisher.getAddress(), actual.getAddress()),
                () -> assertEquals(publisher.getCountry(), actual.getCountry()),
                () -> assertEquals(publisher.getName(), actual.getName()),
                () -> assertEquals(publisher.getTelephone(), actual.getTelephone())
        );
    }

    /**
     * testing result (null) of getById method which gets info regarding Publisher from DB when there no such id in DB
     *
     * @throws Exception
     */
    @Test
    void getObjectByIdWhenExecutedNullReturnTrue() throws Exception {
        Publisher actual = publisherDao.getById(publisher.getPubId()).orElse(null);
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
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_publisher.sql"));
        assertThrows(DaoException.class, () -> {
            publisherDao.getById(publisher.getPubId());
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
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_publisher.sql"));
        assertThrows(Exception.class, () -> {
            publisherDao.getById(publisher.getPubId());
        });
    }

    /**
     * testing successful result of delete method which delete info regarding Publisher from DB
     *
     * @throws Exception
     */
    @Test
    void deleteValidDataReturnTrue() throws Exception {
        publisherDao.create(publisher);
        Integer actual = publisherDao.delete(publisher.getPubId());
        assertEquals(Integer.valueOf(1), actual);
    }

    /**
     * testing if throws DaoException of delete method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void deleteObjectByIdWhenTableDroppedThrowDaoException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_publisher.sql"));
        assertThrows(DaoException.class, () -> {
            publisherDao.getById(publisher.getPubId());
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
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_publisher.sql"));
        assertThrows(Exception.class, () -> {
            publisherDao.getById(publisher.getPubId());
        });
    }

    /**
     * testing successful result of getAll method which gets info regarding all Publishers from DB
     *
     * @throws Exception
     */
    @Test
    void getAllObjectWhenExecutedAndListIsNotEmptyReturnTrue() throws Exception {
        publisherDao.create(publisher);
        boolean actual = publisherDao.getAll().isEmpty();
        assertEquals(Boolean.valueOf(false), actual);
    }

    /**
     * testing successful result of getAll method which gets info regarding all Publishers from DB
     *
     * @throws Exception
     */
    @Test
    void getAllObjectWhenExecutedAndGetListWithSizeTwoReturnTrue() throws Exception {
        publisherDao.create(publisher);
        publisherDao.create(publisher);
        Integer actual = publisherDao.getAll().size();
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
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_publisher.sql"));
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
    void getAllObjectWhenTableDroppedThrowException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_publisher.sql"));
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
        publisherDao.create(publisher);
        Publisher publisherUpdate = new Publisher(1L, "Advert ltd Update",
                "Kyiv Update", "25-17-84 Update", "Ukraine Update");
        Integer actual = publisherDao.update(publisherUpdate);
        assertEquals(Integer.valueOf(1), actual);
    }

    /**
     * testing successful result of update method which updates info regarding Publisher in DB
     *
     * @throws Exception
     */
    @Test
    void updateDataWhenExecutedAndGetResultReturnTrue() throws Exception {
        publisherDao.create(publisher);
        Publisher publisherUpdate = new Publisher(1L, "Advert ltd Update",
                "Kyiv Update", "25-17-84 Update", "Ukraine Update");
        publisherDao.update(publisherUpdate);
        Publisher actual = publisherDao.getById(publisherUpdate.getPubId()).orElse(null);
        assertAll("actual",
                () -> assertEquals(publisherUpdate.getPubId(), actual.getPubId()),
                () -> assertEquals(publisherUpdate.getAddress(), actual.getAddress()),
                () -> assertEquals(publisherUpdate.getCountry(), actual.getCountry()),
                () -> assertEquals(publisherUpdate.getName(), actual.getName()),
                () -> assertEquals(publisherUpdate.getTelephone(), actual.getTelephone())
        );
    }

    /**
     * testing if throws DaoException of update method in case of some troubles
     *
     * @throws Exception
     */
    @Test
    void updateObjectWhenTableDroppedThrowDaoException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        Publisher publisherUpdate = new Publisher(1L, "Advert ltd Update",
                "Kyiv Update", "25-17-84 Update", "Ukraine Update");
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_publisher.sql"));
        assertThrows(DaoException.class, () -> {
            publisherDao.update(publisherUpdate);
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
        Publisher publisherUpdate = new Publisher(1L, "Advert ltd Update",
                "Kyiv Update", "25-17-84 Update", "Ukraine Update");
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_publisher.sql"));
        assertThrows(Exception.class, () -> {
            publisherDao.update(publisherUpdate);
        });
    }
}