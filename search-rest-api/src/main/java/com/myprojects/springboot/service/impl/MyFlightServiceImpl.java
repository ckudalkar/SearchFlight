package com.myprojects.springboot.service.impl;

import com.myprojects.springboot.constants.MyFlightConstants;
import com.myprojects.springboot.dto.FlightDto;
import com.myprojects.springboot.entity.MyFlight;
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
public class MyFlightServiceImpl implements FlightService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyFlightServiceImpl.class);

    private FlightRepository fr;

    public MyFlightServiceImpl(FlightRepository fr) {
        this.fr = fr;
    }

    @Override
    public List<MyFlight> searchmyflightByToAndFro(String origin, String dest) {
        List<MyFlight> flights = fr.findByOriginAndDestination(origin, dest);
        return flights;
    }

    @Override
    public List<MyFlight> searchmyflightByToAndFroOrderByPriceAsc(String origin, String dest) {
        List<MyFlight> flights = fr.findByOriginAndDestinationOrderByPriceAsc(origin,dest);
        return flights;
    }

    @Override
    public List<MyFlight> searchmyflightByToAndFroOrderByPriceDesc(String origin, String dest) {
        List<MyFlight> flights = fr.findByOriginAndDestinationOrderByPriceDesc(origin, dest);
        return flights;
    }

    @Override
    public List<FlightDto> getFlights(String origin, String destination, Optional<String> price, Optional<String> duration) {


        List<FlightDto> myFlightDto = null;
        List<MyFlight> myFlight = null;

        if(price.isPresent() && duration.isPresent()){

            boolean isPriceAsc;
            boolean isDurAsc;
            if(price.get().equalsIgnoreCase(MyFlightConstants.sortAsc) ){
                isPriceAsc = true;
            }else if(price.get().equalsIgnoreCase(MyFlightConstants.sortDesc)) {
                isPriceAsc = false;
            }else{
                //throw exception
                throw new FlightInfoNotFoundException(
                        "No information found for origin: " + origin + " & destination:" + destination
                                + ". Please choose price=asc or price=desc");
            }
            if(duration.get().equalsIgnoreCase(MyFlightConstants.sortAsc)){
                isDurAsc = true;
            }else if (duration.get().equalsIgnoreCase(MyFlightConstants.sortDesc)){
                isDurAsc = false;
            }else{
                //throw exception
                throw new FlightInfoNotFoundException(
                        "No information found for origin: " + origin + " & destination:" + destination
                                + ". Please choose duration=asc or duration=desc");
            }


            if(isPriceAsc ){
                myFlight = searchmyflightByToAndFroOrderByPriceAsc(origin, destination);
            }else{
                myFlight = searchmyflightByToAndFroOrderByPriceDesc(origin, destination);
            }

            if (CollectionUtils.isEmpty(myFlight)) {
                LOGGER.error("getFlights method: Info not found for origin:{} & destination: {}", origin,
                        destination);

                throw new FlightInfoNotFoundException(
                        "No information found for origin: " + origin + " & destination:" + destination);
            } else {
                myFlightDto = CommonUtils.mapFlightEntityToFlightDto(myFlight);
            }

            myFlightDto = CommonUtils.sortFlightLstByPriceAndDuration(isPriceAsc, isDurAsc, myFlightDto);

            return myFlightDto;

        }else if (price.isPresent()){
            // If Sort Only by Price is Present
            if(price.get().equalsIgnoreCase(MyFlightConstants.sortAsc)){
                myFlight = searchmyflightByToAndFroOrderByPriceAsc(origin, destination);
            }else if(price.get().equalsIgnoreCase(MyFlightConstants.sortDesc)){
                myFlight = searchmyflightByToAndFroOrderByPriceDesc(origin, destination);
            }else{
                //throw exception
                throw new FlightInfoNotFoundException(
                        "No information found for origin: " + origin + " & destination:" + destination
                                + ". Please choose price=asc or price=desc");
            }
            if (CollectionUtils.isEmpty(myFlight)) {
                 LOGGER.error("getFlights method: Info Not Found for origin:{} & destination: {}", origin,
                         destination);

                throw new FlightInfoNotFoundException(
                        "No information found for origin: " + origin + " & destination:" + destination);
            } else {
                myFlightDto = CommonUtils.mapFlightEntityToFlightDto(myFlight);
            }

            return myFlightDto;

        }else if (duration.isPresent()){

            myFlight = searchmyflightByToAndFro(origin, destination);
            if (CollectionUtils.isEmpty(myFlight)) {
                 LOGGER.error("getFlights method: Info Not Found for origin:{} & destination: {}", origin,
                         destination);

                throw new FlightInfoNotFoundException(
                        "No information found for origin: " + origin + " & destination:" + destination);
            } else {
                myFlightDto = CommonUtils.mapFlightEntityToFlightDto(myFlight);
            }

            if(duration.get().equalsIgnoreCase(MyFlightConstants.sortAsc)) {
                myFlightDto = CommonUtils.sortFlightLstByDuration(true, myFlightDto);
                return myFlightDto;
            } else if (duration.get().equalsIgnoreCase(MyFlightConstants.sortDesc)){
                myFlightDto = CommonUtils.sortFlightLstByDuration(false, myFlightDto);
                return myFlightDto;
            }else{
                //throw exception
                throw new FlightInfoNotFoundException(
                        "No information found for origin: " + origin + " & destination:" + destination
                                + ". Please choose duration=asc or duration=desc");
            }
        }

        myFlight = searchmyflightByToAndFro(origin, destination);
        if (CollectionUtils.isEmpty(myFlight)) {
             LOGGER.error("getFlights method: Info Not Found for origin:{} & destination: {}", origin,
                     destination);

            throw new FlightInfoNotFoundException(
                    "No information found for origin: " + origin + " & destination:" + destination);
        } else {

            myFlightDto = CommonUtils.mapFlightEntityToFlightDto(myFlight);
        }

        return myFlightDto;
    }

}
