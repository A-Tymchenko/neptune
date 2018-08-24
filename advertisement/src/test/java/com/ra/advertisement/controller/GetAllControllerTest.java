package com.ra.advertisement.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.ra.advertisement.entity.*;
import com.ra.advertisement.service.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetAllControllerTest {
    private static GetAllController getAllController;
    private MockMvc mockMvc;
    private ModelAndView actual;
    private static AdvertisementService advertService = mock(AdvertisementAdvertisementServiceImpl.class);
    private static AdvertisementService deviceService = mock(DeviceAdvertisementServiceImpl.class);
    private static AdvertisementService publisherService = mock(PublisherAdvertisementServiceImpl.class);
    private static AdvertisementService providerService = mock(ProviderAdvertisementServiceImpl.class);
    private static List<Advertisement> listAdvert = new ArrayList<>();
    private static List<Device> listDevices = new ArrayList<>();
    private static List<Provider> listProviders = new ArrayList<>();
    private static List<Publisher> listPublishers = new ArrayList<>();

    @BeforeAll
    public static void setUp() throws IllegalAccessException, NoSuchFieldException {
        Advertisement advertisement = new Advertisement(1L, "AdvertoNEUpdate",
                "WELCOME TO UKRAINE UPDATE", "iMAGE uRL UPDATE", "English UPDATE");
        Device device = new Device(1L, "Nokia", "25-10", "Mobile Phone");
        Provider provider = new Provider(1L, "Coca Cola", "Lviv", "224518", "Ukraine");
        Publisher publisher = new Publisher(1L, "Coca Cola", "Lviv", "224518", "Ukraine");
        listAdvert.add(advertisement);
        listDevices.add(device);
        listProviders.add(provider);
        listPublishers.add(publisher);
        getAllController = new GetAllController();
        Field controllerFieldAdvert = getAllController.getClass().getDeclaredField("advertService");
        controllerFieldAdvert.setAccessible(true);
        controllerFieldAdvert.set(getAllController, advertService);
        Field controllerFieldDevice = getAllController.getClass().getDeclaredField("devService");
        controllerFieldDevice.setAccessible(true);
        controllerFieldDevice.set(getAllController, deviceService);
        Field controllerFieldProvider = getAllController.getClass().getDeclaredField("provService");
        controllerFieldProvider.setAccessible(true);
        controllerFieldProvider.set(getAllController, providerService);
        Field controllerFieldPublisher = getAllController.getClass().getDeclaredField("publService");
        controllerFieldPublisher.setAccessible(true);
        controllerFieldPublisher.set(getAllController, publisherService);
        when(advertService.getAllEntityService()).thenReturn(listAdvert);
        when(deviceService.getAllEntityService()).thenReturn(listDevices);
        when(providerService.getAllEntityService()).thenReturn(listProviders);
        when(providerService.getAllEntityService()).thenReturn(listPublishers);
    }

    @BeforeEach
    public void preSetup() {
        mockMvc = MockMvcBuilders.standaloneSetup(getAllController).build();
        actual = new ModelAndView();
    }

    @Test
    public void requestWithPathAdvertisementGetReturnsStatus200AndCorrespondingVievNameReturnTrue() throws Exception {
        mockMvc.perform(get("/alladvertisementget")).andExpect(status().isOk());
        actual.setViewName("alladvertisement");
        ModelAndView result = getAllController.getAllAdvertisements();
        assertTrue(actual.getViewName().equals(result.getViewName()));
    }

    @Test
    public void requestWithPathDevicesGetGetReturnsStatus200AndCorrespondingVievNameReturnTrue() throws Exception {
        mockMvc.perform(get("/alldevicesget")).andExpect(status().isOk());
        actual.setViewName("alldevices");
        ModelAndView result = getAllController.getAllDevices();
        assertTrue(actual.getViewName().equals(result.getViewName()));
    }

    @Test
    public void requestWithPathProvidersdGetGetReturnsStatus200AndCorrespondingVievNameReturnTrue() throws Exception {
        mockMvc.perform(get("/allprovidersget")).andExpect(status().isOk());
        actual.setViewName("allproviders");
        ModelAndView result = getAllController.getAllProviders();
        assertTrue(actual.getViewName().equals(result.getViewName()));
    }

    @Test
    public void requestWithPathPublishersdGetGetReturnsStatus200AndCorrespondingVievNameReturnTrue() throws Exception {
        mockMvc.perform(get("/allpublishersget")).andExpect(status().isOk());
        actual.setViewName("allpublishers");
        ModelAndView result = getAllController.getAllPublishers();
        assertTrue(actual.getViewName().equals(result.getViewName()));
    }
}