package com.ra.advertisement;

import com.ra.advertisement.config.AdvertisementConfiguration;
import com.ra.advertisement.dao.AdvertisementAdvertisementDaoImpl;
import com.ra.advertisement.dao.AdvertisementDao;
import com.ra.advertisement.entity.Advertisement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
     *
     * @throws Exception
     */
    @Test
    void insertValidDataIntoDbAndGetItsFromThereWithGeneratedIddReturnTrue() throws Exception {
        Advertisement advertisementCreated = advertisementDao.create(ADVERTISEMENT);
        Advertisement actual = advertisementDao.getById(advertisementCreated.getAdId());
        assertAll("actual",
                () -> assertEquals(advertisementCreated.getAdId(), actual.getAdId()),
                () -> assertEquals(advertisementCreated.getTitle(), actual.getTitle()),
                () -> assertEquals(advertisementCreated.getContext(), actual.getContext()),
                () -> assertEquals(advertisementCreated.getImageUrl(), actual.getImageUrl()),
                () -> assertEquals(advertisementCreated.getLanguage(), actual.getLanguage())
        );
    }

    /**
     * testing successful result of getById method which gets info regarding Advertisement from DB
     *
     * @throws Exception
     */
    @Test
    void getObjectByIdExecutedReturnTrue() throws Exception {
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
     * testing successful result of update method which updates info regarding Advertisement in DB
     *
     * @throws Exception
     */
    @Test
    void updateDataExecutedAndAllFieldsOfAdvertisementRecievedAndCheckedReturnTrue() throws Exception {
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