package com.ra.advertisement;

import com.ra.advertisement.config.AdvertisementConfiguration;
import com.ra.advertisement.dao.AdvertisementAdvertisementDaoImpl;
import com.ra.advertisement.entity.Advertisement;
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
@ContextConfiguration(classes = {AdvertisementConfiguration.class})
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/advertisement_db.sql")
@WebAppConfiguration
class AdvertisementAdvertisementDaoImplTest {

    @Autowired
    AdvertisementAdvertisementDaoImpl advertisementDao;

    private static final Advertisement ADVERTISEMENT = new Advertisement("AdvertoNE",
            "WELCOME TO UKRAINE", "iMAGE uRL", "English");
    private static final Advertisement ADVERTISEMENT_UPDATE = new Advertisement(1L, "AdvertoNEUpdate",
            "WELCOME TO UKRAINE UPDATE", "iMAGE uRL UPDATE", "English UPDATE");

    /**
     * testing successful result of create method which save info regarding Advertisement into DB
     */
    @Test
    void insertValidDataIntoDbAndGetItsFromThereWithGeneratedIddReturnTrue() {
        advertisementDao.create(ADVERTISEMENT);
        assertTrue(ADVERTISEMENT.getAdId() != null);
    }

    /**
     * testing successful result of getById method which gets info regarding Advertisement from DB
     */
    @Test
    void getObjectByIdExecutedReturnTrue() {
        Advertisement advertisementCreated = advertisementDao.create(ADVERTISEMENT);
        Advertisement actual = advertisementDao.getById(advertisementCreated.getAdId());
        assertAll("actual",
                () -> assertEquals(advertisementCreated.getAdId(), actual.getAdId()),
                () -> assertEquals(advertisementCreated.getTitle(), actual.getTitle()),
                () -> assertEquals(advertisementCreated.getContext(), actual.getContext()),
                () -> assertEquals(advertisementCreated.getImageUrl(), actual.getImageUrl()),
                () -> assertEquals(advertisementCreated.getLanguage(), actual.getLanguage()));
    }

    /**
     * testing successful result of delete method which delete info regarding Advertisement from DB
     */
    @Test
    void deleteValidDataExecutedReturnTrue() {
        Advertisement advertisementCreated = advertisementDao.create(ADVERTISEMENT);
        Integer actual = advertisementDao.delete(advertisementCreated);
        assertEquals(Integer.valueOf(1), actual);
    }

    /**
     * testing successful result of getAll method which gets info regarding all Advertisement from DB
     */
    @Test
    void getAllObjectExecutedAndListIsNotEmptyReturnTrue() {
        advertisementDao.create(ADVERTISEMENT);
        boolean actual = advertisementDao.getAll().isEmpty();
        assertEquals(Boolean.valueOf(false), actual);
    }

    /**
     * testing successful result of update method which updates info regarding Advertisement in DB
     */
    @Test
    void updateDataExecutedAndAllFieldsOfAdvertisementRecievedAndCheckedReturnTrue() {
        advertisementDao.create(ADVERTISEMENT);
        Advertisement advertisementUpdated = advertisementDao.update(ADVERTISEMENT_UPDATE);
        Advertisement actual = advertisementDao.getById(advertisementUpdated.getAdId());
        assertAll("actual",
                () -> assertEquals(advertisementUpdated.getAdId(), actual.getAdId()),
                () -> assertEquals(advertisementUpdated.getTitle(), actual.getTitle()),
                () -> assertEquals(advertisementUpdated.getContext(), actual.getContext()),
                () -> assertEquals(advertisementUpdated.getImageUrl(), actual.getImageUrl()),
                () -> assertEquals(advertisementUpdated.getLanguage(), actual.getLanguage()));
    }
}