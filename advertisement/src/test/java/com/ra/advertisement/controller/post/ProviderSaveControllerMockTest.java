package com.ra.advertisement.controller.post;

import com.ra.advertisement.service.ProviderAdvertisementServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProviderSaveControllerMockTest {

    private static ProviderSaveController providerSaveController;
    private static ProviderAdvertisementServiceImpl mockProviderService;
    private static HttpServletRequest mockRequest;
    private static HttpServletResponse mockResponse;
    private static String answer;
    private static List<String> listOfAnswers;

    @BeforeAll
    public static void init() {
        answer = "some answer";
        listOfAnswers = new ArrayList<>();
        listOfAnswers.add(answer);
        mockProviderService = mock(ProviderAdvertisementServiceImpl.class);
        mockRequest = mock(HttpServletRequest.class);
        mockResponse = mock(HttpServletResponse.class);
        providerSaveController = new ProviderSaveController(mockProviderService);
    }

    @Test
    void whenSaveServiceWasUsedOnceAndPathWasReturnedReturnTrue() {
        when(mockProviderService.saveEntityService(mockRequest)).thenReturn(listOfAnswers);
        String pathExpected = "/providerform.jsp";
        String pathResult = providerSaveController.execute(mockRequest, mockResponse);
        verify(mockProviderService, times(1)).saveEntityService(mockRequest);
        assertEquals(pathExpected, pathResult);
    }
}