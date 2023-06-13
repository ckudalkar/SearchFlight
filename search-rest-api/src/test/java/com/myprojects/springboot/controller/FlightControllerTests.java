package com.myprojects.springboot.controller;

import com.myprojects.springboot.exception.FlightInfoNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class FlightControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testFlightInfoWithOriginAndDestination() throws Exception {
        // when - action
        ResultActions response = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/Flights")
                        .param("origin", "BOM").param("dest", "DEL"));

        // then - verify the output
        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andDo(print()).andExpect(status().isOk()).andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].flightNum").exists()).andExpect(jsonPath("$[1].origin").value("BOM"))
                .andExpect(jsonPath("$[2].destination").value("DEL"));
    }

    @Test
    void testFlightInfoWithSortByPriceASC() throws Exception {
        // when - action
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/Flights").param("origin", "BOM")
                .param("dest", "DEL").param("price", "asc"));

        // then - verify the output
        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andDo(print()).andExpect(status().isOk()).andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.*").exists());
    }


    @Test
    void testFlightInfoNotFoundForGivenOriginAndDestination() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/Flights")
                        .param("origin", "GOA")
                        .param("dest", "PUNE"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> assertTrue(
                        result.getResolvedException() instanceof FlightInfoNotFoundException))
                .andExpect(result -> assertEquals(
                        "No information found for origin: GOA & destination:PUNE",
                        result.getResolvedException().getMessage()));

    }
}
