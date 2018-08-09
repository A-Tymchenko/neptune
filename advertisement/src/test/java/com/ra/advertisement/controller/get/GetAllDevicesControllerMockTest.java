package com.ra.advertisement.controller.get;

import com.ra.advertisement.dto.DeviceDto;
import com.ra.advertisement.service.DeviceAdvertisementServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GetAllDevicesControllerMockTest {
    private static GetAllDeviceController deviceController;
    private static DeviceAdvertisementServiceImpl mockDeviceService;
    private static HttpServletRequest mockRequest;
    private static HttpServletResponse mockResponse;
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
    }

    @Test
    void whenGetAllEntityServiceWasUsedOnceAndPathWasReturnedReturnTrue() throws ServletException, IOException {
        when(mockDeviceService.getAllEntityService()).thenReturn(listOfDevices);
        String pathExpected = "/WEB-INF/jsp/alldevices.jsp";
        String pathResult = deviceController.execute(mockRequest, mockResponse);
        verify(mockDeviceService, times(1)).getAllEntityService();
        assertEquals(pathExpected, pathResult);
    }
}