package com.ra.advertisement.dao;

import com.ra.advertisement.config.AdvertisementConfiguration;
import com.ra.advertisement.entity.Publisher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.ContextConfiguration;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContextConfiguration(classes = {AdvertisementConfiguration.class})
public class PublisherMockTest {
    private static JdbcTemplate mockjdbcTemplate;
    private static PublisherAdvertisementDaoImpl publisherDao;
    private static final String GET_PUB_BY_ID = "SELECT * FROM PUBLISHER WHERE PUB_ID=?";
    private Publisher publisher;
    private Publisher publisherNoId;
    private Publisher publisherUpdated;
    private KeyHolder mockkeyHolder = new GeneratedKeyHolder();
    private PreparedStatement mockStatement;

    @BeforeAll
    public static void init() {
        mockjdbcTemplate = Mockito.mock(JdbcTemplate.class);
        publisherDao = new PublisherAdvertisementDaoImpl(mockjdbcTemplate);
    }

    @BeforeEach
    public void reInitAdvertisementDao() {
        mockkeyHolder = Mockito.mock(KeyHolder.class);
        mockStatement = Mockito.mock(PreparedStatement.class);
        publisher = new Publisher(1L, "Advert ltd", "Kyiv", "25-17-84", "Ukraine");
        publisherNoId = new Publisher("Advert ltd", "Kyiv", "25-17-84", "Ukraine");
        publisherUpdated = new Publisher(1L, "Advert ltd Update", "Kyiv Update",
                "25-17-84 Update", "Ukraine Update");
    }

    /**
     * Testing method addPPublisher when result true.
     */
    @Test
    public void addPublisherExecuteSuccessfuldReturnTrue() {
        Mockito.when(mockjdbcTemplate.update(Mockito.any(PreparedStatementCreator.class), Mockito.any(KeyHolder.class))).thenReturn(1);
        Mockito.when(mockkeyHolder.getKey()).thenReturn(1L);
        Publisher publisherCreated = publisherDao.create(publisherNoId);
        publisherCreated.setPubId((Long)mockkeyHolder.getKey());
        Assertions.assertAll("publisherCreated",
                () -> Assertions.assertEquals(publisherCreated.getPubId(), publisher.getPubId()),
                () -> Assertions.assertEquals(publisherCreated.getName(), publisher.getName()),
                () -> Assertions.assertEquals(publisherCreated.getAddress(), publisher.getAddress()),
                () -> Assertions.assertEquals(publisherCreated.getCountry(), publisher.getCountry()),
                () -> Assertions.assertEquals(publisherCreated.getTelephone(), publisher.getTelephone()));
    }

    /**
     * Testing method addAPublisher when we don't get id of created entity.
     */
    @Test
    public void addPublisherAndDontGetGeneratedIdReturnTrue() {
        Mockito.when(mockjdbcTemplate.update(Mockito.any(PreparedStatementCreator.class), Mockito.any(KeyHolder.class))).thenReturn(1);
        Mockito.when(mockkeyHolder.getKey()).thenReturn(null);
        publisherDao.create(publisherNoId);
        Assertions.assertTrue(mockkeyHolder.getKey()==null);
    }

    /**
     * Testing method deletePublisher when result true.
     */
    @Test
    public void deletePublisherSuccessfulReturnTrue() {
        final String DELETE_PUBLISHER = "DELETE FROM PUBLISHER WHERE PUB_ID=?";
        Mockito.when(mockjdbcTemplate.update(DELETE_PUBLISHER, publisher.getPubId())).thenReturn(1);
        Integer result = publisherDao.delete(publisher);
        Assertions.assertTrue(result == 1);
    }

    /**
     * Testing method updatePublisher when result true.
     */
    @Test
    public void updatePublisherSuccessfulReturnTrue() {
        int resultFromDB = 0;
        final String UPDATE_PUBLISHER = "update PUBLISHER set NAME = ?, ADDRESS= ?, TELEPHONE = ?"
                + ", COUNTRY = ? where PUB_ID = ?";
        Mockito.when(mockjdbcTemplate.update(Mockito.eq(UPDATE_PUBLISHER), Mockito.any(PreparedStatement.class))).thenReturn(resultFromDB=1);
        Mockito.doAnswer(invocation -> {
            ((PreparedStatementSetter) invocation.getArguments()[1]).setValues(mockStatement);
            return null;
        }).when(mockjdbcTemplate).update(Mockito.eq(UPDATE_PUBLISHER), Mockito.any(PreparedStatementSetter.class));
        Publisher updated = publisherDao.update(publisherUpdated);
        Assertions.assertEquals(1, resultFromDB);
        Assertions.assertAll("updated",
                () -> Assertions.assertEquals(updated.getPubId(), publisherUpdated.getPubId()),
                () -> Assertions.assertEquals(updated.getName(), publisherUpdated.getName()),
                () -> Assertions.assertEquals(updated.getAddress(), publisherUpdated.getAddress()),
                () -> Assertions.assertEquals(updated.getCountry(), publisherUpdated.getCountry()),
                () -> Assertions.assertEquals(updated.getTelephone(), publisherUpdated.getTelephone()));
    }

    /**
     * Testing method getAllPublishers when result true.
     */
    @Test
    public void getAllPublishersExecutedReturnTrue() {
        final String GET_ALL_PUBLISHERS = "SELECT * FROM PUBLISHER";
        List listFromQueryForList = createListOfMap();
        Mockito.when(mockjdbcTemplate.queryForList(Mockito.eq(GET_ALL_PUBLISHERS))).thenReturn(listFromQueryForList);
        List<Publisher> result = publisherDao.getAll();
        Assertions.assertTrue(!result.isEmpty());
    }

    /**
     * Testing method getPublisherById when Device was provided result true.
     */
    @Test
    public void publisherGetByIdReturnPublisherReturnTrue() {
        Mockito.when(mockjdbcTemplate.queryForObject(Mockito.eq(GET_PUB_BY_ID), Mockito.any(RowMapper.class), Mockito.any(Long.class)))
                .thenReturn(publisher);
        Publisher result = publisherDao.getById(publisher.getPubId());
        Assertions.assertAll("result",
                () -> Assertions.assertEquals(result.getPubId(), publisher.getPubId()),
                () -> Assertions.assertEquals(result.getName(), publisher.getName()),
                () -> Assertions.assertEquals(result.getAddress(), publisher.getAddress()),
                () -> Assertions.assertEquals(result.getCountry(), publisher.getCountry()),
                () -> Assertions.assertEquals(result.getTelephone(), publisher.getTelephone()));
    }

    /**
     * Testing method mapListFromQueryForList.
     */
    @Test
    public void mapListFromQueryForListReturnTrue() {
        List<Map<String, Object>> listToMapFrom = createListOfMap();
        List<Publisher> result = publisherDao.mapListFromQueryForList(listToMapFrom);
        Publisher publisherResult = result.get(0);
        Assertions.assertTrue(!result.isEmpty());
        Assertions.assertAll("publisherResult",
                () -> Assertions.assertEquals(publisherResult.getPubId(), publisher.getPubId()),
                () -> Assertions.assertEquals(publisherResult.getName(), publisher.getName()),
                () -> Assertions.assertEquals(publisherResult.getAddress(), publisher.getAddress()),
                () -> Assertions.assertEquals(publisherResult.getCountry(), publisher.getCountry()),
                () -> Assertions.assertEquals(publisherResult.getTelephone(), publisher.getTelephone()));
    }

    /**
     * this method create List of MapStringObject
     *
     * @return list
     */
    private List createListOfMap() {
        List<Map<String, Object>> listToMapFrom = new ArrayList<>();
        Map<String, Object> devId = new HashMap<>();
        devId.put("PUB_ID", publisher.getPubId());
        devId.put("NAME", publisher.getName());
        devId.put("ADDRESS", publisher.getAddress());
        devId.put("TELEPHONE", publisher.getTelephone());
        devId.put("COUNTRY", publisher.getCountry());
        listToMapFrom.add(devId);
        return listToMapFrom;
    }
}