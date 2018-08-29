package com.ra.airport.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ra.airport.config.AirPortConfiguration;
import com.ra.airport.config.AirPortWebConfig;
import com.ra.airport.dto.PlaneDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AirPortConfiguration.class, AirPortWebConfig.class})
@WebAppConfiguration
public class PlaneControllerTest {

    private MockMvc mockMvc;
    private PlaneDto plane;
    private String planeJson;

    @Autowired
    private PlaneController planeController;

    @BeforeEach
    public void beforeTest() throws JsonProcessingException {
        this.mockMvc = MockMvcBuilders.standaloneSetup(planeController).build();
        plane = createPlane();
        planeJson = new ObjectMapper().writeValueAsString(plane);
    }

    @Test
    public void whenGetThenPlanesViewShouldBeReturned() throws Exception {
        mockMvc.perform(get("/planes"))
                .andExpect(status().isOk())
                .andExpect(view().name("plane/planes"));
    }

    @Test
    public void whenPostThenSuccessfulResponseShouldBeReturned() throws Exception {
        mockMvc.perform(post("/planes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(planeJson))
                .andExpect(status().isOk());
    }

    @Test
    public void whenDeleteThenTrueShouldBeReturned() throws Exception {
        mockMvc.perform(delete("/planes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(planeJson))
                .andExpect(status().isOk());
    }

    private PlaneDto createPlane() {
        plane = new PlaneDto();
        plane.setPlaneId(1);
        plane.setModel("Boeing");
        plane.setType("LargeCarrier");
        plane.setSeatsCount(250);
        plane.setPlateNumber(13249);
        return plane;
    }
}
