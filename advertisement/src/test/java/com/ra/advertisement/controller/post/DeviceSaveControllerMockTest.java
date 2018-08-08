package com.ra.advertisement.controller.post;

import com.ra.advertisement.service.DeviceAdvertisementServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class DeviceSaveControllerMockTest {

    private static DeviceSaveController deviceSaveController;
    private static DeviceAdvertisementServiceImpl mockDeviceService;
    private static HttpServletRequest mockRequest;
    private static HttpServletResponse mockResponse;
    private static RequestDispatcher mockRequestDispatcher;
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
        mockRequestDispatcher = mock(RequestDispatcher.class);
    }

    @BeforeEach
    public void reInit() {
        when(mockDeviceService.saveEntityService(mockRequest)).thenReturn(listOfAnswers);
        when(mockRequest.getRequestDispatcher(anyString())).thenReturn(mockRequestDispatcher);
    }

    @Test
    void whenRequestDispatcherUseForwardMethodOnceReturnTrue() throws ServletException, IOException {
        deviceSaveController.execute(mockRequest, mockResponse);
        verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
        verify(mockRequestDispatcher, times(1)).forward(mockRequest, mockResponse);
    }

    @Test
    void whenSaveEntityServiceWasUsedTwiceReturnTrue() throws ServletException, IOException {
        deviceSaveController.execute(mockRequest, mockResponse);
        verify(mockDeviceService, times(2)).saveEntityService(mockRequest);
    }
}