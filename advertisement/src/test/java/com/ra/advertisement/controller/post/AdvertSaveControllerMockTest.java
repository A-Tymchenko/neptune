package com.ra.advertisement.controller.post;

import com.ra.advertisement.service.AdvertisementAdvertisementServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AdvertSaveControllerMockTest {

    private static AdvertSaveController advertSaveController;
    private static AdvertisementAdvertisementServiceImpl mockAdvertisementService;
    private static HttpServletRequest mockRequest;
    private static HttpServletResponse mockResponse;
    private static String answer;
    private static List<String> listOfAnswers;

    @BeforeAll
    public static void init() {
        answer = "some answer";
        listOfAnswers = new ArrayList<>();
        listOfAnswers.add(answer);
        mockAdvertisementService = mock(AdvertisementAdvertisementServiceImpl.class);
        mockRequest = mock(HttpServletRequest.class);
        mockResponse = mock(HttpServletResponse.class);
        advertSaveController = new AdvertSaveController(mockAdvertisementService);
    }

    @Test
    void whenSaveServiceWasUsedOnceAndPathWasReturnedReturnTrue() {
        when(mockAdvertisementService.saveEntityService(mockRequest)).thenReturn(listOfAnswers);
        String pathExpected = "/advertisementform.jsp";
        String pathResult = advertSaveController.execute(mockRequest, mockResponse);
        verify(mockAdvertisementService, times(1)).saveEntityService(mockRequest);
        assertEquals(pathExpected, pathResult);
    }
}