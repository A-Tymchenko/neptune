package com.ra.advertisement.service;

import com.ra.advertisement.dao.PublisherProjectDaoImpl;
import com.ra.advertisement.dto.PublisherDto;
import com.ra.advertisement.entity.Publisher;
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


public class PublisherProjectServiceImplTest {
    private static PublisherProjectServiceImpl publisherService;
    private static PublisherProjectDaoImpl mockPublisherDao;
    private static BeanValidator beanValidator;
    private static Validator validator;
    private PublisherDto publisherDto;
    private PublisherDto publisherDtoCorrect;
    private Publisher publisher;
    private List<Publisher> publisherList;
    private static JdbcTemplate mockJdbcTemplate;

    @BeforeAll
    public static void init() {
        beanValidator = new BeanValidator();
        mockJdbcTemplate = mock(JdbcTemplate.class);
        mockPublisherDao = new PublisherProjectDaoImpl(mockJdbcTemplate);
        publisherService = new PublisherProjectServiceImpl(mockPublisherDao, beanValidator);
        mockPublisherDao = mock(PublisherProjectDaoImpl.class);
        validator = beanValidator.getValidator();
    }

    @BeforeEach
    public void reInitAdvertisementDao() {
        publisherDto = new PublisherDto("Coca Cola", "Lviv", "text", "Ukraine");
        publisherDtoCorrect = new PublisherDto("Coca Cola", "Lviv", "224518", "Ukraine");
        publisher = new Publisher(1L, "Coca Cola", "Lviv", "224518", "Ukraine");
        publisherList = new ArrayList<>();
        publisherList.add(publisher);
    }

    /**
     * Method check validation of the dto Object when  validation was not passed.
     *
     * @return entity
     */
    @Test
    public void saveEntityServiceMethodExecutedWithConstraintViolationReturnTrue() {
        List<String> answers = new ArrayList<>();
        final Set<ConstraintViolation<PublisherDto>> violations = validator.validate(publisherDto);
        answers = publisherService.saveEntityService(publisherDto);
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
        final Set<ConstraintViolation<PublisherDto>> violations = validator.validate(publisherDtoCorrect);
        answers = publisherService.saveEntityService(publisherDtoCorrect);
        assertTrue(violations.isEmpty());
        assertTrue(answers.get(0).equals("Object has been saved successfully"));
    }

    /**
     * Testing method which converts dto object into Entity.
     */
    @Test
    public void convertDtoIntoEntityReturnTrue() {
        Publisher publisherCreated = publisherService.pubCreator(publisherDto);
        assertAll("publisherCreated",
                () -> assertEquals(publisherCreated.getName(), publisherDto.getName()),
                () -> assertEquals(publisherCreated.getAddress(), publisherDto.getAddress()),
                () -> assertEquals(publisherCreated.getTelephone(), publisherDto.getTelephone()),
                () -> assertEquals(publisherCreated.getCountry(), publisherDto.getCountry()));
    }

    /**
     * Testing method getAllService which provides List of Entities.
     */
    @Test
    public void getAllServiceExecutedSuccessfulReturnTrue() {
        when(mockPublisherDao.getAll()).thenReturn(publisherList);
        List listFromQueryForList = createListOfMap();
        when(mockJdbcTemplate.queryForList(anyString())).thenReturn(listFromQueryForList);
        List<PublisherDto> listDto = publisherService.getAllEntityService();
        assertTrue(!listDto.isEmpty());
    }

    /**
     * Testing method which convertListOfEntity into List of Dto Objects.
     */
    @Test
    public void mapEntityListIntoListOfDtoReturnTrue() {
        List<Publisher> publisherList = new ArrayList<>();
        publisherList.add(publisher);
        List<PublisherDto> publisherDtoList = publisherService.mapListEntityIntoDto(publisherList);
        assertTrue(!publisherDtoList.isEmpty());
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
