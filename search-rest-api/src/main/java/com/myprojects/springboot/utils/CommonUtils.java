package com.myprojects.springboot.utils;

import com.myprojects.springboot.dto.FlightDto;
import com.myprojects.springboot.entity.Flight;
import com.myprojects.springboot.exception.FlightInfoNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;


import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import com.myprojects.springboot.constants.MyFlightConstants;


/**
 *
 * CommonUtils : These contain Common utilities
 *
 */
public class CommonUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtils.class);

    /**
     * Sort Flight List By Duration
     *
     * @param sortDurVal : The duration value can be either asc or desc
     * @param flightLst :
     * @return List of FlightDto
     */
    public static List<FlightDto> sortFlightLstByDuration(String sortDurVal, List<FlightDto> flightLst) {

        if(!(sortDurVal.equals(MyFlightConstants.sortAsc)|| sortDurVal.equals(MyFlightConstants.sortDesc)) ){
            throw new FlightInfoNotFoundException(" Please provide sortByDuration=asc or sortByDuration=desc");
        }
        boolean sortByDurInAsc = sortDurVal.equals(MyFlightConstants.sortAsc);

        LOGGER.info("Entered sortFlightLstByDuration");
        if (!sortByDurInAsc) {
            flightLst = flightLst.stream().sorted(Comparator.comparing(FlightDto::getDuration).reversed())
                    .collect(Collectors.toList());
        } else{
            flightLst = flightLst.stream().sorted(Comparator.comparing(FlightDto::getDuration))
                    .collect(Collectors.toList());
        }
        LOGGER.info("Exiting sortFlightLstByDuration");

        return flightLst;
    }

    /**
     * This method will first Sort Flight List By Price then Duration
     *
     * @param sortPriceVal :
     * @param sortDurVal :
     * @param flightList :
     * @return List of FlightDto
     */
    public static List<FlightDto> sortFlightLstByPriceAndDuration( String sortPriceVal, String sortDurVal,
                                                            List<FlightDto> flightList) {
        LOGGER.info("Entered sortFlightLstByPriceAndDuration");

        if(!(sortPriceVal.equals(MyFlightConstants.sortAsc)|| sortPriceVal.equals(MyFlightConstants.sortDesc)) ){
            throw new FlightInfoNotFoundException(" Please provide sortByPrice=asc or sortByPrice=desc");
        }
        if(!(sortDurVal.equals(MyFlightConstants.sortAsc)|| sortDurVal.equals(MyFlightConstants.sortDesc)) ){
            throw new FlightInfoNotFoundException(" Please provide sortByDuration=asc or sortByDuration=desc");
        }

        boolean priceAsc = sortPriceVal.equals(MyFlightConstants.sortAsc);
        boolean durAsc   = sortDurVal.equals(MyFlightConstants.sortAsc);

        if (!priceAsc) {
            if (!durAsc) {
                flightList = flightList.stream()
                        .sorted(Comparator.comparingInt(FlightDto::getPrice).reversed()
                                .thenComparing(Comparator.comparing(FlightDto::getDuration).reversed()))
                        .collect(Collectors.toList());
            } else {
                flightList = flightList.stream().sorted(Comparator.comparingInt(FlightDto::getPrice).reversed()
                        .thenComparing(Comparator.comparing(FlightDto::getDuration))).collect(Collectors.toList());
            }
        }
        if (priceAsc) {
            if (!durAsc) {
                flightList = flightList.stream()
                        .sorted(Comparator.comparingInt(FlightDto::getPrice)
                                .thenComparing(Comparator.comparing(FlightDto::getDuration).reversed()))
                        .collect(Collectors.toList());
            } else {
                flightList = flightList.stream().sorted(Comparator.comparingInt(FlightDto::getPrice)
                        .thenComparing(Comparator.comparing(FlightDto::getDuration))).collect(Collectors.toList());
            }
        }
        LOGGER.info("Exiting sortFlightLstByPriceAndDuration");
        return flightList;
    }

    /**
     * Map Flight Entity to FlightDto DTO class
     *
     * @param flightDetails :
     * @return List of FlightDto
     */
    public static List<FlightDto> mapFlightEntityToFlightDto(List<Flight> flightDetails) {
        LOGGER.info("Inside mapFlightEntityToFlightDto");
        List<FlightDto> flightList = null;
        if (!CollectionUtils.isEmpty(flightDetails)) {
            flightList = flightDetails.stream().filter(Objects::nonNull)
                    .collect(
                            Collectors.mapping(
                                    fs -> new FlightDto(fs.getId(), fs.getFlightNum(), fs.getOrigin(), fs.getDestination(),
                                            fs.getDepartureTime(), fs.getArrivalTime(), fs.getPrice()),
                                    Collectors.toList()));
        }
        LOGGER.info("Exiting mapFlightEntityToFlightDto");
        return flightList;
    }

    /**
     * Sort Flight List By Duration
     *
     * @param sortByPriceVal : The duration value can be either asc or desc
     * @param FlightDtoList :
     * @return List of FlightDto
     */

    public static List<FlightDto> sortFlightLstByPrice(String sortByPriceVal, List<FlightDto> FlightDtoList) {

        LOGGER.info("Inside sortFlightLstByPrice");
        if(!(sortByPriceVal.equals(MyFlightConstants.sortAsc)|| sortByPriceVal.equals(MyFlightConstants.sortDesc)) ){
            LOGGER.error("Please provide sortByPrice=asc or sortByPrice=desc");
            throw new FlightInfoNotFoundException(" Please provide sortByPrice=asc or sortByPrice=desc");
        }
        boolean sortByPriceInAsc = sortByPriceVal.equals(MyFlightConstants.sortAsc);

        if (!sortByPriceInAsc) {
            FlightDtoList = FlightDtoList.stream().sorted(Comparator.comparing(FlightDto::getPrice).reversed())
                    .collect(Collectors.toList());
        } else {
            FlightDtoList = FlightDtoList.stream().sorted(Comparator.comparing(FlightDto::getPrice))
                    .collect(Collectors.toList());
        }

        LOGGER.info("Inside sortFlightLstByPrice");
        return FlightDtoList;
    }
}
