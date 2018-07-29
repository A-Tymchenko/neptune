package com.ra.advertisement.dao;

import com.ra.advertisement.config.AdvertisementConfiguration;
import com.ra.advertisement.entity.Provider;
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
        mockjdbcTemplate = Mockito.mock(JdbcTemplate.class);
        providerDao = new ProviderAdvertisementDaoImpl(mockjdbcTemplate);
    }

    @BeforeEach
    public void reInitAdvertisementDao() {
        mockkeyHolder = Mockito.mock(KeyHolder.class);
        mockStatement = Mockito.mock(PreparedStatement.class);
        provider = new Provider(1l, "Coca Cola", "Lviv", "22-45-18", "Ukraine");
        providerNoId = new Provider("Coca Cola", "Lviv", "22-45-18", "Ukraine");
        providerUpdated = new Provider(1L, "Cocacola", "Coca Cola Update", "LvivUpdate", "Ukraine");
    }

    /**
     * Testing method addProvider when result true.
     */
    @Test
    public void addProviderExecuteSuccessfuldReturnTrue() {
        Mockito.when(mockjdbcTemplate.update(Mockito.any(PreparedStatementCreator.class), Mockito.any(KeyHolder.class))).thenReturn(1);
        Mockito.when(mockkeyHolder.getKey()).thenReturn(1L);
        Provider providerCreated = providerDao.create(providerNoId);
        providerNoId.setProvId((Long) mockkeyHolder.getKey());
        Assertions.assertAll("providerCreated",
                () -> Assertions.assertEquals(providerCreated.getProvId(), provider.getProvId()),
                () -> Assertions.assertEquals(providerCreated.getName(), provider.getName()),
                () -> Assertions.assertEquals(providerCreated.getAddress(), provider.getAddress()),
                () -> Assertions.assertEquals(providerCreated.getCountry(), provider.getCountry()),
                () -> Assertions.assertEquals(providerCreated.getTelephone(), provider.getTelephone()));
    }

    /**
     * Testing method addAProvider when we don't get id of created entity.
     */
    @Test
    public void addProviderAndDontGetGeneratedIdReturnTrue() {
        Mockito.when(mockjdbcTemplate.update(Mockito.any(PreparedStatementCreator.class), Mockito.any(KeyHolder.class))).thenReturn(1);
        Mockito.when(mockkeyHolder.getKey()).thenReturn(null);
        providerDao.create(providerNoId);
        Assertions.assertTrue(mockkeyHolder.getKey()==null);
    }

    /**
     * Testing method deleteProvider when result true.
     */
    @Test
    public void deleteProviderSuccessfulReturnTrue() {
        final String DELETE_PROVIDER = "DELETE FROM PROVIDER WHERE PROV_ID=?";
        Mockito.when(mockjdbcTemplate.update(DELETE_PROVIDER, provider.getProvId())).thenReturn(1);
        Integer result = providerDao.delete(provider);
        Assertions.assertTrue(result == 1);
    }

    /**
     * Testing method updateProvider when result true.
     */
    @Test
    public void updateProviderSuccessfulReturnTrue() {
        int resultFromDB = 0;
        final String UPDATE_PROVIDER = "update PROVIDER set NAME = ?, ADDRESS= ?, TELEPHONE = ?, COUNTRY = ? where PROV_ID = ?";
        Mockito.when(mockjdbcTemplate.update(Mockito.eq(UPDATE_PROVIDER), Mockito.any(PreparedStatement.class))).thenReturn(resultFromDB=1);
        Mockito.doAnswer(invocation -> {
            ((PreparedStatementSetter) invocation.getArguments()[1]).setValues(mockStatement);
            return null;
        }).when(mockjdbcTemplate).update(Mockito.eq(UPDATE_PROVIDER), Mockito.any(PreparedStatementSetter.class));
        Provider updated = providerDao.update(providerUpdated);
        Assertions.assertEquals(1, resultFromDB);
        Assertions.assertAll("updated",
                () -> Assertions.assertEquals(updated.getProvId(), providerUpdated.getProvId()),
                () -> Assertions.assertEquals(updated.getName(), providerUpdated.getName()),
                () -> Assertions.assertEquals(updated.getAddress(), providerUpdated.getAddress()),
                () -> Assertions.assertEquals(updated.getCountry(), providerUpdated.getCountry()),
                () -> Assertions.assertEquals(updated.getTelephone(), providerUpdated.getTelephone()));
    }

    /**
     * Testing method getAllProviders when result true.
     */
    @Test
    public void getAllProvidersExecutedReturnTrue() {
        final String GET_ALL_PROVIDERS = "SELECT * FROM PROVIDER";
        List listFromQueryForList = createListOfMap();
        Mockito.when(mockjdbcTemplate.queryForList(Mockito.eq(GET_ALL_PROVIDERS))).thenReturn(listFromQueryForList);
        List<Provider> result = providerDao.getAll();
        Assertions.assertTrue(!result.isEmpty());
    }

    /**
     * Testing method getProviderById when Device was provided result true.
     */
    @Test
    public void providerGetByIdReturnProviderReturnTrue() {
        Mockito.when(mockjdbcTemplate.queryForObject(Mockito.eq(GET_PROV_BY_ID), Mockito.any(RowMapper.class), Mockito.any(Long.class)))
                .thenReturn(provider);
        Provider result = providerDao.getById(provider.getProvId());
        Assertions.assertAll("result",
                () -> Assertions.assertEquals(result.getProvId(), provider.getProvId()),
                () -> Assertions.assertEquals(result.getName(), provider.getName()),
                () -> Assertions.assertEquals(result.getAddress(), provider.getAddress()),
                () -> Assertions.assertEquals(result.getCountry(), provider.getCountry()),
                () -> Assertions.assertEquals(result.getTelephone(), provider.getTelephone()));
    }

    /**
     * Testing method mapListFromQueryForList.
     */
    @Test
    public void mapListFromQueryForListReturnTrue() {
        List<Map<String, Object>> listToMapFrom = createListOfMap();
        List<Provider> result = providerDao.mapListFromQueryForList(listToMapFrom);
        Provider providerResult = result.get(0);
        Assertions.assertTrue(!result.isEmpty());
        Assertions.assertAll("providerResult",
                () -> Assertions.assertEquals(providerResult.getProvId(), provider.getProvId()),
                () -> Assertions.assertEquals(providerResult.getName(), provider.getName()),
                () -> Assertions.assertEquals(providerResult.getAddress(), provider.getAddress()),
                () -> Assertions.assertEquals(providerResult.getCountry(), provider.getCountry()),
                () -> Assertions.assertEquals(providerResult.getTelephone(), provider.getTelephone()));
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