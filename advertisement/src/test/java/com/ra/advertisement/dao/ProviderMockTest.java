package com.ra.advertisement.dao;

import com.ra.advertisement.entity.Provider;
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

public class ProviderMockTest {
    private static JdbcTemplate mockjdbcTemplate;
    private static ProviderProjectDaoImpl providerDao;
    private static final String GET_PROV_BY_ID = "SELECT * FROM PROVIDER WHERE PROV_ID=?";
    private Provider provider;
    private Provider providerNoId;
    private Provider providerUpdated;
    private KeyHolder mockkeyHolder = new GeneratedKeyHolder();
    private PreparedStatement mockStatement;

    @BeforeAll
    public static void init() {
        mockjdbcTemplate = mock(JdbcTemplate.class);
        providerDao = new ProviderProjectDaoImpl(mockjdbcTemplate);
    }

    @BeforeEach
    public void reInitAdvertisementDao() {
        mockkeyHolder = mock(KeyHolder.class);
        mockStatement = mock(PreparedStatement.class);
        provider = new Provider(1l, "Coca Cola", "Lviv", "22-45-18", "Ukraine");
        providerNoId = new Provider("Coca Cola", "Lviv", "22-45-18", "Ukraine");
        providerUpdated = new Provider(1L, "Cocacola", "Coca Cola Update", "LvivUpdate", "Ukraine");
    }

    /**
     * Testing method addProvider when result true.
     */
    @Test
    public void addProviderExecuteSuccessfuldReturnTrue() {
        when(mockjdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class))).thenReturn(1);
        when(mockkeyHolder.getKey()).thenReturn(1L);
        Provider providerCreated = providerDao.create(providerNoId);
        providerNoId.setProvId((Long) mockkeyHolder.getKey());
        assertAll("providerCreated",
                () -> assertEquals(providerCreated.getProvId(), provider.getProvId()),
                () -> assertEquals(providerCreated.getName(), provider.getName()),
                () -> assertEquals(providerCreated.getAddress(), provider.getAddress()),
                () -> assertEquals(providerCreated.getCountry(), provider.getCountry()),
                () -> assertEquals(providerCreated.getTelephone(), provider.getTelephone()));
    }

    /**
     * Testing method addAProvider when we don't get id of created entity.
     */
    @Test
    public void addProviderAndDontGetGeneratedIdReturnTrue() {
        when(mockjdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class))).thenReturn(1);
        when(mockkeyHolder.getKey()).thenReturn(null);
        providerDao.create(providerNoId);
        assertTrue(mockkeyHolder.getKey() == null);
    }

    /**
     * Testing method deleteProvider when result true.
     */
    @Test
    public void deleteProviderSuccessfulReturnTrue() {
        final String DELETE_PROVIDER = "DELETE FROM PROVIDER WHERE PROV_ID=?";
        when(mockjdbcTemplate.update(DELETE_PROVIDER, provider.getProvId())).thenReturn(1);
        Integer result = providerDao.delete(provider);
        assertTrue(result == 1);
    }

    /**
     * Testing method updateProvider when result true.
     */
    @Test
    public void updateProviderSuccessfulReturnTrue() {
        int resultFromDB = 0;
        final String UPDATE_PROVIDER = "update PROVIDER set NAME = ?, ADDRESS= ?, TELEPHONE = ?, COUNTRY = ? where PROV_ID = ?";
        when(mockjdbcTemplate.update(eq(UPDATE_PROVIDER), any(PreparedStatement.class))).thenReturn(resultFromDB = 1);
        doAnswer(invocation -> {
            ((PreparedStatementSetter) invocation.getArguments()[1]).setValues(mockStatement);
            return null;
        }).when(mockjdbcTemplate).update(eq(UPDATE_PROVIDER), any(PreparedStatementSetter.class));
        Provider updated = providerDao.update(providerUpdated);
        assertEquals(1, resultFromDB);
        assertAll("updated",
                () -> assertEquals(updated.getProvId(), providerUpdated.getProvId()),
                () -> assertEquals(updated.getName(), providerUpdated.getName()),
                () -> assertEquals(updated.getAddress(), providerUpdated.getAddress()),
                () -> assertEquals(updated.getCountry(), providerUpdated.getCountry()),
                () -> assertEquals(updated.getTelephone(), providerUpdated.getTelephone()));
    }

    /**
     * Testing method getAllProviders when result true.
     */
    @Test
    public void getAllProvidersExecutedReturnTrue() {
        final String GET_ALL_PROVIDERS = "SELECT * FROM PROVIDER";
        List listFromQueryForList = createListOfMap();
        when(mockjdbcTemplate.queryForList(eq(GET_ALL_PROVIDERS))).thenReturn(listFromQueryForList);
        List<Provider> result = providerDao.getAll();
        assertTrue(!result.isEmpty());
    }

    /**
     * Testing method getProviderById when Device was provided result true.
     */
    @Test
    public void providerGetByIdReturnProviderReturnTrue() {
        when(mockjdbcTemplate.queryForObject(eq(GET_PROV_BY_ID), any(RowMapper.class), any(Long.class)))
                .thenReturn(provider);
        Provider result = providerDao.getById(provider.getProvId());
        assertAll("result",
                () -> assertEquals(result.getProvId(), provider.getProvId()),
                () -> assertEquals(result.getName(), provider.getName()),
                () -> assertEquals(result.getAddress(), provider.getAddress()),
                () -> assertEquals(result.getCountry(), provider.getCountry()),
                () -> assertEquals(result.getTelephone(), provider.getTelephone()));
    }

    /**
     * Testing method mapListFromQueryForList.
     */
    @Test
    public void mapListFromQueryForListReturnTrue() {
        List<Map<String, Object>> listToMapFrom = createListOfMap();
        List<Provider> result = providerDao.mapListFromQueryForList(listToMapFrom);
        Provider providerResult = result.get(0);
        assertTrue(!result.isEmpty());
        assertAll("providerResult",
                () -> assertEquals(providerResult.getProvId(), provider.getProvId()),
                () -> assertEquals(providerResult.getName(), provider.getName()),
                () -> assertEquals(providerResult.getAddress(), provider.getAddress()),
                () -> assertEquals(providerResult.getCountry(), provider.getCountry()),
                () -> assertEquals(providerResult.getTelephone(), provider.getTelephone()));
    }

    /**
     * this method create List of MapStringObject
     *
     * @return list
     */
    private List createListOfMap() {
        List<Map<String, Object>> listToMapFrom = new ArrayList<>();
        Map<String, Object> devId = new HashMap<>();
        devId.put("PROV_ID", provider.getProvId());
        devId.put("NAME", provider.getName());
        devId.put("ADDRESS", provider.getAddress());
        devId.put("TELEPHONE", provider.getTelephone());
        devId.put("COUNTRY", provider.getCountry());
        listToMapFrom.add(devId);
        return listToMapFrom;
    }
}