package com.ra.airport.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ra.airport.config.AirPortConfiguration;
import com.ra.airport.config.AirPortWebConfig;
import com.ra.airport.dto.AirportDTO;
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
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AirPortConfiguration.class, AirPortWebConfig.class})
@WebAppConfiguration
public class AirportControllerTest {

    private MockMvc mockMvc;
    private AirportDTO airportDTO;
    private String airportJson;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private AirportController airportController;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(airportController).build();
        airportDTO = initAirportDTO();
        airportJson = new ObjectMapper().writeValueAsString(airportDTO);
    }

    @Test
    public void whenCallGETAirportThenIndexJspShoudBeReturned() throws Exception {
        mockMvc.perform(get("/airports"))
                .andExpect(status().isOk())
                .andExpect(view().name("airport/show_airports"));
    }

    @Test
    public void whenCallDELETEAirportThenIndexJspShoudBeReturned() throws Exception {
        mockMvc.perform(delete("/airports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(airportJson))
                .andExpect(status().isOk());
    }

    @Test
    public void whenCallPUTAirportThenIndexJspShoudBeReturned() throws Exception {
        mockMvc.perform(put("/airports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(airportJson))
                .andExpect(status().isOk());
    }

    @Test
    public void whenCallPOSTAirportThenIndexJspShoudBeReturned() throws Exception {
        mockMvc.perform(post("/airports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(airportJson))
                .andExpect(status().isOk());
    }

    private AirportDTO initAirportDTO() {
        var airportDTO = new AirportDTO();
        airportDTO.setApId(1);
        airportDTO.setApName("Texas");
        airportDTO.setApNum(1340);
        airportDTO.setApType("international");
        airportDTO.setAddress("Dallas");
        airportDTO.setTerminalCount(10);
        return airportDTO;
    }
}
