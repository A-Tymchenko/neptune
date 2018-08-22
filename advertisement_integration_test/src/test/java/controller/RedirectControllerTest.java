package controller;

import com.ra.advertisement.config.AdvertisementConfiguration;
import com.ra.advertisement.controller.RedirectController;
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

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = AdvertisementConfiguration.class)
public class RedirectControllerTest {
    ResultMatcher ok = MockMvcResultMatchers.status().is(200);

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private RedirectController redirectController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mockMvc = builder.build();
    }

    @Test
    public void requestWithPathAdvertisementsReturnsStatus200AndCorrespondingVievNameReturnTrue() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/advertisements");
        this.mockMvc.perform(builder).andExpect(ok);
        ModelAndView modelAndView = redirectController.executeAdvertisement();
        assertEquals("advertisementform", modelAndView.getViewName());
    }

    @Test
    public void requestWithPathDevicesReturnsStatus200AndCorrespondingVievNameReturnTrue() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/devices");
        this.mockMvc.perform(builder).andExpect(ok);
        ModelAndView modelAndView = redirectController.executeDevice();
        assertEquals("deviceform", modelAndView.getViewName());
    }

    @Test
    public void requestWithPathPublishersReturnsStatus200AndCorrespondingVievNameReturnTrue() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/publishers");
        this.mockMvc.perform(builder).andExpect(ok);
        ModelAndView modelAndView = redirectController.executePublisher();
        assertEquals("publisherform", modelAndView.getViewName());
    }

    @Test
    public void requestWithPathProvidersReturnsStatus200AndCorrespondingVievNameReturnTrue() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/providers");
        this.mockMvc.perform(builder).andExpect(ok);
        ModelAndView modelAndView = redirectController.executeProvider();
        assertEquals("providerform", modelAndView.getViewName());
    }


    @Test
    public void requestWithPathForIndexPageReturnsStatus200AndCorrespondingVievNameReturnTrue() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/");
        this.mockMvc.perform(builder).andExpect(ok);
        ModelAndView modelAndView = redirectController.getHomePage();
        assertEquals("index", modelAndView.getViewName());
    }
}
