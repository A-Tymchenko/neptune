package com.ra.advertisement;

import com.ra.advertisement.config.AdvertisementConfiguration;
import com.ra.advertisement.dao.AdvertisementAdvertisementDaoImpl;
import com.ra.advertisement.dao.AdvertisementDao;
import com.ra.advertisement.entity.Advertisement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

class AdvertisementAdvertisementDaoImplTest {
    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AdvertisementConfiguration.class);
    private AdvertisementDao<Advertisement> advertisementDao = (AdvertisementAdvertisementDaoImpl) context.getBean("advertDao");
    private static final Advertisement ADVERTISEMENT = new Advertisement("AdvertoNE",
            "WELCOME TO UKRAINE", "iMAGE uRL", "English");
    private static final Advertisement ADVERTISEMENT_UPDATE = new Advertisement(1L, "AdvertoNEUpdate",
            "WELCOME TO UKRAINE UPDATE", "iMAGE uRL UPDATE", "English UPDATE");

    @BeforeEach
    public void setUp() {
        ResourceDatabasePopulator tables = new ResourceDatabasePopulator();
        tables.addScript(new ClassPathResource("/advertisement_db.sql"));
        DatabasePopulatorUtils.execute(tables, (DataSource) context.getBean("simpleDataSource"));
    }

    /**
     * testing successful result of create method which save info regarding Advertisement into DB
     */
    @Test
    void insertValidDataIntoDbAndGetItsFromThereWithGeneratedIddReturnTrue() {
        advertisementDao.create(ADVERTISEMENT);
        Assertions.assertTrue(ADVERTISEMENT.getAdId() != null);
    }

    /**
     * testing successful result of getById method which gets info regarding Advertisement from DB
     */
    @Test
    void getObjectByIdExecutedReturnTrue() {
        Advertisement advertisementCreated = advertisementDao.create(ADVERTISEMENT);
        Advertisement actual = advertisementDao.getById(advertisementCreated.getAdId());
        Assertions.assertAll("actual",
                () -> Assertions.assertEquals(advertisementCreated.getAdId(), actual.getAdId()),
                () -> Assertions.assertEquals(advertisementCreated.getTitle(), actual.getTitle()),
                () -> Assertions.assertEquals(advertisementCreated.getContext(), actual.getContext()),
                () -> Assertions.assertEquals(advertisementCreated.getImageUrl(), actual.getImageUrl()),
                () -> Assertions.assertEquals(advertisementCreated.getLanguage(), actual.getLanguage()));
    }

    /**
     * testing successful result of delete method which delete info regarding Advertisement from DB
     */
    @Test
    void deleteValidDataExecutedReturnTrue() {
        Advertisement advertisementCreated = advertisementDao.create(ADVERTISEMENT);
        Integer actual = advertisementDao.delete(advertisementCreated);
        Assertions.assertEquals(Integer.valueOf(1), actual);
    }

    /**
     * testing successful result of getAll method which gets info regarding all Advertisement from DB
     */
    @Test
    void getAllObjectExecutedAndListIsNotEmptyReturnTrue() {
        advertisementDao.create(ADVERTISEMENT);
        boolean actual = advertisementDao.getAll().isEmpty();
        Assertions.assertEquals(Boolean.valueOf(false), actual);
    }

    /**
     * testing successful result of update method which updates info regarding Advertisement in DB
     */
    @Test
    void updateDataExecutedAndAllFieldsOfAdvertisementRecievedAndCheckedReturnTrue() {
        advertisementDao.create(ADVERTISEMENT);
        Advertisement advertisementUpdated = advertisementDao.update(ADVERTISEMENT_UPDATE);
        Advertisement actual = advertisementDao.getById(advertisementUpdated.getAdId());
        Assertions.assertAll("actual",
                () -> Assertions.assertEquals(advertisementUpdated.getAdId(), actual.getAdId()),
                () -> Assertions.assertEquals(advertisementUpdated.getTitle(), actual.getTitle()),
                () -> Assertions.assertEquals(advertisementUpdated.getContext(), actual.getContext()),
                () -> Assertions.assertEquals(advertisementUpdated.getImageUrl(), actual.getImageUrl()),
                () -> Assertions.assertEquals(advertisementUpdated.getLanguage(), actual.getLanguage()));
    }
}