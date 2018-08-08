package com.ra.advertisement.controller.get;

import com.ra.advertisement.dto.ProviderDto;
import com.ra.advertisement.service.ProviderAdvertisementServiceImpl;
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

public class GetAllProvidersControllerMockTest {
    private static GetAllProviderController providerController;
    private static ProviderAdvertisementServiceImpl mockProviderService;
    private static HttpServletRequest mockRequest;
    private static HttpServletResponse mockResponse;
    private static RequestDispatcher mockRequestDispatcher;
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
        mockRequestDispatcher = mock(RequestDispatcher.class);
    }

    @BeforeEach
    public void reInit() {
        when(mockProviderService.getAllEntityService()).thenReturn(listOfProviders);
        when(mockRequest.getRequestDispatcher(anyString())).thenReturn(mockRequestDispatcher);
    }

    @Test
    void whenRequestDispatcherUseForwardMethodOnceReturnTrue() throws ServletException, IOException {
        providerController.execute(mockRequest, mockResponse);
        verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
        verify(mockRequestDispatcher, times(1)).forward(mockRequest, mockResponse);
    }

    @Test
    void whenGetAllEntityServiceWasUsedTwiceReturnTrue() throws ServletException, IOException {
        providerController.execute(mockRequest, mockResponse);
        verify(mockProviderService, times(2)).getAllEntityService();
    }
}