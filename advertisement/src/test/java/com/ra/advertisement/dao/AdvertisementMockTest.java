package com.ra.advertisement.dao;

import com.ra.advertisement.config.AdvertisementConfiguration;
import com.ra.advertisement.entity.Advertisement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AdvertisementConfiguration.class, AdvertisementAdvertisementDaoImpl.class})
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
        mockjdbcTemplate = mock(JdbcTemplate.class);
        advertisementDao = new AdvertisementAdvertisementDaoImpl(mockjdbcTemplate);
    }

    @BeforeEach
    public void reInitAdvertisementDao() throws SQLException {
        mockkeyHolder = mock(KeyHolder.class);
        mockStatement = mock(PreparedStatement.class);
        advertisement = new Advertisement(1L, "Welcome advert", "Welcome to Ukraine",
                "url", "Ukrainian");
        advertisementNoId = new Advertisement("Welcome advert", "Welcome to Ukraine",
                "url", "Ukrainian");
        advertisementUpdated = new Advertisement(1l, "Update", "Update",
                "url", "Ukrainian");
        when(mockjdbcTemplate.queryForObject(Mockito.eq(GET_ADVERT_BY_ID), Mockito.any(RowMapper.class), Mockito.any(Long.class)))
                .thenReturn(advertisement);
    }

    /**
     * Testing method addAdvertisement when result true.
     */
    @Test
    public void addAdvertisementExecuteSuccessfuldReturnTrue() throws SQLException {
        when(mockjdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class))).thenReturn(1);
        when(mockkeyHolder.getKey()).thenReturn(1L);
        Advertisement advertisementCreated = advertisementDao.create(advertisementNoId);
        assertAll("advertisementCreated",
                () -> assertEquals(advertisementCreated.getAdId(), advertisement.getAdId()),
                () -> assertEquals(advertisementCreated.getTitle(), advertisement.getTitle()),
                () -> assertEquals(advertisementCreated.getContext(), advertisement.getContext()),
                () -> assertEquals(advertisementCreated.getImageUrl(), advertisement.getImageUrl()),
                () -> assertEquals(advertisementCreated.getLanguage(), advertisement.getLanguage()));
    }

    /**
     * Testing method addAdvertisement when we don't get id of created entity.
     *
     * @throws SQLException exception.
     */
    @Test
    public void addAdvertisementAndDontGetGeneratedIdReturnTrue() {
        when(mockjdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class))).thenReturn(1);
        when(mockkeyHolder.getKey()).thenReturn(0L);
        Advertisement advertisementCreated = advertisementDao.create(advertisementNoId);
        assertEquals(advertisementCreated.getAdId(), advertisement.getAdId());
    }

    /**
     * Testing method deleteAdvertisement when result true.
     */
    @Test
    public void deleteAdvertisementSuccessfulReturnTrue() {
        final String DELETE_ADVERT = "DELETE FROM ADVERTISEMENT WHERE AD_ID=?";
        when(mockjdbcTemplate.update(DELETE_ADVERT, advertisement.getAdId())).thenReturn(1);
        Integer result = advertisementDao.delete(advertisement);
        Assertions.assertTrue(result == 1);
    }

    /**
     * Testing method updateAdvertisement when result true.
     */
    @Test
    public void updateAdvertisementSuccessfulReturnTrue() {
        final String UPDATE_ADVERT = "update ADVERTISEMENT set TITLE = ?, CONTEXT= ?, IMAGE_URL = ?,"
                + " LANGUAGE = ? where AD_ID = ?";
        when(mockjdbcTemplate.update(eq(UPDATE_ADVERT), Mockito.any(PreparedStatement.class))).thenReturn(1);
        when(mockjdbcTemplate.queryForObject(Mockito.eq(GET_ADVERT_BY_ID), Mockito.any(RowMapper.class),
                Mockito.any(Long.class))).thenReturn(advertisementUpdated);
        Mockito.doAnswer(invocation -> {
            ((PreparedStatementSetter) invocation.getArguments()[1]).setValues(mockStatement);
            return null;
        }).when(mockjdbcTemplate).update(Mockito.eq(UPDATE_ADVERT), Mockito.any(PreparedStatementSetter.class));
        Advertisement result = advertisementDao.update(advertisement);
        assertAll("result",
                () -> assertEquals(result.getAdId(), advertisementUpdated.getAdId()),
                () -> assertEquals(result.getTitle(), advertisementUpdated.getTitle()),
                () -> assertEquals(result.getContext(), advertisementUpdated.getContext()),
                () -> assertEquals(result.getImageUrl(), advertisementUpdated.getImageUrl()),
                () -> assertEquals(result.getLanguage(), advertisementUpdated.getLanguage()));
    }

    /**
     * Testing method getAllAdvertisement when result true.
     */
    @Test
    public void getAllAdvertisementExecutedReturnTrue() {
        final String GET_ALL_ADVERTS = "SELECT * FROM ADVERTISEMENT";
        List listFromQueryForList = createListOfMap();
        Mockito.when(mockjdbcTemplate.queryForList(eq(GET_ALL_ADVERTS))).thenReturn(listFromQueryForList);
        List<Advertisement> result = advertisementDao.getAll();
        assertTrue(!result.isEmpty());
    }

    /**
     * Testing method getAdvertisementById when Advertisement was provided result true.
     */
    @Test
    public void advertisementGetByIdReturnAdvertisementReturnTrue() {
        Advertisement result = advertisementDao.getById(advertisement.getAdId());
        assertAll("result",
                () -> assertEquals(result.getAdId(), advertisement.getAdId()),
                () -> assertEquals(result.getTitle(), advertisement.getTitle()),
                () -> assertEquals(result.getContext(), advertisement.getContext()),
                () -> assertEquals(result.getImageUrl(), advertisement.getImageUrl()),
                () -> assertEquals(result.getLanguage(), advertisement.getLanguage()));
    }

    /**
     * Testing method mapListFromQueryForList.
     */
    @Test
    public void mapListFromQueryForListReturnTrue() {
        List<Map<String, Object>> listToMapFrom = createListOfMap();
        List<Advertisement> result = advertisementDao.mapListFromQueryForList(listToMapFrom);
        Advertisement advertisementresult = result.get(0);
        assertTrue(!result.isEmpty());
        assertAll("advertisementresult",
                () -> assertEquals(advertisementresult.getAdId(), advertisement.getAdId()),
                () -> assertEquals(advertisementresult.getTitle(), advertisement.getTitle()),
                () -> assertEquals(advertisementresult.getContext(), advertisement.getContext()),
                () -> assertEquals(advertisementresult.getImageUrl(), advertisement.getImageUrl()),
                () -> assertEquals(advertisementresult.getLanguage(), advertisement.getLanguage()));
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