package com.ra.advertisement.service;

import com.ra.advertisement.dao.ProviderAdvertisementDaoImpl;
import com.ra.advertisement.dto.ProviderDto;
import com.ra.advertisement.entity.Provider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ProviderAdvertisementServiceImplTest {
    private static ProviderAdvertisementServiceImpl providerService;
    private static ProviderAdvertisementDaoImpl mockProviderDao;
    private static HttpServletRequest mockRequest;
    private static Validator validator;
    private ProviderDto providerDto;
    private Provider provider;
    private List<Provider> providerList;
    private static JdbcTemplate mockJdbcTemplate;

    @BeforeAll
    public static void init() {
        mockJdbcTemplate = mock(JdbcTemplate.class);
        mockRequest = mock(HttpServletRequest.class);
        mockProviderDao = new ProviderAdvertisementDaoImpl(mockJdbcTemplate);
        providerService = new ProviderAdvertisementServiceImpl(mockProviderDao);
        mockProviderDao = mock(ProviderAdvertisementDaoImpl.class);
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @BeforeEach
    public void reInitAdvertisementDao() {
        providerDto = new ProviderDto("Coca Cola", "Lviv", "224518", "Ukraine");
        provider = new Provider(1L, "Coca Cola", "Lviv", "224518", "Ukraine");
        providerList = new ArrayList<>();
        providerList.add(provider);
        when(mockRequest.getParameter("name")).thenReturn(providerDto.getName());
        when(mockRequest.getParameter("address")).thenReturn(providerDto.getAddress());
        when(mockRequest.getParameter("telephone")).thenReturn(providerDto.getTelephone());
        when(mockRequest.getParameter("country")).thenReturn(providerDto.getCountry());
    }

    /**
     * Method check validation of the dto Object when  validation was not passed.
     *
     * @return entity
     */
    @Test
    public void saveEntityServiceMethodExecutedWithConstraintViolationReturnTrue() {
        when(mockRequest.getParameter("telephone")).thenReturn("text instead of number");
        final ProviderDto dto = ProviderAdvertisementServiceImpl.provDtoCreator(mockRequest);
        final Set<ConstraintViolation<ProviderDto>> violations = validator.validate(dto);
        providerService.saveEntityService(mockRequest);
        assertTrue(!violations.isEmpty());
    }

    /**
     * Method check validation of the dto Object when passed
     *
     * @return entity
     */
    @Test
    public void saveEntityServiceMethodExecutedWithNoConstraintViolationReturnTrue() {
        final List<String> allMessages = new ArrayList<>();
        when(mockRequest.getParameter("telephone")).thenReturn(providerDto.getTelephone());
        final ProviderDto dto = ProviderAdvertisementServiceImpl.provDtoCreator(mockRequest);
        final Set<ConstraintViolation<ProviderDto>> violations = validator.validate(dto);
        providerService.saveEntityService(mockRequest);
        final Provider providerCreated = providerService.provCreator(dto);
        when(mockProviderDao.create(providerCreated)).thenReturn(provider);
        assertTrue(violations.isEmpty());
    }

    /**
     * Testing method which converts dto object into Entity.
     */
    @Test
    public void convertDtoIntoEntityReturnTrue() {
        Provider providerCreated = ProviderAdvertisementServiceImpl.provCreator(providerDto);
        assertAll("providerCreated",
                () -> assertEquals(providerCreated.getName(), providerDto.getName()),
                () -> assertEquals(providerCreated.getAddress(), providerDto.getAddress()),
                () -> assertEquals(providerCreated.getTelephone(), providerDto.getTelephone()),
                () -> assertEquals(providerCreated.getCountry(), providerDto.getCountry()));
    }

    /**
     * Testing method which convert HttpServletRequest into dto object.
     */
    @Test
    public void convertHttpServletRequestIntoDto() {
        ProviderDto providerDtoCreatedSecond = ProviderAdvertisementServiceImpl.provDtoCreator(mockRequest);
        assertAll("providerDtoCreatedSecond",
                () -> assertEquals(providerDtoCreatedSecond.getName(), providerDto.getName()),
                () -> assertEquals(providerDtoCreatedSecond.getAddress(), providerDto.getAddress()),
                () -> assertEquals(providerDtoCreatedSecond.getTelephone(), providerDto.getTelephone()),
                () -> assertEquals(providerDtoCreatedSecond.getCountry(), providerDto.getCountry()));
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
