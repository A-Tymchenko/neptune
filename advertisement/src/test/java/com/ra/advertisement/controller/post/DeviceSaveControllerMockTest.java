package com.ra.advertisement.controller.post;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.advertisement.service.DeviceAdvertisementServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DeviceSaveControllerMockTest {

    private static DeviceSaveController deviceSaveController;
    private static DeviceAdvertisementServiceImpl mockDeviceService;
    private static HttpServletRequest mockRequest;
    private static HttpServletResponse mockResponse;
    private static String answer;
    private static List<String> listOfAnswers;

    @BeforeAll
    public static void init() {
        answer = "some answer";
        listOfAnswers = new ArrayList<>();
        listOfAnswers.add(answer);
        mockDeviceService = mock(DeviceAdvertisementServiceImpl.class);
        mockRequest = mock(HttpServletRequest.class);
        mockResponse = mock(HttpServletResponse.class);
        deviceSaveController = new DeviceSaveController(mockDeviceService);
    }

    @Test
    void whenSaveServiceWasUsedOnceAndPathWasReturnedReturnTrue() {
        when(mockDeviceService.saveEntityService(mockRequest)).thenReturn(listOfAnswers);
        String pathExpected = "/deviceform.jsp";
        String pathResult = deviceSaveController.execute(mockRequest, mockResponse);
        verify(mockDeviceService, times(1)).saveEntityService(mockRequest);
        assertEquals(pathExpected, pathResult);
    }
}