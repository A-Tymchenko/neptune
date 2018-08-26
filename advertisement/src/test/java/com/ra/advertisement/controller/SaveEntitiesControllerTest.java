package com.ra.advertisement.controller;

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
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SaveEntitiesControllerTest {
    private static SaveEntitiesController saveEntitiesController;
    private MockMvc mockMvc;
    private ModelAndView actual;
    private static ProjectService advertService = mock(AdvertisementProjectServiceImpl.class);
    private static ProjectService deviceService = mock(DeviceProjectServiceImpl.class);
    private static ProjectService publisherService = mock(PublisherProjectServiceImpl.class);
    private static ProjectService providerService = mock(ProviderProjectServiceImpl.class);
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
        saveEntitiesController = new SaveEntitiesController(advertService, deviceService, providerService, publisherService);
    }

    @BeforeEach
    public void preSetup() {
        mockMvc = MockMvcBuilders.standaloneSetup(saveEntitiesController).build();
        actual = new ModelAndView();
    }

    @Test
    public void requestWithPathSaveAdvertisementReturnsStatus200AndCorrespondingVievNameReturnTrue() throws Exception {
        mockMvc.perform(post("/advertisements")).andExpect(status().isOk());
        actual.setViewName("advertisementform");
        ModelAndView result = saveEntitiesController.saveAdvertisement(advertisementDto);
        assertTrue(actual.getViewName().equals(result.getViewName()));
    }

    @Test
    public void requestWithPathSaveDevicesReturnsStatus200AndCorrespondingVievNameReturnTrue() throws Exception {
        mockMvc.perform(post("/devices")).andExpect(status().isOk());
        actual.setViewName("deviceform");
        ModelAndView result = saveEntitiesController.saveDevice(deviceDto);
        assertTrue(actual.getViewName().equals(result.getViewName()));
    }

    @Test
    public void requestWithPathSaveProviderReturnsStatus200AndCorrespondingVievNameReturnTrue() throws Exception {
        mockMvc.perform(post("/providers")).andExpect(status().isOk());
        actual.setViewName("providerform");
        ModelAndView result = saveEntitiesController.saveProvider(providerDto);
        assertTrue(actual.getViewName().equals(result.getViewName()));
    }

    @Test
    public void requestWithPathSavePublisherReturnsStatus200AndCorrespondingVievNameReturnTrue() throws Exception {
        mockMvc.perform(post("/publishers")).andExpect(status().isOk());
        actual.setViewName("publisherform");
        ModelAndView result = saveEntitiesController.savePublisherr(publisherDto);
        assertTrue(actual.getViewName().equals(result.getViewName()));
    }
}
