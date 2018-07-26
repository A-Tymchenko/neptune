package com.ra.advertisement;

import com.ra.advertisement.config.AdvertisementConfiguration;
import com.ra.advertisement.dao.AdvertisementDao;
import com.ra.advertisement.dao.PublisherAdvertisementDaoImpl;
import com.ra.advertisement.entity.Publisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PublisherAdvertisementDaoImplTest {
    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AdvertisementConfiguration.class);
    private AdvertisementDao<Publisher> publisherDao = (PublisherAdvertisementDaoImpl) context.getBean("publisherDao");
    private static final Publisher PUBLISHER = new Publisher("Advert ltd",
            "Kyiv", "25-17-84", "Ukraine");
    private static final Publisher PUBLISHER_UPDATE = new Publisher(1L, "Advert ltd Update",
            "Kyiv Update", "25-17-84 Update", "Ukraine Update");

    @BeforeEach
    public void setUp() {
        ResourceDatabasePopulator tables = new ResourceDatabasePopulator();
        tables.addScript(new ClassPathResource("/advertisement_db.sql"));
        DatabasePopulatorUtils.execute(tables, (DataSource) context.getBean("simpleDataSource"));
    }

    /**
     * testing successful result of create method which save info regarding Publisher into DB
     *
     * @throws Exception
     */
    @Test
    void insertValidDataIntoDbAndGetItsFromThereWithGeneratedIddReturnTrue() throws Exception {
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
     * testing successful result of getById method which gets info regarding Publisher from DB
     *
     * @throws Exception
     */
    @Test
    void getObjectByIdExecutedReturnTrue() throws Exception {
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
     * testing successful result of getAll method which gets info regarding all Publishers from DB
     *
     * @throws Exception
     */
    @Test
    void getAllObjectExecutedAndListIsNotEmptyReturnTrue() throws Exception {
        publisherDao.create(PUBLISHER);
        boolean actual = publisherDao.getAll().isEmpty();
        assertEquals(Boolean.valueOf(false), actual);
    }

    /**
     * testing successful result of update method which updates info regarding Publisher in DB
     *
     * @throws Exception
     */
    @Test
    void updateDataExecutedAndAllFieldsOfProviderRecievedAndCheckedReturnTrue() throws Exception {
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