package com.ra.advertisement.service;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.ra.advertisement.dao.PublisherAdvertisementDaoImpl;
import com.ra.advertisement.dto.PublisherDto;
import com.ra.advertisement.entity.Publisher;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class PublisherAdvertisementServiceImplTest {
    private static PublisherAdvertisementServiceImpl publisherService;
    private static PublisherAdvertisementDaoImpl mockPublisherDao;
    private static HttpServletRequest mockRequest;
    private static Validator validator;
    private PublisherDto publisherDto;
    private Publisher publisher;
    private List<Publisher> publisherList;
    private static JdbcTemplate mockJdbcTemplate;

    @BeforeAll
    public static void init() {
        mockJdbcTemplate = mock(JdbcTemplate.class);
        mockRequest = mock(HttpServletRequest.class);
        mockPublisherDao = new PublisherAdvertisementDaoImpl(mockJdbcTemplate);
        publisherService = new PublisherAdvertisementServiceImpl(mockPublisherDao);
        mockPublisherDao = mock(PublisherAdvertisementDaoImpl.class);
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @BeforeEach
    public void reInitAdvertisementDao() {
        publisherDto = new PublisherDto("Coca Cola", "Lviv", "224518", "Ukraine");
        publisher = new Publisher(1L, "Coca Cola", "Lviv", "224518", "Ukraine");
        publisherList = new ArrayList<>();
        publisherList.add(publisher);
        when(mockRequest.getParameter("name")).thenReturn(publisherDto.getName());
        when(mockRequest.getParameter("address")).thenReturn(publisherDto.getAddress());
        when(mockRequest.getParameter("telephone")).thenReturn(publisherDto.getTelephone());
        when(mockRequest.getParameter("country")).thenReturn(publisherDto.getCountry());
    }

    /**
     * Method check validation of the dto Object when  validation was not passed.
     *
     * @return entity
     */
    @Test
    public void saveEntityServiceMethodExecutedWithConstraintViolationReturnTrue() {
        when(mockRequest.getParameter("telephone")).thenReturn("text instead of number");
        final PublisherDto dto = PublisherAdvertisementServiceImpl.pubDtoCreator(mockRequest);
        final Set<ConstraintViolation<PublisherDto>> violations = validator.validate(dto);
        publisherService.saveEntityService(mockRequest);
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
        when(mockRequest.getParameter("telephone")).thenReturn(publisherDto.getTelephone());
        final PublisherDto dto = PublisherAdvertisementServiceImpl.pubDtoCreator(mockRequest);
        final Set<ConstraintViolation<PublisherDto>> violations = validator.validate(dto);
        publisherService.saveEntityService(mockRequest);
        final Publisher publisherCreated = publisherService.pubCreator(dto);
        when(mockPublisherDao.create(publisherCreated)).thenReturn(publisher);
        assertTrue(violations.isEmpty());
    }

    /**
     * Testing method which converts dto object into Entity.
     */
    @Test
    public void convertDtoIntoEntityReturnTrue() {
        Publisher publisherCreated = PublisherAdvertisementServiceImpl.pubCreator(publisherDto);
        assertAll("publisherCreated",
                () -> assertEquals(publisherCreated.getName(), publisherDto.getName()),
                () -> assertEquals(publisherCreated.getAddress(), publisherDto.getAddress()),
                () -> assertEquals(publisherCreated.getTelephone(), publisherDto.getTelephone()),
                () -> assertEquals(publisherCreated.getCountry(), publisherDto.getCountry()));
    }

    /**
     * Testing method which convert HttpServletRequest into dto object.
     */
    @Test
    public void convertHttpServletRequestIntoDto() {
        PublisherDto publisherDtoCreatedSecond = PublisherAdvertisementServiceImpl.pubDtoCreator(mockRequest);
        assertAll("publisherDtoCreatedSecond",
                () -> assertEquals(publisherDtoCreatedSecond.getName(), publisherDto.getName()),
                () -> assertEquals(publisherDtoCreatedSecond.getAddress(), publisherDto.getAddress()),
                () -> assertEquals(publisherDtoCreatedSecond.getTelephone(), publisherDto.getTelephone()),
                () -> assertEquals(publisherDtoCreatedSecond.getCountry(), publisherDto.getCountry()));
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
