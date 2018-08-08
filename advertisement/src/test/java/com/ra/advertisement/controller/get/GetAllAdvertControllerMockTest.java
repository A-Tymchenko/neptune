package com.ra.advertisement.controller.get;

import com.ra.advertisement.dto.AdvertisementDto;
import com.ra.advertisement.service.AdvertisementAdvertisementServiceImpl;
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

public class GetAllAdvertControllerMockTest {

    private static GetAllAdvertController advertController;
    private static AdvertisementAdvertisementServiceImpl mockAdvertisementService;
    private static HttpServletRequest mockRequest;
    private static HttpServletResponse mockResponse;
    private static RequestDispatcher mockRequestDispatcher;
    private static AdvertisementDto advertDto;
    private static List<AdvertisementDto> listOfAdvertisement;

    @BeforeAll
    public static void init() {
        advertDto = new AdvertisementDto();
        listOfAdvertisement = new ArrayList<>();
        listOfAdvertisement.add(advertDto);
        mockAdvertisementService = mock(AdvertisementAdvertisementServiceImpl.class);
        mockRequest = mock(HttpServletRequest.class);
        mockResponse = mock(HttpServletResponse.class);
        advertController = new GetAllAdvertController(mockAdvertisementService);
        mockRequestDispatcher = mock(RequestDispatcher.class);
    }

    @BeforeEach
    public void reInit() {
        when(mockAdvertisementService.getAllEntityService()).thenReturn(listOfAdvertisement);
        when(mockRequest.getRequestDispatcher(anyString())).thenReturn(mockRequestDispatcher);
    }

    @Test
    void whenRequestDispatcherUseForwardMethodOnceReturnTrue() throws ServletException, IOException {
        advertController.execute(mockRequest, mockResponse);
        verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
        verify(mockRequestDispatcher, times(1)).forward(mockRequest, mockResponse);
    }

    @Test
    void whenGetAllEntityServiceWasUsedTwiceReturnTrue() throws ServletException, IOException {
        advertController.execute(mockRequest, mockResponse);
        verify(mockAdvertisementService, times(2)).getAllEntityService();
    }
}