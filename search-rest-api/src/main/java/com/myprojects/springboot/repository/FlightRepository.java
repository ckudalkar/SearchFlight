package com.myprojects.springboot.repository;

import com.myprojects.springboot.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    List<Flight> findByOriginAndDestination(String origin, String destination);

    List<Flight> findByOriginAndDestinationOrderByPriceAsc(String origin, String destination);

    List<Flight> findByOriginAndDestinationOrderByPriceDesc(String origin, String destination);

    List<Flight> findByOrigin(String origin);

    List<Flight> findByOriginOrderByPriceAsc(String origin);

    List<Flight> findByOriginOrderByPriceDesc(String origin);
    List<Flight> findByDestination(String destination);

}
