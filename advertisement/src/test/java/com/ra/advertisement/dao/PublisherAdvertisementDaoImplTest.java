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

    @Test
    void insertValidDataReturnTrue() throws Exception {
        Integer result = publisherDao.create(publisher);
        assertEquals(Integer.valueOf(1), result);
    }

    @Test
    void insertWhenTableDroppedThrowDaoException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_publisher.sql"));
        assertThrows(DaoException.class, () -> {
            publisherDao.create(publisher);
        });
    }

    @Test
    void insertWhenTableDroppedThrowException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_publisher.sql"));
        assertThrows(Exception.class, () -> {
            publisherDao.create(publisher);
        });
    }

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

    @Test
    void getObjectByIdWhenExecutedNullReturnTrue() throws Exception {
        Publisher actual = publisherDao.getById(publisher.getPubId()).orElse(null);
        assertEquals(null, actual);
    }

    @Test
    void getObjectByIdWhenTableDroppedThrowDaoException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_publisher.sql"));
        assertThrows(DaoException.class, () -> {
            publisherDao.getById(publisher.getPubId());
        });
    }

    @Test
    void getObjectByIdWhenTableDroppedThrowException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_publisher.sql"));
        assertThrows(Exception.class, () -> {
            publisherDao.getById(publisher.getPubId());
        });
    }

    @Test
    void deleteValidDataReturnTrue() throws Exception {
        publisherDao.create(publisher);
        Integer actual = publisherDao.delete(publisher.getPubId());
        assertEquals(Integer.valueOf(1), actual);
    }

    @Test
    void deleteObjectByIdWhenTableDroppedThrowDaoException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_publisher.sql"));
        assertThrows(DaoException.class, () -> {
            publisherDao.getById(publisher.getPubId());
        });
    }

    @Test
    void deleteObjectByIdWhenTableDroppedThrowException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_publisher.sql"));
        assertThrows(Exception.class, () -> {
            publisherDao.getById(publisher.getPubId());
        });
    }

    @Test
    void getAllObjectWhenExecutedAndListIsNotEmptyReturnTrue() throws Exception {
        publisherDao.create(publisher);
        boolean actual = publisherDao.getAll().isEmpty();
        assertEquals(Boolean.valueOf(false), actual);
    }

    @Test
    void getAllObjectWhenExecutedAndGetListWithSizeTwoReturnTrue() throws Exception {
        publisherDao.create(publisher);
        publisherDao.create(publisher);
        Integer actual = publisherDao.getAll().size();
        assertEquals(Integer.valueOf(2), actual);
    }

    @Test
    void getAllObjectWhenTableDroppedThrowDaoException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_publisher.sql"));
        assertThrows(DaoException.class, () -> {
            publisherDao.getAll();
        });
    }

    @Test
    void getAllObjectWhenTableDroppedThrowException() throws Exception {
        Connection connection = connectionFactory.getConnection();
        RunScript.execute(connection, new FileReader(".\\src\\test\\resources\\drop_table_publisher.sql"));
        assertThrows(Exception.class, () -> {
            publisherDao.getAll();
        });
    }

    @Test
    void updateDataWhenExecutedReturnTrue() throws Exception {
        publisherDao.create(publisher);
        Publisher publisherUpdate = new Publisher(1L, "Advert ltd Update",
                "Kyiv Update", "25-17-84 Update", "Ukraine Update");
        Integer actual = publisherDao.update(publisherUpdate);
        assertEquals(Integer.valueOf(1), actual);
    }

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