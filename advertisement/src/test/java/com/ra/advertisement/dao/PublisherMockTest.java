package com.ra.advertisement.dao;

import com.ra.advertisement.entity.Publisher;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PublisherMockTest {
    private static JdbcTemplate mockjdbcTemplate;
    private static PublisherProjectDaoImpl publisherDao;
    private static final String GET_PUB_BY_ID = "SELECT * FROM PUBLISHER WHERE PUB_ID=?";
    private Publisher publisher;
    private Publisher publisherNoId;
    private Publisher publisherUpdated;
    private KeyHolder mockkeyHolder = new GeneratedKeyHolder();
    private PreparedStatement mockStatement;

    @BeforeAll
    public static void init() {
        mockjdbcTemplate = mock(JdbcTemplate.class);
        publisherDao = new PublisherProjectDaoImpl(mockjdbcTemplate);
    }

    @BeforeEach
    public void reInitAdvertisementDao() {
        mockkeyHolder = mock(KeyHolder.class);
        mockStatement = mock(PreparedStatement.class);
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
        when(mockjdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class))).thenReturn(1);
        when(mockkeyHolder.getKey()).thenReturn(1L);
        Publisher publisherCreated = publisherDao.create(publisherNoId);
        publisherCreated.setPubId((Long) mockkeyHolder.getKey());
        assertAll("publisherCreated",
                () -> assertEquals(publisherCreated.getPubId(), publisher.getPubId()),
                () -> assertEquals(publisherCreated.getName(), publisher.getName()),
                () -> assertEquals(publisherCreated.getAddress(), publisher.getAddress()),
                () -> assertEquals(publisherCreated.getCountry(), publisher.getCountry()),
                () -> assertEquals(publisherCreated.getTelephone(), publisher.getTelephone()));
    }

    /**
     * Testing method addAPublisher when we don't get id of created entity.
     */
    @Test
    public void addPublisherAndDontGetGeneratedIdReturnTrue() {
        when(mockjdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class))).thenReturn(1);
        when(mockkeyHolder.getKey()).thenReturn(null);
        publisherDao.create(publisherNoId);
        assertTrue(mockkeyHolder.getKey() == null);
    }

    /**
     * Testing method deletePublisher when result true.
     */
    @Test
    public void deletePublisherSuccessfulReturnTrue() {
        final String DELETE_PUBLISHER = "DELETE FROM PUBLISHER WHERE PUB_ID=?";
        when(mockjdbcTemplate.update(DELETE_PUBLISHER, publisher.getPubId())).thenReturn(1);
        Integer result = publisherDao.delete(publisher);
        assertTrue(result == 1);
    }

    /**
     * Testing method updatePublisher when result true.
     */
    @Test
    public void updatePublisherSuccessfulReturnTrue() {
        int resultFromDB = 0;
        final String UPDATE_PUBLISHER = "update PUBLISHER set NAME = ?, ADDRESS= ?, TELEPHONE = ?"
                + ", COUNTRY = ? where PUB_ID = ?";
        when(mockjdbcTemplate.update(eq(UPDATE_PUBLISHER), any(PreparedStatement.class))).thenReturn(resultFromDB = 1);
        doAnswer(invocation -> {
            ((PreparedStatementSetter) invocation.getArguments()[1]).setValues(mockStatement);
            return null;
        }).when(mockjdbcTemplate).update(eq(UPDATE_PUBLISHER), any(PreparedStatementSetter.class));
        Publisher updated = publisherDao.update(publisherUpdated);
        assertEquals(1, resultFromDB);
        assertAll("updated",
                () -> assertEquals(updated.getPubId(), publisherUpdated.getPubId()),
                () -> assertEquals(updated.getName(), publisherUpdated.getName()),
                () -> assertEquals(updated.getAddress(), publisherUpdated.getAddress()),
                () -> assertEquals(updated.getCountry(), publisherUpdated.getCountry()),
                () -> assertEquals(updated.getTelephone(), publisherUpdated.getTelephone()));
    }

    /**
     * Testing method getAllPublishers when result true.
     */
    @Test
    public void getAllPublishersExecutedReturnTrue() {
        final String GET_ALL_PUBLISHERS = "SELECT * FROM PUBLISHER";
        List listFromQueryForList = createListOfMap();
        when(mockjdbcTemplate.queryForList(eq(GET_ALL_PUBLISHERS))).thenReturn(listFromQueryForList);
        List<Publisher> result = publisherDao.getAll();
        assertTrue(!result.isEmpty());
    }

    /**
     * Testing method getPublisherById when Device was provided result true.
     */
    @Test
    public void publisherGetByIdReturnPublisherReturnTrue() {
        when(mockjdbcTemplate.queryForObject(eq(GET_PUB_BY_ID), any(RowMapper.class), any(Long.class)))
                .thenReturn(publisher);
        Publisher result = publisherDao.getById(publisher.getPubId());
        assertAll("result",
                () -> assertEquals(result.getPubId(), publisher.getPubId()),
                () -> assertEquals(result.getName(), publisher.getName()),
                () -> assertEquals(result.getAddress(), publisher.getAddress()),
                () -> assertEquals(result.getCountry(), publisher.getCountry()),
                () -> assertEquals(result.getTelephone(), publisher.getTelephone()));
    }

    /**
     * Testing method mapListFromQueryForList.
     */
    @Test
    public void mapListFromQueryForListReturnTrue() {
        List<Map<String, Object>> listToMapFrom = createListOfMap();
        List<Publisher> result = publisherDao.mapListFromQueryForList(listToMapFrom);
        Publisher publisherResult = result.get(0);
        assertTrue(!result.isEmpty());
        assertAll("publisherResult",
                () -> assertEquals(publisherResult.getPubId(), publisher.getPubId()),
                () -> assertEquals(publisherResult.getName(), publisher.getName()),
                () -> assertEquals(publisherResult.getAddress(), publisher.getAddress()),
                () -> assertEquals(publisherResult.getCountry(), publisher.getCountry()),
                () -> assertEquals(publisherResult.getTelephone(), publisher.getTelephone()));
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