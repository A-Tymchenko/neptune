package controller;

import com.ra.advertisement.config.AdvertisementConfiguration;
import com.ra.advertisement.controller.GetAllController;
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
public class GetAllControllerTest {
    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private GetAllController getAllController;
    private MockMvc mockMvc;
    ResultMatcher ok = MockMvcResultMatchers.status().is(200);

    @Before
    public void setup() {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mockMvc = builder.build();
    }

    @Test
    public void requestWithPathAllAdvertisementReturnsStatus200AndCorrespondingVievNameReturnTrue() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/alladvertisement");
        this.mockMvc.perform(builder).andExpect(ok);
        ModelAndView modelAndView = getAllController.getAllAdvertisements();
        assertEquals("alladvertisement", modelAndView.getViewName());
    }

    @Test
    public void requestWithPathAllDevicesReturnsStatus200AndCorrespondingVievNameReturnTrue() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/alldevices");
        this.mockMvc.perform(builder).andExpect(ok);
        ModelAndView modelAndView = getAllController.getAllDevices();
        assertEquals("alldevices", modelAndView.getViewName());
    }

    @Test
    public void requestWithPathAllProvidersReturnsStatus200AndCorrespondingVievNameReturnTrue() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/allproviders");
        this.mockMvc.perform(builder).andExpect(ok);
        ModelAndView modelAndView = getAllController.getAllProviders();
        assertEquals("allproviders", modelAndView.getViewName());
    }

    @Test
    public void requestWithPathAllPublishersReturnsStatus200AndCorrespondingVievNameReturnTrue() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/allpublishers");
        this.mockMvc.perform(builder).andExpect(ok);
        ModelAndView modelAndView = getAllController.getAllPublishers();
        assertEquals("allpublishers", modelAndView.getViewName());
    }
}
