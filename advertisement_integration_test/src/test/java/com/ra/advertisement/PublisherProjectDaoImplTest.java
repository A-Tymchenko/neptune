package com.ra.advertisement;

import com.ra.advertisement.config.DataBaseConfiguration;
import com.ra.advertisement.dao.PublisherProjectDaoImpl;
import com.ra.advertisement.entity.Publisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DataBaseConfiguration.class})
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/advertisement_db.sql")
@WebAppConfiguration
class PublisherProjectDaoImplTest {

    @Autowired
    private PublisherProjectDaoImpl publisherDao;

    private static final Publisher PUBLISHER = new Publisher("Advert ltd",
            "Kyiv", "25-17-84", "Ukraine");
    private static final Publisher PUBLISHER_UPDATE = new Publisher(1L, "Advert ltd Update",
            "Kyiv Update", "25-17-84 Update", "Ukraine Update");

    /**
     * testing successful result of create method which save info regarding Publisher into DB
     */
    @Test
    void insertValidDataIntoDbAndGetItsFromThereWithGeneratedIddReturnTrue() {
        publisherDao.create(PUBLISHER);
        assertTrue(PUBLISHER.getPubId() != null);
    }

    /**
     * testing successful result of getById method which gets info regarding Publisher from DB
     */
    @Test
    void getObjectByIdExecutedReturnTrue() {
        Publisher publisherCreated = publisherDao.create(PUBLISHER);
        Publisher actual = publisherDao.getById(publisherCreated.getPubId());
        assertAll("actual",
                () -> assertEquals(publisherCreated.getPubId(), actual.getPubId()),
                () -> assertEquals(publisherCreated.getName(), actual.getName()),
                () -> assertEquals(publisherCreated.getAddress(), actual.getAddress()),
                () -> assertEquals(publisherCreated.getCountry(), actual.getCountry()),
                () -> assertEquals(publisherCreated.getTelephone(), actual.getTelephone())
        );
    }

    /**
     * testing successful result of delete method which delete info regarding Publisher from DB
     */
    @Test
    void deleteValidDataExecutedReturnTrue() {
        Publisher publisherCreated = publisherDao.create(PUBLISHER);
        Integer actual = publisherDao.delete(publisherCreated);
        assertEquals(Integer.valueOf(1), actual);
    }

    /**
     * testing successful result of getAll method which gets info regarding all Publishers from DB
     */
    @Test
    void getAllObjectExecutedAndListIsNotEmptyReturnTrue() {
        publisherDao.create(PUBLISHER);
        boolean actual = publisherDao.getAll().isEmpty();
        assertEquals(Boolean.valueOf(false), actual);
    }

    /**
     * testing successful result of update method which updates info regarding Publisher in DB
     */
    @Test
    void updateDataExecutedAndAllFieldsOfProviderRecievedAndCheckedReturnTrue() {
        publisherDao.create(PUBLISHER);
        Publisher publisherUpdated = publisherDao.update(PUBLISHER_UPDATE);
        Publisher actual = publisherDao.getById(publisherUpdated.getPubId());
        assertAll("actual",
                () -> assertEquals(publisherUpdated.getPubId(), actual.getPubId()),
                () -> assertEquals(publisherUpdated.getName(), actual.getName()),
                () -> assertEquals(publisherUpdated.getAddress(), actual.getAddress()),
                () -> assertEquals(publisherUpdated.getCountry(), actual.getCountry()),
                () -> assertEquals(publisherUpdated.getTelephone(), actual.getTelephone())
        );
    }
}