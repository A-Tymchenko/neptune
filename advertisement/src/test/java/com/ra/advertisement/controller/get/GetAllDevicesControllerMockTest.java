package com.ra.advertisement.controller.get;

import com.ra.advertisement.dto.DeviceDto;
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

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class GetAllDevicesControllerMockTest {
    private static GetAllDeviceController deviceController;
    private static DeviceAdvertisementServiceImpl mockDeviceService;
    private static HttpServletRequest mockRequest;
    private static HttpServletResponse mockResponse;
    private static RequestDispatcher mockRequestDispatcher;
    private static DeviceDto deviceDto;
    private static List<DeviceDto> listOfDevices;


    @BeforeAll
    public static void init() {
        deviceDto = new DeviceDto();
        listOfDevices = new ArrayList<>();
        listOfDevices.add(deviceDto);
        mockDeviceService = mock(DeviceAdvertisementServiceImpl.class);
        mockRequest = mock(HttpServletRequest.class);
        mockResponse = mock(HttpServletResponse.class);
        deviceController = new GetAllDeviceController(mockDeviceService);
        mockRequestDispatcher = mock(RequestDispatcher.class);
    }

    @BeforeEach
    public void reInit() {
        when(mockDeviceService.getAllEntityService()).thenReturn(listOfDevices);
        when(mockRequest.getRequestDispatcher(anyString())).thenReturn(mockRequestDispatcher);
    }

    @Test
    void whenRequestDispatcherUseForwardMethodOnceReturnTrue() throws ServletException, IOException {
        deviceController.execute(mockRequest, mockResponse);
        verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
        verify(mockRequestDispatcher, times(1)).forward(mockRequest, mockResponse);
    }

    @Test
    void whenGetAllEntityServiceWasUsedTwiceReturnTrue() throws ServletException, IOException {
        deviceController.execute(mockRequest, mockResponse);
        verify(mockDeviceService, times(2)).getAllEntityService();
    }
}