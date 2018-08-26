package controller;

import com.ra.advertisement.config.DataBaseConfiguration;
import com.ra.advertisement.controller.SaveEntitiesController;
import com.ra.advertisement.dto.AdvertisementDto;
import com.ra.advertisement.dto.DeviceDto;
import com.ra.advertisement.dto.ProviderDto;
import com.ra.advertisement.dto.PublisherDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = DataBaseConfiguration.class)
public class SaveObjectControllerTest {
    ResultMatcher ok = MockMvcResultMatchers.status().is(200);

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private SaveEntitiesController saveEntitiesController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mockMvc = builder.build();
    }

    @Test
    public void requestWithAdvertisementFormReturnsStatus200AndCorrespondingVievNameReturnTrue() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/advertisements");
        this.mockMvc.perform(builder).andExpect(ok).andExpect(model().attributeExists("advertisement"));
        AdvertisementDto advertisementDtoCorrect = new AdvertisementDto("Welcome advert", "Welcome to Ukraine",
                "https://ithillel.ua/", "Ukrainian");
        ModelAndView modelAndView = saveEntitiesController.saveAdvertisement(advertisementDtoCorrect);
        assertEquals("advertisementform", modelAndView.getViewName());
    }

    @Test
    public void requestWithDeviceFormReturnsStatus200AndCorrespondingVievNameReturnTrue() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/devices");
        this.mockMvc.perform(builder).andExpect(ok).andExpect(model().attributeExists("device"));
        DeviceDto deviceDtoCorrect = new DeviceDto("Nokia", "25-10", "Mobile Phone");
        ModelAndView modelAndView = saveEntitiesController.saveDevice(deviceDtoCorrect);
        assertEquals("deviceform", modelAndView.getViewName());
    }

    @Test
    public void requestWithPublisherFormReturnsStatus200AndCorrespondingVievNameReturnTrue() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/publishers");
        this.mockMvc.perform(builder).andExpect(ok).andExpect(model().attributeExists("publisher"));
        PublisherDto publisherDtoCorrect = new PublisherDto("Coca Cola", "Lviv", "224518", "Ukraine");
        ModelAndView modelAndView = saveEntitiesController.savePublisherr(publisherDtoCorrect);
        assertEquals("publisherform", modelAndView.getViewName());
    }

    @Test
    public void requestWithProviderFormReturnsStatus200AndCorrespondingVievNameReturnTrue() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/providers");
        this.mockMvc.perform(builder).andExpect(ok).andExpect(model().attributeExists("provider"));
        ProviderDto providerDtoCorrect = new ProviderDto("Coca Cola", "Lviv", "224518", "Ukraine");
        ModelAndView modelAndView = saveEntitiesController.saveProvider(providerDtoCorrect);
        assertEquals("providerform", modelAndView.getViewName());
    }
}
