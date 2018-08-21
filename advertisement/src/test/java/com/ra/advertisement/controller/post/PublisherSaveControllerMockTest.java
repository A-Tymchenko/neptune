package com.ra.advertisement.controller.post;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.advertisement.service.PublisherAdvertisementServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PublisherSaveControllerMockTest {

    private static PublisherSaveController publisherSaveController;
    private static PublisherAdvertisementServiceImpl mockPublisherService;
    private static HttpServletRequest mockRequest;
    private static HttpServletResponse mockResponse;
    private static String answer;
    private static List<String> listOfAnswers;

    @BeforeAll
    public static void init() {
        answer = "some answer";
        listOfAnswers = new ArrayList<>();
        listOfAnswers.add(answer);
        mockPublisherService = mock(PublisherAdvertisementServiceImpl.class);
        mockRequest = mock(HttpServletRequest.class);
        mockResponse = mock(HttpServletResponse.class);
        publisherSaveController = new PublisherSaveController(mockPublisherService);
    }

    @Test
    void whenSaveServiceWasUsedOnceAndPathWasReturnedReturnTrue() {
        when(mockPublisherService.saveEntityService(mockRequest)).thenReturn(listOfAnswers);
        String pathExpected = "/publisherform.jsp";
        String pathResult = publisherSaveController.execute(mockRequest, mockResponse);
        verify(mockPublisherService, times(1)).saveEntityService(mockRequest);
        assertEquals(pathExpected, pathResult);
    }
}