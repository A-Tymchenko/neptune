package com.ra.advertisement.controller.get;

import com.ra.advertisement.dto.AdvertisementDto;
import com.ra.advertisement.service.AdvertisementAdvertisementServiceImpl;
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

public class GetAllAdvertControllerMockTest {

    private static GetAllAdvertController advertController;
    private static AdvertisementAdvertisementServiceImpl mockAdvertisementService;
    private static HttpServletRequest mockRequest;
    private static HttpServletResponse mockResponse;
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
    }

    @Test
    void whenGetAllEntityServiceWasUsedOnceAndPathWasReturnedReturnTrue() throws ServletException, IOException {
        when(mockAdvertisementService.getAllEntityService()).thenReturn(listOfAdvertisement);
        String pathExpected = "/WEB-INF/jsp/alladvertisement.jsp";
        String pathResult = advertController.execute(mockRequest, mockResponse);
        verify(mockAdvertisementService, times(1)).getAllEntityService();
        assertEquals(pathExpected, pathResult);
    }
}