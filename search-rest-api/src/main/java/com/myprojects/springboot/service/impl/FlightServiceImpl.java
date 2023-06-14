package com.myprojects.springboot.service.impl;


import com.myprojects.springboot.dto.FlightDto;
import com.myprojects.springboot.entity.Flight;
import com.myprojects.springboot.exception.FlightInfoNotFoundException;
import com.myprojects.springboot.repository.FlightRepository;
import com.myprojects.springboot.service.FlightService;
import com.myprojects.springboot.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Service
public class FlightServiceImpl implements FlightService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlightServiceImpl.class);

    private FlightRepository fr;

    public FlightServiceImpl(FlightRepository fr) {
        this.fr = fr;
    }

    @Override
    public List<FlightDto> getFlights(String origin, String destination, Optional<String> sortByPrice, Optional<String> sortByDuration) {

        // Need Origin and Destination Mandatory
        if (origin.isBlank() || destination.isBlank()) {
            LOGGER.error("getFlights method: Need both origin and destination");
            throw new FlightInfoNotFoundException("getFlights method: Need both origin & destination");
        }

        //Now we have Origin and Destination. Get the Records
        List<Flight> FlightList = fr.findByOriginAndDestination(origin, destination);
        if (CollectionUtils.isEmpty(FlightList)) {
            LOGGER.error("getFlights method: No Info for origin:{} & destination: {}", origin, destination);
            throw new FlightInfoNotFoundException("No information found for origin: " + origin + " & destination:" + destination);
        }

        List<FlightDto> FlightDtoList = CommonUtils.mapFlightEntityToFlightDto(FlightList);

        //Now we have valid Flight List. So check Sorting preferences
        Boolean isPriceSort = sortByPrice.isPresent();
        Boolean isDurationSort  = sortByDuration.isPresent();

        if(isPriceSort && isDurationSort){
            FlightDtoList = CommonUtils.sortFlightLstByPriceAndDuration(sortByPrice.get(),sortByDuration.get(), FlightDtoList);
        }else if(isPriceSort){
            FlightDtoList = CommonUtils.sortFlightLstByPrice(sortByPrice.get(), FlightDtoList);
        }else if(isDurationSort){
            FlightDtoList = CommonUtils.sortFlightLstByDuration(sortByDuration.get(), FlightDtoList);
        }
        return FlightDtoList;
    }
}
