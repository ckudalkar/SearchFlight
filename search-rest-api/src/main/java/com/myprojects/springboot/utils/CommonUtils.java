package com.myprojects.springboot.utils;

import com.myprojects.springboot.dto.FlightDto;
import com.myprojects.springboot.entity.MyFlight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


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
     * @param ascend
     * @param flightLst
     * @return List of FlightDto
     */
    public static List<FlightDto> sortFlightLstByDuration(boolean ascend, List<FlightDto> flightLst) {
        LOGGER.info("Entered sortFlightLstByDuration");
        if (ascend == false) {
            flightLst = flightLst.stream().sorted(Comparator.comparing(FlightDto::getDuration).reversed())
                    .collect(Collectors.toList());
        } else if (ascend == true) {
            flightLst = flightLst.stream().sorted(Comparator.comparing(FlightDto::getDuration))
                    .collect(Collectors.toList());
        }
        LOGGER.info("Exiting sortFlightLstByDuration");

        return flightLst;
    }

    /**
     * This method will first Sort Flight List By Price then Duration
     *
     * @param priceAsc
     * @param durAsc
     * @param flightList
     * @return List of FlightDto
     */
    public static List<FlightDto> sortFlightLstByPriceAndDuration( boolean priceAsc, boolean durAsc,
                                                            List<FlightDto> flightList) {
        LOGGER.info("Entered sortFlightLstByPriceAndDuration");

        if (priceAsc == false) {
            if (durAsc == false) {

                flightList = flightList.stream()
                        .sorted(Comparator.comparingInt(FlightDto::getPrice).reversed()
                                .thenComparing(Comparator.comparing(FlightDto::getDuration).reversed()))
                        .collect(Collectors.toList());
            } else if (durAsc == true) {
                flightList = flightList.stream().sorted(Comparator.comparingInt(FlightDto::getPrice).reversed()
                        .thenComparing(Comparator.comparing(FlightDto::getDuration))).collect(Collectors.toList());
            }
        }
        if (priceAsc == true) {
            if (durAsc == false) {
                flightList = flightList.stream()
                        .sorted(Comparator.comparingInt(FlightDto::getPrice)
                                .thenComparing(Comparator.comparing(FlightDto::getDuration).reversed()))
                        .collect(Collectors.toList());
            } else if (durAsc == true) {
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
     * @param flightDetails
     * @return List of FlightDto
     */
    public static List<FlightDto> mapFlightEntityToFlightDto(List<MyFlight> flightDetails) {
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


}
