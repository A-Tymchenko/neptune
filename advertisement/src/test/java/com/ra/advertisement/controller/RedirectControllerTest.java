package com.ra.advertisement.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RedirectControllerTest {
    private RedirectController redirectController;
    private MockMvc mockMvc;
    private ModelAndView actual;

    @BeforeEach
    public void preSetup() {
        redirectController = new RedirectController();
        mockMvc = MockMvcBuilders.standaloneSetup(redirectController).build();
        actual = new ModelAndView();
    }

    @Test
    public void requestWithPathAdvertisementsReturnsStatus200AndCorrespondingVievNameReturnTrue() throws Exception {
        mockMvc.perform(get("/showadvertisementsform")).andExpect(status().isOk());
        actual.setViewName("advertisementform");
        ModelAndView result = redirectController.executeAdvertisement();
        assertTrue(actual.getViewName().equals(result.getViewName()));
    }

    @Test
    public void requestWithPathDevicesReturnsStatus200AndCorrespondingVievNameReturnTrue() throws Exception {
        mockMvc.perform(get("/showdevicesform")).andExpect(status().isOk());
        actual.setViewName("deviceform");
        ModelAndView result = redirectController.executeDevice();
        assertTrue(actual.getViewName().equals(result.getViewName()));
    }

    @Test
    public void requestWithPathPublishersReturnsStatus200AndCorrespondingVievNameReturnTrue() throws Exception {
        mockMvc.perform(get("/showpublishersform")).andExpect(status().isOk());
        ModelAndView actual = new ModelAndView();
        actual.setViewName("publisherform");
        ModelAndView result = redirectController.executePublisher();
        assertTrue(actual.getViewName().equals(result.getViewName()));
    }

    @Test
    public void requestWithPathProvidersReturnsStatus200AndCorrespondingVievNameReturnTrue() throws Exception {
        mockMvc.perform(get("/showprovidersform")).andExpect(status().isOk());
        ModelAndView actual = new ModelAndView();
        actual.setViewName("providerform");
        ModelAndView result = redirectController.executeProvider();
        assertTrue(actual.getViewName().equals(result.getViewName()));
    }

    @Test
    public void requestWithPathHomeReturnsStatus200AndCorrespondingVievNameReturnTrue() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk());
        ModelAndView actual = new ModelAndView();
        actual.setViewName("index");
        ModelAndView result = redirectController.getHomePage();
        assertTrue(actual.getViewName().equals(result.getViewName()));
    }
}
