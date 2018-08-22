package com.ra.advertisement.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RedirectControllerTest {

    RedirectController redirectController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        redirectController = new RedirectController();
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(redirectController).build();
    }

    @Test
    public void requestWithPathAdvertisementsReturnsStatus200AndCorrespondingVievNameReturnTrue() throws Exception {
        this.mockMvc.perform(get("/advertisements")).andExpect(status().isOk());
        ModelAndView actual = new ModelAndView();
        actual.setViewName("advertisementform");
        ModelAndView result = redirectController.executeAdvertisement();
        assertTrue(actual.getViewName().equals(result.getViewName()));
    }
}
