package com.ra.advertisement.controller.get;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.advertisement.dto.ProviderDto;
import com.ra.advertisement.service.ProviderAdvertisementServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GetAllProvidersControllerMockTest {
    private static GetAllProviderController providerController;
    private static ProviderAdvertisementServiceImpl mockProviderService;
    private static HttpServletRequest mockRequest;
    private static HttpServletResponse mockResponse;
    private static ProviderDto providerDto;
    private static List<ProviderDto> listOfProviders;

    @BeforeAll
    public static void init() {
        providerDto = new ProviderDto();
        listOfProviders = new ArrayList<>();
        listOfProviders.add(providerDto);
        mockProviderService = mock(ProviderAdvertisementServiceImpl.class);
        mockRequest = mock(HttpServletRequest.class);
        mockResponse = mock(HttpServletResponse.class);
        providerController = new GetAllProviderController(mockProviderService);
    }

    @Test
    void whenGetAllEntityServiceWasUsedOnceAndPathWasReturnedReturnTrue() {
        when(mockProviderService.getAllEntityService()).thenReturn(listOfProviders);
        String pathExpected = "/WEB-INF/jsp/allproviders.jsp";
        String pathResult = providerController.execute(mockRequest, mockResponse);
        verify(mockProviderService, times(1)).getAllEntityService();
        assertEquals(pathExpected, pathResult);
    }
}