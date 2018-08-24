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
import com.ra.airport.controller.FlightController;
import com.ra.airport.dto.FlightDto;
import org.gradle.internal.impldep.com.google.gson.Gson;
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
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/create_table_skripts.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/tables_backup(data).sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/remove_table_skripts.sql")
})
public class FlightControllerTest {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DEPARTURE_DATE = "2018-06-17 13:15:00";
    private static final String ARRIVAL_DATE = "2018-06-17 15:16:00";

    private MockMvc mockMvc;
    private FlightDto flightDto;
    private String flightJson;

    @Autowired
    private FlightController flightController;

    @BeforeEach
    public void beforeTest() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(flightController).build();
        var gson = new Gson();
        flightDto = createFlightDto();
        flightJson = gson.toJson(flightDto);
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

    private FlightDto createFlightDto() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        LocalDateTime departureDate = LocalDateTime.parse(DEPARTURE_DATE, formatter);
        LocalDateTime arrivalDate = LocalDateTime.parse(ARRIVAL_DATE, formatter);
        flightDto = new FlightDto();
        flightDto.setFlId(1);
        flightDto.setName("Kyiv-Rome");
        flightDto.setCarrier("Wizz Air");
        flightDto.setMealOn(true);
        flightDto.setFare(100.0);
        //flightDto.setDepartureDate(departureDate);
        //flightDto.setArrivalDate(arrivalDate);
        return flightDto;
    }
}
