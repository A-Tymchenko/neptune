package controller;

import com.ra.advertisement.config.DataBaseConfiguration;
import com.ra.advertisement.controller.ShowAllEntitiesController;
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
@ContextConfiguration(classes = DataBaseConfiguration.class)
public class ShowAllEntitiesControllerTest {
    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ShowAllEntitiesController showAllEntitiesController;
    private MockMvc mockMvc;
    ResultMatcher ok = MockMvcResultMatchers.status().is(200);

    @Before
    public void setup() {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mockMvc = builder.build();
    }

    @Test
    public void requestWithPathAllAdvertisementReturnsStatus200AndCorrespondingVievNameReturnTrue() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/advertisements");
        this.mockMvc.perform(builder).andExpect(ok);
        ModelAndView modelAndView = showAllEntitiesController.getAllAdvertisements();
        assertEquals("alladvertisements", modelAndView.getViewName());
    }

    @Test
    public void requestWithPathAllDevicesReturnsStatus200AndCorrespondingVievNameReturnTrue() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/devices");
        this.mockMvc.perform(builder).andExpect(ok);
        ModelAndView modelAndView = showAllEntitiesController.getAllDevices();
        assertEquals("alldevices", modelAndView.getViewName());
    }

    @Test
    public void requestWithPathAllProvidersReturnsStatus200AndCorrespondingVievNameReturnTrue() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/providers");
        this.mockMvc.perform(builder).andExpect(ok);
        ModelAndView modelAndView = showAllEntitiesController.getAllProviders();
        assertEquals("allproviders", modelAndView.getViewName());
    }

    @Test
    public void requestWithPathAllPublishersReturnsStatus200AndCorrespondingVievNameReturnTrue() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/publishers");
        this.mockMvc.perform(builder).andExpect(ok);
        ModelAndView modelAndView = showAllEntitiesController.getAllPublishers();
        assertEquals("allpublishers", modelAndView.getViewName());
    }
}
