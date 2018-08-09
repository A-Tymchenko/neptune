package com.ra.advertisement.controller.get;

import com.ra.advertisement.dto.PublisherDto;
import com.ra.advertisement.service.PublisherAdvertisementServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GetAllPublishersControllerMockTest {
    private static GetAllPublisherController publisherController;
    private static PublisherAdvertisementServiceImpl mockPublisherService;
    private static HttpServletRequest mockRequest;
    private static HttpServletResponse mockResponse;
    private static RequestDispatcher mockRequestDispatcher;
    private static PublisherDto publisherDto;
    private static List<PublisherDto> listOfPublishers;


    @BeforeAll
    public static void init() {
        PublisherDto publisherDto = new PublisherDto();
        listOfPublishers = new ArrayList<>();
        listOfPublishers.add(publisherDto);
        mockPublisherService = mock(PublisherAdvertisementServiceImpl.class);
        mockRequest = mock(HttpServletRequest.class);
        mockResponse = mock(HttpServletResponse.class);
        publisherController = new GetAllPublisherController(mockPublisherService);
    }

    @Test
    void whenGetAllEntityServiceWasUsedOnceAndPathWasReturnedReturnTrue() throws ServletException, IOException {
        when(mockPublisherService.getAllEntityService()).thenReturn(listOfPublishers);
        String pathExpected = "/WEB-INF/jsp/allpublishers.jsp";
        String pathResult = publisherController.execute(mockRequest, mockResponse);
        verify(mockPublisherService, times(1)).getAllEntityService();
        assertEquals(pathExpected, pathResult);
    }
}