package com.myprojects.springboot.service;

import com.myprojects.springboot.dto.FlightDto;
import com.myprojects.springboot.entity.Flight;

import java.util.List;
import java.util.Optional;

public interface FlightService {

    //List<Flight> searchmyflightByToAndFro(String origin, String dest);

    List<FlightDto> getFlights(String origin, String destination,
                               Optional<String> price, Optional<String> duration);

}
