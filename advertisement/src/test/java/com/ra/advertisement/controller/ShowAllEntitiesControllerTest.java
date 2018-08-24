package com.ra.advertisement.controller;

import com.ra.advertisement.service.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ShowAllEntitiesControllerTest {
    private static ShowAllEntitiesController showAllEntitiesController;
    private MockMvc mockMvc;
    private ModelAndView actual;
    private static ProjectService advertService = mock(AdvertisementProjectServiceImpl.class);
    private static ProjectService deviceService = mock(DeviceProjectServiceImpl.class);
    private static ProjectService publisherService = mock(PublisherProjectServiceImpl.class);
    private static ProjectService providerService = mock(ProviderProjectServiceImpl.class);

    @BeforeAll
    public static void setUp() {
        showAllEntitiesController = new ShowAllEntitiesController(advertService, deviceService, providerService, publisherService);
    }

    @BeforeEach
    public void preSetup() {
        mockMvc = MockMvcBuilders.standaloneSetup(showAllEntitiesController).build();
        actual = new ModelAndView();
    }

    @Test
    public void requestWithPathAdvertisementGetReturnsStatus200AndCorrespondingVievNameReturnTrue() throws Exception {
        mockMvc.perform(get("/advertisements")).andExpect(status().isOk());
        actual.setViewName("alladvertisements");
        ModelAndView result = showAllEntitiesController.getAllAdvertisements();
        assertTrue(actual.getViewName().equals(result.getViewName()));
    }

    @Test
    public void requestWithPathDevicesGetGetReturnsStatus200AndCorrespondingVievNameReturnTrue() throws Exception {
        mockMvc.perform(get("/devices")).andExpect(status().isOk());
        actual.setViewName("alldevices");
        ModelAndView result = showAllEntitiesController.getAllDevices();
        assertTrue(actual.getViewName().equals(result.getViewName()));
    }

    @Test
    public void requestWithPathProvidersdGetGetReturnsStatus200AndCorrespondingVievNameReturnTrue() throws Exception {
        mockMvc.perform(get("/providers")).andExpect(status().isOk());
        actual.setViewName("allproviders");
        ModelAndView result = showAllEntitiesController.getAllProviders();
        assertTrue(actual.getViewName().equals(result.getViewName()));
    }

    @Test
    public void requestWithPathPublishersdGetGetReturnsStatus200AndCorrespondingVievNameReturnTrue() throws Exception {
        mockMvc.perform(get("/publishers")).andExpect(status().isOk());
        actual.setViewName("allpublishers");
        ModelAndView result = showAllEntitiesController.getAllPublishers();
        assertTrue(actual.getViewName().equals(result.getViewName()));
    }
}