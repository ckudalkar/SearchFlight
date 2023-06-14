package com.myprojects.springboot.controller;

import com.myprojects.springboot.dto.FlightDto;
import com.myprojects.springboot.service.FlightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller that will help get the flight Information for Origin - Destination
 * FlightController
 *
 */

@RestController
@RequestMapping("/api/v1/Flights")
public class FlightController {
    private FlightService fs;

    public FlightController(FlightService fs) {
        this.fs = fs;
    }


    /**
     * Get Api to help get flight Information
     * http://localhost:8080/api/v1/myflights/search?origin=BOM&dest=DEL
     *
     * @param origin       : required
     * @param destination  : required
     * @param price        : Optional field, to sort flight information by price.
     *                       set price=asc for ascending sort
     *                       set price=desc for descending sort
     * @param duration     : Optional field, to sort flight Information by duration.
     *                       set duration=asc for ascending sort
     *                       set duration=desc for descending sort
     *
     * @return list of FlightDto
     */

    @GetMapping()
    public ResponseEntity<List<FlightDto>> searchmyflightByToAndFro(@RequestParam String origin,
                                                                   @RequestParam String dest,
                                                                   @RequestParam Optional<String> sortByPrice,
                                                                   @RequestParam Optional<String> sortByDuration){

        List<FlightDto> Flights = fs.getFlights(origin, dest, sortByPrice, sortByDuration);
        return ResponseEntity.ok(Flights);
    }
}
