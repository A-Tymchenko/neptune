package com.ra.advertisement.dao;

import com.ra.advertisement.config.AdvertisementConfiguration;
import com.ra.advertisement.entity.Advertisement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.ContextConfiguration;

import java.sql.PreparedStatement;
import java.util.*;

@ContextConfiguration(classes = {AdvertisementConfiguration.class})
public class AdvertisementMockTest {
    private static JdbcTemplate mockjdbcTemplate;
    private static AdvertisementAdvertisementDaoImpl advertisementDao;
    private static final String GET_ADVERT_BY_ID = "SELECT * FROM ADVERTISEMENT WHERE AD_ID=?";
    private Advertisement advertisement;
    private Advertisement advertisementNoId;
    private Advertisement advertisementUpdated;
    private KeyHolder mockkeyHolder = new GeneratedKeyHolder();
    private PreparedStatement mockStatement;

    @BeforeAll
    public static void init() {
        mockjdbcTemplate = Mockito.mock(JdbcTemplate.class);
        advertisementDao = new AdvertisementAdvertisementDaoImpl(mockjdbcTemplate);
    }

    @BeforeEach
    public void reInitAdvertisementDao() {
        mockkeyHolder = Mockito.mock(KeyHolder.class);
        mockStatement = Mockito.mock(PreparedStatement.class);
        advertisement = new Advertisement(1L, "Welcome advert", "Welcome to Ukraine",
                "url", "Ukrainian");
        advertisementNoId = new Advertisement("Welcome advert", "Welcome to Ukraine",
                "url", "Ukrainian");
        advertisementUpdated = new Advertisement(1l, "Update", "Update",
                "url", "Ukrainian");


    }

    /**
     * Testing method addAdvertisement when result true.
     */
    @Test
    public void addAdvertisementExecuteSuccessfuldReturnTrue() {
        Mockito.when(mockjdbcTemplate.update(Mockito.any(PreparedStatementCreator.class), Mockito.any(KeyHolder.class))).thenReturn(1);
        Mockito.when(mockkeyHolder.getKey()).thenReturn(1L);
        Advertisement advertisementCreated = advertisementDao.create(advertisementNoId);
        advertisementCreated.setAdId((Long) (mockkeyHolder.getKey()));
        Assertions.assertAll("advertisementCreated",
                () -> Assertions.assertEquals(advertisementCreated.getAdId(), advertisement.getAdId()),
                () -> Assertions.assertEquals(advertisementCreated.getTitle(), advertisement.getTitle()),
                () -> Assertions.assertEquals(advertisementCreated.getContext(), advertisement.getContext()),
                () -> Assertions.assertEquals(advertisementCreated.getImageUrl(), advertisement.getImageUrl()),
                () -> Assertions.assertEquals(advertisementCreated.getLanguage(), advertisement.getLanguage()));
    }

    /**
     * Testing method addAdvertisement when we don't get id of created entity.
     */
    @Test
    public void addAdvertisementAndDontGetGeneratedIdReturnTrue() {
        Mockito.when(mockjdbcTemplate.update(Mockito.any(PreparedStatementCreator.class), Mockito.any(KeyHolder.class))).thenReturn(1);
        Mockito.when(mockkeyHolder.getKey()).thenReturn(null);
        advertisementDao.create(advertisementNoId);
        Assertions.assertTrue(mockkeyHolder.getKey() == null);
    }

    /**
     * Testing method deleteAdvertisement when result true.
     */
    @Test
    public void deleteAdvertisementSuccessfulReturnTrue() {
        final String DELETE_ADVERT = "DELETE FROM ADVERTISEMENT WHERE AD_ID=?";
        Mockito.when(mockjdbcTemplate.update(DELETE_ADVERT, advertisement.getAdId())).thenReturn(1);
        Integer result = advertisementDao.delete(advertisement);
        Assertions.assertTrue(result == 1);
    }

    /**
     * Testing method updateAdvertisement when result true.
     */
    @Test
    public void updateAdvertisementSuccessfulReturnTrue() {
        int resultFromDB = 0;
        final String UPDATE_ADVERT = "update ADVERTISEMENT set TITLE = ?, CONTEXT= ?, IMAGE_URL = ?,"
                + " LANGUAGE = ? where AD_ID = ?";
        Mockito.when(mockjdbcTemplate.update(Mockito.eq(UPDATE_ADVERT), Mockito.any(PreparedStatement.class))).thenReturn(resultFromDB = 1);
        Mockito.doAnswer(invocation -> {
            ((PreparedStatementSetter) invocation.getArguments()[1]).setValues(mockStatement);
            return null;
        }).when(mockjdbcTemplate).update(Mockito.eq(UPDATE_ADVERT), Mockito.any(PreparedStatementSetter.class));
        Advertisement updated = advertisementDao.update(advertisementUpdated);
        Assertions.assertEquals(1, resultFromDB);
        Assertions.assertAll("updated",
                () -> Assertions.assertEquals(updated.getAdId(), advertisementUpdated.getAdId()),
                () -> Assertions.assertEquals(updated.getTitle(), advertisementUpdated.getTitle()),
                () -> Assertions.assertEquals(updated.getContext(), advertisementUpdated.getContext()),
                () -> Assertions.assertEquals(updated.getImageUrl(), advertisementUpdated.getImageUrl()),
                () -> Assertions.assertEquals(updated.getLanguage(), advertisementUpdated.getLanguage()));
    }

    /**
     * Testing method getAllAdvertisement when result true.
     */
    @Test
    public void getAllAdvertisementExecutedReturnTrue() {
        final String GET_ALL_ADVERTS = "SELECT * FROM ADVERTISEMENT";
        List listFromQueryForList = createListOfMap();
        Mockito.when(mockjdbcTemplate.queryForList(Mockito.eq(GET_ALL_ADVERTS))).thenReturn(listFromQueryForList);
        List<Advertisement> result = advertisementDao.getAll();
        Assertions.assertTrue(!result.isEmpty());
    }

    /**
     * Testing method getAdvertisementById when Advertisement was provided result true.
     */
    @Test
    public void advertisementGetByIdReturnAdvertisementReturnTrue() {
        Mockito.when(mockjdbcTemplate.queryForObject(Mockito.eq(GET_ADVERT_BY_ID), Mockito.any(RowMapper.class), Mockito.any(Long.class)))
                .thenReturn(advertisement);
        Advertisement result = advertisementDao.getById(advertisement.getAdId());
        Assertions.assertAll("result",
                () -> Assertions.assertEquals(result.getAdId(), advertisement.getAdId()),
                () -> Assertions.assertEquals(result.getTitle(), advertisement.getTitle()),
                () -> Assertions.assertEquals(result.getContext(), advertisement.getContext()),
                () -> Assertions.assertEquals(result.getImageUrl(), advertisement.getImageUrl()),
                () -> Assertions.assertEquals(result.getLanguage(), advertisement.getLanguage()));
    }

    /**
     * Testing method mapListFromQueryForList.
     */
    @Test
    public void mapListFromQueryForListReturnTrue() {
        List<Map<String, Object>> listToMapFrom = createListOfMap();
        List<Advertisement> result = advertisementDao.mapListFromQueryForList(listToMapFrom);
        Advertisement advertisementresult = result.get(0);
        Assertions.assertTrue(!result.isEmpty());
        Assertions.assertAll("advertisementresult",
                () -> Assertions.assertEquals(advertisementresult.getAdId(), advertisement.getAdId()),
                () -> Assertions.assertEquals(advertisementresult.getTitle(), advertisement.getTitle()),
                () -> Assertions.assertEquals(advertisementresult.getContext(), advertisement.getContext()),
                () -> Assertions.assertEquals(advertisementresult.getImageUrl(), advertisement.getImageUrl()),
                () -> Assertions.assertEquals(advertisementresult.getLanguage(), advertisement.getLanguage()));
    }

    /**
     * this method create List of MapStringObject
     *
     * @return list
     */
    private List createListOfMap() {
        List<Map<String, Object>> listToMapFrom = new ArrayList<>();
        Map<String, Object> advId = new HashMap<>();
        advId.put("AD_ID", advertisement.getAdId());
        advId.put("TITLE", advertisement.getTitle());
        advId.put("LANGUAGE", advertisement.getLanguage());
        advId.put("CONTEXT", advertisement.getContext());
        advId.put("IMAGE_URL", advertisement.getImageUrl());
        listToMapFrom.add(advId);
        return listToMapFrom;
    }
}