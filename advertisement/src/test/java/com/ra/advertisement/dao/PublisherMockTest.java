package com.ra.advertisement.dao;

import com.ra.advertisement.config.AdvertisementConfiguration;
import com.ra.advertisement.entity.Provider;
import com.ra.advertisement.entity.Publisher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AdvertisementConfiguration.class, AdvertisementAdvertisementDaoImpl.class})
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
        mockjdbcTemplate = mock(JdbcTemplate.class);
        publisherDao = new PublisherAdvertisementDaoImpl(mockjdbcTemplate);
    }

    @BeforeEach
    public void reInitAdvertisementDao() {
        mockkeyHolder = mock(KeyHolder.class);
        mockStatement = mock(PreparedStatement.class);
        publisher = new Publisher(1L, "Advert ltd", "Kyiv", "25-17-84", "Ukraine");
        publisherNoId = new Publisher("Advert ltd", "Kyiv", "25-17-84", "Ukraine");
        publisherUpdated = new Publisher(1L, "Advert ltd Update", "Kyiv Update",
                "25-17-84 Update", "Ukraine Update");
        when(mockjdbcTemplate.queryForObject(Mockito.eq(GET_PUB_BY_ID), Mockito.any(RowMapper.class), Mockito.any(Long.class)))
                .thenReturn(publisher);
    }

    /**
     * Testing method addPPublisher when result true.
     */
    @Test
    public void addPublisherExecuteSuccessfuldReturnTrue() {
        when(mockjdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class))).thenReturn(1);
        when(mockkeyHolder.getKey()).thenReturn(1L);
        Publisher publisherCreated = publisherDao.create(publisherNoId);
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
        when(mockkeyHolder.getKey()).thenReturn(0L);
        Publisher publisherCreated = publisherDao.create(publisherNoId);
        assertEquals(publisherCreated.getPubId(), publisher.getPubId());
    }

    /**
     * Testing method deletePublisher when result true.
     */
    @Test
    public void deletePublisherSuccessfulReturnTrue() {
        final String DELETE_PUBLISHER = "DELETE FROM PUBLISHER WHERE PUB_ID=?";
        when(mockjdbcTemplate.update(DELETE_PUBLISHER, publisher.getPubId())).thenReturn(1);
        Integer result = publisherDao.delete(publisher);
        Assertions.assertTrue(result == 1);
    }

    /**
     * Testing method updatePublisher when result true.
     */
    @Test
    public void updatePublisherSuccessfulReturnTrue() {
        final String UPDATE_PUBLISHER = "update PUBLISHER set NAME = ?, ADDRESS= ?, TELEPHONE = ?"
                + ", COUNTRY = ? where PUB_ID = ?";
        when(mockjdbcTemplate.update(eq(UPDATE_PUBLISHER), Mockito.any(PreparedStatement.class))).thenReturn(1);
        when(mockjdbcTemplate.queryForObject(Mockito.eq(GET_PUB_BY_ID), Mockito.any(RowMapper.class),
                Mockito.any(Long.class))).thenReturn(publisherUpdated);
        Mockito.doAnswer(invocation -> {
            ((PreparedStatementSetter) invocation.getArguments()[1]).setValues(mockStatement);
            return null;
        }).when(mockjdbcTemplate).update(Mockito.eq(UPDATE_PUBLISHER), Mockito.any(PreparedStatementSetter.class));
        Publisher result = publisherDao.update(publisher);
        assertAll("result",
                () -> assertEquals(result.getPubId(), publisherUpdated.getPubId()),
                () -> assertEquals(result.getName(), publisherUpdated.getName()),
                () -> assertEquals(result.getAddress(), publisherUpdated.getAddress()),
                () -> assertEquals(result.getCountry(), publisherUpdated.getCountry()),
                () -> assertEquals(result.getTelephone(), publisherUpdated.getTelephone()));
    }

    /**
     * Testing method getAllPublishers when result true.
     */
    @Test
    public void getAllPublishersExecutedReturnTrue() {
        final String GET_ALL_PUBLISHERS = "SELECT * FROM PUBLISHER";
        List listFromQueryForList = createListOfMap();
        Mockito.when(mockjdbcTemplate.queryForList(eq(GET_ALL_PUBLISHERS))).thenReturn(listFromQueryForList);
        List<Publisher> result = publisherDao.getAll();
        assertTrue(!result.isEmpty());
    }

    /**
     * Testing method getPublisherById when Device was provided result true.
     */
    @Test
    public void publisherGetByIdReturnPublisherReturnTrue() {
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