package com.ra.airport.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ra.airport.config.AirPortConfiguration;
import com.ra.airport.config.AirPortWebConfig;
import com.ra.airport.dto.TicketDTO;
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

import java.sql.Timestamp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AirPortConfiguration.class, AirPortWebConfig.class})
@WebAppConfiguration
class TicketControllerTest {

    private MockMvc mockMvc;
    private TicketDTO ticketDTO;
    private String ticketJson;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private TicketController ticketController;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(ticketController).build();
        ticketDTO = initTicketDTO();
        ticketJson = new ObjectMapper().writeValueAsString(ticketDTO);
    }

    @Test
    public void whenCallGETTicketThenTicketsViewShouldBeReturned() throws Exception {
        mockMvc.perform(get("/tickets"))
                .andExpect(status().isOk())
                .andExpect(view().name("ticket/show_tickets"));
    }

    @Test
    public void whenCallDELETETicketThenTrueShouldBeReturned() throws Exception {
        mockMvc.perform(delete("/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ticketJson))
                .andExpect(status().isOk());
    }

    @Test
    public void whenCallPUTTicketThenTrueShouldBeReturned() throws Exception {
        mockMvc.perform(put("/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ticketJson))
                .andExpect(status().isOk());
    }

    @Test
    public void whenCallPOSTTicketThenTrueShouldBeReturned() throws Exception {
        mockMvc.perform(post("/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ticketJson))
                .andExpect(status().isOk());
    }

    private TicketDTO initTicketDTO() {
        var ticketDTO = new TicketDTO();
        ticketDTO.setTicketId(8);
        ticketDTO.setTicketNumber("A123-456F");
        ticketDTO.setPassengerName("Petro Velykyi");
        ticketDTO.setDocument("AA192939");
        ticketDTO.setSellingDate(Timestamp.valueOf("2018-06-21 21:05:00"));
        return ticketDTO;
    }
}