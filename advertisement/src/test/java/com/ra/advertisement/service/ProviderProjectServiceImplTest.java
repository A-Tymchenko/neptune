package com.ra.advertisement.service;

import com.ra.advertisement.dao.ProviderProjectDaoImpl;
import com.ra.advertisement.dto.ProviderDto;
import com.ra.advertisement.entity.Provider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ProviderProjectServiceImplTest {
    private static ProviderProjectServiceImpl providerService;
    private static ProviderProjectDaoImpl mockProviderDao;
    private static BeanValidator beanValidator;
    private static Validator validator;
    private ProviderDto providerDto;
    private ProviderDto providerDtoCorrect;
    private Provider provider;
    private List<Provider> providerList;
    private static JdbcTemplate mockJdbcTemplate;

    @BeforeAll
    public static void init() {
        beanValidator = new BeanValidator();
        mockJdbcTemplate = mock(JdbcTemplate.class);
        mockProviderDao = new ProviderProjectDaoImpl(mockJdbcTemplate);
        providerService = new ProviderProjectServiceImpl(mockProviderDao, beanValidator);
        mockProviderDao = mock(ProviderProjectDaoImpl.class);
        validator = beanValidator.getValidator();
    }

    @BeforeEach
    public void reInitAdvertisementDao() {
        providerDto = new ProviderDto("Coca Cola", "Lviv", "text", "Ukraine");
        providerDtoCorrect = new ProviderDto("Coca Cola", "Lviv", "224518", "Ukraine");
        provider = new Provider(1L, "Coca Cola", "Lviv", "224518", "Ukraine");
        providerList = new ArrayList<>();
        providerList.add(provider);
    }

    /**
     * Method check validation of the dto Object when  validation was not passed.
     *
     * @return entity
     */
    @Test
    public void saveEntityServiceMethodExecutedWithConstraintViolationReturnTrue() {
        List<String> answers = new ArrayList<>();
        final Set<ConstraintViolation<ProviderDto>> violations = validator.validate(providerDto);
        answers = providerService.saveEntityService(providerDto);
        assertTrue(!violations.isEmpty());
        assertTrue(answers.size() == 1);
    }

    /**
     * Method check validation of the dto Object when passed
     *
     * @return entity
     */
    @Test
    public void saveEntityServiceMethodExecutedWithNoConstraintViolationReturnTrue() {
        List<String> answers = new ArrayList<>();
        final Set<ConstraintViolation<ProviderDto>> violations = validator.validate(providerDtoCorrect);
        answers = providerService.saveEntityService(providerDtoCorrect);
        System.out.println(answers);
        assertTrue(violations.isEmpty());
        assertTrue(answers.get(0).equals("Object has been saved successfully"));

    }

    /**
     * Testing method which converts dto object into Entity.
     */
    @Test
    public void convertDtoIntoEntityReturnTrue() {
        Provider providerCreated = providerService.provCreator(providerDto);
        assertAll("providerCreated",
                () -> assertEquals(providerCreated.getName(), providerDto.getName()),
                () -> assertEquals(providerCreated.getAddress(), providerDto.getAddress()),
                () -> assertEquals(providerCreated.getTelephone(), providerDto.getTelephone()),
                () -> assertEquals(providerCreated.getCountry(), providerDto.getCountry()));
    }

    /**
     * Testing method getAllService which provides List of Entities.
     */
    @Test
    public void getAllServiceExecutedSuccessfulReturnTrue() {
        when(mockProviderDao.getAll()).thenReturn(providerList);
        List listFromQueryForList = createListOfMap();
        when(mockJdbcTemplate.queryForList(anyString())).thenReturn(listFromQueryForList);
        List<ProviderDto> listDto = providerService.getAllEntityService();
        assertTrue(!listDto.isEmpty());
    }

    /**
     * Testing method which convertListOfEntity into List of Dto Objects.
     */
    @Test
    public void mapEntityListIntoListOfDtoReturnTrue() {
        List<Provider> providerList = new ArrayList<>();
        providerList.add(provider);
        List<ProviderDto> providerDtoList = providerService.mapListEntityIntoDto(providerList);
        assertTrue(!providerDtoList.isEmpty());
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
