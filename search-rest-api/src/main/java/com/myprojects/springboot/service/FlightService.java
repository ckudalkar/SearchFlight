package com.myprojects.springboot.service;

import com.myprojects.springboot.dto.FlightDto;
import com.myprojects.springboot.entity.MyFlight;

import java.util.List;
import java.util.Optional;

public interface FlightService {

    List<MyFlight>  searchmyflightByToAndFro(String origin, String dest);

    List<MyFlight>  searchmyflightByToAndFroOrderByPriceAsc(String origin, String dest);
    List<MyFlight>  searchmyflightByToAndFroOrderByPriceDesc(String origin, String dest);

    List<FlightDto> getFlights(String origin, String destination,
                               Optional<String> price, Optional<String> duration);


}
