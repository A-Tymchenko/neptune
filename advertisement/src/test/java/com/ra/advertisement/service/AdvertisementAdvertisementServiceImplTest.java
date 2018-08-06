package com.ra.advertisement.service;

import com.ra.advertisement.dao.AdvertisementAdvertisementDaoImpl;
import com.ra.advertisement.dto.AdvertisementDto;
import com.ra.advertisement.entity.Advertisement;
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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class AdvertisementAdvertisementServiceImplTest {
    private static AdvertisementAdvertisementServiceImpl advertService;
    private static AdvertisementAdvertisementDaoImpl mockAdvertDao;
    private static HttpServletRequest mockRequest;
    private static Validator validator;
    private AdvertisementDto advertisementDto;
    private Advertisement advertisement;
    private List<Advertisement> listAdvert;
    private static JdbcTemplate mockJdbcTemplate;

    @BeforeAll
    public static void init() {
        mockJdbcTemplate = mock(JdbcTemplate.class);
        mockRequest = mock(HttpServletRequest.class);
        mockAdvertDao = new AdvertisementAdvertisementDaoImpl(mockJdbcTemplate);
        advertService = new AdvertisementAdvertisementServiceImpl(mockAdvertDao);
        mockAdvertDao = mock(AdvertisementAdvertisementDaoImpl.class);
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @BeforeEach
    public void reInitAdvertisementDao() {
        advertisementDto = new AdvertisementDto("Welcome advert", "Welcome to Ukraine",
                "url", "Ukrainian");
        advertisement = new Advertisement(1L, "Welcome New advertda", "Welcome to Ukraine",
                "url", "Ukrainian");
        listAdvert = new ArrayList<>();
        listAdvert.add(advertisement);
        when(mockRequest.getParameter("title")).thenReturn(advertisementDto.getTitle());
        when(mockRequest.getParameter("context")).thenReturn(advertisementDto.getContext());
        when(mockRequest.getParameter("language")).thenReturn(advertisementDto.getLanguage());
    }

    /**
     * Method check validation of the dto Object when  validation was not passed.
     *
     * @return entity
     */
    @Test
    public void saveEntityServiceMethodExecutedWithConstraintViolationReturnTrue() {
        when(mockRequest.getParameter("imageUrl")).thenReturn(advertisementDto.getImageUrl());
        final AdvertisementDto dto = AdvertisementAdvertisementServiceImpl.advDtoCreator(mockRequest);
        final Set<ConstraintViolation<AdvertisementDto>> violations = validator.validate(dto);
        advertService.saveEntityService(mockRequest);
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
        when(mockRequest.getParameter("imageUrl")).thenReturn("https://ithillel.ua/");
        final AdvertisementDto dto = AdvertisementAdvertisementServiceImpl.advDtoCreator(mockRequest);
        final Set<ConstraintViolation<AdvertisementDto>> violations = validator.validate(dto);
        advertService.saveEntityService(mockRequest);
        final Advertisement advertisementCreated = advertService.advCreator(dto);
        when(mockAdvertDao.create(advertisementCreated)).thenReturn(advertisement);
        assertTrue(violations.isEmpty());
    }

    /**
     * Testing method which converts dto object into Entity.
     */
    @Test
    public void convertDtoIntoEntityReturnTrue() {
        Advertisement advertisementCreated = AdvertisementAdvertisementServiceImpl.advCreator(advertisementDto);
        assertAll("advertisement",
                () -> assertEquals(advertisementCreated.getTitle(), advertisementDto.getTitle()),
                () -> assertEquals(advertisementCreated.getContext(), advertisementDto.getContext()),
                () -> assertEquals(advertisementCreated.getImageUrl(), advertisementDto.getImageUrl()),
                () -> assertEquals(advertisementCreated.getLanguage(), advertisementDto.getLanguage()));
    }

    /**
     * Testing method which convert HttpServletRequest into dto object.
     */
    @Test
    public void convertHttpServletRequestIntoDto() {
        AdvertisementDto advertisementDtoCreated = AdvertisementAdvertisementServiceImpl.advDtoCreator(mockRequest);
        assertAll("advertisementDtoCreated",
                () -> assertEquals(advertisementDtoCreated.getTitle(), advertisementDto.getTitle()),
                () -> assertEquals(advertisementDtoCreated.getContext(), advertisementDto.getContext()),
                () -> assertEquals(advertisementDtoCreated.getImageUrl(), advertisementDto.getImageUrl()),
                () -> assertEquals(advertisementDtoCreated.getLanguage(), advertisementDto.getLanguage()));
    }

    /**
     * Testing method getAllService which provides List of Entities.
     */
    @Test
    public void getAllServiceExecutedSuccessfulReturnTrue() {
        when(mockAdvertDao.getAll()).thenReturn(listAdvert);
        List listFromQueryForList = createListOfMap();
        when(mockJdbcTemplate.queryForList(anyString())).thenReturn(listFromQueryForList);
        List<AdvertisementDto> listDto = advertService.getAllEntityService();
        assertTrue(!listDto.isEmpty());
    }

    /**
     * Testing method which convertListOfEntity into List of Dto Objects.
     */
    @Test
    public void mapEntityListIntoListOfDtoReturnTrue() {
        List<Advertisement> listAdvert = new ArrayList<>();
        listAdvert.add(advertisement);
        List<AdvertisementDto> advertisementDtoList = advertService.mapListEntityIntoDto(listAdvert);
        assertTrue(!advertisementDtoList.isEmpty());
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
