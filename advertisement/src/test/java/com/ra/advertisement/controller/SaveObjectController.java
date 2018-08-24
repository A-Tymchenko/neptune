package com.ra.advertisement.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.ra.advertisement.dto.*;
import com.ra.advertisement.service.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SaveObjectController {
    private static SaveObjectsController saveObjectsController;
    private MockMvc mockMvc;
    private ModelAndView actual;
    private static AdvertisementService advertService = mock(AdvertisementAdvertisementServiceImpl.class);
    private static AdvertisementService deviceService = mock(DeviceAdvertisementServiceImpl.class);
    private static AdvertisementService publisherService = mock(PublisherAdvertisementServiceImpl.class);
    private static AdvertisementService providerService = mock(ProviderAdvertisementServiceImpl.class);
    private static List<String> answer = new ArrayList<>();
    private static AdvertisementDto advertisementDto;
    private static DeviceDto deviceDto;
    private static ProviderDto providerDto;
    private static PublisherDto publisherDto;

    @BeforeAll
    public static void setUp() throws NoSuchFieldException, IllegalAccessException {
        answer.add("Object has been saved successfully");
        advertisementDto = new AdvertisementDto("AdvertoNEUpdate",
                "WELCOME TO UKRAINE UPDATE", "iMAGE uRL UPDATE", "English UPDATE");
        deviceDto = new DeviceDto("Nokia", "25-10", "Mobile Phone");
        providerDto = new ProviderDto("Coca Cola", "Lviv", "224518", "Ukraine");
        publisherDto = new PublisherDto("Coca Cola", "Lviv", "224518", "Ukraine");
        saveObjectsController = new SaveObjectsController();
        Field controllerFieldAdvert = saveObjectsController.getClass().getDeclaredField("advertService");
        controllerFieldAdvert.setAccessible(true);
        controllerFieldAdvert.set(saveObjectsController, advertService);
        Field controllerFieldDevice = saveObjectsController.getClass().getDeclaredField("devService");
        controllerFieldDevice.setAccessible(true);
        controllerFieldDevice.set(saveObjectsController, deviceService);
        Field controllerFieldPublisher = saveObjectsController.getClass().getDeclaredField("publService");
        controllerFieldPublisher.setAccessible(true);
        controllerFieldPublisher.set(saveObjectsController, publisherService);
        Field controllerFieldProvider = saveObjectsController.getClass().getDeclaredField("providService");
        controllerFieldProvider.setAccessible(true);
        controllerFieldProvider.set(saveObjectsController, providerService);
        when(advertService.saveEntityService(any(AdvertisementDto.class))).thenReturn(answer);
        when(deviceService.saveEntityService(any(DeviceDto.class))).thenReturn(answer);
        when(advertService.saveEntityService(any(PublisherDto.class))).thenReturn(answer);
        when(advertService.saveEntityService(any(ProviderDto.class))).thenReturn(answer);
    }

    @BeforeEach
    public void preSetup() {
        mockMvc = MockMvcBuilders.standaloneSetup(saveObjectsController).build();
        actual = new ModelAndView();
    }

    @Test
    public void requestWithPathSaveAdvertisementReturnsStatus200AndCorrespondingVievNameReturnTrue() throws Exception {
        mockMvc.perform(post("/saveadvertisement")).andExpect(status().isOk());
        actual.setViewName("advertisementform");
        ModelAndView result = saveObjectsController.saveAdvertisement(advertisementDto);
        assertTrue(actual.getViewName().equals(result.getViewName()));
    }

    @Test
    public void requestWithPathSaveDevicesReturnsStatus200AndCorrespondingVievNameReturnTrue() throws Exception {
        mockMvc.perform(post("/savedevice")).andExpect(status().isOk());
        actual.setViewName("deviceform");
        ModelAndView result = saveObjectsController.saveDevice(deviceDto);
        assertTrue(actual.getViewName().equals(result.getViewName()));
    }

    @Test
    public void requestWithPathSavePublisherReturnsStatus200AndCorrespondingVievNameReturnTrue() throws Exception {
        mockMvc.perform(post("/savepublisher")).andExpect(status().isOk());
        actual.setViewName("publisherform");
        ModelAndView result = saveObjectsController.savePublisherr(publisherDto);
        assertTrue(actual.getViewName().equals(result.getViewName()));
    }

    @Test
    public void requestWithPathSaveProviderReturnsStatus200AndCorrespondingVievNameReturnTrue() throws Exception {
        mockMvc.perform(post("/saveprovider")).andExpect(status().isOk());
        actual.setViewName("providerform");
        ModelAndView result = saveObjectsController.saveProvider(providerDto);
        assertTrue(actual.getViewName().equals(result.getViewName()));
    }
}
