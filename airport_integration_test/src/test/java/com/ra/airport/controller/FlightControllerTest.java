package com.ra.airport.controller;

import java.time.*;
import java.time.format.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import com.ra.airport.config.AirPortConfiguration;
import com.ra.airport.config.AirPortWebConfig;
import com.ra.airport.dto.FlightDto;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AirPortConfiguration.class, AirPortWebConfig.class})
@WebAppConfiguration
public class FlightControllerTest {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DEPARTURE_DATE = "2018-06-17 13:15:00";
    private static final String ARRIVAL_DATE = "2018-06-17 15:16:00";

    private MockMvc mockMvc;
    private FlightDto flight;
    private String flightJson;

    @Autowired
    private FlightController flightController;

    @BeforeEach
    public void beforeTest() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(flightController).build();
        var gson = new Gson();
        flight = createFlight();
        flightJson = gson.toJson(flight);
    }

    @Test
    public void whenGetThenFlightsViewShouldBeReturned() throws Exception {
        mockMvc.perform(get("/flights"))
                .andExpect(status().isOk())
                .andExpect(view().name("flight/flights"));
    }

    @Test
    public void whenPostThenSuccessfulResponseShouldBeReturned() throws Exception {
        mockMvc.perform(post("/flights")
                .contentType(MediaType.APPLICATION_JSON)
                .content(flightJson))
                .andExpect(status().isOk());
    }

    @Test
    public void whenDeleteThenTrueShouldBeReturned() throws Exception {
        mockMvc.perform(delete("/flights")
                .contentType(MediaType.APPLICATION_JSON)
                .content(flightJson))
                .andExpect(status().isOk());
    }

    private FlightDto createFlight() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        LocalDateTime departureDate = LocalDateTime.parse(DEPARTURE_DATE, formatter);
        LocalDateTime arrivalDate = LocalDateTime.parse(ARRIVAL_DATE, formatter);
        flight = new FlightDto();
        flight.setFlId(1);
        flight.setName("Kyiv-Rome");
        flight.setCarrier("Wizz Air");
        flight.setMealOn(true);
        flight.setFare(100.0);
        flight.setDepartureDate(departureDate);
        flight.setArrivalDate(arrivalDate);
        return flight;
    }
}
