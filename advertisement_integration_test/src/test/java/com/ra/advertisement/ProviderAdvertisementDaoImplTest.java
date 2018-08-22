package com.ra.advertisement;

import com.ra.advertisement.config.AdvertisementConfiguration;
import com.ra.advertisement.dao.ProviderAdvertisementDaoImpl;
import com.ra.advertisement.entity.Provider;
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
class ProviderAdvertisementDaoImplTest {

    @Autowired
    private ProviderAdvertisementDaoImpl providerDao;

    private static final Provider PROVIDER = new Provider("Coca Cola", "Lviv",
            "22-45-18", "Ukraine");
    private static final Provider PROVIDER_UPDATE = new Provider(1L, "Coca Cola Update",
            "LvivUpdate", "22-45-18Update", "UkraineUpdate");

    /**
     * testing successful result of create method which save info regarding Provider into DB
     */
    @Test
    void insertValidDataIntoDbAndGetItsFromThereWithGeneratedIddReturnTrue() {
        providerDao.create(PROVIDER);
        assertTrue(PROVIDER.getProvId() != null);
    }

    /**
     * testing successful result of getById method which gets info regarding Provider from DB
     */
    @Test
    void getObjectByIdExecutedReturnTrue() {
        Provider providerCreated = providerDao.create(PROVIDER);
        Provider actual = providerDao.getById(providerCreated.getProvId());
        assertAll("actual",
                () -> assertEquals(providerCreated.getProvId(), actual.getProvId()),
                () -> assertEquals(providerCreated.getName(), actual.getName()),
                () -> assertEquals(providerCreated.getAddress(), actual.getAddress()),
                () -> assertEquals(providerCreated.getCountry(), actual.getCountry()),
                () -> assertEquals(providerCreated.getTelephone(), actual.getTelephone())
        );
    }

    /**
     * testing successful result of delete method which delete info regarding Provider from DB
     */
    @Test
    void deleteValidDataExecutedReturnTrue() {
        Provider providerCreated = providerDao.create(PROVIDER);
        Integer actual = providerDao.delete(providerCreated);
        assertEquals(Integer.valueOf(1), actual);
    }

    /**
     * testing successful result of getAll method which gets info regarding all Providers from DB
     */
    @Test
    void getAllObjectExecutedAndListIsNotEmptyReturnTrue() {
        providerDao.create(PROVIDER);
        boolean actual = providerDao.getAll().isEmpty();
        assertEquals(Boolean.valueOf(false), actual);
    }

    /**
     * testing successful result of update method which updates info regarding Provider in DB
     */
    @Test
    void updateDataExecutedAndAllFieldsOfProviderRecievedAndCheckedReturnTrue() {
        providerDao.create(PROVIDER);
        Provider providerUpdated = providerDao.update(PROVIDER_UPDATE);
        Provider actual = providerDao.getById(providerUpdated.getProvId());
        assertAll("actual",
                () -> assertEquals(providerUpdated.getProvId(), actual.getProvId()),
                () -> assertEquals(providerUpdated.getName(), actual.getName()),
                () -> assertEquals(providerUpdated.getAddress(), actual.getAddress()),
                () -> assertEquals(providerUpdated.getCountry(), actual.getCountry()),
                () -> assertEquals(providerUpdated.getTelephone(), actual.getTelephone())
        );
    }
}