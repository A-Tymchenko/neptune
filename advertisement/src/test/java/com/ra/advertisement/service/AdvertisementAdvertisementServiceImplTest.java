package com.ra.advertisement.service;

import com.ra.advertisement.dao.AdvertisementAdvertisementDaoImpl;
import com.ra.advertisement.dto.AdvertisementDto;
import com.ra.advertisement.entity.Advertisement;
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


public class AdvertisementAdvertisementServiceImplTest {
    private static AdvertisementAdvertisementServiceImpl advertService;
    private static AdvertisementAdvertisementDaoImpl mockAdvertDao;
    private static BeanValidator beanValidator;
    private static Validator validator;
    private AdvertisementDto advertisementDto;
    private AdvertisementDto advertisementDtoCorrect;
    private Advertisement advertisement;
    private List<Advertisement> listAdvert;
    private static JdbcTemplate mockJdbcTemplate;

    @BeforeAll
    public static void init() {
        beanValidator = new BeanValidator();
        mockJdbcTemplate = mock(JdbcTemplate.class);
        mockAdvertDao = new AdvertisementAdvertisementDaoImpl(mockJdbcTemplate);
        advertService = new AdvertisementAdvertisementServiceImpl(mockAdvertDao, beanValidator);
        mockAdvertDao = mock(AdvertisementAdvertisementDaoImpl.class);
        validator = beanValidator.getValidator();
    }

    @BeforeEach
    public void reInitAdvertisementDao() {
        advertisementDto = new AdvertisementDto("Welcome advert", "Welcome to Ukraine",
                "url", "Ukrainian");
        advertisementDtoCorrect = new AdvertisementDto("Welcome advert", "Welcome to Ukraine",
                "https://ithillel.ua/", "Ukrainian");
        advertisement = new Advertisement(1L, "Welcome New advertda", "Welcome to Ukraine",
                "url", "Ukrainian");
        listAdvert = new ArrayList<>();
        listAdvert.add(advertisement);
    }

    /**
     * Method check validation of the dto Object when  validation was not passed.
     *
     * @return entity
     */
    @Test
    public void saveEntityServiceMethodExecutedWithConstraintViolationReturnTrue() {
        List<String> answers = new ArrayList<>();
        final Set<ConstraintViolation<AdvertisementDto>> violations = validator.validate(advertisementDto);
        answers = advertService.saveEntityService(advertisementDto);
        assertTrue(!violations.isEmpty());
        assertTrue(answers.size()==2);
    }

    /**
     * Method check validation of the dto Object when passed
     *
     * @return entity
     */
    @Test
    public void saveEntityServiceMethodExecutedWithNoConstraintViolationReturnTrue() {
        List<String> answers = new ArrayList<>();
        final Set<ConstraintViolation<AdvertisementDto>> violations = validator.validate(advertisementDtoCorrect);
        answers = advertService.saveEntityService(advertisementDtoCorrect);
        assertTrue(violations.isEmpty());
        assertTrue(answers.get(0).equals("Object has been saved successfully"));
    }

    /**
     * Testing method which converts dto object into Entity.
     */
    @Test
    public void convertDtoIntoEntityReturnTrue() {
        Advertisement advertisementCreated = advertService.advCreator(advertisementDto);
        assertAll("advertisement",
                () -> assertEquals(advertisementCreated.getTitle(), advertisementDto.getTitle()),
                () -> assertEquals(advertisementCreated.getContext(), advertisementDto.getContext()),
                () -> assertEquals(advertisementCreated.getImageUrl(), advertisementDto.getImageUrl()),
                () -> assertEquals(advertisementCreated.getLanguage(), advertisementDto.getLanguage()));
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
