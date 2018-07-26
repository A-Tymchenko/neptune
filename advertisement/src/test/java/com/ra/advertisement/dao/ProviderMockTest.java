package com.ra.advertisement.dao;

import com.ra.advertisement.config.AdvertisementConfiguration;
import com.ra.advertisement.entity.Provider;
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
public class ProviderMockTest {
    private static JdbcTemplate mockjdbcTemplate;
    private static ProviderAdvertisementDaoImpl providerDao;
    private static final String GET_PROV_BY_ID = "SELECT * FROM PROVIDER WHERE PROV_ID=?";
    private Provider provider;
    private Provider providerNoId;
    private Provider providerUpdated;
    private KeyHolder mockkeyHolder = new GeneratedKeyHolder();
    private PreparedStatement mockStatement;

    @BeforeAll
    public static void init() {
        mockjdbcTemplate = mock(JdbcTemplate.class);
        providerDao = new ProviderAdvertisementDaoImpl(mockjdbcTemplate);
    }

    @BeforeEach
    public void reInitAdvertisementDao() throws SQLException {
        mockkeyHolder = mock(KeyHolder.class);
        mockStatement = mock(PreparedStatement.class);
        provider = new Provider(1l, "Coca Cola", "Lviv", "22-45-18", "Ukraine");
        providerNoId = new Provider("Coca Cola", "Lviv", "22-45-18", "Ukraine");
        providerUpdated = new Provider(1L, "Cocacola", "Coca Cola Update", "LvivUpdate", "Ukraine");
        when(mockjdbcTemplate.queryForObject(Mockito.eq(GET_PROV_BY_ID), Mockito.any(RowMapper.class), Mockito.any(Long.class)))
                .thenReturn(provider);
    }

    /**
     * Testing method addProvider when result true.
     */
    @Test
    public void addProviderExecuteSuccessfuldReturnTrue() throws SQLException {
        when(mockjdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class))).thenReturn(1);
        when(mockkeyHolder.getKey()).thenReturn(1L);
        Provider providerCreated = providerDao.create(providerNoId);
        assertAll("providerCreated",
                () -> assertEquals(providerCreated.getProvId(), provider.getProvId()),
                () -> assertEquals(providerCreated.getName(), provider.getName()),
                () -> assertEquals(providerCreated.getAddress(), provider.getAddress()),
                () -> assertEquals(providerCreated.getCountry(), provider.getCountry()),
                () -> assertEquals(providerCreated.getTelephone(), provider.getTelephone()));
    }

    /**
     * Testing method addAProvider when we don't get id of created entity.
     *
     * @throws SQLException exception.
     */
    @Test
    public void addProviderAndDontGetGeneratedIdReturnTrue() {
        when(mockjdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class))).thenReturn(1);
        when(mockkeyHolder.getKey()).thenReturn(0L);
        Provider providerCreated = providerDao.create(providerNoId);
        assertEquals(providerCreated.getProvId(), provider.getProvId());
    }

    /**
     * Testing method deleteProvider when result true.
     */
    @Test
    public void deleteProviderSuccessfulReturnTrue() {
        final String DELETE_PROVIDER = "DELETE FROM PROVIDER WHERE PROV_ID=?";
        when(mockjdbcTemplate.update(DELETE_PROVIDER, provider.getProvId())).thenReturn(1);
        Integer result = providerDao.delete(provider);
        Assertions.assertTrue(result == 1);
    }

    /**
     * Testing method updateProvider when result true.
     */
    @Test
    public void updateProviderSuccessfulReturnTrue() {
        final String UPDATE_PROVIDER = "update PROVIDER set NAME = ?, ADDRESS= ?, TELEPHONE = ?, COUNTRY = ? where PROV_ID = ?";
        when(mockjdbcTemplate.update(eq(UPDATE_PROVIDER), Mockito.any(PreparedStatement.class))).thenReturn(1);
        when(mockjdbcTemplate.queryForObject(Mockito.eq(GET_PROV_BY_ID), Mockito.any(RowMapper.class),
                Mockito.any(Long.class))).thenReturn(providerUpdated);
        Mockito.doAnswer(invocation -> {
            ((PreparedStatementSetter) invocation.getArguments()[1]).setValues(mockStatement);
            return null;
        }).when(mockjdbcTemplate).update(Mockito.eq(UPDATE_PROVIDER), Mockito.any(PreparedStatementSetter.class));
        Provider result = providerDao.update(provider);
        assertAll("result",
                () -> assertEquals(result.getProvId(), providerUpdated.getProvId()),
                () -> assertEquals(result.getName(), providerUpdated.getName()),
                () -> assertEquals(result.getAddress(), providerUpdated.getAddress()),
                () -> assertEquals(result.getCountry(), providerUpdated.getCountry()),
                () -> assertEquals(result.getTelephone(), providerUpdated.getTelephone()));
    }

    /**
     * Testing method getAllProviders when result true.
     */
    @Test
    public void getAllProvidersExecutedReturnTrue() {
        final String GET_ALL_PROVIDERS = "SELECT * FROM PROVIDER";
        List listFromQueryForList = createListOfMap();
        Mockito.when(mockjdbcTemplate.queryForList(eq(GET_ALL_PROVIDERS))).thenReturn(listFromQueryForList);
        List<Provider> result = providerDao.getAll();
        assertTrue(!result.isEmpty());
    }

    /**
     * Testing method getProviderById when Device was provided result true.
     */
    @Test
    public void providerGetByIdReturnProviderReturnTrue() {
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